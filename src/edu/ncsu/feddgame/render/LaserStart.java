package edu.ncsu.feddgame.render;

import edu.ncsu.feddgame.LaserWrapper;

public class LaserStart extends Model{
	private LaserWrapper laser;
	public LaserStart(float[] vertices, float[] tCoords, int[] indices, LaserWrapper laser) {
		super(vertices, tCoords, indices);
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
