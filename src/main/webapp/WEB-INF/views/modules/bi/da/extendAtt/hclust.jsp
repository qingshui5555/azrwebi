<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>

<table class="whiteTable">
	<tr>
		<th>Distance Method</th>
		<td>
			<select id="extendAtt_hclust_distanceMethod">
				<option value="euclidean">euclidean</option>
				<option value="maximum">maximum</option>
				<option value="manhattan">manhattan</option>
				<option value="canberra">canberra</option>
				<option value="binary">binary</option>
				<option value="minkowski">minkowski</option>
			</select>
		</td>
		<th>Cluster Method</th>
		<td>
			<select id="extendAtt_hclust_hclusterMethod">
			<option value="ward.D">ward.D</option>
			<option value="ward.D2">ward.D2</option>
			<option value="single">single</option>
			<option value="complete">complete</option>
			<option value="average">average</option>
			<option value="mcquitty">mcquitty</option>
			<option value="median">median</option>
			<option value="centroid">centroid</option>
			</select>
		</td>
	</tr>
	<tr>
		<th>Cluster Count</th>
		<td>
			<input type="text" id="extendAtt_hclust_k" value=1>
		</td>
	</tr>
</table>

<!-- <div class="row">
	<div class="col-xs-12">
	<div class="col-xs-2" style="margin-top: 5px;">
		Distance Method
	</div>
	<div class="col-xs-2">
		<select id="extendAtt_hclust_distanceMethod" class="form-control">
			<option value="euclidean">euclidean</option>
			<option value="maximum">maximum</option>
			<option value="manhattan">manhattan</option>
			<option value="canberra">canberra</option>
			<option value="binary">binary</option>
			<option value="minkowski">minkowski</option>
		</select>
	</div>
	<div class="col-xs-2" style="margin-top: 5px;">
		Cluster Method
	</div>
	<div class="col-xs-2">
		<select id="extendAtt_hclust_hclusterMethod" class="form-control">
			<option value="ward.D">ward.D</option>
			<option value="ward.D2">ward.D2</option>
			<option value="single">single</option>
			<option value="complete">complete</option>
			<option value="average">average</option>
			<option value="mcquitty">mcquitty</option>
			<option value="median">median</option>
			<option value="centroid">centroid</option>
		</select>
	</div>
	<div class="col-xs-2" style="margin-top: 5px;">
		Cluster Count
	</div>
	<div class="col-xs-2">
		<input type="text" id="extendAtt_hclust_k">
	</div>
	</div>
</div> -->


<script>
<c:if test="${not empty dfi and not empty daView}">
//如果数据模型内包含已经设置的内容，则将其赋值到相应控件上
$("#extendAtt_hclust_distanceMethod").val("${dfi.extAtt.distanceMethod}");
$("#extendAtt_hclust_hclusterMethod").val("${dfi.extAtt.hclusterMethod}");
$("#extendAtt_hclust_k").val("${dfi.extAtt.k}");
</c:if>

function refulshExtendAtt(){
	var distanceMethod = $("#extendAtt_hclust_distanceMethod").find("option:selected").val();
	var hclusterMethod = $("#extendAtt_hclust_hclusterMethod").find("option:selected").val();
	var k = $("#extendAtt_hclust_k").val();
	
	invokeParams.extAtt={};
	invokeParams.extAtt.distanceMethod = distanceMethod;
	invokeParams.extAtt.hclusterMethod = hclusterMethod;
	invokeParams.extAtt.k = k;
}
function checkExtendAtt(){
	var k = $("#extendAtt_hclust_k").val();

	//参数校验,mu和confLevel必须为数字
	if(!k||isNaN(k)){
		return "Cluster Count <spring:message code='da.extendatt.hclust.mustbeanumber'/>";
	}
	return true;
}
</script>