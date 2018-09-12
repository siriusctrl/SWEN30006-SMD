package automail;

import exceptions.FragileItemBrokenException;
import exceptions.TubeFullException;

import java.util.Stack;

/**
 * The storage tube carried by the robot.
 */
public class StorageTube {

    private int maxCapacity;
    public Stack<MailItem> tube;

    /**
     * Constructor for the storage tube
     */
    public StorageTube(int maxCapacity){
        this.tube = new Stack<MailItem>();
        this.maxCapacity = maxCapacity;
    }

    /**
     * @return if the storage tube is full
     */
    public boolean isFull(){
        return tube.size() == maxCapacity;
    }

    /**
     * @return if the storage tube is empty
     */
    public boolean isEmpty(){
        return tube.isEmpty();
    }
    
    /**
     * @return the first item in the storage tube (without removing it)
     */
    public MailItem peek() {
    	return tube.peek();
    }

    /**
     * Add an item to the tube
     * @param item The item being added
     * @throws TubeFullException thrown if an item is added which exceeds the capacity
     */
    public void addItem(MailItem item) throws TubeFullException, FragileItemBrokenException {
    		if (tube.size() < maxCapacity){
	        	if (tube.isEmpty()) {
	        		tube.add(item);
	        	} else if (item.getFragile() || tube.peek().getFragile()) {
	        		throw new FragileItemBrokenException();
	        	} else {
	        		tube.add(item);
	        	}
    		} else {
            throw new TubeFullException();
        }
    	}

    /** @return the size of the tube **/
    public int getSize(){
    		return tube.size();
    }
    
    /** 
     * @return the first item in the storage tube (after removing it)
     */
    public MailItem pop(){
        return tube.pop();
    }

    /** @return the maximum capacity of the tube **/
    public int getMaxCapacity() {
    		return maxCapacity;
    }
}
