package mycontroller.pipeline.astar;

import java.util.Stack;

import mycontroller.MapRecorder;
import mycontroller.Pathway;
import utilities.Coordinate;


public class TestA {
	public static void testA() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				MapRecorder.cost[i][j] = 1111;
			}
		}
		MapRecorder.cost[1][0] = 9;
		MapRecorder.cost[1][1] = 99;
		MapRecorder.cost[1][2] = 999;
		MapRecorder.cost[1][3] = 999;
		MapRecorder.cost[1][4] = 999;
		MapRecorder.cost[3][3] = 999;
		MapRecorder.cost[3][4] = 999;
		MapRecorder.cost[3][5] = 999;
		MapRecorder.cost[2][5] = 99;
		Pathway p = AStar.findShortestPath(new Coordinate[] {new Coordinate(0, 0),new Coordinate(5, 5)});
		Stack<Coordinate> st = p.getPath();
		while (!st.isEmpty()) {
			System.out.println(st.pop());
		}
		
		System.out.println(p.getCost());
	}
}
