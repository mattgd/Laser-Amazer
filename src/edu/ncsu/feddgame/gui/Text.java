package edu.ncsu.feddgame.gui;

import edu.ncsu.feddgame.render.Alignment;
import edu.ncsu.feddgame.render.FloatColor;
import edu.ncsu.feddgame.render.GameFont;

public class Text implements UIElement {
	
	private GameFont label;
	private float xOffset, yOffset, size;
	private Alignment align;
	
	/**
	 * New Text UIElement
	 * @param xOffset
	 * @param yOffset
	 * @param str
	 * @param f
	 * @param size
	 */
	public Text(float xOffset, float yOffset, String str, FloatColor f, float size){
		label = new GameFont(str, f);
		this.xOffset = xOffset/10f;
		this.yOffset = yOffset/10f;
		this.size = size / 10f;
	}
	
	/**
	 * 
	 * @param xOffset
	 * @param yOffset
	 * @param align
	 * @param str
	 * @param f
	 * @param size
	 */
	public Text(float xOffset, float yOffset, Alignment align, String str, FloatColor f, float size){
		yOffset /= 20f;
		size /= 10f;
		switch (align) {
		case LEFT:
			xOffset = -1.5f;
			break;
		case CENTER:
			xOffset = -0.05f / (0.3f / size);
			xOffset *= str.length();
			break;
		case RIGHT:
			float characterShift = str.length() * 2f + 1f;
			xOffset = 1.5f - ((str.length() + str.length() / characterShift) / 10f);
			break;
		}
		
		label = new GameFont(str, f);
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.size = size;
		this.align = align;
	}
	
	@Override
	public void render() {
		label.renderString(label.getRenderString(), xOffset, yOffset, size);
		
	}
	
	/**
	 * Change the label rendered by the text
	 * @param f
	 */
	public void setLabel(GameFont f) {
		this.label = f;
		String str = label.getRenderString();
		switch (align) {
		case LEFT:
			xOffset = -1.5f;
			break;
		case CENTER:
			xOffset = -0.05f / (0.3f / size);
			xOffset *= str.length();
			break;
		case RIGHT:
			float characterShift = str.length() * 2f + 1f;
			xOffset = 1.5f - ((str.length() + str.length() / characterShift) / 10f);
			break;
		}
	}

}
