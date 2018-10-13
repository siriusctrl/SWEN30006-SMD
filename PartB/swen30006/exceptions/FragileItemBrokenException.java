package exceptions;

/**
 * An exception thrown when the robot moves too fast or other items are put in the tube with this one.
 */
public class FragileItemBrokenException extends Throwable {
	public FragileItemBrokenException(){
		super("Fragile item broken!!");
	}
}
