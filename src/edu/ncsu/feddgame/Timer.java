package edu.ncsu.feddgame;

public class Timer {

	/**
	 * @return System time in seconds
	 */
	public static double getTime() {
		return (double) System.nanoTime() / (double) 1000000000L;
	}
	
}
