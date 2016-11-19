package edu.ncsu.feddgame.gui;

import java.util.LinkedList;

import edu.ncsu.feddgame.GameInstance;
import edu.ncsu.feddgame.render.GameFont;

public class Dropdown extends Button {
	
	private LinkedList<Button> options = new LinkedList<Button>();
	private boolean open = false;
	private float length = 0;
	
	Dropdown(float[] coords, float[] tCoords, int[] indices, GameFont f, float xOffset, float yOffset, float height, float width) {
		super(coords, tCoords, indices, null, f, xOffset, yOffset, height, width);
	}
	
	/**
	 * Adds the button passed and attaches another callback for this dropdown
	 * @param b
	 */
	public void addButton(Button b) {
		b.addCallback(() -> {
			choiceClicked();
		});
		
		length -= (b.height + .05f);
		b.move(0, length, 0);
		options.add(b);
	}
	
	public void addButtons(Button[] b) {
		for (Button but : b) {
			but.addCallback(() -> {
				choiceClicked();
			});
			
			length -= (but.height / 2f + .05f);
			but.move(0, length, 0);
			options.add(but);
		}
	}
	
	@Override
	public boolean checkClick(float xPos, float yPos) {
		if (open) {
			// Check click on all buttons in the dropdown first
			for (Button b : options) {
				b.checkClick(xPos, yPos);
			}
		}
		
		// Then check if the dropdown was clicked
		if (UIUtils.pnpoly(super.xCoords, super.yCoords, xPos * GameInstance.window.ratio, yPos)) {
			choiceClicked();
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Toggles whether the Dropdown is open.
	 */
	public void choiceClicked() {
		open = !open;
	}
	
	/**
	 * Renders everything necessary for a Dropdown menu.
	 */
	@Override
	public void render() { 	
		super.render();
		
		if (open) {
			for (Button b : options) {
				b.render();
			}
		}
	}

}
