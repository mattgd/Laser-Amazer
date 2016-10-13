package edu.ncsu.feddgame;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glViewport;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;

public class Window {

	public long window;
	public int refreshRate;
	private int width, height;
	private boolean fullscreen, mouseHeld = false;
	private String title;
	
	private Input input;
	
	public Window () {
		this.width = 800;
		this.height = 600;
		this.title = "Game Window";
		createWindow();
	}
	/**
	 * 
	 * @param width
	 * @param height
	 * @param title
	 * @param fullscreen
	 */
	public Window (int width, int height, String title, boolean fullscreen) {
		this.width = width;
		this.height = height;
		this.title = title;
		this.fullscreen = fullscreen;
		createWindow();
	}
	
	/**
	 * Creates the window with the parameters
	 * of the local variables.
	 */
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
		GLFWVidMode vidMode = glfwGetVideoMode(monitor);
		refreshRate = vidMode.refreshRate();
		
		if (!fullscreen) {
			glfwSetWindowPos(window, (vidMode.width() - width) / 2, (vidMode.height() - height) / 2); // Show window in center of screen
			glfwShowWindow(window);
		}
		
		setKeyCallback();
		setWindowSizeCallback();
		setMouseButtonCallback();
		glfwMakeContextCurrent(window);
		glfwSwapInterval(1); 	// Set Vsync (swap the double buffer from drawn to displayed every refresh cycle)
		input = new Input(window);
	}
	
	/**
	 * Sets the error callback for the game
	 */
	public static void setCallbacks() {
		glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));
	}
	
	/**
	 * @return close flag of the window
	 */
	public boolean shouldClose() {
		return glfwWindowShouldClose(window);
	}
	
	/**
	 * Swaps the front and back buffers of the window
	 */
	public void swapBuffers() {
		glfwSwapBuffers(window);
	}
	
	/**
	 * Sets the key callback to be checked
	 * when glfwPollEvents is called
	 */
	public void setKeyCallback() {
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> { 	//Key listener
			if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) 	//on Escape, set the window to close
				glfwSetWindowShouldClose(window, true);
		});
	}
	
	/**
	 * Sets the size callback for the window
	 */
	public void setWindowSizeCallback() {
		glfwSetWindowSizeCallback(window, (window, width, height) -> { 	//Resize listener
			glViewport(0,0,width, height); 	//Reset the viewport to the correct size
		});
	}
	
	/**
	 * Sets the mouse callback to be checked
	 * when glfwPollEvents is called
	 */
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
	
	/**
	 * @return true if GLFW_MOUSE_BUTTON_LEFT is held
	 */
	public boolean isMouseHeld() {
		return mouseHeld;
	}
	
	/**
	 * @return width of the game window
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * @return height of the game window
	 */
	public int getHeight() {
		return width;
	}

	/**
	 * @return true if the window is fullscreen
	 */
	public boolean isFullscreen() {
		return fullscreen;
	}
	
	/**
	 * Updates input array and polls GLFW events
	 */
	public void update() {
		input.update();
		glfwPollEvents();
	}
	
}
