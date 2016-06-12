package com.betato.duckfall;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.AbstractMap.SimpleEntry;

import com.betato.gamedisplay.InputPanel;
import com.betato.gamedisplay.KeyStates;
import com.betato.gamedisplay.MouseStates;

public class GameManager {
	
	private InputPanel startPanel;
	private InputPanel scoreboardPanel;
	private InputPanel scoreboardMenuPanel;
	private InputPanel submitScorePanel;
	private InputPanel aboutPanel;
	
	private Game game;
	private HighScoreServer highScoreServer;
	
	public GameManager(Game game) {
		highScoreServer = new HighScoreServer();
		//highScoreServer.submitScore("inuktishuk", 1234);
		
		startPanel = new InputPanel("The Game With the Duck", 600, null, new String[]{"Play", "Quit", "Highscores", "About"}, false,
				0, new Point(45, 200), false);
		
		scoreboardPanel = new InputPanel("Highscores", 600, null, new String[]{"1", "2", "3", "4", "5"},
				false, 0, new Point(45, 35), false);
		
		updateScoreboardPanel(0);
		
		startPanel.visible = true;
		scoreboardPanel.visible = true;
		// Game reference to set gameState and close game
		this.game = game;
	}
	
	public static final int MAX_NAME_SIZE = 32;
	
	private void updateScoreboardPanel(int offset) {
		ArrayList<SimpleEntry<String, Integer>> scores = highScoreServer.getScores(offset + 5);
		
		String[] formattedScores = new String[5];
		String[] rankings = new String[5];
		for (int i = 0; i < 5; i++) {
			if (i + offset < scores.size()) {
				// High score name
				String score = scores.get(i + offset).getKey().toString().trim();
				formattedScores[i] = score.substring(0, Math.min(MAX_NAME_SIZE, score.length()));
				// Spacing
				for (int j = 0; j < MAX_NAME_SIZE - score.length(); j++) {
					formattedScores[i] += " ";
				}
				// High score value
				score = scores.get(i + offset).getValue().toString().trim();
				formattedScores[i] += score.substring(0, Math.min(MAX_NAME_SIZE, score.length()));
			} else {
				// No high score, fill in the space
				formattedScores[i] = " ";
			}
			// Ranking list
			rankings[i] = Integer.toString(i + offset + 1);
		}
		scoreboardPanel.reformatBox("Highscores", rankings, null);
		scoreboardPanel.text = formattedScores;
	}

	
	public void updatePanels(KeyStates keys, MouseStates mouse) {
		startPanel.update(keys, mouse);
		//scoreboardMenuPanel.update(keys, mouse);
		//submitScorePanel.update(keys, mouse);
		//aboutPanel.update(keys, mouse);
		
		switch (startPanel.selectedButton) {
		case 0:
			// Play
			startPanel.visible = false;
			game.gameState = 1;
			game.setCursorInvisible(true);
			break;
			
		case 1:
			// Quit
			game.exit();
			break;

		case 2:
			// High scores
			break;

		case 3:
			// About
			break;
		}
	}
	
	public void drawPanels(Graphics g) {
		startPanel.drawPanel(g);
		scoreboardPanel.drawPanel(g);
		//scoreboardMenuPanel.drawPanel(g);
		//submitScorePanel.drawPanel(g);
		//aboutPanel.drawPanel(g);
	}
}
