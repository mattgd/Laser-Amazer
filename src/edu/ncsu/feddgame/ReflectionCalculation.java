package edu.ncsu.feddgame;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2d;

import edu.ncsu.feddgame.gui.UIElement;
import edu.ncsu.feddgame.gui.UIUtils;
import edu.ncsu.feddgame.render.CreateModel;
import edu.ncsu.feddgame.render.LaserModel;
import edu.ncsu.feddgame.render.LaserStop;
import edu.ncsu.feddgame.render.Model;

public class ReflectionCalculation {

	private static ArrayList<Object[]> intersects = new ArrayList<Object[]>(); // list of arrays : [Model, xIntercept, yIntercept, slope of intersected line segment]
	private static float coords[];
	private static Object[] closest;

	/**
	 * Calculates the path of travel of the laser and sets the laser to such a
	 * path
	 * 
	 * @param laser
	 * @param models
	 */
	static Object[] reflect(LaserModel laser) {
		findIntersects(laser, GameInstance.objectManager.getModels());
		
		// If there exists at least one valid intersection
		
		if (!intersects.isEmpty()) { 
			closest = getClosestIntersection(); // Find the closest one
			
			// Pythagorean theorem to find length of vector
			float length = (float) Math.hypot((float) closest[1] - coords[0], (float) closest[2] - coords[1]); 
			
			laser.setLength(length); // Modify the laser to the correct length
			
			// Calculates reflected vector using the laser's vector and a new vector representing the side of the Model
			Vector2d resultantV = reflectionVector(laser.vect, new Vector2d(10d, 10d * (float) closest[3]));
			
			reflectionCallback((Model) closest[0], laser);

			return new Object[] { CreateModel.createReflectedLaser((float) closest[1], (float) closest[2], resultantV),
					closest[0] };
		}
		
		return null;
	}

	/**
	 * Returns a vector reflected from the incidence vector off of the surface
	 * vector
	 * 
	 * @param incidence
	 * @param surface
	 * @return
	 */
	private static Vector2d reflectionVector(Vector2d incidence, Vector2d surface) {
		Vector2d resultant = new Vector2d();
		Vector2d normal;
		
		// Check for vertical sides of blocks, because JOML is stupid about infinite slopes
		if (surface.y == Double.NEGATIVE_INFINITY) {
			normal = new Vector2d(1, 0);
		} else if (surface.y == Double.POSITIVE_INFINITY) {
			normal = new Vector2d(-1, 0);
		} else {
			// Normalize a perpendicular vector otherwise
			normal = surface.perpendicular().normalize();
		}
		normal.mul(2d * incidence.dot(normal));
		
		// Vector calculations to find reflected ray
		incidence.sub(normal, resultant);

		return resultant;
	}
	
	
	static float[] v;
	static float sl;
	static float xIntercept;
	static float yIntercept;
	
