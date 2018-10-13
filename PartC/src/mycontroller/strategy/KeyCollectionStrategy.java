package mycontroller.strategy;

import mycontroller.MapRecorder;
import mycontroller.MyAIController;
import utilities.Coordinate;

import java.util.ArrayList;

import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

import mycontroller.pipeline.Dijkstra;
import mycontroller.Pathway;

public class KeyCollectionStrategy implements IEscapeStrategy {

	@Override
	public Pathway findDestination(MyAIController myAIController) {
		// ???
		HashMap<Integer, ArrayList<Coordinate>> keys = MapRecorder.keysLocations;
		Set<Integer> got = myAIController.getKeys();
		
		Set<Integer> notYet = new HashSet<Integer>();
		
		for(int key: keys.keySet()) {
			if(!got.contains(key)) {
				notYet.add(key);
			}
		}
		
		if(notYet.size() > 0) {
			ArrayList<Coordinate> allCoords = new ArrayList<>();
			for(int cordKey: notYet) {
				allCoords.addAll(keys.get(cordKey));
			}
			Pathway bestOne = evaluateBest(allCoords);
			if(bestOne != null) {
				return bestOne;
			}
		}else {
			return findExploreTargets(myAIController);
		}
		
		return null;
	}
	
	public Pathway evaluateBest(ArrayList<Coordinate> coords) {
		// calculate distance
		
		// deal with maximum costs, interpreting unreachable keys
		return Dijkstra.findShortestPath();
	}
	
	private Pathway findExploreTargets(MyAIController myAIController) {
		// further decide
		return Dijkstra.findShortestPath();
		
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
