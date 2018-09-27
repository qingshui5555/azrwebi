<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>

<table class="whiteTable">
	<tr>
		<th>Numbers of Cluster Center</th>
		<td>
			<input type="text" id="extendAtt_kmeans_centers" value="2">
		</td>
	</tr>
</table>

<!-- <div class="row">
	<div class="col-xs-12">
	<div class="col-xs-2" style="margin-top: 5px;">
		Numbers of Cluster Center
	</div>
	<div class="col-xs-2">
		<input type="text" id="extendAtt_kmeans_centers" value="2">
	</div>
	</div>
</div> -->


<script>
<c:if test="${not empty dfi and not empty daView}">
//如果数据模型内包含已经设置的内容，则将其赋值到相应控件上
$("#extendAtt_kmeans_centers").val("${dfi.extAtt.centers}");
</c:if>

function refulshExtendAtt(){
	var centers = $("#extendAtt_kmeans_centers").val();
	
	invokeParams.extAtt={};
	invokeParams.extAtt.centers = centers;
}
function checkExtendAtt(){
	var centers = $("#extendAtt_kmeans_centers").val();
	//参数校验,mu和confLevel必须为数字
	if(!centers||isNaN(centers)){
		return "centers <spring:message code='da.extendatt.hclust.mustbeanumber'/>";
	}
	if(centers<=0){
		return "centers <spring:message code='da.exception.mustbemorethan' arguments='1'/>";
	}
	return true;
}
</script>