package mycontroller.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import mycontroller.MapRecorder;
import mycontroller.MyAIController;
import mycontroller.Pathway;
import mycontroller.TileStatus;
import mycontroller.pipeline.SimplifyPath;
import mycontroller.pipeline.Step;
import mycontroller.pipeline.astar.AStar;
import tiles.MapTile;
import utilities.Coordinate;
import world.Car;
import world.World;

/**
 * Strategy for exploration of map
 */
public class ExploreStrategy implements IEscapeStrategy{
	
	public static final int MAX_EXPLORE = 5;
	public static final int UNEXPLORE_THRESHOLD_S = 10;
	
	// initialise pipeline
	Step<Coordinate[], Pathway> findRoute = Step.of(AStar::findShortestPath);
	Step<Coordinate[], Pathway> simpleRoute = findRoute.add(SimplifyPath::simplifyPath); 
	
	@Override
	public Pathway findDestination(MyAIController myAIController) {
		
		// initialise pipeline
		Step<Coordinate[], Pathway> findRoute = Step.of(AStar::findShortestPath);
		Step<Coordinate[], Pathway> simpleRoute = findRoute.add(SimplifyPath::simplifyPath); 
		
		MapTile[][] mapTiles = MapRecorder.mapTiles;
		TileStatus[][] tileStatus = MapRecorder.mapStatus;
		Coordinate myCr = new Coordinate(myAIController.getPosition());
		
		// explored, certainly road
		ArrayList<Coordinate> exactRoads = new ArrayList<>();
		// not sure if it is road, but labelled road
		ArrayList<Coordinate> roadsMaybe = new ArrayList<>();

		for(int x = 0; x < World.MAP_WIDTH; x ++) {
			for(int y = 0; y < World.MAP_HEIGHT; y ++) {
				if(x != myCr.x && y != myCr.y) {

					if(tileStatus[x][y] == TileStatus.EXPLORED && mapTiles[x][y].getType() == MapTile.Type.ROAD) {
						exactRoads.add(new Coordinate(x,y));
					}

					if(mapTiles[x][y].getType() == MapTile.Type.ROAD) {
						roadsMaybe.add(new Coordinate(x,y));
					}
				}
			}
		}
		
		// currently evaulating ecact roads unless no such road
		ArrayList<Coordinate> currentEvaluating = exactRoads;
		if (exactRoads.size() == 0) {
			currentEvaluating = roadsMaybe;
		}
		
		Collections.sort(currentEvaluating, new Comparator<Coordinate>() {
			public int compare(Coordinate cr1, Coordinate cr2) {
				return findExploreCount(cr2) - findExploreCount(cr1);
			}
		});

		ArrayList<Pathway> allPathway = new ArrayList<>();
		Pathway minPath = Pathway.getUnabletoReach();
		for(int index = 0; index < currentEvaluating.size(); index ++) {
			if (findExploreCount(currentEvaluating.get(index)) > UNEXPLORE_THRESHOLD_S) {
				// evaluation
				minPath = evaluateBest(currentEvaluating.subList(index, index + 1), myAIController, simpleRoute);
				if(!Pathway.getUnabletoReach().equals(minPath)) {
					allPathway.add(minPath);
				}
			} else {
				minPath = evaluateBest(currentEvaluating.subList(index, index + 1), myAIController, simpleRoute);
				if(!Pathway.getUnabletoReach().equals(minPath)) {
					break;
				}
			}
		}
		
		// obtain optimised route
		if(allPathway.size() > 0) {
			Collections.sort(allPathway);
			minPath = allPathway.get(0);
		}
		
		return minPath;
	}

	
	/**
	 * Finds at a particular coordinate, how many unexplored coordinates can be further explored.
	 * @param cr coordinate
	 * @return the explore count
	 */
	public int findExploreCount(Coordinate cr){
		
		int itCount = 0;
		
		for (int x = cr.x - Car.VIEW_SQUARE; x <= cr.x + Car.VIEW_SQUARE; x++) {
			for (int y = cr.y - Car.VIEW_SQUARE; y <= cr.y + Car.VIEW_SQUARE; y ++) {
				if (MapRecorder.xyInBound(x, y) && MapRecorder.mapStatus[x][y] == TileStatus.UNEXPLORED) {
					itCount += 1;
				}
			}
		}
		
		return itCount;
		
	}
	
	@Override
	public boolean isFinished(MyAIController myAIController) {
		return false;
	}
	
	@Override
	public boolean checkTakeover(IEscapeStrategy st, MyAIController myAIController) {
		return true;
	}

}
