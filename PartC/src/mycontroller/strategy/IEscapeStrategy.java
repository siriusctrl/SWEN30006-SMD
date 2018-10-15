package mycontroller.strategy;

import mycontroller.MapRecorder;
import mycontroller.MyAIController;
import utilities.Coordinate;
import mycontroller.Pathway;
import mycontroller.pipeline.dijkstra.Dijkstra;
import mycontroller.pipeline.dijkstra.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

public interface IEscapeStrategy {
	
	/**
	 * Get the moving destination for the car based on the strategy
	 * @return a Coordinate object which indicate the destination of moving
	 */
	Pathway findDestination(MyAIController myAIController);
	
	/**
	 * Check whether the strategy has finished it's work
	 * @return whether the strategy is finished 
	 */
	boolean isFinished(MyAIController myAIController);
	
	/**
	 * Check if this strategy needs to takeover the control.
	 * */
	default boolean isTakeover(MyAIController myAIController) {return false;};
	
	default Pathway evaluateBest(List<Coordinate> coords, MyAIController myAIController) {
		
		// all pathways
		ArrayList<Pathway> pathways = new ArrayList<>();
		Node startNode = new Node(new Coordinate(myAIController.getPosition()));
		
		// for all coordinates, find their shortest path to current car
		for(Coordinate cr: coords) {
			Pathway nodePath = Dijkstra.findShortestPath(startNode, new Node(cr));
			System.out.println(nodePath.getCost());
			pathways.add(nodePath);
		}
		
		Pathway minPath = Collections.min(pathways);
		
		// if the shortest path's cost is more than the max cost, then the path is unreachable now.
		if(minPath.getCost() >= MapRecorder.UNEXPLORED_COST) {
			minPath = Pathway.getUnabletoReach();
		}
		
		return minPath;
	}
}
