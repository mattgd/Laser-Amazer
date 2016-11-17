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

import edu.ncsu.feddgame.level.Level;
import edu.ncsu.feddgame.level.Level1;
import edu.ncsu.feddgame.level.Level10;
import edu.ncsu.feddgame.level.Level2;
import edu.ncsu.feddgame.level.Level3;
import edu.ncsu.feddgame.level.TestLevel;
import edu.ncsu.feddgame.render.Alignment;
import edu.ncsu.feddgame.render.Camera;
import edu.ncsu.feddgame.render.FloatColor;
import edu.ncsu.feddgame.render.GameColor;
import edu.ncsu.feddgame.render.GameFont;
import edu.ncsu.feddgame.render.Shader;

public class GameInstance {
	
	public static Window window;
	public static ObjectManager objectManager;
	boolean canRender;
	private int levelTime;
	
	public static List<Level> levels = new ArrayList<Level>() {
		private static final long serialVersionUID = 525308338634565467L;
	{
		add(new Level1());
		add(new Level2());
		add(new Level3());
		add(new TestLevel());
		add(new Level10());
	}};
	
	private static int levNum = 0; // Start with 0
	public static float fade = 90f; // Amount of fade in degrees (0-90)
	private static boolean hasLevel = false;
	public static Shader shader;
	
	private static State state;
	public static volatile boolean gameState; // Boolean value to pause logic Thread when state != GAME
	
	// Game begins here
	public GameInstance() {
		try { 	// Run and quit on error
			setup();
			renderLoop();
		} finally {
			//glfwTerminate();
			//System.exit(1);
		}
	}
	
	private void setup() { 	// Setup all the window settings
		//Window.setCallbacks();
		
		objectManager = new ObjectManager();
		
		if (!glfwInit()) {
			// Throw exception if glfw fails to initialize
			throw new IllegalStateException("Can not initialize GLFW");
		}	

		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); 	// Set window resizable and visible (set at defaults right now)
		glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
		glfwWindowHint(GLFW_SAMPLES, 4);
		glfwWindowHint(GLFW_STENCIL_BITS, 4);
		
		window = new Window(800, 800, "Laser Amazer", false);
		
