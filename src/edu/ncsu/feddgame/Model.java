package edu.ncsu.feddgame;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public class Model {
	
	private int drawCount;
	private int vertexId;
	private int textureId;
	private int indexId;
	public float[] vertices;
	
	/**
	 * Create and bind the vertices, texture coordinates, and indices to the graphics shader.
	 * @param vertices
	 * @param tCoords
	 * @param indices
	 */
	public Model(float[] vertices, float[] tCoords, int[] indices) {
		drawCount = indices.length;
		this.vertices = vertices;
		
		bindVertices(this.vertices);
		
		textureId = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, textureId);
		glBufferData(GL_ARRAY_BUFFER, createFloatBuffer(tCoords), GL_STATIC_DRAW);
		
		indexId = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexId);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, createIntBuffer(indices), GL_STATIC_DRAW);
		
		unbind();
	}
	
	private void bindVertices(float[] v){
		vertexId = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vertexId);
		glBufferData(GL_ARRAY_BUFFER, createFloatBuffer(vertices), GL_STATIC_DRAW);
		unbind();
	}
	/**
	 * Adjusts the verices of the model by the values passed
	 * @param x
	 * @param y
	 * @param z
	 */
	public void move(float x, float y, float z){ 	//Moves the vertices by 
		this.vertices[0] += x;
		this.vertices[3] += x;
		this.vertices[6] += x;
		this.vertices[9] += x;
		this.vertices[1] += y;
		this.vertices[4] += y;
		this.vertices[7] += y;
		this.vertices[10] += y;
		this.vertices[2] += z;
		this.vertices[5] += z;
		this.vertices[8] += z;
		this.vertices[11] += z;
		
	}
	
	/**
	 * Render all of the vertices on screen.
	 */
	public void render() {
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		
		bindVertices(this.vertices);
		
		glBindBuffer(GL_ARRAY_BUFFER, vertexId);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		
		glBindBuffer(GL_ARRAY_BUFFER, textureId);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexId);
		glDrawElements(GL_TRIANGLES, drawCount, GL_UNSIGNED_INT, 0);
		
		unbind();
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
	}
	
	/**
	 * Unbind all of the vertices, texture coordinates,
	 * and indices from the graphics shader.
	 */
	private void unbind() {
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	/**
	 * @param data
	 * @return buffer array of vertex or texture coordinate data
	 * in the correct orientation.
	 */
	private FloatBuffer createFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		
		return buffer;
	}
	
	/**
	 * @param data
	 * @return buffer array of index data in the correct orientation.
	 */
	private IntBuffer createIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		
		return buffer;
	}
	
}