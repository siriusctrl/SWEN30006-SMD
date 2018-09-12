package automail;

import strategies.IMailPool;

public class CarefulRobot extends Robot {
	
	public static final int CAREFUL_APACITY = 3;

	public CarefulRobot(IMailDelivery delivery, IMailPool mailPool) {
		super(delivery, mailPool, true, true, CAREFUL_APACITY);
	}
}