package edu.ncsu.feddgame.level;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import edu.ncsu.feddgame.GameInstance;
import edu.ncsu.feddgame.gui.Button;
import edu.ncsu.feddgame.gui.CreateUI;
import edu.ncsu.feddgame.render.GameColor;
import edu.ncsu.feddgame.render.GameFont;
import edu.ncsu.feddgame.render.LaserStart;
import edu.ncsu.feddgame.render.LaserStop;

public abstract class Level {

	protected ArrayList<LaserStart> laserWrappers = new ArrayList<LaserStart>();
	protected LaserStop laserStop;
	private boolean activeLevel = false;
	private String name;
	private double timeStart;
	
	public Level(String name) {
		this.name = name;
	}
	
	public abstract void renderObjects();
	
	public int randomInt(int min, int max) {
		return new Random().nextInt(max - min + 1) + min;
	}

	public void logicLoop() {
		if (laserWrappers != null) {
			for (Iterator<LaserStart> it = laserWrappers.iterator(); it.hasNext();) {
				it.next().reflect();
			}
			
			// Causes ConcurrentModificationException
			/*for (LaserStart l : laserWrappers){
				l.reflect();
			}*/
		}
	}

	public void renderLoop() {
		if (laserWrappers != null) {
			for (LaserStart l : laserWrappers)
				l.render();
		}
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
