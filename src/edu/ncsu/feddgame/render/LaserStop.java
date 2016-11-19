package edu.ncsu.feddgame.render;

import edu.ncsu.feddgame.GameInstance;
import edu.ncsu.feddgame.State;


public class LaserStop extends Model {

	LaserStop(float[] vertices, float[] tCoords, int[] indices, float xOffset, float yOffset) {
		super(vertices, tCoords, indices, xOffset, yOffset, 4, GameTexture.FINISH.getPath());
	}
	
	/**
	 * Called by ReflectionCalculation once a laser has its destination set to this object
	 */
	public void laserIntersection() {
		GameInstance.setState(State.LEVEL_COMPLETE);
	}

}
