package edu.ncsu.feddgame;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWKeyCallback;

public class Input extends GLFWKeyCallback {

	private long window;
	private boolean[] keys;
	
	public Input(long window) {
		this.window = window;
		this.keys = new boolean[65536];
		
		for (int i = 0; i < keys.length; i++)
			keys[i] = false;
	}
	
    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        keys[key] = action != GLFW_RELEASE;
    }
    
    /**
	 * @param key
	 * @return true if the key is currently being pressed
	 */
    public boolean isKeyDown(int keycode) {
        return keys[keycode];
    }
    
    /*
	public boolean isKeyDown(int key) {
		return false; 	//temp fix, we need to use specific key IDs or use callbacks, as many numbers in the range don't exist and cause errors
		//return glfwGetKey(window, key) == 1;
	}*/
	
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
		for (int i = 0; i < keys.length; i++)
			keys[i] = isKeyDown(i);
	}
	
}
