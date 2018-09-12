package automail;

import strategies.IMailPool;

public class BigRobot extends Robot {
	
	public static final int BIG_CAPACITY = 6;

	public BigRobot(IMailDelivery delivery, IMailPool mailPool) {
		super(delivery, mailPool, true, false, BIG_CAPACITY);
	}
}