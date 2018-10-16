package mycontroller.pipeline;

import java.util.*;
import mycontroller.Pathway;
import utilities.Coordinate;

public class SimplifyPath {

	public static Pathway simplifyPath(Pathway path) {
		if(path == null || path.getPath().size() == 0) {
			return path;
		}
		
		Coordinate[] coors = new Coordinate[path.getPath().size()];
		System.out.println(path.getPath().size());
		Stack<Coordinate> newPath = new Stack<>();
		int pos = 0;
		int now = 0;
		
		for(int i=0;i<coors.length;i++) {
			System.out.println(path.getPath().get(i));
			coors[i] = path.getPath().get(i);
		}
		
		newPath.push(coors[pos]);
		now++;
		
		while(now < coors.length - 1) {
			if(OnStraightLine(coors[pos],coors[now]) && OnStraightLine(coors[pos], coors[now+1])) {
				coors[now] = null;
				now++;
			}else {
				pos = now;
				now++;
			}
		}
		
		for(int i = 1; i < coors.length; i++) {
			if(coors[i] != null) {
				newPath.push(coors[i]);
			}
		}
		
		path.setPath(newPath);
		
		return path;
	}
	
	/**
	 * Test if the two coordinate are on a straight line.
	 * @param c1 a coordinate
	 * @param c2 another coordinate
	 * @return true, if they one a straight line.
	 */
	public static boolean OnStraightLine(Coordinate c1, Coordinate c2) {
		if((c1.x - c2.x == 0) || c1.y - c2.y == 0 ) {
			return true;
		}else {
			return false;
		}
	}

}
