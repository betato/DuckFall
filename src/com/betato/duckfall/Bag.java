package com.betato.duckfall;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.betato.gamedisplay.KeyStates;
import com.betato.gamedisplay.MouseStates;

public class Bag extends Entity {

	private static String[] bagImages = {"whitebag.png", "bluebag.png", "yellowbag.png"};
	
	public Bag(Game game, int width, int height, int x, int y, double xVelocity, double yVelocity) {
		super(game, getRandomBagImage(), width, height, x, y, xVelocity, yVelocity);	
	}

	@Override
	public void update(KeyStates keys, MouseStates mouse) {
		super.update(keys, mouse);
		stepPos();
	}
	
	@Override
	public void draw(Graphics g) {
		super.draw(g);
	}
	
	public boolean isOutOfBounds() {
		return (x < 0 ||
				x - halfWidth > game.screenWidth ||
				y + halfHeight < -64 ||
				y - halfHeight > game.screenHeight);
	}
	
	private static BufferedImage getRandomBagImage() {
		return game.loader.getTexture(bagImages[game.rng.nextInt(bagImages.length)]);
	}
}
