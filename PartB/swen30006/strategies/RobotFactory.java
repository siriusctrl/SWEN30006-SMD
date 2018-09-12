package strategies;

public class RobotFactory {
	
	public RobotAdaptor getAdaptors(String name) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		return (RobotAdaptor) Class.forName("strategies."+name).newInstance();
	}

}
