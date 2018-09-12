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
    	
    	/** Initialize robots */
    
    }
    
}
