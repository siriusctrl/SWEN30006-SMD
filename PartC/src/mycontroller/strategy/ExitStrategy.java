package mycontroller.strategy;

import mycontroller.MyAIController;
import utilities.Coordinate;

import java.util.ArrayList;

public class ExitStrategy implements IEscapeStrategy {

	@Override
	public Coordinate findDestination(MyAIController myAIController) {
		ArrayList<Coordinate> finishCoords = myAIController.mapRecorder.finishLocations;
		return evaluateBest(finishCoords);
	}
	
	private Coordinate evaluateBest(ArrayList<Coordinate> coords) {
		if(coords.size() > 0) {
			return coords.get(0);
		}
		return null;
	}

	@Override
	public boolean isFinished(MyAIController myAIController) {
		return false;
	}
	
	@Override
	public boolean isTakeover(MyAIController myAIController) {
		return false;
	}

}
