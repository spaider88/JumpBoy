package com.spaidi.jumpboy.constants;

public enum Scores {
	GAIN_LOST_LIVE(200), PICK_DEFAULT_CASH(150), END_LEVEL(500);

	private long points;

	private Scores(long points) {
		this.points = points;
	}

	public long getPoints() {
		return points;
	}
}
