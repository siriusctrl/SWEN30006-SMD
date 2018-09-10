package automail;

import exceptions.ExcessiveDeliveryException;
import exceptions.FragileItemBrokenException;
import exceptions.ItemTooHeavyException;
import strategies.IMailPool;

public class WeekRobot extends Robot {
	
	public static final int NORMAL_CAPACITY = 4;
	public static final boolean STRONG = false;


	
	public WeekRobot(IMailDelivery delivery, IMailPool mailPool) {
		super(delivery, mailPool, new StorageTube(NORMAL_CAPACITY));
	}
	
	public void setRoute() throws ItemTooHeavyException {
		super.setRoute(STRONG);
	}
	
	public void moveTowards(int destination) throws FragileItemBrokenException {
		super.moveTowards(destination);
	}
	
	public void changeState(RobotState nextState){
		super.changeState(nextState);
	}
	
    public void dispatch() {
    	super.dispatch();
    }

	@Override
	public void step() throws ExcessiveDeliveryException, ItemTooHeavyException, FragileItemBrokenException {
    	switch(current_state) {
		/** This state is triggered when the robot is returning to the mailroom after a delivery */
		case RETURNING:
			/** If its current position is at the mailroom, then the robot should change state */
            if(current_floor == Building.MAILROOM_LOCATION){
            	while(!tube.isEmpty()) {
            		MailItem mailItem = tube.pop();
            		mailPool.addToPool(mailItem);
                    System.out.printf("T: %3d > old addToPool [%s]%n", Clock.Time(), mailItem.toString());
            	}
    			/** Tell the sorter the robot is ready */
    			mailPool.registerWaiting(this);
            	changeState(RobotState.WAITING);
            } else {
            	/** If the robot is not at the mailroom floor yet, then move towards it! */
                moveTowards(Building.MAILROOM_LOCATION);
            	break;
            }
		case WAITING:
            /** If the StorageTube is ready and the Robot is waiting in the mailroom then start the delivery */
            if(!tube.isEmpty() && receivedDispatch){
            	receivedDispatch = false;
            	deliveryCounter = 0; // reset delivery counter
    			setRoute();
    			mailPool.deregisterWaiting(this);
            	changeState(RobotState.DELIVERING);
            }
            break;
		case DELIVERING:
			if(current_floor == destination_floor){ // If already here drop off either way
                /** Delivery complete, report this to the simulator! */
                delivery.deliver(deliveryItem);
                deliveryCounter++;
                if(deliveryCounter > tube.getCapacity()){  // Implies a simulation bug
                	throw new ExcessiveDeliveryException();
                }
                /** Check if want to return, i.e. if there are no more items in the tube*/
                if(tube.isEmpty()){
                	changeState(RobotState.RETURNING);
                }
                else{
                    /** If there are more items, set the robot's route to the location to deliver the item */
                    setRoute();
                    changeState(RobotState.DELIVERING);
                }
			} else {
        		/** The robot is not at the destination yet, move towards it! */
                moveTowards(destination_floor);
			}
            break;
	}	
	}
	
}
