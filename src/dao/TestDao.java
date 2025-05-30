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
				statement = connection.prepareStatement("select * from Test where student_no=? and subject_cd=? and school_cd=? and no=?");
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
			statement = connection.prepareStatement("select * from Test where student_no=? and subject_cd=? and school_cd=? and no=? and point=? and class_num=?");
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
	public boolean save(List<Test> list)throws Exception {
		try{
			for (Test test : list) {
				Connection connection = getConnection();
				save(test, connection);
			}
		} catch (Exception e) {
			throw e;
		}
		return true;
	}
	private boolean save(Test test, Connection connection)throws Exception{
		Test test1 = new Test();
		PreparedStatement statement = null;
		int count = 0;
		try {
			 test1 = get(test.getStudent(), test.getSubject(), test.getSchool(), test.getNo());
			if(test1 == null){
				statement = connection.prepareStatement("insert into test (student_no, subject_cd, school_cd, no, point, class_num) values (?,?,?,?,?,?)");
				statement.setString(1, test.getStudent().getNo());
				statement.setString(2, test.getSubject().getCd());
				statement.setString(3, test.getSchool().getCd());
				statement.setInt(4, test.getNo());
				statement.setInt(5, test.getPoint());
				statement.setString(6, test.getClassNum());
			}else{
				statement = connection.prepareStatement("update student set student_no=?, subject_cd?, school_cd=?, no=?, point=?, class_num=?");
				statement.setString(1, test.getStudent().getNo());
				statement.setString(2, test.getSubject().getCd());
				statement.setString(3, test.getSchool().getCd());
				statement.setInt(4, test.getNo());
				statement.setInt(5, test.getPoint());
				statement.setString(6, test.getClassNum());
			}
			count = statement.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
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
		return (count > 0);
	}
	public List<Test> filter(School school) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
}
