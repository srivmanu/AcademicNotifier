package SupportClasses;

/**
 * Created by Manu Srivastava on 17-04-2016.
 */
public class TestObject extends Object{
	String subname;
	String date;
	String text;

	public TestObject() {
		subname = "";
		text = "";
		date = "";
	}

	public TestObject(String text, String subname, String date) {
		this.subname = subname;
		this.date = date;
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getSubname() {
		return subname;
	}

	public void setSubname(String subname) {
		this.subname = subname;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
