package strategies;

import automail.BigRobot;
import automail.IMailDelivery;

public class BigRobotAdaptor implements RobotAdaptor {
	
	@Override
	public BigRobot create(IMailPool mailPool, IMailDelivery delivery) {
		return new BigRobot(delivery, mailPool);
	}

}
