package com.spaidi.jumpboy.actors.behaviours;

import com.badlogic.gdx.math.Vector2;

public interface Position {

	public void setPosition(Vector2 position);

	public Vector2 getPosition();

	public void setX(float x);

	public float getX();

	public void setY(float y);

	public float getY();
}
