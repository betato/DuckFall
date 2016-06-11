package com.betato.duckfall;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import com.betato.gamedisplay.GameWindow;
import com.betato.gamedisplay.KeyStates;
import com.betato.gamedisplay.MouseStates;

public class Game extends GameWindow {

	private Entity duck;
	private ArrayList<Entity> bags = new ArrayList<Entity>();
	Random bagRand = new Random();
	public static final int SCREEN_WIDTH = 720;
	public static final int SCREEN_HEIGHT = 540;
	
	public Game() {
		init(60, 120, "Duck Thing", new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT), false, false, true);
	}

	@Override
	public void onInit() {
		// Debug images
		BufferedImage texture = null;
		try {
			texture = ImageIO.read(new File("test.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		duck = new Entity(texture, 0, 0);
		bags.add(new Entity(texture, 0, 0));
		bags.get(0).setVelocity(1.1, 1.1);
	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onUpdate(KeyStates keys, MouseStates mouse, boolean resized) {
		// Follow mouse with duck
		updateBags();
		duck.setPos(mouse.pos);
		// Check collisions
		for (Entity bag : bags){   
			if (bag.isCollidingWith(duck)){
				System.out.println("eh");
			}
		}
	}

	private void updateBags() {
		for (Entity bag : bags){
			bag.stepPos();
		}
		// Remove bags that have left the screen
		for (int i = bags.size() - 1; i >= 0; i--){ // from 9 to 0
			if (bags.get(i).x < 0 ||
					bags.get(i).x - bags.get(i).halfWidth > SCREEN_WIDTH ||
					bags.get(i).y + bags.get(i).halfHeight < 0 ||
					bags.get(i).y - bags.get(i).halfHeight > SCREEN_HEIGHT){
				bags.remove(i);
			}
		}
	}

	@Override
	public void onRender(Graphics g) {
		// Clear background
		g.setColor(Color.white);
		g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		// Draw duck and bags
		duck.draw(g);
		for (Entity bag : bags){
			bag.draw(g);
		}
	}
}
