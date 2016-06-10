package com.betato.duckfall;

import java.awt.Dimension;
import java.awt.Graphics;

import com.betato.gamedisplay.GameWindow;
import com.betato.gamedisplay.KeyStates;
import com.betato.gamedisplay.MouseStates;

public class Game extends GameWindow {

	public Game() {
		init(60, 120, "Duck Thing", new Dimension(720, 540), false, false, true);
	}

	@Override
	public void onInit() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onUpdate(KeyStates keys, MouseStates mouse, boolean resized) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onRender(Graphics g) {
		// TODO Auto-generated method stub
	}
}
