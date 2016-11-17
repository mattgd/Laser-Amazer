package edu.ncsu.feddgame.render;

import org.joml.Vector2d;

import edu.ncsu.feddgame.GameInstance;
import edu.ncsu.feddgame.LaserWrapper;

public class CreateModel {
	
	/**
	 * Creates a Box model and stores it in the objectManager of the GameInstance
	 * @param xOffset
	 * @param yOffset
	 */
	public static Model createBox(float xOffset, float yOffset, float size) {
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
		
		return GameInstance.objectManager.addModel(new Model(vertices, texture, indices, xOffset, yOffset, 4, GameTexture.UNMOVEABLE_BOX.getPath())); 	//Add the model to the objectManager
	}
	
	public static Model createBox(float xOffset, float yOffset) {
		return createBox(xOffset, yOffset, 1); 	// Default size of 1
	}
	
	public static Model createTriangle(float xOffset, float yOffset, float xSide, float ySide) {
		// Right Triangle
		float[] vertices = new float[] {
				-xSide/2f + xOffset, ySide/2f + yOffset, 0, // TOP LEFT - 0
				xSide/2f + xOffset, ySide/2f + yOffset, 0, // TOP RIGHT - 1
				xSide/2f + xOffset, -ySide/2f + yOffset, 0, // BOTTOM - 2
		};
				
		float[] texture = new float[] {
				0, 0, // TOP LEFT
				1, 0, // TOP RIGHT
				1, 1, // BOTTOM RIGHT
		};
		
		int[] indices = new int[] {
				0, 1, 2,
				2
		};
		
		return GameInstance.objectManager.addModel(new Model(vertices, texture, indices, xOffset, yOffset, 3, GameTexture.WALL.getPath()));
	}
	
	public static MovableModel createMovableTriangle(float xOffset, float yOffset, float xSide, float ySide) {
		xOffset += 4 - xSide / 6f;
		yOffset -= 2 + ySide / 6f;
		
		// Right Triangle
		float[] vertices = new float[] {
				-xSide/2f + xOffset, ySide/2f + yOffset, 0, // TOP LEFT - 0
				xSide/2f + xOffset, ySide/2f + yOffset, 0, // TOP RIGHT - 1
				xSide/2f + xOffset, -ySide/2f + yOffset, 0, // BOTTOM - 2
		};
				
		float[] texture = new float[] {
				0, 0, // TOP LEFT
				1, 0, // TOP RIGHT
				1, 1, // BOTTOM RIGHT
		};
		
		int[] indices = new int[] {
				0, 1, 2
		};
		
		return (MovableModel) GameInstance.objectManager.addModel(new MovableModel(vertices, texture, indices, xOffset, yOffset, 3));
	}
	
