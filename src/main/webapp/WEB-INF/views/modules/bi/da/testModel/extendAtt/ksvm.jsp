<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>

<table>
	<tr>
		<td style="width: 50px;">Kernel</td>
		<td style="width: 150px;">
			<select id="extendAtt_ksvm_kernel" class="form-control">
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
		<td style="width: 50px;">Kpar</td>
		<td>
			<select id="extendAtt_ksvm_kparKey">
				
			</select>
			&nbsp;&nbsp;=&nbsp;&nbsp;
			<input type="text" id="extendAtt_ksvm_kparValue" value="0.05" style="width: 100px;">
		</td>
	</tr>
	<tr>
		<td style="width: 50px;">C</td>
		<td style="width: 150px;">
			<input type="text" id="extendAtt_ksvm_c" value="5" style="width: 100px;">
		</td>
		<td style="width: 50px;">Cross</td>
		<td>
			<input type="text" id="extendAtt_ksvm_cross" value="3" style="width: 100px;">
		</td>
	</tr>
</table>

<script>

<c:if test="${not empty dfi and not empty daView}">
//如果数据模型内包含已经设置的内容，则将其赋值到相应控件上
$("#extendAtt_ksvm_kernel").val("${dfi.extAtt.kernel}");
$("#extendAtt_ksvm_kparKey").val("${dfi.extAtt.kparKey}");
$("#extendAtt_ksvm_kparValue").val("${dfi.extAtt.kparValue}");
$("#extendAtt_ksvm_c").val("${dfi.extAtt.c}");
$("#extendAtt_ksvm_cross").val("${dfi.extAtt.cross}");
</c:if>


var kernelKparRel = {};
kernelKparRel.rbfdot = ['sigma'];
kernelKparRel.laplacedot = ['sigma'];
kernelKparRel.polydot = ['degree', 'scale', 'offset'];
kernelKparRel.tanhdot = ['scale', 'offset'];
kernelKparRel.besseldot = ['sigma', 'order', 'degree'];
kernelKparRel.anovadot = ['sigma', 'degree'];
kernelKparRel.stringdot = ['length', 'lambda', 'normalized'];

function refulshTestModelExtendAtt(){
	var kernel = $("#extendAtt_ksvm_kernel").val();
	var kparKey = $("#extendAtt_ksvm_kparKey").val();
	var kparValue = $("#extendAtt_ksvm_kparValue").val();
	var C = $("#extendAtt_ksvm_c").val();
	var cross = $("#extendAtt_ksvm_cross").val();
	
	invokeParams.tmExtAtt={};
	invokeParams.tmExtAtt.kernel = kernel;
	invokeParams.tmExtAtt.kparKey = kparKey;
	invokeParams.tmExtAtt.kparValue = kparValue;
	invokeParams.tmExtAtt.C = C;
	invokeParams.tmExtAtt.cross = cross;

}
function checkTestModelExtendAtt(){
	var C = $("#extendAtt_ksvm_c").val();

	//参数校验,mu和confLevel必须为数字
	if(!C||isNaN(C)){
		return "threshold <spring:message code='da.extendatt.hclust.mustbeanumber'/>";
	}
	if(C<=0){
		return "centers <spring:message code='da.exception.mustbemorethan' arguments='0'/>";
	}
	
	return true;
}

$(function(){
	//初始化kpar选项框
	reflushKpar();
	
	$("#extendAtt_ksvm_kernel").change(function(){
		reflushKpar();
	});
});

function reflushKpar(){
	var kernel = $("#extendAtt_ksvm_kernel").val();
	var kpar = kernelKparRel[kernel];
	var optionstr = "";
	for(var i=0;i<kpar.length;i++){
		optionstr += "<option value='"+kpar[i]+"'>"+kpar[i]+"</option>";
	}
	$("#extendAtt_ksvm_kparKey").html(optionstr);
}
</script>