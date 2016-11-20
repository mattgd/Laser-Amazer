package edu.ncsu.feddgame.level;

import edu.ncsu.feddgame.render.CreateModel;
import edu.ncsu.feddgame.render.LaserStart;
import edu.ncsu.feddgame.render.LaserStop;
import edu.ncsu.feddgame.render.Model;

public class Level6 extends Level {

	public Level6() {
		super("Level 5");
	}
	
	@Override
	public void renderObjects() {
		super.renderObjects();
		// Walls
		{
			// Outer bounds
			CreateModel.createWall(0f, 10f, 20f, .5f);
			CreateModel.createWall(0f, -10f, 20f, .5f);
			CreateModel.createWall(-10f, 0f, .5f, 20f);
			CreateModel.createWall(10f, 0f, .5f, 20f);
			
			// Inner bounds
			CreateModel.createWall(0f, -5f, 8f, .25f);
			CreateModel.createWall(-1f, -9f, .25f, 1.5f);
			CreateModel.createWall(1f, -9f, .25f, 1.5f);
		}
		
		// Laser start/stop
		LaserStart laserStart = CreateModel.createLaserStart(-9f, 9f, 3);
		laserStart.rotate((float) Math.toRadians(45));
		laserWrappers.add(laserStart);
		
		LaserStop laserStop = CreateModel.createLaserStop(0f, -9.5f);
		laserStop.rotate((float) Math.toRadians(180));
		
		// Moveables
		CreateModel.createMovableBox(4.05f, -5.925f);
		CreateModel.createMovableTrapezoid(-4f, 5f, 1.5f, 1f, 1f);
		CreateModel.createMovableTrapezoid(-4f, 8f, 1.5f, 1, 1f);
		
		CreateModel.createMovableTriangle(4f, 5f, 1f, 1f);
		CreateModel.createMovableTriangle(0f, 5f, 1f, 1f);
		
		// Stationary Models
		Model box;
		for (int i = 0; i < 2; i++) {
			for (int j = 0 + i; j < 20 - i * 2; j += 2) {
				box = CreateModel.createBox(-9f + j, i * 2);
				box.rotate((float)Math.toRadians(45));
			}
		}
	}
	
	@Override
	public void logicLoop() {
		super.logicLoop();
	}

}
