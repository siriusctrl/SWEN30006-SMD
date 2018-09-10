package automail;

import exceptions.ExcessiveDeliveryException;
import exceptions.ItemTooHeavyException;
import exceptions.FragileItemBrokenException;
import strategies.IMailPool;
import java.util.Map;
import java.util.TreeMap;

/**
 * The robot delivers mail!
 */
public abstract class Robot {

	protected StorageTube tube;
    protected IMailDelivery delivery;
    protected final String id;
    protected MailItem deliveryItem;
	
	protected int current_floor;
    protected int destination_floor;
    protected IMailPool mailPool;
    protected int deliveryCounter;
    
    protected RobotState current_state;
    
    protected boolean receivedDispatch;
    

    /**
     * Initiates the robot's location at the start to be at the mailroom
     * also set it to be waiting for mail.
     * @param behaviour governs selection of mail items for delivery and behaviour on priority arrivals
     * @param delivery governs the final delivery
     * @param mailPool is the source of mail items
     * @param strong is whether the robot can carry heavy items
     */
    public Robot(IMailDelivery delivery, IMailPool mailPool, StorageTube tube){
    	id = "R" + hashCode();
        // current_state = RobotState.WAITING;
        this.delivery = delivery;
        this.tube = tube;
		receivedDispatch = false;
    	current_state = RobotState.RETURNING;
        current_floor = Building.MAILROOM_LOCATION;
        this.mailPool = mailPool;
        this.deliveryCounter = 0;
    }
    
    /**
     * Let the robot start dispatch
     */
    protected void dispatch() {
    	receivedDispatch = true;
    }

    /**
     * This is called on every time step
     * @throws ExcessiveDeliveryException if robot delivers more than the capacity of the tube without refilling
     */
    public abstract void step() throws ExcessiveDeliveryException, ItemTooHeavyException, FragileItemBrokenException;

    /**
     * Sets the route for the robot
     */
    protected void setRoute(Boolean strong) throws ItemTooHeavyException{
        /** Pop the item from the StorageUnit */
        deliveryItem = tube.pop();
        if (!strong && deliveryItem.weight > 2000) throw new ItemTooHeavyException(); 
        /** Set the destination floor */
        destination_floor = deliveryItem.getDestFloor();
    }

    /**
     * Generic function that moves the robot towards the destination
     * @param destination the floor towards which the robot is moving
     */
    protected void moveTowards(int destination) throws FragileItemBrokenException {
        if (deliveryItem != null && deliveryItem.getFragile() || !tube.isEmpty() && tube.peek().getFragile()) throw new FragileItemBrokenException();
        if(current_floor < destination){
            current_floor++;
        }
        else{
            current_floor--;
        }
    }
    
    protected String getIdTube() {
    	return String.format("%s(%1d/%1d)", id, tube.getSize(), tube.getCapacity());
    }
    
    /**
     * Prints out the change in state
     * @param nextState the state to which the robot is transitioning
     */
    protected void changeState(RobotState nextState){
    	if (current_state != nextState) {
            System.out.printf("T: %3d > %7s changed from %s to %s%n", Clock.Time(), getIdTube(), current_state, nextState);
    	}
    	
    	current_state = nextState;
    	
    	if(nextState == RobotState.DELIVERING){
            System.out.printf("T: %3d > %7s-> [%s]%n", Clock.Time(), getIdTube(), deliveryItem.toString());
    	}
    }

	public StorageTube getTube() {
		return tube;
	}
    
	static private int count = 0;
	static private Map<Integer, Integer> hashMap = new TreeMap<Integer, Integer>();

	@Override
	public int hashCode() {
		Integer hash0 = super.hashCode();
		Integer hash = hashMap.get(hash0);
		if (hash == null) { hash = count++; hashMap.put(hash0, hash); }
		return hash;
	}
}
