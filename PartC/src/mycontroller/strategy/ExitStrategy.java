package mycontroller.strategy;

import mycontroller.MapRecorder;
import mycontroller.MyAIController;
import utilities.Coordinate;
import mycontroller.Pathway;
import mycontroller.pipeline.dijkstra.Dijkstra;
import mycontroller.pipeline.dijkstra.Node;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Collections;

public class ExitStrategy implements IEscapeStrategy {

	@Override
	public Pathway findDestination(MyAIController myAIController) {
		ArrayList<Coordinate> finishCoords = MapRecorder.finishLocations;
		return evaluateBest(finishCoords, myAIController);
	}
	
	private Pathway evaluateBest(ArrayList<Coordinate> coords, MyAIController myAIController) {
		ArrayList<Pathway> pathways = new ArrayList<>();
		Node startNode = new Node(new Coordinate(myAIController.getPosition()));
		for(Coordinate cr: coords) {
			pathways.add(Dijkstra.findShortestPath(startNode, new Node(cr)));
		}
		return Collections.min(pathways);
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
