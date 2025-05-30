package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import bean.TestListSubject;
import dao.TestListSubjectDao;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	int subject = 0; 
    	
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher)session.getAttribute("user");
        subject = Integer.parseInt( request.getParameter("subject") );

        if (teacher == null) {
            // ログインしていない場合、ログインページへフォワード
            request.getRequestDispatcher("../login.jsp").forward(request, response);
            return;
        }

        // 成績一覧を取得
        TestListSubjectDao dao = new TestListSubjectDao();
        List<TestListSubject> testList = dao.filter(0, null, subject, null);

        // リクエストスコープに保存
        request.setAttribute("testList", testList);

        // 成績表示ページへフォワード
        request.getRequestDispatcher("test_list_subject.jsp").forward(request, response);
    }
}
