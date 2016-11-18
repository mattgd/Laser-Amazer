package edu.ncsu.feddgame.level;

import java.util.ArrayList;

import edu.ncsu.feddgame.GameInstance;
import edu.ncsu.feddgame.gui.IClickable;
import edu.ncsu.feddgame.gui.UIElement;

public abstract class Scene {
	public ArrayList<UIElement> elementList = new ArrayList<UIElement>();
	protected boolean activeLevel = false;
	protected String name;
	
	public Scene(String name){
		this.name = name;
	}
	
	public abstract void renderObjects();
	
	public void logicLoop(){
		
	}
	
	public void renderLoop(){
		if (elementList != null){
			for(UIElement e : elementList){
				e.render();
			}
		}
	}
	
	public String getName(){
		return name;
	}
	public boolean isActiveLevel(){
		return activeLevel;
	}
	public void setActiveLevel(boolean active){
		activeLevel = active;
	}
	
	public void checkClick(float mouseX, float mouseY){
		for (UIElement e : elementList){
			if (e instanceof IClickable){
				((IClickable)e).checkClick(mouseX, mouseY);
			}
		}
	}
}
