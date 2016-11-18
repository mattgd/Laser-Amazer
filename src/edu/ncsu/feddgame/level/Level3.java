package edu.ncsu.feddgame.level;

import edu.ncsu.feddgame.render.CreateModel;
import edu.ncsu.feddgame.render.Model;
import edu.ncsu.feddgame.render.MovingBox;

public class Level3 extends Level {

	private MovingBox movingBox;
	
	public Level3() {
		super("Level 3");
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
			
			// Inner bounds 	   xOffset, yOffset, width, height
			CreateModel.createWall(-4f, 2f, 12f, .25f);
			CreateModel.createWall(2f, -1.37f, .25f, 7f);
			CreateModel.createWall(-7.7f, 6f, 5f, .25f);
			
			// Unmoveables
			CreateModel.createBox(-4f, 5f, 1f);
		}
		
		laserWrappers.add(CreateModel.createLaserStart(-10f, -1f, 2, Math.toRadians(-45)));
		
		Model laserStop = CreateModel.createLaserStop(-10, 7);
		laserStop.rotate(1.55f);

		movingBox = new MovingBox(2.85f, 2.6f, 123, 0f, 1f);
		
		Model model;
		for (int i = 0; i < 7; i++) {
			int x = randomInt(1, 9);
			int y = randomInt(2, 8);
			
			model = CreateModel.createMovableBox(x, y);
			
			boolean rotate = false;
			if (Math.random() < 0.5) rotate = true;
			
			if (rotate)
				model.rotate((float) Math.random());
		}
	}
	
	@Override
	public void logicLoop() {
		super.logicLoop();
		
		//TODO: Logic loop is sometimes called first, so cancel the rest if Array is null
		if (movingBox != null) {
			movingBox.logicLoop();
		}
	}

}
