package com.betato.gamedisplay;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * The {@code InputPanel} class allows the display and input of text in movable
 * boxes
 */
public class InputPanel {

	// Layout constants
	private static final int ROW_HEIGHT = 30;
	private static final int OFFSET = 10;
	private static final int TEXT_ELEVATION = 8;
	private static final int TITLE_HEIGHT = 20;

	// Modifiers for everything
	public boolean unfocusOnClick; // Reset selected box if click outside panel
	public boolean numericOnly; // InputPanel accepts only numeric input
	public Point boxLocation; // On screen location of the panel
	private Point relativeLocation = new Point(0, 0); // Location of cursor
														// relative to panel
	public boolean visible; // Whether panel will be rendered or not

	// Main frame
	private int width; // Width without offsets
	private BufferedImage box; // Saved box background
	private Dimension frameDi; // Dimensions of box background

	// Boxes
	public String[] text; // Entered and displayed text in the boxes
	private Point[] boxesDi; // Box locations on the panel
	public int selectedBox = -1; // Box which text will be edited
	public int maxInputLength; // Maximum characters allowed in box

	// Buttons
	private Rectangle[] buttonsDi; // Button locations and sizes on the panel
	public int selectedButton = -1; // Which button has been clicked

	// Creates a new InputPanel with the specified parameters
	public InputPanel(String title, int width, String[] headings,
			String[] buttons, boolean numericOnly, int maxInputLength,
			Point position, boolean unfocusOnClick) {

		// Init other variables
		this.unfocusOnClick = unfocusOnClick;
		this.boxLocation = position;
		this.numericOnly = numericOnly;
		this.maxInputLength = maxInputLength;
		this.width = width;

		// Format box
		reformatBox(title, headings, buttons);
	}

	// Reformats the InputPanel to the specified parameters
	public void reformatBox(String title, String[] headings, String[] buttons) {
		// Assign box size parameters
		// Init arrays to zero length if null is specified
		// Buttons
		if (buttons == null) {
			// No buttons if buttons argument was null
			buttonsDi = new Rectangle[0];
		} else {
			buttonsDi = new Rectangle[buttons.length];
		}
		// Headings and boxes
		if (headings == null) {
			text = new String[0];
			boxesDi = new Point[0];
			headings = new String[0];
		} else {
			text = new String[headings.length];
			boxesDi = new Point[headings.length];
		}

		// Get bounds of all boxes
		boundControls(title, headings.length);

		// Save background box as bufferedImage
		drawBox(buttons, headings, title);
	}

	// Gets control locations and sizes from the specified parameters
	private void boundControls(String title, int headings) {
		// Temporary variables for box layout creation
		int buttonRows = 0;
		int boxesHeight = 0;
		int buttonsHeight = 0;
		int titleHeight = 0;

		// Set title height to zero if no title is specified
		if (title == null || title == "") {
			titleHeight = 0;
		} else {
			titleHeight = TITLE_HEIGHT;
		}

		// Create text box bounding boxes
		for (int i = 0; i < headings; i++) {
			boxesDi[i] = new Point(OFFSET, OFFSET + ROW_HEIGHT + titleHeight
					+ (i * ROW_HEIGHT * 2));
			// Also initialize all values of text array
			text[i] = "";
		}

		boxesHeight = headings * ROW_HEIGHT * 2;

		// Create button bounding boxes
		for (int i = 0; i < buttonsDi.length; i++) {
			if (i % 2 == 0) {
				// Even, create half button
				if (i >= buttonsDi.length - 1) {
					// Last Button, fill the entire row
					buttonsDi[i] = new Rectangle(OFFSET, (OFFSET * 2)
							+ boxesHeight + titleHeight
							+ ((ROW_HEIGHT + OFFSET) * buttonRows), width,
							ROW_HEIGHT);
					buttonRows++;
				} else {
					// Half button
					buttonsDi[i] = new Rectangle(OFFSET, (OFFSET * 2)
							+ boxesHeight + titleHeight
							+ ((ROW_HEIGHT + OFFSET) * buttonRows), (width / 2)
							- (OFFSET / 2), ROW_HEIGHT);
				}
			} else {
				// Odd, offset button
				buttonsDi[i] = new Rectangle((OFFSET * 2) + buttonsDi[0].width,
						(OFFSET * 2) + boxesHeight + titleHeight
								+ ((ROW_HEIGHT + OFFSET) * buttonRows),
						(width / 2) - (OFFSET / 2), ROW_HEIGHT);
				buttonRows++;
			}
		}

		buttonsHeight = (OFFSET + ROW_HEIGHT) * buttonRows;

		// Create frame bounding box
		frameDi = new Dimension(width + (OFFSET * 2), titleHeight
				+ buttonsHeight + boxesHeight + (OFFSET * 2));
	}

