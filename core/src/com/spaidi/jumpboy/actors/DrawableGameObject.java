package com.spaidi.jumpboy.actors;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.spaidi.jumpboy.actors.behaviours.Drawable;
import com.spaidi.jumpboy.actors.behaviours.MakeSound;

public abstract class DrawableGameObject extends GameObject implements Drawable, MakeSound {

	public DrawableGameObject() {
		super();
	}

	public DrawableGameObject(Vector2 position) {
		super(position);
	}

	private Array<TextureRegion> textures = new Array<TextureRegion>();
	private Array<Sound> sounds = new Array<Sound>();

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

	@Override
	public Array<Sound> getSounds() {
		return sounds;
	}

	@Override
	public void addSound(Sound sound) {
		sounds.add(sound);
	}

	@Override
	public void setSounds(Array<Sound> sounds) {
		this.sounds = sounds;
	}
}
