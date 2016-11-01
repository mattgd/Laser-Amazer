package edu.ncsu.feddgame.level;

import edu.ncsu.feddgame.render.CreateModel;
import edu.ncsu.feddgame.render.LaserStart;
import edu.ncsu.feddgame.render.LaserStop;
import edu.ncsu.feddgame.render.Model;
import edu.ncsu.feddgame.render.MovableModel;
import edu.ncsu.feddgame.render.Wall;

public class TestLevel implements ILevel{

	Model box1, tri1;
	MovableModel box2;
	Wall top, bottom, left, right;
	LaserStart laswrap;
	LaserStop lasstop;
	
	@Override
	public void renderObjects() {
		laswrap = CreateModel.createLaserStart(7.5f, 9, 3, Math.toRadians(251));
		lasstop = CreateModel.createLaserStop(3, -9f);
		box1 = CreateModel.createBox(0,0);
		box2 = CreateModel.createMovableBox(4.05f, -5.925f);
		CreateModel.createMovableTrapezoid(-.9f, 5.8f, 1.5f, 1, 1);
		CreateModel.createBox(3.4f, -8);
		tri1 = CreateModel.createTriangle(-4, -4, -1, -2);
		box1.rotate((float)Math.toRadians(45));
		{ 	//Walls
			top = CreateModel.createWall(0f, 10f, 20f, .5f);
			bottom = CreateModel.createWall(0f, -10f, 20f, .5f);
			left = CreateModel.createWall(-10f, 0f, .5f, 20f);
			right = CreateModel.createWall(10f, 0f, .5f, 20f);
		}
		tri1.rotate(.3f);
	}

	int i = 0;
	float dir = 1;
	
	@Override
	public void logicLoop() {
		laswrap.reflect();
			if (i < 160) {
				box1.move(0.05f * dir, 0f, 0f); 	//Test animation of models, this pings the box back and forth
				
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
