package com.robert.gdxtutorial.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.robert.gdxtutorial.controllers.BobWorldController;
import com.robert.gdxtutorial.models.World;
import com.robert.gdxtutorial.renderers.BobWorldRenderer;

/**
 * Created by Robert on 30/07/2016.
 */
public class BobWorld implements Screen, InputProcessor {

    private World world;
    private BobWorldRenderer renderer;
    private BobWorldController controller;

    private int width, height;

    @Override
    public void show() {

        Gdx.input.setCatchBackKey(true);

        world = new World();
        renderer = new BobWorldRenderer(world, false);
        controller = new BobWorldController(world);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        controller.update(delta);
        renderer.render();
    }

    @Override
    public void resize(int width, int height) {
        renderer.setSize(width, height);
        this.width = width;
        this.height = height;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.ESCAPE)
            ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
        if(keycode == Input.Keys.BACK)
            ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
        if(keycode == Input.Keys.LEFT)
            controller.leftPressed();
        if(keycode == Input.Keys.RIGHT)
            controller.rightPressed();
        if(keycode == Input.Keys.Z)
            controller.jumpPressed();
        if(keycode == Input.Keys.X)
            controller.firePressed();
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.LEFT)
            controller.leftReleased();
        if(keycode == Input.Keys.RIGHT)
            controller.rightReleased();
        if(keycode == Input.Keys.Z)
            controller.jumpReleased();
        if(keycode == Input.Keys.X)
            controller.fireReleased();
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(screenX < width / 2 && screenY > height / 2) {
            controller.leftPressed();
        }
        if(screenX > width / 2 && screenY > height / 2) {
            controller.rightPressed();
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(screenX < width / 2 && screenY > height / 2) {
            controller.leftReleased();
        }
        if(screenX > width / 2 && screenY > height / 2) {
            controller.rightReleased();
        }
        return true;
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
    public boolean scrolled(int amount)
    {
        return false;
    }
}

