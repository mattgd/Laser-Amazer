package edu.ncsu.feddgame.level;

import edu.ncsu.feddgame.render.CreateModel;
import edu.ncsu.feddgame.render.LaserStart;
import edu.ncsu.feddgame.render.LaserStop;
import edu.ncsu.feddgame.render.Model;
import edu.ncsu.feddgame.render.MovableModel;
import edu.ncsu.feddgame.render.MovingBox;

public class Level10 extends Level {

	private MovingBox[] movingBoxes;
	private Model tri1;
	private MovableModel box2;
	private LaserStop laserStop;
	
	public Level10() {
		super("Level 10");
	}
	
	@Override
	public void renderObjects() {
		LaserStart start = CreateModel.createLaserStart(9.3f, 9.3f, 3, Math.toRadians(254));
		laserWrappers.add(start);
		
		laserStop = CreateModel.createLaserStop(-9.3f, 9.3f);
		
		// MovingBoxes
		MovingBox movingBox1 = new MovingBox(2.85f, 2f, 123);
		movingBox1.rotate(45);
		
		MovingBox movingBox2 = new MovingBox(-8f, 2f, 123);
		
		movingBoxes = new MovingBox[] { movingBox1, movingBox2 };
		
		box2 = CreateModel.createMovableBox(4.05f, -5.925f);
		CreateModel.createMovableTrapezoid(-.9f, 5.8f, 1.5f, 1, 1);
		CreateModel.createBox(3.4f, -8);
		
		tri1 = CreateModel.createTriangle(-4, -4, -1, -2);
		tri1.rotate(.3f);
		
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
		
		for (MovingBox mBox : movingBoxes) {
			if (mBox != null)
				mBox.logicLoop();
		}
	}

}