	/**
	 * Creates a movable box model and stores it in the objectManager
	 * @param xOffset
	 * @param yOffset
	 * @param size
	 * @return
	 */
	public static MovableModel createMovableBox(float xOffset, float yOffset, float size) {
		// Vertices for a quadrilateral
		float[] vertices = new float[] {
			-size/2f, size/2f, 0, // TOP LEFT - 0
			size/2f, size/2f, 0, // TOP RIGHT - 1
			size/2f, -size/2f, 0, // BOTTOM RIGHT - 2
			-size/2f, -size/2f, 0, // BOTTOM LEFT - 3
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
		
		return (MovableModel)GameInstance.objectManager.addModel(new MovableModel(vertices, texture, indices, xOffset, yOffset, 4));
	}
	
	public static MovableModel createMovableBox(float xOffset, float yOffset) {
		return createMovableBox(xOffset, yOffset, 1);
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
	public static Model createTrapezoid(float xOffset, float yOffset, float topBase, float bottomBase, float height) {
		// Vertices for a trapezoid
		float[] vertices = new float[] {
				-topBase / 2f + xOffset, height / 2f + yOffset, 0, // TOP LEFT - 0
				topBase / 2f + xOffset, height / 2f + yOffset, 0, // TOP RIGHT - 1
				bottomBase / 2f + xOffset, -height / 2f + yOffset, 0, // BOTTOM RIGHT - 2
				-bottomBase / 2f + xOffset, -height / 2f + yOffset, 0, // BOTTOM LEFT - 3
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
		
		return GameInstance.objectManager.addModel(new Model(vertices, texture, indices, xOffset, yOffset, 4, GameTexture.MOVEABLE_BOX.getPath())); 	//Add the model to the objectManager
	}
	
	public static MovableModel createMovableTrapezoid(float xOffset, float yOffset, float topBase, float bottomBase, float height) {
		// Vertices for a trapezoid
		float[] vertices = new float[] {
				-topBase/2f, height/2f, 0, // TOP LEFT - 0
				topBase/2f, height/2f, 0, // TOP RIGHT - 1
				bottomBase/2f, -height/2f, 0, // BOTTOM RIGHT - 2
				-bottomBase/2f, -height/2f, 0, // BOTTOM LEFT - 3
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
		
		return (MovableModel)GameInstance.objectManager.addModel(new MovableModel(vertices, texture, indices, xOffset, yOffset, 4)); 	//Add the model to the objectManager
	}
	
	public static Wall createWall(float xOffset, float yOffset, float width, float height) {
		// Vertices for a trapezoid
		float[] vertices = new float[] {
				-width/2f + xOffset, height/2f + yOffset, 0, // TOP LEFT - 0
				width/2f + xOffset, height/2f + yOffset, 0, // TOP RIGHT - 1
				width/2f + xOffset, -height/2f + yOffset, 0, // BOTTOM RIGHT - 2
				-width/2f + xOffset, -height/2f + yOffset, 0, // BOTTOM LEFT - 3
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
		
		Wall m = new Wall(vertices, texture, indices);
		GameInstance.objectManager.addModel(m);
		
		return m;
	}
	
	private static LaserModel createLaser(float begX, float begY, double angle, float length) {
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
		
		return new LaserModel(texture, indices, begX, begY, (float)angle, length); 	//Add the model to the objectManager
	}
	
	public static LaserWrapper newLaser(float begX, float begY, double angle, float length){
		return new LaserWrapper(createLaser(begX, begY, angle - (float)Math.PI/2f, length));
	}
	
	/**
	 * Creates a LaserModel object and passes it to the lasers list of the objectManager
	 * This is used for all reflected lasers to avoid calculating redundant reflections on lasers
	 * @param begX
	 * @param begY
	 * @param vect
	 * @return
	 */
	public static LaserModel createReflectedLaser(float begX, float begY, Vector2d vect){
		
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
		
		return new LaserModel(texture, indices, begX, begY, vect); 	//Return the new Laser
	}
	
	/**
	 * Creates the Laser starting block
	 * @param xOffset
	 * @param yOffset
	 * @param side
	 * @param angle
	 * @return
	 */
	public static LaserStart createLaserStart(float xOffset, float yOffset, int side, double angle) {
		float width = 1, height = 1;
		float[] vertices = new float[] {
				-width/2f + xOffset, height/2f + yOffset, 0, // TOP LEFT - 0
				width/2f + xOffset, height/2f + yOffset, 0, // TOP RIGHT - 1
				width/2f + xOffset, -height/2f + yOffset, 0, // BOTTOM RIGHT - 2
				-width/2f + xOffset, -height/2f + yOffset, 0, // BOTTOM LEFT - 3
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

		LaserStart l = new LaserStart(vertices, texture, indices, xOffset, yOffset, newLaser(xOffset, yOffset - height/2f, angle, .1f));
		l.rotate((float)Math.toRadians(-90 - 90 * side));
		GameInstance.objectManager.addModel(l);
		
		return l;
	}
	
	/**
	 * Creates the Laser starting block
	 * @param xOffset
	 * @param yOffset
	 * @param side
	 * @param angle
	 * @return
	 */
	public static LaserStart createLaserStart(float xOffset, float yOffset, int side) {
		float width = 1, height = 1;
		float[] vertices = new float[] {
				-width/2f + xOffset, height/2f + yOffset, 0, // TOP LEFT - 0
				width/2f + xOffset, height/2f + yOffset, 0, // TOP RIGHT - 1
				width/2f + xOffset, -height/2f + yOffset, 0, // BOTTOM RIGHT - 2
				-width/2f + xOffset, -height/2f + yOffset, 0, // BOTTOM LEFT - 3
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

		LaserStart l = new LaserStart(vertices, texture, indices, xOffset, yOffset, newLaser(xOffset, yOffset - height/2f, 0, .1f));
		l.rotate((float)Math.toRadians(-90 - 90 * side));
		GameInstance.objectManager.addModel(l);
		
		return l;
	}
	
	/**
	 * Creates the laser reciever block
	 * @param xOffset
	 * @param yOffset
	 * @return
	 */
	public static LaserStop createLaserStop(float xOffset, float yOffset){
		int size = 1;
		
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
		
		LaserStop l = new LaserStop(vertices, texture, indices, xOffset, yOffset);
		GameInstance.objectManager.addModel(l);
		
		return l;
	}

}
