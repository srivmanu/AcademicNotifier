package SupportClasses;

/**
 * Created by Manu Srivastava on 22-03-2016.
 */
public class TimeTable {
	Day week[];

	public TimeTable() {
	}

	public TimeTable(Day[] week) {

		this.week = week;
	}

	public Day[] getWeek() {
		return week;
	}

	public void setWeek(Day[] week) {
		this.week = week;
	}
}
