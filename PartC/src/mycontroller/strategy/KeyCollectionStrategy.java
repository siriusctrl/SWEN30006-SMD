package mycontroller.strategy;

import mycontroller.MapRecorder;
import mycontroller.MyAIController;
import utilities.Coordinate;
import world.Car;
import world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import mycontroller.Pathway;
import mycontroller.pipeline.dijkstra.Dijkstra;
import mycontroller.pipeline.dijkstra.Node;
import tiles.MapTile;
import mycontroller.TileStatus;

public class KeyCollectionStrategy implements IEscapeStrategy {
	
	public static final int MAX_EXPLORE = 5;
	private IEscapeStrategy explore;
	
	public KeyCollectionStrategy() {
		explore = EscapeStrategyFactory.getInstance().getStrategy(StrategyManager.EXPL_ST_NAME);
	}

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
			Pathway bestOne = evaluateBest(allCoords, myAIController);
			
			if(!Pathway.getUnabletoReach().equals(bestOne)) {
				return bestOne;
			}
		}
		
		return explore.findDestination(myAIController);
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
