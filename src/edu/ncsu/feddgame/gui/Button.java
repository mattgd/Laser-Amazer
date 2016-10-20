package edu.ncsu.feddgame.gui;

import edu.ncsu.feddgame.render.Model;

public class Button extends Model implements UIElement, IClickable{
	
	Runnable callback;
	float [] xCoords, yCoords;
	/**
	 * New Button extension from Model with Runnable object for execution on click events
	 * @param coords
	 * @param tCoords
	 * @param indices
	 * @param r
	 */
	public Button(float[] coords, float[] tCoords, int[] indices, Runnable r){
		super(coords, tCoords, indices, 4, "bgtile.png");
		callback = r;
		xCoords = new float[]{coords[0], coords[3], coords[6], coords[9]};
		yCoords = new float[]{coords[1], coords[4], coords[7], coords[10]};
	}
	@Override
	/**
	 * Set the callback function to be executed upon a click event
	 */
	public void setCallback(Runnable r) {
		callback = r;
		
	}
	@Override
	/**
	 * Returns whether the button was clicked based on mouse coordinates passed
	 * @param xPos
	 * @param yPos
	 * @return
	 */
	public Boolean checkClick(float xPos, float yPos) {
		if (UIUtils.pnpoly(xCoords, yCoords, xPos, yPos)){
			if (callback != null)
				callback.run();
			return true;
		}else{
			return false;
		}
	}

}
