package strategies;

import java.util.*;

import automail.IMailDelivery;
import automail.Robot;
import automail.Simulation.RobotType;
import automail.BigRobot;
import automail.CarefulRobot;;

public class Automail {
	      
	public ArrayList<Robot> robots = new ArrayList<>();
    public IMailPool mailPool;
    
    public Automail(IMailPool mailPool, IMailDelivery delivery, List<RobotType> robotTypes) {
    	// Swap between simple provided strategies and your strategies here
    	    	
    	/** Initialize the MailPool */
    	
    	this.mailPool = mailPool;
    	
    	/** Initialize robots */
    	for(RobotType r: robotTypes) {
    		robots.add(Class<?>))
    	}
    	
    }
    
}
