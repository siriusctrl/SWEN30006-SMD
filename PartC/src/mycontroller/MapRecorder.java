package mycontroller;
import tiles.*;
import utilities.Coordinate;
import world.World;

import java.util.*;

public class MapRecorder {
	// cost to get through a normal road
	public static final int ROAD_COST = 1;
	// cost to get through a lava
	public static final int LAVA_COST = 1000;
	// cost to get through a grass
	public static final int GRASS_COST = 20;
	// cost to get through a mud, wall or unexplored road is too high to go through
	public static final int MUD_COST = 99999999;
	public static final int WALL_COST = 99999999;
	public static final int UNEXPLORED_COST = 99999999;
	
	// record all the Tiles of the map
	public static MapTile[][] mapTiles = new MapTile[World.MAP_WIDTH][World.MAP_HEIGHT];
	// record the status for each tile
	public static TileStatus[][] mapStatus = new TileStatus[World.MAP_WIDTH][World.MAP_HEIGHT];
	// the cost to go through each point
	public static Integer[][] cost = new Integer[World.MAP_WIDTH][World.MAP_HEIGHT];
	
	// record the locations for all the keys
	public static HashMap<Integer,HashSet<Coordinate>> keysLocations = new HashMap<>();
	// record the locations for all the health point
	public static ArrayList<Coordinate> healthLocations = new ArrayList<>();
	// record the locations for finish states
	public static ArrayList<Coordinate> finishLocations = new ArrayList<>();
		
	/**
	 * Load the map from a HashMap into a 2D array
	 * 
	 * @param mapHashMap A hash table which contains the map information
	 */
	public static void loadMap(HashMap<Coordinate, MapTile> mapHashMap) {
		Iterator<Coordinate> keys = mapHashMap.keySet().iterator();
		Coordinate start = null;
		
		for (int i = 0; i < World.MAP_WIDTH; i++) {
			for (int j = 0; j < World.MAP_HEIGHT; j++) {
				cost[i][j] = UNEXPLORED_COST;
			}
		}
		
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
		
		traverseMap(start.x, start.y);
	}
	
	private static void traverseMap(int x, int y) {
		if (!xyInBound(x, y)) {
			return;
		} else if (mapStatus[x][y] != null) {
			return;
		} else if (mapTiles[x][y].getType() == MapTile.Type.WALL) {
			mapStatus[x][y] = TileStatus.UNREACHABLE;
			cost[x][y] = WALL_COST;
			return;
		} else if (mapTiles[x][y].getType() == MapTile.Type.START ||
				mapTiles[x][y].getType() == MapTile.Type.FINISH) {
			mapStatus[x][y] = TileStatus.EXPLORED;
			cost[x][y] = ROAD_COST;
		} else {
			mapStatus[x][y] = TileStatus.UNEXPLORED;
		}
		
		// recursion
		traverseMap(x - 1, y);
		traverseMap(x, y - 1);
		traverseMap(x + 1, y);
		traverseMap(x, y + 1);
	}
	
	/**
	 * Update the surrounding condition which capture by the car sensor also update the cost
	 * 
	 * @param mapHashMap 9*9 grid around car
	 */
	public static void updateCarView(HashMap<Coordinate, MapTile> mapHashMap) {
		Iterator<Coordinate> keys = mapHashMap.keySet().iterator();
		
		while(keys.hasNext()) {
			Coordinate c = keys.next();
			MapTile m = mapHashMap.get(c);
			
			if(xyInBound(c.x, c.y)) {
				mapTiles[c.x][c.y] = m;
				mapStatus[c.x][c.y] = TileStatus.EXPLORED;
				
				if (m instanceof LavaTrap) {
					LavaTrap trap = (LavaTrap) m;
					cost[c.x][c.y] = LAVA_COST;
					if (trap.getKey() > 0) {
						if (!keysLocations.containsKey(trap.getKey())) {
							keysLocations.put(trap.getKey(), new HashSet<>());
						}
						keysLocations.get(trap.getKey()).add(c);
					}
				} else if (m instanceof HealthTrap){
					healthLocations.add(c);
					cost[c.x][c.y] = ROAD_COST;
				} else if (m instanceof GrassTrap) {
					cost[c.x][c.y] = GRASS_COST;
				} else if (m instanceof MudTrap) {
					cost[c.x][c.y] = MUD_COST;
				} else if (m.getType().equals(MapTile.Type.WALL)) {
					cost[c.x][c.y] = WALL_COST;
				} else {
					cost[c.x][c.y] = ROAD_COST;
				}
			}
		}
	}
	
	public static boolean xyInBound(int x, int y) {
		if (x < 0 || x >= World.MAP_WIDTH || y < 0 || y >= World.MAP_HEIGHT) {
			return false;
		}
		return true;
	}

}
