package com.betato.duckfall;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.betato.gamedisplay.KeyStates;
import com.betato.gamedisplay.MouseStates;

public abstract class Scene {

	Game game;
	
	ArrayList<Entity> entities;
	BufferedImage background;
	
	public Scene(Game game) {
		this.game = game;
	}
	
	public abstract void enter();		
	public abstract void exit();
	public abstract void update(KeyStates keys, MouseStates mouse);
	public abstract void draw(Graphics g);
	
}
