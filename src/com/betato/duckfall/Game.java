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
	private ArrayList<Entity> bags = new ArrayList<Entity>();
	Random bagRand = new Random();
	public static final int WINDOW_WIDTH = 720;
	public static final int WINDOW_HEIGHT = 540;
	private int screenHeight;
	private int screenWidth;
	public static final int MAX_DUCK_SPEED = 20;
	
	private BufferedImage background;
	
	private String[] bagImages = {"whitebag.png", "bluebag.png", "yellowbag.png"};
	
	TextureLoader loader;
	GameManager gameManager;
	
	public Game() {
		loader = new TextureLoader();
		background = loader.getTexture("background.png");
		init(60, 120, "Duck Thing", new Dimension(720, 540), false, false, false);
	}

	@Override
	public void onInit() {
		screenHeight = getContentSize().height;
		screenWidth = getContentSize().width;
		gameManager = new GameManager();
		duck = new Entity(loader.getTexture("moneyduck.png"), 64, 64);
	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onUpdate(KeyStates keys, MouseStates mouse, boolean resized) {
		gameManager.updatePanels(keys, mouse);
		switch (gameManager.gameState) {
		case 1:
			// Start game
			break;

		case 2:
			exit();
			break;
		}
		generateBags();
		updateBags();
		updateDuck(mouse.pos.x - duck.x, mouse.pos.y - duck.y);

		// Check collisions
		for (Entity bag : bags){   
			if (duck.isCollidingWith(bag)){
				System.out.println("eh");
			}
		}
	}

	private void updateDuck(int diffX, int diffY) {
		// Follow mouse with duck
		int deltaX;
		int deltaY;
		
		// Duck speed limits
		deltaX = Math.min(diffX, 20);
		deltaX = Math.max(deltaX, -20);
		deltaY = Math.min(diffY, 20);
		deltaY = Math.max(deltaY, -20);
		
		// Duck screen boundaries
		deltaX = Math.min(deltaX, screenWidth - duck.x - duck.halfWidth);
		deltaX = Math.max(deltaX, 0 - duck.x - duck.halfWidth);
		deltaY = Math.min(deltaY, screenHeight - duck.y - duck.halfHeight);
		deltaY = Math.max(deltaY, 0 - duck.y - duck.halfHeight);
		
		duck.incrementPos(deltaX, deltaY);
	}

	private void generateBags() {
		if (bagRand.nextInt(40) == 0) {
			int i = bagRand.nextInt(64) + 32;
			bags.add(new Entity(getRandomBagImage(), i, i, bagRand.nextInt(screenWidth),
					-i, randomDouble(-0.4, 0.4), randomDouble(0.6, 3)));
		}
	}

	private double randomDouble(double min, double max) {
		return min + (max - min) * bagRand.nextDouble();
	}

	private void updateBags() {
		for (Entity bag : bags){
			bag.stepPos();
		}
		// Remove bags that have left the screen
		for (int i = bags.size() - 1; i >= 0; i--){ // from 9 to 0
			if (bags.get(i).x < 0 ||
					bags.get(i).x - bags.get(i).halfWidth > screenWidth ||
					bags.get(i).y + bags.get(i).halfHeight < -64 ||
					bags.get(i).y - bags.get(i).halfHeight > screenHeight){
				bags.remove(i);
			}
		}
	}
	
	private BufferedImage getRandomBagImage() {
		return loader.getTexture(bagImages[bagRand.nextInt(bagImages.length)]);
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
		gameManager.drawPanels(g);
	}
}
