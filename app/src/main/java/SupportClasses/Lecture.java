package SupportClasses;

/**
 * Created by Manu Srivastava on 22-03-2016.
 */
public class Lecture {
	String subcode;
	String empcode;


	public Lecture() {
		this.subcode = "";

		this.empcode = "";
	}

	public Lecture(String subcode, String empcode) {
		this.subcode = subcode;

		this.empcode = empcode;
	}

	public String getSubcode() {
		return subcode;
	}

	public void setSubcode(String subcode) {
		this.subcode = subcode;
	}

	public String getEmpcode() {
		return empcode;
	}

	public void setEmpcode(String empcode) {
		this.empcode = empcode;
	}
}
