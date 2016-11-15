package edu.ncsu.feddgame.render;

import edu.ncsu.feddgame.GameInstance;
import edu.ncsu.feddgame.gui.IClickable;
import edu.ncsu.feddgame.gui.UIUtils;

public class MovableModel extends Model implements IClickable{
	public float[] xCoords; 	//Top Left, Top Right, Bottom Right, Bottom Left
	public float[] yCoords;
	
	private Runnable callback = null;
	public MovableModel(float[] vertices, float[] tCoords, int[] indices, float xOffset, float yOffset, int sides) {
		super(vertices, tCoords, indices, sides, "box.png");
		adjustOffset(xOffset, yOffset);
	}
	public MovableModel(float[] vertices, float[] tCoords, int[] indices, float xOffset, float yOffset, int sides, String tex){
		super(vertices, tCoords, indices, sides, tex);
		adjustOffset(xOffset, yOffset);
	}

	@Override
	public void setCallback(Runnable r) {
		this.callback = r;
		
	}
	
	private void adjustOffset(float xOffset,float yOffset){
		float[] coords = new float[]{
			super.vertices[0] + xOffset - super.xOffset, super.vertices[1] + yOffset - super.yOffset, 0, // TOP LEFT - 0
			super.vertices[3] + xOffset - super.xOffset, super.vertices[4] + yOffset - super.yOffset, 0, // TOP RIGHT - 1
			super.vertices[6] + xOffset - super.xOffset, super.vertices[7] + yOffset - super.yOffset, 0, // BOTTOM RIGHT - 2
			super.vertices[9] + xOffset - super.xOffset, super.vertices[10] + yOffset - super.yOffset, 0, // BOTTOM LEFT - 3
		};
		splitCoords(coords);
		super.setVertices(coords);
		super.xOffset = xOffset;
		super.yOffset = yOffset;
		//System.out.println(xOffset + ", "  + yOffset);
	}
	private void splitCoords(float[] coords){
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
	public boolean checkClick(float xPos, float yPos){
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
		if (xPos > 10)
			xPos = 10;
		if (xPos < -10)
			xPos = -10;
		if (yPos > 10)
			yPos = 10;
		if (yPos < -10)
			yPos = -10;
		adjustOffset(xPos * GameInstance.window.ratio, yPos);
	}

}
