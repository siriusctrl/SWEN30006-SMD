package mycontroller.strategy;

import mycontroller.MapRecorder;
import mycontroller.MyAIController;
import utilities.Coordinate;
import mycontroller.Pathway;
import mycontroller.pipeline.Step;

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
	default boolean isTakeover(MyAIController myAIController) {return false;}
	
	default Pathway evaluateBest(List<Coordinate> coords, MyAIController myAIController, Step<Coordinate[], Pathway> simplePath) {
		
		// all pathways
		ArrayList<Pathway> pathways = new ArrayList<>();
		Coordinate startNode = new Coordinate(myAIController.getPosition());
		
		// for all coordinates, find their shortest path to current car
		for(Coordinate cr: coords) {
			// System.out.println("startNode: " + startNode + "cr: " + cr);
			Pathway nodePath = simplePath.execute(new Coordinate[] {startNode, cr});
			/* System.out.println("--------------------------------");
			System.out.println(nodePath.getPath());
			System.out.println("--------------------------------");
			System.out.println(nodePath.getCost()); */
			pathways.add(nodePath);
		}
		
		Collections.sort(pathways);
		Pathway minPath = Pathway.getUnabletoReach();
		if(pathways.size() > 0) {
			minPath = pathways.get(pathways.size() - 1);
		}
		
		
		
		System.out.println(pathways);
		System.out.println(minPath);
		
		// if the shortest path's cost is more than the max cost, then the path is unreachable now.
		if(minPath == null || minPath.getCost() >= MapRecorder.UNEXPLORED_COST || minPath.getPath().size() == 0) {
			minPath = Pathway.getUnabletoReach();
		}
		
		return minPath;
	}
}
