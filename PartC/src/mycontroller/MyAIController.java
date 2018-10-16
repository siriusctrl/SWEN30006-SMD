package mycontroller;

import mycontroller.Pathway;
import mycontroller.pipeline.dijkstra.Node;
import mycontroller.pipeline.dijkstra.TestDijkstra;

import controller.CarController;
import world.Car;
import world.WorldSpatial;
import utilities.Coordinate;
import mycontroller.strategy.StrategyManager;

import java.util.HashMap;

public class MyAIController extends CarController{
	
	private StrategyManager stManager;
	
	private Pathway pathway;
	
	private Coordinate lastPosition;
	private Coordinate nextDest = null;
	
	//current position
	private Coordinate currPos;
	
	private static HashMap<WorldSpatial.Direction, String[]> turnInfo;
	
	public static final String LEFT_TURN = "lft";
	public static final String RIGHT_TURN = "rgt";
	public static final String FORWARD_MOVE = "fw";
	public static final String BKWARD_MOVE = "bw";
	
	
	public static final String[] NORTH_TURN = new String[] {RIGHT_TURN, LEFT_TURN, FORWARD_MOVE, BKWARD_MOVE};
	public static final String[] SOUTH_TURN = new String[] {LEFT_TURN, RIGHT_TURN, BKWARD_MOVE, FORWARD_MOVE};
	public static final String[] WEST_TURN = new String[] {BKWARD_MOVE, FORWARD_MOVE, RIGHT_TURN, LEFT_TURN};
	public static final String[] EAST_TURN = new String[] {FORWARD_MOVE, BKWARD_MOVE, LEFT_TURN, RIGHT_TURN,};
	
	
	
	

	/**
	 * constructor for MyAIController
	 * @param car Car object
	 */
	public MyAIController(Car car) {
		super(car);

		MapRecorder.loadMap(super.getMap());

		stManager = new StrategyManager();
		
		
		turnInfo = new HashMap<>();
		
		turnInfo.put(WorldSpatial.Direction.NORTH, NORTH_TURN);
		turnInfo.put(WorldSpatial.Direction.SOUTH, SOUTH_TURN);
		turnInfo.put(WorldSpatial.Direction.WEST, WEST_TURN);
		turnInfo.put(WorldSpatial.Direction.EAST, EAST_TURN);
		
	}

	@Override
	public void update() {
		
		// may check if the car moves first?
		MapRecorder.updateCarView(super.getView());
		
		if(checkUpdateManager()) {
			pathway = stManager.findNewPathway(this);
		}
		
		/* if(pathway != null) {
			for (Node o : pathway.getPath()) {
				System.out.println("wor" + o.getCoordinate().toString());
			}
		} */
		
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
		
		/* if(nextDest.equals(new Coordinate(getPosition()))) {
			pathway.removeNext();
			if((nextDest = pathway.getNext()) != null) {
				turn();
			}else {
				super.applyReverseAcceleration();
				nextDest = null;
			}
		} */
		
		if(nextDest == null) {
			nextDest = pathway.getNext();
			moveTo(nextDest);
			// startMoving();
		}
		
		if(nextDest.equals(new Coordinate(getPosition()))) {
			pathway.removeNext();
			nextDest = null;
		}
		
		
	}
	
	public void moveTo(Coordinate nextDest) {
		Coordinate nowPos = new Coordinate(getPosition());
		
		int deltaX = nextDest.x - nowPos.x;
		int deltaY = nextDest.y - nowPos.y;
		
		WorldSpatial.Direction Ori = super.getOrientation();
		System.out.println(Ori);
		System.out.println(nextDest);
		String[] turningInfo = turnInfo.get(Ori);
		boolean[] conditions = new boolean[] {deltaX > 0, deltaX < 0, deltaY < 0, deltaY > 0};
		for(int index = 0; index < conditions.length; index ++) {
			if(conditions[index]) {
				System.out.println(index);
				doTurnInfo(turningInfo[index]);
				System.out.println(turningInfo[index]);
				break;
			}
		}
		
	}
	
	public void doTurnInfo(String info) {
		if(info == BKWARD_MOVE) {
			turnLeft();
			turnLeft();
			applyForwardAcceleration();
		}else {
			if(info == LEFT_TURN) {
				System.out.println(info);
				turnLeft();
			}else {
				turnRight();
			}
			applyForwardAcceleration();
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
		
		if (nextDest.x - currPos.x > 0) {
			if (Ori == WorldSpatial.Direction.NORTH) {
				super.turnRight();
			} else if (Ori == WorldSpatial.Direction.SOUTH) {
				super.turnLeft();
			}
		} else if (nextDest.x - currPos.x < 0) {
			if (Ori == WorldSpatial.Direction.NORTH) {
				super.turnLeft();
			} else if (Ori == WorldSpatial.Direction.SOUTH) {
				super.turnRight();
			}
		} else if (nextDest.y - currPos.y > 0) {
			if (Ori == WorldSpatial.Direction.WEST) {
				super.turnRight();
			} else if (Ori == WorldSpatial.Direction.EAST) {
				super.turnLeft();
			}
		} else if (nextDest.y - currPos.y < 0) {
			if (Ori == WorldSpatial.Direction.EAST) {
				super.turnRight();
			} else if (Ori == WorldSpatial.Direction.WEST) {
				super.turnLeft();
			}
		}
	}
	
	/**
	 * Determine whether needs to go forward or backward
	 */
	private void startMoving() {
		WorldSpatial.Direction Ori = super.getOrientation();
		
		/*if ((nextDest.x - currPos.x < 0)) {
			if (Ori == WorldSpatial.Direction.EAST) {
				super.applyReverseAcceleration();
			}
		} else if ((nextDest.x - currPos.x > 0)) {
			if (Ori == WorldSpatial.Direction.WEST) {
				super.applyReverseAcceleration();
			}	
		} else if ((nextDest.y - currPos.y < 0)) {
			if (Ori == WorldSpatial.Direction.NORTH) {
				super.applyReverseAcceleration();
			}
		} else if ((nextDest.y - currPos.y > 0)) {
			if (Ori == WorldSpatial.Direction.SOUTH) {
				super.applyReverseAcceleration();
			}
		} else {
			super.applyForwardAcceleration();
		}*/
	}

}
