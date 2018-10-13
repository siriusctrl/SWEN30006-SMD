package mycontroller.strategy;

import mycontroller.MyAIController;
import utilities.Coordinate;

public class KeyCollectionStrategy implements IEscapeStrategy {

	@Override
	public Coordinate findDestination(MyAIController myAIController) {
		// TODO Auto-generated method stub
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
