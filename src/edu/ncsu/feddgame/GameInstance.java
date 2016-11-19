package edu.ncsu.feddgame;

import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_SAMPLES;
import static org.lwjgl.glfw.GLFW.GLFW_STENCIL_BITS;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glRectf;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL;

import edu.ncsu.feddgame.level.GameComplete;
import edu.ncsu.feddgame.level.Level1;
import edu.ncsu.feddgame.level.Level10;
import edu.ncsu.feddgame.level.Level2;
import edu.ncsu.feddgame.level.Level3;
import edu.ncsu.feddgame.level.Level4;
import edu.ncsu.feddgame.level.Level5;
import edu.ncsu.feddgame.level.MainMenu;
import edu.ncsu.feddgame.level.OptionsMenu;
import edu.ncsu.feddgame.level.Scene;
import edu.ncsu.feddgame.render.Alignment;
import edu.ncsu.feddgame.render.Camera;
import edu.ncsu.feddgame.render.FloatColor;
import edu.ncsu.feddgame.render.GameColor;
import edu.ncsu.feddgame.render.GameFont;
import edu.ncsu.feddgame.render.Shader;

public class GameInstance {
	
	public static Window window;
	public static ObjectManager objectManager;

	public static boolean levelCompleteDialogue = true;
	public static boolean showTimer = true;
	public static int samplingLevel = 4;
	public static int latestLevel = 5;
	
	private boolean canRender;
	
	public static List<Scene> scenes = new ArrayList<Scene>() {
		private static final long serialVersionUID = 525308338634565467L;
	{
		add(new MainMenu());
		add(new OptionsMenu());
		add(new Level1());
		add(new Level2());
		add(new Level3());
		add(new Level4());
		add(new Level5());
		add(new Level10());
		add(new GameComplete());
	}};
	
	private static int levNum = 0; // Start with 0
	private static float fade = 90f; // Amount of fade in degrees (0-90)
	private static boolean hasLevel = false;
	public static Shader shader;
	
	private static State state;
	static volatile boolean gameState; // Boolean value to pause logic Thread when state != GAME
	
	// Game begins here
	public GameInstance() {
		// Run and quit on error
		try { 	
			setup();
			renderLoop();
		} finally {
			//glfwTerminate();
			//System.exit(1);
		}
	}
	
	/**
	 * Setup all the window settings
	 */
	private void setup() {
		SaveData.readData();
		objectManager = new ObjectManager();
		
		// If glfw fails to initialize, throw exception 
		if (!glfwInit())
			throw new IllegalStateException("Cannot initialize GLFW");

		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); 	// Set window resizable and visible (set at defaults right now)
		glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
		glfwWindowHint(GLFW_SAMPLES, 4);
		glfwWindowHint(GLFW_STENCIL_BITS, 4);
		
