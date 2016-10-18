package edu.ncsu.feddgame.render;

public class LaserStop extends Model{

	public LaserStop(float[] vertices, float[] tCoords, int[] indices) {
		super(vertices, tCoords, indices, "finish.png");
	}
	
	/**
	 * Called by ReflectionCalculation once a laser has its destination set to this object
	 */
	public void laserIntersection(){
		//System.out.println("End of Level");
	}

}
