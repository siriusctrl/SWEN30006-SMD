package mycontroller;

import controller.CarController;
import world.Car;

public class MyAIController extends CarController{
	
	public MapRecorder map;

	public MyAIController(Car car) {
		super(car);	
		map = new MapRecorder(super.getMap());
	}

	@Override
	public void update() {
		map.updateCarView(super.getView());
		
	}

}
