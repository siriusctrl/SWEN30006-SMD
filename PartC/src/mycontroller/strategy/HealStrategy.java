package mycontroller.strategy;

import mycontroller.MyAIController;
import utilities.Coordinate;

import java.util.ArrayList;

public class HealStrategy implements IEscapeStrategy {
	
	public static final int HEALTH_THRESHOLD = 75;

	@Override
	public Coordinate findDestination(MyAIController myAIController) {
		return evaluateBest(myAIController.mapRecorder.healthLocations);
	}
	
	private Coordinate evaluateBest(ArrayList<Coordinate> coords) {
		// normally using a star to find a nearest
		
		// check if now is on the health tile
		if(coords.size() > 0) {
			return coords.get(0);
		}
		return null;
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
