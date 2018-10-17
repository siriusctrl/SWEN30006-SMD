package mycontroller;

import mycontroller.Pathway;

import controller.CarController;
import world.Car;
import world.WorldSpatial;
import utilities.Coordinate;
import mycontroller.strategy.StrategyManager;
import tiles.MapTile;

import java.util.HashMap;

public class MyAIController extends CarController{
	
	private StrategyManager stManager;
	
	private int wallSensitivity = 1;
	
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
		
		/* super.turnLeft();
		super.applyForwardAcceleration(); */
		
		// may check if the car moves first?
		MapRecorder.updateCarView(super.getView());
		
		if(checkUpdateManager()) {
			pathway = stManager.findNewPathway(this);
		}
		
		/* if(pathway != null) {
			for (Coordinate o : pathway.getPath()) {
				
				System.out.println(MapRecorder.mapTiles[o.x][o.y].getType());
			}
		} */
		
		// when pathway.desti is (-1, -1), stays the same
		// only appears when standing in health area
		if(pathway == null || Pathway.getStays().getDesti().equals(pathway.getDesti())) {
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
		
		if(nextDest == null || nextDest.equals(new Coordinate(getPosition()))) {
			pathway.removeNext();
			if(pathway.getPath().size() > 0) {
				nextDest = pathway.getNext();
			}
			applyBrake();
		}
		
		moveTo(nextDest);
		
		/* if(nextDest != null) {
			moveTo(nextDest);
		} */
		
		
	}
	
	public void moveTo(Coordinate nextDest) {
		Coordinate nowPos = new Coordinate(getPosition());
		
		int deltaX = nextDest.x - nowPos.x;
		int deltaY = nextDest.y - nowPos.y;
		
		WorldSpatial.Direction Ori = super.getOrientation();
		// System.out.println("now position: " + nowPos);
		// System.out.println(nextDest);
		String[] turningInfo = turnInfo.get(Ori);
		boolean[] conditions = new boolean[] {deltaX > 0, deltaX < 0, deltaY > 0, deltaY < 0};
		for(int index = 0; index < conditions.length; index ++) {
			if(conditions[index]) {
				doTurnInfo(turningInfo[index]);
				break;
			}
		}
		
	}
	
	public void doTurnInfo(String info) {
		if(info == BKWARD_MOVE) {
			applyReverseAcceleration();
		}else if(info != FORWARD_MOVE) {
			if(info == LEFT_TURN) {
				if(checkWallAhead(super.getOrientation(), super.getView())) {
					applyReverseAcceleration();
					
				}else {
					applyForwardAcceleration();
				}
				super.turnLeft();
				// System.out.println("left hey");
			}else {
				if(checkWallAhead(super.getOrientation(), super.getView())) {
					applyReverseAcceleration();
					
				}else {
					applyForwardAcceleration();
				}
				super.turnRight();
				// System.out.println("right fuck");
			}
		}else {
			applyForwardAcceleration();
		}
	}
	
	/**
	 * Check if you have a wall in front of you!
	 * @param orientation the orientation we are in based on WorldSpatial
	 * @param currentView what the car can currently see
	 * @return
	 */
	private boolean checkWallAhead(WorldSpatial.Direction orientation, HashMap<Coordinate, MapTile> currentView){
		switch(orientation){
		case EAST:
			return checkEast(currentView);
		case NORTH:
			return checkNorth(currentView);
		case SOUTH:
			return checkSouth(currentView);
		case WEST:
			return checkWest(currentView);
		default:
			return false;
		}
	}
	
	/**
	 * Check if the wall is on your left hand side given your orientation
	 * @param orientation
	 * @param currentView
	 * @return
	 */
	private boolean checkFollowingWall(WorldSpatial.Direction orientation, HashMap<Coordinate, MapTile> currentView) {
		
		switch(orientation){
		case EAST:
			return checkNorth(currentView);
		case NORTH:
			return checkWest(currentView);
		case SOUTH:
			return checkEast(currentView);
		case WEST:
			return checkSouth(currentView);
		default:
			return false;
		}	
	}
	
	/**
	 * Method below just iterates through the list and check in the correct coordinates.
	 * i.e. Given your current position is 10,10
	 * checkEast will check up to wallSensitivity amount of tiles to the right.
	 * checkWest will check up to wallSensitivity amount of tiles to the left.
	 * checkNorth will check up to wallSensitivity amount of tiles to the top.
	 * checkSouth will check up to wallSensitivity amount of tiles below.
	 */
	public boolean checkEast(HashMap<Coordinate, MapTile> currentView){
		// Check tiles to my right
		Coordinate currentPosition = new Coordinate(getPosition());
		for(int i = 0; i <= wallSensitivity; i++){
			MapTile tile = currentView.get(new Coordinate(currentPosition.x+i, currentPosition.y));
			if(tile.isType(MapTile.Type.WALL)){
				return true;
			}
		}
		return false;
	}
	
	public boolean checkWest(HashMap<Coordinate,MapTile> currentView){
		// Check tiles to my left
		Coordinate currentPosition = new Coordinate(getPosition());
		for(int i = 0; i <= wallSensitivity; i++){
			MapTile tile = currentView.get(new Coordinate(currentPosition.x-i, currentPosition.y));
			if(tile.isType(MapTile.Type.WALL)){
				return true;
			}
		}
		return false;
	}
	
	public boolean checkNorth(HashMap<Coordinate,MapTile> currentView){
		// Check tiles to towards the top
		Coordinate currentPosition = new Coordinate(getPosition());
		for(int i = 0; i <= wallSensitivity; i++){
			MapTile tile = currentView.get(new Coordinate(currentPosition.x, currentPosition.y+i));
			if(tile.isType(MapTile.Type.WALL)){
				return true;
			}
		}
		return false;
	}
	
	public boolean checkSouth(HashMap<Coordinate,MapTile> currentView){
		// Check tiles towards the bottom
		Coordinate currentPosition = new Coordinate(getPosition());
		for(int i = 0; i <= wallSensitivity; i++){
			MapTile tile = currentView.get(new Coordinate(currentPosition.x, currentPosition.y-i));
			if(tile.isType(MapTile.Type.WALL)){
				return true;
			}
		}
		return false;
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
