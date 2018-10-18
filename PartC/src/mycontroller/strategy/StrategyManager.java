package mycontroller.strategy;

import java.util.Map;
import java.util.HashMap;
import mycontroller.MyAIController;
import mycontroller.Pathway;

/**
 * Manages all strategies, including takeover, altering and decision change
 *
 */
public class StrategyManager {
	
	private IEscapeStrategy currentStrategy = null;
	private Map<String, IEscapeStrategy> strategies;
	
	/**
	 * EXIT_ST_NAME is the name of ExitStrategy
	 * */
	public static final String EXIT_ST_NAME = "Exit";
	
	/**
	 * HEAL_ST_NAME is the name of HealStrategy
	 * */
	public static final String HEAL_ST_NAME = "Heal";
	
	/**
	 * KEY_ST_NAME is the name of KeyCollectionStrategy
	 * */
	public static final String KEY_ST_NAME = "KeyCollection";
	
	/**
	 * EXPL_ST_NAME is the name of ExploreStrategy
	 * */
	public static final String EXPL_ST_NAME = "Explore";
	
	/**
	 * collection of all strategy names, in the order of priority for checking takeover
	 * */
	public static final String[] strategyNames = new String[] {HEAL_ST_NAME, EXIT_ST_NAME, KEY_ST_NAME};
	
	/**
	 * DEFAULT_ST is the default one
	 * */
	public static final String DEFAULT_ST = KEY_ST_NAME;
	
	/**
	 * Constructor for StrategyManager
	 */
	public StrategyManager() {
		strategies = new HashMap<String, IEscapeStrategy>();
		initialize();
	}
	
	/**
	 * Initialize by putting strategies to a map
	 */
	public void initialize() {
		for(String name: strategyNames) {
			strategies.put(name, (IEscapeStrategy) EscapeStrategyFactory.getInstance().getStrategy(name));
		}
		
		takeover(DEFAULT_ST);
	}
	
	/**
	 * Update strategy
	 * 
	 * @param myAIController MyAIController.
	 * @return new pathway to get to the destination
	 * */
	public Pathway findNewPathway(MyAIController myAIController) {
		Pathway newPathway = currentStrategy.findDestination(myAIController);
		
		// in case of no available pathway, get new path way from default strategy
		// normally will be the explore one
		if(newPathway.equals(Pathway.getUnabletoReach())) {
			takeover(DEFAULT_ST);
			newPathway = currentStrategy.findDestination(myAIController);
		}

		return newPathway;
	}
	
	
	/**
	 * Check if currently need a strategy taking over.
	 * 
	 * @param myAIController MyAIController.
	 * @return true if strategy is changed
	 * */
	public boolean checkAndTakeover(MyAIController myAIController) {
		
		if(currentStrategy == null) {
			return takeover(DEFAULT_ST);
		}
		
		for(int index = 0; index < strategyNames.length; index ++) {
			if(strategies.get(strategyNames[index]).checkTakeover(currentStrategy, myAIController)) {
				return takeover(strategyNames[index]);
			}
		}
		
		return false;

	}
	
	// check if after taking over, the strategy is changed
	private boolean takeover(String strategyName) {
		
		IEscapeStrategy newStrategy = strategies.get(strategyName);
		
		if(currentStrategy == newStrategy) {
			return false;
		}
		currentStrategy = newStrategy;
		
		return true;
	}
}
