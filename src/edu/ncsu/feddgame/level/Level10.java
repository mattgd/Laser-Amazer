package edu.ncsu.feddgame.level;

import edu.ncsu.feddgame.render.CreateModel;
import edu.ncsu.feddgame.render.Model;

public class Level10 extends Level {

	private Model box1, tri1;
	private int i;
	private float dir;
	
	public Level10() {
		super("Level 10");
	}
	
	@Override
	public void renderObjects() {
		laserWrapers.add(CreateModel.createLaserStart(7.5f, 9, 3, Math.toRadians(251)));
		CreateModel.createLaserStop(-9.3f, 9.3f);
		box1 = CreateModel.createBox(2.85f, 0f);
		CreateModel.createMovableBox(4.05f, -5.925f);
		CreateModel.createMovableTrapezoid(-.9f, 5.8f, 1.5f, 1, 1);
		CreateModel.createBox(3.4f, -8);
		tri1 = CreateModel.createTriangle(-4, -4, -1, -2);
		box1.rotate((float)Math.toRadians(45));
		{
			// Outer bounds
			CreateModel.createWall(0f, 10f, 20f, .5f);
			CreateModel.createWall(0f, -10f, 20f, .5f);
			CreateModel.createWall(-10f, 0f, .5f, 20f);
			CreateModel.createWall(10f, 0f, .5f, 20f);
			
			// Inner bounds
			CreateModel.createWall(-5f, 2f, .25f, 18f);
			CreateModel.createWall(2f, -2f, .25f, 16f);
		}
		tri1.rotate(.3f);
		i = 0;
		dir = 1;
		
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
		if (i < 123) {
			if (box1 != null)
				box1.move(0.05f * dir, 0f, 0f); // Test animation of models, this pings the box back and forth
			
			i++;
		} else {
			
			i = 0;
			dir *= -1f;
		}
	}

}
