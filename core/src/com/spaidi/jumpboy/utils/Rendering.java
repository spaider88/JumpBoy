package com.spaidi.jumpboy.utils;

import com.badlogic.gdx.Gdx;

public class Rendering {
	public static float calculateTextWidth(String text) {
		return ContentLoader.getInstance().gameFont.getSpaceWidth() * text.length();
	}

	public static float calculateVerticallyCenterTextWidth(String text) {
		return Gdx.graphics.getWidth() / 2 - calculateTextWidth(text);
	}
}
