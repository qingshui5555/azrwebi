<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>

<div class="row">
	<div class="col-xs-1" style="margin-right: 0px;padding-right: 0px;">
		    <label class="form-label" style="line-height: 28px;"><spring:message code='da.paramset.default.datamodel'/>
			</label>
	</div>
	<div class="col-xs-2">
			<select id="model_meta_sel" style="width: 100%" class="form-control">
				<c:forEach items="${modelList }" var="model">
					<option value="${model.id }">${model.modelName }</option>
				</c:forEach>
			</select>
	</div>
	<div class="col-xs-9" id="model_column_div">
	</div>
</div>

<script>

var dpc_chosen;

function refushModelColumnInfo(type){
	var chosenModelMetaId = $("#model_meta_sel").val();
	if(chosenModelMetaId){
		$("#model_column_div").html("<i class='icon-spinner icon-spin blue bigger-150'></i>");
		$.post("${ctxbi}/da/paramset/loadModelColumn",
				{modelMetaId:chosenModelMetaId},
				function(data){
					$("#model_column_div").html("<select name='model_column_sels' multiple='' class='width-50 chosen-select' id='model_column_sel' data-placeholder='<spring:message code='da.paramset.default.chooseaparameter'/>'></select>");
					var sel = $("#model_column_sel");
					sel.html("");
					if(data!=null && data!=undefined && data.length>0){
						$.each(data,function(i,n){
							var opt = "";
							if(n.modelColumnUnit){
								opt = "<option value='"+n.id+"' data-type='"+n.modelColumnType+"' data-columnName='"+n.modelColumnName+"'>"+n.modelColumnNameLab+"("+n.modelColumnUnit+")</option>";
							}else{
								opt = "<option value='"+n.id+"' data-type='"+n.modelColumnType+"' data-columnName='"+n.modelColumnName+"'>"+n.modelColumnNameLab+"</option>";
							}
							
							sel.append(opt);
						});
						
					}
					dpc_chosen = sel.chosen();
					//如果是edit页面请求则初始化预先选中的指标
					if(type=='edit'){
						$("#model_column_sel").val(invokeParams.modelColumnIds);
						$("#model_column_sel").trigger("chosen:updated");
					}
				},
				"json");
	}
	
}

$(function(){
	$("#model_meta_sel").on("change",function(){
		refushModelColumnInfo();
	});
	refushModelColumnInfo();
	console.log("PCohortCountLimitRight:"+ ${test.PCohortCountLimitRight});
});

/**
 * 以下方法很重要，每个页面都需要有该方法，告知父页面用户到底选了那些column id
 */
function getChosenColumn(){
	console.log(dpc_chosen.val());
	return dpc_chosen.val();
}
function checkColumnsCount(){
	var countLimitLeft = ${test.paramCountLimitLeft};
	var countLimitRight = ${test.paramCountLimitRight};
	var chosen = dpc_chosen.val();
	if(chosen==null || chosen == undefined){
		chosen = [];
	}
	var chosenCount = chosen.length;
	console.log(chosen);
	if(countLimitLeft>0 && countLimitRight>=0){
		//当左右边界相等，并且边界大于1时英文需要显示复数s
		if(countLimitLeft==countLimitRight&&countLimitLeft!=1){
			if(chosenCount<countLimitLeft || chosenCount>countLimitLeft){
				return "<spring:message code='da.paramset.default.pleasechoose' arguments='"+countLimitLeft+",s"+"' argumentSeparator=','/>";
			}
		}else if(countLimitLeft==countLimitRight&&countLimitLeft==1){
			if(chosenCount<countLimitLeft || chosenCount>countLimitLeft){
				return "<spring:message code='da.paramset.default.pleasechoose' arguments='"+countLimitLeft+","+"' argumentSeparator=','/>";
			}
		}else{
			if(chosenCount<countLimitLeft || chosenCount>countLimitRight){
				//return "只能选择"+countLimitLeft+"至"+countLimitRight+"个参数！";
				return "<spring:message code='da.paramset.default.pleasechoosefromto' arguments='"+countLimitLeft+","+countLimitRight+"' argumentSeparator=','/>";
			}
		}
	}else if(countLimitLeft<0 && countLimitRight>0){
		if(chosenCount>countLimitRight){
			//return "至多只能选择"+countLimitRight+"个参数！";
			return "<spring:message code='da.paramset.default.pleasechooseatmost' arguments='"+countLimitRight+",s"+"' argumentSeparator=','/>";
		}
	}else if(countLimitLeft>0 && countLimitRight<0){
		if(chosenCount<countLimitLeft){
			//return "至少需要选择"+countLimitLeft+"个参数！";
			return "<spring:message code='da.paramset.default.pleasechooseatleast' arguments='"+countLimitLeft+",s"+"' argumentSeparator=','/>";
		}
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