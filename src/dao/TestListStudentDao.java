package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;

public class TestListStudentDao extends Dao {

    private String baseSql = "select subject.name, test.subject.cd, test.no, test.point from test";

    private List<TestListStudent> postFilter(ResultSet rSet) throws Exception {
        // リストの初期化
        List<TestListStudent> list = new ArrayList<>();
        try {
            while (rSet.next()) {
                TestListStudent tls = new TestListStudent();
                tls.setSubjectName(rSet.getString("name"));
                tls.setSubjectCd(rSet.getString("subject_cd"));
                tls.setNum(rSet.getInt("no"));
                tls.setPoint(rSet.getInt("point"));
                list.add(tls);
            }
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<TestListStudent> filter(Student student) throws Exception {
        // リストの初期化
        List<TestListStudent> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rSet = null;
        String join = "join subject on test.subject_cd = subject.cd";
        String condition = " where test.student_no = ? and test.school_cd = ?";
        String order = " order by subject_cd asc, no asc";

        try {
            // プライベートメンバーからSQL文を生成
            statement = connection.prepareStatement(baseSql + join + condition + order);
            statement.setString(1, student.getNo());
            statement.setString(2, student.getSchool().getCd());
            rSet = statement.executeQuery();
            list = postFilter(rSet);
        } catch (SQLException e) {
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
        return list;
    }
}