package edu.ncsu.feddgame;

import java.util.ArrayList;



public class ObjectManager {
	
	private ArrayList<Model> models = new ArrayList<Model>();
	/**
	 * Adds a passed Model to the stored arraylist
	 * @param Model m
	 */
	public int addModel(Model m){
		models.add(m);
		return models.indexOf(m);
	}
	/**
	 * Removed the model object at the index specified
	 * @param index
	 */
	public void removeModel(int index){
		models.remove(index);
	}
	/**
	 * Removes the model passed
	 * @param m
	 */
	public void removeModel(Model m){
		models.remove(m);
	}
	
	/**
	 * Calls the render() function on all models
	 */
	public void render(){
		for(Model m : models){
			m.render();
		}
		
	}
	/**
	 * Moves the model at the index specified by the values passed
	 * @param index
	 * @param x
	 * @param y
	 * @param z
	 */
	public void moveModel(int index, float x, float y, float z){
		if (models.get(index) != null){
			models.get(index).move(x, y, z);
		}
	}

}
