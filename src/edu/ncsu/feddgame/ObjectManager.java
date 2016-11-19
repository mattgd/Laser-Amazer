package edu.ncsu.feddgame;

import java.util.ArrayList;
import java.util.List;

import edu.ncsu.feddgame.render.LaserModel;
import edu.ncsu.feddgame.render.Model;

public class ObjectManager {

	private static ArrayList<Model> models = new ArrayList<Model>();
	private ArrayList<Model> addModels = new ArrayList<Model>();

	/**
	 * Adds a passed Model to the stored ArrayList
	 * 
	 * @param Model
	 *            m
	 */
	public Model addModel(Model m) {
		addModels.add(m);
		return m;
	}

	/**
	 * Returns the models Arraylist
	 * @return
	 */
	public List<Model> getModels() {
		return models;
	}

	public void setModels(ArrayList<Model> models) {
		ObjectManager.models = models;
	}

	/**
	 * Flushes the buffer ArrayList into the primary ArrayLists
	 */
	void updateModels() {
		models.addAll(addModels);
		addModels.clear();
		addModels.trimToSize();
	}

	/**
	 * Removed the model object at the index specified
	 * 
	 * @param index
	 */
	public void removeModel(int index) {
		models.remove(index);
	}

	/**
	 * Removes the model passed
	 * 
	 * @param m
	 */
	public void removeModel(Model m) {
		getModels().remove(m);
	}

	/**
	 * Returns the model at the given index
	 * 
	 * @param index
	 * @return
	 */
	Model getModel(int index) {
		return getModels().get(index);
	}

	/**
	 * Calls the render() function on all models and lasers
	 */
	void renderAll() {
		for (int i = 0; i < getModels().size(); i++) {
			getModels().get(i).render();
		}
	}
	
	void clearAll() {
		models.clear();
	}

}
