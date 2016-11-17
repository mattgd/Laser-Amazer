package edu.ncsu.feddgame.gui;

public class UIUtils {
	/**
	 * Shamelessly stolen code from Randolph Franklin
	 * https://www.ecse.rpi.edu/Homepages/wrf/Research/Short_Notes/pnpoly.html
	 * 		Converted to Java by Jeremy Collier
	 * @param nvert
	 * @return
	 */
	public static boolean pnpoly(float[] vertx, float[] verty, float testx, float testy){
		int nvert = vertx.length;
	    int i, j;
	    boolean c = false;
	    for (i = 0, j = nvert-1; i < nvert; j = i++) {
	        if ( ((verty[i]>testy) != (verty[j]>testy)) &&
	                (testx < (vertx[j]-vertx[i]) * (testy-verty[i]) / (verty[j]-verty[i]) + vertx[i]) )
	            c = !c;
	    }
	    return c;
	}
	
	/**
	 * Determines whether the test coordinates passed are within the coordinates of the polygon passed
	 * @param coords
	 * @param testX
	 * @param testY
	 * @return
	 */
	public static boolean checkIntersection(float[] coords, float testX, float testY){
		int sideCount = coords.length / 3;
		float[] xCoords = new float[sideCount];
		float[] yCoords = new float[sideCount];
		for (int i = 0; i < sideCount; i++){
			xCoords[i] = coords[i * 3];
		}
		for (int i = 0; i < sideCount; i++){
			yCoords[i] = coords[i * 3 + 1];
		}
		return pnpoly(xCoords, yCoords, testX, testY);
	}
	
	/**
	 * Converts cursor coordinates into coordinates on the Cartesian coordinate system in-game
	 * @param xPos
	 * @param yPos
	 * @return
	 */
	public static float[] convertToWorldspace(float xPos, float yPos, int winWidth, int winHeight){
		float xP = (xPos - winWidth/2f) * (20f / winWidth);
		float yP = (-yPos + winHeight/2f) * (20f / winHeight);
		return new float[]{xP, yP};
	}
	
}
