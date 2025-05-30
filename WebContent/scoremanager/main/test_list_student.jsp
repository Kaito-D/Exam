<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="bean.TestListStudent" %>
<%

    @SuppressWarnings("unchecked")
    List<TestListStudent> testList = (List<TestListStudent>) request.getAttribute("testList");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>テスト成績一覧</title>
</head>
<body>
    <h2>テスト成績一覧</h2>

    <%
        if (testList != null && !testList.isEmpty()) {
            for (TestListStudent test : testList) {
    %>
        <p>科目名: <%= test.getSubjectName() %></p>
        <p>科目コード: <%= test.getSubjectCd() %></p>
        <p>テスト回数: <%= test.getNum() %></p>
        <p>得点: <%= test.getPoint() %></p>
        <hr>
    <%
            }
        } else {
    %>
        <p>成績データがありません。</p>
    <%
        }
    %>
</body>
</html>
