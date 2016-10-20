package edu.ncsu.feddgame.render;

import edu.ncsu.feddgame.gui.IClickable;
import edu.ncsu.feddgame.gui.UIUtils;

public class MovableModel extends Model implements IClickable{
	public float[] xCoords; 	//Top Left, Top Right, Bottom Right, Bottom Left
	public float[] yCoords;
	
	private float[] vertices;
	private float[] coords;
	private Runnable callback = null;
	public MovableModel(float[] vertices, float[] tCoords, int[] indices, float xOffset, float yOffset) {
		super(vertices, tCoords, indices, 4, "bound.png");
		coords = vertices;
		this.vertices = coords;
		adjustOffset(xOffset, yOffset);
	}
	public MovableModel(float[] vertices, float[] tCoords, int[] indices, float xOffset, float yOffset, String tex){
		super(vertices, tCoords, indices, 4, tex);
		coords = vertices;
		this.vertices = coords;
		adjustOffset(xOffset, yOffset);
	}

	@Override
	public void setCallback(Runnable r) {
		this.callback = r;
		
	}
	
	private void adjustOffset(float xOffset,float yOffset){
		coords = new float[]{
			vertices[0] + xOffset, vertices[1] + yOffset, 0, // TOP LEFT - 0
			vertices[3] + xOffset, vertices[4] + yOffset, 0, // TOP RIGHT - 1
			vertices[6] + xOffset, vertices[7] + yOffset, 0, // BOTTOM RIGHT - 2
			vertices[9] + xOffset, vertices[10] + yOffset, 0, // BOTTOM LEFT - 3
		};
		splitCoords();
		super.setVertices(coords);
		//System.out.println(xOffset + ", "  + yOffset);
	}
	private void splitCoords(){
		this.xCoords = new float[]{coords[0], coords[3], coords[6], coords[9]};
		this.yCoords = new float[]{coords[1], coords[4], coords[7], coords[10]};
	}

	@Override
	/**
	 * Returns whether the button was clicked based on mouse coordinates passed
	 * @param xPos
	 * @param yPos
	 * @return
	 */
	public Boolean checkClick(float xPos, float yPos){
		if (UIUtils.pnpoly(xCoords, yCoords, xPos, yPos)){
			if (callback != null)
				callback.run();
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Translates the model to the coordinates provided
	 * @param xPos
	 * @param yPos
	 */
	public void followCursor(float xPos, float yPos){
		adjustOffset(xPos, yPos);
	}

}
