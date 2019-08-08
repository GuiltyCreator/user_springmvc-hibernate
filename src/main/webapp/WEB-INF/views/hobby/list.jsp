<%--
  Created by IntelliJ IDEA.
  User: a7279
  Date: 2019/8/5
  Time: 14:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shixun.online/paging" prefix="qsx"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<section class="toolbar">
    <button id="addHobbyBtn" type="button">新增爱好</button>
</section>
<section>
    <table id="hobby" class="table">
        <thead>
        <tr>
            <td>序号</td>
            <td>爱好名称</td>
            <td>创建时间</td>
            <td align="center">操作</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${pageInfo.result}">
            <tr>
                <td>${item.id}</td>
                <td>${item.name}</td>
                <td><fmt:formatDate value="${item.creatTime }" type="date" pattern="yyyy-MM-dd HH:MM:SS" /></td>
                <td align="center"><a href="JavaScript:doEdit('${ctx }/hobby/${item.id }/edit');" class="edit">【编辑】</a>  <a href="JavaScript:doDelete('${ctx }/hobby/delete','${item.id }','${ctx }/hobby/list','${item.name }');" class="delete">【删除】</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div style="float: right; margin-top: 15px;">
        <qsx:paging url="/hobby/list?page=" />
    </div>

</section>

<script type="text/javascript">
    $(function() {

        //新增爱好
        $("#addHobbyBtn").on("click", function(e) {
            loadMainContent(ctx + "/hobby/add");
        });
        //初始化数据分页
        initPagingEvent("");
    })

</script>

