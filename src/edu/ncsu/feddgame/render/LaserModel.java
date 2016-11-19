package edu.ncsu.feddgame.render;

import org.joml.Vector2d;

public class LaserModel extends Model {
	
	private static final float LASER_WIDTH = .2f; // Width of the laser rendered
	
	private static Vector2d originV = new Vector2d(10, 0);
	private float angle; // Angle in radians
	private float coords[] = new float[2];
	
	public Vector2d vect;
	public int xDir = 0, yDir = 0;
	
	/**
	 * New LaserModel given angle and length
	 * @param vertices
	 * @param tCoords
	 * @param indices
	 * @param x0
	 * @param y0
	 * @param x1
	 * @param y1
	 */
	LaserModel(float[] tCoords, int[] indices, float x0, float y0, float angle, float length) {
		super(getVertices(x0, y0, angle, length, LASER_WIDTH), tCoords, indices, 4, GameTexture.LASER.getPath());
		this.angle = angle;
		this.vect = new Vector2d(length * Math.cos(angle), length * Math.sin(angle));
		coords[0] = x0;
		coords[1] = y0;
		
		determineDirection();
	}
	
	/**
	 * New LaserModel given a vector
	 * @param tCoords
	 * @param indices
	 * @param x0
	 * @param y0
	 * @param vect
	 */
	LaserModel(float[] tCoords, int[] indices, float x0, float y0, Vector2d vect) {
		super(getVertices(x0, y0, (float)originV.angle(vect), (float)vect.length(), LASER_WIDTH), tCoords, indices, 4, GameTexture.LASER.getPath());
		this.angle = (float)originV.angle(vect);
		this.vect = vect;
		coords[0] = x0;
		coords[1] = y0;
		
		determineDirection();
	}
	
	/**
	 * Returns the coordinates for vertices of a rectangle extruded from the line passed of width w
	 * @param begX
	 * @param begY
	 * @param endX
	 * @param endY
	 * @param LASER_WIDTH
	 * @return
	 */
	private static float[] getVertices(float begX, float begY, float angle, float length, float width) {
		length += .05f;
		
		float endX = length * (float)Math.cos(angle) + begX;
		float endY = length * (float)Math.sin(angle) + begY;
		float dy = endY - begY;
		float dx = endX - begX;
		float xS = (width * dy / length) / 2;
		float yS = (width * dx / length) / 2;
		
		float[] vertices = new float[] {
				begX - xS, begY + yS, 0f,
				begX + xS, begY - yS, 0f,
				endX + xS, endY - yS, 0f,
				endX - xS, endY + yS, 0f,
		};
		
		return vertices;
	}
	
	/**
	 * @return float value of the angle of the laser line
	 */
	public float getAngle() {
		return this.angle;
	}
	
	/**
	 * Sets the initial angle of the laser
	 * @param a
	 */
	public void setAngle(float a){
		this.angle = a;
		this.vect = new Vector2d(.1f * Math.cos(a), .1f * Math.sin(a));
		determineDirection();
	}
	
	/**
	 * Sets a value of 1 for positive, -1 for negative for the vector directions of the laser
	 */
	private void determineDirection() {
		// Y
		if (Math.sin(angle) >= 0) {
			yDir = 1;
		} else {
			yDir = -1;
		}
		
		// X
		if (Math.cos(angle) >= 0) {
			xDir = 1;
		} else {
			xDir = -1;
		}
	}
	
	public void setLength(float length) {
		this.vertices = getVertices(coords[0], coords[1], angle, length, LASER_WIDTH);
	}
	
	public void setVector(Vector2d vect) {
		this.vect = vect;
		this.angle = (float)originV.angle(vect);
	}
	
	/**
	 * Returns the coordinates of the line from which the laser is generated
	 * @return
	 */
	public float[] getCoords() {
		return this.coords;
	}
	
	/**
	 * Sets the starting coordinates of the laser
	 * @param f
	 */
	public void setCoords(float[] f) {
		this.coords = f;
	}

}
