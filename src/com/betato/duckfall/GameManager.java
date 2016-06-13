package com.betato.duckfall;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.AbstractMap.SimpleEntry;

import com.betato.gamedisplay.InputPanel;
import com.betato.gamedisplay.KeyStates;
import com.betato.gamedisplay.MouseStates;

public class GameManager {
	
	InputPanel startPanel;
	InputPanel scoreboardPanel;
	InputPanel scoreboardMenuPanel;
	InputPanel submitScorePanel;
	
	private Game game;
	private HighScoreServer highScoreServer;
	int submissionScore;
	int scoreboardOffset;
	
	public GameManager(Game game) {
		highScoreServer = new HighScoreServer();
		
		startPanel = new InputPanel(game.TITLE, 600, null, new String[]{"Play", "Quit", "Highscores"}, false,
				0, new Point(45, 200), false);
		
		scoreboardPanel = new InputPanel("Highscores", 600, null, new String[]{"1", "2", "3", "4", "5"},
				false, 0, new Point(45, 45), false);
		
		submitScorePanel = new InputPanel("Submit Score", 600, new String[]{"Nickname"}, new String[]{"OK", "Cancel"},
				false, 23, new Point(45, 200), false);
		
		scoreboardMenuPanel = new InputPanel(null, 600, null, new String[]{"<<", ">>", "Back"},
				false, 23, new Point(45, 395), false);
		
		updateScoreboardPanel(0);
		
		startPanel.visible = true;
		//scoreboardPanel.visible = true;
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
		if (startPanel.visible){
			startPanel.update(keys, mouse);
		}
		if (submitScorePanel.visible){
			submitScorePanel.update(keys, mouse);
		}
		if (scoreboardMenuPanel.visible){
			scoreboardMenuPanel.update(keys, mouse);
		}
		
		switch (startPanel.selectedButton) {
		case 0:
			// Play
			startPanel.visible = false;
			game.scene.running = true;
			game.scene.enter();
			game.setCursorInvisible(true);
			startPanel.selectedButton = -1;
			break;
			
		case 1:
			// Quit
			startPanel.selectedButton = -1;
			game.exit();
			break;

		case 2:
			// High scores
			updateScoreboardPanel(scoreboardOffset);
			startPanel.visible = false;
			scoreboardMenuPanel.visible = true;
			scoreboardPanel.visible = true;
			startPanel.selectedButton = -1;
			break;
		}
		
		switch (submitScorePanel.selectedButton) {
		case 0:
			// OK
			String name = submitScorePanel.text[0].trim();
			if (name.isEmpty()) {
				highScoreServer.submitScore("<UNNAMED>", submissionScore);
			} else {
				highScoreServer.submitScore(name, submissionScore);
			}
			submitScorePanel.visible = false;
			startPanel.visible = true;
			submitScorePanel.clearPanel();
			submitScorePanel.selectedButton = -1;
			break;
			
		case 1:
			// Cancel
			submitScorePanel.visible = false;
			startPanel.visible = true;
			submitScorePanel.selectedButton = -1;
			break;
		}
		
		switch (scoreboardMenuPanel.selectedButton) {
		case 0:
			// Left
			if (scoreboardOffset >= 1) {
				if (keys.keyStates[KeyStates.SHIFT]) {
					// Jump
					scoreboardOffset = 0;
				} else {
					// Increment
					scoreboardOffset -= 5;
				}
			}
			updateScoreboardPanel(scoreboardOffset);
			scoreboardMenuPanel.selectedButton = -1;
			break;
			
		case 1:
			// Right
			if (keys.keyStates[KeyStates.SHIFT]) {
				scoreboardOffset += 100;
			} else {
				// Increment
				scoreboardOffset += 5;
			}
			updateScoreboardPanel(scoreboardOffset);
			scoreboardMenuPanel.selectedButton = -1;
			break;
			
		case 2:
			// Back
			scoreboardMenuPanel.visible = false;
			scoreboardPanel.visible = false;
			startPanel.visible = true;
			scoreboardMenuPanel.selectedButton = -1;
			break;
		}
	}
	
	public void drawPanels(Graphics g) {
		startPanel.drawPanel(g);
		scoreboardPanel.drawPanel(g);
		scoreboardMenuPanel.drawPanel(g);
		submitScorePanel.drawPanel(g);
	}
}
