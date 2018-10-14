package mycontroller;

import java.util.*;

import mycontroller.pipeline.Node;
import utilities.Coordinate;

public class Pathway {
	
	private PriorityQueue<Node> path = new PriorityQueue<>();
	private int cost;
	private Node desti;
	
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
	
		return p.getDesti().getCoordinate().equals(desti.getCoordinate());
	}

	/**
	 * getter for path
	 * @return a queue of path coordinates
	 */
	public PriorityQueue<Node> getPath() {
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
	public Node getDesti() {
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
	 * setter for destination
	 * @param desti the desti to set
	 */
	public void setPath(PriorityQueue<Node> queue) {
		this.path = queue;
	}
	
	/**
	 * setter for destination
	 * @return destination coordinate
	 */
	public void setDesti(Node o) {
		desti = o;
	}
	
	public Coordinate pollNext() {
		return path.poll();
	}

	public Coordinate peekNext() {
		return path.peek();
	}
}
