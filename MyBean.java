package jersey1;
public class MyBean {
	private String m;
	private java.io.File f;
	public String getValue() {
		return m;
	}	
	public void setValue(String m1) {
		m = m1;
	}
	public void setOpen(java.io.File fo) {
			f = fo;
	}
	public String toString() {
		return m;
	}
}
