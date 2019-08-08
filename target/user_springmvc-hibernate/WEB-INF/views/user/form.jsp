<%--
  Created by IntelliJ IDEA.
  User: a7279
  Date: 2019/8/2
  Time: 15:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<section>
    <h4 class="form-title">
        <c:if test="${user.id == 0}">
            新增用户信息
        </c:if>
        <c:if test="${user.id != 0}">
            编辑用户信息
        </c:if>
    </h4>
    <form id="userForm" name="user" action="#" method="post" onSubmit="return false;" enctype="multipart/form-data" class="form nice-validator n-yellow" data-validator-option="{timely:2, theme:'yellow_top'}" novalidate="novalidate">
        <form:hidden path="user.id" id="id" />
        <div class="form-input">
            <div class="form-left">
                <div class="input-group ">
                    <label class="input-label">用户名称：</label>
                    <c:if test="${user.id == 0}">
                        <form:input id="name" path="user.name" maxlength="100" class="input" placeholder="请输入您的用户名..." data-rule="用户名: required;" autofocus="autofocus" />
                    </c:if>
                    <!-- 编辑时不允许修改名称  -->
                    <c:if test="${user.id != 0}">
                        ${user.name }
                    </c:if>
                </div>
                <!-- user.id == 0 表示新增用户（只在新增用户时，才输入密码） -->
                <c:if test="${user.id == 0}">
                    <div class="input-group ">
                        <label class="input-label">登录密码：</label>
                        <form:password id="password" path="user.password" class="input" placeholder="请输入您的密码..." data-rule="登录密码: required;length(6~8);" />
                    </div>
                    <div class="input-group ">
                        <label class="input-label">重复密码：</label>
                        <form:password path="user.passwordAgain" class="input" placeholder="请输入您的确认密码..." data-rule="重复密码: required;length(6~8);match(password);" />
                    </div>
                </c:if>
                <div class="input-group ">
                    <label class="input-label">电子邮箱：</label>
                    <form:input id="email" path="user.email" type="email" class="input" placeholder="请输入您的邮箱..." data-rule="电子邮箱: required;email;" />
                </div>
                <div class="input-group ">
                    <label class="input-label">出生日期：</label>
                    <form:input id="birthday" path="user.birthday" class='input' data-language='zh' placeholder="请选择您的出生日期..." data-rule="出生日期: required;" />
                </div>
                <div class="input-group">
                    <label class="radio-label">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</label>
                    <div class="radio-group">
                        <form:radiobutton id="gender" path="user.gender" value="男" label="男" />
                        <form:radiobutton id="gender" path="user.gender" value="女" label="女" />
                    </div>
                </div>
                <div class="input-group clr">
                    <label class="">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;业：</label>
                    <form:select id="profession" path="user.profession" class="select">
                        <form:options items="${allProfessions}" itemValue="id" itemLabel="name" />
                    </form:select>
                </div>
                <div class="input-group">
                    <label class="input-label">爱&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;好：</label>
                    <form:select id="hobby" class="multiple-select" path="user.hobby" multiple="multiple" size="4" data-rule="爱好: required;">
                        <c:forEach var="item" items="${allHobbies}">
                            <form:option label="${item.name }" value="${item.id}"  />
                        </c:forEach>
                    </form:select>
                </div>
            </div>
            <div class="form-right">
                <div class="upload-avatar ">
                    <!-- 图片选择输入框 -->
                    <input id="avatarImgFileInput" type="file" name="avatar" accept="image/*"  />
                    <c:if test="${user.name == null}">
                        <img id="avatarImgPreview" src="${ctx }/images/blank-avatar.png" />
                    </c:if>
                    <c:if test="${user.name != null}">
                        <img id="avatarImgPreview" src="${ctx }/images/avatar/${user.userAvatarImage }" />
                    </c:if>
                    <p>
                        <label for="avatarImgFileInput" class="select-avatar-label">选择头像图片</label>
                    </p>
                </div>
            </div>
        </div>
        <div class="form-control">
            <hr>
            <button type="submit" id="saveUserBtn" class="button-green">保存</button>
            <button type="reset" class="button-blue">重置</button>
            <button type="button" id="returnUserListBtn" class="button-gray">返回</button>
        </div>
    </form>
</section>
<script type="text/javascript">
    $(function() {
        //初始化日期生日选择
        $("#birthday").datepicker({
            dateFormat : "yyyy-mm-dd",
            autoClose : true
        });

        //点击返回按钮事件
        $("#returnUserListBtn").on("click", function(e) {
            loadMainContent(ctx + "/user/list");
        });

        // 选取一张图片，并预览
        $("#avatarImgFileInput").on("change", function(e) {
            // 获取图片资源
            var file = e.target.files[0];
            // 只选择图片文件
            if (!file.type.match('image.*')) {
                return false;
            }
            // 定义文件读取器
            var reader = new FileReader();
            // 读取文件
            reader.readAsDataURL(file);
            // 选择图片文件后，进行图片预览
            reader.onload = function(arg) {
                $("#avatarImgPreview").attr("src", arg.target.result);
            }
        });

        //
        $("#saveUserBtn").on("click", function(e) {
            // 校验不通过不做处理
            if (!$('#userForm').isValid()) {
                return false;
            }
            startLoading();
            //alert($("#avatarImgFileInput").val());
            if ($("#avatarImgFileInput").val() == "") {
                $("#avatarImgFileInput").remove();
            }

            //
            $("#userForm").ajaxSubmit({
                url : ctx + "/user/save",
                type:'POST',
                dataType : "json",
                data : {},
                success : function(result) {
                    console.log(result);
                    if (result.status == 0) {
                        loadMainContent(ctx + "/user/list");
                        alert("用户保存成功！");
                    } else {
                        alert(result.data);
                    }
                },
                complete : function(data) {
                    stopLoading();
                }
            });

        });
    })
</script>
