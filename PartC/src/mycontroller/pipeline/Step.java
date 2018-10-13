package mycontroller.pipeline;

/**
 * Interface for individual steps in the pipeline
 * @param <T>
 * @param <U>
 */
public interface Step<T, U> {
    U execute(T input);
}