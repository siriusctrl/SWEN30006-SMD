package mycontroller.pipeline.dijkstra;

import java.util.*;

import mycontroller.MapRecorder;
import mycontroller.Pathway;
import world.World;
import world.WorldSpatial.Direction;

public class TestDijkstra {
	public static void testDijkstra() {
		
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				MapRecorder.cost[i][j] = 1;
			}
		}
		
		Pathway p = Dijkstra.findShortestPath(new Node(2, 3), new Node(3, 5));
		Stack<Node> st = p.getPath();
		while (!st.isEmpty()) {
			System.out.println(st.pop().getCoordinate().toString());
		}
		
	}
}
