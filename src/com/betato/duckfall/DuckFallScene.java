package com.betato.duckfall;

import java.awt.Graphics;
import java.util.ArrayList;

import com.betato.gamedisplay.KeyStates;
import com.betato.gamedisplay.MouseStates;

public class DuckFallScene extends Scene {
	
	Duck duck;
	ArrayList<Bag> bags;
	
	int level = 1;
	int bagsThisLevel;
	int bagRate = 2;
	double score;
	double bagSpeed = 1;
	public static final int BAGS_FOR_LEVEL_ADVANCE = 60;
	
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
				// Do not check collisions if game is in menu mode
				if (game.gameState == 1 && bag.isCollidingWith(duck)) {
					System.out.println("QUACK");
				}
			}
		}
		// Update level and score
		if (bagsThisLevel >= BAGS_FOR_LEVEL_ADVANCE){
			bagsThisLevel = 0;
			// Don't advance level past 10
			if (level < 10){
				bagRate++;
				bagSpeed++;
				level++;
			}
		}
		System.out.println(bagRate);
		System.out.println(level);
		System.out.println(score);
		score += 0.002 * (level + 4);
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(background, -2, -2, null);
		// Draw duck only if game is running
		if (game.gameState == 1) {
			duck.draw(g);
		}
		for (Bag bag : bags) {
			bag.draw(g);
		}
	}
	
	private void startGame() {
		duck = new Duck(game, 64, 64);
		bags = new ArrayList<>();
	}
	
	private void generateBags() {
		if (game.rng.nextInt(100) < bagRate) {
			bagsThisLevel++;
			int i = game.rng.nextInt(64) + 32;
			bags.add(new Bag(game, i, i, game.rng.nextInt(game.screenWidth),
					-i, game.randomDouble(-0.4, 0.4), game.randomDouble(0.5 + bagSpeed / 2, 1 + bagSpeed)));
		}
	}


}
