package mycontroller.pipeline.astar;

import utilities.Coordinate;

/**
 * Node class for A* Algorithm.
 * Represents a coordinate in the map
 */
public class Node implements Comparable<Node> {
    public Coordinate coordinate;
    public Node parent;
    public int gCost; // cost to this point
    public int hCost; // heuristic cost to finish point
    public int walkCost; // cost of walking on tile

    /**
     * Constructor for Node
     *
     * @param x x coordinate
     * @param y y coordinate
     * @param walkCost cost of walking on tile, -1 means not walkable
     */
    public Node(int x, int y, int walkCost) {
        this.coordinate = new Coordinate(x, y);
        this.walkCost = walkCost;
    }

    /**
     * Constructor for Node, overload
     *
     * @param coordinate coordinate of node
     * @param parent parent node
     * @param gCost cost to this point
     * @param hCost heuristic cost to finish point
     * @param walkCost cost of walking on tile, -1 means not walkable
     */
    public Node(Coordinate coordinate, Node parent, int gCost, int hCost, int walkCost) {
        this.coordinate = coordinate;
        this.parent = parent;
        this.gCost = gCost;
        this.hCost = hCost;
        this.walkCost = walkCost;
    }
    
    /**
     * get total cost
     * @return total cost
     */
    public int getTotalCost() {
        return gCost + hCost;
    }

    /**
     * Compare function
     *
     * @param o node object
     * @return comparison result
     */
    @Override
    public int compareTo(Node o) {
        if (o == null) {
        		return -1;
        }
        
        int currCost = this.getTotalCost();
        int otherCost = o.getTotalCost();
        
        return Integer.compare(currCost, otherCost);
    }
    
    @Override
    public boolean equals(Object other) {
	    	if (this == other) {
	    		return true;
	    	}
	    	
	    	if (other == null) {
	    		return false;
	    	}
	    	
	    	if (other instanceof Node) {
	    		return this.coordinate.equals(((Node)other).coordinate);
	    	}
	    	
	    	return false;
    }
}