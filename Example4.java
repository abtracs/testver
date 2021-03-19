package jersey1;

public class Example4 {

	private String foo;
	private int dollars;

	public Example4(String s, int d) {
		foo = s;
		dollars = d;
	}

	public String toString() {
		return foo + " was $" +dollars;
	}
	

}