	// Draws the InputPanel and saves it in a BufferedImage for fast access and drawing
	private void drawBox(String[] buttonLabels, String[] headings, String title) {
		// Get graphics
		box = new BufferedImage(frameDi.width, frameDi.height,
				BufferedImage.TYPE_INT_ARGB);
		Graphics g = box.getGraphics();
		// Set font
		g.setFont(new Font("SansSerif", Font.BOLD, 18));

		// Outer box
		g.setColor(new Color(222, 184, 135, 128));
		g.fillRect(0, 0, frameDi.width, frameDi.height);

		// Draw buttons
		for (int i = 0; i < buttonsDi.length; i++) {
			// Box
			g.setColor(Color.gray);
			g.fillRect(0 + buttonsDi[i].x, buttonsDi[i].y, buttonsDi[i].width,
					buttonsDi[i].height);
			// Label
			g.setColor(Color.black);
			drawStringCentered(g, buttonLabels[i], buttonsDi[i]);
		}

		// Draw text
		g.setColor(Color.black);
		for (int i = 0; i < headings.length; i++) {
			g.drawString(headings[i], OFFSET, boxesDi[i].y - TEXT_ELEVATION);
		}

		// Draw title
		g.setFont(new Font("SansSerif", Font.BOLD, 24));
		if (title != null && title != "") {
			drawStringCentered(g, title, new Rectangle(OFFSET, OFFSET, width,
					TITLE_HEIGHT));
		}
	}

	// Draws a string centered in a rectangle
	public void drawStringCentered(Graphics g, String text, Rectangle rect) {
		// Get FontMetrics
		FontMetrics metrics = g.getFontMetrics();
		// Determine x coordinate
		int x = (rect.width - metrics.stringWidth(text)) / 2;
		// Determine y coordinate
		int y = ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
		// Draw the String
		g.drawString(text, x + rect.x, y + rect.y);
	}

	// Do not call update for non-editable boxes
	public void update(KeyStates keys, MouseStates mouse) {
		// zero mouse to TextBox position
		relativeLocation.x = mouse.pos.x - boxLocation.x;
		relativeLocation.y = mouse.pos.y - boxLocation.y;

		// get input and selection if click occurs
		if (mouse.buttonReleases[MouseStates.BUTTON_LEFT]) {
			getSelection(relativeLocation);
		}
		// Only if box is selected
		if (selectedBox >= 0) {
			getInput(keys);
		}
	}

	// Do not call update for non-editable boxes
	public void updateSelection(MouseStates mouse) {
		// zero mouse to TextBox position
		relativeLocation.x = mouse.pos.x - boxLocation.x;
		relativeLocation.y = mouse.pos.y - boxLocation.y;

		// get input and selection if click occurs
		if (mouse.buttonReleases[MouseStates.BUTTON_LEFT]) {
			getSelection(relativeLocation);
		}
	}

