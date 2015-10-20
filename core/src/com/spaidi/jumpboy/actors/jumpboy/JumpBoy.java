package com.spaidi.jumpboy.actors.jumpboy;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.spaidi.jumpboy.actors.GameObject;

public class JumpBoy extends GameObject {

	public enum State {
		IDLE, WALKING, JUMPING, DYING
	}

	public static final float JUMP_VELOCITY = 1f;
	public static final float SPEED = 4f; // unit per second
	public static final float DEFAULT_SIZE = 0.5f; // unit per second

	private Vector2 position = new Vector2();
	private Vector2 acceleration = new Vector2();
	private Vector2 velocity = new Vector2();
	private Rectangle bounds = new Rectangle();
	private State state = State.IDLE;
	private boolean facingLeft = true;
	private float stateTime = 0;

	public JumpBoy(Vector2 position) {
		super(position);
		setSize(DEFAULT_SIZE, DEFAULT_SIZE);
		setStartPosition(position);
	}

	public void setStartPosition(Vector2 position) {
		setPosition(position);
		this.position = position;
		this.bounds.setX(position.x);
		this.bounds.setY(position.y);
	}

	public Vector2 getAcceleration() {
		return acceleration;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public State getState() {
		return state;
	}

	public boolean isFacingLeft() {
		return facingLeft;
	}

	public void setFacingLeft(boolean facingLeft) {
		this.facingLeft = facingLeft;
	}

	public void setState(State newState) {
		this.state = newState;
	}

	public float getStateTime() {
		return stateTime;
	}

	public void update(float delta) {
		stateTime += delta;
		position.add(velocity.cpy().scl(delta));
	}
}