<!-- 成績参照検索JSP -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp">
  <c:param name="title">得点情報システム</c:param>
  <c:param name="content">
    <h2>成績参照</h2>

    <div>
      <form action="TestListSubjectExecute.action" method="get">
        <div>
          <div>
            <p>科目情報</p>
          </div>

          <div style="display:inline;">
            <label>入学年度</label>
            <select name="f1">
              <option value="0">--------</option>
              <c:forEach var="year" items="${entYearSet}">
                <option value="${year}" <c:if test="${year==f1}">selected</c:if>>${year}</option>
              </c:forEach>
            </select>
          </div>

          <div style="display:inline;">
            <label>クラス</label>
            <select name="f2">
              <option value="0">--------</option>
              <c:forEach var="num" items="${Numlist}">
                <option value="${num}" <c:if test="${num==f2}">selected</c:if>>${num}</option>
              </c:forEach>
            </select>
          </div>

          <div style="display:inline;">
            <label>科目</label>
            <select name="f3">
              <option value="0">--------</option>
              <c:forEach var="subject" items="${list}">
                <option value="${subject.cd}" <c:if test="${subject.cd==f3}">selected</c:if>>${subject.name}</option>
              </c:forEach>
            </select>
          </div>

          <div style="display:inline;">
            <input type="hidden" name="f" value="sj" />
            <input type="submit" value="検索" />
          </div>

          <div class="mt-2 text-warning">${errors.get("1")}</div>
        </div>
      </form>

      <form action="TestListStudentExecute.action" method="get">
        <div>
          <div>
            <p>学生情報</p>
          </div>

          <div style="display:inline;">
            <label>学生番号</label>
            <input type="text" name="f4" value="${f4}" />
          </div>

          <div style="display:inline;">
            <input type="hidden" name="f" value="st" />
            <input type="submit" value="検索" />
          </div>
        </div>
      </form>
    </div>

    <p>科目情報を選択または学生情報を入力して検索ボタンをクリックしてください</p>

     </c:param>
</c:import>
