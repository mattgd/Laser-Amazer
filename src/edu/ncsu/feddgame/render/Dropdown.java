package edu.ncsu.feddgame.render;

import java.util.LinkedList;

import edu.ncsu.feddgame.gui.Button;
import edu.ncsu.feddgame.gui.UIUtils;

public class Dropdown extends Button{
	
	LinkedList<Button> options = new LinkedList<Button>();
	Boolean open = false;
	float length = 0;

	public Dropdown(float[] coords, float[] tCoords, int[] indices, float xOffset, float yOffset, float height, float width) {
		super(coords, tCoords, indices, null, xOffset, yOffset, height, width);
	}
	
	public Dropdown(float[] coords, float[] tCoords, int[] indices, Font f, float xOffset, float yOffset, float height, float width) {
		super(coords, tCoords, indices, null, f, xOffset, yOffset, height, width);
	}
	
	/**
	 * Adds the button passed and attaches another callback for this dropdown
	 * @param b
	 */
	public void addButton(Button b){
		b.addCallback(() -> {
			choiceClicked();
		});
		length -= (b.height + .05f);
		b.move(0, length, 0);
		options.add(b);
	}
	public void addButtons(Button[] b){
		for (Button but : b){
			but.addCallback(() -> {
				choiceClicked();
			});
			length -= (but.height / 2f + .05f);
			but.move(0, length, 0);
			options.add(but);
		}
	}
	
	@Override
	public Boolean checkClick(float xPos, float yPos){
		if (open){
			for (Button b : options){ 	//Check click on all buttons in the dropdown first
				b.checkClick(xPos, yPos);
			}
		}
		if (UIUtils.pnpoly(super.xCoords, super.yCoords, xPos, yPos)){ 	//Then check if the dropdown was clicked
			choiceClicked();
			return true;
		}else{
			return false;
		}
		
	}
	
	public void choiceClicked(){ 	//Toggles whether the dropdown is open
		open = !open;
	}
	
	@Override
	public void render(){ 	//Renders everything necessary
		super.render();
		if (open){
			for(Button b : options){
				//System.out.println(b);
				b.render();
			}
		}
	}

}
