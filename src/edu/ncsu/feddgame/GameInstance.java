package edu.ncsu.feddgame;

import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

public class GameInstance {
	
	private Window window;
	public static ObjectManager objectManager;
	boolean canRender;
	public ILevel level;
	
	
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
		
		if (!glfwInit()) 	//Throw exception if glfw fails to initialize
			throw new IllegalStateException("Can not initialize GLFW");

		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); 	// Set window resizable and visible (set at defaults right now)
		glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
		
		window = new Window(800, 600, "FEDD Game", false);
		
		level = new TestLevel();
	}
	
	private void renderLoop() { 	// Render Loop
		GL.createCapabilities();
		
		//TODO Should probably throw exception and exit here if window is null
		
		Camera camera = new Camera(window.getWidth(), window.getHeight());
		
		glEnable(GL_TEXTURE_2D);
		
		level.renderObjects(); 	//Add all objects to the scene from the level class
		objectManager.updateModels();
		
		Shader shader = new Shader("shader");
		Texture tex = new Texture("bound.png");
		
		Matrix4f scale = new Matrix4f()
				.translate(new Vector3f(100, 0, 0))
				.scale(32);
		
		Matrix4f target = new Matrix4f();
		
		camera.setPosition(new Vector3f(-100, 0, 0));
		
		double frameCap = 1.0 / 60; // 60 FPS
		
		double frameTime = 0;
		int frames = 0;
		
		double time = Timer.getTime();
		double unprocessed = 0;
		
		new Thread(() -> logicLoop()).start(); 	//Run the logic in a separate thread
		
		while (!window.shouldClose()) { 	// Poll window while window isn't about to close
			canRender = false;
			
			{
				double timeNow = Timer.getTime();
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
				
				//TODO Put key/mouse events here
				window.update();
				
				if (frameTime >= 1.0) {
					frameTime = 0;
					// Uncomment to display FPS counter
					//System.out.println("FPS: " + frames);
					frames = 0;
				}
			}
			
			// Render when scene changes
			if (canRender) {
				//glClearColor(1, 1, 1, 1);
				glClear(GL_COLOR_BUFFER_BIT);
				
				shader.bind();
				shader.setUniform("sampler", 0);
				shader.setUniform("projection", camera.getProjection().mul(target));
				tex.bind(0);
				objectManager.renderAll();
				
				window.swapBuffers(); // Swap the render buffers
				frames++;
			}
		}
	}
	
	private void logicLoop() {	
		long timing = Math.round(1f / 60 * 1000f); 	//Get the number of milliseconds between frames based on 60 times a second
		
		while (!window.shouldClose()){
			objectManager.reflectAll();
			objectManager.updateModels();
			double timeNow = Timer.getTime(); 	//Get time at the start of the loop
			
				level.logicLoop(); 	//Run the logic necessary for the level
			
			{
				long sleeptime = timing - (long)(Timer.getTime() - timeNow); 	//Sync the game loop to update at the refresh rate
				//System.out.println(sleeptime);
				try {
					Thread.sleep(sleeptime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}	
				
		}
	}
}
