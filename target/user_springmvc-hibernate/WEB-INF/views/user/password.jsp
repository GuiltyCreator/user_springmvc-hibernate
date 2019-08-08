<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<section>
	<h4 class="form-title">编辑用户密码</h4>
	<form id="userPasswordForm" name="user" action="#" method="post" onSubmit="return false;" class="form nice-validator n-yellow" data-validator-option="{timely:2, theme:'yellow_top'}" novalidate="novalidate">
		<form:hidden path="user.id"/>
		<div class="form-input">
			<div class="form-left">
				<div class="input-group ">
					<label class="input-label">用户名称：${user.name }</label>
				</div>
				<div class="input-group ">
					<label class="input-label">登录密码：</label>
					<form:password id="password" path="user.password" class="input" placeholder="请输入您的密码..." data-rule="登录密码: required;length(6~8);" />
				</div>
				<div class="input-group ">
					<label class="input-label">重复密码：</label>
					<form:password path="user.passwordAgain" class="input" placeholder="请输入您的确认密码..." data-rule="重复密码: required;length(6~8);match(password);" />
				</div>
			</div>
		</div>
		<div class="form-control">
			<hr>
			<button type="submit" id="updateUserPasswordBtn" class="button-green">保存</button>
			<button type="reset" class="button-blue">重置</button>
			<button type="button" id="returnUserListBtn" class="button-gray">返回</button>
		</div>
	</form>
</section>
<script type="text/javascript">
    $(function() {
        //点击返回按钮事件
        $("#returnUserListBtn").on("click", function(e) {
            loadMainContent(ctx + "/user/list");
        });

        // 
        $("#updateUserPasswordBtn").on("click", function(e) {
            // 校验不通过不做处理
            if (!$('#userPasswordForm').isValid()) {
                return false;
            }
            //
            $("#userPasswordForm").ajaxSubmit({
                url : ctx + "/user/password/update",
                dataType : "json",
                data : {},
                success : function(result) {
                    console.log(result);
                    if (result.status == 0) {
                        loadMainContent(ctx + "/user/list");
                        alert("用户密码更新成功！");
                    } else {
                        alert(result.data);
                    }
                }
            });
        });
    })
</script>