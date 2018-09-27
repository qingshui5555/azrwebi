<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>

<div class="row" style="margin-bottom: 10px;">
	<div class="col-xs-1">
		<label><spring:message code='da.paramset.default.datamodel'/></label>
	</div>
	<div class="col-xs-11">
		<select id="model_meta_sel" >
			<c:forEach items="${modelList }" var="model">
				<option value="${model.id }">${model.modelName }</option>
			</c:forEach>
		</select>
	</div>
</div>
<div class="row" style="margin-bottom: 10px;">
	<div class="col-xs-1">
		<label><spring:message code='da.paramset.survival.date'/></label>
	</div>
	<div class="col-xs-11" id="model_column_date_div">
	</div>
</div>
<div class="row">
	<div class="col-xs-1">
		<label><spring:message code='da.paramset.survival.event'/></label>
	</div>
	<div class="col-xs-11" id="model_column_event_div">
	</div>
</div>
<script>
var cloumnIds = [];

function refushModelColumnInfo(type){
	var chosenModelMetaId = $("#model_meta_sel").val();
	$("#model_column_date_div").html("<i class='icon-spinner icon-spin blue bigger-150'></i>");
	$("#model_column_event_div").html("<i class='icon-spinner icon-spin blue bigger-150'></i>");
	
	$.post("${ctxbi}/da/paramset/loadModelColumn",
			{modelMetaId:chosenModelMetaId},
			function(data){
				$("#model_column_date_div").html("<select id='model_column_date_sel' ></select>");
				$("#model_column_event_div").html("<select id='model_column_event_sel' ></select>");
				var date_sel = $("#model_column_date_sel");
				var event_sel = $("#model_column_event_sel");
				if(data!=null && data!=undefined && data.length>0){
					$.each(data,function(i,n){
						//var opt = "<option value='"+n.id+"' data-type='"+n.modelColumnType+"' data-columnName='"+n.modelColumnName+"'>"+n.modelColumnNameLab+"("+n.modelColumnUnit+")</option>";
						var opt = "";
						if(n.modelColumnUnit){
							opt = "<option value='"+n.id+"' data-type='"+n.modelColumnType+"' data-columnName='"+n.modelColumnName+"'>"+n.modelColumnNameLab+"("+n.modelColumnUnit+")</option>";
						}else{
							opt = "<option value='"+n.id+"' data-type='"+n.modelColumnType+"' data-columnName='"+n.modelColumnName+"'>"+n.modelColumnNameLab+"</option>";
						}
						date_sel.append(opt);
						event_sel.append(opt);
					});
					
				}
				
				if(type=='edit'){
					reflushTestFormulaParameter();
				}
			},
			"json");
	
	
// 	var chosenModelMetaId = $("#model_meta_sel").val();
// 	$("#model_column_div").html("<i class='icon-spinner icon-spin blue bigger-150'></i>");

}

$(function(){
	$("#model_meta_sel").on("change",function(){
		refushModelColumnInfo();
	})
	refushModelColumnInfo();
});

/**
 * 以下方法很重要，每个页面都需要有该方法，告知父页面用户到底选了那些column id
 */
function getChosenColumn(){
	cloumnIds=[];
	var date_sel = $("#model_column_date_sel");
	var event_sel = $("#model_column_event_sel");
	cloumnIds[0]=date_sel.val();
	cloumnIds[1]=event_sel.val();
	console.log(cloumnIds);
	return cloumnIds
}
function checkColumnsCount(){
	var date_sel = $("#model_column_date_sel");
	var event_sel = $("#model_column_event_sel");
	var dateColumnName = date_sel.find("option:selected").attr("data-columnName");
	var eventColumnName = event_sel.find("option:selected").attr("data-columnName");
	if(dateColumnName!="time"){
		return "<spring:message code='da.paramset.survival.pleasechoosetherighttimeparameter'/>";
	}
	if(eventColumnName!="event"){
		return "<spring:message code='da.paramset.survival.pleasechoosetherighteventparameter'/>";
	}
	if(date_sel.val()==event_sel.val()){
		return "<spring:message code='da.paramset.survival.youcannotchoosethesameparameter'/>";
	}
	return true;
}
function checkCohortsCount(){
	var cohortCountLimitLeft = ${test.cohortCountLimitLeft};
	var cohortCountLimitRight = ${test.PCohortCountLimitRight};
	
	var cohortsChosen = cohorts_chosen_sel.val();
	if(cohortsChosen==null || cohortsChosen == undefined){
		cohortsChosen = [];
	}
	var chosenCount = cohortsChosen.length;
	console.log(chosenCount);
	if(cohortCountLimitLeft>0 && cohortCountLimitRight>=0){
		//当左右边界相等，并且边界大于1时英文需要显示复数s
		if(cohortCountLimitLeft==cohortCountLimitRight&&cohortCountLimitLeft!=1){
			if(chosenCount<cohortCountLimitLeft || chosenCount>cohortCountLimitLeft){
				return "<spring:message code='da.paramset.cohort.pleasechoose' arguments='"+cohortCountLimitLeft+",s"+"' argumentSeparator=','/>";
			}
		}else if(cohortCountLimitLeft==cohortCountLimitRight&&cohortCountLimitLeft==1){
			if(chosenCount<cohortCountLimitLeft || chosenCount>cohortCountLimitLeft){
				return "<spring:message code='da.paramset.cohort.pleasechoose' arguments='"+cohortCountLimitLeft+","+"' argumentSeparator=','/>";
			}
		}else{
			if(chosenCount<cohortCountLimitLeft || chosenCount>cohortCountLimitRight){
				//return "只能选择"+cohortCountLimitLeft+"至"+cohortCountLimitRight+"个参数！";
				return "<spring:message code='da.paramset.cohort.pleasechoosefromto' arguments='"+cohortCountLimitLeft+","+cohortCountLimitRight+"' argumentSeparator=','/>";
			}
		}
	}else if(cohortCountLimitLeft<0 && cohortCountLimitRight>0){
		if(chosenCount>cohortCountLimitRight){
			//return "至多只能选择"+cohortCountLimitRight+"个参数！";
			return "<spring:message code='da.paramset.cohort.pleasechooseatmost' arguments='"+cohortCountLimitRight+",s"+"' argumentSeparator=','/>";
		}
	}else if(cohortCountLimitLeft>0 && cohortCountLimitRight<0){
		if(chosenCount<cohortCountLimitLeft){
			//return "至少需要选择"+cohortCountLimitLeft+"个参数！";
			return "<spring:message code='da.paramset.cohort.pleasechooseatleast' arguments='"+cohortCountLimitLeft+",s"+"' argumentSeparator=','/>";
		}
	}
	return true;
}
function getFormula(){
	var formula = "";
	return formula;
}
function getFormulaSymbol(){
	var formulaSymbol = new Array();
	return formulaSymbol;
}
</script>