package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.TestListStudent;
import dao.TestListStudentDao;
import tool.Action;

public class TestListStudentExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        Student teacher = (Student) session.getAttribute("student");

        if (teacher == null) {
            // ログインしていない場合、ログインページへフォワード
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        // 成績一覧を取得
        TestListStudentDao dao = new TestListStudentDao();
        List<TestListStudent> testList = dao.filter(teacher);

        // リクエストスコープに保存
        request.setAttribute("testList", testList);

        // 成績表示ページへフォワード
        request.getRequestDispatcher("test_list_student.jsp").forward(request, response);
    }
}
