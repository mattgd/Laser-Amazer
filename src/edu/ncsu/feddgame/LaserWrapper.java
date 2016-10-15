package edu.ncsu.feddgame;

import java.util.LinkedList;

import edu.ncsu.feddgame.render.LaserModel;

public class LaserWrapper {
	private LinkedList<LaserModel> laserList = new LinkedList<LaserModel>();
	
	public LaserWrapper(LaserModel init){
		laserList.add(init);
	}
	
	public void calculateReflections(){
		Object[] newL = ReflectionCalculation.reflect(laserList.getLast());
		if (newL != null){
			laserList.add((LaserModel)newL[0]);
			if ((Boolean)newL[1]){
				
			}
		}
	}

}
