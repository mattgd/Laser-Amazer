package edu.ncsu.feddgame;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;

public class Window {
	
	private long window;
	private long monitor;
	
	public Window(){
		try{ 	//Run stuff, quit on error
			setup();
			loop();
		}finally{
			glfwTerminate();
		}
		
	}
	
	private void setup(){ 	//Setup all the window settings
		GLFWErrorCallback.createPrint(System.err).set();
		
		if (!glfwInit()) 	//Throw exception if glfw fails to initialize
			throw new IllegalStateException("Can not initialize GLFW");
		
		monitor = glfwGetPrimaryMonitor(); 	//Set the monitor to use
		
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); 	//Set window resizable and visible (set at defaults right now)
		glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
		
		GLFWVidMode vidmode = glfwGetVideoMode(monitor); 	//Get screen information
		
		window = glfwCreateWindow(vidmode.width(), vidmode.height(), "Game Name", monitor, 0);
		
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> { 	//Key listener
			if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) 	//on Escape, set the window to close
				glfwSetWindowShouldClose(window, true);
		});
		
		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);
	}
	
	private void loop(){ 	//Render Loop (ish)
		GL.createCapabilities();
		
		while(!glfwWindowShouldClose(window)){ 	//Poll window while window isn't about to close
			glfwPollEvents();
		}
	}
}
