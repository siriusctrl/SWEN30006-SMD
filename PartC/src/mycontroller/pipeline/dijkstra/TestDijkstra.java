package mycontroller.pipeline.dijkstra;

import java.util.PriorityQueue;

import mycontroller.MapRecorder;
import mycontroller.Pathway;
import world.World;
import world.WorldSpatial.Direction;

public class TestDijkstra {
	public static void testDijkstra() {
		/*
		Pathway p = Dijkstra.findShortestPath(new Node(2, 3), new Node(15, 9));
		PriorityQueue<Node> q = p.getPath();
		for (Node o : q) {
			System.out.println(o.getCoordinate().toString());
		}
		*/
		
		for (int i = 0; i < World.MAP_WIDTH; i++) {
			for (int j = 0; j < World.MAP_HEIGHT; j++) {
				System.out.print(MapRecorder.cost[i][j] + ", ");
			}
			System.out.print("\n");
		}
	}
}
