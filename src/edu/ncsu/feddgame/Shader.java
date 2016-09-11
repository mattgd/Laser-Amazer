package edu.ncsu.feddgame;

import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glBindAttribLocation;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

public class Shader {

	private int program, vs, fs; // Vertex and fragment shaders
	
	public Shader(String path) {
		program = glCreateProgram();
		
		// Vertex shader
		vs = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vs, readFile(path + ".vs"));
		glCompileShader(vs);
		
		if (glGetShaderi(vs, GL_COMPILE_STATUS) != 1) {
			System.err.println(glGetShaderInfoLog(vs)); // Print shader error
			System.exit(1);
		}
		
		// Fragment shader
		fs = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fs, readFile(path + ".fs"));
		glCompileShader(fs);
		
		// Check for shader error
		if (glGetShaderi(fs, GL_COMPILE_STATUS) != 1) {
			System.err.println(glGetShaderInfoLog(fs)); // Print shader error
			System.exit(1);
		}
		
		glAttachShader(program, vs);
		glAttachShader(program, fs);
		
		glBindAttribLocation(program, 0, "vertices");
		glBindAttribLocation(program, 1, "textures");
		
		glLinkProgram(program);
		// Check for link error
		if (glGetProgrami(program, GL_LINK_STATUS) != 1) {
			System.err.println(glGetProgramInfoLog(program));
			System.exit(1);
		}
		
		glValidateProgram(program);
		// Check for validate error
		if (glGetProgrami(program, GL_VALIDATE_STATUS) != 1) {
			System.err.println(glGetProgramInfoLog(program));
			System.exit(1);
		}
	}
	
	public void bind() {
		glUseProgram(program);
	}
	
	public void unbind() {
		glUseProgram(0);
	}
	
	private String readFile(String path) {
		StringBuilder sb = new StringBuilder();
		BufferedReader reader;
		
		try {
			reader = new BufferedReader(new FileReader(new File("./shaders/" + path)));
			String line;
			
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return sb.toString();
	}
	
	public void setUniform(String name, int value) {
		int location = glGetUniformLocation(program, name);
		
		if (location != -1)
			glUniform1i(location, value);
	}
	
	public void setUniform(String name, Matrix4f matrix) {
		int location = glGetUniformLocation(program, name);
		FloatBuffer buffer = BufferUtils.createFloatBuffer(4 * 4);
		
		matrix.get(buffer);
		
		if (location != -1)
			glUniformMatrix4fv(location, false, buffer);
	}
	
}
