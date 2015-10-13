package com.spaidi.jumpboy.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.spaidi.jumpboy.World;
import com.spaidi.jumpboy.actors.blocks.Block;
import com.spaidi.jumpboy.actors.blocks.BlockBase;
import com.spaidi.jumpboy.actors.items.Cash;
import com.spaidi.jumpboy.actors.jumpboy.JumpBoy;
import com.spaidi.jumpboy.actors.jumpboy.JumpBoy.State;
import com.spaidi.jumpboy.constants.Messages;

public class WorldRenderer {

	private static final float CAMERA_WIDTH = 10f;
	private static final float CAMERA_HEIGHT = 7f;
	private static final float RUNNING_FRAME_DURATION = 0.06f;
	private static final int FONT_SIZE = 20;

	private World world;
	private OrthographicCamera cam;

	private TextureRegion jumpBoyIdleLeft;
	private TextureRegion jumpBoyIdleRight;
	private TextureRegion blockTexture;
	private TextureRegion jumpBoyFrame;
	private TextureRegion jumpBoyJumpLeft;
	private TextureRegion jumpBoyFallLeft;
	private TextureRegion jumpBoyJumpRight;
	private TextureRegion jumpBoyFallRight;
	private Array<AtlasRegion> fireTexture;
	private Array<AtlasRegion> cashTexture;
	private TextureRegion liveTexture;
	private BitmapFont gameFont;

	private Animation walkLeftAnimation;
	private Animation walkRightAnimation;

	private ShapeRenderer debugRenderer;
	private SpriteBatch spriteBatch;
	private SpriteBatch hudSpriteBatch;
	private boolean debug = false;

	private long currentRedraw = 0;// XXX
	private int currentFireTextureNumber = 0;// XXX
	private int currentCashTextureNumber = 0;// XXX

	Cash cash = new Cash(new Vector2(1, 3));// XXX

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
		loadTextures();
		loadFont();
	}

	private void loadFont() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Fipps-Regular.otf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = FONT_SIZE;
		parameter.kerning = true;
		parameter.genMipMaps = true;
		parameter.magFilter = Texture.TextureFilter.Linear;
		parameter.minFilter = Texture.TextureFilter.Linear;
		gameFont = generator.generateFont(parameter);
		generator.dispose();
	}

	private void loadTextures() {
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("images/textures/textures.atlas"));
		jumpBoyIdleLeft = atlas.findRegion("jump-boy-01");
		jumpBoyIdleRight = new TextureRegion(jumpBoyIdleLeft);
		jumpBoyIdleRight.flip(true, false);
		blockTexture = atlas.findRegion("block");
		fireTexture = atlas.findRegions("fire");
		cashTexture = atlas.findRegions("cash");
		liveTexture = atlas.findRegion("heart");
		TextureRegion[] walkLeftFrames = new TextureRegion[5];
		for (int i = 0; i < 5; i++) {
			walkLeftFrames[i] = atlas.findRegion("jump-boy-0" + (i + 2));
		}
		walkLeftAnimation = new Animation(RUNNING_FRAME_DURATION, walkLeftFrames);

		TextureRegion[] walkRightFrames = new TextureRegion[5];

		for (int i = 0; i < 5; i++) {
			walkRightFrames[i] = new TextureRegion(walkLeftFrames[i]);
			walkRightFrames[i].flip(true, false);
		}
		walkRightAnimation = new Animation(RUNNING_FRAME_DURATION, walkRightFrames);
		jumpBoyJumpLeft = atlas.findRegion("jump-boy-up");
		jumpBoyJumpRight = new TextureRegion(jumpBoyJumpLeft);
		jumpBoyJumpRight.flip(true, false);
		jumpBoyFallLeft = atlas.findRegion("jump-boy-down");
		jumpBoyFallRight = new TextureRegion(jumpBoyFallLeft);
		jumpBoyFallRight.flip(true, false);
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
			gameFont.draw(hudSpriteBatch, message.getMessage(), 320 - (calculateTextWidth(message.getMessage()) / 2),
					240);
		}
		world.getMessages().clear();
		hudSpriteBatch.end();
	}

	private void drawHud() {
		hudSpriteBatch.begin();
		int livesNumber = world.getHud().getLives().getLivesNumber();
		float heartScale = 0.5f;
		float heartWidth = liveTexture.getRegionWidth() * heartScale;
		float heartHeight = liveTexture.getRegionHeight() * heartScale;
		for (int live = 0; live < livesNumber; live++) {
			hudSpriteBatch.draw(liveTexture, live * heartWidth, 480 - heartHeight, heartWidth, heartHeight);
		}
		gameFont.draw(hudSpriteBatch, String.valueOf(world.getHud().getScore().getScore()), 320, 480);
		hudSpriteBatch.end();
	}

	private void drawBlocks() {
		spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.begin();
		for (BlockBase block : world.getDrawableBlocks(world.getLevel().getBlocks())) {
			spriteBatch.draw(blockTexture, block.getPosition().x, block.getPosition().y, Block.SIZE, Block.SIZE);
		}
		spriteBatch.draw(cashTexture.get(currentCashTextureNumber), cash.getPosition().x, cash.getPosition().y, cash.getSize(), cash.getSize());
		spriteBatch.end();
		if (currentRedraw % 6 == 0) {
			currentCashTextureNumber++;
		}
		if (currentCashTextureNumber == cashTexture.size - 1) {
			currentCashTextureNumber = 0;
		}
	}

	private void drawGround() {
		spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.begin();
		Vector2 xBounds = world.getVisibleXBounds();
		int groundBlockNumber = (int) xBounds.x;
		for (; groundBlockNumber <= xBounds.y; groundBlockNumber++) {
			// TODO check level ground type!
			spriteBatch.draw(fireTexture.get(currentFireTextureNumber), groundBlockNumber, 0, BlockBase.SIZE,
					BlockBase.SIZE);
		}
		spriteBatch.end();
		if (currentRedraw % 6 == 0) {
			currentFireTextureNumber++;
		}
		if (currentFireTextureNumber == fireTexture.size - 1) {
			currentFireTextureNumber = 0;
		}
	}

	private void drawJumpBoy() {
		// spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.begin();
		JumpBoy jumpBoy = world.getJumpBoy();
		jumpBoyFrame = jumpBoy.isFacingLeft() ? jumpBoyIdleLeft : jumpBoyIdleRight;
		if (jumpBoy.getState().equals(State.WALKING)) {
			jumpBoyFrame = jumpBoy.isFacingLeft() ? walkLeftAnimation.getKeyFrame(jumpBoy.getStateTime(), true) :
					walkRightAnimation.getKeyFrame(jumpBoy.getStateTime(), true);
		} else if (jumpBoy.getState().equals(State.JUMPING)) {
			if (jumpBoy.getVelocity().y > 0) {
				jumpBoyFrame = jumpBoy.isFacingLeft() ? jumpBoyJumpLeft : jumpBoyJumpRight;
			} else {
				jumpBoyFrame = jumpBoy.isFacingLeft() ? jumpBoyFallLeft : jumpBoyFallRight;
			}
		}
		spriteBatch.draw(jumpBoyFrame, jumpBoy.getPosition().x, jumpBoy.getPosition().y, JumpBoy.SIZE, JumpBoy.SIZE);
		spriteBatch.end();
	}

	private void drawDebug() {
		// render blocks
		debugRenderer.begin(ShapeType.Line);
		for (BlockBase block : world.getDrawableBlocks(world.getLevel().getBlocks())) {
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
		return (int) (gameFont.getSpaceWidth() * text.length());
	}
}
