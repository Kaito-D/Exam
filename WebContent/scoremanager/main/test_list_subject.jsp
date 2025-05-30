<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="bean.TestListSubject" %>
<%@ page import="java.util.Map" %>
<%
    TestListSubject subject = (TestListSubject) request.getAttribute("subject");

    if (subject == null) {
        subject = new TestListSubject();
        subject.setEntYear(2022);
        subject.setStudentNo("STU123");
        subject.setStudentName("佐藤 花子");
        subject.setClassNum("3A");
        subject.putPoint(1, 85);
        subject.putPoint(2, 92);
        subject.putPoint(3, 78);
    }

    Map<Integer, Integer> scores = subject.getPoints();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>学生別成績一覧</title>
</head>
<body>
    <h2>学生別成績一覧</h2>
    <p>入学年度: <%= subject.getEntYear() %></p>
    <p>学生番号: <%= subject.getStudentNo() %></p>
    <p>学生名: <%= subject.getStudentName() %></p>
    <p>クラス: <%= subject.getClassNum() %></p>

    <h3>テスト成績</h3>
    <%
        for (Map.Entry<Integer, Integer> entry : scores.entrySet()) {
    %>
        <p><%= entry.getKey() %> 回目: <%= entry.getValue() %> 点</p>
    <%
        }
    %>
</body>
</html>
