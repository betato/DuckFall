package com.betato.duckfall;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.betato.gamedisplay.GameWindow;
import com.betato.gamedisplay.KeyStates;
import com.betato.gamedisplay.MouseStates;

public class Game extends GameWindow {

	private Entity duck;
	private ArrayList<Entity> bags = new ArrayList<Entity>();
	TextureLoader loader;
	
	public Game() {
		loader = new TextureLoader();
		init(60, 120, "Duck Thing", new Dimension(720, 540), false, false, true);
	}

	@Override
	public void onInit() {
		duck = new Entity(loader.getTexture("moneyduck.png"), 64, 64);
		bags.add(new Entity(loader.getTexture("whiteduck.png"), 64, 64));
		bags.get(0).incrementPos(100, 150);
	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onUpdate(KeyStates keys, MouseStates mouse, boolean resized) {
		// Follow mouse with duck
		duck.setPos(mouse.pos);
		for (Entity bag : bags){   
			if (bag.isCollidingWith(duck)){
				System.out.println("eh");
			}
		}
	}

	@Override
	public void onRender(Graphics g) {
		// Clear background
		g.setColor(Color.white);
		g.fillRect(0, 0, 720, 540);
		// Draw duck and bags
		duck.draw(g);
		for (Entity bag : bags){
			bag.draw(g);
		}
	}
}
