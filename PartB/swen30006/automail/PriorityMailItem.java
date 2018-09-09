package automail;

public class PriorityMailItem extends MailItem{
	
	/** The priority of the mail item from 1 low to 100 high */
    private final int PRIORITY_LEVEL;
    
	public PriorityMailItem(int dest_floor, int arrival_time, int weight, boolean fragile, int priority_level) {
		super(dest_floor, arrival_time, weight, fragile);
        this.PRIORITY_LEVEL = priority_level;
	}
	
    /**
    *
    * @return the priority level of a mail item
    */
   public int getPriorityLevel(){
       return PRIORITY_LEVEL;
   }
   
   @Override
   public String toString(){
       return super.toString() + String.format(" | Priority: %3d", PRIORITY_LEVEL);
   }

}
