package mycontroller;

import java.util.Queue;
import utilities.Coordinate;

public class Pathway {
	
	public Queue<Coordinate> path;
	public int cost;
	public Coordinate desti;
	
	public static final Coordinate STAYS = new Coordinate(-1, -1);
	
	public boolean isSameDesti(Pathway p) {
		if(p == null) {
			return false;
		}
		return desti.equals(p);
	}

}
