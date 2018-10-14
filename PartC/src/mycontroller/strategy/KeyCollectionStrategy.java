package mycontroller.strategy;

import mycontroller.MapRecorder;
import mycontroller.MyAIController;
import utilities.Coordinate;
import world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

import mycontroller.Pathway;
import mycontroller.pipeline.dijkstra.Dijkstra;
import mycontroller.pipeline.dijkstra.Node;
import tiles.MapTile;
import mycontroller.TileStatus;

public class KeyCollectionStrategy implements IEscapeStrategy {
	
	public KeyCollectionStrategy() {
		
	}

	@Override
	public Pathway findDestination(MyAIController myAIController) {
		// ???
		HashMap<Integer, ArrayList<Coordinate>> keys = MapRecorder.keysLocations;
		Set<Integer> got = myAIController.getKeys();
		
		Set<Integer> notYet = new HashSet<Integer>();
		
		for(int key: keys.keySet()) {
			if(!got.contains(key)) {
				notYet.add(key);
			}
		}
		
		if(notYet.size() > 0) {
			ArrayList<Coordinate> allCoords = new ArrayList<>();
			for(int cordKey: notYet) {
				allCoords.addAll(keys.get(cordKey));
			}
			Pathway bestOne = evaluateBest(allCoords, myAIController);
			if(bestOne != null) {
				return bestOne;
			}
		}else {
			return findExploreTargets(myAIController);
		}
		
		return null;
	}
	
	public Pathway evaluateBest(ArrayList<Coordinate> coords, MyAIController myAIController) {
		// calculate distance
		
		// deal with maximum costs, interpreting unreachable keys
		ArrayList<Pathway> pathways = new ArrayList<>();
		Node startNode = new Node(new Coordinate(myAIController.getPosition()));
		for(Coordinate cr: coords) {
			pathways.add(Dijkstra.findShortestPath(startNode, new Node(cr)));
		}
		return Collections.min(pathways);
	}
	
	private Pathway findExploreTargets(MyAIController myAIController) {
		// further decide
		
		MapTile[][] mapTiles = MapRecorder.mapTiles;
		TileStatus[][] tileStatus = MapRecorder.mapStatus;
		Coordinate myCr = new Coordinate(myAIController.getPosition());
		
		ArrayList<Coordinate> exactRoads = new ArrayList<>();
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
		
		ArrayList<Coordinate> currentEvaluating;
		
		currentEvaluating = exactRoads;
		
		if(exactRoads.size() == 0) {
			currentEvaluating = roadsMaybe;
		}
		
		return evaluateBest(currentEvaluating, myAIController);
		
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
