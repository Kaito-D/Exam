<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
<c:param name="title">得点管理システム</c:param>
<c:param name="content">
    <form action="StudentList.action" method="post">
        <h2>学生情報登録</h2>
        <div><a href="StudentCreate.action">新規登録</a></div>
        <div>
            <label>入学年度</label>
            <select name="f1">
                <option value="0">---------</option>
                <c:forEach var="year" items="${ent_year_set }">
                    <option value="${year }" <c:if test="${year==f1 }">selected</c:if>>${year}</option>
                </c:forEach>
            </select>
        </div>
        <div>
            <label>クラス</label>
            <select name="f2">
                <option value="0">---------</option>
                <c:forEach var="num" items="${class_num_set }">
                    <option value="${num }" <c:if test="${num==f2 }">selected</c:if>>${num }</option>
                </c:forEach>
            </select>
        </div>
        <div>
            <label>在学生</label>
            <input type="checkbox" name="f3" value="t" <c:if test="${!empty f3 }">checked</c:if> />
        </div>
        <div>
            <input type="submit" name="" value="絞込み" />
        </div>
    </form>
    <c:choose>
        <c:when test="${students.size()>0 }">
            <div>検索結果：${students.size()}件</div>
            <table>
                <tr>
                    <th>入学年度</th>
                    <th>学生番号</th>
                    <th>氏名</th>
                    <th>クラス</th>
                    <th>在学生</th>
                    <th></th>
                </tr>
                <c:forEach var="student" items="${students }">
                    <tr>
                        <td>${student.entYear }</td>
                        <td>${student.no }</td>
                        <td>${student.name }</td>
                        <td>${student.classNum }</td>
                        <td class="text-center">
                            <c:choose>
                                <c:when test="${student.isAttend() }">○</c:when>
                                <c:otherwise>×</c:otherwise>
                            </c:choose>
                        </td>
                        <td><a href="StudentUpdate.action?no=${student.no }">変更</a></td>
                    </tr>
                </c:forEach>
            </table>
        </c:when>
    </c:choose>
</c:param>
</c:import>