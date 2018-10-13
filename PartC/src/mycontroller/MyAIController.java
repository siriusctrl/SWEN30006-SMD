package mycontroller;

import controller.CarController;
import world.Car;

public class MyAIController extends CarController{
	
	public MapRecorder mapRecorder;

	public MyAIController(Car car) {
		super(car);
		
		mapRecorder = new MapRecorder(this.getMap());
	}

	@Override
	public void update() {
		
	}

}
