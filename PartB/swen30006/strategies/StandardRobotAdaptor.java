package strategies;

import automail.CarefulRobot;
import automail.IMailDelivery;

public class StandardRobotAdaptor implements RobotAdaptor {

	@Override
	public CarefulRobot create(IMailPool mailPool, IMailDelivery delivery) {
		// TODO Auto-generated method stub
		return new CarefulRobot(delivery, mailPool);
	}

}
