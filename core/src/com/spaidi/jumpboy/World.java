package com.spaidi.jumpboy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.spaidi.jumpboy.actors.blocks.BlockBase;
import com.spaidi.jumpboy.actors.jumpboy.JumpBoy;
import com.spaidi.jumpboy.actors.levels.Level;
import com.spaidi.jumpboy.utils.levelloader.LevelLoader;

public class World {

	/** Our player controlled hero **/
	JumpBoy jumpBoy;
	/** A world has a level through which Bob needs to go through **/
	Level level;

	/** The collision boxes **/
	Array<Rectangle> collisionRects = new Array<Rectangle>();

	// Getters -----------

	public Array<Rectangle> getCollisionRects() {
		return collisionRects;
	}

	public JumpBoy getJumpBoy() {
		return jumpBoy;
	}

	public Level getLevel() {
		return level;
	}

	private final List<BlockBase> blocks = new ArrayList<BlockBase>(20);

	/** Return only the blocks that need to be drawn **/
	public List<BlockBase> getDrawableBlocks(BlockBase[][] allBlocks) {
		Vector2 xBound = getVisibleXBounds();
		Vector2 yBound = getVisibleYBounds();

		blocks.clear();
		for (int col = (int) xBound.x; col <= xBound.y; col++) {
			for (int row = (int) yBound.x; row <= yBound.y; row++) {
				BlockBase block = allBlocks[col][row];
				if (block != null) {
					blocks.add(block);
				}
			}
		}
		return blocks;
	}

	public Vector2 getVisibleXBounds() {
		// FIXME
		int x = Math.max(0, (int) jumpBoy.getPosition().x - level.getWidth());
		int x2 = Math.min(level.getWidth() - 1, (x + 2 * level.getWidth()));
		return new Vector2(x, x2);
	}

	public Vector2 getVisibleYBounds() {
		// FIXME
		int y = Math.max(0, (int) jumpBoy.getPosition().y - level.getHeight());
		int y2 = Math.min(level.getHeight() - 1, (y + 2 * level.getHeight()));
		return new Vector2(y, y2);
	}

	// --------------------
	public World() {
		createDemoWorld();
	}

	private void createDemoWorld() {
		try {
			level = LevelLoader.loadLevel("tmp_level");
			jumpBoy = new JumpBoy(level.getStartPosition());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void respawnJumpBoy() {
		jumpBoy.setStartPosition(level.getStartPosition());
	}
}
