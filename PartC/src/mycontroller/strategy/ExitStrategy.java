package mycontroller.strategy;

import mycontroller.MapRecorder;
import mycontroller.MyAIController;
import utilities.Coordinate;
import mycontroller.Pathway;
import mycontroller.pipeline.Dijkstra;

import java.util.ArrayList;
import java.util.Queue;

public class ExitStrategy implements IEscapeStrategy {

	@Override
	public Pathway findDestination(MyAIController myAIController) {
		ArrayList<Coordinate> finishCoords = MapRecorder.finishLocations;
		return evaluateBest(finishCoords);
	}
	
	private Pathway evaluateBest(ArrayList<Coordinate> coords) {
		return Dijkstra.findShortestPath();
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
