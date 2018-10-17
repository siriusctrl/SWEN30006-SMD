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

public class HealStrategy implements IEscapeStrategy {
	
	public static final int HEALTH_THRESHOLD = 50;
	
	public static final int HEALTH_LEAVE_AT = 100;
	
	public static final String HEALTH_TRAP = "health";
	
	public HealStrategy() {
		
	}
	
	//initialise pipeline
	Step<Coordinate[], Pathway> findRoute = Step.of(AStar::findShortestPath);
	Step<Coordinate[], Pathway> simpleRoute = findRoute.add(SimplifyPath::simplifyPath); 
	
	@Override
	public Pathway findDestination(MyAIController myAIController) {
		ArrayList<Coordinate> AllLocations = new ArrayList<>();
		AllLocations.addAll(MapRecorder.healthLocations);
		if(myAIController.getKeys().size() == myAIController.numKeys()) {
			AllLocations.addAll(MapRecorder.finishLocations);
		}
		
		Coordinate curPos = new Coordinate(myAIController.getPosition());
		
		MapTile curTile = MapRecorder.mapTiles[curPos.x][curPos.y];
		
		if(curTile.getType() == MapTile.Type.TRAP) {
			if(((TrapTile)curTile).getTrap() == HEALTH_TRAP) {
				return Pathway.getStays();
			}
		}
		
		Pathway newBest = evaluateBest(AllLocations, myAIController, simpleRoute);
		/* if(!Pathway.getUnabletoReach().equals(newBest) && newBest.getDesti().equals(new Coordinate(myAIController.getPosition()))) {
			return Pathway.getStays();
		} */
		return newBest;
	}

	@Override
	public boolean isFinished(MyAIController myAIController) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean isTakeover(MyAIController myAIController) {
		return false;
	}
	
}
