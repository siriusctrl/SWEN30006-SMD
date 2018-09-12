package strategies;
import automail.IMailDelivery;
import automail.Robot;

public interface RobotAdapter {
	Robot create(IMailPool mailPool, IMailDelivery delivery);
}
