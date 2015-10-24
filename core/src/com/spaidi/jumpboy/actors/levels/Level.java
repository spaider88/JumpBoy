package com.spaidi.jumpboy.actors.levels;

import com.badlogic.gdx.math.Vector2;
import com.spaidi.jumpboy.actors.DrawableGameObject;
import com.spaidi.jumpboy.actors.GameObject;
import com.spaidi.jumpboy.constants.GroundTypes;

public class Level {
	private int width;
	private int height;
	private Vector2 startPosition;
	private DrawableGameObject[][] drawableGameObjects;
	private GroundTypes groundType;

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Vector2 getStartPosition() {
		return startPosition.cpy();
	}

	public DrawableGameObject[][] getDrawableGameObjects() {
		return drawableGameObjects;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setStartPosition(Vector2 startPosition) {
		this.startPosition = startPosition;
	}

	public void setDrawableGameObjects(DrawableGameObject[][] drawableGameObjects) {
		this.drawableGameObjects = drawableGameObjects;
	}

	public DrawableGameObject get(int x, int y) {
		return drawableGameObjects[x][y];
	}

	public void destroyGameObject(GameObject gameObject) {
		drawableGameObjects[(int) gameObject.getPosition().x][(int) gameObject.getPosition().y] = null;
	}

	public GroundTypes getGroundType() {
		return groundType;
	}

	public void setGroundType(GroundTypes groundType) {
		this.groundType = groundType;
	}
}
