package com.betato.gamedisplay;

import java.awt.Point;

/**
 * The {@code MouseStates} class stores a cursor position and button states
 */
public class MouseStates {
	public Point pos;
	public boolean[] buttons = new boolean[3];
	public long wheel = 0;

	public static final int BUTTON_1 = 1;
	public static final int BUTTON_2 = 2;
	public static final int BUTTON_3 = 3;
}
