package edu.ncsu.feddgame.render;

import edu.ncsu.feddgame.LaserWrapper;

public class LaserStart extends Model{
	private LaserWrapper laser;
	public LaserStart(float[] vertices, float[] tCoords, int[] indices, float xOffset, float yOffset, LaserWrapper laser) {
		super(vertices, tCoords, indices, xOffset, yOffset, 4, "start.png");
		this.laser = laser;
	}
	
	@Override
	public void render(){
		super.render();
		laser.render();
	}
	
	public void reflect(){
		laser.runReflections();
	}

}
