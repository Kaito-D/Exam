// 学生別成績一覧

package bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TestListSubject implements Serializable {

    /** 入学年度 : int */
    private int entYear;

    /** 学生番号 : String */
    private String studentNo;

    /** 学生名 : String */
    private String studentName;

    /** クラス : String */
    private String classNum;

    /** 成績とその科目 : Map<Integer, Integer> */
    private Map<Integer, Integer> points = new HashMap<>();

    /** ゲッター・セッター */
    public int getEntYear() {
        return entYear;
    }

    public void setEntYear(int entYear) {
        this.entYear = entYear;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    public Map<Integer, Integer> getPoints() {
        return points;
    }

    public void setPoints(Map<Integer, Integer> points) {
        this.points = points;
    }

    /**
     * (テスト図鑑)から値(成績)をゲットするメソッド
     * @param key テスト図鑑 : int
     * @return 成績 (String)
     */
    public String getPoint(int key) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        map = getPoints();
        return Integer.toString(map.get(key));
    }

    /**
     * pointsフィールドにMapでテスト図鑑と成績をセットするメソッド
     * @param key テスト図鑑 : int
     * @param value 成績 : int
     */
    public void putPoint(int key, int value) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        map = getPoints();
        map.put(key, value);
        setPoints(map);
    }
}