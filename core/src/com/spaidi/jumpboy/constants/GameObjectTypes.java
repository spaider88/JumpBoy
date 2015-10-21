package com.spaidi.jumpboy.constants;

import com.spaidi.jumpboy.exceptions.TypeNotFoundException;

public enum GameObjectTypes {
	PLAYER("player"), BLOCK("block"), CASH("cash");
	private String name;

	private GameObjectTypes(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	public static GameObjectTypes fromString(String name) {
		name = name.toLowerCase();
		if (PLAYER.name.toLowerCase().equals(name)) {
			return PLAYER;
		} else if (BLOCK.name.toLowerCase().equals(name)) {
			return BLOCK;
		} else if (CASH.name.toLowerCase().equals(name)) {
			return CASH;
		} else {
			throw new TypeNotFoundException("GameObjectsTypes not found");
		}
	}
}
