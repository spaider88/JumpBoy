package com.spaidi.jumpboy.actors.behaviours;

import com.badlogic.gdx.math.Shape2D;

public interface Bounds<T extends Shape2D> extends Position, Measurable {
	T getBounds();
}
