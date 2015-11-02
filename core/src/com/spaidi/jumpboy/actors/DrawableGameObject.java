package com.spaidi.jumpboy.actors;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.spaidi.jumpboy.actors.behaviours.Drawable;

public abstract class DrawableGameObject extends GameObject implements Drawable {

	public DrawableGameObject() {
		super();
	}

	public DrawableGameObject(Vector2 position) {
		super(position);
	}

	private Array<TextureRegion> textures = new Array<TextureRegion>();

	@Override
	public void update(float deltaTime) {}

	@Override
	public Array<TextureRegion> getTextures() {
		return textures;
	}

	@Override
	public void addTexture(TextureRegion blockTexture) {
		textures.add(blockTexture);
	}

	@Override
	public void setTextures(Array<TextureRegion> textures) {
		this.textures = textures;
	}
}
