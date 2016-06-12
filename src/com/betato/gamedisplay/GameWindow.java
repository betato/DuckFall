package com.betato.gamedisplay;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class GameWindow extends GameLoop {

	// Window
	private JFrame window;
	private JPanel display;
	private Dimension lastSize;

	// GameWindow status
	private KeyStates keys = new KeyStates();
	private MouseStates mouse = new MouseStates();
	private boolean resized;
	int fps, ups = 0;
	
	public void init(final int fps, final int ups, String title, Dimension size, boolean resizable, boolean fullscreen, boolean hideCursor) {
		set(fps, ups);
		// Add components and set up window
		window = new JFrame(title);
		display = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				// Call renderer when repainted by gameloop
				onRender(g, fps, ups);
			}
		};
		window.add(display);
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		lastSize = size;
		
		// Set specified parameters
		if (fullscreen) {
			window.setUndecorated(fullscreen);
			window.setExtendedState(window.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		} else {
			window.setSize(size);
		}
		
		if (hideCursor) { 			
			// Set cursor only if needed 			
			setCursorVisibility(hideCursor); 			
		} 
		
		setResizable(resizable);
		initListeners();
		
		// Show window and run gameloop
		window.setVisible(true);
		run();
	}
	
	public void setCursorVisibility(boolean hideCursor){ 			
		if (hideCursor) { 			
			// Init transparent cursor image. 			
			BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB); 			
			// Create a new blank cursor. 			
			Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor( 			
					cursorImg, new Point(0, 0), "blank cursor"); 			
			// Set the cursor
			window.getContentPane().setCursor(blankCursor);
		} else { 			
			// Set default cursor 			
			window.getContentPane().setCursor(Cursor.getDefaultCursor()); 			
		} 			
	} 
	 
	public void setFullscreen(boolean fullscreen) {
		// Dispose window, as decorating window cannot be done while visible
		window.dispose();
		window.setUndecorated(fullscreen);
		if (fullscreen) {
			// Set window fullscreen
			lastSize = getFrameSize().getSize();
			window.setExtendedState(window.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		} else {
			// Set window normal
			window.setSize(lastSize);
		}
		// Show window again
		window.setVisible(true);
	}

	// Set the frame resizable
	public void setResizable(boolean resizable) {
		window.setResizable(resizable);
	}
	
	// Set the frame size
	public void setFrameSize(Dimension size) {
		window.setSize(size);
	}
	
	// Set the content size, then pack the frame
	public void setContentSize(Dimension size) {
		window.getContentPane().setPreferredSize(size);
		window.pack();
	}

	// Get inner window size
	public Rectangle getContentSize() {
		return display.getBounds();
	}

	// Get outer window size
	public Rectangle getFrameSize() {
		return window.getBounds();
	}

	// Exit GameWindow
	public void exit() {
		// Halt GameLoop
		stop();
		// Call the exit stuff
		onExit();
		// Shutdown everything
		System.exit(0);
	}
	
	// Initialize all listeners
	private void initListeners() {
		// All listeners are used to keep track of key and mouse states
		window.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				mouse.wheel += e.getWheelRotation();
			}
		});

		window.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) { }

			@Override
			public void mouseEntered(MouseEvent e) { }

			@Override
			public void mouseExited(MouseEvent e) { }

			@Override
			public void mousePressed(MouseEvent e) {
				mouse.buttonStates[e.getButton()] = true;
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				mouse.buttonStates[e.getButton()] = false;
			}
		});

		window.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				keys.keyStates[e.getKeyCode()] = true;
			}

			@Override
			public void keyReleased(KeyEvent e) {
				keys.keyStates[e.getKeyCode()] = false;
			}

			@Override
			public void keyTyped(KeyEvent e) { }
		});

		window.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				resized = true;
			}
		});

		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				exit();
			}
		});
	}

	// Called by GameLoop once after starting
	@Override
	public void init() {
		// Call GameWindow init
		onInit();
	}

	// Called by GameLoop n times every second
	@Override
	public void update() {
		// Get mouse position
		Point windowPos = MouseInfo.getPointerInfo().getLocation();
		mouse.pos = new Point((int) (windowPos.x - display.getLocationOnScreen().getX()),
				(int) (windowPos.y - display.getLocationOnScreen().getY()));
		// Call update
		onUpdate(keys, mouse, resized);
		resized = false;
		// Update key and mouse events
		keys.update();
		mouse.update();
	}
  
	// Called by GameLoop n times every second
	@Override
	public void render() {
		display.repaint();
	}

	// Called by GameLoop to display fps and ups every second
	@Override
	public void displayFps(int fps, int ups) {
		// Store fps to be passed in onRender method
		this.fps = fps;
		this.ups = ups;
	}

	// Invoked on initialization
	abstract public void onInit();

	// Invoked on update
	abstract public void onUpdate(KeyStates keys, MouseStates mouse, boolean resized);

	// Invoked on render
	abstract public void onRender(Graphics g, int fps, int ups);

	// Invoked on GameWindow exit
	abstract public void onExit();
}