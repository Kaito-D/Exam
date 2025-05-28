package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao {

	private String baseSql;
	public Test get(Student student, Subject subject, School school, int no) throws Exception {
			Test test = new Test();
			Connection connection = getConnection();
			PreparedStatement statement = null;
			try {
				statement = connection.prepareStatement("select * from Test where student_no=? and subject_cd=? and school_cd=? and int_no=?");
				statement.setString(1,student.getNo());
				statement.setString(2,subject.getCd());
				statement.setString(3,school.getCd());
				statement.setInt(4, no);
				ResultSet resultSet = statement.executeQuery();

				StudentDao studentDao = new StudentDao();
				SubjectDao subjectDao = new SubjectDao();
				SchoolDao schoolDao = new SchoolDao();


				if (resultSet.next()) {
					test.setStudent(studentDao.get(resultSet.getString("student_no")));
					test.setSubject (subjectDao.get(resultSet.getString("subject_cd"), schoolDao.get(resultSet.getString("school_cd"))));
					test.setClassNum (resultSet.getString("class_num"));
					test.setSchool(schoolDao.get(resultSet.getString("school_cd")));
					test.setNo(resultSet.getInt("no"));
					test.setPoint(resultSet.getInt("point"));

				}
				else {
					test = null;
				}
			}catch (Exception e){
				throw e;
			}
			finally {
				if (statement != null) {
					try {
						statement.close();
					} catch (SQLException sqle) {
						throw sqle;
					}
				}
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException sqle) {
						throw sqle;
					}
				}
			}
			return test;
		}
	private List<Test> postFilter(ResultSet resultSet, School school) throws Exception {
		List<Test> list = new ArrayList<>();
		try{
			while(resultSet.next()) {
				Test test = new Test();
				StudentDao student = new StudentDao();
				SubjectDao subject = new SubjectDao();
				test.setStudent (student.get(resultSet.getString("student_no")));
				test.setClassNum (resultSet.getString("class_num"));
				test.setSubject (subject.get(resultSet.getString("subject_cd"),(school)));
				test.setSchool(school);
				test.setNo (resultSet.getInt("no"));
				test.setPoint (resultSet.getInt("point"));
				list.add(test);
			}
		}catch(SQLException | NullPointerException e) {
			e.printStackTrace();
		}
		return list;
	}
	public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school)throws Exception{
		List<Test> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		try{
			statement = connection.prepareStatement("select * from List where rSet=no? and school_no=?");
			statement.setInt(1,entYear);
			statement.setString(2,classNum);
			statement.setString(3,subject.getCd());
			statement.setInt(4, num);
			statement.setString(5,school.getCd());
			ResultSet resultSet = statement.executeQuery();

		}catch (Exception e){
			throw e;
		}
		finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		return list;
	}
	public boolean save(List<Test> list){

		return true;
	}
	private boolean save(Test test, Connection connection){
		    String sql = "INSERT INTO tests (id, name, score) VALUES (?, ?, ?)";

		    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
		        stmt.setInt(1, test.getId());
		        stmt.setString(2, test.getName());
		        stmt.setDouble(3, test.getScore());

		        int rowsAffected = stmt.executeUpdate();
		        return rowsAffected > 0;
		    } catch (SQLException e) {
		        e.printStackTrace(); // or use a logger
		        return false;
		    }
		return true;
	}
}
