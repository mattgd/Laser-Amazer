package edu.ncsu.feddgame.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import edu.ncsu.feddgame.GameInstance;

public class Model {
	
	private int drawCount, vertexId, textureId, indexId;
	public int sideCount;
	private boolean generated = false;
	public float[] vertices, tCoords;
	private int[] indices;
	private Texture tex;
	private String texStr;
	protected float xOffset, yOffset;
	private float[] newv;
	
	FloatBuffer vert, coords;
	IntBuffer indec;
	
	private String defaultTexString = "bgtile.png";
	/**
	 * Create and bind the vertices, texture coordinates, and indices to the graphics shader.
	 * @param vertices
	 * @param tCoords
	 * @param indices
	 * @param texture
	 */
	public Model(float[] vertices, float[] tCoords, int[] indices, float xOffset, float yOffset, int sideNum, String texture) {
		this.indices = indices;
		this.tCoords = tCoords;
		this.vertices = vertices;
		this.sideCount = sideNum;
		drawCount = indices.length;
		this.texStr = texture;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	public Model(float[] vertices, float[] tCoords, int[] indices, int sideNum, String texture) {
		this.indices = indices;
		this.tCoords = tCoords;
		this.vertices = vertices;
		this.sideCount = sideNum;
		drawCount = indices.length;
		this.texStr = texture;
	}
	
	/**
	 * Create and bind the vertices, texture coordinates, and indices with the default texture
	 * @param vertices
	 * @param tCoords
	 * @param indices
	 */
	public Model(float[] vertices, float[] tCoords, int[] indices, float xOffset, float yOffset, int sideNum) {
		this.indices = indices;
		this.tCoords = tCoords;
		this.vertices = vertices;
		this.sideCount = sideNum;
		drawCount = indices.length;
		this.texStr = defaultTexString;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	public Model(float[] vertices, float[] tCoords, int[] indices, int sideNum) {
		this.indices = indices;
		this.tCoords = tCoords;
		this.vertices = vertices;
		this.sideCount = sideNum;
		drawCount = indices.length;
		this.texStr = defaultTexString;
	}
	
	protected void finalize() throws Throwable {
		glDeleteBuffers(vertexId);
		glDeleteBuffers(textureId);
		glDeleteBuffers(indexId);
	}
	
	/**
	 * Render all of the vertices on screen.
	 */
	public void render() {
		if (!generated) {
			tex = new Texture(texStr);
			vertexId = glGenBuffers();
			textureId = glGenBuffers();
			indexId = glGenBuffers();
			generated = true;
			vert = createBuffer(vertices);
			coords = createBuffer(tCoords);
			indec = BufferUtils.createIntBuffer(indices.length);
			indec.put(indices);
			indec.flip();
		}
		
		newv = vertices.clone();
		//System.out.println(ratio);
		for (int i = 0; i < this.sideCount; i++){
			newv[i * 3] /= GameInstance.window.ratio;
		}
		updateBuffer(vert, newv);
		glBindBuffer(GL_ARRAY_BUFFER, vertexId);
		glBufferData(GL_ARRAY_BUFFER, vert, GL_STATIC_DRAW);
		
		
		glBindBuffer(GL_ARRAY_BUFFER, textureId);
		glBufferData(GL_ARRAY_BUFFER, coords, GL_STATIC_DRAW);
		
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
	
	/**
	 * @param data
	 * @return buffer array of vertex or texture coordinate data
	 * in the correct orientation.
	 */
	private FloatBuffer createBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		
		return buffer;
	}
	private void updateBuffer(FloatBuffer b, float[] data){
		b.clear();
		b.put(data);
		b.flip();
	}
	
	/**
	 * Adjusts the vertices of the model by the x, y, and z values passed
	 * @param x
	 * @param y
	 * @param z
	 */
	public void move(float x, float y, float z){ 	//Moves the vertices by x, y, and z
		for (int i = 0; i < this.sideCount; i++){
			this.vertices[i * 3] += x;
		}
		for (int i = 0; i < this.sideCount; i++){
			this.vertices[i * 3 + 1] += y;
		}
		for (int i = 0; i < this.sideCount; i++){
			this.vertices[i * 3 + 2] += z;
		}
	}
	
	public void setVertices(float[] vertices){
		this.vertices = vertices;
	}
	/**
	 * Rotates by the passed angle in radians
	 * @param angle
	 */
	public void rotate(float angle){
			for (int i = 0; i < sideCount; i++){
				float newX = (float) (xOffset + (this.vertices[i * 3]-xOffset)*Math.cos(angle) - (this.vertices[i * 3 + 1]-yOffset)*Math.sin(angle));
				float newY = (float) (yOffset + (this.vertices[i * 3]-xOffset)*Math.sin(angle) + (this.vertices[i * 3 + 1]-yOffset)*Math.cos(angle));
				vertices[i * 3] = newX;
				vertices[i * 3 + 1] = newY;
			}
	}
	
}