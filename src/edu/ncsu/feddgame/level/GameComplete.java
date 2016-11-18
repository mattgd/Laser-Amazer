package edu.ncsu.feddgame.level;

import edu.ncsu.feddgame.GameInstance;
import edu.ncsu.feddgame.SaveData;
import edu.ncsu.feddgame.gui.CreateUI;
import edu.ncsu.feddgame.gui.Text;
import edu.ncsu.feddgame.render.Alignment;
import edu.ncsu.feddgame.render.GameColor;
import edu.ncsu.feddgame.render.GameFont;

public class GameComplete extends Menu {
	
	public GameComplete() {
		super("Game Complete");
	}

	@Override
	public void renderObjects() {
		elementList.add(CreateUI.createButton(0, -2f, 6f, 1.5f, () ->{
			GameInstance.setLevel(0);
		}, new GameFont("Main Menu", GameColor.TEAL.getFloatColor())));
		elementList.add(CreateUI.createButton(0, -4f, 6f, 1.5f, () ->{
			SaveData.writeData();
			System.exit(1);
		}, new GameFont("Quit Game", GameColor.RED.getFloatColor())));
		
		elementList.add(new Text(0f, 3.5f, Alignment.CENTER, "Congratulations!", GameColor.ORANGE.getFloatColor(), 5f));
		elementList.add(new Text(0f, 1.5f, Alignment.CENTER, "You've completed the game.", GameColor.YELLOW.getFloatColor(), 2.5f));
	}

}
