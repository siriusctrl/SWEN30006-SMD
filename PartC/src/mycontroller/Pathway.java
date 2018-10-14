package mycontroller;

import java.util.Queue;
import utilities.Coordinate;

public class Pathway {
	
	private Queue<Coordinate> path;
	private int cost;
	private Coordinate desti;
	
	public static final Coordinate STAYS = new Coordinate(-1, -1);
	
	/**
	 * see if two destinations are the same.
	 * @param p coordinate of destination
	 * @return true if two destinations have the same coordinate
	 */
	public boolean isSameDesti(Pathway p) {
		if (p == null) {
			return false;
		}
	
		return p.getDesti().equals(desti);
	}

	/**
	 * getter for path
	 * @return a queue of path coordinates
	 */
	public Queue<Coordinate> getPath() {
		return path;
	}

	/**
	 * getter for cost
	 * @return cost
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * getter for destination
	 * @return destination coordinate
	 */
	public Coordinate getDesti() {
		return desti;
	}
	
	public Coordinate pollNext() {
		return path.poll();
	}
	
	public Coordinate peekNext() {
		return path.peek();
	}
}