		state = State.MAIN_MENU; // Set the game state
	}
	
	private void renderLoop() { 	// Render Loop
		GL.createCapabilities();
		//TODO Should probably throw exception and exit here if window is null
		
		//TODO: Move this to it's own class, and have Fonts instantiated with location info
		// Set up main menu text
		GameFont menuTitle = new GameFont("Laser Amazer", new FloatColor(GameColor.RED.getColor()));
		GameFont menuItem = new GameFont("Start Game", new FloatColor(GameColor.ORANGE.getColor()));
		GameFont startGame = new GameFont("Press Space to Start Game", new FloatColor(GameColor.YELLOW.getColor()));
		
		Camera camera = new Camera(window.getWidth(), window.getHeight());
		
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		FloatColor clearColor = new FloatColor(GameColor.DARK_GREY.getColor());
		glClearColor(clearColor.red(), clearColor.blue(), clearColor.green(), 1f);
		//glClearColor(1f, 1f, 1f, 1f);
		
		shader = new Shader("shader");

		Matrix4f scale = new Matrix4f()
				.translate(new Vector3f(100, 0, 0))
				.scale(40);
		Matrix4f target = new Matrix4f();
		
		camera.setPosition(new Vector3f(-100, 0, 0));
		
		double frameCap = 1.0 / 60; // 60 FPS
		
		double frameTime = 0;
		@SuppressWarnings("unused")
		int frames = 0;
		
		double time = getTime();
		double unprocessed = 0;
		
		setLevel(0); // Set the level starting level
		
		new Thread(() -> logicLoop()).start(); 	//Run the logic in a separate thread
		glfwSetWindowSize(window.window, 1200, 800);
		window.centerWindow(); // Center window on screen

		while (!window.shouldClose()) { 	// Poll window while window isn't about to close
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
				
				if (frameTime >= 1.0) {
					frameTime = 0;
					// Display FPS counter in console
					//System.out.println("FPS: " + frames);
					frames = 0;
				}
			}
			
			renderLevel();
			
			// Render when scene changes
			if (canRender) {
				glClear(GL_COLOR_BUFFER_BIT);
				
				if (state.equals(State.GAME)) {
					gameState = true;
					shader.bind();
					shader.updateUniforms(camera, target);
					objectManager.renderAll();
					
					// Make sure it's the active level
					if (levels.get(levNum).isActiveLevel())
						levels.get(levNum).logicLoop();

					window.renderElements();
				} else if (state.equals(State.GAME_COMPLETE)) {
					gameState = false;
					menuItem.renderString("Congratulations!", Alignment.CENTER, 0.1f, 0.3f);
					menuItem.renderString("You've completed the game.", Alignment.CENTER, 0.02f, 0.3f);
					menuTitle.renderString("Made by", Alignment.CENTER, -0.1f, 0.3f);
					menuTitle.renderString("thejereman13 and mattgd", Alignment.CENTER, -0.17f, 0.23f);
					startGame.renderString("(Press Space to return to the menu.)", Alignment.CENTER, -0.45f, 0.23f);
				} else if (state.equals(State.LEVEL_COMPLETE)) {
					if (gameState) {
						levelTime = (int)levels.get(levNum).getElapsedTime();
					}
					
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
					
					menuItem.renderString("Congratulations!",  Alignment.CENTER, 0.1f, 0.45f);
					menuItem.renderString("You've completed " + levels.get(levNum).getName() + " in " + levelTime + " seconds.", Alignment.CENTER, 0.02f, 0.2f);
					startGame.renderString("(Press Space to continue.)", Alignment.CENTER, -0.45f, 0.3f);
				} else if (state.equals(State.MAIN_MENU)) {
					gameState = false;

					menuTitle.renderString(menuTitle.getRenderString(), Alignment.CENTER, 0.3f, 0.4f);
					menuItem.renderString("> Start Game", -0.64f, 0.1f, 0.3f);
					menuItem.renderString("> How to Play", -0.64f, 0.02f, 0.3f);
					startGame.renderString("(Press Space to start.)", Alignment.CENTER, -0.45f, 0.3f);
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
				frames++;
			}
			
		}
	}
	
	private void logicLoop() {
		Thread.currentThread().setName("Logic");
		long timing = Math.round(1f / 60 * 1000f); 	//Get the number of milliseconds between frames based on 60 times a second
		
		while (!window.shouldClose()) {
			if (!gameState) continue;
			
			double timeNow = getTime(); 	//Get time at the start of the loop
			
			// Make sure it's the active level
			if (levels.get(levNum).isActiveLevel())
				levels.get(levNum).logicLoop();
			
			{
				long sleeptime = timing - (long)(getTime() - timeNow); 	//Sync the game loop to update at the refresh rate
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
		fade = 90f;
		state = s;
	}
	
	public static State getState() {
		return state;
	}
	
	public static void nextLevel() {
		levels.get(levNum).setActiveLevel(false); // No longer the active level
		
		// If all levels complete, reset to level 0
		levNum++;
		
		if (levNum > levels.size() - 1) {
			levNum = 0;
			setState(State.GAME_COMPLETE);
		}
		
		levels.get(levNum).setActiveLevel(true); // Set new active level
		hasLevel = false;
	}
	
	public static void setLevel(int lev) {
		levNum = lev;
		levels.get(levNum).setActiveLevel(true); // Set active level
		hasLevel = false;
	}
	
	public void renderLevel() {
		if (levNum < levels.size() && !hasLevel) {
			objectManager.clearAll();
			window.clearElements();
			levels.get(levNum).renderObjects(); // Add all objects to the scene from the level class
			objectManager.updateModels();
			window.addElements();
			hasLevel = true;
		} else if (!hasLevel) {
			System.out.println("End of game");
		}
	}
	
}
