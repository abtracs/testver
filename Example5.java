package jersey1;

public class Example5 {

	private String foo;
	private int dollars;

	public Example5(String s, int d) {
		foo = s;
		dollars = d;
	}

	public String toString() {
		return java.net.URLEncoder.encode(foo + " was $" +dollars);
	}
	

}
