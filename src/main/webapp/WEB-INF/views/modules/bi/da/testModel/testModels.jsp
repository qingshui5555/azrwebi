<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>
<c:if test="${test.needTestModel == true}">
	<div class="row">
		<div class="col-xs-2">
			<select class="form-control" id="da_test_model_sel">
			<option value="-1"><spring:message code='da.testmodels.choosemodel'/></option>
			<c:forEach items="${modelList }" var="model">
				<option value="${model.id }">${model.name }</option>
			</c:forEach>
			</select>
		</div>
		<div class="col-xs-1" align="center">
			<h5>OR</h5>
		</div>
		<div class="col-xs-9">
			<h5 class="blue"><a id="da_test_model_save_a" href="javaScript:void('0')"><spring:message code='da.testmodels.newmodel'/></a></h5>
		</div>
	</div>
	
	<!-- model 创建界面 -->
	<div class="modal fade" id="da_test_model_save_modal">
	   <div class="modal-dialog">
	      <div class="modal-content">
	         <div class="modal-header">
	            <button type="button" class="close" 
	               data-dismiss="modal" aria-hidden="true">
	                  &times;
	            </button>
	            <h4 class="modal-title">
					<!-- 新建测试模型 -->
	            	<spring:message code='da.testmodels.newmodel'/>
	            </h4>
	         </div>
	         <div class="modal-body" id="da_test_model_save_model_body_div">
	         	<div class="row">
	         			<h5 class="blue">
	         			<spring:message code='da.testmodels.custom.attributes'/>
	         			</h5>
	         	</div>
         		<div class="row" style="margin-top: 5px;">
	         		<div class="col-xs-3">
<!-- 	         			模型名称 -->
					<spring:message code='da.testmodels.modelname'/>
	         		</div>
	         		<div class="col-xs-6">
	         			<input type="text" class="form-control input-sm" id="da_test_model_save_model_body_div_name_input">
	         		</div>
	         		<div class="col-xs-3">
	         		</div>
         		</div>
         		<div class="row" style="margin-top: 5px;">
	         		<div class="col-xs-3">
<!-- 	         			备注 -->
	         			<spring:message code='da.testmodels.desc'/>
	         		</div>
	         		<div class="col-xs-6">
	         			<textarea class="form-control" rows="3" id="da_test_model_save_model_body_div_remarks_ta"></textarea>
	         		</div>
	         		<div class="col-xs-3">
	         		</div>
         		</div>
	         	<div class="row">
	         			<h5 class="blue">
