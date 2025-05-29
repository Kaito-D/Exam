package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import bean.Test;
import dao.TestDao;
import tool.Action;

public class TestRegistExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // セッション情報の取得
        HttpSession session = req.getSession(false);
        if (session == null || !(session.getAttribute("user") instanceof Teacher)) {
            forwardError(req, res, "ログインしてください。");
            return;
        }
        Teacher teacher = (Teacher) session.getAttribute("user");

        // リクエストパラメータ取得
        Map<String, String> params = new HashMap<>();
        params.put("studentId", req.getParameter("student_id"));
        params.put("subjectCd", req.getParameter("subject_cd"));
        params.put("scoreValue", req.getParameter("score"));
        params.put("entYearStr", req.getParameter("ent_year"));
        params.put("classNum", req.getParameter("class_num"));
        params.put("noStr", req.getParameter("no"));
        params.put("testNoStr", req.getParameter("test_no"));

        // 必須項目チェック
        if (params.values().stream().anyMatch(value -> value == null || value.isEmpty())) {
            forwardError(req, res, "すべての項目を入力してください。");
            return;
        }

        try {
            int scoreNum = validateAndParseInteger(params.get("scoreValue"), 0, 100, "成績は0～100の範囲で入力してください。");
            int entYear = validateAndParseInteger(params.get("entYearStr"), "入学年度は正しい値を入力してください。");
            int no = validateAndParseInteger(params.get("noStr"), "出席番号は正しい値を入力してください。");
            int testNo = validateAndParseInteger(params.get("testNoStr"), 1, 2, "試験回数は1または2のみ選択可能です。");

            // Testオブジェクトの作成
            Test test = new Test();

            // DAOによる保存処理
            TestDao testDao = new TestDao();
            if (!testDao.save(test)) {
                forwardError(req, res, "成績の登録に失敗しました。");
                return;
            }
            
            req.getRequestDispatcher("score_register_done.jsp").forward(req, res);

        } catch (NumberFormatException e) {
            forwardError(req, res, e.getMessage());
        }
    }

    // 汎用的な数値変換＆バリデーション
    private int validateAndParseInteger(String value, String errorMessage) throws NumberFormatException {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(errorMessage);
        }
    }

    private int validateAndParseInteger(String value, int min, int max, String errorMessage) throws NumberFormatException {
        int parsedValue = validateAndParseInteger(value, errorMessage);
        if (parsedValue < min || parsedValue > max) {
            throw new NumberFormatException(errorMessage);
        }
        return parsedValue;
    }

    private void forwardError(HttpServletRequest req, HttpServletResponse res, String message) throws Exception {
        req.setAttribute("error", message);
        req.getRequestDispatcher("error.jsp").forward(req, res);
    }
}