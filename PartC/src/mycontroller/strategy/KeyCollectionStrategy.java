package mycontroller.strategy;

import mycontroller.MyAIController;
import utilities.Coordinate;

import java.util.ArrayList;

import java.util.Set;
import java.util.HashMap;

public class KeyCollectionStrategy implements IEscapeStrategy {

	@Override
	public Coordinate findDestination(MyAIController myAIController) {
		// ???
		HashMap<Integer, ArrayList<Coordinate>> keys = myAIController.mapRecorder.keysLocations;
		Set<Integer> got = myAIController.getKeys();
		
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
