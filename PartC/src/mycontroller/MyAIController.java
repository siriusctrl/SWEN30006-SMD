package mycontroller;

import mycontroller.Pathway;

import controller.CarController;
import world.Car;
import world.WorldSpatial;
import utilities.Coordinate;
import mycontroller.strategy.StrategyManager;
import tiles.MapTile;
import tiles.MudTrap;

import java.util.HashMap;

/**
 * MyAIController class.
 * Extends from CarController, contains our group's AI implementation.
 */
public class MyAIController extends CarController{
	
	private StrategyManager stManager;

	private Pathway pathway;
	private Coordinate nextDest = null;

	// information for turning
	private static HashMap<WorldSpatial.Direction, String[]> turnInfo;
	private static HashMap<WorldSpatial.Direction, int[]> coordInfo;
	
	// constants for turning information
	public static final String LEFT_TURN = "lft";
	public static final String RIGHT_TURN = "rgt";
	public static final String FORWARD_MOVE = "fw";
	public static final String BKWARD_MOVE = "bw";
	
	public static final String[] NORTH_TURN = new String[] {RIGHT_TURN, LEFT_TURN, FORWARD_MOVE, BKWARD_MOVE};
	public static final String[] SOUTH_TURN = new String[] {LEFT_TURN, RIGHT_TURN, BKWARD_MOVE, FORWARD_MOVE};
	public static final String[] WEST_TURN = new String[] {BKWARD_MOVE, FORWARD_MOVE, RIGHT_TURN, LEFT_TURN};
	public static final String[] EAST_TURN = new String[] {FORWARD_MOVE, BKWARD_MOVE, LEFT_TURN, RIGHT_TURN};
	
	public static final int[] NORTH_AHEAD = new int[] {0, 1};
	public static final int[] SOUTH_AHEAD = new int[] {0, -1};
	public static final int[] WEST_AHEAD = new int[] {-1, 0};
	public static final int[] EAST_AHEAD = new int[] {1, 0};
	

	/**
	 * constructor for MyAIController
	 * @param car Car object
	 */
	public MyAIController(Car car) {
		super(car);

		// load map and manager
		MapRecorder.loadMap(super.getMap());
		stManager = new StrategyManager();
		
		// initialise turn info
		turnInfo = new HashMap<>();
		coordInfo = new HashMap<>();
		
		turnInfo.put(WorldSpatial.Direction.NORTH, NORTH_TURN);
		turnInfo.put(WorldSpatial.Direction.SOUTH, SOUTH_TURN);
		turnInfo.put(WorldSpatial.Direction.WEST, WEST_TURN);
		turnInfo.put(WorldSpatial.Direction.EAST, EAST_TURN);
		
		coordInfo.put(WorldSpatial.Direction.NORTH, NORTH_AHEAD);
		coordInfo.put(WorldSpatial.Direction.SOUTH, SOUTH_AHEAD);
		coordInfo.put(WorldSpatial.Direction.WEST, WEST_AHEAD);
		coordInfo.put(WorldSpatial.Direction.EAST, EAST_AHEAD);
		
	}

	@Override
	public void update() {

		MapRecorder.updateCarView(super.getView());
		
		if(checkUpdateManager()) {
			pathway = stManager.findNewPathway(this);
		}
		
		System.out.println(pathway.getPath());
		System.out.println("now" + new Coordinate(super.getPosition()));
		System.out.println("to" + nextDest);
		
		
		// when pathway.desti is (-1, -1), stays the same
		// only appears when standing in health area
		if(pathway == null || Pathway.getStays().getDesti().equals(pathway.getDesti())) {
			// stays
		} else if(pathway != null) {
			navigation();
		}

	}
	
	// check update manager to see if action is needed
	private boolean checkUpdateManager() {
		return pathway == null || stManager.checkAndTakeover(this) || pathway.getPath().size() == 0;
	}
	
	/**
	 * Drive towards the next destination point
	 */
	public void navigation() {

		if(nextDest == null || nextDest.equals(new Coordinate(getPosition()))) {
			pathway.removeNext();
			if(pathway.getPath().size() > 0) {
				nextDest = pathway.getNext();
			}
			applyBrake();
		}
		
		moveTo(nextDest);
		
	}
	
	/**
	 * Move to the destination
	 * @param nextDest target coordination
	 */
	public void moveTo(Coordinate nextDest) {
		Coordinate nowPos = new Coordinate(getPosition());
		
		int deltaX = nextDest.x - nowPos.x;
		int deltaY = nextDest.y - nowPos.y;
		
		WorldSpatial.Direction Ori = super.getOrientation();
		String[] turningInfo = turnInfo.get(Ori);
		boolean[] conditions = new boolean[] {deltaX > 0, deltaX < 0, deltaY > 0, deltaY < 0};
		for(int index = 0; index < conditions.length; index ++) {
			if(conditions[index]) {
				doTurnInfo(turningInfo[index]);
				break;
			}
		}
		
	}
	
	/**
	 * Do turning given information stored
	 * @param info turn info
	 */
	public void doTurnInfo(String info) {
		
		applyForwardAcceleration();
		
		if(info == BKWARD_MOVE) {
			// brake
			applyBrake();
			applyReverseAcceleration();
		} else if(info != FORWARD_MOVE) {
			if (info == LEFT_TURN) {
				applyBrake();
				if(checkOriWallAhead(getOrientation(), getView())) {
					applyReverseAcceleration();
					// return;
				}
				turnLeft();
			} else {
				applyBrake();
				if(checkOriWallAhead(getOrientation(), getView())) {
					applyReverseAcceleration();
					// return;
					
				}
				turnRight();
			}
		}
	}

	/**
	 * Check if there is wall ahead in current orientation
	 * @param dre direction
	 * @param currentView current view of car
	 * @return true if there is wall ahead
	 */
	public boolean checkOriWallAhead(WorldSpatial.Direction dre, HashMap<Coordinate, MapTile> currentView) {
		
		Coordinate curPos = new Coordinate(getPosition());
		int[] delta = coordInfo.get(dre);
		MapTile tile = currentView.get(new Coordinate(curPos.x + delta[0], curPos.y + delta[1]));
		
		if(tile.isType(MapTile.Type.WALL) || tile instanceof MudTrap){
			return true;
		}
		return false;
		
	}

}
