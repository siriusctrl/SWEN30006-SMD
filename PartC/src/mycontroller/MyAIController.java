package mycontroller;



import mycontroller.Pathway;
import controller.CarController;
import world.Car;
import utilities.Coordinate;
import mycontroller.strategy.StrategyManager;

public class MyAIController extends CarController{
	
	private StrategyManager stManager;
	
	private Pathway pathway;

	public MyAIController(Car car) {
		super(car);
		MapRecorder.loadMap(super.getMap());
		stManager = new StrategyManager();
	}

	@Override
	public void update() {
		
		// may check if the car moves first?
		MapRecorder.updateCarView(super.getView());
		
		boolean targetChanged = stManager.update(this);

		if(targetChanged) {
			pathway = stManager.getPathway();
		}
		
		// 
		
		
		
		// use pipeline to decide path.. drive on path..
		// consider the situation when target hasn't changed
	}

}
