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
		// calculate distance
		
		// deal with maximum costs, interpreting unreachable keys
		ArrayList<Pathway> pathways = new ArrayList<>();
		Node startNode = new Node(new Coordinate(myAIController.getPosition()));
		for(Coordinate cr: coords) {
			pathways.add(Dijkstra.findShortestPath(startNode, new Node(cr)));
		}
		
		Pathway minPath = Collections.min(pathways);
		
		if(minPath.getCost() >= MapRecorder.UNEXPLORED_COST) {
			minPath = Pathway.getUnabletoReach();
		}
		return minPath;
	}
}
