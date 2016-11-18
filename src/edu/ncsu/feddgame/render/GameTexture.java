package edu.ncsu.feddgame.render;

public enum GameTexture {

	BUTTON("button.png"),
	MOVEABLE_BOX("moveablebox.png"),
	UNMOVEABLE_BOX("unmoveablebox.png"),
	WALL("bgtile.png");
	
	private final String path;
	
	GameTexture(final String path) {
		this.path = path;
	}
	
	public String getPath() {
		return path;
	}
	
	@Override
	public String toString() {
		return path;
	}
	
}
