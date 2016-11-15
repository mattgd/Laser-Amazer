package edu.ncsu.feddgame.level;

import java.util.Random;

import edu.ncsu.feddgame.render.LaserStart;
import edu.ncsu.feddgame.render.LaserStop;

public abstract class Level {

	protected LaserStart laserWrap;
	protected LaserStop laserStop;
	private boolean activeLevel = false;
	private String name;
	double timeStart;
	
	public Level(String name) {
		this.name = name;
	}
	
	public abstract void renderObjects();
	
	public int randomInt(int min, int max) {
		return new Random().nextInt(max - min + 1) + min;
	}

	public void logicLoop() {
		if (laserWrap != null)
			laserWrap.reflect();
	}

	public void renderLoop() {
		if (laserWrap != null)
			laserWrap.render();
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isActiveLevel() {
		return activeLevel;
	}
	
	public void setActiveLevel(boolean active) {
		activeLevel = active;
		timeStart = getTime();
	}
	
	public double getElapsedTime(){
		return getTime() - timeStart;
	}
	
	/**
	 * @return System time in seconds
	 */
	public double getTime() {
		return (double) System.nanoTime() / (double) 1000000000L;
	}
	
}
