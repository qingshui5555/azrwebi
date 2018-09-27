<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>
<div class="row">
	<div class="col-xs-3" id="results_div">
		<label><spring:message code='da.index.testname'/></label>
	</div>
	<div class="col-xs-9" id="results_div">
		<span>${dfi.testName }</span>
	</div>
</div>
<div class="row" style="margin-top: 5px;">
	<div class="col-xs-3" id="results_div">
		<label><spring:message code='da.index.title'/></label>
	</div>
	<div class="col-xs-9">
		<span>${daView.title }</span>
		<input type="hidden" id="daview_edit_modal_daview_id_hidden" value="">
		<input type="hidden" id="daview_edit_modal_tabview_id_hidden" value="${tabViewId }">
		<input type="hidden" id="daview_edit_modal_test_name_hidden" value="">
	</div>
</div>
<div class="row" style="margin-top: 5px;">
	<div class="col-xs-3" id="results_div">
		<label><spring:message code='da.index.desc'/></label>
	</div>
	<div class="col-xs-9">
		${daView.remarks }
	</div>
</div>
<div class="row" style="margin-top: 5px;">
	<div class="col-xs-12">
		<label><spring:message code='da.index.testextendattset'/></label>
	</div>
</div>
<div class="row" style="margin-top: 5px;" id="daview_edit_extend_att_div">
	<div class="col-xs-12">
		
	</div>
</div>
<script>
$(function(){
	var daViewId="${daView.id}";
	//$("#daview_edit_extend_att_div").load("${ctxbi}/da/extendAtt",{testName:"${dfi.testName }",daViewId:daViewId});
	//reRunTestName,extAtt为dashboard中的invokeParams值
	$("#daview_edit_extend_att_div").load("${ctxbi}/da/extendAtt",{
		testName:"${dfi.testName }",daViewId:daViewId, 
		"reRunTestName" : invokeParams.testName, "extAtt" : invokeParams.extAtt
		});
	console.log("daViewEdit extAtt:"+invokeParams.extAtt);
	$("#daview_edit_modal_daview_id_hidden").val(daViewId);
	$("#daview_edit_modal_test_name_hidden").val("${dfi.testName }");
});
</script>