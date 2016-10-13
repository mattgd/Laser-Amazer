package edu.ncsu.feddgame;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import edu.ncsu.feddgame.render.Texture;

public class TextureManager {

	private Map<String, Texture> textures = new HashMap<String, Texture>();
	
	public TextureManager() {
		initializeTextures();
	}
	
	/**
	 * Adds all of the textures within the "textures" folder
	 * to the textures Map.
	 */
	private void initializeTextures() {
		File textureFolder = new File("./textures/");
		String name;
		
		for (File f : textureFolder.listFiles()) {
			name = f.getName();
			textures.put(name.substring(0, name.indexOf(".png")), new Texture(name));
		}
	}
	
	/**
	 * @return A Map of all of the textures with the file prefix
	 * as the key
	 */
	public Map<String, Texture> getTextures() {
		return textures;
	}
	
}
