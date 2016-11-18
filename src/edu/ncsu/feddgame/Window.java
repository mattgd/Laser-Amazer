package edu.ncsu.feddgame;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
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
import static org.lwjgl.glfw.GLFW.glfwSetWindowIcon;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.glViewport;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;

import de.matthiasmann.twl.utils.PNGDecoder;
import edu.ncsu.feddgame.gui.IClickable;
import edu.ncsu.feddgame.gui.Text;
import edu.ncsu.feddgame.gui.UIElement;
import edu.ncsu.feddgame.gui.UIUtils;
import edu.ncsu.feddgame.level.Level;
import edu.ncsu.feddgame.render.GameColor;
import edu.ncsu.feddgame.render.Model;
import edu.ncsu.feddgame.render.MovableModel;

public class Window {

	public long window;
	private float mouseX, mouseY;
	public float ratio;
	public int refreshRate, width, height;
	public boolean fullscreen, mouseHeld = false, ctrlHeld = false, shiftHeld = false;
	private String title;
	public static boolean isClicked = false;
	private GLFWVidMode vidMode;
	
	public ArrayList<UIElement> elementList = new ArrayList<UIElement>();
	
	public Window () {
		this(800, 800, "FEDD Game", false);
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
		this.ratio = (float)width / (float)height;
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
		
		vidMode = glfwGetVideoMode(monitor);
		refreshRate = vidMode.refreshRate();
		
		glfwSetWindowPos(window, (vidMode.width() - width) / 2, (vidMode.height() - height) / 2); // Show window in center of screen
		glfwShowWindow(window);
		
		setWindowIcon();
		setKeyCallback();
		setWindowSizeCallback();
		setMouseButtonCallback();
		setMousePosCallback();
		glfwMakeContextCurrent(window);
		glfwSwapInterval(1); 	// Set Vsync (swap the double buffer from drawn to displayed every refresh cycle)
	}
	
	/**
	 * Adds all specified elements to the Window's array and scene
	 */
	public void addElements() {
		//Add items here that need to be rendered every frame on all screens
		if (GameInstance.getCurrentLevel() instanceof Level && GameInstance.showTimer)
			elementList.add(new Text(11f, -2f, "Time: " + (int)((Level)GameInstance.getCurrentLevel()).getElapsedTime(), GameColor.TEAL.getFloatColor(), 1));
	}
	
	public void clearElements() {
		elementList.clear();
	}
	
	public void centerWindow() {
		glfwSetWindowPos(window, (vidMode.width() - width) / 2, (vidMode.height() - height) / 2); // Show window in center of screen
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
	 * Sets the error callback for the game
	 */
	public static void setCallbacks() {
		glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));
	}

	/**
	 * Sets the key callback to be checked
	 * when glfwPollEvents is called
	 */
	public void setKeyCallback() {
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> { 	//Key listener
			if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) 	//on Escape, set the window to close
				glfwSetWindowShouldClose(window, true);
			if (key == GLFW_KEY_LEFT_CONTROL) {
				if (action == GLFW_PRESS) {
					ctrlHeld = true;
					
					System.out.println("Mouse: " + mouseX + ", " + mouseY);
				} else if (action == GLFW_RELEASE) {
					ctrlHeld = false;
				}
			}
			
			if (key == GLFW_KEY_LEFT_SHIFT) {
				if (action == GLFW_PRESS) {
					shiftHeld = true;
				} else if (action == GLFW_RELEASE) {
					shiftHeld = false;
				}
			}
			
			if (key == GLFW_KEY_SPACE && action == GLFW_RELEASE) {
				State state = GameInstance.getState();
				switch (state) {
				case GAME:
					break;
				case GAME_COMPLETE:
					//GameInstance.setState(State.MAIN_MENU);
					break;
				case LEVEL_COMPLETE:
					GameInstance.setState(State.NEXT_LEVEL);
					break;
				default:
					GameInstance.setState(State.GAME);
				}
			}	
		});
	}
	
	/**
	 * Sets the size callback for the window
	 */
	public void setWindowSizeCallback() {
		glfwSetWindowSizeCallback(window, (window, width, height) -> { 	//Resize listener
			glViewport(0,0,width, height); 	//Reset the viewport to the correct size
			this.width = width;
			this.height = height;
			this.ratio = (float)width / (float)height;
		});
	}
	
	/**
	 * Sets the mouse callback to be checked
	 * when glfwPollEvents is called
	 */
	public void setMouseButtonCallback() {
		glfwSetMouseButtonCallback(window, (window, button, action, mods) -> { 	//Mouse click listener
			if (button == GLFW_MOUSE_BUTTON_LEFT) { 	//If left mouse button
				if (action == GLFW_RELEASE) { 	//Set a boolean variable based on state of mouse (GLFW won't poll mouse state again if already pressed, need to manually store state)
					mouseHeld = false;
				} else if (action == GLFW_PRESS && GameInstance.gameState) {
					mouseHeld = true;
					
					// Iterate over all models in the scene
					for (int j = GameInstance.objectManager.getModels().size()-1; j >= 0; j--) {
						Model b = GameInstance.objectManager.getModel(j);
						if (b instanceof MovableModel) {
							MovableModel m = (MovableModel) b;
							if (m.checkClick(mouseX, mouseY)){ 	//If the object is movable and is the one clicked
								new Thread(() -> { 	//Start a new thread to move it while the mouse is being held
									while (mouseHeld){
										try {
											//TODO: The game breaks without some random code before the followCursor call
											wait(0);
										} catch (Exception e) {}
										
										//TODO: Redundant code
										if (GameInstance.gameState) { 	
											m.followCursor(mouseX, mouseY);
										} else {
											return;
										}
									}
								}).start();
								break;
							}
						}
					}
					
					for (UIElement e : elementList) {
						if (e instanceof IClickable) {
							((IClickable) e).checkClick(mouseX, mouseY);
						}
					}
					GameInstance.getCurrentLevel().checkClick(mouseX, mouseY);
				}
			}
		});
	}
	
	public void setMousePosCallback() {
		glfwSetCursorPosCallback(window, (window, xPos, yPos) -> {
			float[] newC = UIUtils.convertToWorldspace((float)xPos, (float)yPos, this.width, this.height);
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
		//input.update();
	}
	
	/**
	 * Renders all UI elements in elementList
	 */
	public void renderElements() {
		for (UIElement e : elementList)
			e.render();
	}
	
	private void setWindowIcon() {
		GLFWImage image = GLFWImage.malloc();
		image.set(32, 32, loadIcon("res/icon.png"));
		GLFWImage.Buffer images = GLFWImage.malloc(1);
		images.put(0, image);

		glfwSetWindowIcon(window, images);

		images.free();
		image.free();
	}
	
	private ByteBuffer loadIcon(String path) {
		PNGDecoder dec = null;
		ByteBuffer buf = null;
		
		try {
		    dec = new PNGDecoder(new FileInputStream(path));
		    int width = dec.getWidth();
		    int height = dec.getHeight();
		    buf = BufferUtils.createByteBuffer(width * height * 4);
		    dec.decode(buf, width * 4, PNGDecoder.Format.RGBA);
		    buf.flip();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		return buf;
    }
	
}
