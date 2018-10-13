package strategies;

import automail.StandardRobot;
import automail.IMailDelivery;

/** Adaptor for standard robots **/
public class StandardRobotAdaptor implements RobotAdaptor {

	@Override
	public StandardRobot create(IMailDelivery delivery) {
		
		return new StandardRobot(delivery);
	}

}
