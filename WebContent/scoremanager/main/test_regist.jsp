<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp" >
	<!-- ヘッダー -->
	<c:param name="title">得点管理システム</c:param>
	<!-- ボディ -->
	<c:param name="content">
	<form action="TestListStudentExecute.action">
		<div>
			<h2>成績管理</h2>
			<div>
				<label>入学年度</label>
				<select name="f1"/>
				<option value="0">--------</option>
				<c:forEach var="yearfor" items="${ent_year}">
					<option value="${yearfor}">${yearfor}</option>
				</c:forEach>
			</div>
			<div>
				<label>クラス</label>
				<select name="f2"/>
					<option value="0">--------</option>
					<c:forEach var="classfor" items="${class_num_set}">
						<option value="${classfor}">${classfor}</option>
					</c:forEach>
			</div>
			<div>
				<label>科目</label>
				<select name="f3"/>
				<option value="0">--------</option>
				<c:forEach var="subfor" items="${subject}">
					<option value="${subfor}">${subfor}</option>
				</c:forEach>
			</div>
			<div>
				<label>回数</label>
				<select name="f4">
				    <option value="0">--------</option>
				    <c:forEach var="count" begin="1" end="10">
				        <option value="${count}">${count}回目</option>
				    </c:forEach>
				</select>
			</div>
			<div>
				<label>検索</label>
				<input type="button" />
				<option value="0">--------</option>
			</div>
		</div>
	</form>
	</c:param>
	<c:param name="content">
	</c:param>
</c:import>