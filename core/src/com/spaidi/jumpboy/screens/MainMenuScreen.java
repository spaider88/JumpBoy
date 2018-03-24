package com.spaidi.jumpboy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.spaidi.jumpboy.JumpBoyGame;
import com.spaidi.jumpboy.utils.ContentLoader;
import com.spaidi.jumpboy.utils.Rendering;

public class MainMenuScreen implements Screen, InputProcessor {

	private JumpBoyGame game = null;

	private SpriteBatch textSpriteBatch = null;

	private Stage menuStage = null;

	private Table buttonsTable = null;
	private TextButtonStyle buttonsStyle = null;
	private TextButton playBtn = null;
	private TextButton settingsBtn = null;
	private TextButton aboutBtn = null;
	private TextButton exitBtn = null;

	public MainMenuScreen(JumpBoyGame game) {
		this.game = game;
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.ENTER) {
			dispose();
		}
		menuStage.keyDown(keycode);
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		menuStage.keyUp(keycode);
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		menuStage.keyTyped(character);
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		menuStage.touchDown(screenX, screenY, pointer, button);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		menuStage.touchUp(screenX, screenY, pointer, button);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		menuStage.touchDragged(screenX, screenY, pointer);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		menuStage.mouseMoved(screenX, screenY);
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		menuStage.scrolled(amount);
		return false;
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);

		textSpriteBatch = new SpriteBatch();

		menuStage = new Stage();
		buttonsTable = new Table();
		buttonsTable.setBounds(0, -0.25f * Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		buttonsStyle = new TextButtonStyle();
		buttonsStyle.font = ContentLoader.getInstance().gameFont;
		buttonsStyle.pressedOffsetX = -1;
		buttonsStyle.pressedOffsetX = 1;

		playBtn = setupButton("Play");
		settingsBtn = setupButton("Settings");
		aboutBtn = setupButton("About");
		exitBtn = setupButton("Exit");
		playBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(game.getGameScreen());
			}
		});
		aboutBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new AboutScreen(game));
			}
		});
		exitBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				dispose();
			}
		});

		buttonsTable.add(playBtn);
		buttonsTable.add(settingsBtn);
		buttonsTable.add(aboutBtn);
		buttonsTable.add(exitBtn);

		menuStage.addActor(buttonsTable);
	}

	private TextButton setupButton(String text) {
		TextButton btn = new TextButton(text, buttonsStyle);
		btn.pad(20.0f);
		return btn;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		textSpriteBatch.begin();
		ContentLoader.getInstance().gameFont.getData().setScale(2.0f);
		ContentLoader.getInstance().gameFont.draw(textSpriteBatch, "JumpBoy", 320 - Rendering.calculateTextWidth(
				"JumpBoy"), 0.75f * Gdx.graphics.getHeight());
		ContentLoader.getInstance().gameFont.getData().setScale(1.0f);
		textSpriteBatch.end();

		menuStage.act(delta);
		menuStage.draw();
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
		Gdx.app.exit();
	}
}
