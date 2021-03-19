package jersey1;

public class Example3 {

	private String foo;
	private int dollars;

	public Example3(String s, int d) {
		foo = s;
		dollars = d;
	}

	public String toString() {
		return foo + " was $" +dollars;
	}
	

}
