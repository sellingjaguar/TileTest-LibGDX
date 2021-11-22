package com.tiletest.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tiletest.game.TileTest;

public class DesktopLauncher {
	public static void main (String[] arg) {

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "Tile test";

		config.width = 1200;
		config.height = 700;

		new LwjglApplication(new TileTest(), config);

	}
}
