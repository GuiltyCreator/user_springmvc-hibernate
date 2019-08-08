<%--
  Created by IntelliJ IDEA.
  User: a7279
  Date: 2019/8/5
  Time: 14:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<section>
    <h4 class="form-title">
        <c:if test="${profession.id == 0}">
            新增职位信息
        </c:if>
        <c:if test="${profession.id != 0}">
            编辑职位信息
        </c:if>
    </h4>
    <form id="professionForm" name="profession" action="#" method="post" onSubmit="return false;"
          class="form nice-validator n-yellow" data-validator-option="{timely:2, theme:'yellow_top'}"
          novalidate="novalidate">
        <form:hidden path="profession.id"/>
        <div class="form-input">
            <div class="form-left">
                <div class="input-group ">
                    <label class="input-label">职业名称：</label>
                        <form:input id="name" path="profession.name" maxlength="100" class="input" placeholder="职业名称..."
                                    data-rule="职业名称: required;" autofocus="autofocus"/>
                </div>
                <div class="form-control">
                    <hr>
                    <button type="submit" id="saveProfessionBtn" class="button-green">保存</button>
                    <button type="reset" class="button-blue">重置</button>
                    <button type="button" id="returnProfessionListBtn" class="button-gray">返回</button>
                </div>
            </div>
        </div>
    </form>
</section>
<script type="text/javascript">
    $(function() {

        //点击返回按钮事件
        $("#returnProfessionListBtn").on("click", function(e) {
            loadMainContent(ctx + "/profession/list");
        });


        $("#saveProfessionBtn").on("click", function(e) {
            // 校验不通过不做处理
            if (!$('#professionForm').isValid()) {
                return false;
            }
            if($('#name')==""||$('#name')==null){
                alert("名称不能为空！")
                return false;
            }
            startLoading();

            $("#professionForm").ajaxSubmit({
                url: ctx + "/profession/save",
                dataType: "json",
                data: {},
                success: function (result) {
                    console.log(result);
                    if (result.status == 0) {
                        loadMainContent(ctx + "/profession/list");
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
