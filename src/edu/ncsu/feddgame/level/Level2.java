package edu.ncsu.feddgame.level;

import java.util.Random;

import edu.ncsu.feddgame.render.CreateModel;
import edu.ncsu.feddgame.render.LaserStart;
import edu.ncsu.feddgame.render.Model;

public class Level2 implements ILevel {
	
	LaserStart laswrap;
	Model m;

	@Override
	public void renderObjects() {
		{ // Walls
			// Outer bounds
			CreateModel.createWall(0f, 10f, 20f, .5f);
			CreateModel.createWall(0f, -10f, 20f, .5f);
			CreateModel.createWall(-10f, 0f, .5f, 20f);
			CreateModel.createWall(10f, 0f, .5f, 20f);
			
			// Inner bounds
			CreateModel.createWall(-5f, 2f, .25f, 18f);
			CreateModel.createWall(2f, -2f, .25f, 16f);
		}
		laswrap = CreateModel.createLaserStart(-10f, -1f, 2, Math.toRadians(-45));
		CreateModel.createLaserStop(10, 7);

		Model model;
		for (int i = 0; i < 7; i++) {
			int x = randomInt(1, 9);
			int y = randomInt(2, 8);
			
			model = CreateModel.createMovableBox(x, y);
			
			boolean rotate = false;
			if (Math.random() < 0.5) rotate = true;
			
			if (rotate)
				model.rotate((float) Math.random());
		}
	}
	
	public int randomInt(int min, int max) {
		return new Random().nextInt(max - min + 1) + min;
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
		return "Level 2";
	}

}
