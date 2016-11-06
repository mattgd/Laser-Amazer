package edu.ncsu.feddgame.level;

import edu.ncsu.feddgame.render.CreateModel;
import edu.ncsu.feddgame.render.LaserStart;
import edu.ncsu.feddgame.render.Model;

public class Level1 implements ILevel {
	LaserStart laswrap;
	Model m;

	@Override
	public void renderObjects() {
		{ // Walls
			CreateModel.createWall(0f, 10f, 20f, .5f);
			CreateModel.createWall(0f, -10f, 20f, .5f);
			CreateModel.createWall(-10f, 0f, .5f, 20f);
			CreateModel.createWall(10f, 0f, .5f, 20f);
			CreateModel.createWall(0, 1f, .25f, 18f);
		}
		laswrap = CreateModel.createLaserStart(-10f, -1f, 2, Math.toRadians(-45));
		// CreateModel.createLaserStop(10, 7);
		m = CreateModel.createMovableBox(4, 0);
		CreateModel.createMovableBox(2, 4);
		m.rotate(-.5f);

	}

	@Override
	public void logicLoop() {
		laswrap.reflect();

	}

	@Override
	public void renderLoop() {
		laswrap.render();
	}

	@Override
	public String getName() {
		return "Level 1";
	}

}
