package mycontroller.strategy;

import mycontroller.MapRecorder;
import mycontroller.MyAIController;
import utilities.Coordinate;

import java.util.*;

import mycontroller.Pathway;
import mycontroller.pipeline.SimplifyPath;
import mycontroller.pipeline.Step;
import mycontroller.pipeline.astar.AStar;

/**
 * Key collection strategy
 *
 */
public class KeyCollectionStrategy implements IEscapeStrategy {
	
	public static final int MAX_EXPLORE = 5;
	
	// explore strategy
	private IEscapeStrategy explore;
	
	// initialise pipeline
	Step<Coordinate[], Pathway> findRoute = Step.of(AStar::findShortestPath);
	Step<Coordinate[], Pathway> simpleRoute = findRoute.add(SimplifyPath::simplifyPath);
	
	/**
	 * Constructor
	 */
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
		return false;
	}
	
	@Override
	public boolean checkTakeover(IEscapeStrategy st, MyAIController myAIController) {
		return true;
	}

}
