package edu.ncsu.feddgame;

import java.util.ArrayList;

import edu.ncsu.feddgame.render.LaserModel;
import edu.ncsu.feddgame.render.Model;
import edu.ncsu.feddgame.render.ReflectionCalculation;



public class ObjectManager {
	
	private static ArrayList<Model> models = new ArrayList<Model>();
	private static ArrayList<LaserModel> lasers = new ArrayList<LaserModel>();
	private ArrayList<Model> addModels = new ArrayList<Model>();
	private ArrayList<LaserModel> addLasers = new ArrayList<LaserModel>();
	/**
	 * Adds a passed Model to the stored ArrayList
	 * @param Model m
	 */
	public Model addModel(Model m){
		addModels.add(m);
		return m;
	}
	
	/**
	 * Adds a passed Laser to the stored ArrayList
	 * @param m
	 * @return
	 */
	public LaserModel addLaserModel(LaserModel m){
		addLasers.add(m);
		return m;
	}
	
	/**
	 * Flushes the buffer ArrayList into the primary ArrayLists
	 */
	public void updateModels() {
		models.addAll(addModels);
		lasers.addAll(addLasers);
		addModels.clear();
		addLasers.clear();
	}
	
	/**
	 * Removed the model object at the index specified
	 * @param index
	 */
	public void removeModel(int index) {
		models.remove(index);
	}
	/**
	 * Removes the model passed
	 * @param m
	 */
	public void removeModel(Model m) {
		models.remove(m);
	}
	
	/**
	 * Returns the model at the given index
	 * @param index
	 * @return
	 */
	public Model getModel(int index) {
		return models.get(index);
	}
	
	/**
	 * Returns the index of the given Model
	 * @param m
	 * @return
	 */
	public int indexOf(Model m) {
		return models.indexOf(m);
	}
	
	/**
	 * Calls the render() function on all models and lasers
	 */
	public void renderAll() {
		for(int i = 0; i < models.size(); i++) {
			models.get(i).render();
		}
		for(int i = 0; i < lasers.size(); i++) {
			lasers.get(i).render();
		}
	}
	/**
	 * Calculates reflection on all lasers that were initially added in the models arraylist
	 */
	public void reflectAll() {
		lasers.clear();
		int size = models.size();
		for (int i = 0; i < size; i++){
			if (models.get(i) instanceof LaserModel){
				reflectLaser(i);
			}
		}
	}
	
	/**
	 * Moves the model at the index specified by the values passed
	 * @param index
	 * @param x
	 * @param y
	 * @param z
	 */
	public void moveModel(int index, float x, float y, float z) {
		if (models.get(index) != null) {
			models.get(index).move(x, y, z);
		}
		
	}
	/**
	 * Executes the calculation of the path of a single laser segment at the index passed
	 * @param index
	 */
	public void reflectLaser(int index) {
		if (models.get(index) != null && models.get(index) instanceof LaserModel)
			ReflectionCalculation.reflect((LaserModel)models.get(index), models);
	}

}
