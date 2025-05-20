package bean;

import java.io.Serializable;

public class Teacher extends User implements Serializable {

	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public School getSchool() {
		return School;
	}
	public void setSchool(School School) {
		this.School = School;
	}
	private String name;
	private String password;
	private School School;
}
