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
import tiles.MapTile;
import mycontroller.TileStatus;
import mycontroller.pipeline.SimplifyPath;
import mycontroller.pipeline.Step;
import mycontroller.pipeline.astar.AStar;

public class KeyCollectionStrategy implements IEscapeStrategy {
	
	public static final int MAX_EXPLORE = 5;
	
	// explore strategy
	private IEscapeStrategy explore;
	
	Step<Coordinate[], Pathway> findRoute = Step.of(AStar::findShortestPath);
	Step<Coordinate[], Pathway> simpleRoute = findRoute.add(SimplifyPath::simplifyPath);
	
	public KeyCollectionStrategy() {
		explore = EscapeStrategyFactory.getInstance().getStrategy(StrategyManager.EXPL_ST_NAME);
	}
	
	@Override
	public Pathway findDestination(MyAIController myAIController) {
		
		// explored keys' location
		HashMap<Integer, HashSet<Coordinate>> keys = MapRecorder.keysLocations;
		
		// keys got
		Set<Integer> got = myAIController.getKeys();
		
		// keys haven't got
		Set<Integer> notYet = new HashSet<Integer>();
		
		//initialise pipeline
		Step<Coordinate[], Pathway> findRoute = Step.of(AStar::findShortestPath);
		Step<Coordinate[], Pathway> simpleRoute = findRoute.add(SimplifyPath::simplifyPath);
		
		for(int key: keys.keySet()) {
			if(!got.contains(key)) {
				notYet.add(key);
			}
		}
		
		// if there's any key that is explored but not yet gotten
		if(notYet.size() > 0) {
			ArrayList<Coordinate> allCoords = new ArrayList<>();
			for(int cordKey: notYet) {
				System.out.println("all:" + allCoords);
				allCoords.addAll(keys.get(cordKey));
			}
			Pathway bestOne = evaluateBest(allCoords, myAIController, simpleRoute);
			
			// if the there's a way to the key
			if(!Pathway.getUnabletoReach().equals(bestOne)) {
				return bestOne;
			}
		}
		
		// if there's no key availble (including unreachable), explore vertices
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
