package mycontroller;

import java.util.*;

import utilities.Coordinate;

/**
 * Class to record the path from start to target coordinates.
 * The pathway will be recorded as a stack of coordinates.
 */
public class Pathway implements Comparable<Pathway> {
	
	private Stack<Coordinate> path = new Stack<>();
	private int cost; // total cost
	private Coordinate desti; // target point
	
	public static final Coordinate STAYS = new Coordinate(-1, -1); // health location
	private static Pathway cannot_reach_now;
	private static Pathway stays_now;
	
	/**
	 * Check if a destination is unable to reach
	 * @return null if pathway unable to reach
	 */
	public static Pathway getUnabletoReach() {
		if (cannot_reach_now == null) {
			cannot_reach_now = new Pathway();
			cannot_reach_now.cost = Integer.MAX_VALUE;
		}
		return cannot_reach_now;
	}
	
	/**
	 * check health location.
	 * @return pathway with health destination
	 */
	public static Pathway getStays() {
		if(stays_now == null) {
			stays_now = new Pathway();
			stays_now.desti = STAYS;
		}
		return stays_now;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		
		if (o == null) {
			return false;
		}
		
		if (o instanceof Pathway) {
			return isSameDesti((Pathway)o) && cost == ((Pathway)o).getCost();
		}
		
		return false;
	}
	
	/**
	 * see if two destinations are the same.
	 * @param p pathway
	 * @return true if two destinations have the same coordinate
	 */
	public boolean isSameDesti(Pathway p) {
		if (p == null || desti == null) {
			return false;
		}
		
		if(p.getDesti() == null) {
			return desti == null;
		}
	
		return desti.equals(p.getDesti());
	}
	
	/**
     * Compare function
     *
     * @param pathway pathway object
     * @return comparison result
     */
    @Override
	public int compareTo(Pathway pathway) {
		if(cost == pathway.cost) {
			return 0;
		}
		
		if(cost == Integer.MAX_VALUE) {
			return 1;
		}
		
		if (pathway.cost == Integer.MAX_VALUE) {
			return -1;
		}
		
		return cost - pathway.cost;
	}

	/**
	 * getter for path
	 * @return a stack of path coordinates
	 */
	public Stack<Coordinate> getPath() {
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

	/**
	 * setter for cost
	 * @param cost the cost to set
	 */
	public void setCost(int cost) {
		this.cost = cost;
	}

	/**
	 * setter for path
	 * @param coords the path to set
	 */
	public void setPath(Stack<Coordinate> coords) {
		this.path = coords;
	}
	
	/**
	 * setter for destination
	 * @param o destination coordinate
	 */
	public void setDesti(Coordinate o) {
		desti = o;
	}
	
	/**
	 * Remove from path
	 * @return popped coordinate
	 */
	public Coordinate removeNext() {
		return path.pop();
	}

	/**
	 * Get next coordinate in pathway
	 * @return Coordinate peek
	 */
	public Coordinate getNext() {
		return path.peek();
	}

}
