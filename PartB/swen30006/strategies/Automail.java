package strategies;

import java.util.ArrayList;

import automail.IMailDelivery;
import automail.Robot;

public class Automail {
	      
    public ArrayList<Robot> robot = new ArrayList<>();
    public IMailPool mailPool;
    
    public Automail(IMailPool mailPool, IMailDelivery delivery) {
    	// Swap between simple provided strategies and your strategies here
    	    	
    	/** Initialize the MailPool */
    	
    	this.mailPool = mailPool;
    	
        /** Initialize the RobotAction */
    	boolean weak = false;  // Can't handle more than 2000 grams
    	boolean strong = true; // Can handle any weight that arrives at the building
    	boolean big = true; // Can take more item at a time
    	boolean small = false; // Stating for not a big robot.
    	boolean careful = true; // Can handle fragile item, but can only take up to 3 item at a time and is half the speed of other robots. 
    	boolean careless = false; // Can't handle fragile item.
    	
    	
    }
}

