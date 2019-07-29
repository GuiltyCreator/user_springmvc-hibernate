<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>用户查询</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="keywords" content="实训,教育,IT">
<meta name="description" content="轻实训用户注册案例">
<meta name="author" content="轻实训">

<!-- 引入 jQuery 库 -->
<script src="plugins/jquery-2.2.4.min.js"></script>

<link href="${ctx }/plugins/waitme/waitMe.min.css" rel="stylesheet">
<script src="${ctx }/plugins/waitme/waitMe.min.js"></script>

<!-- 引入日期选择器插件 -->
<link href="${ctx }/plugins/air-datepicker/css/datepicker.min.css" rel="stylesheet" type="text/css">
<script src="${ctx }/plugins/air-datepicker/js/datepicker.min.js"></script>
<!-- 日期选择器引入中文语言 -->
<script src="${ctx }/plugins/air-datepicker/js/i18n/datepicker.zh.js"></script>

<link href="${ctx }/plugins/nice-validator/jquery.validator.css" rel="stylesheet">
<script src="${ctx }/plugins/nice-validator/jquery.validator.min.js"></script>
<script src="${ctx }/plugins/nice-validator/local/zh-CN.js"></script>

<link href="${ctx }/plugins/pagination/simplePagination.css" rel="stylesheet">
<script src="${ctx }/plugins/pagination/jquery.simplePagination.js"></script>

<script src="${ctx }/plugins/jquery.form.js"></script>

<!-- 引入自定义样式文件 -->
<link rel="stylesheet" href="css/style.css">
</head>

<body>
	<header>
		<h2>用户管理系统</h2>
		<nav>
			<a href="#">帮助</a>
			<a href="${ctx }/logout">退出</a>
		</nav>
	</header>
	<div class="content">
		<aside id="left" class="left">
			<div class="menu">
				<a href="${ctx}/user/list" class="menu-item">用户管理</a>
				<a href="${ctx}/profession/list" class="menu-item">职业管理</a>
				<a href="${ctx}/hobby/list" class="menu-item">爱好管理</a>
			</div>
			<div class="head-avatar">
				<img src="images/avatar.png" alt="头像" />
				<h5>当前登录用户</h5>
			</div>
		</aside>
		<div id="right" class="right">
			<div id="mainPanel" class="main-panel"></div>
			<footer id="footer"> Copyright © 2017.轻实训版权所有 </footer>
		</div>
	</div>

	<!-- 引入自定义公用 js 库 -->
	<script src="js/common.js"></script>

	<script type="text/javascript">
        var ctx = "${ctx}";
        var $mainPanel;
        $(function() {
            $mainPanel = $("#mainPanel");
            //左侧菜单项点击事件
            $("#left a.menu-item").click(function(e) {
                //阻止 a 链接元素的默认的事件
                e.preventDefault();
                var url = $(this).attr("href");
                if (url === "#") {
                    return false;
                }
                //根据 url 加载内容
                loadMainContent(url);
                //去掉所有菜单的选中状态
                $(this).parent().find("a.menu-item").removeClass("active");
                //为当前菜单添加选中状态
                $(this).addClass("active");
            }).first().click(); //默认点击（加载）第一个菜单内容
        })

        //在主内容区加载内容
        function loadMainContent(url, data, callback) {
            //显示加载中提示
            startLoading();
            $mainPanel.load(url, data, function(e) {
                //加载完成后，停止加载中提示
                stopLoading();
                //加载完成的回调函数
                if ($.isFunction(callback)) {
                    callback.call(this);
                }
            });
        }

        //编辑数据信息
        function doEdit(url) {
            loadMainContent(url);
        }

        //删除数据信息
        function doDelete(url, id, returnUrl, message) {
            //提示是否确认删除
            var deleteConfirm = confirm("是否确认删除：" + message + " ?");
            if (deleteConfirm == true) {
                //显示加载中提示
                startLoading();
                //通过 Ajax 执行数据删除操作
                $.ajax({
                    type : "POST",
                    url : url,
                    data : {
                        id : id
                    },
                    dataType : "json",
                    success : function(result) {
                        console.log(result);
                        //执行成功
                        if (result.status == 0) {
                            loadMainContent(returnUrl);
                            alert("删除成功！");
                        } else {
                            alert(result.data);
                        }
                    },
                    complete : function(data) {
                        //执行完成后，停止加载中提示
                        stopLoading();
                    }
                });
            }
        }

        //初始化数据分页
        function initPagingEvent(data) {
            $(".page-link").on("click", function(e) {
                e.preventDefault();
                var url = $(this).attr("href");
                loadMainContent(url, data);
            });
        }

        //显示加载中提示（基于 waitMe 插件实现）
        function startLoading() {
            $("body").waitMe({
                effect : 'bounce',
                text : '数据加载中...',
                bg : 'rgba(255,255,255,0.7)',
                color : '#000',
                sizeW : '',
                sizeH : ''
            });
        }

        //停止加载中提示
        function stopLoading() {
            $("body").waitMe('hide');
        }
    </script>
</body>
</html>
