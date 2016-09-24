package edu.ncsu.feddgame;

import java.util.ArrayList;
import java.util.List;

public class ReflectionCalculation {
	
	static List<Object[]> intersects = new ArrayList<Object[]>(); 	//list of arrays : [Model, xIntercept, yIntercept, slope of intersected line segment]
	static float coords[];
	
	/**
	 * Calculates the path of travel of the laser and sets the laser to such a path
	 * @param laser
	 * @param models
	 */
	public static void reflect(LaserModel laser, ArrayList<Model> models){
		findIntersects(laser, models);
		if (!intersects.isEmpty()){ 	//If there exists at least one valid intersection
			Object[] closest = getClosestIntersection(); 	//Find the closest one
			float length = (float) Math.hypot((float)closest[1] - coords[0], (float)closest[2] - coords[1]);
			laser.setLength(length); 	//Modify the laser to the correct length
			float angle = laser.getAngle() - (float)closest[3];
			float newAngle = (float)Math.atan((Math.sin(angle) * -laser.yDir) / (Math.cos(angle) * -laser.xDir));
			System.out.println(laser.getAngle() + "-> " + angle);
			
			if ((float)closest[1] < Float.MAX_VALUE / 3 && (float)closest[2] < Float.MAX_VALUE / 3)
			CreatePolygon.createReflectedLaser((float)closest[1], (float)closest[2], newAngle, 1f);
			
		}else{
			//Not needed once the walls are implemented
			//laser.setEndPoint(100f * laser.xDir, laser.getAngle() * 100f * laser.yDir);
		}
	}
	
	public static void findIntersects(LaserModel laser, ArrayList<Model> models){
		intersects.clear();
		float slope = (float)Math.tan(laser.getAngle());
		coords = laser.getCoords();
		int xDir = laser.xDir;
		int yDir = laser.yDir;
		List<Model> mods = new ArrayList<Model>();
		for(Model m : models){ 	//Crude way to avoid concurrentModification errors when testing by adding objects to the scene
			mods.add(m);
		}
		for (Model m : mods){ 	//For all Models in the scene
			if (!(m instanceof LaserModel)){ 	//Don't intersect with lasers
				for (int side = 0; side < 4; side++){
					float[] v = getY1X1(m, side);
					float sl = getSlope(v);
					float xIntercept;
					float yIntercept;
					if (slope == Float.POSITIVE_INFINITY || slope == Float.NEGATIVE_INFINITY){ 	//If the laser is vertical
						if (sl == Float.POSITIVE_INFINITY || sl == Float.NEGATIVE_INFINITY){ 	//If both are vertical
							xIntercept = 100f; 	//They will never intercept
							yIntercept = 100f;
						}else{
							yIntercept = sl * (coords[0] - v[0]) + v[1]; 	//Use separate math for vertical laser and non-vertical edge
							xIntercept = coords[0];
						}
					}else if (sl < Float.POSITIVE_INFINITY && sl > Float.NEGATIVE_INFINITY){ 	//if not, make sure the line isn't vertical
						xIntercept = (-sl * v[0] + slope * coords[0] + v[1] - coords[1]) / (slope - sl); 	//Calculate intersection points of the two lines
						yIntercept = sl * (xIntercept - v[0]) + v[1];
					}else{
						xIntercept = v[0];
						yIntercept = slope * (xIntercept - coords[0]) + coords[1]; 	//If vertical, use easier methods for finding intersections
					}
					
					
					if (((xIntercept - coords[0])* xDir >= 0) && ((yIntercept - coords[1]) * yDir >= 0)){ 	//Check it the point of intersection is in the correct direction
						//CreatePolygon.createBox(xIntercept, yIntercept, .5f); 	//Enable to visualize all possible intersections
						if (((xIntercept <= v[0]) && (xIntercept >= v[2])) || ((xIntercept >= v[0]) && (xIntercept <= v[2]))){ 	//Check if the point lies on a side of the polygon
							if (((yIntercept <= v[1]) && (yIntercept >= v[3])) || ((yIntercept >= v[1]) && (yIntercept <= v[3]))){ 	//TODO: check if this works for all polygons (it might, not sure)
								intersects.add(new Object[]{m, xIntercept, yIntercept, sl});		
							}
						}
					}
				}
			}
		}
	}
	/**
	 * Returns the object data of the closest intersection point
	 * @return
	 */
	private static Object[] getClosestIntersection(){
		Object[] closest = new Object[]{null, Float.MAX_VALUE / 2, Float.MAX_VALUE / 2, 0}; 	//Start with a massively large value
		for(Object[] b : intersects){ 	//For all intersecting points
			if ((Math.hypot((float)b[1] - coords[0], (float)b[2] - coords[1])) < (Math.hypot((float)closest[1] - coords[0], (float)closest[2] - coords[1]))) 	//if the new object is closer than the old one
				closest = b; 	//set the new one to the closest
		}
		return closest;
	}
	
	/**
	 * Returns an array of the correct vertices for the given side
	 * @param mod
	 * @param side
	 * @return
	 */
	private static float[] getY1X1(Model mod, int side){
		float[] vertices = null;
		switch (side){
		case 0: vertices = new float[]{mod.vertices[9], mod.vertices[10], mod.vertices[0], mod.vertices[1]}; 	//Left side
		break;
		case 1: vertices = new float[]{mod.vertices[0], mod.vertices[1], mod.vertices[3], mod.vertices[4]}; 	//Top side
		break;
		case 2: vertices = new float[]{mod.vertices[6], mod.vertices[7], mod.vertices[3], mod.vertices[4]}; 	//Right side 	//flipped so that the vector isn't facing upwards
		break;
		case 3: vertices = new float[]{mod.vertices[6], mod.vertices[7], mod.vertices[9], mod.vertices[10]}; 	//Bottom side
		break;
		}
		return vertices;
	}
	
	/**
	 * Calculates the slope of a line between the two passed vertices
	 * @param vert
	 * @return
	 */
	private static float getSlope(float[] vert){
		return (vert[3] - vert[1])/(vert[2] - vert[0]);
	}

}
