package mycontroller.strategy;

public class EscapeStrategyFactory {
	private static EscapeStrategyFactory escapeStrategyFactory;
	
	/**
	 * Get instance of the factory, allow only one instance to be initialised.
	 * @return instance of EscapeStrategyFactory.
	 */
	public static EscapeStrategyFactory getInstance() {
		if (escapeStrategyFactory == null) {
			escapeStrategyFactory = new EscapeStrategyFactory();
		}
		return escapeStrategyFactory;
	}
	
	/**
	 * Initiate different strategy classes.
	 * @param name Strategy name
	 * @return strategy class
	 */
	public IEscapeStrategy getStrategy(String name) {
		IEscapeStrategy strategy = null;
		try {
			System.out.println(name + "Strategy");
			strategy = (IEscapeStrategy) Class.forName("mycontroller.strategy." + name + "Strategy").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return strategy;
	}
}
