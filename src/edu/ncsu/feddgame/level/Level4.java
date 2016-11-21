package edu.ncsu.feddgame.level;

import edu.ncsu.feddgame.render.CreateModel;
import edu.ncsu.feddgame.render.LaserStop;
import edu.ncsu.feddgame.render.Model;

public class Level4 extends Level {

	public Level4() {
		super("Level 4");
	}
	
	@Override
	public void renderObjects() {
		super.renderObjects();
		
		// Inner bounds
		CreateModel.createWall(-6.3f, -2f, 8f, .25f);
		
		// Laser start/stop
		laserWrappers.add(CreateModel.createLaserStart(0f, 9f, 3));
		LaserStop laserStop = CreateModel.createLaserStop(0f, -9f);
		laserStop.rotate((float) Math.toRadians(180));
		
		// Moveables
		CreateModel.createMovableBox(4.05f, -5.925f);
		CreateModel.createMovableTrapezoid(-4f, 5f, 1.5f, 1, 1);
		
		CreateModel.createMovableTriangle(4f, 1f, 3f, 1.4f);
		CreateModel.createMovableTriangle(0f, 2f, 1f, 1f);
		
		// Stationary Models
		CreateModel.createBox(-4f, 2f);
		
		Model model = CreateModel.createBox(0f, 0f);
		model.rotate((float) Math.toRadians(45));
		
		model = CreateModel.createTriangle(-4f, -4f, -1f, -2f);
		model.rotate((float) Math.toRadians(17));
	}

}
