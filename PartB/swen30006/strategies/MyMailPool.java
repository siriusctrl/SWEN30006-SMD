package strategies;

import java.util.LinkedList;
import java.util.Comparator;
import java.util.ListIterator;
import java.util.function.Consumer;

import automail.MailItem;
import automail.PriorityMailItem;
import automail.Robot;
import automail.StorageTube;
import exceptions.TubeFullException;
import exceptions.FragileItemBrokenException;

/** MailPool implementation **/
public class MyMailPool implements IMailPool {
	
	public static final int MAX_WEIGHT = 2000;
	private boolean hasCarefulRobot = false;
	
	private class Item {
		int priority;
		int destination;
		boolean heavy;
		boolean fragile;
		MailItem mailItem;
		// Use stable sort to keep arrival time relative positions
		
		public Item(MailItem mailItem) {
			priority = (mailItem instanceof PriorityMailItem) ? ((PriorityMailItem) mailItem).getPriorityLevel() : 1;
			heavy = mailItem.getWeight() >= MAX_WEIGHT;
			destination = mailItem.getDestFloor();
			fragile = mailItem.getFragile();
			this.mailItem = mailItem;
		}
	}
	
	public class ItemComparator implements Comparator<Item> {
		@Override
		public int compare(Item i1, Item i2) {
			int order = 0;
			if (i1.priority < i2.priority) {
				order = 1;
			} else if (i1.priority > i2.priority) {
				order = -1;
			} else if (i1.destination < i2.destination) {
				order = 1;
			} else if (i1.destination > i2.destination) {
				order = -1;
			}
			return order;
		}
	}
	
	// pool stores non-fragile items while fragilePool only stores fragile items
	private LinkedList<Item> pool;
	private LinkedList<Item> fragilePool;
	private LinkedList<Robot> robots;

	public MyMailPool(){
		// Start empty
		pool = new LinkedList<Item>();
		fragilePool = new LinkedList<Item>();
		robots = new LinkedList<Robot>();
	}

	public void addToPool(MailItem mailItem) throws FragileItemBrokenException {
		Item item = new Item(mailItem);
		if (item.fragile) {
			if (!hasCarefulRobot) {
				// If there is no careful robot while there is any fragile item, should throw FragileItemBrokenException.
				throw new FragileItemBrokenException();
			}
			fragilePool.add(item);
		} else {
			pool.add(item);
		}
		pool.sort(new ItemComparator());
		fragilePool.sort(new ItemComparator());
	}
	
	@Override
	public void step() throws FragileItemBrokenException {
		for (Robot robot: (Iterable<Robot>) robots::iterator) { 
			fillStorageTube(robot); 
		}
	}
	
	private void fillStorageTube(Robot robot) throws FragileItemBrokenException {
		StorageTube tube = robot.getTube();
		StorageTube temp = new StorageTube(tube.getMaxCapacity());
		try { 
			if (!robot.isStrong()) {
				// Weak robot, can only take light and not-fragile items
				ListIterator<Item> i = pool.listIterator();
				while(i.hasNext() && temp.getSize() < temp.getMaxCapacity()) {
					Item item = i.next();
					if (!item.heavy && !item.fragile) {
						temp.addItem(item.mailItem);
						i.remove();
					}
				}
			} else if (robot.isCareful()) {
				// Careful robot, takes one fragile item first, if there is any
				if (!fragilePool.isEmpty()) {
					Item item = fragilePool.remove();
					temp.addItem(item.mailItem);
				}
				// Then take normal items
				while (temp.getSize() < temp.getMaxCapacity() && !pool.isEmpty()) {
					Item item = pool.remove();
					temp.addItem(item.mailItem);
				}
			} else {
				// Strong or big robot
				while(temp.getSize() < temp.getMaxCapacity() && !pool.isEmpty() ) {
					Item item = pool.remove();
					temp.addItem(item.mailItem);
				}
			}
			
			if (temp.getSize() > 0) {
				while (!temp.isEmpty()) {
					tube.addItem(temp.pop());
				}
				robot.dispatch();
			}
		}
		catch(TubeFullException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void registerWaiting(Robot robot) {
		if (robot.isCareful()) {
			// there is a careful robot
			hasCarefulRobot = true;
		}
		if (robot.isStrong()) {
			robots.add(robot); 
		} else {
			// weak robot last as want more efficient delivery with highest priorities
			robots.addLast(robot);
		}
	}

	@Override
	public void deregisterWaiting(Robot robot) {
		robots.remove(robot);
	}

}
