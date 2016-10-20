package edu.ncsu.feddgame.gui;

public interface IClickable {
	void setCallback(Runnable r);
	Boolean checkClick(float xPos, float yPos);

}
