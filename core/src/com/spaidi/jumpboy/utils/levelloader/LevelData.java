package com.spaidi.jumpboy.utils.levelloader;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.spaidi.jumpboy.actors.blocks.Block;
import com.spaidi.jumpboy.actors.levels.Level;
import com.spaidi.jumpboy.constants.GroundTypes;

public class LevelData {
	private String name;
	private int width = 0;
	private int height = 0;
	private Vector2 startPosition;
	private ArrayList<BlockData> blocks;
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

	public ArrayList<BlockData> getBlocks() {
		return blocks;
	}

	public void setBlocks(ArrayList<BlockData> blocks) {
		this.blocks = blocks;
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
		level.setBlocks(createBlocks());
		level.setGroundType(GroundTypes.valueOf(groundType.toUpperCase()));
		return level;
	}

	private Block[][] createBlocks() {
		Block[][] createdBlocks = new Block[width][height];
		for (BlockData blockData : blocks) {
			int x = blockData.getX();
			int y = blockData.getY();
			float width = blockData.getWidth();
			float height = blockData.getHeight();
			Block block = new Block(new Vector2(x, y));
			block.setHeight(height);
			block.setWidth(width);
			createdBlocks[x][y] = block;
		}
		return createdBlocks;
	}
}
