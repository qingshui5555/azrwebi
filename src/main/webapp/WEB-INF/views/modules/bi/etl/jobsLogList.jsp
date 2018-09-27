<%@page import="com.movit.rwe.modules.bi.base.entity.enums.ViewTypeEnum"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" buffer="1024kb"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="layout.head.analytics" /></title>
<c:import url="/bi/layout/resource" />
<style type="text/css">
.highlight {
	background-color: #FDECA8 !important;
}
</style>
</head>
<body>
	<%-- <c:import url="/bi/layout/foot" /> --%>

	<c:import url="/bi/layout/head" />
	<div class="main-container" id="main-container">
		<script type="text/javascript">
			try {
				ace.settings.check('main-container', 'fixed')
			} catch (e) {
			}
		</script>
		<div class="main-container-inner">
			<a class="menu-toggler" id="menu-toggler" href="#"> <span
				class="menu-text"></span>
			</a>
			<c:import url="/bi/layout/sidebar">
				<c:param name="dataMenuActive" value="29" />
			</c:import>
			<div class="main-content">

				<div class="breadcrumbs" id="breadcrumbs">
					<ul class="breadcrumb">
						<li><i class="icon-home home-icon"></i><a href="#"><spring:message
									code="layout.menu.name.system.configuration" /></a></li>
						<li class="active"><spring:message code="layout.menu.name.etl.config"/></li>
					</ul>
					<!-- .breadcrumb -->
				</div>

				<div class="page-content">
				<div class="row">
					<div class="col-xs-12">
						<table id="data_frame_table" class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th style="width:20%"><spring:message code="etl.log.jobName"/></th>
									<th style="width:16%"><spring:message code="etl.log.status"/></th>
									<th style="width:16%"><spring:message code="etl.log.linesWritten"/></th>
									<th style="width:16%"><spring:message code="etl.log.startDate"/></th>
									<th style="width:16%"><spring:message code="etl.log.endDate"/></th>
									<th style="width:16%"><spring:message code="etl.log.errordetail"/></th>
								</tr>
							</thead>
							<tbody>
							<c:forEach items="${jobsLogList }" var="jobsLog" varStatus="sta">
								<tr>
									<td>${jobsLog.jobName }</td>
									<td>
									<c:choose>
										<c:when test="${jobsLog.status=='0' }">
											<spring:message code="status.fail"/>
										</c:when>
										<c:otherwise>
											<spring:message code="status.success"/>
										</c:otherwise>
									</c:choose>
									</td>
									<td>${jobsLog.linesWritten }</td>
									<td><fmt:formatDate value="${jobsLog.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
									<td><fmt:formatDate value="${jobsLog.endDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
									<td>
									<c:if test="${jobsLog.status == '0'}">
										<div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
											<input name="ipt_errors" value="${jobsLog.errors }" type="hidden"/>
						                	<a name="a_jobslog_detail" class="green" title="<spring:message code="etl.log.errordetail"/>" onclick="showJobslogModal(this)">
						                		<i class="icon-comment bigger-130"></i>
						                	</a>
						                </div>
									</c:if>
									</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				</div>
			</div>
			<!-- /.main-content -->

		</div>
		<!-- /.main-container-inner -->

	</div>
	<!-- /.main-container -->

</body>

<div class="modal fade" id="jobslog_modal" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">
					<spring:message code='etl.log.errordetail' />
				</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-xs-12" id="div_jobslog_detail">
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

<script>
$(function(){
	$('#data_frame_table').dataTable({
		"oLanguage": dataTableLanguage,
		"aaSorting": [[3,'desc']]
		//"bSort" : false
	});
});

function showJobslogModal(obj){
	var errors = $(obj).parent().find("[name='ipt_errors']").val();
	$("#div_jobslog_detail").html(errors);
	$("#jobslog_modal").modal();
}
</script>
</html>