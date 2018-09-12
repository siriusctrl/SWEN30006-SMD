package strategies;

/**
 * Robot factory, stores information about robot.
 * Currently there is only robot adaptor due to simple specification, but if future changes
 * are complex it can manage them easily.
 */
public class RobotFactory {
	
	/**
	 * Robot adaptor, for robot creation
	 * @param name Robot name
	 * @return RobotAdaptor class
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public RobotAdaptor getAdaptors(String name) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		return (RobotAdaptor) Class.forName("strategies."+name).newInstance();
	}

}
