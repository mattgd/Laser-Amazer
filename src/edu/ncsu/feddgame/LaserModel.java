package edu.ncsu.feddgame;

public class LaserModel extends Model{
	private float slope;
	private float coords[] = new float[4];
	private float w;
	
	public int xDir = 0, yDir = 0;
	
	
	/**
	 * New Laser Model
	 * @param vertices
	 * @param tCoords
	 * @param indices
	 * @param x0
	 * @param y0
	 * @param x1
	 * @param y1
	 */
	public LaserModel(float[] tCoords, int[] indices, float x0, float y0, float x1, float y1, float w) {
		super(getVertices(x0, y0, x1, y1, w), tCoords, indices);
		this.w = w;
		this.slope = (y1 - y0)/(x1 - x0);
		coords[0] = x0;
		coords[1] = y0;
		coords[2] = x1;
		coords[3] = y1;
		determineDirection();
	}
	
	/**
	 * Returns the coordinates for vertices of a rectangle extruded from the line passed of width w
	 * @param begX
	 * @param begY
	 * @param endX
	 * @param endY
	 * @param w
	 * @return
	 */
	public static float[] getVertices(float begX, float begY, float endX, float endY, float w){
		float dy = endY - begY;
		float dx = endX - begX;
		float length = (float)Math.sqrt(dx * dx + dy * dy);
		float xS = (w * dy / length) / 2;
		float yS = (w * dx / length) / 2;
		
		float[] vertices = new float[] {
				begX - xS, begY + yS, 0,
				begX + xS, begY - yS, 0,
				endX + xS, endY - yS, 0,
				endX - xS, endY + yS, 0,
			};
		
		return vertices;
	}
	
	/**
	 * Returns the slope of the laser line
	 * @return
	 */
	public float getSlope(){
		return this.slope;
	}
	/**
	 * Sets a value of 1 for positive, -1 for negative for the vector directions of the laser
	 */
	private void determineDirection(){
		if (coords[3] - coords[1] >= 0){
			yDir = 1;
		}else{
			yDir = -1;
		}
		if (coords[2] - coords[0] >= 0){
			xDir = 1;
		}else{
			xDir = -1;
		}
	}
	
	public void setEndPoint(float xCoord, float yCoord){
		this.vertices = getVertices(coords[0], coords[1], xCoord, yCoord, w);
	}
	
	/**
	 * Returns the coords of the line from which the laser is generated
	 * @return
	 */
	public float[] getCoords(){
		return this.coords;
	}

}
