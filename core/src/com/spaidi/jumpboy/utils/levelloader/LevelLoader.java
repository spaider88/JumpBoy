package com.spaidi.jumpboy.utils.levelloader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;
import com.spaidi.jumpboy.actors.levels.Level;
import com.spaidi.jumpboy.utils.ContentLoader;

public class LevelLoader {

	public static Level loadLevel(String name, ContentLoader contentLoader) throws IOException {
		LevelData levelData = null;
		try (BufferedReader br = new BufferedReader(new FileReader("levels/" + name + ".json"))) {
			levelData = new Gson().fromJson(br, LevelData.class);
		}
		return levelData.createLevel(contentLoader);
	}
}
