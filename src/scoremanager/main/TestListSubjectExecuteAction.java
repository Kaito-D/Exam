package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Subject; // 追加: Subject Beanをインポート
import bean.Teacher;
import bean.Test;    // 追加: Test Beanをインポート
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao; // 追加: SubjectDaoをインポート
import dao.TestDao;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // TODO 自動生成されたメソッド・スタブ
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher)session.getAttribute("user");

        // ローカル変数の設定
        String entYearStr = ""; // 入力された入学年度
        String classNum = ""; // 入力されたクラス番号
        String isAttendStr = ""; // 入力された在学フラグ
        String subjectCd = ""; // 追加: 入力された科目コード
        String numStr = "";    // 追加: 入力された回数
        int entYear = 0; // 入学年度
        int num = 0;     // 追加: 回数（整数型）
        boolean isAttend = false; // 在学フラグ
        List<Student> students = null; // 学生リスト
        LocalDate todaysDate = LocalDate.now();
        int year =  todaysDate.getYear(); // 現在年度を取得
        StudentDao studentDao = new StudentDao(); // 学生Dao
        ClassNumDao classNumDao = new ClassNumDao(); // クラス番号Daoを初期化
        TestDao testDao = new TestDao();
        SubjectDao subjectDao = new SubjectDao(); // 追加: SubjectDaoを初期化
        Map<String, String> errors = new HashMap<>(); // エラーメッセージ

        // リクエストパラメータの取得
        entYearStr = req.getParameter("f1");
        classNum = req.getParameter("f2");
        isAttendStr = req.getParameter("f3");
        subjectCd = req.getParameter("f4"); // 追加
        numStr = req.getParameter("f5");    // 追加

        // ビジネスロジック
        if (entYearStr != null && !entYearStr.isEmpty()) { // 空文字チェックも追加
            entYear = Integer.parseInt(entYearStr);
        }

        if (numStr != null && !numStr.isEmpty()) { // 追加: 回数の数値変換と空文字チェック
            num = Integer.parseInt(numStr);
        }

        if (isAttendStr != null && !isAttendStr.isEmpty()) { // 空文字チェックも追加
            isAttend = true;
        }

        // 入学年度のセットを生成（現在年度と前年度）
        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 1; i < year + 1; i++) {
            entYearSet.add(i);
        }

        // クラス番号のリストを取得
        List<String> classNumSet = classNumDao.filter(teacher.getSchool());

        // 科目のリストを取得
        List<Subject> subjectSet = subjectDao.filter(teacher.getSchool()); // 追加

        // 回数のリストを生成（例: 1回, 2回） - 必要に応じてデータベースから取得するロジックに変更
        List<Integer> numSet = new ArrayList<>(); // 追加
        numSet.add(1); // 例として1回
        numSet.add(2); // 例として2回
        // もし試験回数が動的であれば、TestDaoなどから取得するロジックが必要

        // 学生のフィルタリングロジック
        if (entYear != 0 && !classNum.equals("0")) {
            students = studentDao.filter(teacher.getSchool(), entYear, classNum, isAttend);
        } else if (entYear != 0 && classNum.equals("0")) { // クラスが未選択の場合
            students = studentDao.filter(teacher.getSchool(), entYear, isAttend);
        } else if (entYear == 0 && (classNum == null || classNum.equals("0"))) { // 入学年度もクラスも未選択の場合
            students = studentDao.filter(teacher.getSchool(), isAttend);
        } else { // 想定外の組み合わせ（クラス指定があるのに年度指定がないなど）
            errors.put("f1", "クラスを指定する場合は入学年度も指定してください");
            req.setAttribute("errors", errors);
            // エラー時でも、何らかのリストを表示するために在籍者全員をフィルタリング
            students = studentDao.filter(teacher.getSchool(), isAttend);
        }

        // 取得した生徒リストに対して、さらに科目と回数で試験結果を絞り込む、または試験結果を付加する
        if (students != null && !students.isEmpty()) {
            for (Student student : students) {
                // ここで各生徒の試験結果を取得するロジックを追加
                // 例: TestDaoを使って、生徒ID、科目コード、回数に基づいて試験結果を取得
                // testDao.filterByStudentSubjectNum(student.getNo(), subjectCd, num);
                // 取得した試験結果をStudent Beanにセットするか、別途Mapで管理するなど
                // ここでは簡略化のため具体的なTestオブジェクトの取得・設定は省略します。
                // 実際には、Test Beanに必要な情報（点数など）が含まれるはずです。
                List<Test> studentTests = testDao.filter(student.getSchool());
                // student.setTests(studentTests); // Student BeanにTestリストを保持するフィールドがあればセット
            }
        }


        // リクエスト属性の設定
        req.setAttribute("f1", entYear);
        req.setAttribute("f2", classNum);
        req.setAttribute("f3", isAttendStr); // f3はhidden属性なので、値の保持が重要
        req.setAttribute("f4", subjectCd);   // 追加
        req.setAttribute("f5", num);         // 追加

        req.setAttribute("students", students);
        req.setAttribute("class_num_set", classNumSet); // 変数名をlistからclassNumSetに変更（より分かりやすく）
        req.setAttribute("ent_year_set", entYearSet);
        req.setAttribute("subject_set", subjectSet); // 追加
        req.setAttribute("num_set", numSet);         // 追加

        // jspへフォワード
        req.getRequestDispatcher("test_regist_subject.jsp").forward(req, res);
    }
}