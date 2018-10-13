package mycontroller.pipeline;

/**
 * A step interface as an implementation of pipeline strategy
 * reference to "https://stackoverflow.com/questions/39947155/pipeline-design-pattern-implementation" 
 * with some modification
 * @param <I> the Input value type
 * @param <O> the output value type
 */
public interface Step<I, O> {
	
    /**
     * implement the method stack based on the given input
     * @param input the input value
     * @return the result based on implement one or more function
     */
    O execute(I input);
    
    /**
     * wrap another step which will take the result of last one and execute
     * @param step the step that will be executed after the current step
     * @return the wrapped step, has more than more method will be executed.
     */
    default <A> Step<I,A> add(Step<O,A> step) {
    		return input -> step.execute(execute(input));
    }
    
    /**
     * This method use to add the first method to the step
     * @param step The method that will be implemented
     * @return step
     */
    static <I,O> Step<I,O> of(Step<I,O> step){
    		return step;
    }
}