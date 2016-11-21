package edu.ncsu.feddgame.level;

import java.util.ArrayList;

import edu.ncsu.feddgame.GameInstance;
import edu.ncsu.feddgame.SaveData;
import edu.ncsu.feddgame.gui.Button;
import edu.ncsu.feddgame.gui.CreateUI;
import edu.ncsu.feddgame.gui.Dropdown;
import edu.ncsu.feddgame.gui.IClickable;
import edu.ncsu.feddgame.gui.Text;
import edu.ncsu.feddgame.gui.UIElement;
import edu.ncsu.feddgame.render.Alignment;
import edu.ncsu.feddgame.render.GameColor;
import edu.ncsu.feddgame.render.GameFont;

public class OptionsMenu extends Menu {
	
	public ArrayList<UIElement> gameList = new ArrayList<UIElement>();
	public ArrayList<UIElement> graphicsList = new ArrayList<UIElement>();
	private int display = 0;
	private boolean levelComplete = true, showTimer = true;
	private Button complete, timer;
	private Dropdown multisampling;
	
	public OptionsMenu() {
		super("Options Menu");
	}

	@Override
	public void renderObjects() {
		elementList.clear();
		gameList.clear();
		graphicsList.clear();
		elementList.add(CreateUI.createButton(-2, 6, 3, 1.5f, () ->{
			display = 0;
		}, new GameFont("Game", GameColor.TEAL.getFloatColor())));
		elementList.add(CreateUI.createButton(2, 6, 3, 1.5f, () ->{
			display = 1;
		}, new GameFont("Graphics", GameColor.TEAL.getFloatColor())));
		elementList.add(CreateUI.createButton(0, -6, 6, 1.5f, () ->{
			SaveData.writeData();
			GameInstance.setLevel(0);
		}, new GameFont("Return to Main Menu", GameColor.TEAL.getFloatColor())));
		
		gameList.add(new Text(0f, 4f, Alignment.CENTER, "Game Options", GameColor.ORANGE.getFloatColor(), 1.5f));
		gameList.add(new Text(-9f, 1f, "Show Level Complete -", GameColor.BLUE.getFloatColor(), 1.2f));
		gameList.add(new Text(-5.4f, 0f, "Show Timer -", GameColor.BLUE.getFloatColor(), 1.2f));
		complete = CreateUI.createButton(2f, 2.25f, 3, .8f, () -> {
			levelComplete = !levelComplete;
			if (levelComplete){
				complete.setLabel(new GameFont(" Yes ", GameColor.YELLOW.getFloatColor()));
			}else{
				complete.setLabel(new GameFont(" No ", GameColor.YELLOW.getFloatColor()));
			}
			GameInstance.levelCompleteDialogue = levelComplete;
		}, new GameFont(GameInstance.levelCompleteDialogue ? " Yes " : " No ", GameColor.YELLOW.getFloatColor()));
		gameList.add(complete);
		timer = CreateUI.createButton(2f, 0.25f, 3, .8f, () -> {
			showTimer = !showTimer;
			if (showTimer){
				timer.setLabel(new GameFont(" Yes ", GameColor.YELLOW.getFloatColor()));
			}else{
				timer.setLabel(new GameFont(" No ", GameColor.YELLOW.getFloatColor()));
			}
			GameInstance.showTimer = showTimer;
		}, new GameFont(GameInstance.showTimer ? " Yes " : " No ", GameColor.YELLOW.getFloatColor()));
		gameList.add(timer);
		
		graphicsList.add(new Text(0f, 4f, Alignment.CENTER, "Graphics Options", GameColor.ORANGE.getFloatColor(), 1.5f));
		graphicsList.add(new Text(-9f, 1f, "Multisampling Level -", GameColor.BLUE.getFloatColor(), 1.2f));
		multisampling = CreateUI.createDropdown(2f, 2.25f, 2.5f, .8f, new GameFont(" " + Integer.toString(GameInstance.samplingLevel), GameColor.YELLOW.getFloatColor()), new GameFont[]{
				new GameFont(" 0", GameColor.YELLOW.getFloatColor()),
				new GameFont(" 1", GameColor.YELLOW.getFloatColor()),
				new GameFont(" 2", GameColor.YELLOW.getFloatColor()),
				new GameFont(" 3", GameColor.YELLOW.getFloatColor()),
				new GameFont(" 4", GameColor.YELLOW.getFloatColor())
		}, new Runnable[]{
				() -> {
					GameInstance.samplingLevel = 0;
					multisampling.setLabel(new GameFont(" " + Integer.toString(GameInstance.samplingLevel), GameColor.YELLOW.getFloatColor()));
				},
				() -> {
					GameInstance.samplingLevel = 1;
					multisampling.setLabel(new GameFont(" " + Integer.toString(GameInstance.samplingLevel), GameColor.YELLOW.getFloatColor()));
				},
				() -> {
					GameInstance.samplingLevel = 2;
					multisampling.setLabel(new GameFont(" " + Integer.toString(GameInstance.samplingLevel), GameColor.YELLOW.getFloatColor()));
				},
				() -> {
					GameInstance.samplingLevel = 3;
					multisampling.setLabel(new GameFont(" " + Integer.toString(GameInstance.samplingLevel), GameColor.YELLOW.getFloatColor()));
				},
				() -> {
					GameInstance.samplingLevel = 4;
					multisampling.setLabel(new GameFont(" " + Integer.toString(GameInstance.samplingLevel), GameColor.YELLOW.getFloatColor()));
				}
		});
		graphicsList.add(multisampling);
	}
	
	@Override
	public void renderLoop(){
		super.renderLoop();
		if (display == 0){
			for (UIElement e : gameList){
				e.render();
			}
		}else if(display == 1){
			for (UIElement e : graphicsList)
				e.render();
		}
	}
	
	@Override
	public void checkClick(float mouseX, float mouseY){
		super.checkClick(mouseX, mouseY);
		for (UIElement e : gameList){
			if (e instanceof IClickable){
				((IClickable)e).checkClick(mouseX, mouseY);
			}
		}
		for (UIElement e : graphicsList){
			if (e instanceof IClickable){
				((IClickable)e).checkClick(mouseX, mouseY);
			}
		}
	}

}
