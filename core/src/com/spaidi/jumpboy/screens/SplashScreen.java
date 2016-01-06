package com.spaidi.jumpboy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.spaidi.jumpboy.JumpBoyGame;
import com.spaidi.jumpboy.utils.ContentLoader;
import com.spaidi.jumpboy.utils.Rendering;

public class SplashScreen implements Screen, InputProcessor {

	private float elapsedTime = 0.0f;

	private JumpBoyGame game = null;;

	private SpriteBatch textSpriteBatch = null;;

	public SplashScreen(JumpBoyGame game) {
		this.game = game;
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.ENTER) {
			dispose();
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		dispose();
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
		textSpriteBatch = new SpriteBatch();
		ContentLoader.getInstance().loadContent();
	}

	@Override
	public void render(float delta) {
		elapsedTime += delta;
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		String textToDisplay;
		textSpriteBatch.begin();
		if (elapsedTime < 0.8f) {
			textToDisplay = "Welcome...";
		} else if (elapsedTime < 1.6f) {
			textToDisplay = "to...";
		} else if (elapsedTime < 5.0f) {
			textToDisplay = "JumpBoy";
		} else {
			textToDisplay = "Click or tap to play...";
		}
		ContentLoader.getInstance().gameFont.draw(textSpriteBatch, textToDisplay, 320 - Rendering.calculateTextWidth(
				textToDisplay), 240);
		textSpriteBatch.end();
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {
		game.setScreen(new MainMenuScreen(game));
	}

}
