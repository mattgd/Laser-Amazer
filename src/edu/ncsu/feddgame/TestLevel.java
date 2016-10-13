package edu.ncsu.feddgame;

public class TestLevel implements ILevel{

	Model box1, laser1, laser2;
	
	@Override
	public void renderObjects() {
		box1 = CreatePolygon.createBox(0,0);
		CreatePolygon.createBox(2f, -6);
		CreatePolygon.createTrapezoid(-10f, 5f, 2f, 1, 1);
		CreatePolygon.createBox(4f, -9);
		laser1 = CreatePolygon.createLaser(-8.7f, 2, Math.toRadians(100), 1);
		laser2 = CreatePolygon.createLaser(8f, 10, Math.toRadians(251), 1);
	}

	int i = 0;
	float dir = 1;
	
	@Override
	public void logicLoop() {
		if (i < 80) {
			GameInstance.objectManager.moveModel(GameInstance.objectManager.indexOf(box1), 0.1f * dir, 0f, 0f); 	//Test animation of models, this pings the box back and forth
			i++;
		} else {
			i = 0;
			dir *= -1f;
		}
	}

}
