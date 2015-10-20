package com.spaidi.jumpboy.actors.items;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.spaidi.jumpboy.actors.GameObject;
import com.spaidi.jumpboy.actors.behaviours.Destroyable;
import com.spaidi.jumpboy.actors.behaviours.Scoreable;
import com.spaidi.jumpboy.constants.Scores;

public class Cash extends GameObject implements Scoreable, Destroyable {

	private float size;

	public Cash(Vector2 position) {
		super(position);
		generateCashSize();
	}

	private void generateCashSize() {
		setSize((1.0f + new Random().nextFloat()) / 2.0f);
	}

	@Override
	public long score() {
		return (long) (size * Scores.PICK_DEFAULT_CASH.getPoints());
	}

	@Override
	public void destroy() {
	}
}
