package strategies;

import automail.IMailDelivery;
import automail.WeakRobot;

public class WeakRobotAdaptor implements RobotAdaptor {

	@Override
	public WeakRobot create(IMailPool mailPool, IMailDelivery delivery) {
		return new WeakRobot(delivery, mailPool);
	}

}
