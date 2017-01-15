package com.robert.gdxtutorial.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.robert.gdxtutorial.gdxtutorial;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = gdxtutorial.TITLE + " v" + gdxtutorial.VERSION;
		config.vSyncEnabled = true;
		config.width = 1280;
		config.height = 720;


		new LwjglApplication(new gdxtutorial(), config);
	}
}
