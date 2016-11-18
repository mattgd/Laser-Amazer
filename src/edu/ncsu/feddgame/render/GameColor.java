package edu.ncsu.feddgame.render;

import java.awt.Color;

public enum GameColor {
	
	DARK_GREY(48, 48, 48),
	GREY(188, 188, 188),
	ORANGE(243, 119, 53),
	DARK_BLUE(5, 92, 151),
	BLUE(32, 119, 177),
	TEAL(38, 155, 146),
	YELLOW(232, 231, 43),
	RED(228, 21, 19);
	
	/* Old colors
	BLUE(0, 174, 219),
	GREEN(0, 177, 89),
	RED(209, 17, 65),
	TEAL(50, 153, 187),
	YELLOW(255, 196, 37);
	*/
	
	private final Color color;
	
	GameColor(int r, int g, int b) {
		this.color = new Color(r, g, b);
	}
	
	public Color getColor() {
		return color;
	}
	public FloatColor getFloatColor(){
		return new FloatColor(color);
	}
}