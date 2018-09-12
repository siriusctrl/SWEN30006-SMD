package automail;

import strategies.IMailPool;

public class StandardRobot extends Robot {
	
	public static final int STANDARD_CAPACITY = 4;

	public StandardRobot(IMailDelivery delivery, IMailPool mailPool) {
		super(delivery, mailPool, true, false, STANDARD_CAPACITY);
	}
}