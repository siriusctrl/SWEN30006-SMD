package automail;

import strategies.IMailPool;

/**
 * Weak robot subclass, inherits from Robot.
 */
public class WeakRobot extends Robot {
	
	/** Weak robot tube capacity **/
	public static final int WEAK_CAPACITY = 4;

	/**
     * Constructor for a weak robot
     * @param delivery governs the final delivery
     * @param mailPool is the source of mail items
     */
	public WeakRobot(IMailDelivery delivery, IMailPool mailPool) {
		super(delivery, mailPool, false, false, WEAK_CAPACITY);
	}
}