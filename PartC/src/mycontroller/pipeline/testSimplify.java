package mycontroller.pipeline;

import utilities.Coordinate;
import java.util.*;

import mycontroller.Pathway;

public class testSimplify {


	public static void main(String[] args) {
		Coordinate one = new Coordinate(1,1);
		Coordinate two = new Coordinate(1,2);
		Coordinate three = new Coordinate(1,3);
		Coordinate four = new Coordinate(2,3);
		Coordinate five = new Coordinate(3,3);
		
		Stack<Coordinate> p = new Stack<>();
		
		p.push(five);
		p.push(four);
		p.push(three);
		p.push(two);
		p.push(one);

		
		Pathway path = new Pathway();
		
		path.setPath(p);
		
		System.out.println(p);
		
		System.out.println(SimplifyPath.simplifyPath(path));
	}

}
