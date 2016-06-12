package com.betato.gamedisplay;

import java.util.HashMap;

/**
 * The {@code KeyStates} class stores key states
 */
public class KeyStates {
	public static final int NUM_KEYS = 223;
	private static final int NUM_LOOPS = NUM_KEYS - 1;
	
	public boolean[] keyStates = new boolean[NUM_KEYS];
	public boolean[] keyPresses = new boolean[NUM_KEYS];
	public boolean[] keyReleases = new boolean[NUM_KEYS];
	private boolean[] lastState = new boolean[NUM_KEYS];
	
	// Contains typeable characters
	public HashMap<Integer, String> keyMap = new HashMap<Integer, String>();
	
	public KeyStates() {
		keyMap.put(9, "TAB");
		// Some symbols
		keyMap.put(44, ",");
		keyMap.put(45, "-");
		keyMap.put(46, ".");
		keyMap.put(47, "/");
		// Number Bar
		keyMap.put(48, "0");
		keyMap.put(49, "1");
		keyMap.put(50, "2");
		keyMap.put(51, "3");
		keyMap.put(52, "4");
		keyMap.put(53, "5");
		keyMap.put(54, "6");
		keyMap.put(55, "7");
		keyMap.put(56, "8");
		keyMap.put(57, "9");
		// Some symbols
		keyMap.put(59, ";");
		keyMap.put(61, "=");
		// Letter Keys
		keyMap.put(65, "A");
		keyMap.put(66, "B");
		keyMap.put(67, "C");
		keyMap.put(68, "D");
		keyMap.put(69, "E");
		keyMap.put(70, "F");
		keyMap.put(71, "G");
		keyMap.put(72, "H");
		keyMap.put(73, "I");
		keyMap.put(74, "J");
		keyMap.put(75, "K");
		keyMap.put(76, "L");
		keyMap.put(77, "M");
		keyMap.put(78, "N");
		keyMap.put(79, "O");
		keyMap.put(80, "P");
		keyMap.put(81, "Q");
		keyMap.put(82, "R");
		keyMap.put(83, "S");
		keyMap.put(84, "T");
		keyMap.put(85, "U");
		keyMap.put(86, "V");
		keyMap.put(87, "W");
		keyMap.put(88, "X");
		keyMap.put(89, "Y");
		keyMap.put(90, "Z");
		// Some symbols
		keyMap.put(91, "]");
		keyMap.put(92, "[");
		keyMap.put(93, "\\");
		// Number pad
		keyMap.put(96, "0");
		keyMap.put(97, "1");
		keyMap.put(98, "2");
		keyMap.put(99, "3");
		keyMap.put(100, "4");
		keyMap.put(101, "5");
		keyMap.put(102, "6");
		keyMap.put(103, "7");
		keyMap.put(104, "8");
		keyMap.put(105, "9");
		keyMap.put(106, "*");
		keyMap.put(107, "+");
		keyMap.put(109, "-");
		keyMap.put(110, ".");
		keyMap.put(111, "/");
		// Symbols
		keyMap.put(186, ";");
		keyMap.put(187, "=");
		keyMap.put(188, ",");
		keyMap.put(189, "-");
		keyMap.put(190, ".");
		keyMap.put(191, "/");
		keyMap.put(192, "`");
		keyMap.put(219, "[");
		keyMap.put(220, "\\");
		keyMap.put(221, "]"); 
		keyMap.put(222, "'");
	}
	
	// Updates presses and releases
	public void update() {
		// Check for all changed keys
		for (int i = 0; i <= NUM_LOOPS; i++) {
			if (keyStates[i] != lastState[i]) {
				// Key has been changed
				if (keyStates[i]) {
					// Key is down
					keyPresses[i] = true;
				} else {
					// Key is up
					keyReleases[i] = true;
				}
			} else {
				// Key not changed, reset states
				keyPresses[i] = false;
				keyReleases[i] = false;
			}
		}
		// Save key states
		System.arraycopy(keyStates, 0, lastState, 0, NUM_KEYS);
	}
	
	// Indexes of key types
	public static final int START_NUMROW = 48;
	public static final int END_NUMROW = 57;
	public static final int START_LETTERS = 65;
	public static final int END_LETTERS = 90;
	public static final int START_NUMPAD = 96;
	public static final int END_NUMPAD = 111;
	public static final int START_SYMBOLDS = 186;
	public static final int END_SYMBOLS = 222;
	
