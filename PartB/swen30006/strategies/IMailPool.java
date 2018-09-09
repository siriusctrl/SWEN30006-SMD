package strategies;

import automail.MailItem;
import automail.Robot;
import exceptions.FragileItemBrokenException;

/**
 * addToPool is called when there are mail items newly arrived at the building to add to the MailPool or
 * if a robot returns with some undelivered items - these are added back to the MailPool.
 * The data structure and algorithms used in the MailPool is your choice.
 * 
 */
public interface IMailPool {
	
	/**
     * Adds an item to the mail pool
     * @param mailItem the mail item being added.
     */
    void addToPool(MailItem mailItem);
    
    /**
     * load up any waiting robots with mailItems, if any.
     */
	void step() throws FragileItemBrokenException;

	/**
     * @param robot refers to a robot which has arrived back ready for more mailItems to deliver
     */	
	void registerWaiting(Robot robot);
    
	/**
     * @param robot refers to a robot which has left (with more mailItems to deliver)
     */	
	void deregisterWaiting(Robot robot);
}