	private static void findIntersects(LaserModel laser, List<Model> models) {
		intersects.clear(); // Remove existing intersects from the list
		intersects.trimToSize();
		float slope = (float) Math.tan(laser.getAngle());
		coords = laser.getCoords();
		int xDir = laser.xDir;
		int yDir = laser.yDir;
		if (slope  < .01f && slope > 0){
			slope = .01f;
		} else if (slope > -.01f && slope < 0) {
			slope = -.01f;
		}
		
		// For all Models in the scene
		for (Model m : models) {
			// Don't intersect with lasers or UI Elements
			if (!(m instanceof LaserModel) && !(m instanceof UIElement)) { 
				for (int side = 0; side < m.sideCount; side++) {
					v = getY1X1(m, side);
					sl = getSlope(v);
					
					// If the laser is vertical
					if (slope == Float.POSITIVE_INFINITY || slope == Float.NEGATIVE_INFINITY) {
						
						// If both are vertical
						if (sl == Float.POSITIVE_INFINITY || sl == Float.NEGATIVE_INFINITY) {
							xIntercept = 100f; // They will never intercept
							yIntercept = 100f;
						} else {
							// Use separate math for vertical laser and non-vertical edge
							yIntercept = sl * (coords[0] - v[0]) + v[1];
							xIntercept = coords[0];
						}
					} else if (sl < Float.POSITIVE_INFINITY && sl > Float.NEGATIVE_INFINITY) {
						// Make sure the line isn't vertical if unsure
						
						// Calculate intersection points of the two lines
						xIntercept = (-sl * v[0] + slope * coords[0] + v[1] - coords[1]) / (slope - sl);
						yIntercept = sl * (xIntercept - v[0]) + v[1];
					} else {
						xIntercept = v[0];
						
						// If vertical, use easier methods for finding intersections
						yIntercept = slope * (xIntercept - coords[0]) + coords[1];
					}
					
					// Check if the point of intersection  is in the correct direction
					if (((xIntercept - coords[0]) * xDir >= 0) && ((yIntercept - coords[1]) * yDir >= 0)) {
						// Check if the point lies on a side of the polygon
						if (((xIntercept <= v[0]) && (xIntercept >= v[2]))
								|| ((xIntercept >= v[0]) && (xIntercept <= v[2]))) {
							
							//TODO: Check if this works for all polygons (it might, not sure)
							if (((yIntercept <= v[1]) && (yIntercept >= v[3]))
									|| ((yIntercept >= v[1]) && (yIntercept <= v[3]))) {
								intersects.add(new Object[] { m, xIntercept, yIntercept, sl });
							}
						}
					}
				}
			}
		}
	}

	static float[] midpoint;
	static float length;
	/**
	 * Returns the object data of the closest intersection point
	 * 
	 * @return
	 */
	private static Object[] getClosestIntersection() {
		// Start with a massive value
		closest = new Object[] { null, Float.MAX_VALUE / 2f, Float.MAX_VALUE / 2f, 0f };
		length = 0;
		midpoint = new float[2];
		
		ArrayList<Object[]> inters = new ArrayList<Object[]>();
		inters.addAll(intersects);
		
		// For all intersecting points
		for (Object[] b : intersects) {
			try {
				length = (float) Math.hypot((float) b[1] - coords[0], (float) b[2] - coords[1]);
				// If the new object is closer than the old one
				if ((Math.hypot((float) b[1] - coords[0], (float) b[2] - coords[1])) < (Math
						.hypot((float) closest[1] - coords[0], (float) closest[2] - coords[1]))) {
					midpoint[0] = ((float)b[1]+coords[0]) / 2f;
					midpoint[1] = ((float)b[2]+coords[1]) /2f;
					// Ensure that the new intersection point isn't at the exact same spot
					if (length > 0.05f) {
						boolean inside = false;
						
						for (Model m : GameInstance.objectManager.getModels()) {
							if (UIUtils.checkIntersection(m.vertices, midpoint[0], midpoint[1])) {
								inside = true;
							}
						}
						
						if (!inside)
							closest = b; // set the new one to the closest
					}
				}
			} catch (Exception e) {}
		}
		
		return closest;
	}

	/**
	 * Returns an array of the correct vertices for the given side of any
	 * n-sided polygon model
	 * 
	 * @param mod
	 * @param side
	 * @return
	 */
	private static float[] getY1X1(Model mod, int side) {
		if (side == 0) {
			// If the first side, use last vertex and first
			return new float[] {
					mod.vertices[mod.vertices.length - 3],
					mod.vertices[mod.vertices.length - 2],
					mod.vertices[0], mod.vertices[1]
			};
		} else if (side > 0) {
			// else use the set determined by side number
			return new float[] { mod.vertices[side * 3 - 3], mod.vertices[side * 3 - 2], mod.vertices[side * 3],
					mod.vertices[side * 3 + 1] };
		} else {
			return null;
		}
	}

	/**
	 * Calculates the slope of a line between the two passed vertices
	 * 
	 * @param vert
	 * @return
	 */
	private static float getSlope(float[] vert) {
		return (vert[3] - vert[1]) / (vert[2] - vert[0]);
	}

	/**
	 * Executes methods for certain objects in the scene specified below
	 * @param m
	 * @param l
	 */
	private static void reflectionCallback(Model m, LaserModel l) {
		if (m instanceof LaserStop) {
			((LaserStop) m).laserIntersection();
		}
	}

}
