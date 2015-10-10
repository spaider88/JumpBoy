package com.spaidi.jumpboy.constants;

public enum Messages {
	GAME_OVER("Game Over!");

	private String message;

	private Messages(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
