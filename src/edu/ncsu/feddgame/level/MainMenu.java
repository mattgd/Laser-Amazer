package edu.ncsu.feddgame.level;

import edu.ncsu.feddgame.GameInstance;
import edu.ncsu.feddgame.gui.CreateUI;
import edu.ncsu.feddgame.gui.Text;
import edu.ncsu.feddgame.render.Alignment;
import edu.ncsu.feddgame.render.GameColor;
import edu.ncsu.feddgame.render.GameFont;

public class MainMenu extends Menu{
	public MainMenu() {
		super("Main Menu");
	}

	@Override
	public void renderObjects() {
		elementList.add(CreateUI.createButton(0, 3, 3, 1.25f, () ->{
			GameInstance.setLevel(GameInstance.latestLevel);
		}, new GameFont("Continue", GameColor.RED.getFloatColor())));
		elementList.add(CreateUI.createButton(0, 1.5f, 3, 1.25f, () ->{
			GameInstance.latestLevel = 2;
			GameInstance.setLevel(2);
		}, new GameFont("New Game", GameColor.RED.getFloatColor())));
		elementList.add(CreateUI.createButton(0, 0, 3, 1.25f, () ->{
			GameInstance.setLevel(1);
		}, new GameFont("Options", GameColor.RED.getFloatColor())));
		elementList.add(CreateUI.createButton(0, -1.5f, 3, 1.25f, () ->{
			System.exit(1);
		}, new GameFont("Quit", GameColor.RED.getFloatColor())));
		
		elementList.add(new Text(0f, 7f, Alignment.CENTER, "Laser Amazer", GameColor.RED.getFloatColor(), 4f));
	}

}
