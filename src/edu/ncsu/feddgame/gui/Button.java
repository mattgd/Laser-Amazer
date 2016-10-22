package edu.ncsu.feddgame.gui;

import edu.ncsu.feddgame.GameInstance;
import edu.ncsu.feddgame.render.FloatColor;
import edu.ncsu.feddgame.render.Font;
import edu.ncsu.feddgame.render.Model;

public class Button extends Model implements UIElement, IClickable{
	
	Runnable callback;
	float [] xCoords, yCoords;
	Font label;
	float xOffset, yOffset, height, width;
	/**
	 * New Button extension from Model with Runnable object for execution on click events
	 * @param coords
	 * @param tCoords
	 * @param indices
	 * @param r
	 */
	public Button(float[] coords, float[] tCoords, int[] indices, Runnable r, float xOffset, float yOffset, float height, float width){
		super(coords, tCoords, indices, 4, "bgtile.png");
		this.callback = r;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.height = height;
		this.width = width;
		label = new Font("", new FloatColor(0, 0, 0));
		xCoords = new float[]{coords[0], coords[3], coords[6], coords[9]};
		yCoords = new float[]{coords[1], coords[4], coords[7], coords[10]};
	}
	
	public Button(float[] coords, float[] tCoords, int[] indices, Runnable r, Font f, float xOffset, float yOffset, float height, float width){
		super(coords, tCoords, indices, 4, "bgtile.png");
		this.callback = r;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.height = height;
		this.width = width;
		this.label = f;
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
	
	@Override
	public void render(){
		super.render();
		GameInstance.shader.unbind();
		float w = width /(float)label.getRenderString().length() / 3.5f;
		label.renderString(label.getRenderString(), 16, xCoords[0] / 10, (yOffset - w * 2f) / 20, w);
		GameInstance.shader.bind();
	}

}
