package SupportClasses;

/**
 * Created by Manu Srivastava on 22-03-2016.
 */
public class Day {
	Lecture lectures[];

	public Day(Lecture[] lectures) {
		this.lectures = lectures;
	}

	public Day() {
	}

	public Lecture[] getLectures() {
		return lectures;
	}

	public void setLectures(Lecture[] lectures) {
		this.lectures = lectures;
	}
}
