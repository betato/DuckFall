package com.betato.duckfall;

import java.awt.Graphics;

import com.betato.gamedisplay.KeyStates;
import com.betato.gamedisplay.MouseStates;

public class Duck extends Entity {
	
	public static final int MAX_DUCK_SPEED = 20;
	
	public Duck(Game game, int x, int y) {
		super(game, game.loader.getTexture("moneyduck.png"), 64, 64, x, y);
	}

	@Override
	public void update(KeyStates keys, MouseStates mouse) {
		super.update(keys, mouse);
		updateMovement(mouse.pos.x - x - halfWidth, mouse.pos.y - y - halfHeight);
	}
	
	private void updateMovement(int diffX, int diffY) {
		// Follow mouse with duck
		int deltaX;
		int deltaY;
				
		// Duck speed limits
		deltaX = Math.min(diffX, MAX_DUCK_SPEED);
		deltaX = Math.max(deltaX, -MAX_DUCK_SPEED);
		deltaY = Math.min(diffY, MAX_DUCK_SPEED);
		deltaY = Math.max(deltaY, -MAX_DUCK_SPEED);
				
		// Duck screen boundaries
		deltaX = Math.min(deltaX, game.screenWidth - x - halfWidth);
		deltaX = Math.max(deltaX, 0 - x - halfWidth);
		deltaY = Math.min(deltaY, game.screenHeight - y - halfHeight);
		deltaY = Math.max(deltaY, 0 - y - halfHeight);
				
		incrementPos(deltaX, deltaY);
	}
	
	@Override
	public void draw(Graphics g) {
		super.draw(g);
	}
}
