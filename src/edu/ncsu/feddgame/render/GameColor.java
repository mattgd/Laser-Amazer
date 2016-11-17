package edu.ncsu.feddgame.render;

import java.awt.Color;

public enum GameColor {
	BLUE(0, 174, 219),
	DARK_GREY(48, 48, 48),
	GREEN(0, 177, 89),
	GREY(188, 188, 188),
	ORANGE(243, 119, 53),
	RED(209, 17, 65),
	TEAL(50, 153, 187),
	YELLOW(255, 196, 37);
	
	private final Color color;
	
	GameColor(int r, int g, int b) {
		this.color = new Color(r, g, b);
	}
	
	public Color getColor() {
		return color;
	}
}