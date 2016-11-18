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
		{ // Walls
			// Outer bounds
			CreateModel.createWall(0f, 10f, 20f, .5f);
			CreateModel.createWall(0f, -10f, 20f, .5f);
			CreateModel.createWall(-10f, 0f, .5f, 20f);
			CreateModel.createWall(10f, 0f, .5f, 20f);
			
			// Inner bounds
			CreateModel.createWall(-5f, 2f, .25f, 18f);
			CreateModel.createWall(2f, -2f, .25f, 16f);
		}
		laserWrappers.add(CreateModel.createLaserStart(-10f, -1f, 2, Math.toRadians(-45)));
		
		Model model = CreateModel.createLaserStop(10, 7);
		model.rotate(-1.55f);

		for (int i = 0; i < 7; i++) {
			int x = randomInt(1, 9);
			int y = randomInt(2, 8);
			
			model = CreateModel.createMovableBox(x, y);
			
			boolean rotate = false;
			if (Math.random() < 0.5) rotate = true;
			float r = (float)Math.random();
			if (rotate)
				model.rotate(r < .5f ? (-(float)Math.PI / 3f):((float)Math.PI /6f));
		}
		CreateModel.createMovableTriangle(-1f, 1f, 1, 1);
		CreateModel.createTriangle(2, 7, 1, 1);
	}

}
