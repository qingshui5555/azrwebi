<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>

<table class="whiteTable">
	<tr>
		<th>Method</th>
		<td>
			<select id="extendAtt_cortest_method" >
				<option value="pearson">pearson</option>
				<option value="kendall">kendall</option>
				<option value="spearman">spearman</option>
			</select>
		</td>
		<th>Alternative</th>
		<td>
			<select id="extendAtt_cortest_alternative" >
				<option value="two.sided">two.sided</option>
				<option value="less">less</option>
				<option value="greater">greater</option>
			</select>
		</td>
	</tr>
	<tr>
		<th>Confidence Level</th>
		<td>
			<input type="text" id="extendAtt_cortest_confLevel" value="0.95">
		</td>
		
	</tr>
</table>

<%-- <div class="row">
	<div class="col-xs-12">
		<div class="col-xs-1" style="margin-top: 5px;">
			Method
		</div>
		<div class="col-xs-2">
			<select id="extendAtt_cortest_method" class="form-control">
				<option value=""><spring:message code="bi.dataanalysis.ui.pleasechoose"/></option>
				<option value="pearson">pearson</option>
				<option value="kendall">kendall</option>
				<option value="spearman">spearman</option>
			</select>
		</div>
		<div class="col-xs-1" style="margin-top: 5px;">
			Alternative
		</div>
		<div class="col-xs-2">
			<select id="extendAtt_cortest_alternative" class="form-control">
				<option value=""><spring:message code="bi.dataanalysis.ui.pleasechoose"/></option>
				<option value="two.sided">two.sided</option>
				<option value="less">less</option>
				<option value="greater">greater</option>
			</select>
		</div>
		<div class="col-xs-2" style="margin-top: 5px;">
			Confidence Level
		</div>
		<div class="col-xs-2">
			<input type="text" id="extendAtt_cortest_confLevel" value="0.95">
		</div>
	</div>
</div> --%>

<script>

<c:if test="${not empty dfi and not empty daView}">
//如果数据模型内包含已经设置的内容，则将其赋值到相应控件上
$("#extendAtt_cortest_method").val("${dfi.extAtt.method}");
$("#extendAtt_cortest_alternative").val("${dfi.extAtt.alternative}");
$("#extendAtt_cortest_confLevel").val("${dfi.extAtt.confLevel}");
</c:if>




function refulshExtendAtt(){
	var method = $("#extendAtt_cortest_method").find("option:selected").val();
	var alternative = $("#extendAtt_cortest_alternative").find("option:selected").val();
	var confLevel =  $("#extendAtt_cortest_confLevel").val()
	
	invokeParams.extAtt={};
	invokeParams.extAtt.method = method;
	invokeParams.extAtt.alternative = alternative;
	invokeParams.extAtt.confLevel = confLevel;
}
function checkExtendAtt(){
	var confLevel =  $("#extendAtt_ttest_confLevel").val()

	//参数校验,confLevel必须为数字
	var confLevelNum = parseInt(confLevel);
 	if(confLevelNum<0 || confLevelNum >1){
 		return "confLevel <spring:message code='da.extendatt.ttest.mustbefrom1to2'/>";
 	}
	return true;
}
</script>