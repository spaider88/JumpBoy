package com.spaidi.jumpboy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.spaidi.jumpboy.World;
import com.spaidi.jumpboy.contollers.WorldController;
import com.spaidi.jumpboy.view.WorldRenderer;

public class GameScreen implements Screen, InputProcessor {

	private World world;
	private WorldRenderer renderer;
	private WorldController controller;

	private int width, height;

	@Override
	public void show() {
		world = new World();
		renderer = new WorldRenderer(world, false);
		controller = new WorldController(world);
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		controller.update(delta);
		world.update(delta);
		renderer.render();
	}

	@Override
	public void resize(int width, int height) {
		renderer.setSize(width, height);
		this.width = width;
		this.height = height;
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
	}

	@Override
	public void dispose() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.LEFT) controller.leftPressed();
		if (keycode == Keys.RIGHT) controller.rightPressed();
		if (keycode == Keys.UP) controller.jumpPressed();
		if (keycode == Keys.CONTROL_LEFT) controller.firePressed();
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.LEFT) controller.leftReleased();
		if (keycode == Keys.RIGHT) controller.rightReleased();
		if (keycode == Keys.UP) controller.jumpReleased();
		if (keycode == Keys.CONTROL_LEFT) controller.fireReleased();
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		if (x < width / 2 && y > height / 2) {
			controller.leftPressed();
		}
		if (x > width / 2 && y > height / 2) {
			controller.rightPressed();
		}
		return true;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		if (x < width / 2 && y > height / 2) {
			controller.leftReleased();
		}
		if (x > width / 2 && y > height / 2) {
			controller.rightReleased();
		}
		return true;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
