package com.betato.gamedisplay;

public abstract class GameLoop {
	private int targetFps = 60;
	private int nanoFps = 1000000000 / targetFps;
	private int targetUps = 60;
	private int nanoUps = 1000000000 / targetUps;
	private boolean running = true;
	
	public void set(int targetFps, int targetUps) {
		this.targetFps = targetFps;
		this.targetFps = targetUps;
		nanoFps = 1000000000 / targetFps;
		nanoUps = 1000000000 / targetUps;
	}
	
	public void stop() {
		running = false;
	}
	
	public void run() {
		long startTime = System.nanoTime();
		long deltaFps = 0;
		long deltaUps = 0;
		long deltaDisplay = 0;
		int framecount = 0;
		int updatecount = 0;
		
		init();
		
		while (running) {
			// Get current time
			long currentTime = System.nanoTime();
			// Get time since last loop
			deltaFps += currentTime - startTime;
			deltaUps += currentTime - startTime;
			deltaDisplay += currentTime - startTime;

			// Set start time of this loop for use in next cycle
			startTime = currentTime;

			// Render if target time has been reached
			if (deltaFps >= nanoFps) {
				// Render
				render();
				framecount++;
				deltaFps = 0;
			}

			// Update if target time has been reached
			if (deltaUps >= nanoUps) {
				// Update
				update();
				updatecount++;
				deltaUps = 0;
			}

			// Update fps display if one second has passed
			if (deltaDisplay >= 1000000000) {
				displayFps(framecount, updatecount);
				framecount = 0;
				updatecount = 0;
				deltaDisplay = 0;
			}
		}
	}

	abstract public void init();
	
	abstract public void update();

	abstract public void render();

	abstract public void displayFps(int fps, int ups);
}
