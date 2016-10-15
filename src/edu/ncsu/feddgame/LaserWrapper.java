package edu.ncsu.feddgame;

import java.util.LinkedList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import edu.ncsu.feddgame.render.LaserModel;

public class LaserWrapper {
	private LinkedList<LaserModel> laserList = new LinkedList<LaserModel>(); 	//Linked List of all lasers
	Object[] newL;
	private final ReadWriteLock lock = new ReentrantReadWriteLock(); 	//ReadWriteLock for synchronizing the access of the list between threads
	LaserModel root; 	//Root node in the laser list
	
	/**
	 * New LaserWrapper with the starting laser init
	 * @param init
	 */
	public LaserWrapper(LaserModel init){
		laserList.add(init);
		root = init;
	}
	/**
	 * Runs recursively the reflectioncalculations on the last laser in the list
	 */
	private void calculateReflections(){
		newL = ReflectionCalculation.reflect(laserList.getLast()); 	//reflect the last laser in the list
		if (newL != null && (Boolean)newL[1]){ 	//if the returned reflection is neither null nor off a wall
			laserList.add((LaserModel)newL[0]); 	//add the new laser and
			calculateReflections(); 				//reflect again with that new one
		}
	}
	/**
	 * Run everything necessary to generate a path for the lasers
	 */
	public void runReflections(){
		lock.writeLock().lock(); 	//Lock access to the list
		laserList.clear(); 	//Clear and restart the list
		laserList.add(root);
		calculateReflections(); 	//Run reflections
		lock.writeLock().unlock(); 	//Unlock access
	}
	
	/**
	 * Renders all the lasers in the system
	 */
	public void render(){
		lock.readLock().lock(); 	//Locks access to the list
		for(LaserModel m : laserList){ 	//Renders all lasers in the list
			m.render();
		}
		lock.readLock().unlock(); 	//Unlocks the list
	}

}
