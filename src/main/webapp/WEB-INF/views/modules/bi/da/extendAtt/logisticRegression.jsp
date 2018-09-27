<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>

<table class="whiteTable">
	<tr>
		<th>Threshold</th>
		<td>
			<input type="text" id="extendAtt_logisticregression_threshold" value="0.5">
		</td>
	</tr>
</table>

<!-- <div class="row">
	<div class="col-xs-12">
	<div class="col-xs-1" style="margin-top: 5px;">
		Threshold
	</div>
	<div class="col-xs-2">
		<input type="text" id="extendAtt_logisticregression_threshold" value="0.5">
	</div>
	</div>
</div> -->

<script>

<c:if test="${not empty dfi and not empty daView}">
//如果数据模型内包含已经设置的内容，则将其赋值到相应控件上
$("#extendAtt_logisticregression_threshold").val("${dfi.extAtt.threshold}");
</c:if>




function refulshExtendAtt(){
	var threshold = $("#extendAtt_logisticregression_threshold").val();
	
	invokeParams.extAtt={};
	invokeParams.extAtt.threshold = threshold;

}
function checkExtendAtt(){
	var threshold = $("#extendAtt_logisticregression_threshold").val();

	//参数校验,mu和confLevel必须为数字
	if(!threshold||isNaN(threshold)){
		return "threshold <spring:message code='da.extendatt.hclust.mustbeanumber'/>";
	}
	
	return true;
}
</script>