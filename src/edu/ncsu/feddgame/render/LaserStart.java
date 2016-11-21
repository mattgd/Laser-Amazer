package edu.ncsu.feddgame.render;

import edu.ncsu.feddgame.LaserWrapper;

public class LaserStart extends Model {
	
	private LaserWrapper laser;
	
	LaserStart(float[] vertices, float[] tCoords, int[] indices, float xOffset, float yOffset, LaserWrapper laser) {
		super(vertices, tCoords, indices, xOffset, yOffset, 4, GameTexture.START.getPath());
		this.laser = laser;
	}
	
	@Override
	public void render() {
		super.render();
		laser.render();
	}
	
	public void reflect() {
		laser.runReflections();
	}
	
	public void setLaser(LaserWrapper l) {
		this.laser = l;
	}
	
	@Override
	public void rotate(float angle) {
		super.rotate(angle);
		
		if (laser != null) 
			laser.rotateStart(angle, super.xOffset, super.yOffset);
	}

}