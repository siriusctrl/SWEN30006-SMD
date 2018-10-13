package mycontroller;
import tiles.*;
import utilities.Coordinate;
import world.World;

import java.util.*;

public class MapRecorder {
	
	// record all the Tiles of the map
	public MapTile[][] mapTiles = new MapTile[World.MAP_HEIGHT][World.MAP_WIDTH];
	// record the status for each tile
	public TileStatus[][] mapStatus = new TileStatus[World.MAP_HEIGHT][World.MAP_WIDTH];
	
	// record the locations for all the keys
	public ArrayList<Coordinate> keysLocations = new ArrayList<>();
	// record the locations for all the health point
	public ArrayList<Coordinate> healthLocations = new ArrayList<>();
	// record the locations for finish states
	public ArrayList<Coordinate> finishLocations = new ArrayList<>();
	
	/**
	 * Constructor
	 * 
	 * @param mapHashMap A hash table which contains the map information
	 * @param numOfKeys Total number of keys
	 */
	public MapRecorder(HashMap<Coordinate, MapTile> mapHashMap) {
		Coordinate start = null; 
		
		start = loadMap(mapHashMap);
		
		traverseMap(start.x, start.y);
		
	}
	
	
	/**
	 * Load the map from a HashMap into a 2D array
	 * 
	 * @param mapHashMap A hash table which contains the map information
	 * @return The starting point of the car
	 */
	private Coordinate loadMap(HashMap<Coordinate, MapTile> mapHashMap) {
		Iterator<Coordinate> keys = mapHashMap.keySet().iterator();
		Coordinate start = null;
		
		// a loop to load the map
		while(keys.hasNext()) {
			Coordinate c = keys.next();
			MapTile t = mapHashMap.get(c);
			
			mapTiles[c.x][c.y] = t;
			
			if (t.getType() == MapTile.Type.START) {
				start = new Coordinate(c.x, c.y);
			}
			if (t.getType() == MapTile.Type.FINISH) {
				finishLocations.add(c);
			}
		}
		
		return start;
		
	}
	
	private void traverseMap(int x, int y) {
		if (x < 0 || x >= World.MAP_WIDTH || y < 0 || y > World.MAP_HEIGHT) {
			return;
		} else if(mapStatus[x][y] != null) {
			return;
		} else if(mapTiles[x][y].getType() == MapTile.Type.WALL) {
			mapStatus[x][y] = TileStatus.UNREACHABLE;
			return;
		} else if(mapTiles[x][y].getType() == MapTile.Type.START) {
			mapStatus[x][y] = TileStatus.REACHED;
		} else if(mapTiles[x][y].getType() == MapTile.Type.FINISH) {
			mapStatus[x][y] = TileStatus.REACHED;
		} else {
			mapStatus[x][y] = TileStatus.UNREACHED;
		}
		
		// recursion
		traverseMap(x - 1, y);
		traverseMap(x, y - 1);
		traverseMap(x + 1, y);
		traverseMap(x, y + 1);
	}
	
	/**
	 * Update the surrounding condition which capture by the car sensor
	 * 
	 * @param mapHashMap 9*9 grid around car
	 */
	public void updateCarView(HashMap<Coordinate, MapTile> mapHashMap) {
		Iterator<Coordinate> keys = mapHashMap.keySet().iterator();
		
		while(keys.hasNext()) {
			Coordinate c = keys.next();
			MapTile m = mapHashMap.get(c);
			
			mapTiles[c.x][c.y] = m;
			mapStatus[c.x][c.y] = TileStatus.REACHED;
			
			if (m instanceof LavaTrap) {
				LavaTrap trap = (LavaTrap) m;
				if(trap.getKey() > 0) {
					keysLocations.add(c);
				}
			} else if(m instanceof HealthTrap){
				healthLocations.add(c);
			}
		}
	}

}
