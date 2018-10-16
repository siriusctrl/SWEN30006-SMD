package mycontroller.pipeline.astar;

import java.util.*;

import mycontroller.MapRecorder;
import mycontroller.Pathway;
import utilities.Coordinate;
import world.World;

/**
 * Class for A* Algorithm implementation.
 * The task is to find the best pathway between two points on the map.
 * Implementation modified from pseudo-code online.
 */
public class AStar {
	
	public static Node[][] nodes = new Node[World.MAP_WIDTH][World.MAP_HEIGHT];

	/**
	 * A Star algorithm to find pathway between start and end coordinate.
	 * @param start start coordinate
	 * @param end end coordinate
	 * @return pathway from start to end
	 */
	public static Pathway findShortestPath(Coordinate start, Coordinate end){
		
		Pathway path = new Pathway();
		
		initialiseNodesWithCost();
		
		// Find path
        List<Node> nodePath = calculatePath(start, end);

        // Obtain coordinates path
        Stack<Coordinate> coordPath = nodePathToCoord(nodePath);

        path.setPath(coordPath);
        path.setDesti(end);
        path.setCost(nodes[end.x][end.y].getTotalCost());
        
        return path;
	}
	
	// core function for A Star algorithm path calculation.
	// make use of openQueue and closeSet for waiting to visit and already visited nodes
	private static List<Node> calculatePath(Coordinate start, Coordinate end) {
		
		Node begin = nodes[start.x][start.y];
		Node target = nodes[end.x][end.y];
		
		PriorityQueue<Node> openQueue = new PriorityQueue<>();
		HashSet<Node> closeSet = new HashSet<Node>();
		
		openQueue.add(begin);
		
		while (openQueue.size() > 0) {
			
			// obtain best node
		    Node curr = openQueue.poll();
		    closeSet.add(curr);
		
		    if (curr.equals(target)) {
		    		return backTrack(begin, target);
		    }
		    
		    List<Node> neighbours = addToNeighbours(curr); // neighbours of current node
		
		    for (Node neighbour : neighbours) {
		        if (neighbour.walkCost == MapRecorder.WALL_COST || closeSet.contains(neighbour)) continue;
		
		        int newCost = curr.gCost + calcH(curr, neighbour) ;
		        if (newCost < neighbour.gCost || !openQueue.contains(neighbour)) {
		        		// update
		            neighbour.gCost = newCost;
		            neighbour.hCost = calcH(neighbour, target);
		            neighbour.parent = curr;
		
		            if (!openQueue.contains(neighbour)) {
		            		openQueue.add(neighbour);
		            }
		        }
		    }
		}
		
		return null;
	}
	
	// get path towards target
	private static List<Node> backTrack(Node start, Node end) {
		List<Node> path = new ArrayList<Node>();
        Node currentNode = end;

        while (!currentNode.equals(start)) {
            path.add(currentNode);
            currentNode = currentNode.parent;
        }
        
        return path;
	}
	
	// path of node (end to start) to stack (top is start coordinate)
	private static Stack<Coordinate> nodePathToCoord(List<Node> nodePath) {
		Stack<Coordinate> st = new Stack<>();
		
		if (nodePath != null) {
			for (Node node : nodePath) {
				st.push(new Coordinate(node.coordinate.x, node.coordinate.y));
			}
		}
		
		return st;
	}
	
	// initialise nodes with their cost
	private static void initialiseNodesWithCost() {
		// construct nodes for every tile
		for (int i = 0; i < World.MAP_WIDTH; i++) {
			for (int j = 0; j < World.MAP_HEIGHT; j++) {
				nodes[i][j] = new Node(i, j, MapRecorder.cost[i][j]);
			}
		}
	}
	
	// calculate heuristic distance
    private static int calcH(Node end, Node coord) {
        return Math.abs(end.coordinate.x - coord.coordinate.x) + Math.abs(end.coordinate.y - coord.coordinate.y);
    }
    
    
    // obtain node neighbours
    private static List<Node> addToNeighbours(Node node) {
    	
        List<Node> neighbours = new ArrayList<Node>();
        
        Coordinate coord = node.coordinate;

        if (coord.y < World.MAP_HEIGHT - 1) neighbours.add(nodes[coord.x][coord.y + 1]); // up
        if (coord.y > 0) neighbours.add(nodes[coord.x][coord.y - 1]); // down
        if (coord.x > 0) neighbours.add(nodes[coord.x - 1][coord.y]); // left
        if (coord.x < World.MAP_WIDTH - 1) neighbours.add(nodes[coord.x + 1][coord.y]); // right

        return neighbours;
    }
}
