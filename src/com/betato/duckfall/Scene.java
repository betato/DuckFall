package com.betato.duckfall;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Scene {

	Game game;
	
	ArrayList<Entity> entities;
	BufferedImage background;
	
	public Scene(Game game) {
		this.game = game;
	}
	
	public abstract void enter();		
	public abstract void exit();
	public abstract void update();
	
	public void draw(Graphics g) {
		g.drawImage(background, 0, 0, null);
		for (Entity entity : entities) {
			entity.draw(g);
		}
	}
	
}
