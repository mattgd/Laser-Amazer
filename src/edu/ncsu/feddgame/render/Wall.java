package edu.ncsu.feddgame.render;

public class Wall extends Model{

	public Wall(float[] vertices, float[] tCoords, int[] indices) {
		this(vertices, tCoords, indices, 4);
	}
	
	public Wall(float[] vertices, float[] tCoords, int[] indices, int sideNum) {
		super(vertices, tCoords, indices, sideNum, "bgtile.png");
	}

}
