package edu.ncsu.feddgame.level;

import edu.ncsu.feddgame.render.CreateModel;
import edu.ncsu.feddgame.render.Model;
import edu.ncsu.feddgame.render.MovingBox;

public class DemoLevel extends Level{

	private MovingBox movingBox;
	private Model box1, box2, box3, box4;
	
	public DemoLevel() {
		super("Demo Level");
	}
	
	@Override
	public void renderObjects(){
		super.renderObjects();
		
		laserWrappers.add(CreateModel.createLaserStart(-10f, 5f, 2, (float) Math.toRadians(-45)));
		laserWrappers.add(CreateModel.createLaserStart(2f, -10f, 1, (float) Math.toRadians(38)));
		
		movingBox = new MovingBox(2, 0, 240, 120, .5f, 0);
		movingBox.rotate((float)Math.toRadians(45));
		CreateModel.createTriangle(-5.5f, 1f, 2f, 1f);
		box1 = CreateModel.createBox(-2, 8, 2);
		box1.rotate((float)Math.toRadians(30));
		box2 = CreateModel.createBox(0, 5.5f,1.5f);
		box2.rotate((float)Math.toRadians(40));
		box3 = CreateModel.createBox(7, -1);
		box4 = CreateModel.createBox(-7f, -1);
		isRendered = true;
	}
	
	@Override
	public void logicLoop() {
		if (isRendered) {
			super.logicLoop();
			box3.rotate((float)Math.toRadians(.5));
			box4.rotate((float)Math.toRadians(.3));
			movingBox.logicLoop();
		}
	}

}
