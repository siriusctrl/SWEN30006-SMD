package mycontroller.strategy;

import java.util.Map;

import mycontroller.MapRecorder;
import mycontroller.MyAIController;
import utilities.Coordinate;

import mycontroller.Pathway;

public class StrategyManager {
	
	private IEscapeStrategy currentStrategy = null;
	private Map<String, IEscapeStrategy> strategies;
	
	public static final String EXIT_ST_NAME = "Exit";
	public static final String HEAL_ST_NAME = "Heal";
	public static final String KEY_ST_NAME = "KeyCollection";
	
	public static final String[] strategyNames = new String[] {EXIT_ST_NAME, HEAL_ST_NAME, KEY_ST_NAME};
	
	public static final String DEFAULT_ST = KEY_ST_NAME;
	
	private Pathway curPathway = null;
	
	
	public StrategyManager() {
		initialize();
	}
	
	public void initialize() {
		for(String name: strategyNames) {
			strategies.put(name, EscapeStrategyFactory.getInstance().getStrategy(name));
		}
	}
	
	/**
	 * Update strategy
	 * 
	 * @param myAIController MyAIController.
	 * @return isTargetChanged true if the destination changes.
	 * */
	public boolean update(MyAIController myAIController) {
		checkAndTakeover(myAIController);
		Pathway newPathway = currentStrategy.findDestination(myAIController);
		curPathway = newPathway;
		if(curPathway == null || !newPathway.isSameDesti(curPathway)) {
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * Check if currently need a strategy taking over.
	 * 
	 * @param myAIController MyAIController.
	 * @return isStrategyChanged true if strategy is changed
	 * */
	private boolean checkAndTakeover(MyAIController myAIController) {
		if(currentStrategy == null) {
			return takeover(strategies.get(DEFAULT_ST));
		}
		
		if(checkHealTakeover(myAIController)) {
			return takeover(strategies.get(HEAL_ST_NAME));
		}
		
		if(myAIController.getKeys().size() == myAIController.numKeys()) {
			return takeover(strategies.get(EXIT_ST_NAME));
		}else {
			return takeover(strategies.get(KEY_ST_NAME));
		}

	}
	
	private boolean takeover(IEscapeStrategy newStrategy) {
		if(currentStrategy == newStrategy) {
			return false;
		}
		currentStrategy = newStrategy;
		return true;
	}
	
	public Pathway getPathway() {
		return curPathway;
	}
	
	private boolean checkHealTakeover(MyAIController myAIController) {

		boolean needHeal = true;
		needHeal = needHeal && MapRecorder.healthLocations.size() > 0;
		needHeal = needHeal && myAIController.getHealth() < HealStrategy.HEALTH_THRESHOLD;
		
		// a star
		
		return needHeal;
	}
}
