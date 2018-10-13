package mycontroller.strategy;

import mycontroller.MyAIController;
import utilities.Coordinate;

public class HealStrategy implements IEscapeStrategy {
	
	public static final int HEALTH_THRESHOLD = 75;

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
