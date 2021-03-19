package jersey1;

public class Example2 {

	private String foo;
	private int dollars;

	public Example2(String s, int d) {
		foo = s;
		dollars = d;
	}

	public String toString() {
		return foo + " was $" +dollars;
	}
	

}
