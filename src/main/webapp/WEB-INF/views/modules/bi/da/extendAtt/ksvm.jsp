<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>

<table class="whiteTable">
	<tr>
		<th>Kernel</th>
		<td>
			<select id="extendAtt_ksvm_kernel">
				<option value="rbfdot">rbfdot</option>
				<option value="polydot">polydot</option>
				<option value="vanilladot">vanilladot</option>
				<option value="tanhdot">tanhdot</option>
				<option value="laplacedot">laplacedot</option>
				<option value="besseldot">besseldot</option>
				<option value="anovadot">anovadot</option>
				<option value="splinedot">splinedot</option>
				<option value="stringdot">stringdot</option>
			</select>
		</td>
		<th>Kpar</th>
		<td>
			<select id="extendAtt_ksvm_kparKey">
				
			</select>
			&nbsp;&nbsp;=&nbsp;&nbsp;
			<input type="text" id="extendAtt_ksvm_kparValue" value="0.05">
		</td>
	</tr>
	<tr>
		<th>C</th>
		<td>
			<input type="text" id="extendAtt_ksvm_c" value="5">
		</td>
		<th>Cross</th>
		<td>
			<input type="text" id="extendAtt_ksvm_cross" value="3">
		</td>
	</tr>
</table>

<!-- <div class="row">
	<div class="col-xs-12">
	<div class="col-xs-1" style="margin-top: 5px;">
		Kernel
	</div>
	<div class="col-xs-2">
		<select id="extendAtt_ksvm_kernel">
			<option value="rbfdot">rbfdot</option>
			<option value="polydot">polydot</option>
			<option value="vanilladot">vanilladot</option>
			<option value="tanhdot">tanhdot</option>
			<option value="laplacedot">laplacedot</option>
			<option value="besseldot">besseldot</option>
			<option value="anovadot">anovadot</option>
			<option value="splinedot">splinedot</option>
			<option value="stringdot">stringdot</option>
		</select>
	</div>
	<div class="col-xs-1" style="margin-top: 5px;">
		Kpar
	</div>
	<div class="col-xs-2">
		<select id="extendAtt_ksvm_kparKey">
				
		</select>
		&nbsp;&nbsp;=&nbsp;&nbsp;
		<input type="text" id="extendAtt_ksvm_kparValue" value="0.05">
	</div>
	<div class="col-xs-1" style="margin-top: 5px;">
		C
	</div>
	<div class="col-xs-2">
		<input type="text" id="extendAtt_ksvm_c" value="5">
	</div>
	<div class="col-xs-1" style="margin-top: 5px;">
		Cross
	</div>
	<div class="col-xs-2">
		<input type="text" id="extendAtt_ksvm_cross" value="3">
	</div>
	</div>
</div> -->

<script>

<c:if test="${not empty dfi and not empty daView}">
//如果数据模型内包含已经设置的内容，则将其赋值到相应控件上
$("#extendAtt_ksvm_kernel").val("${dfi.extAtt.threshold}");
$("#extendAtt_ksvm_kparKey").val("${dfi.extAtt.threshold}");
$("#extendAtt_ksvm_kparValue").val("${dfi.extAtt.threshold}");
$("#extendAtt_ksvm_c").val("${dfi.extAtt.threshold}");
$("#extendAtt_ksvm_cross").val("${dfi.extAtt.threshold}");
</c:if>


var kernelKparRel = {};
kernelKparRel.rbfdot = ['sigma'];
kernelKparRel.laplacedot = ['sigma'];
kernelKparRel.polydot = ['degree', 'scale', 'offset'];
kernelKparRel.tanhdot = ['scale', 'offset'];
kernelKparRel.besseldot = ['sigma', 'order', 'degree'];
kernelKparRel.anovadot = ['sigma', 'degree'];
kernelKparRel.stringdot = ['length', 'lambda', 'normalized'];

function refulshExtendAtt(){
	var kernel = $("#extendAtt_ksvm_kernel").val();
	var kparKey = $("#extendAtt_ksvm_kparKey").val();
	var kparValue = $("#extendAtt_ksvm_kparValue").val();
	var C = $("#extendAtt_ksvm_c").val();
	var cross = $("#extendAtt_ksvm_cross").val();
	
	invokeParams.extAtt={};
	invokeParams.extAtt.kernel = kernel;
	invokeParams.extAtt.kparKey = kparKey;
	invokeParams.extAtt.kparValue = kparValue;
	invokeParams.extAtt.C = C;
	invokeParams.extAtt.cross = cross;

}
function checkExtendAtt(){
	var C = $("#extendAtt_ksvm_c").val();

	//参数校验,mu和confLevel必须为数字
	if(!C||isNaN(C)){
		return "threshold <spring:message code='da.extendatt.hclust.mustbeanumber'/>";
	}
	if(c<=0){
		return "centers <spring:message code='da.exception.mustbemorethan' arguments='0'/>";
	}
	
	return true;
}

$(function(){
	var kernel = $("#extendAtt_ksvm_kernel").val();
	var kpar = kernelKparRel[kernel];
	var optionstr = "";
	for(var i=0;i<kpar.length;i++){
		optionstr += "<option value='"+kpar[i]+"'>"+kpar[i]+"</option>";
	}
	$("#extendAtt_ksvm_kparKey").html(optionstr);
})
</script>