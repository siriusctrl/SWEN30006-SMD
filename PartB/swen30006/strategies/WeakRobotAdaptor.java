package strategies;

import automail.IMailDelivery;
import automail.WeakRobot;

public class WeakRobotAdaptor implements RobotAdapter {

	@Override
	public WeakRobot create(IMailPool mailPool, IMailDelivery delivery) {
		return new WeakRobot(delivery, mailPool);
	}

}
