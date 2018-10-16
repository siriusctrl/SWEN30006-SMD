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
	
	public static final int HEALTH_THRESHOLD = 75;
	
	public HealStrategy() {
		
	}
	
	//initialise pipeline
	Step<Coordinate[], Pathway> findRoute = Step.of(AStar::findShortestPath);
	Step<Coordinate[], Pathway> simpleRoute = findRoute.add(SimplifyPath::simplifyPath); 
	
	@Override
	public Pathway findDestination(MyAIController myAIController) {
		Pathway newBest = evaluateBest(MapRecorder.healthLocations, myAIController);
		if(newBest.getDesti().equals(new Node(new Coordinate(myAIController.getPosition())))) {
			return Pathway.getStays();
		}
		return evaluateBest(MapRecorder.healthLocations, myAIController);
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
