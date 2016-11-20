package edu.ncsu.feddgame.level;

import edu.ncsu.feddgame.render.CreateModel;
import edu.ncsu.feddgame.render.LaserStart;
import edu.ncsu.feddgame.render.LaserStop;
import edu.ncsu.feddgame.render.Model;
import edu.ncsu.feddgame.render.MovingBox;

public class Level7 extends Level {

	private MovingBox movingBox;
	
	public Level7() {
		super("Level 7");
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
			CreateModel.createWall(5f, -9f, .25f, 1.5f);
			CreateModel.createWall(7f, -8.5f, .25f, 2.5f);
			
			CreateModel.createWall(0f, 7f, 8f, .25f);
			CreateModel.createWall(4f, 4f, 5f, .25f);
			CreateModel.createWall(-4f, 4f, 5f, .25f);
			
			CreateModel.createWall(5.8f, 0f, 8f, .25f);
			CreateModel.createWall(-5.8f, -2f, 8f, .25f);
		}
		
		// Laser start/stop
		LaserStart laserStart = CreateModel.createLaserStart(6f, 9f, 3);
		laserStart.rotate((float) Math.toRadians(-35));
		laserWrappers.add(laserStart);
		
		LaserStop laserStop = CreateModel.createLaserStop(6f, -9.5f);
		laserStop.rotate((float) Math.toRadians(180));
		
		// Moveables
		CreateModel.createMovableBox(4.05f, -5.925f);
		CreateModel.createMovableTrapezoid(-4f, 5f, 1.5f, 1f, 1f);
		
		CreateModel.createMovableTriangle(4f, 5f, 1f, 1f);
		CreateModel.createMovableTriangle(0f, 5f, 1f, 1f);
		
		Model model;
		for (int i = 0; i < 3; i++) {
			int x = randomInt(-3, 9);
			int y = randomInt(-3, 8);
			
			model = CreateModel.createMovableBox(x, y);
			
			boolean rotate = false;
			if (Math.random() < 0.5) rotate = true;
			float r = (float)Math.random();
			if (rotate)
				model.rotate(r < .5f ? (-(float)Math.PI / 3f):((float)Math.PI / 6f));
		}
		
		// Moving Models
		movingBox = new MovingBox(-5f, 3f, 180, 0.5f, -0.5f);
		
		// Stationary Models
		Model triangle = CreateModel.createTriangle(3.9f, -9f, 1.5f, 2f);
		triangle.rotate((float) Math.toRadians(-90));
		
		triangle = CreateModel.createTriangle(8.1f, -9f, 2f, 1.5f);
		triangle.rotate((float) Math.toRadians(180));
		
		triangle = CreateModel.createTriangle(9.25f, -9.25f, 1f, 1f);
		triangle.rotate((float) Math.toRadians(-90));
	}
	
	@Override
	public void logicLoop() {
		super.logicLoop();
		
		if (movingBox != null) {
			movingBox.logicLoop();
		}
	}

}
