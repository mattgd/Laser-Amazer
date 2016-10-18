package edu.ncsu.feddgame.render;

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
	private boolean generated = false;
	public float[] vertices;
	private float[] tCoords;
	private int[] indices;
	private Texture tex;
	private String texStr;
	
	private String defaultTexString = "box.png";
	/**
	 * Create and bind the vertices, texture coordinates, and indices to the graphics shader.
	 * @param vertices
	 * @param tCoords
	 * @param indices
	 * @param texture
	 */
	public Model(float[] vertices, float[] tCoords, int[] indices, String texture) {
		this.indices = indices;
		this.tCoords = tCoords;
		this.vertices = vertices;
		drawCount = indices.length;
		this.texStr = texture;
	}
	
	/**
	 * Create and bind the vertices, texture coordinates, and indices with the default texture
	 * @param vertices
	 * @param tCoords
	 * @param indices
	 */
	public Model(float[] vertices, float[] tCoords, int[] indices){
		this.indices = indices;
		this.tCoords = tCoords;
		this.vertices = vertices;
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
		//System.out.println("red");
		
		if (!generated) {
			tex = new Texture(texStr);
			glDeleteBuffers(vertexId);
			glDeleteBuffers(textureId);
			glDeleteBuffers(indexId);
			vertexId = glGenBuffers();
			glBindBuffer(GL_ARRAY_BUFFER, vertexId);
			glBufferData(GL_ARRAY_BUFFER, createBuffer(vertices), GL_STATIC_DRAW);
			
			textureId = glGenBuffers();
			glBindBuffer(GL_ARRAY_BUFFER, textureId);
			glBufferData(GL_ARRAY_BUFFER, createBuffer(tCoords), GL_STATIC_DRAW);
			
			indexId = glGenBuffers();
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexId);
			
			IntBuffer buffer = BufferUtils.createIntBuffer(indices.length);
			buffer.put(indices);
			buffer.flip();
			
			glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);

			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
			glBindBuffer(GL_ARRAY_BUFFER, 0);
			generated = false;
		}
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
	
	/**
	 * Adjusts the vertices of the model by the values passed
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
	
}