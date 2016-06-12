package com.betato.duckfall;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import com.betato.gamedisplay.GameWindow;
import com.betato.gamedisplay.KeyStates;
import com.betato.gamedisplay.MouseStates;

public class Game extends GameWindow {

	Random rng = new Random();
	public static final int WINDOW_WIDTH = 720;
	public static final int WINDOW_HEIGHT = 540;
	public int screenHeight;
	public int screenWidth;
	
	TextureLoader loader;
	HighScoreServer highScoreServer;
	
	DuckFallScene scene;
	
	public Game() {
		loader = new TextureLoader();
		highScoreServer = new HighScoreServer();
		scene = new DuckFallScene(this);
		scene.enter();
		init(60, 120, "Duck Thing", new Dimension(720, 540), false, false, true);
	}

	@Override
	public void onInit() {
		screenHeight = getContentSize().height;
		screenWidth = getContentSize().width;	
	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onUpdate(KeyStates keys, MouseStates mouse, boolean resized) {
		scene.update(keys, mouse);
	}

	public double randomDouble(double min, double max) {
		return min + (max - min) * rng.nextDouble();
	}

	@Override
	public void onRender(Graphics g) {
		// Clear background
		g.setColor(Color.white);
		g.fillRect(0, 0, screenWidth, screenHeight);
		scene.draw(g);
		g.drawString("FPS: " + fps, 10, 20);
		g.drawString("UPS: " + ups, 10, 35);
	}
}
