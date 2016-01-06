package com.spaidi.jumpboy.utils;

public class Rendering {
	public static int calculateTextWidth(String text) {
		return (int) (ContentLoader.getInstance().gameFont.getSpaceWidth() * text.length());
	}
}
