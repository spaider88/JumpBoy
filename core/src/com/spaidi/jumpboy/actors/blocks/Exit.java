package com.spaidi.jumpboy.actors.blocks;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.spaidi.jumpboy.actors.DrawableGameObject;

public class Exit extends DrawableGameObject {
	@Override
	public TextureRegion getCurrentTexture() {
		return getTextures().first();
	}
}
