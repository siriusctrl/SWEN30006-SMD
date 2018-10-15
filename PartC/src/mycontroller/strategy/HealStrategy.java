package mycontroller.strategy;

import mycontroller.MapRecorder;
import mycontroller.MyAIController;
import utilities.Coordinate;
import mycontroller.Pathway;

import java.util.ArrayList;

public class HealStrategy implements IEscapeStrategy {
	
	public static final int HEALTH_THRESHOLD = 75;
	
	public HealStrategy() {
		
	}

	@Override
	public Pathway findDestination(MyAIController myAIController) {
		return evaluateBest(MapRecorder.healthLocations, myAIController);
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
