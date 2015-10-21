package com.spaidi.jumpboy.utils.levelloader;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.spaidi.jumpboy.actors.GameObject;
import com.spaidi.jumpboy.actors.blocks.Block;
import com.spaidi.jumpboy.actors.items.Cash;
import com.spaidi.jumpboy.actors.jumpboy.JumpBoy;
import com.spaidi.jumpboy.actors.levels.Level;
import com.spaidi.jumpboy.constants.GameObjectTypes;
import com.spaidi.jumpboy.constants.GroundTypes;

public class LevelData {
	private String name;
	private int width = 0;
	private int height = 0;
	private Vector2 startPosition;
	private ArrayList<GameObjectData> gameObjects;
	private String groundType;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public ArrayList<GameObjectData> getGameObjects() {
		return gameObjects;
	}

	public void setGameObjects(ArrayList<GameObjectData> gameObjects) {
		this.gameObjects = gameObjects;
	}

	public String getGroundType() {
		return groundType;
	}

	public void setGroundType(String groundType) {
		this.groundType = groundType;
	}

	public Vector2 getStartPosition() {
		return startPosition;
	}

	public void setStartPosition(Vector2 startPosition) {
		this.startPosition = startPosition;
	}

	public Level createLevel() {
		Level level = new Level();
		level.setWidth(width);
		level.setHeight(height);
		level.setStartPosition(startPosition);
		level.setGameObjects(createGameObjects());
		level.setGroundType(GroundTypes.valueOf(groundType.toUpperCase()));
		return level;
	}

	private GameObject[][] createGameObjects() {
		GameObject[][] createdGameObjects = new GameObject[width][height];
		populateGameObjects(createdGameObjects);
		return createdGameObjects;
	}

	private <T> void populateGameObjects(GameObject[][] createdGameObjects) {
		for (GameObjectData gameObject : gameObjects) {
			GameObjectTypes gameObjectType = GameObjectTypes.fromString(gameObject.getType());
			switch (gameObjectType) {
				case BLOCK:
					addBlock(gameObject, createdGameObjects);
					break;
				case CASH:
					addCash(gameObject, createdGameObjects);
					break;
				case PLAYER:
					addPlayer(gameObject, createdGameObjects);
					break;
				default:
					break;
			}

		}
	}

	private void addBlock(GameObjectData gameObject, GameObject[][] createdGameObjects) {
		Block block = new Block(new Vector2(gameObject.getX(), gameObject.getY()));
		block.setWidth(gameObject.getWidth());
		block.setHeight(gameObject.getHeight());
		createdGameObjects[gameObject.getX()][gameObject.getY()] = block;
	}

	private void addCash(GameObjectData gameObject, GameObject[][] createdGameObjects) {
		Cash cash = new Cash(new Vector2(gameObject.getX(), gameObject.getY()));
		cash.setWidth(gameObject.getWidth());
		cash.setHeight(gameObject.getHeight());
		createdGameObjects[gameObject.getX()][gameObject.getY()] = cash;
	}

	private void addPlayer(GameObjectData gameObject, GameObject[][] createdGameObjects) {
		JumpBoy player = new JumpBoy(new Vector2(gameObject.getX(), gameObject.getY()));
		player.setWidth(gameObject.getWidth());
		player.setHeight(gameObject.getHeight());
		createdGameObjects[gameObject.getX()][gameObject.getY()] = player;
	}
}
