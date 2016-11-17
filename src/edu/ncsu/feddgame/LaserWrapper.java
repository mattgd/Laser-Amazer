package edu.ncsu.feddgame;

import java.util.LinkedList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import edu.ncsu.feddgame.render.LaserModel;
import edu.ncsu.feddgame.render.LaserStart;
import edu.ncsu.feddgame.render.LaserStop;
import edu.ncsu.feddgame.render.Wall;

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
	 * Runs recursively the reflection calculations on the last laser in the list
	 */
	private void calculateReflections(){
		//if (GameInstance.window.shiftHeld) System.out.println("<< " + laserList.indexOf(laserList.getLast()) + " >>");
		newL = ReflectionCalculation.reflect(laserList.getLast()); 	//reflect the last laser in the list
		if (newL != null && (!(newL[1] instanceof Wall) && !(newL[1] instanceof LaserStop) && !(newL[1] instanceof LaserStart)) && laserList.size() < 20){ 	//if the returned reflection is neither null nor off a wall
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
	
	public void rotateStart(float angle, float xOffset, float yOffset){
		float[] c = laserList.getFirst().getCoords();
		float x = c[0] - xOffset, y = c[1] - yOffset;
		float[] g = new float[]{
				 (float)(x * Math.cos(angle) - y * Math.sin(angle) + xOffset),
				 (float)(x * Math.sin(angle) + y * Math.cos(angle) + yOffset)
		};
		laserList.getFirst().setCoords(g);
		float ang = (float)(laserList.getFirst().getAngle() + angle) % ((float)Math.PI * 2f);
		laserList.getFirst().setAngle((ang < 0) ? (float)(Math.PI * 2f + ang) : ang);
	}

}
