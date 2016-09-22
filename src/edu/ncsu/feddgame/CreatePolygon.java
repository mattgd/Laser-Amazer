package edu.ncsu.feddgame;

public class CreatePolygon {
	
	private static final float w = .25f; 	//Width of the laser rendered
	
	/**
	 * Creates a Box model and stores it in the objectManager of the GameInstance
	 * @param xOffset
	 * @param yOffset
	 */
	public static int createBox(float xOffset,float yOffset, float size){
		// Vertices for a quadrilateral
		float[] vertices = new float[] {
			-size/2f + xOffset, size/2f + yOffset, 0, // TOP LEFT - 0
			size/2f + xOffset, size/2f + yOffset, 0, // TOP RIGHT - 1
			size/2f + xOffset, -size/2f + yOffset, 0, // BOTTOM RIGHT - 2
			-size/2f + xOffset, -size/2f + yOffset, 0, // BOTTOM LEFT - 3
		};
		
		float[] texture = new float[] {
			0, 0, // TOP LEFT
			1, 0, // TOP RIGHT
			1, 1, // BOTTOM RIGHT
			0, 1, // BOTTOM LEFT
		};
		
		int[] indices = new int[] {
				0, 1, 2,
				2, 3, 0
				};
		return GameInstance.objectManager.addModel(new Model(vertices, texture, indices)); 	//Add the model to the objectManager
	}
	public static int createBox(float xOffset, float yOffset){
		return createBox(xOffset, yOffset, 1); 	//default size of 1
	}
	
	/**
	 * Creates a trapezoid with the bases on top and bottom, and a vertical height between them
	 * @param xOffset
	 * @param yOffset
	 * @param topBase
	 * @param bottomBase
	 * @param height
	 * @return
	 */
	public static int createTrapezoid(float xOffset, float yOffset, float topBase, float bottomBase, float height){
		// Vertices for a trapezoid
		float[] vertices = new float[] {
			-topBase/2f + xOffset, height + yOffset, 0, // TOP LEFT - 0
			topBase/2f + xOffset, height + yOffset, 0, // TOP RIGHT - 1
			bottomBase/2f + xOffset, -height + yOffset, 0, // BOTTOM RIGHT - 2
			-bottomBase/2f + xOffset, -height + yOffset, 0, // BOTTOM LEFT - 3
		};
		
		float[] texture = new float[] {
			0, 0, // TOP LEFT
			1, 0, // TOP RIGHT
			1, 1, // BOTTOM RIGHT
			0, 1, // BOTTOM LEFT
		};
		
		int[] indices = new int[] {
				0, 1, 2,
				2, 3, 0
		};
		return GameInstance.objectManager.addModel(new Model(vertices, texture, indices)); 	//Add the model to the objectManager
	}
	
	/**
	 * Creates a laser with start and end points passed
	 * @param begX
	 * @param begY
	 * @param endX
	 * @param endY
	 * @return
	 */
	public static int createLaser(float begX, float begY, float endX, float endY){
		
		float[] texture = new float[] {
			0, 0,
			1, 0,
			1, 1,
			0, 1,
		};
		
		int[] indices = new int[] {
				0, 1, 2,
				2, 3, 0
		};
		
		return GameInstance.objectManager.addModel(new LaserModel(texture, indices, begX, begY, endX, endY, w)); 	//Add the model to the objectManager
	}

}
