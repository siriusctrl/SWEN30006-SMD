package automail;

import exceptions.FragileItemBrokenException;
import exceptions.TubeFullException;

import java.util.Stack;

/**
 * The storage tube carried by the robot.
 */
public class StorageTube{
	
    //public static final int CAREFUL_CAPACITY = 3;
    //public static final int NORMAL_CAPACITY = 4;
    //public static final int BIG_CAPACITY = 6;
    
    public Stack<MailItem> tube;
    private int capacity;

    /**
     * Constructor for the storage tube
     */
    public StorageTube(int capacity){
        this.tube = new Stack<MailItem>();
        this.capacity = capacity;
    }

    /**
     * @return if the storage tube is full
     */
    public boolean isFull(){
        return tube.size() == capacity;
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
        if(tube.size() < capacity){
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
     * @return the maximum number of mail which the tube can take.
     */
    public int getCapacity() {
    	return capacity;
    }
    
    /** 
     * @return the first item in the storage tube (after removing it)
     */
    public MailItem pop(){
        return tube.pop();
    }

}
