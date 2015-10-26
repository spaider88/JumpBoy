package com.spaidi.jumpboy.utils;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

public class TextureSetup {

	public static void main(String[] args) {
		Settings settings = new Settings();
		settings.useIndexes = true;

		TexturePacker.process(settings, "..\\android\\assets\\images\\",
				"..\\android\\assets\\images\\textures\\", "textures");
	}
}
