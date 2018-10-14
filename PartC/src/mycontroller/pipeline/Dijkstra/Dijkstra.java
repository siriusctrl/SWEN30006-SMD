package mycontroller.pipeline.dijkstra;

import java.util.*;
import world.World;
import mycontroller.MapRecorder;
import mycontroller.Pathway;

public class Dijkstra {
	
	/**
	 * Dijkstra algorithm to find pathway between start and end coordinate.
	 * @param start start node
	 * @param end end node
	 * @return
	 */
	public static Pathway findShortestPath(Node start, Node end){
		Pathway path = new Pathway();
		
		// construct nodes for every tile
		Node[][] nodes = new Node[World.MAP_HEIGHT][World.MAP_WIDTH];
		for (int i = 0; i < World.MAP_WIDTH; i++) {
			for (int j = 0; j < World.MAP_HEIGHT; j++) {
				nodes[i][j] = new Node(i, j);
			}
		}
		
		// construct neighbours
		nodes = constructNeighbours(nodes);
		
		calculatePath(start);
		PriorityQueue<Node> queue = getPathTowards(end);
		path.setPath(queue);
		path.setCost(nodes[end.getCoordinate().x][end.getCoordinate().y].getMinCost());
		path.setDesti(end);
		
		return path;
	}
	
	private static void calculatePath(Node start) {
		
		start.setMinCost(0);
		PriorityQueue<Node> queue = new PriorityQueue<Node>();
        queue.add(start);

        // Dijkstra implementation
        while (!queue.isEmpty()) {
        		// get node
            Node node = queue.poll();
            
            // Visit each edge from u
            for (Edge e : node.getNeighbours()) {
                Node target = e.getTarget();
                int weight = e.getWeight();
                int dTarget = node.getMinCost() + weight; // distance to target
                if (dTarget < target.getMinCost()) {
                		// update
					queue.remove(target);
					target.setMinCost(dTarget);
					target.setPrevNode(node);
					queue.add(target);
                }
            }
        }
	}
	
	// get path towards target
	private static PriorityQueue<Node> getPathTowards(Node target) {
		
	    PriorityQueue<Node> queue = new PriorityQueue<>();
	    for (Node v = target; v != null; v = v.getPrevNode()) {
	    		// add to queue
	    		queue.add(v);
	    }
	    return queue;
	}
	
	// construct neighbours
	private static Node[][] constructNeighbours(Node[][] nodes) {
		
		for (int i = 0; i < World.MAP_WIDTH; i++) {
			for (int j = 0; j < World.MAP_HEIGHT; j++) {
				// edge to four directions if not out of bound
				Node curr = nodes[i][j];
				if (i < World.MAP_WIDTH - 1) {
					curr.addToNeighbours(new Edge(new Node(i + 1, j), MapRecorder.cost[i + 1][j]));
				}
				if (i > 0) {
					curr.addToNeighbours(new Edge(new Node(i - 1, j), MapRecorder.cost[i - 1][j]));
				}
				if (j < World.MAP_HEIGHT - 1) {
					curr.addToNeighbours(new Edge(new Node(i, j + 1), MapRecorder.cost[i][j + 1]));
				}
				if (j > 0) {
					curr.addToNeighbours(new Edge(new Node(i, j - 1), MapRecorder.cost[i][j - 1]));
				}
			}
		}
		
		return nodes;
	}
}
