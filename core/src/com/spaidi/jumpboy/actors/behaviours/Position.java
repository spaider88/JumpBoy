package com.spaidi.jumpboy.actors.behaviours;

import com.badlogic.gdx.math.Vector2;

public interface Position {

	void setPosition(Vector2 position);

	Vector2 getPosition();

	void setX(float x);

	float getX();

	void setY(float y);

	float getY();
}
