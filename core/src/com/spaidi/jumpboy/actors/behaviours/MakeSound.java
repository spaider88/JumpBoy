package com.spaidi.jumpboy.actors.behaviours;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;

public interface MakeSound {
	Array<Sound> getSounds();

	void addSound(Sound sound);

	void setSounds(Array<Sound> sounds);

	Sound getCurrentSound();
}
