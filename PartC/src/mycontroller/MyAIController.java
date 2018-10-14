package mycontroller;

import mycontroller.Pathway;

import java.util.Queue;

import controller.CarController;
import world.Car;
import world.WorldSpatial;
import utilities.Coordinate;
import mycontroller.strategy.StrategyManager;

public class MyAIController extends CarController{
	
	private StrategyManager stManager;
	
	private Pathway pathway;
	
	private Coordinate lastPosition;
	private Coordinate nextDest = null;
	
	//current position
	private Coordinate currPos;
	

	/**
	 * constructor for MyAIController
	 * @param car Car object
	 */
	public MyAIController(Car car) {
		super(car);

		MapRecorder.loadMap(super.getMap());

		stManager = new StrategyManager();
	}

	@Override
	public void update() {
		
		// may check if the car moves first?
		MapRecorder.updateCarView(super.getView());
		
		if(checkUpdateManager()) {
			pathway = stManager.findNewPathway(this);
		}
		
		if(pathway != null) {
			System.out.println(getPosition() + "|.|" + pathway.getDesti().getCoordinate());
		}
		
		// when pathway.desti is (-1, -1), stays the same
		// only appears when standing in health area
		if(pathway != null && Pathway.STAYS.equals(pathway.getDesti())) {
			// stays
		} else if(pathway != null) {
			navigation();
		}
		
		// use pipeline to decide path.. drive on path..
		// consider the situation when target hasn't changed
	}
	
	private boolean checkUpdateManager() {
		return pathway == null || stManager.checkAndTakeover(this) || pathway.getPath().size() == 0;
	}
	
	/**
	 * Drive towards the next destination point
	 */
	public void navigation() {
		currPos = new Coordinate(super.getPosition());
		
		if(nextDest == null) {
			nextDest = pathway.peekNext();
			startMoving();
		}
		
		if(distance(nextDest) == 0) {
			pathway.pollNext();
			if((nextDest = pathway.peekNext()) != null) {
				turn();
			}else {
				super.applyReverseAcceleration();
				nextDest = null;
			}
		}
	}
	
	/**
	 * Return the distance between current position and the target position
	 * @param o other coordinate
	 * @return the distance
	 */
	public int distance(Coordinate o) {
		return Math.abs(o.x - currPos.x) + Math.abs(o.y - currPos.y); 
	}
	
	private void turn() {
		WorldSpatial.Direction Ori = super.getOrientation();
		
		if(nextDest.x - currPos.x > 0) {
			if(Ori == WorldSpatial.Direction.NORTH) {
				super.turnRight();
			}else {
				super.turnLeft();
			}
		}else if (nextDest.x - currPos.x < 0) {
			if(Ori == WorldSpatial.Direction.NORTH) {
				super.turnLeft();
			}else {
				super.turnRight();
			}
		}else if (nextDest.y - currPos.y > 0) {
			if(Ori == WorldSpatial.Direction.WEST) {
				super.turnLeft();
			}else {
				super.turnRight();
			}
		}else if (nextDest.y - currPos.y < 0) {
			if(Ori == WorldSpatial.Direction.WEST) {
				super.turnRight();
			}else {
				super.turnLeft();
			}
		}
	}
	
	/**
	 * Determine whether needs to go forward or backward
	 */
	private void startMoving() {
		WorldSpatial.Direction Ori = super.getOrientation();
		
		if((nextDest.x - currPos.x < 0)) {
			if(Ori == WorldSpatial.Direction.EAST) {
				super.applyReverseAcceleration();
			}
		}else if ((nextDest.x - currPos.x > 0)) {
			if(Ori == WorldSpatial.Direction.WEST) {
				super.applyReverseAcceleration();
			}	
		}else if((nextDest.y - currPos.y < 0)) {
			if(Ori == WorldSpatial.Direction.SOUTH) {
				super.applyReverseAcceleration();
			}
		}else if((nextDest.x - currPos.x > 0)) {
			if(Ori == WorldSpatial.Direction.NORTH) {
				super.applyReverseAcceleration();
			}
		}else {
			super.applyForwardAcceleration();
		}
	}

}
