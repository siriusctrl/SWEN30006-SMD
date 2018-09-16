package automail;

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
	public WeakRobot(IMailDelivery delivery) {
		super(delivery, false, false, WEAK_CAPACITY);
	}
}