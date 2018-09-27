<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>

<br>
<table class="whiteTable">
	<tr>
		<th>Formula</th>
		<td>
		<div id="extendAtt_anova_formuladiv"></div>
			<input type="text" id="extendAtt_kmeans_centers" value="2">
		</td>
	</tr>
</table>





<script>
function refulshExtendAtt(){
	var centers = $("#extendAtt_kmeans_centers").val();
	
	invokeParams.extAtt={};
	invokeParams.extAtt.centers = centers;
}
function checkExtendAtt(){
	var centers = $("#extendAtt_kmeans_centers").val();
	//参数校验,mu和confLevel必须为数字
	if(!centers||isNaN(centers)){
		return false;
	}
	return true;
}

</script>