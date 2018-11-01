package SupportClasses;

/**
 * Created by Manu Srivastava on 17-04-2016.
 */
public class AssignmentObject extends Object {
	String subname;
	String date;
	String text;

	public AssignmentObject() {
		subname = "";
		text = "";
		date = "";
	}

	public AssignmentObject(String text, String subname, String date) {
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
