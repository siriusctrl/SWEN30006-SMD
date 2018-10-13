package mycontroller.strategy;

import mycontroller.MyAIController;
import utilities.Coordinate;

public interface IEscapeStrategy {
	
	/**
	 * Get the moving destination for the car based on the strategy
	 * @return a Coordinate object which indicate the destination of moving
	 */
	Coordinate findDestination(MyAIController myAIController);
	
	/**
	 * Check whether the strategy has finished it's work
	 * @return whether the strategy is finished 
	 */
	boolean isFinished(MyAIController myAIController);
	
	/**
	 * Check if this strategy needs to takeover the control.
	 * */
	default boolean isTakeover(MyAIController myAIController) {return false;};
}
