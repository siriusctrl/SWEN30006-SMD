package strategies;
import automail.IMailDelivery;
import automail.Robot;

/**
 * An interface for creating robot.
 * Provides multiple benefits include protected variation, and 
 * adds a level of indirection to varying APIs in other components.
 * 
 * Currently the design is simple, but it is flexible to changes.
 */
public interface RobotAdaptor {
	
	/** Create robot 
	 * @param delivery governs the final delivery
	 **/
	Robot create(IMailDelivery delivery);
}
