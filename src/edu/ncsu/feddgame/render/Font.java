package edu.ncsu.feddgame.render;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_ENABLE_BIT;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_ONE;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_BIT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glPopAttrib;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushAttrib;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

import de.matthiasmann.twl.utils.PNGDecoder;
import edu.ncsu.feddgame.GameInstance;

public class Font {

	private int fontTexture;
	private FloatColor color;
	private final String renderStr;
	
	private final static int GRID_SIZE = 16;
	
	public Font(String str, FloatColor color) {
		this.renderStr = str;
		this.color = color;
		
		try {
			setUpTextures();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setUpTextures() throws IOException {
        fontTexture = glGenTextures(); // Create new texture for the bitmap font
        // Bind the texture object to the GL_TEXTURE_2D target, specifying that it will be a 2D texture.
        glBindTexture(GL_TEXTURE_2D, fontTexture);
        
        // Use TWL's utility classes to load the png file
        PNGDecoder decoder = new PNGDecoder(new FileInputStream("res/font.png"));
        ByteBuffer buffer = BufferUtils.createByteBuffer(4 * decoder.getWidth() * decoder.getHeight());
        decoder.decode(buffer, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
        buffer.flip();
        
        // Load the previously loaded texture data into the texture object.
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, decoder.getWidth(), decoder.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE,
                buffer);
        
        glBindTexture(GL_TEXTURE_2D, 0); // Unbind the texture
    }
	
	public void renderString(String string, float x, float y, float characterWidth) {
		GameInstance.shader.unbind();
		
		float ratio = GameInstance.window.ratio;
		float characterHeight = 0.52f * characterWidth; // Automatically calculate the height from aspect ratio
		characterWidth /= ratio;
		x /= ratio;
		glPushAttrib(GL_TEXTURE_BIT | GL_ENABLE_BIT | GL_COLOR_BUFFER_BIT);
		glEnable(GL_CULL_FACE);
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, fontTexture);
		// Enable linear texture filtering for smoothed results.
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		// Enable additive blending. This means that the colors will be added to already existing colors in the
		// frame buffer. In practice, this makes the black parts of the texture become invisible.
		glEnable(GL_BLEND);
		glBlendFunc(GL_ONE, GL_ONE);
		// Store the current model-view matrix.
		glPushMatrix();
		// Offset all subsequent (at least up until 'glPopMatrix') vertex coordinates.
		glTranslatef(x, y, 0);
		
		glColor4f(color.red(), color.green(), color.blue(), 0.5f); // Set Font color
		
		glBegin(GL_QUADS);
		
		// Iterate over all the characters in the string.
		for (int i = 0; i < string.length(); i++) {
			// Get the ASCII-code of the character by type-casting to integer.
			int asciiCode = (int) string.charAt(i);
			
			// There are 16 cells in a texture, and a texture coordinate ranges from 0.0 to 1.0.
			final float cellSize = 1.0f / GRID_SIZE;
			
			// The cell's x-coordinate is the greatest integer smaller than remainder of the ASCII-code divided by the
			// amount of cells on the x-axis, times the cell size.
			float cellX = ((int) asciiCode % GRID_SIZE) * cellSize;
			// The cell's y-coordinate is the greatest integer smaller than the ASCII-code divided by the amount of
			// cells on the y-axis.
			float cellY = ((int) asciiCode / GRID_SIZE) * cellSize;
			
			glTexCoord2f(cellX, cellY + cellSize);
			glVertex2f(i * characterWidth / 3, y);
			glTexCoord2f(cellX + cellSize, cellY + cellSize);
			glVertex2f(i * characterWidth / 3 + characterWidth / 2, y);
			glTexCoord2f(cellX + cellSize, cellY);
			glVertex2f(i * characterWidth / 3 + characterWidth / 2, y + characterHeight);
			glTexCoord2f(cellX, cellY);
			glVertex2f(i * characterWidth / 3, y + characterHeight);
		}
		glEnd();
		glPopMatrix();
		glPopAttrib();
		GameInstance.shader.bind();
	}
	
	public void renderString(String string, Alignment align, float y, float characterWidth) {
		float x = 0;
		
		// Moderately arbitrary algorithms to get the desired text placement outcome
		switch (align) {
		case LEFT:
			x = -1.5f;
			break;
		case CENTER:
			x = -0.05f / (0.3f / characterWidth);
			x *= string.length();
			break;
		case RIGHT:
			float characterShift = string.length() * 2f + 1f;
			x = 1.5f - ((string.length() + string.length() / characterShift) / 10f);
			break;
		}

		renderString(string, x, y, characterWidth);
	}
	
	public int getFontTexture() {
		return fontTexture;
	}
	
	public String getRenderString() {
		return renderStr;
	}
	
	public void setColor(FloatColor color) {
		this.color = color;
	}
	
}
