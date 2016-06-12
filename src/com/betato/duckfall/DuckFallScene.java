package com.betato.duckfall;

import java.awt.Graphics;
import java.util.ArrayList;

import com.betato.gamedisplay.KeyStates;
import com.betato.gamedisplay.MouseStates;

public class DuckFallScene extends Scene {
	
	Duck duck;
	ArrayList<Bag> bags;
	
	boolean running;
	int level;
	int bagsThisLevel;
	int bagRate;
	double score;
	double bagSpeed;
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
				if (running && bag.isCollidingWith(duck)) {
					// Stop game
					running = false;
					game.endGame();
					
					// Reset bag parameters, but not score
					bagsThisLevel = 0;
					bagRate = 2;
					bagSpeed = 1;
				}
			}
		}
		// Update level and score
		if (running){
			if (bagsThisLevel >= BAGS_FOR_LEVEL_ADVANCE){
				bagsThisLevel = 0;
				// Don't advance level past 10
				if (level < 10){
					bagRate++;
					bagSpeed++;
					level++;
				}
			}
			score += 0.002 * (level * 2 + 4);
		}
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(background, -2, -2, null);
		for (Bag bag : bags) {
			bag.draw(g);
		}
		// Draw duck and scores only if game is running
		if (running) {
			duck.draw(g);
			g.drawString("Score: " + String.valueOf((int)score), 600, 20);
			g.drawString("Level: " + String.valueOf(level), 600, 40);
		} else {
			g.drawString("Last Score: " + String.valueOf((int)score), 600, 20);
			g.drawString("Last Level: " + String.valueOf(level), 600, 40);
		}
	}
	
	private void startGame() {
		// Clear game
		level = 1;
		bagsThisLevel = 0;
		bagRate = 2;
		score = 0;
		bagSpeed = 1;
		
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
