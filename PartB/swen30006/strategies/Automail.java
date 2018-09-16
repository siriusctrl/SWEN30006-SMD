package strategies;

import java.util.*;

import automail.IMailDelivery;
import automail.Robot;
import automail.Simulation.RobotType;

/**
 * Automail class for mailpool and robot initialisation
 */
public class Automail {
	      
	public ArrayList<Robot> robots = new ArrayList<>();
    public static IMailPool mailPool;
    
    public Automail(IMailPool mailPool, IMailDelivery delivery, List<RobotType> robotTypes) {
    	
	    	/** Initialize the MailPool */
	    	Automail.mailPool = mailPool;
	    	
	    	/** Initialize robots */
	    	for(RobotType r: robotTypes) {
	    		try {
	    			// Use adaptor to create robots
	    			robots.add(RobotFactory.getInstance().getAdaptors(r.toString() + "RobotAdaptor").create(delivery));
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	    	}
	    	
    }
    
}
