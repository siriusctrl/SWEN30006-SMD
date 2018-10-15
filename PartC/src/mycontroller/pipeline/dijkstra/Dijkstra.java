package mycontroller.pipeline.dijkstra;

import java.util.*;
import world.World;
import mycontroller.MapRecorder;
import mycontroller.Pathway;

public class Dijkstra {
	
	public static Node[][] nodes = new Node[World.MAP_WIDTH][World.MAP_HEIGHT];
	
	/**
	 * Dijkstra algorithm to find pathway between start and end coordinate.
	 * @param start start node
	 * @param end end node
	 * @return pathway from start to end
	 */
	public static Pathway findShortestPath(Node start, Node end){
		
		Pathway path = new Pathway();
		
		// construct nodes for every tile
		for (int i = 0; i < World.MAP_WIDTH; i++) {
			for (int j = 0; j < World.MAP_HEIGHT; j++) {
				nodes[i][j] = new Node(i, j);
			}
		}

		constructNeighbours();
		
		calculatePath(start);
		
		// set path
		Stack<Node> st = getPathTowards(start, end);
		System.out.println("st = "+st);
		path.setPath(st);
		path.setCost(nodes[end.getCoordinate().x][end.getCoordinate().y].getMinCost());
		path.setDesti(end);
		
		
		System.out.println(path.toString());
		return path;
	}
	
	private static void calculatePath(Node start) {
		
		HashSet<Node> visited = new HashSet<>();
		
		nodes[start.getCoordinate().x][start.getCoordinate().y].setMinCost(0);
		PriorityQueue<Node> queue = new PriorityQueue<>();
        queue.add(nodes[start.getCoordinate().x][start.getCoordinate().y]);

        // Dijkstra implementation
        while (!queue.isEmpty()) {
	        	//System.out.println(queue);
	        	Node node = queue.poll();
	        	visited.add(node);
	        	//System.out.println(node.getNeighbours().size());

            // Visit each edge from node
            for (Edge e : node.getNeighbours()) {
				Node target = e.getTarget();
				int weight = e.getWeight();
				int dTarget = node.getMinCost() + weight; // distance to target
				if (dTarget < target.getMinCost()) {
					queue.remove(nodes[target.getCoordinate().x][target.getCoordinate().y]);
					//System.out.println("cor ="+target.getCoordinate());
					nodes[target.getCoordinate().x][target.getCoordinate().y].setMinCost(dTarget);
					nodes[target.getCoordinate().x][target.getCoordinate().y].setPrevNode(nodes[node.getCoordinate().x][node.getCoordinate().y]);
					if (!visited.contains(nodes[target.getCoordinate().x][target.getCoordinate().y])) {
						queue.add(nodes[target.getCoordinate().x][target.getCoordinate().y]);
					}
				}
            }
        }
	}
	
	// get path towards target
	private static Stack<Node> getPathTowards(Node start, Node target) {
	    Stack<Node> st = new Stack<>();
	    for (Node v = nodes[target.getCoordinate().x][target.getCoordinate().y]; v != null && v != start; v = v.getPrevNode()) {
			System.out.println("v = "+v);
			System.out.println("previous= "+ v.getPrevNode());
			System.out.println("cost= " + v.getMinCost());
	    	// add to queue
	    	st.push(v);
	    }
	    st.push(start);
	    return st;
	}
	
	// construct neighbours
	private static void constructNeighbours() {
		
		for (int i = 0; i < World.MAP_WIDTH; i++) {
			for (int j = 0; j < World.MAP_HEIGHT; j++) {
				// edge to four directions if not out of bound
				if (i < World.MAP_WIDTH - 1) {
					nodes[i][j].addToNeighbours(new Edge(new Node(i + 1, j), MapRecorder.cost[i + 1][j]));
				}
				if (i > 0) {
					nodes[i][j].addToNeighbours(new Edge(new Node(i - 1, j), MapRecorder.cost[i - 1][j]));
				}
				if (j < World.MAP_HEIGHT - 1) {
					nodes[i][j].addToNeighbours(new Edge(new Node(i, j + 1), MapRecorder.cost[i][j + 1]));
				}
				if (j > 0) {
					nodes[i][j].addToNeighbours(new Edge(new Node(i, j - 1), MapRecorder.cost[i][j - 1]));
				}
			}
		}

	}
}
