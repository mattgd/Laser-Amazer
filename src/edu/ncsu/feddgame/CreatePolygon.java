package edu.ncsu.feddgame;

public class CreatePolygon {
	/**
	 * Creates a Box model and stores it in the objectManager of the GameInstance
	 * @param xOffset
	 * @param yOffset
	 */
	public static void createBox(float xOffset,float yOffset){
		// Vertices for a quadrilateral
				float[] vertices = new float[] {
					-0.5f + xOffset, 0.5f + yOffset, 0, // TOP LEFT - 0
					0.5f + xOffset, 0.5f + yOffset, 0, // TOP RIGHT - 1
					0.5f + xOffset, -0.5f + yOffset, 0, // BOTTOM RIGHT - 2
					-0.5f + xOffset, -0.5f + yOffset, 0, // BOTTOM LEFT - 3
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
		 GameInstance.objectManager.addModel(new Model(vertices, texture, indices)); 	//Add the model to the objectManager
	}

}
