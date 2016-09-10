package edu.ncsu.feddgame;

public class Level {
	
	private int number; // Level number which stores as a seed for the algorithm
	private int[][] levelMap;
	
	Level(int number) {
		this.number = number;
		//TODO Test values here, maybe 16x16 would be better?
		this.levelMap = new int[8][8]; // Save data for 8x8 grid not including the outside bounds
	}
	
	// Generate the level based on an algorithm
	int[] generateLevel() {
		for (int i = 0; i < levelMap.length; i++) {
			for (int j = 0; j < levelMap[0].length; j++) {
				
			}
		}
		
		return null;
	}
	
}
