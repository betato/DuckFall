package com.betato.gamedisplay;

import java.awt.Point;

/**
 * The {@code MouseStates} class stores a cursor position and button states
 */
public class MouseStates {
	public static final int NUM_BUTTONS = 4;
	private static final int NUM_LOOPS = NUM_BUTTONS - 1;

	public Point pos;
	public long wheel = 0;
	public boolean[] buttonStates = new boolean[NUM_BUTTONS];
	public boolean[] buttonPresses = new boolean[NUM_BUTTONS];
	public boolean[] buttonReleases = new boolean[NUM_BUTTONS];
	private boolean[] lastState = new boolean[NUM_BUTTONS];

	// Updates button presses and releases
	public void update() {
		for (int i = 0; i <= NUM_LOOPS; i++) {
			// Check for all changed keys
			if (buttonStates[i] != lastState[i]) {
				// Key has been changed
				if (buttonStates[i]) {
					// Key is down
					buttonPresses[i] = true;
				} else {
					// Key is up
					buttonReleases[i] = true;
				}
			} else {
				// Key not changed, reset states
				buttonPresses[i] = false;
				buttonReleases[i] = false;
			}
		}
		// Save button states
		System.arraycopy(buttonStates, 0, lastState, 0, NUM_BUTTONS);
	}

	// Mouse button constants
	public static final int BUTTON_LEFT = 1;
	public static final int BUTTON_MIDDLE = 2;
	public static final int BUTTON_RIGHT = 3;
}
