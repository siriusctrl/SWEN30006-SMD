package controller;

import java.util.Set;
import com.badlogic.gdx.Input;
import world.Car;
import swen30006.driving.Simulation;

// Manual Controls for the car
public class ManualController extends CarController {
	
	public ManualController(Car car){
		super(car);
	}
	
	public void update(){
		Set<Integer> keys = Simulation.getKeys();
		Simulation.resetKeys();
		// System.out.print("Get Keys: ");
        // System.out.println(keys);
        for (int k : keys){
		     switch (k){
		        case Input.Keys.B:
		        	applyBrake();
		            break;
		        case Input.Keys.UP:
		        	applyForwardAcceleration();
		            break;
		        case Input.Keys.DOWN:
		        	applyReverseAcceleration();
		        	break;
		        case Input.Keys.LEFT:
		        	turnLeft();
		        	break;
		        case Input.Keys.RIGHT:
		        	turnRight();
		        	break;
		        default:
		      }
		  }
	}
}
