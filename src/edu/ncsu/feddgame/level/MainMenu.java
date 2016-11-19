package edu.ncsu.feddgame.level;

import edu.ncsu.feddgame.GameInstance;
import edu.ncsu.feddgame.SaveData;
import edu.ncsu.feddgame.gui.CreateUI;
import edu.ncsu.feddgame.gui.Text;
import edu.ncsu.feddgame.render.Alignment;
import edu.ncsu.feddgame.render.GameColor;
import edu.ncsu.feddgame.render.GameFont;

public class MainMenu extends Menu {
	
	public MainMenu() {
		super("Main Menu");
	}

	@Override
	public void renderObjects() {
		elementList.add(CreateUI.createButton(0, 1, 3, 1.25f, () -> {
			GameInstance.setLevel(GameInstance.latestLevel);
		}, new GameFont("Continue", GameColor.RED.getFloatColor())));
		
		elementList.add(CreateUI.createButton(0, -0.5f, 3, 1.25f, () -> {
			GameInstance.latestLevel = 2;
			GameInstance.setLevel(2);
		}, new GameFont("New Game", GameColor.RED.getFloatColor())));
		
		elementList.add(CreateUI.createButton(0, -2f, 3, 1.25f, () -> {
			GameInstance.setLevel(1);
		}, new GameFont("Options", GameColor.RED.getFloatColor())));
		elementList.add(CreateUI.createButton(0, -3.5f, 3, 1.25f, () ->{
			SaveData.writeData();
			System.exit(1);
		}, new GameFont("Quit", GameColor.RED.getFloatColor())));
		
		elementList.add(new Text(0f, 5f, Alignment.CENTER, "Laser Amazer", GameColor.RED.getFloatColor(), 6f));
		elementList.add(new Text(0f, 4f, Alignment.CENTER, "A FEDD educational computer game.", GameColor.ORANGE.getFloatColor(), 2f));
	}

}
