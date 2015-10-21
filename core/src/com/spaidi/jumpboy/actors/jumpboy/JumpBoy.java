package com.spaidi.jumpboy.actors.jumpboy;

import com.badlogic.gdx.math.Vector2;
import com.spaidi.jumpboy.actors.GameObject;
import com.spaidi.jumpboy.actors.behaviours.Moveable;

public class JumpBoy extends GameObject implements Moveable {

	public static final float DEFAULT_SIZE = 0.5f; // unit per second

	public enum State {
		IDLE, WALKING, JUMPING, DYING
	}

	private Vector2 acceleration = new Vector2();
	private Vector2 velocity = new Vector2();
	private State state = State.IDLE;
	private boolean facingLeft = true;
	private float stateTime = 0;

	public JumpBoy(Vector2 position) {
		super(position);
		setSize(DEFAULT_SIZE);
	}

	@Override
	public void setAcceleration(Vector2 acceleration) {
		this.acceleration = acceleration;
	}

	@Override
	public Vector2 getAcceleration() {
		return acceleration;
	}

	@Override
	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	@Override
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

	@Override
	public void update(float delta) {
		stateTime += delta;
		getPosition().add(velocity.cpy().scl(delta));
	}
}