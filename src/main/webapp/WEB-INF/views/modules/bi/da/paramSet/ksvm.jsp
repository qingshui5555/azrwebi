<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>

<div class="row" style="margin-bottom: 10px;">
	<div class="col-xs-2">
		<label><spring:message code='da.paramset.default.datamodel'/></label>
	</div>
	<div class="col-xs-10">
		<select id="model_meta_sel" >
			<c:forEach items="${modelList }" var="model">
				<option value="${model.id }">${model.modelName }</option>
			</c:forEach>
		</select>
	</div>
</div>
<div class="row" style="margin-bottom: 10px;">
	<div class="col-xs-2">
		<label><spring:message code='da.paramset.formulaparamter'/></label>
	</div>
	<div class="col-xs-10" id="model_column_param_div">
	</div>
</div>
<div class="row" style="margin-bottom: 10px;">
	<div class="col-xs-2">
		<label><spring:message code='da.paramset.formularesult'/></label>
	</div>
	<div class="col-xs-10" id="model_column_result_div">
		
	</div>
</div>
<div class="row"  style="margin-bottom: 10px;">
	<div class="col-xs-12">
		<button type="button" class="btn btn-purple btn-sm" id="model_btn_add_param">Add Param</button>
		<button type="button" class="btn btn-purple btn-sm" id="model_btn_clear">Clear</button>
	</div>
</div>

<script>

var $select;

function refushModelColumnInfo(type){
	var chosenModelMetaId = $("#model_meta_sel").val();
	$.post("${ctxbi}/da/paramset/loadModelColumn",
			{modelMetaId:chosenModelMetaId},
			function(data){
				$select = $("<select name='model_column_sels' data-placeholder='Choose a Param...' ></select>");
				$.each(data,function(i,n){
					//var opt = "<option value='"+n.id+"' data-type='"+n.modelColumnType+"' data-columnName='"+n.modelColumnName+"'>"+n.modelColumnNameLab+"("+n.modelColumnUnit+")</option>";
					var opt = "";
					if(n.modelColumnUnit){
						opt = "<option value='"+n.id+"' data-type='"+n.modelColumnType+"' data-columnName='"+n.modelColumnName+"'>"+n.modelColumnNameLab+"("+n.modelColumnUnit+")</option>";
					}else{
						opt = "<option value='"+n.id+"' data-type='"+n.modelColumnType+"' data-columnName='"+n.modelColumnName+"'>"+n.modelColumnNameLab+"</option>";
					}
					$select.append(opt);
				});
				
				refushParam();
				$("#model_column_result_div").empty();
				$("#model_column_result_div").append($select.clone());
				
				if(type=='edit'){
					reflushTestFormulaParameter();
				}
			},
			"json");
}

$(function(){
	$("#model_meta_sel").on("change",function(){
		refushModelColumnInfo();
	})
	refushModelColumnInfo();
	
	$("#model_btn_add_param").click(function(){
		addParam();
	});
	
	$("#model_btn_clear").click(function(){
		refushParam();
	});
});

function addParam(){
	var selectLength = $("#model_column_param_div").find("select").length;
	if(!selectLength){
		$("#model_column_param_div").append($select.clone());
	}else{
		$("#model_column_param_div").append("+").append($select.clone());
	}
	
}

function addResult(){
	$("#model_column_div").append("=").append($select.clone());
}

function refushParam(){
	$("#model_column_param_div").empty();
	$("#model_column_param_div").append($select.clone());
}

/**
 * 以下方法很重要，每个页面都需要有该方法，告知父页面用户到底选了那些column id
 */
function getChosenColumn(){
	var cloumnIds = [];
	$("#model_column_param_div").find("select").each(function(){
		cloumnIds.push($(this).val());
	});
	
	var result = $("#model_column_result_div").find("select").val();
	cloumnIds.push(result);
	return cloumnIds;
}
function checkColumnsCount(){
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