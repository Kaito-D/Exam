package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao {

	private String baseSql;
	public Test get(Student student, Subject subject, School school, int no){
		return null;
	}
	private List<Test> postFilter(ResultSet rSet, School school){
		return null;
	}
	public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school){
		return null;
	}
	public boolean save(List<Test> list){
		return true;
	}
	private boolean save(Test test, Connection connection){
		return true;
	}
}
