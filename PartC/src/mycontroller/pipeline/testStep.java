package mycontroller.pipeline;

public class testStep {

	public static void main(String[] args) {
		Step<String, Integer> toHex = Step.of(it -> Integer.parseInt(it, 16));
		
		Step<String, Integer> toInt = Step.of(it -> Integer.parseInt(it));
		Step<String, Integer> addup = toInt.add(it -> it + 10);
		
		System.out.println(toHex.execute("11"));
		System.out.println(addup.execute("100"));
	}

}
