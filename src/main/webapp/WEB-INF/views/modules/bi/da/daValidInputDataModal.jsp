<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>
<!-- 计算结果中查看有效数据弹出界面 -->
<div class="modal fade" id="da_valid_input_data_modal" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">
					<spring:message code='da.index.analysisparamterfromsampledata' />
				</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-xs-12" id="da_valid_input_data_modal_data_frame">
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<spring:message code='da.index.close' />
				</button>
				<%-- <button type="button" class="btn btn-primary" id="da_save_modal_save_bt">
				              	 <spring:message code='da.index.save'/>
				            </button> --%>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>

	<!-- PAGE CONTENT ENDS -->
</div>


