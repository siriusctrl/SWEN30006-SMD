package automail;

/**
 * Big robot subclass, inherits from Robot.
 */
public class BigRobot extends Robot {
	
	/** Big robot tube capacity **/
	public static final int BIG_CAPACITY = 6;

	/**
     * Constructor for a big robot
     * @param delivery governs the final delivery
     * @param mailPool is the source of mail items
     */
	public BigRobot(IMailDelivery delivery) {
		super(delivery, true, false, BIG_CAPACITY);
	}
}