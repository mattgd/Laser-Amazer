package edu.ncsu.feddgame;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL11.glViewport;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

public class GameInstance {
	
	private long window;
	private long monitor;
	GLFWVidMode vidmode;
	
	private final int WINDOW_WIDTH = 800, WINDOW_HEIGHT = 600; 	//Store the window size
	private boolean mouseHeld;
	
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
	
	private void setup(){ 	//Setup all the window settings
		GLFWErrorCallback.createPrint(System.err).set();
		
		if (!glfwInit()) 	//Throw exception if glfw fails to initialize
			throw new IllegalStateException("Can not initialize GLFW");
		
		monitor = glfwGetPrimaryMonitor(); 	//Set the monitor to use
		
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); 	//Set window resizable and visible (set at defaults right now)
		glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
		
		vidmode = glfwGetVideoMode(monitor); //Get screen information
		
		window = glfwCreateWindow(WINDOW_WIDTH, WINDOW_HEIGHT, "FEDD Game", 0, 0);

		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> { 	//Key listener
			if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) 	//on Escape, set the window to close
				glfwSetWindowShouldClose(window, true);
		});
		
		glfwSetWindowSizeCallback(window, (window, width, height) -> { 	//Resize listener
			//System.out.println("resize" + width + " : " + height);
			glViewport(0,0,width, height); 	//Reset the viewport to the correct size
		});
		
		glfwSetMouseButtonCallback(window, (window, button, action, mods) -> { 	//Mouse click listener
			if (button == GLFW_MOUSE_BUTTON_LEFT){ 	//If left mouse button
				if (action == GLFW_RELEASE) 	//Set a boolean variable based on state of mouse (GLFW won't poll mouse state again if already pressed, need to manually store state)
					mouseHeld = false;
				else if (action == GLFW_PRESS)
					mouseHeld = true;
			}
		});
		
		glfwMakeContextCurrent(window);

		glfwSwapInterval(1); 	//set Vsync (swap the double buffer from drawn to displayed every refresh cycle)
	}
	
	private void loop(){ 	//Render Loop
		GL.createCapabilities();
		
		Camera camera = new Camera(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		glEnable(GL_TEXTURE_2D);
		
		// Vertices for a quadrilateral
		float[] vertices = new float[] {
			-0.5f, 0.5f, 0, // TOP LEFT - 0
			0.5f, 0.5f, 0, // TOP RIGHT - 1
			0.5f, -0.5f, 0, // BOTTOM RIGHT - 2
			-0.5f, -0.5f, 0, // BOTTOM LEFT - 3
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
		
		Model model = new Model(vertices, texture, indices);
		Shader shader = new Shader("shader");
		Texture tex = new Texture("bound.png");
		
		//Matrix4f projection = new Matrix4f()
				//.ortho2D(-WINDOW_WIDTH / 2, WINDOW_WIDTH / 2, -WINDOW_HEIGHT / 2, WINDOW_HEIGHT / 2)
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
		
		while (!glfwWindowShouldClose(window)) { 	//Poll window while window isn't about to close
			boolean canRender = false;
			
			double timeNow = Timer.getTime();
			double elapsed = timeNow - time;
			unprocessed += elapsed;
			frameTime += elapsed;
			
			time = timeNow;
			
			// Run all non-render related tasks
			while (unprocessed >= frameCap) {
				unprocessed -= frameCap;
				canRender = true;
				
				target = scale;
				
				//TODO Put key/mouse events here
				glfwPollEvents();
				
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
				model.render();
				tex.bind(0);
				
				//renderBox(x, y); 	//Test rendering of objects
				//renderLine();
				
				glfwSwapBuffers(window); 	//Swap the render buffers
				frames++;
			}
		}
	}
	
	private void renderBox(float x, float y){
		glBegin(GL_QUADS);
		glColor4f(1,1,1,0);
		glTexCoord2f(0.0f, 0.0f);
		glVertex2f(-.1f + x, .1f + y);
		
		glTexCoord2f(1.0f, 0.0f);
		glVertex2f(.1f + x, .1f + y); 	//draw a white box in the center of the screen
		
		glTexCoord2f(1.0f, 1.0f);
		glVertex2f(.1f + x, -.1f + y);
		
		glTexCoord2f(0.0f, 1.0f);
		glVertex2f(-.1f + x, -.1f + y);
		glEnd();
	}
	private void renderLine(){
		if (!mouseHeld) 	//Continue if the mouse button is held
			return;
		glBegin(GL_LINES);
		glColor4f(0,1,0,0);
		glVertex2f(.5f, -.1f); 	//Draw a green vertical line
		glVertex2f(.5f, .1f);
		glEnd();
	}
	
}
