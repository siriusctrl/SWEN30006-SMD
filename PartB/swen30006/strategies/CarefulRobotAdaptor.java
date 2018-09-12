package strategies;

import automail.CarefulRobot;
import automail.IMailDelivery;

/** Adaptor for careful robots **/
public class CarefulRobotAdaptor implements RobotAdaptor {

	@Override
	public CarefulRobot create(IMailPool mailPool, IMailDelivery delivery) {
		
		return new CarefulRobot(delivery, mailPool);
	}

}
