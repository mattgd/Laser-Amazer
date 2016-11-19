package edu.ncsu.feddgame.render;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.Vector2d;
import org.lwjgl.BufferUtils;

import edu.ncsu.feddgame.GameInstance;


public class LaserModel extends Model {
	
	private static final float LASER_WIDTH = .2f; // Width of the laser rendered
	
	private static Vector2d originV = new Vector2d(10, 0);
	private float angle; // Angle in radians
	private float coords[] = new float[2];
	
	public Vector2d vect;
	public int xDir = 0, yDir = 0;
	
	private static boolean renderGens = false;
	FloatBuffer vert;
	static FloatBuffer coors;
	static IntBuffer indec;
	static Texture tex;
	static int vertexId, textureId, indexId;
	/**
	 * New LaserModel given angle and length
	 * @param vertices
	 * @param tCoords
	 * @param indices
	 * @param x0
	 * @param y0
	 * @param x1
	 * @param y1
	 */
	LaserModel(float[] tCoords, int[] indices, float x0, float y0, float angle, float length) {
		super(getVertices(x0, y0, angle, length, LASER_WIDTH), tCoords, indices, 4, GameTexture.LASER.getPath());
		this.angle = angle;
		this.vect = new Vector2d(length * Math.cos(angle), length * Math.sin(angle));
		coords[0] = x0;
		coords[1] = y0;
		
		determineDirection();
	}
	
	/**
	 * New LaserModel given a vector
	 * @param tCoords
	 * @param indices
	 * @param x0
	 * @param y0
	 * @param vect
	 */
	LaserModel(float[] tCoords, int[] indices, float x0, float y0, Vector2d vect) {
		super(getVertices(x0, y0, (float)originV.angle(vect), (float)vect.length(), LASER_WIDTH), tCoords, indices, 4, GameTexture.LASER.getPath());
		this.angle = (float)originV.angle(vect);
		this.vect = vect;
		coords[0] = x0;
		coords[1] = y0;
		
		determineDirection();
	}
	
	/**
	 * Returns the coordinates for vertices of a rectangle extruded from the line passed of width w
	 * @param begX
	 * @param begY
	 * @param endX
	 * @param endY
	 * @param LASER_WIDTH
	 * @return
	 */
	private static float[] getVertices(float begX, float begY, float angle, float length, float width) {
		length += .05f;
		
		float endX = length * (float)Math.cos(angle) + begX;
		float endY = length * (float)Math.sin(angle) + begY;
		float dy = endY - begY;
		float dx = endX - begX;
		float xS = (width * dy / length) / 2;
		float yS = (width * dx / length) / 2;
		
		float[] vertices = new float[] {
				begX - xS, begY + yS, 0f,
				begX + xS, begY - yS, 0f,
				endX + xS, endY - yS, 0f,
				endX - xS, endY + yS, 0f,
		};
		
		return vertices;
	}
	
	/**
	 * @return float value of the angle of the laser line
	 */
	public float getAngle() {
		return this.angle;
	}
	
	/**
	 * Sets the initial angle of the laser
	 * @param a
	 */
	public void setAngle(float a){
		this.angle = a;
		this.vect = new Vector2d(.1f * Math.cos(a), .1f * Math.sin(a));
		determineDirection();
	}
	
	/**
	 * Sets a value of 1 for positive, -1 for negative for the vector directions of the laser
	 */
	private void determineDirection() {
		// Y
		if (Math.sin(angle) >= 0) {
			yDir = 1;
		} else {
			yDir = -1;
		}
		
		// X
		if (Math.cos(angle) >= 0) {
			xDir = 1;
		} else {
			xDir = -1;
		}
	}
	
	public void setLength(float length) {
		this.vertices = getVertices(coords[0], coords[1], angle, length, LASER_WIDTH);
	}
	
	public void setVector(Vector2d vect) {
		this.vect = vect;
		this.angle = (float)originV.angle(vect);
	}
	
	/**
	 * Returns the coordinates of the line from which the laser is generated
	 * @return
	 */
	public float[] getCoords() {
		return this.coords;
	}
	
	/**
	 * Sets the starting coordinates of the laser
	 * @param f
	 */
	public void setCoords(float[] f) {
		this.coords = f;
	}
	
	@Override
	public void render() {
		
		if (!renderGens) {
			tex = new Texture(texStr);
			vertexId = glGenBuffers();
			textureId = glGenBuffers();
			indexId = glGenBuffers();
			renderGens = true;
			coors = createBuffer(tCoords);
			indec = BufferUtils.createIntBuffer(indices.length);
			indec.put(indices);
			indec.flip();
		}
		
		newv = vertices.clone();
		for (int i = 0; i < this.sideCount; i++){
			newv[i * 3] /= GameInstance.window.ratio;
		}
		vert = createBuffer(newv);
		
		glBindBuffer(GL_ARRAY_BUFFER, vertexId);
		glBufferData(GL_ARRAY_BUFFER, vert, GL_STATIC_DRAW);
		
		
		glBindBuffer(GL_ARRAY_BUFFER, textureId);
		glBufferData(GL_ARRAY_BUFFER, coors, GL_STATIC_DRAW);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexId);
		
		
		
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indec, GL_STATIC_DRAW);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		tex.bind(0);
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);

		glBindBuffer(GL_ARRAY_BUFFER, vertexId);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		
		glBindBuffer(GL_ARRAY_BUFFER, textureId);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexId);
		glDrawElements(GL_TRIANGLES, drawCount, GL_UNSIGNED_INT, 0);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);

		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		tex.unbind();
		
	}

}
