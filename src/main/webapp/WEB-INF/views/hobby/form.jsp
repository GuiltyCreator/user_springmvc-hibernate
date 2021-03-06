<%--
  Created by IntelliJ IDEA.
  User: a7279
  Date: 2019/8/5
  Time: 15:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<section>
    <h4 class="form-title">
        <c:if test="${hobby.id == 0}">
            新增爱好信息
        </c:if>
        <c:if test="${hobby.id != 0}">
            编辑爱好信息
        </c:if>
    </h4>
    <form id="hobbyForm" name="hobby" action="#" method="post" onSubmit="return false;"
          class="form nice-validator n-yellow" data-validator-option="{timely:2, theme:'yellow_top'}"
          novalidate="novalidate">
        <form:hidden path="hobby.id"/>
        <div class="form-input">
            <div class="form-left">
                <div class="input-group ">
                    <label class="input-label">职业名称：</label>
                    <form:input id="name" path="hobby.name" maxlength="100" class="input" placeholder="爱好名称..."
                                data-rule="爱好名称: required;" autofocus="autofocus"/>
                </div>
                <div class="form-control">
                    <hr>
                    <button type="submit" id="saveHobbyBtn" class="button-green">保存</button>
                    <button type="reset" class="button-blue">重置</button>
                    <button type="button" id="returnHobbyListBtn" class="button-gray">返回</button>
                </div>
            </div>
        </div>
    </form>
</section>
<script type="text/javascript">
    $(function() {

        //点击返回按钮事件
        $("#returnHobbyListBtn").on("click", function(e) {
            loadMainContent(ctx + "/hobby/list");
        });


        $("#saveHobbyBtn").on("click", function(e) {
            // 校验不通过不做处理
            if (!$('#HobbyForm').isValid()) {
                return false;
            }
            if($('#name')==""||$('#name')==null){
                alert("名称不能为空！")
                return false;
            }
            startLoading();

            $("#hobbyForm").ajaxSubmit({
                url: ctx + "/hobby/save",
                dataType: "json",
                data: {},
                success: function (result) {
                    console.log(result);
                    if (result.status == 0) {
                        loadMainContent(ctx + "/hobby/list");
                        alert("爱好保存成功！");
                    } else {
                        alert(result.data);
                    }
                },
                complete: function (data) {
                    stopLoading();
                }
            });
        });
    })
</script>
