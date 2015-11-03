package com.spaidi.jumpboy.contollers;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.spaidi.jumpboy.World;
import com.spaidi.jumpboy.actors.GameObject;
import com.spaidi.jumpboy.actors.behaviours.Destroyable;
import com.spaidi.jumpboy.actors.behaviours.Scoreable;
import com.spaidi.jumpboy.actors.blocks.Exit;
import com.spaidi.jumpboy.actors.jumpboy.JumpBoy;
import com.spaidi.jumpboy.actors.jumpboy.JumpBoy.State;

public class WorldController {

	enum Keys {
		LEFT, RIGHT, JUMP, FIRE, ALT, DOWN
	}

	private static final long LONG_JUMP_PRESS = 150l;
	private static final float ACCELERATION = 30f;
	private static final float GRAVITY = -30f;
	private static final float MAX_JUMP_SPEED = 11f;
	private static final float MAX_CAM_SPEED = 0.15f;
	private static final float DAMP = 0.90f;
	private static final float MAX_VEL = 4f;

	private World world;
	private JumpBoy jumpBoy;
	private OrthographicCamera cam;
	private long jumpPressedTime;
	private boolean jumpingPressed;
	private boolean grounded = false;

	// This is the rectangle pool used in collision detection
	// Good to avoid instantiation each frame
	private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
		@Override
		protected Rectangle newObject() {
			return new Rectangle();
		}
	};

	static Map<Keys, Boolean> keys = new HashMap<WorldController.Keys, Boolean>();

	static {
		keys.put(Keys.LEFT, false);
		keys.put(Keys.RIGHT, false);
		keys.put(Keys.JUMP, false);
		keys.put(Keys.DOWN, false);
		keys.put(Keys.FIRE, false);
		keys.put(Keys.ALT, false);
	};

	// GameObjects that JumpBoy can collide with any given frame
	private Array<GameObject> collidable = new Array<GameObject>();

	public WorldController() {}

	public void setWorld(World world) {
		this.world = world;
		this.cam = world.getRenderer().getCam();
		this.jumpBoy = world.getJumpBoy();
	}

	// ** Key presses and touches **************** //

	public void leftPressed() {
		keys.put(Keys.LEFT, true);
	}

	public void rightPressed() {
		keys.put(Keys.RIGHT, true);
	}

	public void jumpPressed() {
		keys.put(Keys.JUMP, true);
	}

	public void downPressed() {
		keys.put(Keys.DOWN, true);
	}

	public void altPressed() {
		keys.put(Keys.ALT, true);
	}

	public void firePressed() {
		keys.put(Keys.FIRE, true);
	}

	public void leftReleased() {
		keys.put(Keys.LEFT, false);
	}

	public void rightReleased() {
		keys.put(Keys.RIGHT, false);
	}

	public void jumpReleased() {
		keys.put(Keys.JUMP, false);
		jumpingPressed = false;
	}

	public void downReleased() {
		keys.put(Keys.DOWN, false);
	}

	public void fireReleased() {
		keys.put(Keys.FIRE, false);
	}

	public void altReleased() {
		keys.put(Keys.ALT, false);
	}

	/** The main update method **/
	public void update(float delta) {
		// Processing the input - setting the states of JumpBoy
		processInput();

		// If JumpBoy is grounded then reset the state to IDLE
		if (grounded && jumpBoy.getState().equals(State.JUMPING)) {
			jumpBoy.setState(State.IDLE);
		}

		// Setting initial vertical acceleration
		jumpBoy.getAcceleration().y = GRAVITY;

		// Convert acceleration to frame time
		jumpBoy.getAcceleration().scl(delta);

		// apply acceleration to change velocity
		jumpBoy.getVelocity().add(jumpBoy.getAcceleration().x, jumpBoy.getAcceleration().y);

		// checking collisions with the surrounding game objects depending on JumpBoy's velocity
		checkCollisionWithGameObjects(delta);
		checkIfJumpBoyTouchTheGround();

		// apply damping to halt JumpBoy nicely
		jumpBoy.getVelocity().x *= DAMP;

		// ensure terminal velocity is not exceeded
		if (jumpBoy.getVelocity().x > MAX_VEL) {
			jumpBoy.getVelocity().x = MAX_VEL;
		}
		if (jumpBoy.getVelocity().x < -MAX_VEL) {
			jumpBoy.getVelocity().x = -MAX_VEL;
		}

		// simply updates the state time
		jumpBoy.update(delta);
		jumpBoy.getBounds().x = jumpBoy.getPosition().x;
		jumpBoy.getBounds().y = jumpBoy.getPosition().y;

	}

	private void checkIfJumpBoyTouchTheGround() {
		int endY = (int) (jumpBoy.getBounds().y + jumpBoy.getBounds().height);
		if (endY <= 0) {
			jumpBoy.stop();
			world.killJumpBoy();
		}
	}

	/** Collision checking **/
	private void checkCollisionWithGameObjects(float delta) {
		// scale velocity to frame units
		jumpBoy.getVelocity().scl(delta);

		// Obtain the rectangle from the pool instead of instantiating it
		Rectangle jumpBoyRect = rectPool.obtain();
		// set the rectangle to JumpBoy's bounding box
		jumpBoyRect.set(jumpBoy.getBounds().x, jumpBoy.getBounds().y, jumpBoy.getBounds().width, jumpBoy
				.getBounds().height);

		// we first check the movement on the horizontal X axis
		int startX, endX;
		int startY = (int) jumpBoy.getBounds().y;
		int endY = (int) (jumpBoy.getBounds().y + jumpBoy.getBounds().height);
		// if JumpBoy is heading left then we check if he collides with the game object on
		// his left
		// we check the game object on his right otherwise
		if (jumpBoy.getVelocity().x < 0) {
			startX = endX = (int) Math.floor(jumpBoy.getBounds().x + jumpBoy.getVelocity().x);
		} else {
			startX = endX = (int) Math.floor(jumpBoy.getBounds().x + jumpBoy.getBounds().width
					+ jumpBoy.getVelocity().x);
		}

		// get the game object(s) JumpBoy can collide with
		populateCollidableGameObjects(startX, startY, endX, endY);

		// simulate JumpBoy's movement on the X
		jumpBoyRect.x += jumpBoy.getVelocity().x;

		// clear collision boxes in world
		world.getCollisionRects().clear();

		// if JumpBoy collides, make his horizontal velocity 0
		for (GameObject gameObject : collidable) {
			if (gameObject == null) {
				continue;
			}
			if (jumpBoyRect.overlaps(gameObject.getBounds())) {
				if (handleCollision(gameObject)) {
					jumpBoy.getVelocity().x = 0;
					break;
				}
			}
		}

		// reset the x position of the collision box
		jumpBoyRect.x = jumpBoy.getPosition().x;

		// the same thing but on the vertical Y axis
		startX = (int) jumpBoy.getBounds().x;
		endX = (int) (jumpBoy.getBounds().x + jumpBoy.getBounds().width);
		if (jumpBoy.getVelocity().y < 0) {
			startY = endY = (int) Math.floor(jumpBoy.getBounds().y + jumpBoy.getVelocity().y);
		} else {
			startY = endY = (int) Math.floor(jumpBoy.getBounds().y + jumpBoy.getBounds().height
					+ jumpBoy.getVelocity().y);
		}

		populateCollidableGameObjects(startX, startY, endX, endY);

		jumpBoyRect.y += jumpBoy.getVelocity().y;

		for (GameObject gameObject : collidable) {
			if (gameObject == null) {
				continue;
			}
			if (jumpBoyRect.overlaps(gameObject.getBounds())) {
				if (handleCollision(gameObject)) {
					if (jumpBoy.getVelocity().y < 0) {
						grounded = true;
					}
					jumpBoy.getVelocity().y = 0;
					break;
				}
			}
		}
		// reset the collision box's position on Y
		jumpBoyRect.y = jumpBoy.getPosition().y;

		// un-scale velocity (not in frame time)
		jumpBoy.getVelocity().scl(1 / delta);

		// release rectangle back to pool
		rectPool.free(jumpBoyRect);
	}

	private boolean handleCollision(GameObject gameObject) {
		boolean blockPlayer = true;
		world.getCollisionRects().add(gameObject.getBounds());
		if (gameObject instanceof Scoreable) {
			world.getHud().getScore().addPoints(((Scoreable) gameObject).score());
		}
		if (gameObject instanceof Destroyable) {
			((Destroyable) gameObject).destroy();
			world.getLevel().destroyGameObject(gameObject);
			blockPlayer = false;
		}
		if (gameObject instanceof Exit) {
			((Exit) gameObject).reached();
			jumpBoy.stop();
			world.endLevel();
		}
		return blockPlayer;
	}

	/**
	 * populate the collidable array with the game objects found in the enclosing coordinates
	 **/
	private void populateCollidableGameObjects(int startX, int startY, int endX, int endY) {
		collidable.clear();
		for (int x = startX; x <= endX; x++) {
			for (int y = startY; y <= endY; y++) {
				if (x >= 0 && x < world.getLevel().getWidth() && y >= 0 && y < world.getLevel().getHeight()) {
					collidable.add(world.getLevel().get(x, y));
				}
			}
		}
	}

	/** Change JumpBoy's state and parameters based on input controls **/
	private boolean processInput() {
		if (keys.get(Keys.ALT)) {
			jumpBoy.setState(State.IDLE);
			if (keys.get(Keys.LEFT)) {
				cam.position.x -= MAX_CAM_SPEED;
			}
			if (keys.get(Keys.RIGHT)) {
				cam.position.x += MAX_CAM_SPEED;
			}
			if (keys.get(Keys.JUMP)) {
				cam.position.y += MAX_CAM_SPEED;
			}
			if (keys.get(Keys.DOWN)) {
				cam.position.y -= MAX_CAM_SPEED;
			}
		} else {
			cam.position.set(jumpBoy.getPosition().x, jumpBoy.getPosition().y, 0);
			if (keys.get(Keys.JUMP)) {
				if (!jumpBoy.getState().equals(State.JUMPING)) {
					jumpingPressed = true;
					jumpPressedTime = System.currentTimeMillis();
					jumpBoy.setState(State.JUMPING);
					jumpBoy.getVelocity().y = MAX_JUMP_SPEED;
					grounded = false;
				} else {
					if (jumpingPressed && ((System.currentTimeMillis() - jumpPressedTime) >= LONG_JUMP_PRESS)) {
						jumpingPressed = false;
					} else {
						if (jumpingPressed) {
							jumpBoy.getVelocity().y = MAX_JUMP_SPEED;
						}
					}
				}
			}
			if (keys.get(Keys.LEFT)) {
				// left is pressed
				jumpBoy.setFacingLeft(true);
				if (!jumpBoy.getState().equals(State.JUMPING)) {
					jumpBoy.setState(State.WALKING);
				}
				jumpBoy.getAcceleration().x = -ACCELERATION;
			} else if (keys.get(Keys.RIGHT)) {
				// left is pressed
				jumpBoy.setFacingLeft(false);
				if (!jumpBoy.getState().equals(State.JUMPING)) {
					jumpBoy.setState(State.WALKING);
				}
				jumpBoy.getAcceleration().x = ACCELERATION;
			} else {
				if (!jumpBoy.getState().equals(State.JUMPING)) {
					jumpBoy.setState(State.IDLE);
				}
				jumpBoy.getAcceleration().x = 0;

			}
		}
		cam.update();
		return false;
	}
}