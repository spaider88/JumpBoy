package com.spaidi.jumpboy.hud;

public class Hud {
	private Lives lives;
	private Score score;

	public Hud() {
		lives = new Lives();
		score = new Score();
	}

	public Lives getLives() {
		return lives;
	}

	public Score getScore() {
		return score;
	}
}
