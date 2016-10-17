package edu.ncsu.feddgame;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.glViewport;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;

public class Window {

	public long window;
	public int refreshRate;
	private int width, height;
	private boolean fullscreen, mouseHeld = false;
	private String title;
	private float mouseX, mouseY;
	
	private Input input;
	
	public ArrayList<Button> buttonList = new ArrayList<Button>();
	
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
		setMousePosCallback();
		glfwMakeContextCurrent(window);
		glfwSwapInterval(1); 	// Set Vsync (swap the double buffer from drawn to displayed every refresh cycle)
		input = new Input(window);
		
		createButton(-8f, 1f, 1, 1, () -> {
			System.out.println("Click");
		});
		createButton(4f, 8f, 1, 1, () -> {
			System.out.println("Click2");
		});
		
		
	}
	
	/**
	 * Creates a new button object and adds it to the window view
	 * @param xOffset
	 * @param yOffset
	 * @param height
	 * @param width
	 * @param r
	 */
	public void createButton(float xOffset, float yOffset, float height, float width, Runnable r){
		// Vertices for a trapezoid
		float[] vertices = new float[] {
			-width/2f + xOffset, height/2f + yOffset, 0, // TOP LEFT - 0
			width/2f + xOffset, height/2f + yOffset, 0, // TOP RIGHT - 1
			width/2f + xOffset, -height/2f + yOffset, 0, // BOTTOM RIGHT - 2
			-width/2f + xOffset, -height/2f + yOffset, 0, // BOTTOM LEFT - 3
		};
		
		float[] texture = new float[] {
			0, 0, // TOP LEFT
			1, 0, // TOP RIGHT
			1, 1, // BOTTOM RIGHT
			0, 1, // BOTTOM LEFT
		};
		
		int[] indices = new int[] {
				0, 1, 2,
				2, 3, 0
		};
		buttonList.add(new Button(vertices, texture, indices, r));
	}
	
	/**
	 * Converts cursor coordinates into coordinates on the cartesian coordinate system in-game
	 * @param xPos
	 * @param yPos
	 * @return
	 */
	public float[] convertToWorldspace(float xPos, float yPos){
		float xP = (xPos - this.width/2f) * (20f / this.width);
		float yP = (-yPos + this.height/2f) * (20f / this.height);
		return new float[]{xP, yP};
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
				if (action == GLFW_RELEASE){ 	//Set a boolean variable based on state of mouse (GLFW won't poll mouse state again if already pressed, need to manually store state)
					mouseHeld = false;
				}else if (action == GLFW_PRESS){
					mouseHeld = true;
					for(Button b : buttonList){ 	//Iterate and perform click checks on all buttons in the arraylist
						b.checkClick(mouseX, mouseY);
					}
				}
			}
		});
	}
	public void setMousePosCallback(){
		glfwSetCursorPosCallback(window, (window, xPos, yPos) -> {
			float[] newC = convertToWorldspace((float)xPos, (float)yPos);
			this.mouseX = newC[0];
			this.mouseY = newC[1];
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
		glfwPollEvents();
		input.update();
		
	}
	
	public void renderButtons(){
		for (Button b : buttonList){
			b.render();
		}
	}
	
}
