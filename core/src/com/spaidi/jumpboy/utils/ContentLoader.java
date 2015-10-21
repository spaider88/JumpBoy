package com.spaidi.jumpboy.utils;

import com.badlogic.gdx.Gdx;
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

	public Array<AtlasRegion> fireTexture;
	public Array<AtlasRegion> cashTexture;

	public BitmapFont gameFont;

	public Animation walkLeftAnimation;
	public Animation walkRightAnimation;

	public void loadContent() {
		loadTextures();
		loadFont();
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
