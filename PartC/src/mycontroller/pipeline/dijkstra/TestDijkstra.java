package mycontroller.pipeline.dijkstra;

import java.util.PriorityQueue;

import mycontroller.MapRecorder;
import mycontroller.Pathway;
import world.World;
import world.WorldSpatial.Direction;

public class TestDijkstra {
	public static void testDijkstra() {
		MapRecorder.cost[2][3] = 1;
		MapRecorder.cost[2][4] = 1;
		MapRecorder.cost[2][5] = 1;
		MapRecorder.cost[3][5] = 1;
		
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				System.out.print(MapRecorder.cost[i][j] + ", ");
			}
			System.out.print("\n");
		}
		
		Pathway p = Dijkstra.findShortestPath(new Node(2, 3), new Node(3, 5));
		PriorityQueue<Node> q = p.getPath();
		for (Node o : q) {
			System.out.println(o.getCoordinate().toString());
		}
		
	}
}
