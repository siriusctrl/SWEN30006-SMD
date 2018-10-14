package mycontroller.pipeline.dijkstra;

import java.util.ArrayList;

import mycontroller.pipeline.dijkstra.Edge;
import utilities.Coordinate;

/**
 * A class for nodes for Dijkstra.
 * Stores coordinate, neighbours, minimum cost from start and previous node
 */
public class Node implements Comparable<Node> {
	
	private Coordinate coordinate;
	private ArrayList<Edge> neighbours = new ArrayList<>();
	private int minCost = 9999;
	private Node prevNode;
	
	/**
     * Constructor for node
     *
     * @param coordinate coordinate
     */
    public Node(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
	
	/**
     * Constructor for node
     *
     * @param x x coordinate
     * @param y y coordinate
     */
    public Node(int x, int y) {
        this.coordinate = new Coordinate(x, y);
    }

    public boolean equals(Node other) {
    	if(this == other) {
    		return true;
    	}
    	
    	if(other == null) {
    		return false;
    	}
    	
    	return this.coordinate.equals(other.coordinate);
    }
    
    /**
     * Compare function
     *
     * @param o node object
     * @return int value for comparison
     */
    @Override
    public int compareTo(Node o) {
        if (o == null) {
        		return -1;
        }
        return Integer.compare(minCost, o.getMinCost());
    }

	/**
	 * getter for coordinate of node
	 * @return the coordinate
	 */
	public Coordinate getCoordinate() {
		return coordinate;
	}

	/**
	 * getter for four neighbours
	 * @return the neighbours
	 */
	public ArrayList<Edge> getNeighbours() {
		return neighbours;
	}

	/**
	 * getter for min cost
	 * @return the minCost
	 */
	public int getMinCost() {
		return minCost;
	}

	/**
	 * getter for previous node
	 * @return the previous node
	 */
	public Node getPrevNode() {
		return prevNode;
	}

	/**
	 * setter for coordinate
	 * @param coordinate the coordinate to set
	 */
	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

	/**
	 * add neighbour
	 * @param neighbours the neighbours to set
	 */
	public void addToNeighbours(Edge e) {
		neighbours.add(e);
	}

	/**
	 * setter for min cost
	 * @param minCost the minCost to set
	 */
	public void setMinCost(int minCost) {
		this.minCost = minCost;
	}

	/**
	 * setter for previous node
	 * @param prevNode the prevNode to set
	 */
	public void setPrevNode(Node prevNode) {
		this.prevNode = prevNode;
	}
    
	
}