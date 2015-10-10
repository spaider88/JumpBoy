package com.spaidi.jumpboy.hud;

public class Score {

	private final static long MAX_MULTIPLY_BONUS = 10;
	private final static long MIN_MULTIPLY_BONUS = 1;
	private long multiplyBonus;
	private long score;

	public Score() {
		multiplyBonus = MIN_MULTIPLY_BONUS;
		score = 0;
	}

	public void resetScore() {
		score = 0;
	}

	public void addPoints(long points) {
		score += (multiplyBonus * points);
	}

	public void takePoints(long points) {
		score -= points;
	}

	public long getScore() {
		return score;
	}

	public void resetMultiplyBonus() {
		multiplyBonus = 1;
	}

	public void raiseMultiplyBonus() {
		if (multiplyBonus < MAX_MULTIPLY_BONUS) {
			++multiplyBonus;
		}
	}

	public void reduceMultiplyBonus() {
		if (multiplyBonus > MIN_MULTIPLY_BONUS) {
			--multiplyBonus;
		}
	}
}
