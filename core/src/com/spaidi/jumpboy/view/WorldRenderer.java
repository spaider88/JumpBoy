package com.spaidi.jumpboy.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.spaidi.jumpboy.World;
import com.spaidi.jumpboy.actors.GameObject;
import com.spaidi.jumpboy.actors.jumpboy.JumpBoy;
import com.spaidi.jumpboy.actors.jumpboy.JumpBoy.State;
import com.spaidi.jumpboy.constants.Messages;
import com.spaidi.jumpboy.utils.ContentLoader;

public class WorldRenderer {

	private static final float CAMERA_WIDTH = 10f;
	private static final float CAMERA_HEIGHT = 7f;

	private World world;
	private ContentLoader contentLoader;
	private OrthographicCamera cam;

	private ShapeRenderer debugRenderer;
	private SpriteBatch spriteBatch;
	private SpriteBatch hudSpriteBatch;
	private boolean debug = false;

	private long currentRedraw = 0;// XXX
	private int currentFireTextureNumber = 0;// XXX

	@SuppressWarnings("unused")
	private int width, height;

	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public WorldRenderer(World world, boolean debug) {
		this.world = world;
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
		this.cam.update();
		this.debug = debug;
		if (debug) {
			debugRenderer = new ShapeRenderer();
		}
		spriteBatch = new SpriteBatch();
		hudSpriteBatch = new SpriteBatch();

		contentLoader = new ContentLoader();
		contentLoader.loadContent();
	}

	public void render() {
		++currentRedraw;
		if (currentRedraw > 100) {
			currentRedraw = 0;
		}
		cam.position.set(world.getJumpBoy().getPosition().x, world.getJumpBoy().getPosition().y, 0);
		cam.update();
		drawHud();
		drawBlocks();
		drawGround();
		drawJumpBoy();
		drawMessages();
		if (debug) {
			drawCollisionBlocks();
			drawDebug();
		}
	}

	private void drawMessages() {
		hudSpriteBatch.begin();
		for (Messages message : world.getMessages()) {
			contentLoader.gameFont.draw(hudSpriteBatch, message.getMessage(),
					320 - (calculateTextWidth(message.getMessage()) / 2), 240);
		}
		world.getMessages().clear();
		hudSpriteBatch.end();
	}

	private void drawHud() {
		hudSpriteBatch.begin();
		int livesNumber = world.getHud().getLives().getLivesNumber();
		float heartScale = 0.5f;
		float heartWidth = contentLoader.liveTexture.getRegionWidth() * heartScale;
		float heartHeight = contentLoader.liveTexture.getRegionHeight() * heartScale;
		for (int live = 0; live < livesNumber; live++) {
			hudSpriteBatch.draw(contentLoader.liveTexture, live * heartWidth, 480 - heartHeight, heartWidth, heartHeight);
		}
		contentLoader.gameFont.draw(hudSpriteBatch, String.valueOf(world.getHud().getScore().getScore()), 320, 480);
		hudSpriteBatch.end();
	}

	private void drawBlocks() {
		spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.begin();
		for (GameObject block : world.getDrawableGameObjects(world.getLevel().getGameObjects())) {
			spriteBatch.draw(contentLoader.blockTexture, block.getPosition().x, block.getPosition().y, block.getWidth(), block.getHeight());
		}
		spriteBatch.end();
	}

	private void drawGround() {
		spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.begin();
		Vector2 xBounds = world.getVisibleXBounds();
		int groundBlockNumber = (int) xBounds.x;
		for (; groundBlockNumber <= xBounds.y; groundBlockNumber++) {
			// TODO check level ground type!
			spriteBatch.draw(contentLoader.fireTexture.get(currentFireTextureNumber), groundBlockNumber, 0, GameObject.DEFAULT_SIZE,
					GameObject.DEFAULT_SIZE);
		}
		spriteBatch.end();
		if (currentRedraw % 6 == 0) {
			currentFireTextureNumber++;
		}
		if (currentFireTextureNumber == contentLoader.fireTexture.size - 1) {
			currentFireTextureNumber = 0;
		}
	}

	private void drawJumpBoy() {
		// spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.begin();
		JumpBoy jumpBoy = world.getJumpBoy();
		contentLoader.jumpBoyFrame = jumpBoy.isFacingLeft() ? contentLoader.jumpBoyIdleLeft : contentLoader.jumpBoyIdleRight;
		if (jumpBoy.getState().equals(State.WALKING)) {
			contentLoader.jumpBoyFrame = jumpBoy.isFacingLeft() ? contentLoader.walkLeftAnimation.getKeyFrame(jumpBoy.getStateTime(), true) :
					contentLoader.walkRightAnimation.getKeyFrame(jumpBoy.getStateTime(), true);
		} else if (jumpBoy.getState().equals(State.JUMPING)) {
			if (jumpBoy.getVelocity().y > 0) {
				contentLoader.jumpBoyFrame = jumpBoy.isFacingLeft() ? contentLoader.jumpBoyJumpLeft : contentLoader.jumpBoyJumpRight;
			} else {
				contentLoader.jumpBoyFrame = jumpBoy.isFacingLeft() ? contentLoader.jumpBoyFallLeft : contentLoader.jumpBoyFallRight;
			}
		}
		spriteBatch.draw(contentLoader.jumpBoyFrame, jumpBoy.getPosition().x, jumpBoy.getPosition().y, jumpBoy.getWidth(), jumpBoy.getHeight());
		spriteBatch.end();
	}

	private void drawDebug() {
		// render blocks
		debugRenderer.begin(ShapeType.Line);
		for (GameObject block : world.getDrawableGameObjects(world.getLevel().getGameObjects())) {
			Rectangle rect = block.getBounds();
			debugRenderer.setColor(new Color(1, 0, 0, 1));
			debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);
		}
		// render Bob
		JumpBoy jumpBoy = world.getJumpBoy();
		Rectangle rect = jumpBoy.getBounds();
		debugRenderer.setColor(new Color(0, 1, 0, 1));
		debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);
		debugRenderer.end();
	}

	private void drawCollisionBlocks() {
		debugRenderer.setProjectionMatrix(cam.combined);
		debugRenderer.begin(ShapeType.Filled);
		debugRenderer.setColor(new Color(1, 1, 1, 1));
		for (Rectangle rect : world.getCollisionRects()) {
			debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);
		}
		debugRenderer.end();

	}

	private int calculateTextWidth(String text) {
		return (int) (contentLoader.gameFont.getSpaceWidth() * text.length());
	}
}
