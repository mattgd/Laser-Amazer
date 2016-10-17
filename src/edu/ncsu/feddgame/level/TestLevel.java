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
		CreateModel.createTrapezoid(5.5f, 0.1f, 1.5f, 1, 1);
		CreateModel.createBox(3.4f, -8);
		laswrap = CreateModel.createLaserStart(7.5f, 9, 3, Math.toRadians(251));
		lasstop = CreateModel.createLaserStop(3, -9f);
		{ 	//Walls
			top = CreateModel.createWall(0f, 10f, 25f, .5f);
			bottom = CreateModel.createWall(0f, -10f, 25f, .5f);
			left = CreateModel.createWall(-10f, 0f, .5f, 25f);
			right = CreateModel.createWall(10f, 0f, .5f, 25f);
		}
	}

	int i = 0;
	float dir = 1;
	
	@Override
	public void logicLoop() {
		laswrap.reflect();
		if (GameInstance.window.isMouseHeld()){
			if (i < 160) {
				GameInstance.objectManager.moveModel(GameInstance.objectManager.indexOf(box1), 0.05f * dir, 0f, 0f); 	//Test animation of models, this pings the box back and forth
				i++;
			} else {
				
				i = 0;
				dir *= -1f;
			}
		}
	}
	
	@Override
	public void renderLoop(){
		laswrap.render();
	}

}
