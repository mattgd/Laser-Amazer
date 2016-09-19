package edu.ncsu.feddgame;

import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL;

public class GameInstance {
	
	private Window window;
	public static ObjectManager objectManager;
	
	// Game begins here
	public GameInstance() {
		try { 	// Run and quit on error
			setup();
			loop();
		} finally {
			glfwTerminate();
			System.exit(1);
		}
	}
	
	private void setup() { 	// Setup all the window settings
		Window.setCallbacks();
		
		objectManager = new ObjectManager();
		
		if (!glfwInit()) 	//Throw exception if glfw fails to initialize
			throw new IllegalStateException("Can not initialize GLFW");

		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); 	// Set window resizable and visible (set at defaults right now)
		glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
		
		window = new Window(800, 600, "FEDD Game", false);

		glfwSwapInterval(1); 	// Set Vsync (swap the double buffer from drawn to displayed every refresh cycle)
	}
	@SuppressWarnings("unused")
	private void loop() { 	// Render Loop
		GL.createCapabilities();
		
		//TODO Should probably throw exception and exit here if window is null
		
		Camera camera = new Camera(window.getWidth(), window.getHeight());
		
		glEnable(GL_TEXTURE_2D);
		
		int box1 = CreatePolygon.createBox(0,0);
		CreatePolygon.createBox(10f, .2f);
		CreatePolygon.createTrapezoid(-10f, 5f, 2f, 1, 1);
		Shader shader = new Shader("shader");
		Texture tex = new Texture("bound.png");
		
		//Matrix4f projection = new Matrix4f()
				//.ortho2D(-window.getWidth() / 2, window.getWidth() / 2, -window.getHeight() / 2, window.getHeight() / 2)
				//.scale(32);
		
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
		
		//projection.mul(scale, target);
		int i = 0; 	//Temp int
		float dir = 1;
		while (!window.shouldClose()) { 	// Poll window while window isn't about to close
			boolean canRender = false;
			
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
				if (i < 80){
					objectManager.moveModel(box1, 0.1f * dir, 0f, 0f); 	//Test animation of models, this pings the box back and forth
					i++;
				}else{
					i = 0;
					dir *= -1f;
				}
				
				
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
				objectManager.render();
				tex.bind(0);
				
				window.swapBuffers(); // Swap the render buffers
				frames++;
			}
		}
	}
}
