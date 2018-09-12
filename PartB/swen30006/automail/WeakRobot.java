package automail;

import strategies.IMailPool;

public class WeakRobot extends Robot {
	
	public static final int WEAK_CAPACITY = 4;

	public WeakRobot(IMailDelivery delivery, IMailPool mailPool) {
		super(delivery, mailPool, false, false, WEAK_CAPACITY);
	}
}