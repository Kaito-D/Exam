<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title>成績参照</title>
</head>
<body>
<h1> 成績参照</h1>


<!-- 科目情報検索フォーム -->
<form action="TestRegist.action" method="get">
<fieldset>
<legend>科目情報</legend>

    <label>入学年度</label>
<select name="f1">
<option value="">----</option>
<c:forEach var="y"  begin="2018" end="2025">
<option value="${y}" <c:if test="${y == year}">selected</c:if>>${y}</option>
</c:forEach>
</select>

    <label>クラス</label>
<select name="f2">
<option value="">----</option>
<c:forEach var="cls" items="${classNumList}">
<option value="${cls}" <c:if test="${cls == classId}">selected</c:if>>${cls}</option>
</c:forEach>
</select>

    <label>科目</label>
<select name="f3">
<option value="">----</option>
<c:forEach var="sub" items="${subjectList}">
<option value="${sub.cd}" <c:if test="${sub.cd == subjectId}">selected</c:if>>${sub.name}</option>
</c:forEach>
</select>

    <label>回数</label>
<select name="f4">
<option value="">----</option>
<c:forEach var="c" begin="1" end="2">
<option value="${y}" <c:if test="${c == count}">selected</c:if>>${c}</option>
</c:forEach>
</select>
    <button type="submit" name="searchType" value="subject">検索</button>

</fieldset>

</form>




<form action="TestRegistExecute.action" method="get">
	<c:choose>
	<c:when test="${testlist.size()>0 }">
	<div>検索結果：${testlist.size()}件</div>
	<table>
	<tr>
	<th>入学年度</th>
	<th>クラス</th>
	<th>学番</th>
	<th>氏名</th>
	<th>点数</th>
	</tr>
	<c:forEach var="test" items="${testlist }">
	<tr>
	<td>${f1}</td>
	<td>${f2}</td>
	<td>${test.student.no }</td>
	<td>${test.student.name}</td>
	<td><input type="number" name="" value=${ test.point }></td>
	</tr>
	</c:forEach>
	</table>
	</c:when>
	</c:choose>

    <button type="submit" name="save" value="subject">変更終了</button>


</form>


