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

	private Entity duck;
	private ArrayList<Bag> bags = new ArrayList<Bag>();
	Random rng = new Random();
	public static final int WINDOW_WIDTH = 720;
	public static final int WINDOW_HEIGHT = 540;
	public int screenHeight;
	public int screenWidth;
	private BufferedImage background;
	
	TextureLoader loader;
	HighScoreServer highScoreServer;
	
	public Game() {
		loader = new TextureLoader();
		highScoreServer = new HighScoreServer();
		background = loader.getTexture("background.png");
		init(60, 120, "Duck Thing", new Dimension(720, 540), false, false, true);
	}

	@Override
	public void onInit() {
		screenHeight = getContentSize().height;
		screenWidth = getContentSize().width;
		duck = new Duck(this, 64, 64);
	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onUpdate(KeyStates keys, MouseStates mouse, boolean resized) {
		generateBags();
		duck.update(keys, mouse);
		//we loop backwards here to handle removals
		for (int i = bags.size() - 1; i >= 0; i--){ // from 9 to 0
			Bag bag = bags.get(i);
			//remove any out of bounds bags
			if (bag.isOutOfBounds()) {
				bags.remove(i);
			}
			//otherwise, update and check for collisions
			else {
				bag.update(keys, mouse);
				if (bag.isCollidingWith(duck)) {
					System.out.println("QUACK");
				}
			}
		}	
	}

	private void generateBags() {
		if (rng.nextInt(40) == 0) {
			int i = rng.nextInt(64) + 32;
			bags.add(new Bag(this, i, i, rng.nextInt(screenWidth),
					-31, randomDouble(-0.4, 0.4), randomDouble(0.6, 3)));
		}
	}

	private double randomDouble(double min, double max) {
		return min + (max - min) * rng.nextDouble();
	}

	@Override
	public void onRender(Graphics g, int fps, int ups) {
		// Clear background
		g.setColor(Color.white);
		g.fillRect(0, 0, screenWidth, screenHeight);
		// Draw the background image. The position has to be at negative two, or else, there's a white border for some reason
		g.drawImage(background, -2, -2, null);
		// Draw duck and bags
		duck.draw(g);
		g.drawString("FPS: " + fps, 10, 20);
		g.drawString("UPS: " + ups, 10, 35);
		for (Entity bag : bags){
			bag.draw(g);
		}
	}
}
