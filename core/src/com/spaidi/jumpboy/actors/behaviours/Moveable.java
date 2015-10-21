package com.spaidi.jumpboy.actors.behaviours;

import com.badlogic.gdx.math.Vector2;

public interface Moveable extends Position {

	void setAcceleration(Vector2 acceleration);

	Vector2 getAcceleration();

	void setVelocity(Vector2 velocity);

	Vector2 getVelocity();

	void update(float delta);
}
