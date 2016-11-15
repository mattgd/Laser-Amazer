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
import edu.ncsu.feddgame.gui.CreateUI;
import edu.ncsu.feddgame.gui.IClickable;
import edu.ncsu.feddgame.gui.UIElement;
import edu.ncsu.feddgame.gui.UIUtils;
import edu.ncsu.feddgame.level.Level;
import edu.ncsu.feddgame.render.Dropdown;
import edu.ncsu.feddgame.render.FloatColor;
import edu.ncsu.feddgame.render.Font;
import edu.ncsu.feddgame.render.Model;
import edu.ncsu.feddgame.render.MovableModel;

public class Window {

	public long window;
	public float ratio;
	public int refreshRate;
	public int width;
	public int height;
	public boolean fullscreen, mouseHeld = false, ctrlHeld = false, shiftHeld = false;
	private String title;
	private float mouseX, mouseY;
	public static boolean isclicked = false;
	GLFWVidMode vidMode;
	
	//private GLFWKeyCallback keyCallback; // Prevents our window from crashing later on
	
	//private Input input;
	
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
		
		if (fullscreen) {
			float aspect = 4.0f / 3.0f;

			int screenWidth = vidMode.width();
			int screenHeight = vidMode.height();

			int viewWidth = screenWidth;
			int viewHeight = (int) (screenWidth / aspect);

			if (viewHeight > screenHeight) {
				viewHeight = screenHeight;
				viewWidth = (int) (screenHeight * aspect);
			}

			int vportX = (screenWidth - viewWidth) / 2;
			int vportY = (screenHeight - viewHeight) / 2;
			
			glViewport(0, 0, viewWidth, viewHeight);
		} else {
			glfwSetWindowPos(window, (vidMode.width() - width) / 2, (vidMode.height() - height) / 2); // Show window in center of screen
			glfwShowWindow(window);
		}
		
		setWindowIcon();
		setKeyCallback();
		setWindowSizeCallback();
		setMouseButtonCallback();
		setMousePosCallback();
		glfwMakeContextCurrent(window);
		glfwSwapInterval(1); 	// Set Vsync (swap the double buffer from drawn to displayed every refresh cycle)
		//input = new Input(window);
		
	}
	/**
	 * Adds all specified elements to the Window's array and scene
	 */
	public void addElements() {
		/*Dropdown dp = CreateUI.createDropdown(-3f, 0, 2f, 1f, new Font("Dropdown", new FloatColor(25,  255,  0)), new Font[]{
				new Font("Option 1", new FloatColor(25,  255,  0)),
				new Font("Op 2", new FloatColor(25,  255,  0)),
				new Font("Test 3", new FloatColor(25,  255,  0))
		}, new Runnable[]{
				() -> {
					System.out.println("Ahoy 1");
				},
				() -> {
					System.out.println("Test 2");
				},
				() -> {
					System.out.println("3");
				}
		});
		elementList.add(dp);*/
		Dropdown du = CreateUI.createDropdown(-12f, 8f, 2f, 1f, new Font("Select Level", new FloatColor(25,  255,  0)));
		for (Level level : GameInstance.levels) {
			du.addButton(CreateUI.createButton(-12f, 8f, 2f, 1, () -> {
				GameInstance.setLevel(GameInstance.levels.indexOf(level));
			}, new Font(level.getName(), new FloatColor(25, 255, 0))));
		}
		elementList.add(du);
	}
	
	public void clearElements(){
		elementList.clear();
	}
	
	public void centerWindow(){
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
				case GAME_COMPLETE:
					GameInstance.setState(State.MAIN_MENU);
					break;
				case LEVEL_COMPLETE:
					GameInstance.setState(State.NEXT_LEVEL);
					break;
				case MAIN_MENU:
					GameInstance.setState(State.GAME);
					break;
				default:
					GameInstance.setState(State.MAIN_MENU);
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
			if (button == GLFW_MOUSE_BUTTON_LEFT){ 	//If left mouse button
				if (action == GLFW_RELEASE){ 	//Set a boolean variable based on state of mouse (GLFW won't poll mouse state again if already pressed, need to manually store state)
					mouseHeld = false;
				}else if (action == GLFW_PRESS && GameInstance.gameState){
					mouseHeld = true;
					for(int j = GameInstance.objectManager.getModels().size()-1; j >= 0; j--){ 	//Iterate over all models in the scene
						Model b = GameInstance.objectManager.getModel(j);
						if (b instanceof MovableModel){
							MovableModel m = (MovableModel)b;
							if (m.checkClick(mouseX, mouseY)){ 	//If the object is movable and is the one clicked
								new Thread(() -> { 	//Start a new thread to move it while the mouse is being held
								while (mouseHeld){
									try{
									wait(0); 			//Don't ask: the game breaks without some random code before the followCursor call
									}catch (Exception e){}
									if (GameInstance.gameState){ 	//Don't ask about this redundant code either...
										m.followCursor(mouseX, mouseY);
									}else{
										return;
									}
								}
								}).start();
								break;
							}
						}
					}
					for (UIElement e : elementList){
						if (e instanceof IClickable){
							((IClickable)e).checkClick(mouseX, mouseY);
						}
					}
				}
			}
		});
	}
	public void setMousePosCallback(){
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
