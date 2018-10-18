package mycontroller.strategy;

import mycontroller.MapRecorder;
import mycontroller.MyAIController;
import utilities.Coordinate;
import mycontroller.Pathway;
import mycontroller.pipeline.SimplifyPath;
import mycontroller.pipeline.Step;
import mycontroller.pipeline.astar.AStar;
import tiles.MapTile;
import tiles.TrapTile;

import java.util.ArrayList;

/**
 * Heal strategy for car
 */
public class HealStrategy implements IEscapeStrategy {
	
	/**
	 * HEALTH_THRESHOLD decides when the car needs to get healing
	 * */
	public static final int HEALTH_THRESHOLD = 50;
	
	/**
	 * HEALTH_LEAVE_AT decides when the car should leave healing area
	 * */
	public static final int HEALTH_LEAVE_AT = 100;
	
	/**
	 * Name of the health trap
	 * */
	public static final String HEALTH_TRAP = "health";
	
	// initialise pipeline
	Step<Coordinate[], Pathway> findRoute = Step.of(AStar::findShortestPath);
	Step<Coordinate[], Pathway> simpleRoute = findRoute.add(SimplifyPath::simplifyPath); 
	
	@Override
	public Pathway findDestination(MyAIController myAIController) {
		
		ArrayList<Coordinate> AllLocations = new ArrayList<>();
		AllLocations.addAll(MapRecorder.healthLocations);
		
		// if all keys found then check if the finish is closer
		if(myAIController.getKeys().size() == myAIController.numKeys()) {
			// collected all keys
			AllLocations.addAll(MapRecorder.finishLocations);
		}
		
		Coordinate curPos = new Coordinate(myAIController.getPosition());
		
		MapTile curTile = MapRecorder.mapTiles[curPos.x][curPos.y];
		
		// stay if currently at the health tile
		if(curTile.getType() == MapTile.Type.TRAP) {
			if(((TrapTile)curTile).getTrap() == HEALTH_TRAP) {
				return Pathway.getStays();
			}
		}
		
		Pathway newBest = evaluateBest(AllLocations, myAIController, simpleRoute); // new best path
		
		return newBest;
	}
	
	@Override
	public boolean checkTakeover(IEscapeStrategy st, MyAIController myAIController) {
		
		boolean needHeal = (MapRecorder.healthLocations.size() > 0);

		if(st == this) {
			needHeal = !(myAIController.getHealth() == HealStrategy.HEALTH_LEAVE_AT);
		}else {
			needHeal = needHeal && myAIController.getHealth() < HealStrategy.HEALTH_THRESHOLD;
		}

		return needHeal;
	}
	
}
