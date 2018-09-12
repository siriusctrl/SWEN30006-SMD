package strategies;

import automail.CarefulRobot;
import automail.IMailDelivery;

public class CarefulRobotAdapter implements RobotAdapter {

	@Override
	public CarefulRobot create(IMailPool mailPool, IMailDelivery delivery) {
		
		return new CarefulRobot(delivery,mailPool);
	}

}
