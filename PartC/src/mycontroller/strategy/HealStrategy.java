package mycontroller.strategy;

import mycontroller.MapRecorder;
import mycontroller.MyAIController;
import utilities.Coordinate;
import mycontroller.Pathway;

import java.util.ArrayList;
import java.util.Queue;

public class HealStrategy implements IEscapeStrategy {
	
	public static final int HEALTH_THRESHOLD = 75;

	@Override
	public Pathway findDestination(MyAIController myAIController) {
		return evaluateBest(MapRecorder.healthLocations);
	}
	
	private Pathway evaluateBest(ArrayList<Coordinate> coords) {
		// normally using a star to find a nearest
		
		// check if now is on the health tile
		return new Pathway();
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
