<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shixun.online/paging" prefix="qsx"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<section class="toolbar">
	<button id="addUserBtn" type="button">新增用户</button>
	<button id="batchDeleteUserBtn" type="button">批量删除</button>
	<button id="exportUserBtn" type="button">导出用户</button>
	<label for="">用户名称：</label> <input id="searchUserName" type="text" name="searchUserName" value="${searchUserName }" />
	<button id="searchUserBtn" type="button">查询</button>
	<button id="resetSearchUserBtn" type="button">重置</button>
</section>
<section>
	<table id="userTable" class="table">
		<thead>
			<tr>
				<td><input type="checkbox" id="selectAll" />用户名称</td>
				<td>职业</td>
				<td>邮箱</td>
				<td>性别</td>
				<td>生日</td>
				<td>爱好</td>
				<td>创建时间</td>
				<td align="center">操作</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${pageInfo.result}">
				<tr>
					<td><input type="checkbox" class="check" value="${item.id }" />${item.name}</td>
					<td>${item.profession }</td>
					<td>${item.email }</td>
					<td>${item.gender }</td>
					<td><fmt:formatDate value="${item.birthday }" type="date" pattern="yyyy-MM-dd" /></td>
					<td> </td>
					<td></td>
					<td align="center"><a href="JavaScript:doEdit('${ctx }/user/${item.id }/edit');" class="edit">【编辑】</a> <a href="JavaScript:doEditUserPassword('${ctx }/user/${item.id }/password/edit');" class="edit">【修改密码】</a> <a href="JavaScript:doDelete('${ctx }/user/delete','${item.id }','${ctx }/user/list','${item.name }');" class="delete">【删除】</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div style="float: right; margin-top: 15px;">
		<qsx:paging url="/user/list?page=" />
	</div>

</section>

<script type="text/javascript">
    $(function() {
        //点击全选Checkbox
        $("#selectAll").on("click", function(e) {
            //注意：此处不可以使用 .attr("checked", this.checked)
            $("#right").find(".check").prop("checked", this.checked);
        });

        //新增用户
        $("#addUserBtn").on("click", function(e) {
            loadMainContent(ctx + "/user/add");
        });

        //批量删除用户
        $("#batchDeleteUserBtn").on("click", function(e) {
            //待删除用户Id数组
            var selectedUserIds = [];
            //遍历已经选择的用户
            $("#userTable").find("input[type=checkbox].check:checked").each(function(index, e) {
                var userId = $(this).val();
                selectedUserIds.push(userId);
            });

            //未选择待删除用户
            if (selectedUserIds.length == 0) {
                alert("请选择待删除用户！");
                return false;
            }

            //提示是否确认删除
            var deleteConfirm = confirm("是否确认删除用户?");
            if (deleteConfirm == false) {
                return false;
            }

            //提交删除操作
            $.ajax({
                url : ctx + "/user/delete/batch",
                type : "POST",
                traditional : true, //重要参数：序列化的传统样式
                cache : false,
                dataType : "json",
                data : {
                    ids : selectedUserIds
                },
                success : function(result) {
                    //
                    loadMainContent(ctx + "/user/list");
                    alert("删除成功！");
                }
            });
        });

        //导出用户列表
        $("#exportUserBtn").on("click", function(e) {
            window.location.href = ctx + "/user/export";
        });

        //查询用户
        $("#searchUserBtn").on("click", function(e) {
            var url = ctx + "/user/list";
            loadMainContent(url, getSearchUserData());
        });

        //清除查询条件
        $("#resetSearchUserBtn").on("click", function(e) {
            var url = ctx + "/user/list";
            $("#searchUserName").val("");
            loadMainContent(url, getSearchUserData());
        });

        //初始化数据分页
        initPagingEvent(getSearchUserData());
    })

    //获取用户查询条件
    function getSearchUserData() {
        var searchData = {
            searchUserName : $("#searchUserName").val()
        }
        return searchData;
    }

    //修改用户密码
    function doEditUserPassword(url) {
        loadMainContent(url);
    }
</script>
