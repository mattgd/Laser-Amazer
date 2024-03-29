package edu.ncsu.feddgame.render;

import edu.ncsu.feddgame.GameInstance;
import edu.ncsu.feddgame.gui.IClickable;
import edu.ncsu.feddgame.gui.UIUtils;

public class MovableModel extends Model implements IClickable {
	
	public float[] xCoords; 	//Top Left, Top Right, Bottom Right, Bottom Left
	public float[] yCoords;
	
	private Runnable callback = null;
	public MovableModel(float[] vertices, float[] tCoords, int[] indices, float xOffset, float yOffset, int sides) {
		super(vertices, tCoords, indices, sides, "moveablebox.png");
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
	
	private void adjustOffset(float xOffset,float yOffset) {
		float[] coords = new float[3 * super.sideCount];
		for (int i = 0; i < this.sideCount; i++){
			coords[i * 3] = super.vertices[i * 3] + xOffset - super.xOffset;
		}
		for (int i = 0; i < this.sideCount; i++){
			coords[i * 3 + 1] = super.vertices[i * 3 + 1] + yOffset - super.yOffset;
		}
		for (int i = 0; i < this.sideCount; i++){
			coords[i * 3 + 2] = 0;
		}
		
		splitCoords(coords);
		super.setVertices(coords);
		super.xOffset = xOffset;
		super.yOffset = yOffset;
	}
	
	private void splitCoords(float[] coords){
		this.xCoords = new float[sideCount];
		this.yCoords = new float[sideCount];
		for (int i = 0; i < this.sideCount; i++){
			this.xCoords[i] = coords[i * 3];
		}
		for (int i = 0; i < this.sideCount; i++){
			this.yCoords[i] = coords[i * 3 + 1];
		}
	}

	@Override
	/**
	 * Returns whether the button was clicked based on mouse coordinates passed
	 * @param xPos
	 * @param yPos
	 * @return
	 */
	public boolean checkClick(float xPos, float yPos){
		splitCoords(super.vertices);
		if (UIUtils.pnpoly(xCoords, yCoords, xPos * GameInstance.window.ratio, yPos)){
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
