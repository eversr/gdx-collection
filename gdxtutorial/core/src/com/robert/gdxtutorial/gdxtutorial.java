package com.robert.gdxtutorial;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.robert.gdxtutorial.screens.Levels;
import com.robert.gdxtutorial.screens.MainMenu;
import com.robert.gdxtutorial.screens.Splash;

public class gdxtutorial extends Game{

	public static final String TITLE = "Experiments and Tutorials", VERSION = "0.0.0.0.reallyEarly";

	@Override
	public void create() {
		// logs with parameters TAG and TEXT (used in LogCat)
		Gdx.app.log(TITLE, "create()");
		setScreen(new MainMenu());
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}
