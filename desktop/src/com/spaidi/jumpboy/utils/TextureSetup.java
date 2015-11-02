package com.spaidi.jumpboy.utils;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

public class TextureSetup {

	public static void main(String[] args) {
		Settings settings = new Settings();
		settings.useIndexes = true;

		TexturePacker.process(settings, "..\\android\\assets\\images\\",
				"..\\android\\assets\\images\\textures\\", "textures");
		// TextureUnpacker unpacker = new TextureUnpacker();
		// TextureAtlasData data = new TextureAtlasData(new FileHandle(
		// "..\\android\\assets\\images\\textures\\textures.atlas"),
		// new FileHandle("..\\android\\assets\\images\\textures\\"), false);
		// unpacker.splitAtlas(data, "..\\android\\assets\\images\\");
	}
}
