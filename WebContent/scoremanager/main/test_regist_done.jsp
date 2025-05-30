<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>
	<c:param name="content">
		<h2>成績管理</h2>
		<div><p><label>登録が完了しました</label></div>
		<div><a href="TestRegistExecute.action">戻る</a></div>
		<div><a href="TestRegist.action">学生一覧</a></div>
	</c:param>
</c:import>