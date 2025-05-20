package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.ClassNum;
import bean.School;
public class ClassNumDao extends Dao {
	public ClassNum get(String class_num, School school) throws Exception {
		ClassNum classNum = new ClassNum();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement("select * from class_num where class_num=? and school_cd=?");
			statement.setString(1, class_num);
			statement.setString(2, school.getCd());
			ResultSet resultSet = statement.executeQuery();
			SchoolDao schoolDao = new SchoolDao();
			if (resultSet.next()) {
				classNum.setClass_num(resultSet.getString("class_num"));
				classNum.setSchool(schoolDao.get(resultSet.getString("school_cd")));
			} else {
				classNum = null;
			}
		}catch (Exception e) {
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
		return classNum;
	}
	public List<String> filter (School school) throws Exception {
		List<String> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement("select class_num from class_num where school_cd=? order by class_num");
			statement.setString(1, school.getCd());
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				list.add(resultSet.getString("class_num"));
			}
		} catch (Exception e) {
			throw e;
		} finally{
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
	public boolean save(ClassNum classNum) throws Exception {
		Connection connection = getConnection();
		PreparedStatement statement = null;
		int count = 0;
		try {
			statement = connection.prepareStatement("insert into class_num(school_cd, class_num) values(?, ?)");
			statement.setString(1, classNum.getSchool().getCd());
			statement.setString(2, classNum.getClass_num());
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
	public boolean save(ClassNum classNum, String newClassNum) throws Exception {
		Connection connection = getConnection();
		PreparedStatement statement = null;
		int count = 0;
		try {
			statement = connection.prepareStatement("update class_num set class_num = ? where school_cd = ? and class_num = ?");
		statement.setString(1, newClassNum);
		statement.setString(2, classNum.getSchool().getCd());
		statement.setString(3, classNum.getClass_num());
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
}
