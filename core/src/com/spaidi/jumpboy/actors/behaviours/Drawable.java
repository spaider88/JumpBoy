package com.spaidi.jumpboy.actors.behaviours;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public interface Drawable extends Changable {

	Array<TextureRegion> getTextures();

	void addTexture(TextureRegion blockTexture);

	void setTextures(Array<TextureRegion> textures);

	TextureRegion getCurrentTexture();
}
