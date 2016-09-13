package edu.ncsu.feddgame;

import java.util.ArrayList;
import java.util.List;

public class ObjectManager {
	
	private List<Model> models = new ArrayList<Model>();
	/**
	 * Adds a passed Model to the stored arraylist
	 * @param Model m
	 */
	public void addModel(Model m){
		models.add(m);
	}
	
	/**
	 * Calls the render() function on all models
	 */
	public void render(){
		for(Model m : models){
			m.render();
		}
	}

}
