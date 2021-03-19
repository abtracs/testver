package jersey1;

public class Example1 {

	private String foo;
	private int dollars;

	public Example1(String s, int d) {
		foo = s;
		dollars = d;
	}

	public String toString() {
		return foo + " was $" +dollars;
	}

	public String getName() {
		return "http://"+foo;
	}	

}
