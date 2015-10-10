package com.spaidi.jumpboy.hud;

public class Lives {

	private int livesNumber = 5;

	public void takeLive() {
		--livesNumber;
	}

	public void giveLive() {
		++livesNumber;
	}

	public boolean hasLives() {
		return livesNumber > 0;
	}

	public int getLivesNumber() {
		return livesNumber;
	}
}
