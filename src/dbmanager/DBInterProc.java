/**
 *  This aid class serves where a PApplet derived class is needed. This occurs 
 *  when calling to some methods from Processing framework such as sketchPath.  
 */

package dbmanager;

import processing.app.Base;
import processing.core.PApplet;

public class DBInterProc extends PApplet {
	
	String pathToSketch;

	public String getSketchPath() {
		
		Base.locateSketchbookFolder();
		String pathToSketch = Base.getSketchbookFolder().getAbsolutePath();
		System.out.println(pathToSketch);
		
		return pathToSketch;
	}
}
