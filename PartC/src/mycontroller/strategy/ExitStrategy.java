package mycontroller.strategy;

import mycontroller.MapRecorder;
import mycontroller.MyAIController;
import utilities.Coordinate;
import mycontroller.Pathway;
import mycontroller.pipeline.SimplifyPath;
import mycontroller.pipeline.Step;
import mycontroller.pipeline.astar.AStar;

import java.util.*;

/**
 * Exit strategy for car
 *
 */
public class ExitStrategy implements IEscapeStrategy {

	// initialise the pipeline
	Step<Coordinate[], Pathway> findRoute = Step.of(AStar::findShortestPath);
	Step<Coordinate[], Pathway> simpleRoute = findRoute.add(SimplifyPath::simplifyPath); 
	
	@Override
	public Pathway findDestination(MyAIController myAIController) {
		ArrayList<Coordinate> finishCoords = MapRecorder.finishLocations;
		return evaluateBest(finishCoords, myAIController, simpleRoute);
	}

	@Override
	public boolean isFinished(MyAIController myAIController) {
		return false;
	}
	
	@Override
	public boolean checkTakeover(IEscapeStrategy st, MyAIController myAIController) {
		boolean check = (myAIController.getKeys().size() == myAIController.numKeys());
		Pathway shortestPath = findDestination(myAIController);
		check = check && !Pathway.getUnabletoReach().equals(shortestPath);
		return check;
	}

}
