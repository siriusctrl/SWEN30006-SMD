package mycontroller.strategy;

public class EscapeStrategyFactory {
	private static EscapeStrategyFactory escapeStrategyFactory;
	
	public static EscapeStrategyFactory getInstance() {
		if (escapeStrategyFactory == null) {
			escapeStrategyFactory = new EscapeStrategyFactory();
		}
		return escapeStrategyFactory;
	}
	
	
}
