package edu.ncsu.feddgame.level;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import edu.ncsu.feddgame.GameInstance;
import edu.ncsu.feddgame.gui.CreateUI;
import edu.ncsu.feddgame.gui.Dropdown;
import edu.ncsu.feddgame.render.GameColor;
import edu.ncsu.feddgame.render.GameFont;
import edu.ncsu.feddgame.render.LaserStart;
import edu.ncsu.feddgame.render.LaserStop;

public abstract class Level extends Scene {
	
	protected ArrayList<LaserStart> laserWrappers = new ArrayList<LaserStart>();
	protected LaserStop laserStop;
	
	public Level(String name) {
		super(name);
	}
	
	protected int randomInt(int min, int max) {
		return new Random().nextInt(max - min + 1) + min;
	}

	public void logicLoop() {
		if (laserWrappers != null) {
			for (Iterator<LaserStart> it = laserWrappers.iterator(); it.hasNext();) {
				it.next().reflect();
			}
		}
	}
	
	@Override
	public void renderObjects() {
		laserWrappers.clear();
		
		Dropdown du = CreateUI.createDropdown(-12f, 8f, 3f, 1f, new GameFont("Select Level", GameColor.RED.getFloatColor()));
		for (Scene level : GameInstance.scenes) {
			if (level instanceof Level && GameInstance.scenes.indexOf(level) <= GameInstance.latestLevel)
				du.addButton(CreateUI.createButton(-12f, 8f, 3f, 1, () -> {
					GameInstance.setLevel(GameInstance.scenes.indexOf(level));
				}, new GameFont(level.getName(), GameColor.RED.getFloatColor())));
		}
		elementList.add(du);
		
		elementList.add(CreateUI.createButton(-12f, 9.25f, 3, 1, ()-> {
			GameInstance.setLevel(0);
		}, new GameFont("Main Menu", GameColor.RED.getFloatColor())));
	}

	@Override
	public void renderLoop() {
		super.renderLoop();
		
		if (laserWrappers != null) {
			for (LaserStart l : laserWrappers)
				l.render();
		}
	}

}
