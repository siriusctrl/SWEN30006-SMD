package mycontroller.pipeline.dijkstra;

/**
 * A class for directed edge from one node to another.
 * The weight of the edge is just the weight of target tile.
 */
public class Edge {
	
	// target node
	public final Node target;
	// edge weight
	private int weight;
	
	/**
	 * Edge constructor
	 * 
	 * @param target edge target
	 * @param weight edge weight, or the target tile wcost
	 */
	public Edge(Node target, int weight) {
		this.target = target;
		this.weight = weight;
	}

	/**
	 * getter for weight
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * setter for weight
	 * @param weight the weight to set
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}

	/**
	 * getter for target
	 * @return the target
	 */
	public Node getTarget() {
		return target;
	}
	
	
}