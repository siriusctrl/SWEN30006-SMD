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

public class MyMailPool implements IMailPool {
	
	public static final int MAX_WEIGHT = 2000;
	
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
			fragile = mailItem.getFragile();
			destination = mailItem.getDestFloor();
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

	public MyMailPool() {
		// Start empty
		pool = new LinkedList<Item>();
		robots = new LinkedList<Robot>();
	}

	public void addToPool(MailItem mailItem) {
		Item item = new Item(mailItem);
		if (item.fragile) {
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
			// Get as many items as available or as fit
			if (!robot.isStrong()) {
				// Weak robot, can only take light and not-fragile items
				ListIterator<Item> i = pool.listIterator();
				while(temp.getSize() < temp.getMaxCapacity()) {
					Item item = i.next();
					if (!item.heavy && !item.fragile) {
						temp.addItem(item.mailItem);
						i.remove();
					}
				}
			} else if (robot.isCareful()) {
				// Careful robot, take items from fragilePool
				while(temp.getSize() < temp.getMaxCapacity() && !fragilePool.isEmpty()) {
					Item item = fragilePool.remove();
					temp.addItem(item.mailItem);
				}
				while (temp.getSize() < temp.getMaxCapacity() && !pool.isEmpty()) {
					Item item = pool.remove();
					if (!item.heavy) {
						temp.addItem(item.mailItem);
					}
				}
			} else {
				// Strong or big robot
				while(temp.getSize() < temp.getMaxCapacity() && !pool.isEmpty() ) {
					Item item = pool.remove();
					if (!item.heavy) {
						temp.addItem(item.mailItem);
					}
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
	public void registerWaiting(Robot robot) { // assumes won't be there
		if (robot.isStrong()) {
			robots.add(robot); 
		} else {
			robots.addLast(robot); // weak robot last as want more efficient delivery with highest priorities
		}
	}

	@Override
	public void deregisterWaiting(Robot robot) {
		robots.remove(robot);
	}

}
