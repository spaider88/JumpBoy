package com.spaidi.jumpboy.actors.items;

import java.util.Random;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.spaidi.jumpboy.actors.DrawableGameObject;
import com.spaidi.jumpboy.actors.behaviours.Changable;
import com.spaidi.jumpboy.actors.behaviours.Destroyable;
import com.spaidi.jumpboy.actors.behaviours.MakeSound;
import com.spaidi.jumpboy.actors.behaviours.Scoreable;
import com.spaidi.jumpboy.constants.Scores;

public class Money extends DrawableGameObject implements Scoreable, Destroyable, MakeSound, Changable {

	private static final float CASH_FRAME_DURATION = 0.08f;

	private float size;
	private float liveTime;

	private Animation cashAnimation;

	@Override
	public void setTextures(Array<TextureRegion> textures) {
		cashAnimation = new Animation(CASH_FRAME_DURATION, textures);
		super.setTextures(textures);
	}

	public void randomizeParameters() {
		generateCashSize();
		generateStartLiveTime();
	}

	private void generateCashSize() {
		size = randomFloat();
		setSize(size);
	}

	private void generateStartLiveTime() {
		liveTime = randomFloat();
	}

	private float randomFloat() {
		return (1.0f + new Random().nextFloat()) / 2.0f;
	}

	@Override
	public long score() {
		return (long) (size * Scores.PICK_DEFAULT_CASH.getPoints());
	}

	@Override
	public void destroy() {
		// TODO on destroy... some animation?
	}

	@Override
	public void update(float deltaTime) {
		liveTime += deltaTime;
	}

	@Override
	public TextureRegion getCurrentTexture() {
		return cashAnimation.getKeyFrame(liveTime, true);
	}

	@Override
	public Sound getCurrentSound() {
		return getSounds().get(new Random().nextInt(getSounds().size));
	}
}
