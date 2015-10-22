package com.spaidi.jumpboy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.spaidi.jumpboy.actors.DrawableGameObject;
import com.spaidi.jumpboy.actors.jumpboy.JumpBoy;
import com.spaidi.jumpboy.actors.levels.Level;
import com.spaidi.jumpboy.constants.Messages;
import com.spaidi.jumpboy.constants.Scores;
import com.spaidi.jumpboy.hud.Hud;
import com.spaidi.jumpboy.utils.ContentLoader;
import com.spaidi.jumpboy.utils.levelloader.LevelLoader;

public class World {

	private final static int VISIBILITY_X_LIMIT = 5;
	private final static int VISIBILITY_Y_LIMIT = 4;

	private JumpBoy jumpBoy;
	private Level level;
	private Hud hud;

	private ContentLoader contentLoader;

	private Array<Rectangle> collisionRects = new Array<Rectangle>();
	private Array<Messages> messages = new Array<Messages>();

	public World() {
		createDemoWorld();
	}

	private void createDemoWorld() {
		try {
			contentLoader = new ContentLoader();
			contentLoader.loadContent();
			level = LevelLoader.loadLevel("tmp_level", contentLoader);
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

	public ContentLoader getContentLoader() {
		return contentLoader;
	}

	public Array<Messages> getMessages() {
		return messages;
	}

	private final List<DrawableGameObject> drawableGameObjects = new ArrayList<DrawableGameObject>(20);

	public List<DrawableGameObject> getDrawableGameObjects(DrawableGameObject[][] allGameObjects) {
		Vector2 xBound = getVisibleXBounds();
		Vector2 yBound = getVisibleYBounds();

		drawableGameObjects.clear();
		for (int col = (int) xBound.x; col <= xBound.y; col++) {
			for (int row = (int) yBound.x; row <= yBound.y; row++) {
				DrawableGameObject gameObject = allGameObjects[col][row];
				if (gameObject != null) {
					drawableGameObjects.add(gameObject);
				}
			}
		}
		return drawableGameObjects;
	}

	public Vector2 getVisibleXBounds() {
		int x = Math.max(0, (int) jumpBoy.getPosition().x - VISIBILITY_X_LIMIT);
		int x2 = Math.min(level.getWidth() - 1, (int) (jumpBoy.getPosition().x + VISIBILITY_X_LIMIT));
		return new Vector2(x, x2);
	}

	public Vector2 getVisibleYBounds() {
		int y = Math.max(0, (int) jumpBoy.getPosition().y - VISIBILITY_Y_LIMIT);
		int y2 = Math.min(level.getHeight() - 1, (int) (jumpBoy.getPosition().y + VISIBILITY_Y_LIMIT));
		return new Vector2(y, y2);
	}

	public void respawnJumpBoy() {
		jumpBoy.setPosition(level.getStartPosition());
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

	public void update(float delta) {
		for (int currentX = 0; currentX < level.getWidth(); currentX++) {
			for (int currentY = 0; currentY < level.getHeight(); currentY++) {
				DrawableGameObject dgo = level.get(currentX, currentY);
				if (dgo != null) {
					dgo.update(delta);
				}
			}
		}
	}
}
