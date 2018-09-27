<%@page import="org.apache.commons.lang.math.RandomUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>
<%!
int randomNum = RandomUtils.nextInt();
%>
<div class="row" style="margin-top: 5px;">
	<div class="col-xs-12">
		<h4 class="red"><spring:message code="${daEx.messageI18nCode }" /></h4>
	</div>
</div>
<div class="row" style="margin-top: 5px;">
	<div class="col-xs-12">
		<label class="blue" id="da_test_error_detail_show_bt" data-random-num="<%=randomNum%>">
			<span id="da_test_error_detail_show_lb_1_<%=randomNum%>"><spring:message code="da.test.invoke.exception.default.detail.show" /></span>
			<span id="da_test_error_detail_show_lb_2_<%=randomNum%>" style="display: none;"><spring:message code="da.test.invoke.exception.default.detail.close" /></span>
		</label>
	</div>
</div>
<div class="row" id="da_test_error_detail_<%=randomNum%>" style="display: none;">
	<div class="col-xs-12">
	<label>${fn:substring(daEx.message,fn:indexOf(daEx.message,":")+1,-1) }</label></br>
	<%-- <label>${daEx.message }</label></br>
	<c:forEach items="${daEx.stackTrace }" var="stackTrace">
	<label>${stackTrace }</label></br>
	</c:forEach> --%>
	</div>
</div>
<!-- 测试 错误的标记，让父页面知道test出错误了-->
<div id="testErrorMarker"></div>
<script>
$(function(){
	$("#da_test_error_detail_show_bt").off();
	$("#da_test_error_detail_show_bt").on("click",function(){
		var randomNum = $(this).attr("data-random-num");
		$("#da_test_error_detail_"+randomNum).slideToggle();
		$("#da_test_error_detail_show_lb_1_"+randomNum).toggle();
		$("#da_test_error_detail_show_lb_2_"+randomNum).toggle();
	});
});
</script>