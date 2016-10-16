package edu.ncsu.feddgame.level;

import edu.ncsu.feddgame.GameInstance;
import edu.ncsu.feddgame.render.CreateModel;
import edu.ncsu.feddgame.render.LaserStart;
import edu.ncsu.feddgame.render.LaserStop;
import edu.ncsu.feddgame.render.Model;
import edu.ncsu.feddgame.render.Wall;

public class TestLevel implements ILevel{

	Model box1;
	Wall top, bottom, left, right;
	LaserStart laswrap;
	LaserStop lasstop;
	
	@Override
	public void renderObjects() {
		box1 = CreateModel.createBox(0,0);
		CreateModel.createBox(2f, -6);
		CreateModel.createTrapezoid(-10f, 5f, 2f, 1, 1);
		CreateModel.createBox(4f, -9);
		laswrap = CreateModel.createLaserStart(8, 10, 3, Math.toRadians(251));
		lasstop = CreateModel.createLaserStop(3, -10.4f);
			top = CreateModel.createWall(0f, 12.5f, 25f, .5f);
			bottom = CreateModel.createWall(0f, -12.5f, 25f, .5f);
			left = CreateModel.createWall(-12.5f, 0f, .5f, 25f);
			right = CreateModel.createWall(12.5f, 0f, .5f, 25f);
	}

	int i = 0;
	float dir = 1;
	
	@Override
	public void logicLoop() {
		laswrap.reflect();
		if (i < 80) {
			GameInstance.objectManager.moveModel(GameInstance.objectManager.indexOf(box1), 0.1f * dir, 0f, 0f); 	//Test animation of models, this pings the box back and forth
			i++;
		} else {
			
			i = 0;
			dir *= -1f;
		}
	}
	
	@Override
	public void renderLoop(){
		laswrap.render();
	}

}
