package jersey1;

public class Example {

	private String foo;
	private int dollars;

	public Example(String s, int d) {
		foo = s;
		dollars = d;
	}

	public String toString() {
		return foo + " was $" +dollars;
	}
	

}
