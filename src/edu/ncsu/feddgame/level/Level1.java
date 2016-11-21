package edu.ncsu.feddgame.level;

import edu.ncsu.feddgame.render.CreateModel;
import edu.ncsu.feddgame.render.Model;

public class Level1 extends Level {
	
	private Model m;
	
	public Level1() {
		super("Level 1");
	}

	@Override
	public void renderObjects() {
		super.renderObjects();
		
		// Inner bounds
		CreateModel.createWall(0, 1f, .25f, 18f);
		
		// Laser start/stop
		laserWrappers.add(CreateModel.createLaserStart(-10f, -1f, 2, (float) Math.toRadians(-45)));
		CreateModel.createLaserStop(7f, 9.9f);
		
		m = CreateModel.createMovableBox(4, 0);
		m.rotate((float) Math.toRadians(-30));
		
		CreateModel.createMovableBox(2, 4);
		
		m = CreateModel.createBox(7, 4, 1); // Stationary box
		
		isRendered = true;
	}
	
	@Override
	public void logicLoop() {
		if (isRendered) {
			super.logicLoop();
			
			m.rotate((float) Math.toRadians(1));
		}
	}

}
