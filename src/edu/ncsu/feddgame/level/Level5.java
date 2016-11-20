package edu.ncsu.feddgame.level;

import edu.ncsu.feddgame.render.CreateModel;
import edu.ncsu.feddgame.render.LaserStart;
import edu.ncsu.feddgame.render.Model;

public class Level5 extends Level {

	public Level5() {
		super("Level 6");
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
			
			// Around LaserStart
			CreateModel.createWall(-1f, -1f, .25f, 3f);
			CreateModel.createWall(1f, -1f, .25f, 3f);
			CreateModel.createWall(0f, 0.6f, 2.25f, .25f);
			
			// Around Laser Stop
			CreateModel.createWall(-5f, 9f, .25f, 1.5f);
			CreateModel.createWall(-3f, 9f, .25f, 1.5f);
			CreateModel.createWall(-7.5f, 10f, 4.8f, 3.5f);
			CreateModel.createWall(3.5f, 10f, 12.8f, 3.5f);
			
			// Others
			CreateModel.createWall(-4f, 0f, .25f, 5f);
			CreateModel.createWall(4f, -2.625f, .25f, 5f);
			CreateModel.createWall(-6f, -2.625f, .25f, 5f);
			CreateModel.createWall(-4f, 5f, 5f, .25f);
			CreateModel.createWall(4f, 5f, 5f, .25f);
		}
		
		// Laser start/stop
		LaserStart laserStart = CreateModel.createLaserStart(0f, 0f, 3);
		laserWrappers.add(laserStart);
		CreateModel.createLaserStop(-4f, 9f);
		
		// Moveables
		CreateModel.createMovableBox(4.05f, -5.925f);
		CreateModel.createMovableTrapezoid(-4f, 5f, 1.5f, 1f, 1f);
		CreateModel.createMovableTrapezoid(-4f, 8f, 1.5f, 1, 1f);
		
		Model triangle = CreateModel.createMovableTriangle(4f, 5f, 1f, 1f);;
		triangle.rotate((float) Math.toRadians(35));
		
		triangle = CreateModel.createMovableTriangle(4f, 6f, 1f, 1f);;
		triangle.rotate((float) Math.toRadians(90));
		
		CreateModel.createMovableTriangle(4f, 5f, 1f, 1f);
		CreateModel.createMovableTriangle(0f, 5f, 1f, 1f);
		
		// Stationary Models
		triangle = CreateModel.createTriangle(-8.8f, 7.25f, 2f, 2f);
		triangle.rotate((float) Math.toRadians(90));
		
		triangle = CreateModel.createTriangle(-5.38f, 7.75f, 1f, 1f);
		triangle = CreateModel.createTriangle(8.8f, 7.25f, 2f, 2f);
	}
	
	@Override
	public void logicLoop() {
		super.logicLoop();
	}

}
