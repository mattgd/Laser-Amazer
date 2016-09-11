package edu.ncsu.feddgame;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glViewport;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;

public class Window {

	private long window;
	private int width, height;
	private boolean fullscreen, mouseHeld = false;
	private String title;
	
	public static void setCallbacks() {
		glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));
	}
	
	public Window () {
		this.width = 800;
		this.height = 600;
		this.title = "Game Window";
	}
	
	public Window (int width, int height, String title, boolean fullscreen) {
		this.width = width;
		this.height = height;
		this.title = title;
		this.fullscreen = fullscreen;
		createWindow();
	}
	
	public void createWindow() {
		long monitor = glfwGetPrimaryMonitor();

		window = glfwCreateWindow(
				width,
				height,
				title,
				fullscreen ? monitor : 0,
				0);
		
		if (window == 0)
			throw new IllegalStateException("Failed to create window.");
		
		if (!fullscreen) {
			GLFWVidMode vidMode = glfwGetVideoMode(monitor);
			glfwSetWindowPos(window, (vidMode.width() - width) / 2, (vidMode.height() - height) / 2); // Show window in center of screen
			
			glfwShowWindow(window);
		}
		
		setKeyCallback();
		setWindowSizeCallback();
		setMouseButtonCallback();
		
		glfwMakeContextCurrent(window);
	}
	
	public boolean shouldClose() {
		return glfwWindowShouldClose(window);
	}
	
	public void swapBuffers() {
		glfwSwapBuffers(window);
	}
	
	public void setKeyCallback() {
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> { 	//Key listener
			if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) 	//on Escape, set the window to close
				glfwSetWindowShouldClose(window, true);
		});
	}
	
	public void setWindowSizeCallback() {
		glfwSetWindowSizeCallback(window, (window, width, height) -> { 	//Resize listener
			//System.out.println("resize" + width + " : " + height);
			glViewport(0,0,width, height); 	//Reset the viewport to the correct size
		});
	}
	
	public void setMouseButtonCallback() {
		glfwSetMouseButtonCallback(window, (window, button, action, mods) -> { 	//Mouse click listener
			if (button == GLFW_MOUSE_BUTTON_LEFT){ 	//If left mouse button
				if (action == GLFW_RELEASE) 	//Set a boolean variable based on state of mouse (GLFW won't poll mouse state again if already pressed, need to manually store state)
					mouseHeld = false;
				else if (action == GLFW_PRESS)
					mouseHeld = true;
			}
		});
	}
	
	public boolean isMouseHeld() {
		return mouseHeld;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return width;
	}

	public boolean isFullscreen() {
		return fullscreen;
	}
	
}
