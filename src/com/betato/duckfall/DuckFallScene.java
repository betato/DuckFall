package com.betato.duckfall;

import java.awt.Graphics;
import java.util.ArrayList;

import com.betato.gamedisplay.KeyStates;
import com.betato.gamedisplay.MouseStates;

public class DuckFallScene extends Scene {
	
	Duck duck;
	ArrayList<Bag> bags;

	public DuckFallScene(Game game) {
		super(game);
		background = game.loader.getTexture("background.png");
	}
	
	@Override
	public void enter() {
		startGame();
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(KeyStates keys, MouseStates mouse) {
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

	@Override
	public void draw(Graphics g) {
		g.drawImage(background, -2, -2, null);
		duck.draw(g);
		for (Bag bag : bags) {
			bag.draw(g);
		}
		
	}
	
	private void startGame() {
		duck = new Duck(game, 64, 64);
		bags = new ArrayList<>();
	}
	
	private void generateBags() {
		if (game.rng.nextInt(40) == 0) {
			int i = game.rng.nextInt(64) + 32;
			bags.add(new Bag(game, i, i, game.rng.nextInt(game.screenWidth),
					-i, game.randomDouble(-0.4, 0.4), game.randomDouble(0.6, 3)));
		}
	}


}
