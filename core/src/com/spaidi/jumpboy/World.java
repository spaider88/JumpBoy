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
import com.spaidi.jumpboy.constants.Messages;
import com.spaidi.jumpboy.constants.Scores;
import com.spaidi.jumpboy.hud.Hud;
import com.spaidi.jumpboy.utils.levelloader.LevelLoader;

public class World {

	private JumpBoy jumpBoy;
	private Level level;
	private Hud hud;

	private Array<Rectangle> collisionRects = new Array<Rectangle>();
	private Array<Messages> messages = new Array<Messages>();

	public World() {
		createDemoWorld();
	}

	private void createDemoWorld() {
		try {
			level = LevelLoader.loadLevel("tmp_level");
			jumpBoy = new JumpBoy(level.getStartPosition());
			hud = new Hud();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Array<Rectangle> getCollisionRects() {
		return collisionRects;
	}

	public JumpBoy getJumpBoy() {
		return jumpBoy;
	}

	public Level getLevel() {
		return level;
	}

	public Hud getHud() {
		return hud;
	}

	public Array<Messages> getMessages() {
		return messages;
	}

	private final List<BlockBase> blocks = new ArrayList<BlockBase>(20);

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

	public void respawnJumpBoy() {
		jumpBoy.setStartPosition(level.getStartPosition());
	}

	public void killJumpBoy() {
		hud.getLives().takeLive();
		if (hud.getLives().hasLives()) {
			hud.getScore().takePoints(Scores.GAIN_LOST_LIVE.getPoints());
			respawnJumpBoy();
		} else {
			addGameMessage(Messages.GAME_OVER);
		}
	}

	private void addGameMessage(Messages msg) {
		messages.add(msg);
	}
}
