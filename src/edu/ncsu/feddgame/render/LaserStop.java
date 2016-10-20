package edu.ncsu.feddgame.render;

import edu.ncsu.feddgame.gui.UIElement;

public class LaserStop extends Model implements UIElement{

	public LaserStop(float[] vertices, float[] tCoords, int[] indices) {
		super(vertices, tCoords, indices, 4, "finish.png");
	}
	
	/**
	 * Called by ReflectionCalculation once a laser has its destination set to this object
	 */
	public void laserIntersection(){
		//System.out.println("End of Level");
	}

}
