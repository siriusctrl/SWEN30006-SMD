package controller;

import java.util.HashMap;
import java.util.Set;

import tiles.MapTile;
import utilities.Coordinate;
import world.Car;
import world.World;
import world.WorldSpatial;

public abstract class CarController {
	
	private Car car;
	
	/**
	 * Instantiates the car
	 * @param car
	 */
	public CarController(Car car){
		this.car = car;
	}
	
	/**
	 * Slows the car down
	 */
	public void applyBrake(){
		car.brake();
	}
	
	/**
	 * Speeds the car up in the forward direction
	 */
	public void applyForwardAcceleration(){
		car.applyForwardAcceleration();
	}
	
	/**
	 * Speeds the car up in the backwards direction
	 */
	public void applyReverseAcceleration(){
		car.applyReverseAcceleration();
	}
	
	/**
	 * Turns the car left
	 */
	public void turnLeft(){
		car.turnLeft();
	}
	
	/**
	 * Turns the car right
	 */
	public void turnRight(){
		car.turnRight();
	}
	
	/**
	 * Retrieves the car's current position
	 */
	public String getPosition(){
		return car.getPosition();
	}
	
	/**
	 * Returns the car's current velocity.
	 */
	public float getSpeed(){
		return car.getSpeed();
	}
	
	/**
	 * Returns the car's current health
	 */
	public float getHealth(){
		return car.getHealth();
	}
	
	/**
	 * Returns the view around your car (this is a VIEW_SQUARExVIEW_SQUARE area)
	 */
	public HashMap<Coordinate,MapTile> getView(){
		return car.getView();
	}
	
	/**
	 * Get the distance the car can see
	 */
	public int getViewSquare(){
		return Car.VIEW_SQUARE;
	}
	
	/**
	 * Number of keys car needs to exit
	 */
	public int numKeys(){
		return car.numKeys;
	}
	
	/**
	 * Keys car already has
	 */
	public Set<Integer> getKeys(){
		return car.getKeys();
	}
	
	/**
	 * Get the current car orientation (North, West, East or South)
	 */
	public WorldSpatial.Direction getOrientation(){
		return car.getOrientation();
	}
	
	/*
	 * Get visible map
	 */
	public HashMap<Coordinate,MapTile> getMap(){
		return World.getMap();
	}
	
	/*
	 * Get map height
	 */
	public int mapHeight() {
		return World.MAP_HEIGHT;
	}
	
	/*
	 * Get map width
	 */
	public int mapWidth() {
		return World.MAP_WIDTH;
	}
	
	/**
	 * This is the required update step for a controller.
	 */
	public abstract void update();
}
