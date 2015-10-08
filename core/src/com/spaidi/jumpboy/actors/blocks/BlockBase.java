package com.spaidi.jumpboy.actors.blocks;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class BlockBase {
	public static final float SIZE = 1f;

	private Vector2 position = new Vector2();
	private Rectangle bounds = new Rectangle();

	public BlockBase(Vector2 position) {
		this.position = position;
		this.bounds.setX(position.x);
		this.bounds.setY(position.y);
		this.bounds.width = SIZE;
		this.bounds.height = SIZE;
	}

	public Vector2 getPosition() {
		return position;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setWidth(float width) {
		this.bounds.width = width;
	}

	public void setHeight(float height) {
		this.bounds.width = height;
	}
}
