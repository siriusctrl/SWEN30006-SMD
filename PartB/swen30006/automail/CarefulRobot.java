package automail;

import strategies.IMailPool;

/**
 * Careful robot subclass, inherits from Robot.
 * Careful robot can take fragile items, one at a time.
 */
public class CarefulRobot extends Robot {
	
	/** Careful robot tube capacity **/
	public static final int CAREFUL_APACITY = 3;

	/**
     * Constructor for a careful robot
     * @param delivery governs the final delivery
     * @param mailPool is the source of mail items
     */
	public CarefulRobot(IMailDelivery delivery, IMailPool mailPool) {
		super(delivery, mailPool, true, true, CAREFUL_APACITY);
	}
}