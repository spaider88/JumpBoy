package com.spaidi.jumpboy.actors.items;

import java.util.Random;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.spaidi.jumpboy.actors.behaviours.Destroyable;
import com.spaidi.jumpboy.actors.behaviours.Scoreable;
import com.spaidi.jumpboy.constants.Scores;

public class Cash implements Scoreable, Destroyable {

	private Vector2 position = new Vector2();
	private Rectangle bounds = new Rectangle();
	private float size;

	public Cash(Vector2 position) {
		this.position = position;
		this.bounds.setX(position.x);
		this.bounds.setY(position.y);
		setSize(generateCashSize());
	}

	private float generateCashSize() {
		return (1.0f + new Random().nextFloat()) / 2.0f;
	}

	public Vector2 getPosition() {
		return position;
	}

	public float getSize() {
		return size;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setSize(float size) {
		this.size = size;
		this.bounds.width = size;
		this.bounds.width = size;
	}

	@Override
	public long score() {
		return (long) (size * Scores.PICK_DEFAULT_CASH.getPoints());
	}

	@Override
	public void destroy() {
	}
}
