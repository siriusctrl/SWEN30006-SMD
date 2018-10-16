package mycontroller;

import java.util.*;

import utilities.Coordinate;

public class Pathway implements Comparable<Pathway> {
	
	private Stack<Coordinate> path = new Stack<>();
	private int cost;
	private Coordinate desti;
	
	//public static final Node STAYS = new Node(new Coordinate(-1, -1));
	private static Pathway cannot_reach_now;
	private static Pathway stays_now;
	
	public static Pathway getUnabletoReach() {
		if(cannot_reach_now == null) {
			cannot_reach_now = new Pathway();
			cannot_reach_now.cost = -1;
		}
		return cannot_reach_now;
	}
	
	/*
	public static Pathway getStays() {
		if(stays_now == null) {
			stays_now = new Pathway();
			stays_now.desti = STAYS;
		}
		return stays_now;
	}*/
	
	public boolean equals(Object o) {
		if(o == null) {
			return false;
		}
		
		if(o instanceof Pathway) {
			if(this == o) {
				return true;
			}
			
			return isSameDesti((Pathway)o) && cost == ((Pathway)o).cost;
		}
		
		return false;
	}
	
	/**
	 * see if two destinations are the same.
	 * @param p coordinate of destination
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
	
	public int compareTo(Pathway pathway) {
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
	 * @param stack the path to set
	 */
	public void setPath(Stack<Coordinate> coords) {
		this.path = coords;
	}
	
	/**
	 * setter for destination
	 * @return destination coordinate
	 */
	public void setDesti(Coordinate o) {
		desti = o;
	}
	
	public Coordinate removeNext() {
		return path.pop();
	}

	public Coordinate getNext() {
		return path.peek();
	}
	
	public String toString() {
		Iterator<Coordinate> coord = path.iterator();
		String res = "path = ";
		while(coord.hasNext()) {
			res += coord.next().toString() + " ";
		}
		return res+"\n";
	}
}
