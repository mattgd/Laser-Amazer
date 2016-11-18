package edu.ncsu.feddgame.level;

import edu.ncsu.feddgame.render.CreateModel;
import edu.ncsu.feddgame.render.Model;

public class Level1 extends Level {
	boolean isrendered = false;
	private Model m;
	
	public Level1() {
		super("Level 1");
	}

	@Override
	public void renderObjects() {
		super.renderObjects();
		{ // Walls
			CreateModel.createWall(0f, 10f, 20f, .5f);
			CreateModel.createWall(0f, -10f, 20f, .5f);
			CreateModel.createWall(-10f, 0f, .5f, 20f);
			CreateModel.createWall(10f, 0f, .5f, 20f);
			CreateModel.createWall(0, 1f, .25f, 18f);
		}
		
		laserWrappers.add(CreateModel.createLaserStart(-10f, -1f, 2, Math.toRadians(-45)));
		
		CreateModel.createLaserStop(7, 10);
		m = CreateModel.createMovableBox(4, 0);
		CreateModel.createMovableBox(2, 4);
		m.rotate(-.5f);
		
		m = CreateModel.createBox(7, 4, 1); // Stationary box
		isrendered = true;
	}
	
	@Override
	public void logicLoop() {
		if (isrendered){
			super.logicLoop();
			m.rotate(.005f);
		}
	}

}
