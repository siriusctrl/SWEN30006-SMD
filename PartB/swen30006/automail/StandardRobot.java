package automail;

import strategies.IMailPool;

/**
 * Standard robot subclass, inherits from Robot.
 */
public class StandardRobot extends Robot {
	
	/** Standard robot tube capacity **/
	public static final int STANDARD_CAPACITY = 4;

	/**
     * Constructor for a standard robot
     * @param delivery governs the final delivery
     * @param mailPool is the source of mail items
     */
	public StandardRobot(IMailDelivery delivery, IMailPool mailPool) {
		super(delivery, mailPool, true, false, STANDARD_CAPACITY);
	}
}