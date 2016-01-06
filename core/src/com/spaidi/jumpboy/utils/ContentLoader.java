package com.spaidi.jumpboy.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Array;

public class ContentLoader {

	private static final int FONT_SIZE = 20;
	private static final float RUNNING_FRAME_DURATION = 0.06f;

	public TextureRegion jumpBoyIdleLeft;
	public TextureRegion jumpBoyIdleRight;
	public TextureRegion blockTexture;
	public TextureRegion jumpBoyFrame;
	public TextureRegion jumpBoyJumpLeft;
	public TextureRegion jumpBoyFallLeft;
	public TextureRegion jumpBoyJumpRight;
	public TextureRegion jumpBoyFallRight;
	public TextureRegion liveTexture;

	public Array<TextureRegion> coinTexture;
	public Array<TextureRegion> cashTexture;
	public Array<TextureRegion> exitTexture;

	public Array<Sound> coinSounds;
	public Array<Sound> cashSounds;
	public Array<Sound> collisionSounds;
	public Array<Sound> jumpSounds;
	public Sound death;
	public Sound win;
	public Sound gameOver;

	public BitmapFont gameFont;

	public Animation fireAnimation;
	public Animation walkLeftAnimation;
	public Animation walkRightAnimation;

	private static ContentLoader instance = null;

	private ContentLoader() {}

	public static ContentLoader getInstance() {
		if (instance == null) {
			instance = new ContentLoader();
		}
		return instance;
	}

	public void loadContent() {
		loadTextures();
		loadSounds();
		loadFont();
	}

	private void loadTextures() {
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("images/textures/textures.atlas"));
		jumpBoyIdleLeft = atlas.findRegion("jump-boy-01");
		jumpBoyIdleRight = new TextureRegion(jumpBoyIdleLeft);
		jumpBoyIdleRight.flip(true, false);
		blockTexture = atlas.findRegion("block");
		fireAnimation = new Animation(0.1f, atlas.findRegions("fire"));
		cashTexture = convertToArrayOfTextureRegion(atlas.findRegions("cash"));
		coinTexture = convertToArrayOfTextureRegion(atlas.findRegions("coin"));
		exitTexture = convertToArrayOfTextureRegion(atlas.findRegions("door"));
		liveTexture = atlas.findRegion("heart");
		AtlasRegion[] walkLeftFrames = new AtlasRegion[5];
		for (int i = 0; i < 5; i++) {
			walkLeftFrames[i] = atlas.findRegion("jump-boy-0" + (i + 2));
		}
		walkLeftAnimation = new Animation(RUNNING_FRAME_DURATION, walkLeftFrames);

		AtlasRegion[] walkRightFrames = new AtlasRegion[5];

		for (int i = 0; i < 5; i++) {
			walkRightFrames[i] = new AtlasRegion(walkLeftFrames[i]);
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

	private void loadSounds() {
		coinSounds = new Array<Sound>();
		coinSounds.add(Gdx.audio.newSound(Gdx.files.internal("audio/coins_00.wav")));
		coinSounds.add(Gdx.audio.newSound(Gdx.files.internal("audio/coins_01.wav")));
		coinSounds.add(Gdx.audio.newSound(Gdx.files.internal("audio/coins_02.wav")));

		cashSounds = new Array<Sound>();
		cashSounds.add(Gdx.audio.newSound(Gdx.files.internal("audio/cash_00.wav")));
		cashSounds.add(Gdx.audio.newSound(Gdx.files.internal("audio/cash_01.wav")));

		collisionSounds = new Array<Sound>();
		collisionSounds.add(Gdx.audio.newSound(Gdx.files.internal("audio/collision_00.wav")));
		collisionSounds.add(Gdx.audio.newSound(Gdx.files.internal("audio/collision_01.wav")));

		jumpSounds = new Array<Sound>();
		jumpSounds.add(Gdx.audio.newSound(Gdx.files.internal("audio/jump_00.wav")));
		jumpSounds.add(Gdx.audio.newSound(Gdx.files.internal("audio/jump_01.wav")));
		jumpSounds.add(Gdx.audio.newSound(Gdx.files.internal("audio/jump_02.wav")));

		death = Gdx.audio.newSound(Gdx.files.internal("audio/death.wav"));
		win = Gdx.audio.newSound(Gdx.files.internal("audio/win.wav"));
		gameOver = Gdx.audio.newSound(Gdx.files.internal("audio/game_over.wav"));
	}

	private Array<TextureRegion> convertToArrayOfTextureRegion(Array<AtlasRegion> array) {
		Array<TextureRegion> result = new Array<TextureRegion>();
		result.addAll(array);
		return result;
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
}
