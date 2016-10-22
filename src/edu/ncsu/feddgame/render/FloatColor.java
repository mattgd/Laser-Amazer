package edu.ncsu.feddgame.render;

public class FloatColor {

	private float r, g, b;
	
	public FloatColor(int r, int g, int b) {
		this.r = (1.0f / 255.0f) * ((float) r);
		this.g = (1.0f / 255.0f) * ((float) g);
		this.b = (1.0f / 255.0f) * ((float) b);
	}
	
	public float red() {
		return r;
	}
	
	public float green() {
		return g;
	}
	
	public float blue() {
		return b;
	}
	
}
