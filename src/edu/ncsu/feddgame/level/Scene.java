package edu.ncsu.feddgame.level;

import java.text.DecimalFormat;
import java.util.ArrayList;

import edu.ncsu.feddgame.gui.IClickable;
import edu.ncsu.feddgame.gui.UIElement;

public abstract class Scene {
	
	public ArrayList<UIElement> elementList = new ArrayList<UIElement>();
	protected boolean active = false;
	protected String name;
	private double timeStart, timeStop;
	
	public Scene(String name) {
		this.name = name;
	}
	
	public abstract void renderObjects();
	
	public void logicLoop() {}
	
	public void renderLoop() {
		if (elementList != null){
			for (UIElement e : elementList) {
				e.render();
			}
		}
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		if (this.active == active) return;
		
		this.active = active;
		
		if (active) {
			timeStart = getTime();
		} else {
			timeStop = getTime();
		}
	}
	
	public void checkClick(float mouseX, float mouseY) {
		for (UIElement e : elementList) {
			if (e instanceof IClickable) {
				((IClickable)e).checkClick(mouseX, mouseY);
			}
		}
	}
	
	public double getElapsedTime() {
		if (active) {
			return getTime() - timeStart;
		} else {
			return timeStop - timeStart;
		}
	}
	
	public String getElapsedSeconds() {
		return new DecimalFormat("#.#").format(getElapsedTime());
	}
	
	/**
	 * @return System time in seconds
	 */
	public double getTime() {
		return (double) System.nanoTime() / (double) 1000000000L;
	}
	
}