		window = new Window(800, 800, "Laser Amazer", false);
	}
	
	/**
	 * Game render loop
	 */
	private void renderLoop() {
		GL.createCapabilities();
		//TODO Should probably throw exception and exit here if window is null
		
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		FloatColor clearColor = new FloatColor(GameColor.DARK_GREY.getColor());
		glClearColor(clearColor.red(), clearColor.blue(), clearColor.green(), 1f);

		// Set up main menu text
		GameFont menuItem = new GameFont("Start Game", new FloatColor(GameColor.ORANGE.getColor()));
		GameFont startGame = new GameFont("Press Space to Start Game", new FloatColor(GameColor.YELLOW.getColor()));
		
		Matrix4f scale = new Matrix4f().translate(new Vector3f(100, 0, 0)).scale(40);
		Matrix4f target = new Matrix4f();
		
		Camera camera = new Camera(window.getWidth(), window.getHeight());
		camera.setPosition(new Vector3f(-100, 0, 0));
		
		shader = new Shader("shader");
		
		double frameCap = 1.0 / 60; // 60 FPS
		
		double frameTime = 0;
		double time = getTime();
		double unprocessed = 0;
		
		setState(State.GAME); // Set the game state
		setLevel(6); // Set the starting level
		
		new Thread(() -> logicLoop()).start(); // Run the logic in a separate thread
		
		glfwSetWindowSize(window.window, 1200, 800);
		window.centerWindow(); // Center window on screen
		// Poll window while window isn't about to close
		while (!window.shouldClose()) {
			canRender = false;
			
			// Control frames per second
			{
				double timeNow = getTime();
				double elapsed = timeNow - time;
				unprocessed += elapsed;
				frameTime += elapsed;
				time = timeNow;
			}
			
			// Run all non-render related tasks
			while (unprocessed >= frameCap) {
				unprocessed -= frameCap;
				canRender = true;
				target = scale;
				
				window.update();
				
				if (frameTime >= 1.0) 
					frameTime = 0;
			}
			
			// Render when scene changes
			if (canRender) {
				renderLevel();
				glClear(GL_COLOR_BUFFER_BIT);
				
				if (state.equals(State.GAME)) {
					gameState = true;
					shader.bind();
					shader.updateUniforms(camera, target);
					objectManager.renderAll();
					window.updateTime();
					window.renderElements();
					getCurrentLevel().renderLoop();
				} else if (state.equals(State.LEVEL_COMPLETE)) {
					getCurrentLevel().setActive(false); // Set level as inactive
					
					// If user has the level complete dialogue enabled
					if (levelCompleteDialogue) {
						gameState = false;
						
						shader.bind();
						shader.updateUniforms(camera, target);
						objectManager.renderAll();
						
						window.renderElements();
						
						// Add dark rectangle to make text more readable
						shader.unbind();
						glEnable(GL_BLEND);
						glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
						
						glColor4f(clearColor.red(), clearColor.green(), clearColor.blue(), .5f);
						glRectf(-10f, -10f, 10f, 10f);
						
						Scene latestLevel = getCurrentLevel();
						
						menuItem.renderString("Congratulations!",  Alignment.CENTER, 0.1f, 0.45f);
						menuItem.renderString("You've completed " + latestLevel.getName() + " in " + latestLevel.getElapsedSeconds() + " seconds.", Alignment.CENTER, 0.02f, 0.2f);
						startGame.renderString("(Press Space to continue.)", Alignment.CENTER, -0.45f, 0.3f);
					} else {
						setState(State.NEXT_LEVEL);
					}
				} else if (state.equals(State.NEXT_LEVEL)) {
					gameState = false;
					nextLevel();
					setState(State.GAME);
				}
				
				if (!state.equals(State.LEVEL_COMPLETE)) {
					// Fading
					shader.unbind();
					glEnable(GL_BLEND);
					glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
					
					if (fade > 0) {
						fade -= 2.5f;
						glColor4f(clearColor.red(), clearColor.blue(), clearColor.green(), (float) Math.sin(Math.toRadians(fade)));
						glRectf(-10f, -10f, 10f, 10f);
					}
				}

				window.swapBuffers(); // Swap the render buffers
			}
			
		}
		SaveData.writeData();
	}
	
	private void logicLoop() {
		Thread.currentThread().setName("Logic");
		long timing = Math.round(1f / 60 * 1000f); 	// Get the number of milliseconds between frames based on 60 times a second
		
		while (!window.shouldClose()) {
			if (!gameState) continue;
			
			double timeNow = getTime(); // Get time at the start of the loop
			
			// Make sure it's the active level
			if (getCurrentLevel().isActive())
				getCurrentLevel().logicLoop();
			
			{
				long sleeptime = timing - (long)(getTime() - timeNow); // Sync the game loop to update at the refresh rate
				//System.out.println(sleeptime);
				try {
					Thread.sleep(sleeptime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}	
				
		}
	}
	
	/**
	 * @return System time in seconds
	 */
	public double getTime() {
		return (double) System.nanoTime() / (double) 1000000000L;
	}
	
	public static void setState(State s) {
		fade();
		state = s;
	}
	
	static State getState() {
		return state;
	}
	
	private static void nextLevel() {
		getCurrentLevel().setActive(false); // No longer the active level
		
		// If all levels complete, reset to level 0
		levNum++;
		latestLevel = (levNum <= latestLevel) ? (latestLevel) : (levNum);
		
		if (levNum > scenes.size() - 1) {
			levNum = 0;
			setLevel(scenes.size() - 1); // Set to GameComplete (last level)
		}
		
		getCurrentLevel().setActive(true); // Set new active level
		hasLevel = false;
	}
	
	static Scene getCurrentLevel() {
		return scenes.get(levNum);
	}
	
	public static void setLevel(int levelNumber) {
		if (levelNumber > scenes.size() - 1) {
			levNum = scenes.size() - 1;
		} else {
			levNum = levelNumber;
		}
		
		fade();
		scenes.get(levNum).setActive(true); // Set active level
		hasLevel = false;
	}
	
	private void renderLevel() {
		if (levNum < scenes.size() && !hasLevel) {
			objectManager.clearAll();
			window.clearElements();
			window.addElements();
			getCurrentLevel().renderObjects(); // Add all objects to the scene from the level class
			objectManager.updateModels();
			hasLevel = true;
		} else if (!hasLevel) {
			System.out.println("End of game");
		}
	}
	
	private static void fade() {
		fade = 90f;
	}
	
}
