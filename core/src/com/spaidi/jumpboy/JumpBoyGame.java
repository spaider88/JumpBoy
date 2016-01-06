package com.spaidi.jumpboy;

import com.badlogic.gdx.Game;
import com.spaidi.jumpboy.screens.GameScreen;
import com.spaidi.jumpboy.screens.SplashScreen;

public class JumpBoyGame extends Game {

	private GameScreen game;

	@Override
	public void create() {
		this.game = new GameScreen();
		setScreen(new SplashScreen(this));
	}

	public GameScreen getGameScreen() {
		return game;
	}
}
