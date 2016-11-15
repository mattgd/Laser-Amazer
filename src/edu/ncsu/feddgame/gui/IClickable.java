package edu.ncsu.feddgame.gui;

public interface IClickable {
	
	void setCallback(Runnable r);
	boolean checkClick(float xPos, float yPos);

}
