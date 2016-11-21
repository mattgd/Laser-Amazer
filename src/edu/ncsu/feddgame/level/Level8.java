package edu.ncsu.feddgame.level;

import edu.ncsu.feddgame.render.CreateModel;
import edu.ncsu.feddgame.render.LaserStart;
import edu.ncsu.feddgame.render.LaserStop;
import edu.ncsu.feddgame.render.Model;

public class Level8 extends Level {

	public Level8() {
		super("Level 8");
	}
	
	@Override
	public void renderObjects() {
		super.renderObjects();
		
		// Walls
		{
			// Inner bounds
			CreateModel.createWall(-8f, 5f, .25f, 5f);
			CreateModel.createWall(-2f, 5f, .25f, 5f);
			CreateModel.createWall(-5f, 2.4f, 6.25f, .25f);
			
			CreateModel.createWall(8f, 5f, .25f, 5f);
			CreateModel.createWall(2f, 5f, .25f, 5f);
			CreateModel.createWall(5f, 2.4f, 6.25f, .25f);
			
			CreateModel.createWall(0f, 0f, 4f, .25f);
			
			// Around LaserStop
			CreateModel.createWall(-1f, -8.5f, .25f, 2.5f);
			CreateModel.createWall(1f, -8.5f, .25f, 2.5f);
		}
		
		// Laser start/stop
		LaserStart laserStart = CreateModel.createLaserStart(3f, 9.3f, 3);
		laserWrappers.add(laserStart);
		
		LaserStop laserStop = CreateModel.createLaserStop(0f, -9.5f);
		laserStop.rotate((float) Math.toRadians(180));
		
		// Moveables
		Model model;
		for (int i = 0; i < 4; i++) {
			int x = randomInt(-3, 9);
			int y = randomInt(-3, 8);
			
			model = CreateModel.createMovableBox(x, y);
			randomRotate(model);
		}
		
		for (int i = 0; i < 6; i++) {
			int x = randomInt(-3, 9);
			int y = randomInt(-3, 8);
			
			model = CreateModel.createMovableTriangle(x, y, 1f, 1f);
			randomRotate(model);
		}
		
		// Stationary Models
		for (int i = 0; i < 10; i+= 2) {
			CreateModel.createBox(-4f + i, -6f);
		}
		
		// Box 1
		Model triangle = CreateModel.createTriangle(-3.1f, 3.5f, 2f, 2f);
		triangle.rotate((float) Math.toRadians(-90));
		triangle = CreateModel.createTriangle(-6.9f, 3.5f, 2f, 2f);
		triangle.rotate((float) Math.toRadians(-180));
		
		// Box 2
		triangle = CreateModel.createTriangle(3.1f, 3.5f, 2f, 2f);
		triangle.rotate((float) Math.toRadians(-180));
		triangle = CreateModel.createTriangle(6.9f, 3.5f, 2f, 2f);
		triangle.rotate((float) Math.toRadians(-90));
		
		// Box 3
		triangle = CreateModel.createTriangle(-6.9f, -6f, 2f, 2f);
		triangle.rotate((float) Math.toRadians(-180));
		triangle = CreateModel.createTriangle(6.9f, -6f, 2f, 2f);
		triangle.rotate((float) Math.toRadians(-90));
		
		triangle = CreateModel.createTriangle(8.75f, 8.75f, 2f, 2f);
		triangle = CreateModel.createTriangle(-8.75f, 8.75f, 2f, 2f);
		triangle.rotate((float) Math.toRadians(90));
	}

}
