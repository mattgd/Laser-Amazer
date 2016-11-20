package edu.ncsu.feddgame.level;

import edu.ncsu.feddgame.render.CreateModel;
import edu.ncsu.feddgame.render.LaserStop;
import edu.ncsu.feddgame.render.Model;

public class Level4 extends Level {

	public Level4() {
		super("Level 4");
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
			CreateModel.createWall(-6.3f, -2f, 8f, .25f);
		}
		
		// Laser start/stop
		laserWrappers.add(CreateModel.createLaserStart(0f, 9f, 3));
		LaserStop laserStop = CreateModel.createLaserStop(0f, -9f);
		laserStop.rotate((float) Math.toRadians(180.1));
		
		// Moveables
		CreateModel.createMovableBox(4.05f, -5.925f);
		CreateModel.createMovableTrapezoid(-4f, 5f, 1.5f, 1, 1);
		
		CreateModel.createMovableTriangle(4f, 1f, 3f, 1.4f);
		CreateModel.createMovableTriangle(0f, 2f, 1f, 1f);
		
		// Stationary Models
		CreateModel.createBox(-4f, 2f);
		
		Model centerBox = CreateModel.createBox(0f, 0f);
		centerBox.rotate((float)Math.toRadians(45));
		
		Model triangle = CreateModel.createTriangle(-4, -4, -1, -2);
		triangle.rotate(.3f);
	}
	
	@Override
	public void logicLoop() {
		super.logicLoop();
	}

}
