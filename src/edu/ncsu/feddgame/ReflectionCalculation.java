package edu.ncsu.feddgame;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2d;

import edu.ncsu.feddgame.gui.UIUtils;
import edu.ncsu.feddgame.render.CreateModel;
import edu.ncsu.feddgame.render.LaserModel;
import edu.ncsu.feddgame.render.LaserStop;
import edu.ncsu.feddgame.render.Model;
import edu.ncsu.feddgame.render.Wall;

public class ReflectionCalculation {
	
	static List<Object[]> intersects = new ArrayList<Object[]>(); 	//list of arrays : [Model, xIntercept, yIntercept, slope of intersected line segment]
	static float coords[];
	
	/**
	 * Calculates the path of travel of the laser and sets the laser to such a path
	 * @param laser
	 * @param models
	 */
	public static Object[] reflect(LaserModel laser){
		//if (GameInstance.window.shiftHeld) System.out.println("Input: " + laser.getCoords()[0] + ", " + laser.getCoords()[1]);
		findIntersects(laser, GameInstance.objectManager.getModels());
		if (!intersects.isEmpty()){ 	//If there exists at least one valid intersection
			Object[] closest = getClosestIntersection(); 	//Find the closest one
			float length = (float) Math.hypot((float)closest[1] - coords[0], (float)closest[2] - coords[1]); 	//Pythagorean theorem to find length of vector
			laser.setLength(length); 	//Modify the laser to the correct length
			Vector2d resultantV = reflectionVector(laser.vect, new Vector2d(10d, 10d * (float)closest[3])); 	//Calculates reflected vector using the laser's vector and then a new vector representing the side of the model
			reflectionCallback((Model)closest[0], laser);
			//if (GameInstance.window.shiftHeld) System.out.println("Final: " + closest[1] + ", " + closest[2]);
			return new Object[]{CreateModel.createReflectedLaser((float)closest[1], (float)closest[2], resultantV), closest[0]};
			
		}
		return null;
	}
	
	/**
	 * Returns a vector reflected from the incidence vector off of the surface vector
	 * @param incidence
	 * @param surface
	 * @return
	 */
	public static Vector2d reflectionVector(Vector2d incidence, Vector2d surface){
		Vector2d resultant = new Vector2d();
		Vector2d normal;
		if (surface.y == Double.NEGATIVE_INFINITY){ 	//Check for vertical sides of blocks, because JOML is stupid about infinite slopes
			normal = new Vector2d(1, 0);
		}else if (surface.y == Double.POSITIVE_INFINITY){
			normal = new Vector2d(-1, 0);
		}else{
			normal = surface.perpendicular().normalize(); 	//Normalize a perpendicular vector otherwise
		}
		normal.mul(2d * incidence.dot(normal));
		incidence.sub(normal, resultant); 	//Vector calculations to find reflected ray
		//resultant.mul(100); 	//Increase magnitude off-screen 				//Can be removed once walls are implemented
		return resultant;
		
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
						if (GameInstance.window.shiftHeld) System.out.println("Potential: " + xIntercept + ", " + yIntercept);
						if (((xIntercept <= v[0]) && (xIntercept >= v[2])) || ((xIntercept >= v[0]) && (xIntercept <= v[2]))){ 	//Check if the point lies on a side of the polygon
							if (((yIntercept <= v[1]) && (yIntercept >= v[3])) || ((yIntercept >= v[1]) && (yIntercept <= v[3]))){ 	//TODO: check if this works for all polygons (it might, not sure)
								//if (GameInstance.window.shiftHeld) System.out.println("Added: " + xIntercept + ", " + yIntercept);
								intersects.add(new Object[]{m, xIntercept, yIntercept, sl});							}
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
		Object[] closest = new Object[]{null, Float.MAX_VALUE / 2f, Float.MAX_VALUE / 2f, 0f}; 	//Start with a massively large value
		float length;
		for(Object[] b : intersects){ 	//For all intersecting points
			length = (float) Math.hypot((float)b[1] - coords[0], (float)b[2] - coords[1]);
			if ((Math.hypot((float)b[1] - coords[0], (float)b[2] - coords[1])) < (Math.hypot((float)closest[1] - coords[0], (float)closest[2] - coords[1]))) 	//if the new object is closer than the old one
				if (length > 0.05f) 	//Ensure that the new intersection point isn't at the exact same spot
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
		case 2: vertices = new float[]{mod.vertices[3], mod.vertices[4], mod.vertices[6], mod.vertices[7]}; 	//Right side 	//flipped so that the vector isn't facing upwards
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
	
	private static void reflectionCallback(Model m, LaserModel l){
		//System.out.println(m.getClass());
		Class<? extends Model> c = m.getClass();
		if (c == Wall.class){
			//System.out.println("wall, dude");
		}else if(c == LaserStop.class){
			((LaserStop)m).laserIntersection();
		}
	}

}
