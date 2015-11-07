package com.spaidi.jumpboy.actors.blocks;

import java.util.Random;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.spaidi.jumpboy.actors.DrawableGameObject;

public class Block extends DrawableGameObject {
	@Override
	public TextureRegion getCurrentTexture() {
		return getTextures().first();
	}

	@Override
	public Sound getCurrentSound() {
		return getSounds().get(new Random().nextInt(getSounds().size));
	}
}
