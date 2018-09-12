package strategies;

import java.util.*;

import automail.IMailDelivery;
import automail.Robot;
import automail.Simulation.RobotType;

public class Automail {
	      
	public ArrayList<Robot> robots = new ArrayList<>();
    public IMailPool mailPool;
    public RobotFactory robotFactory = new RobotFactory();
    
    public Automail(IMailPool mailPool, IMailDelivery delivery, List<RobotType> robotTypes) {
    	// Swap between simple provided strategies and your strategies here
    	    	
    	/** Initialize the MailPool */
    	
    	this.mailPool = mailPool;
    	
    	/** Initialize robots */
    	for(RobotType r: robotTypes) {
    		try {
				robots.add(robotFactory.getAdaptors(r.toString()+"RobotAdaptor").create(mailPool, delivery));
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
