package gui;

public class UIUtils {
	/**
	 * Shamelessly stolen code from Randolph Franklin
	 * https://www.ecse.rpi.edu/Homepages/wrf/Research/Short_Notes/pnpoly.html
	 * 		Converted to Java by Jeremy Collier
	 * @param nvert
	 * @return
	 */
	public static boolean pnpoly(float[] vertx, float[] verty, float testx, float testy)
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
	
	/**
	 * Converts cursor coordinates into coordinates on the cartesian coordinate system in-game
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