	// Add keyboard input to selected box
	private void getInput(KeyStates keys) {
		// Only add text if under maximum
		if (text[selectedBox].length() <= maxInputLength) {
			// Always get numbers
			for (int j = KeyStates.START_NUMROW; j <= KeyStates.END_NUMROW; j++) {
				// If number key is pressed
				if (keys.keyReleases[j]) {
					text[selectedBox] += keys.keyMap.get(j);
				}
			}

			for (int j = KeyStates.START_NUMPAD; j <= KeyStates.END_NUMPAD; j++) {
				// If number key is pressed
				if (keys.keyReleases[j]) {
					text[selectedBox] += keys.keyMap.get(j);
				}
			}

			// Get alphabetical characters if enabled
			if (!numericOnly) {
				// Get space
				if (keys.keyReleases[KeyStates.SPACE]) {
					text[selectedBox] += " ";
				}
				// Get letters
				for (int j = KeyStates.START_LETTERS; j <= KeyStates.END_LETTERS; j++) {
					// If letter key is pressed
					if (keys.keyReleases[j]) {
						text[selectedBox] += keys.keyMap.get(j);
					}
				}
			} else {
				// Get e for exponent if intOnly is true
				if (keys.keyReleases[KeyStates.E]) {
					if (!text[selectedBox].contains("E")
							&& text[selectedBox].length() > 0) {
						// Only one exponent allowed and only with a preceding
						// number
						text[selectedBox] += "E";
					}
				}
				// Get decimal if numericOnly is true
				if (keys.keyReleases[KeyStates.PERIOD]
						|| keys.keyReleases[KeyStates.DECIMAL_POINT]) {
					if (!text[selectedBox].contains(".")
							&& text[selectedBox].length() > 0) {
						// Only one decimal allowed and only with a preceding
						// number
						text[selectedBox] += ".";
					}
				}
				// Get negative if numericOnly is true
				if (keys.keyReleases[KeyStates.SUBTRACT]
						|| keys.keyReleases[KeyStates.DASH]) {
					if (text[selectedBox].isEmpty()) {
						// Negative only allowed at start of text box
						text[selectedBox] += "-";
					}
				}
			}
		}
		// Backspace if pressed
		if (keys.keyReleases[KeyStates.BACKSPACE]) {
			if (keys.keyStates[KeyStates.CTRL]) {
				// Delete all
				text[selectedBox] = "";
			}
			if (text[selectedBox].length() > 0) {
				// Delete one line
				text[selectedBox] = text[selectedBox].substring(0,
						text[selectedBox].length() - 1);
			}
		}
	}

	// Get selected box from mouse position
	private void getSelection(Point pos) {
		// Do not check boxes if click is outside of box
		if (pos.x < 0 || pos.x > frameDi.width || pos.y < 0
				|| pos.y > frameDi.height) {
			if (unfocusOnClick) {
				selectedBox = -1;
			}
			// Skip checking the other boxes if the cursor is outside the
			// boxes
			return;
		}
		if (pos.x > OFFSET && pos.x < width + OFFSET) {
			for (int i = 0; i < boxesDi.length; i++) {
				if (pos.y > boxesDi[i].y && pos.y < boxesDi[i].y + ROW_HEIGHT) {
					// If mouse is in box
					selectedBox = i;
				}
			}
		}
		for (int i = 0; i < buttonsDi.length; i++) {
			if (pos.y > buttonsDi[i].y
					&& pos.y < buttonsDi[i].y + buttonsDi[i].height
					&& pos.x > buttonsDi[i].x
					&& pos.x < buttonsDi[i].x + buttonsDi[i].width) {
				// If mouse is in box
				selectedButton = i;
				break;
			}
		}
	}

	// Clears all panel parameters
	public void clearPanel() {
		Arrays.fill(text, "");
		selectedBox = -1;
		selectedButton = -1;
	}

	// Parses and formats InputPanel text as double
	public double getDouble(int boxIndex) {
		try {
			return Double.parseDouble(text[boxIndex]);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	// Draws the panel with the specified graphics
	public void drawPanel(Graphics g) {
		// Draw only if visible
		if (visible) {
			// Draw constant frame
			g.drawImage(box, boxLocation.x, boxLocation.y, null);
			g.setFont(new Font("Monospaced", Font.BOLD, 18));

			// Draw boxes
			for (int i = 0; i < boxesDi.length; i++) {
				if (i == selectedBox) {
					g.setColor(Color.yellow);
				} else {
					g.setColor(Color.white);
				}
				g.fillRect(boxLocation.x + OFFSET,
						boxLocation.y + boxesDi[i].y, width, ROW_HEIGHT);
			}

			// Draw text
			g.setColor(Color.black);
			for (int i = 0; i < boxesDi.length; i++) {
				g.drawString(text[i], boxLocation.x + (OFFSET * 2),
						boxLocation.y + ROW_HEIGHT + boxesDi[i].y
								- TEXT_ELEVATION);
			}
		}
	}
}