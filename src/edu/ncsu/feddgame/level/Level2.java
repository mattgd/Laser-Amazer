package edu.ncsu.feddgame.level;

import edu.ncsu.feddgame.render.CreateModel;
import edu.ncsu.feddgame.render.Model;

public class Level2 extends Level {
	
	public Level2() {
		super("Level 2");
	}

	@Override
	public void renderObjects() {
		super.renderObjects();
		
		// Inner bounds
		CreateModel.createWall(-5f, 2f, .25f, 18f);
		CreateModel.createWall(2f, -2f, .25f, 16f);
		
		// Laser start/stop
		laserWrappers.add(CreateModel.createLaserStart(-10f, -1f, 2, (float) Math.toRadians(-45)));
		Model model = CreateModel.createLaserStop(10, 7);
		model.rotate((float) Math.toRadians(-90));

		// Moveables
		for (int i = 0; i < 7; i++) {
			int x = randomInt(-3, 9);
			int y = randomInt(-3, 8);
			
			model = CreateModel.createMovableBox(x, y);
			randomRotate(model);
		}
		
		CreateModel.createMovableTriangle(-1f, 1f, 1f, 1f);
		
		// Unmoveables
		CreateModel.createTriangle(2f, 7f, 1f, 1f);
	}

}
