package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.TestListSubject;
import dao.TestListSubjectDao;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        Subject subject = (Subject) session.getAttribute("subject");

        if (subject == null) {
            // ログインしていない場合、ログインページへフォワード
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        // 成績一覧を取得
        TestListSubjectDao dao = new TestListSubjectDao();
        List<TestListSubject> testList = dao.filter(0, null, subject, null);

        // リクエストスコープに保存
        request.setAttribute("testList", testList);

        // 成績表示ページへフォワード
        request.getRequestDispatcher("testlistsubject.jsp").forward(request, response);
    }
}
