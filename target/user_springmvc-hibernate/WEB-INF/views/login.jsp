<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>登录</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="keywords" content="实训,教育,IT">
<meta name="description" content="轻实训用户登录案例">
<meta name="author" content="轻实训">

<!-- 引入 jQuery 库 -->
<script src="plugins/jquery-2.2.4.min.js"></script>

<style>
html {
	margin: 0;
	padding: 0;
	border: 0;
	font-size: 100%;
}

body {
	text-align: center;
}

.login-container {
	width: 780px;
	height: 360px;
	margin: 10% auto 0 auto;
	padding: 15px;
	border: solid 1px #ccc;
}

.container-left {
	width: 40%;
	float: left;
}

.container-right {
	width: 60%;
	float: left;
}

.login-container h1 {
	font-size: 25px;
	font-weight: 700;
	line-height: 60px;
	background: url(/images/logo.png) no-repeat 90px;
	background-size: 50px;
	border-bottom: 1px solid #ddd;
}

form {
	position: relative;
	width: 100%;
	text-align: center;
}

.input-group  .input {
	width: 50%;
	height: 30px;
	margin-top: 30px;
	padding: 0 10px;
	font-size: 14px;
}

.remember-me {
	width: 100%;
	text-align: left;
	margin-left: 15%;
	margin-top: 30px;
}

.login-control {
	margin-top: 20px;
}

.login-control button {
	width: 80px;
	height: 25px;
	margin: 15px;
}
</style>
</head>

<body>
	<div class="login-container">
		<div class="container-left">
			<img src="image/login.jpg">
		</div>
		<div class="container-right">
			<h1>用户管理系统登录</h1>
			<form id="loginForm" action="${ctx }/login" id="signin-form" onsubmit="return login()" method="post">
				<div class="input-group">
					<label>用户名：</label> <input type="text" name="name" class="input" id="username" placeholder="请输入您的用户名！">
				</div>
				<div class="input-group">
					<label class="letter">密&nbsp;&nbsp;&nbsp;码：</label> <input type="password" name="password" class="input" id="passWord" placeholder="请输入您的登录密码！">
				</div>
				<div class="remember-me">
					<input type="checkbox" id="remember-me" name="remember-me"> <label for="remember-me">记住密码</label>
				</div>
				<c:if test="${error != null }">
					<div class="alert alert-warning fade in">${error }</div>
				</c:if>
				<div class="login-control">
					<button type="submit" class="">登录</button>
					<button type="reset" class="">重置</button>
				</div>
			</form>
		</div>
	</div>
	<script type="text/javascript">
        // 登陆验证函数
        function login() {
            //获取用户输入的用户名
            var username = $("#username").val();
            //获取用户输入的密码
            var password = $("#passWord").val();

            //验证用户输入项
            if (username == "") {
                alert("用户名不允许为空！");
                return false;
            } else if (password == "") {
                alert("密码不允许为空！");
                return false;
            }
            return true;
        }
    </script>
</body>
</html>
