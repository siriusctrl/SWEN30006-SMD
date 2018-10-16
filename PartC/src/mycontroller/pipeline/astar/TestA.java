package mycontroller.pipeline.astar;

import java.util.Stack;

import mycontroller.MapRecorder;
import mycontroller.Pathway;
import utilities.Coordinate;


public class TestA {
	public static void testA() {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				MapRecorder.cost[i][j] = 1;
			}
		}
		MapRecorder.cost[2][4] = 9999;
		Pathway p = AStar.findShortestPath(new Coordinate(2, 3), new Coordinate(3, 5));
		Stack<Coordinate> st = p.getPath();
		while (!st.isEmpty()) {
			System.out.println(st.pop());
		}
	}
}
