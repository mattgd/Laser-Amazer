package edu.ncsu.feddgame.level;

import edu.ncsu.feddgame.render.CreateModel;
import edu.ncsu.feddgame.render.Model;

public class TestLevel extends Level {

	private Model box1, tri1;
	private int i;
	private float dir;
	
	public TestLevel() {
		super("Test Level");
	}
	
	@Override
	public void renderObjects() {
		laserWrapers.add(CreateModel.createLaserStart(7.5f, 9, 3));
		//CreateModel.createLaserStop(3, -9f);
		box1 = CreateModel.createBox(0,0);
		CreateModel.createMovableBox(4.05f, -5.925f);
		CreateModel.createMovableTrapezoid(-.9f, 5.8f, 1.5f, 1, 1);
		CreateModel.createBox(-4f, 2f);
		CreateModel.createMovableTriangle(-4f, 2f, 3f, -1.4f);
		CreateModel.createMovableTriangle(-4f, 2f, -1f, 1f);
		tri1 = CreateModel.createTriangle(-4, -4, -1, -2);
		box1.rotate((float)Math.toRadians(45));
		{ 	//Walls
			CreateModel.createWall(0f, 10f, 20f, .5f);
			CreateModel.createWall(0f, -10f, 20f, .5f);
			CreateModel.createWall(-10f, 0f, .5f, 20f);
			CreateModel.createWall(10f, 0f, .5f, 20f);
		}
		tri1.rotate(.3f);
		i = 0;
		dir = 1;
		laserWrapers.get(0).rotate((float)Math.toRadians(-12));
	}
	
	@Override
	public void logicLoop() {
		super.logicLoop();
		if (i < 160) {
			if (box1 != null)
				box1.move(0.05f * dir, 0f, 0f); // Test animation of models, this pings the box back and forth
			laserWrapers.get(0).rotate((float)Math.toRadians(1));
			i++;
		} else {
			
			i = 0;
			dir *= -1f;
		}
	}

}
