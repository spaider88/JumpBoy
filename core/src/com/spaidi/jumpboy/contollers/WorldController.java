package com.spaidi.jumpboy.contollers;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.spaidi.jumpboy.World;
import com.spaidi.jumpboy.actors.blocks.Block;
import com.spaidi.jumpboy.actors.jumpboy.JumpBoy;
import com.spaidi.jumpboy.actors.jumpboy.JumpBoy.State;

public class WorldController {

	enum Keys {
		LEFT, RIGHT, JUMP, FIRE
	}

	private static final long LONG_JUMP_PRESS = 150l;
	private static final float ACCELERATION = 30f;
	private static final float GRAVITY = -30f;
	private static final float MAX_JUMP_SPEED = 11f;
	private static final float DAMP = 0.90f;
	private static final float MAX_VEL = 4f;

	private World world;
	private JumpBoy jumpBoy;
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
		keys.put(Keys.FIRE, false);
	};

	// Blocks that Bob can collide with any given frame
	private Array<Block> collidable = new Array<Block>();

	public WorldController(World world) {
		this.world = world;
		this.jumpBoy = world.getJumpBoy();
	}

	// ** Key presses and touches **************** //

	public void leftPressed() {
		keys.get(keys.put(Keys.LEFT, true));
	}

	public void rightPressed() {
		keys.get(keys.put(Keys.RIGHT, true));
	}

	public void jumpPressed() {
		keys.get(keys.put(Keys.JUMP, true));
	}

	public void firePressed() {
		keys.get(keys.put(Keys.FIRE, true));
	}

	public void leftReleased() {
		keys.get(keys.put(Keys.LEFT, false));
	}

	public void rightReleased() {
		keys.get(keys.put(Keys.RIGHT, false));
	}

	public void jumpReleased() {
		keys.get(keys.put(Keys.JUMP, false));
		jumpingPressed = false;
	}

	public void fireReleased() {
		keys.get(keys.put(Keys.FIRE, false));
	}

	/** The main update method **/
	public void update(float delta) {
		// Processing the input - setting the states of Bob
		processInput();

		// If Bob is grounded then reset the state to IDLE
		if (grounded && jumpBoy.getState().equals(State.JUMPING)) {
			jumpBoy.setState(State.IDLE);
		}

		// Setting initial vertical acceleration
		jumpBoy.getAcceleration().y = GRAVITY;

		// Convert acceleration to frame time
		jumpBoy.getAcceleration().scl(delta);

		// apply acceleration to change velocity
		jumpBoy.getVelocity().add(jumpBoy.getAcceleration().x, jumpBoy.getAcceleration().y);

		// checking collisions with the surrounding blocks depending on Bob's
		// velocity
		checkCollisionWithBlocks(delta);
		checkIfJumpBoyFallOnGround();

		// apply damping to halt Bob nicely
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

	private void checkIfJumpBoyFallOnGround() {
		int endY = (int) (jumpBoy.getBounds().y + jumpBoy.getBounds().height);
		if (endY <= 0) {
			jumpBoy.getAcceleration().scl(0.0f);
			jumpBoy.getVelocity().scl(0.0f);
			world.respawnJumpBoy();
		}
	}

	/** Collision checking **/
	private void checkCollisionWithBlocks(float delta) {
		// scale velocity to frame units
		jumpBoy.getVelocity().scl(delta);

		// Obtain the rectangle from the pool instead of instantiating it
		Rectangle bobRect = rectPool.obtain();
		// set the rectangle to bob's bounding box
		bobRect.set(jumpBoy.getBounds().x, jumpBoy.getBounds().y, jumpBoy.getBounds().width, jumpBoy.getBounds().height);

		// we first check the movement on the horizontal X axis
		int startX, endX;
		int startY = (int) jumpBoy.getBounds().y;
		int endY = (int) (jumpBoy.getBounds().y + jumpBoy.getBounds().height);
		// if Bob is heading left then we check if he collides with the block on
		// his left
		// we check the block on his right otherwise
		if (jumpBoy.getVelocity().x < 0) {
			startX = endX = (int) Math.floor(jumpBoy.getBounds().x + jumpBoy.getVelocity().x);
		} else {
			startX = endX = (int) Math.floor(jumpBoy.getBounds().x + jumpBoy.getBounds().width
					+ jumpBoy.getVelocity().x);
		}

		// get the block(s) bob can collide with
		populateCollidableBlocks(startX, startY, endX, endY);

		// simulate bob's movement on the X
		bobRect.x += jumpBoy.getVelocity().x;

		// clear collision boxes in world
		world.getCollisionRects().clear();

		// if bob collides, make his horizontal velocity 0
		for (Block block : collidable) {
			if (block == null) continue;
			if (bobRect.overlaps(block.getBounds())) {
				jumpBoy.getVelocity().x = 0;
				world.getCollisionRects().add(block.getBounds());
				break;
			}
		}

		// reset the x position of the collision box
		bobRect.x = jumpBoy.getPosition().x;

		// the same thing but on the vertical Y axis
		startX = (int) jumpBoy.getBounds().x;
		endX = (int) (jumpBoy.getBounds().x + jumpBoy.getBounds().width);
		if (jumpBoy.getVelocity().y < 0) {
			startY = endY = (int) Math.floor(jumpBoy.getBounds().y + jumpBoy.getVelocity().y);
		} else {
			startY = endY = (int) Math.floor(jumpBoy.getBounds().y + jumpBoy.getBounds().height
					+ jumpBoy.getVelocity().y);
		}

		populateCollidableBlocks(startX, startY, endX, endY);

		bobRect.y += jumpBoy.getVelocity().y;

		for (Block block : collidable) {
			if (block == null) continue;
			if (bobRect.overlaps(block.getBounds())) {
				if (jumpBoy.getVelocity().y < 0) {
					grounded = true;
				}
				jumpBoy.getVelocity().y = 0;
				world.getCollisionRects().add(block.getBounds());
				break;
			}
		}
		// reset the collision box's position on Y
		bobRect.y = jumpBoy.getPosition().y;

		// update Bob's position
		// jumpBoy.getPosition().add(jumpBoy.getVelocity());
		// jumpBoy.getBounds().x = jumpBoy.getPosition().x;
		// jumpBoy.getBounds().y = jumpBoy.getPosition().y;

		// un-scale velocity (not in frame time)
		jumpBoy.getVelocity().scl(1 / delta);

		// release rectangle back to pool
		rectPool.free(bobRect);
	}

	/**
	 * populate the collidable array with the blocks found in the enclosing
	 * coordinates
	 **/
	private void populateCollidableBlocks(int startX, int startY, int endX, int endY) {
		collidable.clear();
		for (int x = startX; x <= endX; x++) {
			for (int y = startY; y <= endY; y++) {
				if (x >= 0 && x < world.getLevel().getWidth() && y >= 0 && y < world.getLevel().getHeight()) {
					collidable.add(world.getLevel().get(x, y));
				}
			}
		}
	}

	/** Change Bob's state and parameters based on input controls **/
	private boolean processInput() {
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
		return false;
	}
}