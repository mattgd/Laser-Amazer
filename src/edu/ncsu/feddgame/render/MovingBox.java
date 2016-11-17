package edu.ncsu.feddgame.render;

public class MovingBox {

	private Model model;
	private int distance = 0, maxDistance;
	private float velocity = 1f;
	
	public MovingBox(float x, float y, int maxDistance) {
		this.model = CreateModel.createBox(x, y);
		this.maxDistance = maxDistance;
	}
	
	public MovingBox(float x, float y, int maxDistance, int distance, float velocity) {
		this.model = CreateModel.createBox(x, y);
		this.maxDistance = maxDistance;
		this.distance = distance;
		this.velocity = velocity;
	}
	
	public void logicLoop() {
		if (distance < maxDistance) {
			model.move(0.05f * velocity, 0f, 0f); // Moves the box back and forth
			distance++;
		} else {
			distance = 0;
			velocity *= -1f;
		}
	}
	
	public void rotate(float degrees) {
		model.rotate((float) Math.toRadians(degrees));
	}
	
}
