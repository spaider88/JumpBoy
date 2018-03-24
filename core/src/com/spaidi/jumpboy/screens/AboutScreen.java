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

public class AboutScreen implements Screen, InputProcessor {

	private JumpBoyGame game = null;

	private final String ABOUT = "About:";
	private final String AUTHOR = "Author: $pa!d!";
	private final String DATE = "Date: 2016";
	private final String GDX = "Powered by GDX";

	private SpriteBatch textSpriteBatch = null;

	public AboutScreen(JumpBoyGame game) {
		this.game = game;
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.ENTER || keycode == Keys.ESCAPE) {
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
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		dispose();
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
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		textSpriteBatch.begin();
		ContentLoader.getInstance().gameFont.getData().setScale(1.0f);
		renderText(ABOUT, Rendering.calculateVerticallyCenterTextWidth(ABOUT), 0.80f);
		renderText(AUTHOR, 100, 0.60f);
		renderText(DATE, 100, 0.40f);
		renderText(GDX, Rendering.calculateVerticallyCenterTextWidth(GDX), 0.20f);
		textSpriteBatch.end();
	}

	private void renderText(final String text, final float x, final float y) {
		ContentLoader.getInstance().gameFont.draw(textSpriteBatch, text, x, y * Gdx.graphics.getHeight());
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
