package tiles;

import world.Car;

public class LavaTrap extends TrapTile {
	public static final int HealthDelta = 20;
	private int key = 0;
	
	public String getTrap() { return "lava"; }

	public void applyTo(Car car, float delta) {
		car.reduceHealth(HealthDelta * delta);
		if (key>0) car.findKey(key);
	}
	
	public boolean canAccelerate() {
		return true;
	}
	
	public boolean canTurn() {
		return true;
	}

	public void setKey(int key) {
		this.key = key;
		if (key > 0) System.out.println("Lava key = " + key);
	}
	
	public int getKey() {
		return key;
	}

}
