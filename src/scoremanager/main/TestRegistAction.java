package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestRegistAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher)session.getAttribute("user");
        TestDao testDao = new TestDao();
        ClassNumDao classDao = new ClassNumDao();
        SubjectDao subjectDao = new SubjectDao();
        School school = teacher.getSchool();

        // フォームパラメータ取得
        String f0 = req.getParameter("f1"); // 入学年度
        String f2 = req.getParameter("f2"); // クラス番号
        String f3 = req.getParameter("f3"); // 科目名
        String f5 = req.getParameter("f4"); // 回数

        if (f0 == null || f0.isEmpty()) {
            f0 = "0";
        }
        if (f5 == null || f5.isEmpty()) {
            f5 = "0";
        }
        	int f1 = Integer.parseInt(f0);
        	int f4 = Integer.parseInt(f5);
        // 初期値

        try {
            f1 = Integer.parseInt(f0);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            // エラー処理（例：デフォルト値にする、エラーメッセージを表示するなど）
        }


        List<Test> testList=new ArrayList<>();
        if(f2!=null || f3!=null){
        	testList = testDao.filter(f1,f2,subjectDao.get(f3,school),f4,school);
	        // フィルター項目をJSPに戻す

        }

        req.setAttribute("testlist", testList);
        req.setAttribute("f1", f0);
        req.setAttribute("f2", f2);
        req.setAttribute("f3", f3);
        req.setAttribute("f4", f5);
        // 成績取得処理



        List<String> classNumList = null;
        try {
            classNumList = classDao.filter(school);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Subject> subjectList = null;
        try {
            subjectList = subjectDao.filter(school);
        } catch (Exception e) {
            e.printStackTrace();
        }
        req.setAttribute("classNumList", classNumList);
        req.setAttribute("subjectList", subjectList);
        req.getRequestDispatcher("/scoremanager/main/test_regist.jsp").forward(req, res);
    }
}
