package com.spaidi.jumpboy.constants;

import com.spaidi.jumpboy.exceptions.TypeNotFoundException;

public enum GameObjectTypes {
	BLOCK("block"), CASH("cash"), COIN("coin");
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
		if (BLOCK.name.toLowerCase().equals(name)) {
			return BLOCK;
		} else if (CASH.name.toLowerCase().equals(name)) {
			return CASH;
		} else if (COIN.name.toLowerCase().equals(name)) {
			return COIN;
		} else {
			throw new TypeNotFoundException("GameObjectsTypes not found");
		}
	}
}
