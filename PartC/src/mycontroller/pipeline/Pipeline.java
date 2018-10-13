package mycontroller.pipeline;

import java.util.*;

public class Pipeline<In, Out> {
	
	private ArrayList<Step> pipelineSteps = new ArrayList<>();
	
	public<A> Pipeline<In, A> add(Step<In, A> step){
		pipelineSteps.add(step);
		return (Pipeline<In, A>) this;
	}
}
