package edu.ncsu.feddgame;

import edu.ncsu.feddgame.render.Model;

public class Button extends Model{
	
	public float[] xCoords; 	//Top Left, Top Right, Bottom Right, Bottom Left
	public float[] yCoords;
	private Runnable callback;
	/**
	 * New Button extension from Model with Runnable object for execution on click events
	 * @param coords
	 * @param tCoords
	 * @param indices
	 * @param r
	 */
	public Button(float[] coords, float[] tCoords, int[] indices, Runnable r){
		super(coords, tCoords, indices, "bgtile.png");
		this.xCoords = new float[]{coords[0], coords[3], coords[6], coords[9]};
		this.yCoords = new float[]{coords[1], coords[4], coords[7], coords[10]};
		this.callback = r;
	}
	
	/**
	 * Returns whether the button was clicked based on mouse coordinates passed
	 * @param xPos
	 * @param yPos
	 * @return
	 */
	public Boolean checkClick(float xPos, float yPos){
		if (pnpoly(xCoords, yCoords, xPos, yPos)){
			callback.run();
			return true;
		}else{
			return false;
		}
	}
	
	public void setCallback(Runnable r){
		this.callback = r;
	}
	
	/**
	 * Shamelessly stolen code from Randolph Franklin
	 * https://www.ecse.rpi.edu/Homepages/wrf/Research/Short_Notes/pnpoly.html
	 * 		Converted to Java by Jeremy Collier
	 * @param nvert
	 * @return
	 */
	static boolean pnpoly(float[] vertx, float[] verty, float testx, float testy)
	{
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

}
