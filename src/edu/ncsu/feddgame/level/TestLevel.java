package edu.ncsu.feddgame.level;

import edu.ncsu.feddgame.GameInstance;
import edu.ncsu.feddgame.LaserWrapper;
import edu.ncsu.feddgame.render.CreatePolygon;
import edu.ncsu.feddgame.render.Model;
import edu.ncsu.feddgame.render.Wall;

public class TestLevel implements ILevel{

	Model box1;
	Wall top, bottom, left, right;
	LaserWrapper laswrap;
	
	@Override
	public void renderObjects() {
		box1 = CreatePolygon.createBox(0,0);
		CreatePolygon.createBox(2f, -6);
		CreatePolygon.createTrapezoid(-10f, 5f, 2f, 1, 1);
		CreatePolygon.createBox(4f, -9);
		laswrap = CreatePolygon.newLaser(8, 10, Math.toRadians(251), 1);
		top = CreatePolygon.createWall(0f, 12.5f, 25f, .5f);
		bottom = CreatePolygon.createWall(0f, -12.5f, 25f, .5f);
		left = CreatePolygon.createWall(-12.5f, 0f, .5f, 25f);
		right = CreatePolygon.createWall(12.5f, 0f, .5f, 25f);
	}

	int i = 0;
	float dir = 1;
	
	@Override
	public void logicLoop() {
		laswrap.runReflections();
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
