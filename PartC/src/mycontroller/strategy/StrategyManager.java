package mycontroller.strategy;

import java.util.Map;

import java.util.HashMap;

import mycontroller.MapRecorder;
import mycontroller.MyAIController;

import mycontroller.Pathway;
import utilities.Coordinate;

public class StrategyManager {
	
	private IEscapeStrategy currentStrategy = null;
	private Map<String, IEscapeStrategy> strategies;
	
	private Coordinate currentDest;

	public static final String EXIT_ST_NAME = "Exit";
	public static final String HEAL_ST_NAME = "Heal";
	public static final String KEY_ST_NAME = "KeyCollection";
	
	public static final String EXPL_ST_NAME = "Explore";
	
	public static final String[] strategyNames = new String[] {HEAL_ST_NAME, KEY_ST_NAME, EXIT_ST_NAME};
	
	public static final String DEFAULT_ST = KEY_ST_NAME;
	
	
	public StrategyManager() {
		strategies = new HashMap<String, IEscapeStrategy>();
		initialize();
	}
	
	public void initialize() {
		for(String name: strategyNames) {
			strategies.put(name, (IEscapeStrategy) EscapeStrategyFactory.getInstance().getStrategy(name));
		}
		
		takeover(strategies.get(DEFAULT_ST));
	}
	
	/**
	 * Update strategy
	 * 
	 * @param myAIController MyAIController.
	 * @return newPathway new pathway to get to the destination
	 * */
	public Pathway findNewPathway(MyAIController myAIController) {
		Pathway newPathway = currentStrategy.findDestination(myAIController);
		
		// in case of no available pathway, get new path way from default strategy
		// normally will be the explore one
		if(newPathway.equals(Pathway.getUnabletoReach())) {
			takeover(strategies.get(DEFAULT_ST));
			newPathway = currentStrategy.findDestination(myAIController);
		}
		
		/* System.out.println("newPath=");
		if(newPathway != null) {
			for (Coordinate o : newPathway.getPath()) {
				System.out.println("---");
				System.out.println("x:" + o.x + "y:" + o.y);
				System.out.println(MapRecorder.mapTiles[o.x][o.y].getType());
				System.out.println("---");
			}
		} 
		System.out.println("="); */
		return newPathway;
	}
	
	
	/**
	 * Check if currently need a strategy taking over.
	 * 
	 * @param myAIController MyAIController.
	 * @return isStrategyChanged true if strategy is changed
	 * */
	public boolean checkAndTakeover(MyAIController myAIController) {
		if(currentStrategy == null) {
			return takeover(strategies.get(DEFAULT_ST));
		}
		
		if(checkHealTakeover(myAIController)) {
			return takeover(strategies.get(HEAL_ST_NAME));
		}
		
		if(myAIController.getKeys().size() == myAIController.numKeys()) {
			return takeover(strategies.get(EXIT_ST_NAME));
		}else{
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
	
	private boolean checkHealTakeover(MyAIController myAIController) {

		boolean needHeal = true;
		needHeal = needHeal && MapRecorder.healthLocations.size() > 0;
		// needHeal = needHeal && myAIController.getHealth() < HealStrategy.HEALTH_THRESHOLD;
		if(currentStrategy == strategies.get(HEAL_ST_NAME)) {
			needHeal = !(myAIController.getHealth() == HealStrategy.HEALTH_LEAVE_AT);
		}else {
			needHeal = needHeal && myAIController.getHealth() < HealStrategy.HEALTH_THRESHOLD;
		}
		
		// a star
		
		return needHeal;
	}
}