<!-- 	         			Model Summary -->
	         			<spring:message code='da.testmodels.model.summary'/>
	         			</h5>
	         	</div>
	         	<div class="row">
	         		<div class="col-xs-12" id="da_test_model_save_model_body_result_div">
	         		</div>
	         	</div>
	         </div>
	         <div class="modal-footer">
	            <button type="button" class="btn btn-default" data-dismiss="modal">
					<spring:message code='da.index.close'/>
	            </button>
	            <button type="button" class="btn btn-purple" id="da_test_model_save_modal_save_bt">
	              	 <spring:message code='da.index.save'/>
	            </button>
	         </div>
	      </div><!-- /.modal-content -->
		</div>
	</div>
	
	<!-- model 入参界面 -->
	<div class="modal fade" id="da_test_model_extend_att_modal" tabindex="-2">
	   <div class="modal-dialog">
	      <div class="modal-content">
	         <div class="modal-header">
	            <button type="button" class="close" 
	               data-dismiss="modal" aria-hidden="true">
	                  &times;
	            </button>
	            <h4 class="modal-title">
					确认模型参数
	            </h4>
	         </div>
	         <div class="modal-body" >
	         	<div class="row">
	         		<div class="col-xs-12" id="da_test_model_extend_att_model_body_div">
	         		</div>
	         	</div>
	         </div>
	         <div class="modal-footer">
	            <button type="button" class="btn btn-default" data-dismiss="modal">
					<spring:message code='da.index.close'/>
	            </button>
	            <button type="button" class="btn btn-purple" id="da_test_model_extend_att_model_excute">
	              	 创建模型
	            </button>
	         </div>
	      </div><!-- /.modal-content -->
		</div>
	</div>
	
	
	<script>
		var test_modal;
		
		function openTestModelExtendAtt(){
			$("#da_test_model_extend_att_model_body_div").html("<i class='icon-spinner icon-spin blue bigger-150'></i>");
			test_modal = $("#da_test_model_extend_att_modal").modal();
			$("#da_test_model_extend_att_model_body_div").load("${ctxbi}/da/model/extendAtt/${test.testName}",invokeParams);
		}
		
		function reOpenTestModelExtendAtt(){
			test_modal.modal("toggle");
			test_modal = $("#da_test_model_extend_att_modal").modal();
		}
		
		function goExcuteTestModel(){
			if(test_modal!=undefined){
				test_modal.modal("toggle");
			}
			<c:if test="${test.needTestModelExtendAtt == true}">
			if(checkTestModelExtendAtt!=undefined&&refulshTestModelExtendAtt!=undefined){
				var result = checkTestModelExtendAtt();
				if(result!=true){
					$.simplyToast(result, 'danger');
					return false;
				}
				refulshTestModelExtendAtt();
			}
			</c:if>
			$("#da_test_model_save_model_body_result_div").html("<i class='icon-spinner icon-spin blue bigger-150'></i>");
			test_modal = $("#da_test_model_save_modal").modal();
			$("#da_test_model_save_model_body_result_div").load("${ctxbi}/da/model/excute",invokeParams);
		}
		
		$(function(){
			$("#da_test_model_save_a").on("click",function(){
				var checkResult = checkAll("testModel");
				//var checkResult = true;
				console.log(checkResult);
				if(checkResult){
					console.log(invokeParams);
					<c:if test="${test.needTestModelExtendAtt == true}">
					openTestModelExtendAtt();
					</c:if>
					<c:if test="${test.needTestModelExtendAtt == false}">
					goExcuteTestModel();
					</c:if>
				}
			});
			
			$("#da_test_model_extend_att_model_excute").on("click",function(){
				goExcuteTestModel();
			});
			
			$("#da_test_model_save_modal_save_bt").on("click",function(){
				
				var name = $("#da_test_model_save_model_body_div_name_input").val();
				
				if($.trim(name)==""){
					$.simplyToast("<spring:message code='da.testmodels.alert.modelnameisempty'/>", 'danger');
					return false;
				}
				
				var remarks = $.trim($("#da_test_model_save_model_body_div_remarks_ta").val());
				
				invokeParams.daTestModelName=name;
				invokeParams.daTestModelRemarks=remarks;
				
				console.log(invokeParams);
				
				$.post("${ctxbi}/da/model/save",
						invokeParams,
						function(data){
							console.log(data);
							if(data.isSuccess){
								test_modal.modal("toggle");
								//给一些时间关闭遮罩，再刷新自己所在的容器
								setTimeout(function(){
									reflushTestModelDiv();
								},200);
								
							}else{
								$.simplyToast(data.message, 'danger');
							}
						},
						"json");
				
			});
		});
	</script>
</c:if>

<c:if test="${test.needTestModel == false}">
	<div class="row">
		<div class="col-xs-12">
<!-- 			该测试方法不需要使用测试模型! -->
		<spring:message code='da.testmodels.therearenotestmodelfortheapproach'/>
		</div>
	</div>
</c:if>

<script>
var needTestModel = ${test.needTestModel};
invokeParams.testModelId='';
function refulshTestModel(){
	if(needTestModel){
		var testModel =  $("#da_test_model_sel");
		var testModelId = testModel.val();
		invokeParams.testModelId = testModelId;
	}
}
function checkTestModel(){
	if(needTestModel){
		var testModel =  $("#da_test_model_sel");
		var testModelId = testModel.val();
		if(testModelId=='-1'){
			return "请先选择一个测试模型!";
		}
	}
	return true;
}
</script>