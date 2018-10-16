package mycontroller.strategy;

import mycontroller.MapRecorder;
import mycontroller.MyAIController;
import utilities.Coordinate;
import mycontroller.Pathway;
import mycontroller.pipeline.SimplifyPath;
import mycontroller.pipeline.Step;
import mycontroller.pipeline.astar.AStar;

import java.util.ArrayList;

public class HealStrategy implements IEscapeStrategy {
	
	public static final int HEALTH_THRESHOLD = 50;
	
	public static final int HEALTH_LEAVE_AT = 100;
	
	public HealStrategy() {
		
	}
	
	//initialise pipeline
	Step<Coordinate[], Pathway> findRoute = Step.of(AStar::findShortestPath);
	Step<Coordinate[], Pathway> simpleRoute = findRoute.add(SimplifyPath::simplifyPath); 
	
	@Override
	public Pathway findDestination(MyAIController myAIController) {
		ArrayList<Coordinate> AllLocations = new ArrayList<>();
		AllLocations.addAll(MapRecorder.healthLocations);
		AllLocations.addAll(MapRecorder.finishLocations);
		Pathway newBest = evaluateBest(AllLocations, myAIController, simpleRoute);
		if(newBest.getDesti().equals(new Coordinate(myAIController.getPosition()))) {
			return Pathway.getStays();
		}
		return newBest;
	}

	@Override
	public boolean isFinished(MyAIController myAIController) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean isTakeover(MyAIController myAIController) {
		return false;
	}
	
}