	public static final int COMMA = 44;
	public static final int DASH = 45;
	public static final int PERIOD = 46;
	public static final int SLASH = 47;
	public static final int SEMICOLON = 59;
	public static final int EQUALS = 61;
	public static final int BRACKET_OPEN = 91;
	public static final int BACKSLASH = 92;
	public static final int BRACKET_CLOSE = 93;
	
	// All other keys
	public static final int BACKSPACE = 8;
	public static final int TAB = 9;
	public static final int ENTER = 13;
	public static final int SHIFT = 16;
	public static final int CTRL = 17;
	public static final int ALT = 18;
	public static final int PAUSE_BREAK = 19;
	public static final int LOCK_CAPS = 20;
	public static final int ESCAPE = 27;
	public static final int SPACE = 32;
	public static final int PAGE_UP = 33;
	public static final int PAGE_DOWN = 34;
	public static final int END = 35;
	public static final int HOME = 36;
	public static final int ARROW_LEFT = 37;
	public static final int ARROW_UP = 38;
	public static final int ARROW_RIGHT = 39;
	public static final int ARROW_DOWN = 40;
	public static final int INSERT = 45;
	public static final int DELETE = 46;
	public static final int NUMROW_0 = 48;
	public static final int NUMROW_1 = 49;
	public static final int NUMROW_2 = 50;
	public static final int NUMROW_3 = 51;
	public static final int NUMROW_4 = 52;
	public static final int NUMROW_5 = 53;
	public static final int NUMROW_6 = 54;
	public static final int NUMROW_7 = 55;
	public static final int NUMROW_8 = 56;
	public static final int NUMROW_9 = 57;
	public static final int A = 65;
	public static final int B = 66;
	public static final int C = 67;
	public static final int D = 68;
	public static final int E = 69;
	public static final int F = 70;
	public static final int G = 71;
	public static final int H = 72;
	public static final int I = 73;
	public static final int J = 74;
	public static final int K = 75;
	public static final int L = 76;
	public static final int M = 77;
	public static final int N = 78;
	public static final int O = 79;
	public static final int P = 80;
	public static final int Q = 81;
	public static final int R = 82;
	public static final int S = 83;
	public static final int T = 84;
	public static final int U = 85;
	public static final int V = 86;
	public static final int W = 87;
	public static final int X = 88;
	public static final int Y = 89;
	public static final int Z = 90;
	public static final int WINDOWS_KEY_LEFT = 91;
	public static final int WINDOWS_KEY_RIGHT = 92;
	public static final int SELECT_KEY = 93;
	public static final int NUMPAD_0 = 96;
	public static final int NUMPAD_1 = 97;
	public static final int NUMPAD_2 = 98;
	public static final int NUMPAD_3 = 99;
	public static final int NUMPAD_4 = 100;
	public static final int NUMPAD_5 = 101;
	public static final int NUMPAD_6 = 102;
	public static final int NUMPAD_7 = 103;
	public static final int NUMPAD_8 = 104;
	public static final int NUMPAD_9 = 105;
	public static final int MULTIPLY = 106;
	public static final int ADD = 107;
	public static final int SUBTRACT = 109;
	public static final int DECIMAL_POINT = 110;
	public static final int DIVIDE = 111;
	public static final int F1 = 112;
	public static final int F2 = 113;
	public static final int F3 = 114;
	public static final int F4 = 115;
	public static final int F5 = 116;
	public static final int F6 = 117;
	public static final int F7 = 118;
	public static final int F8 = 119;
	public static final int F9 = 120;
	public static final int F10 = 121;
	public static final int F11 = 122;
	public static final int F12 = 123;
	public static final int LOCK_NUM = 144;
	public static final int LOCK_SCROLL = 145;
	public static final int FORWARD_SLASH = 191;
	public static final int GRAVE_ACCENT = 192;
	public static final int BRAKET_CLOSE = 221;
	public static final int SINGLE_QUOTE = 222;
	
	// Alternate keys
	public static final int SEMICOLON_A = 186;
	public static final int EQUALS_A = 187;
	public static final int COMMA_A = 188;
	public static final int DASH_A = 189;
	public static final int PERIOD_A = 190;
	public static final int BRACKET_OPEN_A = 219;
	public static final int BACKSLASH_A = 220;
}
