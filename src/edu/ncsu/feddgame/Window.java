package edu.ncsu.feddgame;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;

public class Window {
	
	private long window;
	private long monitor;
	GLFWVidMode vidmode;
	
	private int windowWidth = 800, windowHeight = 600; 	//Store the window size
	
	private boolean mouseHeld;
	
	public Window(){
		try{ 	//Run stuff, quit on error
			setup();
			loop();
		}finally{
			glfwTerminate();
			System.exit(1);
		}
		
	}
	
	private void setup(){ 	//Setup all the window settings
		GLFWErrorCallback.createPrint(System.err).set();
		
		if (!glfwInit()) 	//Throw exception if glfw fails to initialize
			throw new IllegalStateException("Can not initialize GLFW");
		
		monitor = glfwGetPrimaryMonitor(); 	//Set the monitor to use
		
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); 	//Set window resizable and visible (set at defaults right now)
		glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
		
		vidmode = glfwGetVideoMode(monitor); //Get screen information
		
		window = glfwCreateWindow(windowWidth, windowHeight, "Game Name", 0, 0);
		
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> { 	//Key listener
			if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) 	//on Escape, set the window to close
				glfwSetWindowShouldClose(window, true);
		});
		
		glfwSetWindowSizeCallback(window, (window, width, height) -> { 	//Resize listener
			//System.out.println("resize" + width + " : " + height);
			glViewport(0,0,width, height); 	//Reset the viewport to the correct size
		});
		
		glfwSetMouseButtonCallback(window, (window, button, action, mods) -> { 	//Mouse click listener
			if (button == GLFW_MOUSE_BUTTON_LEFT){ 	//If left mouse button
				if (action == GLFW_RELEASE) 	//Set a boolean variable based on state of mouse (GLFW won't poll mouse state again if already pressed, need to manually store state)
					mouseHeld = false;
				else if (action == GLFW_PRESS)
					mouseHeld = true;
			}
		});
		
		glfwMakeContextCurrent(window);
		
		glfwSwapInterval(1); 	//set Vsync (swap the double buffer from drawn to displayed every refresh cycle)
	}
	
	private void loop(){ 	//Render Loop
		GL.createCapabilities();
		
		while(!glfwWindowShouldClose(window)){ 	//Poll window while window isn't about to close
			glfwPollEvents();
			//glClearColor(1, 1, 1, 1);
			glClear(GL_COLOR_BUFFER_BIT);
			
			renderBox(); 	//Test rendering of objects
			renderLine();
			
			glfwSwapBuffers(window); 	//Swap the render buffers
		}
	}
	
	private void renderBox(){
		glBegin(GL_QUADS);
		glColor4f(1,1,1,0);
		glVertex2f(-.1f, .1f);
		glVertex2f(.1f, .1f); 	//draw a white box in the center of the screen
		glVertex2f(.1f, -.1f);
		glVertex2f(-.1f, -.1f);
		glEnd();
	}
	private void renderLine(){
		if (!mouseHeld) 	//Continue if the mouse button is held
			return;
		glBegin(GL_LINES);
		glColor4f(0,1,0,0);
		glVertex2f(.5f, -.1f); 	//Draw a green vertical line
		glVertex2f(.5f, .1f);
		glEnd();
	}

}
