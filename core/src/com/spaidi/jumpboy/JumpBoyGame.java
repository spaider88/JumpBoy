package com.spaidi.jumpboy;

import com.badlogic.gdx.Game;
import com.spaidi.jumpboy.screens.GameScreen;

public class JumpBoyGame extends Game {
	@Override
	public void create() {
		setScreen(new GameScreen());
	}
}
