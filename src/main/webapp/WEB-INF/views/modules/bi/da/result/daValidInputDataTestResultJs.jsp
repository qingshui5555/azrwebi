<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>

<script>
$(function(){
	validInputDataBtClick_${resultTestRandom }();
});

function validInputDataBtClick_${resultTestRandom } (){
	$("#valid_input_data_bt_${resultTestRandom }").click(function(){
		var params = daValidInputDataInvokeParams_${resultTestRandom }();
		var result = true;
		if(false){
			result = checkAll("dataFrame");
		}
		if(result){
			$("#da_valid_input_data_modal_data_frame").html("<i class='icon-spinner icon-spin blue bigger-150'></i>");
			$("#da_valid_input_data_modal_data_frame").load("${ctxbi}/da/dataframe",params);
			$("#da_valid_input_data_modal").modal();
		}
	});
}

function daValidInputDataInvokeParams_${resultTestRandom }(){
	var invokeParams = {};
	invokeParams.taId='${dfi.taId}';
	invokeParams.testName='${dfi.testName}';
	invokeParams.studyId='${dfi.studyId}';
	invokeParams.modelMetaId='${dfi.modelMeta.id}';
	invokeParams.modelColumnIds = [];
	invokeParams.cohortIds = [];
	invokeParams.groupIds = [];
	invokeParams.extAtt = {};
	<c:forEach items="${dfi.chosenParams }" var="dataModelParamChosen" varStatus="sta">
		invokeParams.modelColumnIds.push('${dataModelParamChosen.column.id }');
	</c:forEach>
	<c:forEach items="${dfi.conhortChosen.cohorts }" var="cohort" varStatus="sta">
		invokeParams.cohortIds.push('${cohort }');
	</c:forEach>
	<c:forEach items="${dfi.conhortChosen.groups }" var="group" varStatus="sta">
		invokeParams.groupIds.push('${group }');
	</c:forEach>
		
	return invokeParams;
}
</script>