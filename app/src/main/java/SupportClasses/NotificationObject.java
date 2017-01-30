package SupportClasses;

/**
 * Created by Manu Srivastava on 17-04-2016.
 */
public class NotificationObject {

	String title;
	String date;
	String text;

	public NotificationObject() {
		title= "";
		text = "";
		date = "";
	}

	public String getTitle() {

		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public NotificationObject(String title, String date, String text) {

		this.title = title;
		this.date = date;
		this.text = text;
	}
}
