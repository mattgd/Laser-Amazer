package edu.ncsu.feddgame.level;

public interface ILevel {
	/**
	 * Add all objects to the scene
	 */
	public void renderObjects();
	
	/**
	 * Run all logic that needs to be run, called 60 times a second
	 */
	public void logicLoop();

}
