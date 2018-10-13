package mycontroller.pipeline;

/**
 * Interface for individual steps in the pipeline
 * @param <T>
 * @param <U>
 */
public interface Step<I, O> {
	
    O execute(I input);
    
    default <A> Step<I,A> pipe(Step<O,A> source){
    	return input -> source.execute(execute(input));
    }
    
    static <I,O> Step<I,O> of(Step<I,O> source){
    	return source;
    }
}