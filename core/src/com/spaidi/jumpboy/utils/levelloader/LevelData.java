package com.spaidi.jumpboy.utils.levelloader;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.spaidi.jumpboy.actors.DrawableGameObject;
import com.spaidi.jumpboy.actors.blocks.Block;
import com.spaidi.jumpboy.actors.blocks.Exit;
import com.spaidi.jumpboy.actors.items.Money;
import com.spaidi.jumpboy.actors.levels.Level;
import com.spaidi.jumpboy.constants.GameObjectTypes;
import com.spaidi.jumpboy.constants.GroundTypes;
import com.spaidi.jumpboy.utils.ContentLoader;

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

	public Level createLevel(ContentLoader contentLoader) {
		Level level = new Level();
		level.setWidth(width);
		level.setHeight(height);
		level.setStartPosition(startPosition);
		level.setDrawableGameObjects(createDrawableGameObjects(contentLoader));
		level.setGroundType(GroundTypes.valueOf(groundType.toUpperCase()));
		return level;
	}

	private DrawableGameObject[][] createDrawableGameObjects(ContentLoader contentLoader) {
		DrawableGameObject[][] createdGameObjects = new DrawableGameObject[width][height];
		populateGameObjects(createdGameObjects, contentLoader);
		return createdGameObjects;
	}

	private void populateGameObjects(DrawableGameObject[][] drawableGameObjects, ContentLoader contentLoader) {
		for (GameObjectData gameObject : gameObjects) {
			GameObjectTypes gameObjectType = GameObjectTypes.fromString(gameObject.getType());
			switch (gameObjectType) {
				case BLOCK:
					addDrawable(new Block(), gameObject, drawableGameObjects, contentLoader.blockTexture);
					break;
				case CASH:
					addMoney(gameObject, drawableGameObjects, contentLoader.cashTexture);
					break;
				case COIN:
					addMoney(gameObject, drawableGameObjects, contentLoader.coinTexture);
					break;
				case EXIT:
					addDrawable(new Exit(), gameObject, drawableGameObjects, contentLoader.exitTexture);
					break;
				default:
					break;
			}

		}
	}

	private void addMoney(GameObjectData gameObjectData, DrawableGameObject[][] drawableGameObjects,
			Array<TextureRegion> texture) {
		Money money = new Money();
		addDrawable(money, gameObjectData, drawableGameObjects, texture);
		money.randomizeParameters();
	}

	private void addDrawable(DrawableGameObject obj, GameObjectData gameObjectData,
			DrawableGameObject[][] drawableGameObjects,
			Array<TextureRegion> texture) {
		obj.setPosition(new Vector2(gameObjectData.getX(), gameObjectData.getY()));
		obj.setWidth(gameObjectData.getWidth());
		obj.setHeight(gameObjectData.getHeight());
		obj.setTextures(texture);
		drawableGameObjects[gameObjectData.getX()][gameObjectData.getY()] = obj;
	}

	private void addDrawable(DrawableGameObject obj, GameObjectData gameObjectData,
			DrawableGameObject[][] drawableGameObjects,
			TextureRegion ... texture) {
		addDrawable(obj, gameObjectData, drawableGameObjects, new Array<TextureRegion>(texture));
	}
}
