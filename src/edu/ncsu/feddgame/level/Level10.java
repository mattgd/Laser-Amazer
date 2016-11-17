package edu.ncsu.feddgame.level;

import edu.ncsu.feddgame.render.CreateModel;
import edu.ncsu.feddgame.render.LaserStart;
import edu.ncsu.feddgame.render.LaserStop;
import edu.ncsu.feddgame.render.Model;
import edu.ncsu.feddgame.render.MovableModel;

public class Level10 extends Level {

	private Model movingBox1, tri1;
	private MovableModel box2;
	private LaserStop lasstop;
	private int i;
	private float dir;
	
	public Level10() {
		super("Level 10");
	}
	
	@Override
	public void renderObjects() {
		LaserStart start = CreateModel.createLaserStart(9.3f, 9.3f, 3, Math.toRadians(254));
		laserWrapers.add(start);
		
		lasstop = CreateModel.createLaserStop(-9.3f, 9.3f);
		
		movingBox1 = CreateModel.createBox(2.85f, 2f);
		
		box2 = CreateModel.createMovableBox(4.05f, -5.925f);
		CreateModel.createMovableTrapezoid(-.9f, 5.8f, 1.5f, 1, 1);
		CreateModel.createBox(3.4f, -8);
		tri1 = CreateModel.createTriangle(-4, -4, -1, -2);
		movingBox1.rotate((float)Math.toRadians(45));
		
		{
			// Outer bounds
			CreateModel.createWall(0f, 10f, 20f, .5f);
			CreateModel.createWall(0f, -10f, 20f, .5f);
			CreateModel.createWall(-10f, 0f, .5f, 20f);
			CreateModel.createWall(10f, 0f, .5f, 20f);
			
			// Inner walls
			CreateModel.createWall(-5f, 2f, .25f, 18f);
			CreateModel.createWall(2f, -2f, .25f, 16f);
			CreateModel.createWall(6f, -4f, .25f, 9f);
			CreateModel.createWall(6f, 7.5f, .25f, 8f);
		}
		tri1.rotate(.3f);
		i = 0;
		dir = 1;
		
		Model model;
		for (int i = 0; i < 7; i++) {
			int x = randomInt(1, 9);
			int y = randomInt(2, 8);
			
			model = CreateModel.createMovableTriangle(x, y, 1, 1);
			
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
			if (movingBox1 != null)
				movingBox1.move(0.05f * dir, 0f, 0f); // Test animation of models, this pings the box back and forth
			
			i++;
		} else {
			
			i = 0;
			dir *= -1f;
		}
	}

}
