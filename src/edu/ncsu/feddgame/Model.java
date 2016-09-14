package edu.ncsu.feddgame;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

/**
 * This class provides methods for binding objects
 * to the graphics shader and rendering them on screen.
 * This is done by providing vertex and index data for the
 * object. Also provides shader unbinding.
 */

public class Model {
	
	private int drawCount;
	private int vertexId;
	private int textureId;
	private int indexId;
	
	// Create and bind the vertices, texture coordinates, and indices
	// to the graphics shader.
	public Model(float[] vertices, float[] tCoords, int[] indices) {
		drawCount = indices.length;
		
		vertexId = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vertexId);
		glBufferData(GL_ARRAY_BUFFER, createFloatBuffer(vertices), GL_STATIC_DRAW);
		
		textureId = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, textureId);
		glBufferData(GL_ARRAY_BUFFER, createFloatBuffer(tCoords), GL_STATIC_DRAW);
		
		indexId = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexId);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, createIntBuffer(indices), GL_STATIC_DRAW);
		
		unbind();
	}
	
	public void render() {
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		
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
	
	private void unbind() {
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	// Used for vertex and texture coordinate data
	private FloatBuffer createFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		
		return buffer;
	}
	
	// Used for index data
	private IntBuffer createIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		
		return buffer;
	}
	
}
