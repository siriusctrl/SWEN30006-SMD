package mycontroller.strategy;

import mycontroller.MapRecorder;
import mycontroller.MyAIController;
import utilities.Coordinate;
import mycontroller.Pathway;
import mycontroller.pipeline.Step;

import java.util.*;

/**
 * Strategy interface
 */
public interface IEscapeStrategy {
	
	/**
	 * Get the moving destination for the car based on the strategy
	 * @param myAIController the car controller
	 * @return pathway which indicates the direction of moving
	 */
	Pathway findDestination(MyAIController myAIController);
	
	/**
	 * Check whether the strategy has finished it's work
	 * @param myAIController the car controller
	 * @return whether the strategy is finished 
	 */
	boolean isFinished(MyAIController myAIController);
	
	/**
	 * Check if this strategy needs to takeover the control.
	 * @param myAIController the car controller
	 * @return whether takeover or not 
	 */
	default boolean isTakeover(MyAIController myAIController) {
		return false;
	}
	
	/**
	 * Evaluates the best pathway
	 * @param coords list of coordinates
	 * @param myAIController the car controller
	 * @param simplePath simplified path
	 * @return the best pathway the car should take now
	 */
	default Pathway evaluateBest(List<Coordinate> coords, MyAIController myAIController, Step<Coordinate[], Pathway> simplePath) {
		
		// all pathways
		ArrayList<Pathway> pathways = new ArrayList<>();
		Coordinate startNode = new Coordinate(myAIController.getPosition());
		
		// for all coordinates, find their shortest path to current car
		for(Coordinate cr: coords) {
			Pathway nodePath = simplePath.execute(new Coordinate[] {startNode, cr});
			if(nodePath != null && nodePath.getPath().size() > 0) {
				pathways.add(nodePath);
			}
		}
		
		Pathway minPath = Pathway.getUnabletoReach();
		if(pathways.size() > 0) {
			Collections.sort(pathways);
			minPath = pathways.get(pathways.size() - 1);
		}
		
		// if the shortest path's cost is more than the max cost, then the path is unreachable now.
		if(minPath == null || minPath.getCost() >= MapRecorder.UNEXPLORED_COST || minPath.getPath().size() == 0) {
			minPath = Pathway.getUnabletoReach();
		}
		
		return minPath;
	}
	
	/**
	 * Check takeover strategy
	 * @param st strategy
	 * @param myAIController the car controller
	 * @return true if the strategy is now in control
	 */
	boolean checkTakeover(IEscapeStrategy st, MyAIController myAIController);
}
