package edu.ncsu.feddgame;

import static org.lwjgl.glfw.GLFW.*;

public class Input {

	private long window;
	private boolean[] keys;
	
	public Input(long window) {
		this.window = window;
		this.keys = new boolean[GLFW_KEY_LAST];
		
		for (int i = 0; i < GLFW_KEY_LAST; i++)
			keys[i] = false;
	}
	
	/**
	 * @param key
	 * @return true if the key is currently being held
	 */
	public boolean isKeyDown(int key) {
		return false; 	//temp fix, we need to use specific key IDs or use callbacks, as many numbers in the range don't exist and cause errors
		//return glfwGetKey(window, key) == 1;
	}
	
	/**
	 * @param key
	 * @return true if the key is currently being pressed
	 */
	public boolean isKeyPressed(int key) {
		return (isKeyDown(key) && !keys[key]);
	}
	
	/**
	 * @param key
	 * @return true if the key has been released
	 */
	public boolean isKeyReleased(int key) {
		return (!isKeyDown(key) && keys[key]);
	}
	
	/**
	 * @param button
	 * @return true if the button is currently being pressed
	 */
	public boolean isMouseButtonPressed(int button) {
		return glfwGetMouseButton(window, button) == 1;
	}
	
	/**
	 * Update the key status array (runs during every Window update)
	 */
	public void update() {
		for (int i = 0; i < GLFW_KEY_LAST; i++)
			keys[i] = isKeyDown(i);
	}
	
}
