package mycontroller.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

public class ExploreStrategy implements IEscapeStrategy{
	
	public static final int MAX_EXPLORE = 10;
	Step<Coordinate[], Pathway> findRoute = Step.of(AStar::findShortestPath);
	Step<Coordinate[], Pathway> simpleRoute = findRoute.add(SimplifyPath::simplifyPath); 
	
	public Pathway findDestination(MyAIController myAIController) {
		MapTile[][] mapTiles = MapRecorder.mapTiles;
		TileStatus[][] tileStatus = MapRecorder.mapStatus;
		Coordinate myCr = new Coordinate(myAIController.getPosition());
		
		ArrayList<Coordinate> exactRoads = new ArrayList<>();
		ArrayList<Coordinate> roadsMaybe = new ArrayList<>();
		
		for(int x = 0; x < World.MAP_WIDTH; x ++) {
			for(int y = 0; y < World.MAP_HEIGHT; y ++) {
				if(x != myCr.x && y != myCr.y) {
					/*if(tileStatus[x][y] == TileStatus.EXPLORED && mapTiles[x][y].getType() == MapTile.Type.ROAD) {
						exactRoads.add(new Coordinate(x,y));
					}*/
					
					if(tileStatus[x][y] == TileStatus.EXPLORED && mapTiles[x][y].getType() == MapTile.Type.ROAD) {
						exactRoads.add(new Coordinate(x,y));
					}

					if(mapTiles[x][y].getType() == MapTile.Type.ROAD) {
						roadsMaybe.add(new Coordinate(x,y));
					}
				}
			}
		}
		
		ArrayList<Coordinate> currentEvaluating;
		
		currentEvaluating = exactRoads;
		
		if(exactRoads.size() == 0) {
			currentEvaluating = roadsMaybe;
		}
		
		Collections.sort(currentEvaluating, new Comparator<Coordinate>() {
			public int compare(Coordinate cr1, Coordinate cr2) {
				return findExploreCount(cr2) - findExploreCount(cr1);
			}
		});
		
		System.out.println(currentEvaluating);
		
		Pathway minPath = Pathway.getUnabletoReach();
		
		int startIndex = 0, endIndex = MAX_EXPLORE;
		System.out.println("me: "+MAX_EXPLORE);
		while(startIndex < currentEvaluating.size() || !Pathway.getUnabletoReach().equals(minPath)) {
			minPath = evaluateBest(currentEvaluating.subList(startIndex, endIndex), myAIController, simpleRoute);
			startIndex += MAX_EXPLORE;
			if(startIndex > currentEvaluating.size()) {
				break;
			}
			
			endIndex += MAX_EXPLORE;
			
			if(endIndex > currentEvaluating.size()) {
				break;
			}
		}
		
		System.out.println("explore");
		return minPath;
	}
	
	
	public int findExploreCount(Coordinate cr){
		MapTile[][] mapTiles = MapRecorder.mapTiles;
		TileStatus[][] tileStatus = MapRecorder.mapStatus;
		
		int itCount = 0;
		
		for(int x = cr.x - Car.VIEW_SQUARE; x <= cr.x + Car.VIEW_SQUARE; x ++) {
			for(int y = cr.y - Car.VIEW_SQUARE; y <= cr.y + Car.VIEW_SQUARE; y ++) {
				if(MapRecorder.xyInBound(x, y) && tileStatus[x][y] == TileStatus.UNEXPLORED) {
					itCount += 1;
				}
			}
		}
		
		return itCount;
		
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
