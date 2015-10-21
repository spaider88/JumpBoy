package com.spaidi.jumpboy.actors.levels;

import com.badlogic.gdx.math.Vector2;
import com.spaidi.jumpboy.actors.GameObject;
import com.spaidi.jumpboy.constants.GroundTypes;

public class Level {
	private int width;
	private int height;
	private Vector2 startPosition;
	private GameObject[][] gameObjects;
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

	public GameObject[][] getGameObjects() {
		return gameObjects;
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

	public void setGameObjects(GameObject[][] gameObjects) {
		this.gameObjects = gameObjects;
	}

	public GameObject get(int x, int y) {
		return gameObjects[x][y];
	}

	public GroundTypes getGroundType() {
		return groundType;
	}

	public void setGroundType(GroundTypes groundType) {
		this.groundType = groundType;
	}
}
