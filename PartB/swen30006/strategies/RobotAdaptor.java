package strategies;
import automail.IMailDelivery;
import automail.Robot;

public interface RobotAdaptor {
	Robot create(IMailPool mailPool, IMailDelivery delivery);
}
