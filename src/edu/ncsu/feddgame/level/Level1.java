package edu.ncsu.feddgame.level;

import edu.ncsu.feddgame.render.CreateModel;

public class Level1 implements ILevel{

	@Override
	public void renderObjects() {
		{ 	//Walls
			CreateModel.createWall(0f, 10f, 20f, .5f);
			CreateModel.createWall(0f, -10f, 20f, .5f);
			CreateModel.createWall(-10f, 0f, .5f, 20f);
			CreateModel.createWall(10f, 0f, .5f, 20f);
		}
		
	}

	@Override
	public void logicLoop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void renderLoop() {
		// TODO Auto-generated method stub
		
	}

}
