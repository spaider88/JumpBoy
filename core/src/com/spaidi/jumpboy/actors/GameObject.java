package com.spaidi.jumpboy.actors;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.spaidi.jumpboy.actors.behaviours.Bounds;
import com.spaidi.jumpboy.actors.behaviours.Measurable;
import com.spaidi.jumpboy.actors.behaviours.Position;

public abstract class GameObject implements Measurable, Position, Bounds<Rectangle> {
	public static final float DEFAULT_SIZE = 1f;

	private Vector2 position = new Vector2();
	private Rectangle bounds = new Rectangle();

	public GameObject(Vector2 position) {
		this.position = position;
		this.bounds.setX(position.x);
		this.bounds.setY(position.y);
		this.bounds.width = DEFAULT_SIZE;
		this.bounds.height = DEFAULT_SIZE;
	}

	@Override
	public void setPosition(Vector2 position) {
		this.position = position;
	}

	@Override
	public Vector2 getPosition() {
		return position;
	}

	@Override
	public float getX() {
		return position.x;
	}

	@Override
	public float getY() {
		return position.y;
	}

	@Override
	public void setWidth(float width) {
		this.bounds.width = width;
	}

	@Override
	public void setHeight(float height) {
		this.bounds.height = height;
	}

	@Override
	public float getWidth() {
		return this.bounds.width;
	}

	@Override
	public float getHeight() {
		return this.bounds.height;
	}

	@Override
	public Rectangle getBounds() {
		return bounds;
	}

	@Override
	public void setX(float x) {
		position.x = x;
	}

	@Override
	public void setY(float y) {
		position.x = y;
	}

	@Override
	public void setSize(float width, float height) {
		setWidth(width);
		setHeight(height);
	}

	@Override
	public void setSize(float size) {
		setSize(size, size);
	}
}
