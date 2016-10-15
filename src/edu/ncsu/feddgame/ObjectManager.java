package edu.ncsu.feddgame;

import java.util.ArrayList;

import edu.ncsu.feddgame.render.LaserModel;
import edu.ncsu.feddgame.render.Model;



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
	
	public ArrayList<Model> getModels() {
		return models;
	}

	public void setModels(ArrayList<Model> models) {
		ObjectManager.models = models;
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
		getModels().addAll(addModels);
		lasers.addAll(addLasers);
		addModels.clear();
		addLasers.clear();
	}
	
	/**
	 * Removed the model object at the index specified
	 * @param index
	 */
	public void removeModel(int index) {
		getModels().remove(index);
	}
	/**
	 * Removes the model passed
	 * @param m
	 */
	public void removeModel(Model m) {
		getModels().remove(m);
	}
	
	/**
	 * Returns the model at the given index
	 * @param index
	 * @return
	 */
	public Model getModel(int index) {
		return getModels().get(index);
	}
	
	/**
	 * Returns the index of the given Model
	 * @param m
	 * @return
	 */
	public int indexOf(Model m) {
		return getModels().indexOf(m);
	}
	
	/**
	 * Calls the render() function on all models and lasers
	 */
	public void renderAll() {
		for(int i = 0; i < getModels().size(); i++) {
			getModels().get(i).render();
		}
	}
	/**
	 * Calculates reflection on all lasers that were initially added in the models arraylist
	 */
	public void reflectAll() {
		lasers.clear();
		int size = getModels().size();
		for (int i = 0; i < size; i++){
			if (getModels().get(i) instanceof LaserModel){
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
		if (getModels().get(index) != null) {
			getModels().get(index).move(x, y, z);
		}
		
	}
	/**
	 * Executes the calculation of the path of a single laser segment at the index passed
	 * @param index
	 */
	public void reflectLaser(int index) {
		if (getModels().get(index) != null && getModels().get(index) instanceof LaserModel){}
			//ReflectionCalculation.reflect((LaserModel)getModels().get(index), getModels());
	}

}
