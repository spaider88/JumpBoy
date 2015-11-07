package com.spaidi.jumpboy.actors.blocks;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.spaidi.jumpboy.actors.DrawableGameObject;
import com.spaidi.jumpboy.actors.behaviours.Changable;
import com.spaidi.jumpboy.actors.behaviours.Scoreable;
import com.spaidi.jumpboy.constants.Scores;

public class Exit extends DrawableGameObject implements Scoreable, Changable {

	private static final float EXIT_FRAME_DURATION = 0.11f;

	private boolean isReached = false;
	private float timeAfterBeingReached = 0.0f;

	private Animation exitAnimation;

	@Override
	public void setTextures(Array<TextureRegion> textures) {
		exitAnimation = new Animation(EXIT_FRAME_DURATION, textures);
		super.setTextures(textures);
	}

	@Override
	public TextureRegion getCurrentTexture() {
		return exitAnimation.getKeyFrame(timeAfterBeingReached);
	}

	public void reached() {
		isReached = true;
	}

	@Override
	public void update(float deltaTime) {
		if (isReached) {
			timeAfterBeingReached += deltaTime;
		}
	}

	@Override
	public long score() {
		return Scores.END_LEVEL.getPoints();
	}

	@Override
	public Sound getCurrentSound() {
		return getSounds().first();
	}
}
