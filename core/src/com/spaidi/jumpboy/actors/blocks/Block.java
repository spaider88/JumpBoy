package com.spaidi.jumpboy.actors.blocks;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.spaidi.jumpboy.actors.DrawableGameObject;

public class Block extends DrawableGameObject {

	public Block(Vector2 position) {
		super(position);
	}

	@Override
	public TextureRegion getCurrentTexture() {
		return getTextures().first();
	}
}
