<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>

<div class="row">
	<div class="col-xs-12" id="cohorts_chosen_div">
		<select id="cohorts_chosen_sel" multiple='' class='chosen-select' style="width: 60%;" 
		data-placeholder="<spring:message code="dashboard.filter.search.select.cohort"/>">
			<c:forEach items="${cohortList }" var="cohort">
				<option  value="${cohort.id }" data-type="${cohort.groupType }">${cohort.groupName }(${cohort.patinetCount })</option>
			</c:forEach>
		</select>
	</div>
</div>

<script>
var cohorts_chosen_sel;
var cohorts_chosen=[];
$(function(){
	cohorts_chosen_sel = $("#cohorts_chosen_sel").chosen();
});

/*
 * 很重要 返回界面选中cohorts信息
 */
function getCohortsChosenInfo(){
	cohorts_chosen=[];
	var ids = cohorts_chosen_sel.val();
	//获取cohortIds逻辑修改
	//var $options = $("#cohorts_chosen_sel>option[selected='selected']");
	var cohorts_chosen_index=[];
	var searchChoiceCloseArray = $("#cohorts_chosen_sel_chosen").find(".search-choice-close");
	for(var i=0;i<searchChoiceCloseArray.length;i++){
		var searchChoiceClose = searchChoiceCloseArray[i];
		var idx = $(searchChoiceClose).attr("data-option-array-index");
		var selectedOption = $("#cohorts_chosen_sel>option").get(idx);
		var chosen = {};
		chosen.id = $(selectedOption).val();
		chosen.data_type = $(selectedOption).attr("data-type");
		cohorts_chosen.push(chosen);
	}
	
	/* $.each(ids,function(i,n){
		var chosen = {};
		chosen.id = n;
		//chosen.data_type = $("option[value='"+n+"']").attr("data-type");
		chosen.data_type = $("#cohorts_chosen_sel>option[value='"+n+"']").attr("data-type");
		cohorts_chosen.push(chosen);
	}); */
	console.log(cohorts_chosen);
	return cohorts_chosen;
}
function checkCohortsChosenInfo(){
	
	var ids = cohorts_chosen_sel.val();
	console.log("cohorts:");
	console.log(ids);
	if(ids==undefined || ids.length==0){
		return "<spring:message code='da.cohorts.pleasechooseacohortatleast'/>";
	}
	 return true;
}

</script>