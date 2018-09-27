<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>

<table class="whiteTable">
	<tr>
		<th>Mu</th>
		<td>
			<input type="text" id="extendAtt_wilcox_mu" value="0">
		</td>
		<!-- <th>paired</th>
		<td>
			<input id="extendAtt_wilcox_paired" type="checkbox" class="ace"/><span class='lbl'></span>
			<input type="text" id="extendAtt_ttest_paired">
		</td> -->
	</tr>
	<tr>
		<th>Confidence Level</th>
		<td>
			<input type="text" id="extendAtt_wilcox_confLevel" value="0.95">
		</td>
		<th>Alternative</th>
		<td>
			<select id="extendAtt_wilcox_alternative">
				<option value="two.sided">two.sided</option>
				<option value="less">less</option>
				<option value="greater">greater</option>
			</select>
			<!-- <input type="text" id="extendAtt_ttest_alternative"> -->
		</td>
	</tr>
</table>

<%-- <div class="row">
	<div class="col-xs-12">
		<div class="col-xs-1" style="margin-top: 5px;">
			Mu
		</div>
		<div class="col-xs-2">
			<input type="text" id="extendAtt_wilcox_mu" value="0">
		</div>
		<div class="col-xs-2" style="margin-top: 5px;">
			Confidence Level
		</div>
		<div class="col-xs-2">
			<input type="text" id="extendAtt_wilcox_confLevel" value="0.95">
		</div>
		<div class="col-xs-1" style="margin-top: 5px;">
			Alternative
		</div>
		<div class="col-xs-2">
			<select id="extendAtt_wilcox_alternative" class="form-control">
				<option value=""><spring:message code="bi.dataanalysis.ui.pleasechoose"/></option>
				<option value="two.sided">two.sided</option>
				<option value="less">less</option>
				<option value="greater">greater</option>
			</select>
		</div>
		<!-- <input id="extendAtt_wilcox_paired" type="checkbox" class="ace"/><span class='lbl'></span> -->
	</div>
</div>
 --%>


<script>
<c:if test="${not empty dfi and not empty daView}">
//如果数据模型内包含已经设置的内容，则将其赋值到相应控件上
$("#extendAtt_wilcox_mu").val("${dfi.extAtt.mu}");
$("#extendAtt_wilcox_confLevel").val("${dfi.extAtt.confLevel}");
$("#extendAtt_wilcox_alternative").val("${dfi.extAtt.alternative}");
</c:if>


function refulshExtendAtt(){
	var mu = $("#extendAtt_wilcox_mu").val();
	var paired = $("#extendAtt_wilcox_paired").prop("checked");
	var confLevel =  $("#extendAtt_wilcox_confLevel").val()
	var alternative = $("#extendAtt_wilcox_alternative").find("option:selected").val();
	
	invokeParams.extAtt={};
	invokeParams.extAtt.alternative = alternative;
	invokeParams.extAtt.mu = mu;
	if(paired){
		invokeParams.extAtt.paired = "TRUE";
	}else{
		invokeParams.extAtt.paired = "FALSE";
	}
	invokeParams.extAtt.confLevel = confLevel;
}
function checkExtendAtt(){
	var mu = $("#extendAtt_wilcox_mu").val();
	var confLevel =  $("#extendAtt_wilcox_confLevel").val()

	//参数校验,mu和confLevel必须为数字
	if(!mu||isNaN(mu)){
		return "mu <spring:message code='da.extendatt.hclust.mustbeanumber'/>";
	}else if(!confLevel||isNaN(confLevel)){
		return "confLevel<spring:message code='da.extendatt.hclust.mustbeanumber'/>";
	}
	return true;
}
</script>