package com.betato.gamedisplay;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
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

import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class GameWindow extends GameLoop {

	private JFrame window;
	private JPanel display;
	private Dimension lastSize;

	private KeyStates keys = new KeyStates();
	private MouseStates mouse = new MouseStates();
	private boolean resized;

	public void init(int fps, int ups, String title, Dimension size, boolean resizable, boolean fullscreen) {
		set(fps, ups);
		window = new JFrame(title);
		display = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				onRender(g);
			}
		};
		window.add(display);
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		lastSize = size;
		if (fullscreen) {
			window.setUndecorated(fullscreen);
			window.setExtendedState(window.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		} else {
			window.setSize(size);
		}
		setResizable(resizable);
		initListeners();
		window.setVisible(true);
		run();
	}

	public void setFullscreen(boolean fullscreen) {
		window.dispose();
		window.setUndecorated(fullscreen);
		if (fullscreen) {
			lastSize = getFrameSize().getSize();
			window.setExtendedState(window.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		} else {
			window.setSize(lastSize);
		}
		window.setVisible(true);
	}

	public void setResizable(boolean resizable) {
		window.setResizable(resizable);
	}

	public void setFrameSize(Dimension size) {
		window.setSize(size);
	}

	public void setContentSize(Dimension size) {
		window.getContentPane().setPreferredSize(size);
		window.pack();
	}

	public Rectangle getContentSize() {
		return display.getBounds();
	}

	public Rectangle getFrameSize() {
		return window.getBounds();
	}

	public void exit() {
		// Halt gameloop
		stop();
		// Call the exit stuff
		onExit();
		// Shutdown everything
		System.exit(0);
	}
	
	private void initListeners() {
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
				mouse.buttons[e.getButton()] = true;
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				mouse.buttons[e.getButton()] = false;
			}
		});

		window.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				keys.keystates[e.getKeyCode()] = true;
			}

			@Override
			public void keyReleased(KeyEvent e) {
				keys.keystates[e.getKeyCode()] = false;
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

	@Override
	public void init() {
		onInit();
	}

	@Override
	public void update() {
		// Get mouse position
		Point windowPos = MouseInfo.getPointerInfo().getLocation();
		mouse.pos = new Point((int) (windowPos.x - display.getLocationOnScreen().getX()),
				(int) (windowPos.y - display.getLocationOnScreen().getY()));
		onUpdate(keys, mouse, resized);
		resized = false;
	}

	@Override
	public void render() {
		display.repaint();
	}

	@Override
	public void displayFps(int fps, int ups) {
		System.out.println("Fps: " + fps);
		System.out.println("Ups: " + ups);
	}

	abstract public void onInit();

	abstract public void onUpdate(KeyStates keys, MouseStates mouse, boolean resized);

	abstract public void onRender(Graphics g);

	abstract public void onExit();
}
