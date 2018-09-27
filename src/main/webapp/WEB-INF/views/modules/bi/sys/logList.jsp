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
		table td {
			word-break: break-all;
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
				<c:param name="dataMenuActive"
					value="8236af6a6f41488cb0d1c13f1eaabd7d" />
			</c:import>
			<div class="main-content">
				<div class="breadcrumbs" id="breadcrumbs">
					<ul class="breadcrumb">
						<li><i class="icon-home home-icon"></i><a href="#"><spring:message
									code="layout.menu.name.system.configuration" /></a></li>
						<li class="active"><spring:message code="accesslog.name" /></li>
					</ul>
					<!-- .breadcrumb -->
				</div>
				<div class="page-content">
					<!-- PAGE CONTENT BEGINS -->
					<div class="row">
						<div class="col-xs-12">
							<!-- PAGE CONTENT BEGINS -->
							<div class="row">
								<div class="col-xs-12">
									<div class="row">
										<div class="col-xs-12">
											<form action="${ctxbi}/sys/log/" method="post"
												class="breadcrumb form-search" id="logForm">
												<table class="whiteTable">
													<tr>
														<th><spring:message code="accesslog.userid" /></th>
														<td><input id="createById" name="createBy.id"
															type="text" maxlength="50" class=""
															value="${log.createBy.id}" /></td>

														<th>URI</th>
														<td><input id="requestUri" name="requestUri"
															type="text" maxlength="50" class=""
															value="${log.requestUri}" /></td>

														<th></th>
													</tr>
													<tr>
														<th><spring:message code="accesslog.daterange" /></th>
														<td><input id="beginDate" name="beginDate"
															type="text" maxlength="20" class=""
															value="<fmt:formatDate value="${log.beginDate}" pattern="yyyy-MM-dd"/>"
															onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
															&nbsp;&nbsp;--&nbsp;&nbsp;&nbsp; <input id="endDate"
															name="endDate" type="text" maxlength="20" class=""
															value="<fmt:formatDate value="${log.endDate}" pattern="yyyy-MM-dd"/>"
															onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
														</td>

														<th style="width: 160px"><input id="exception"
															name="exception" type="checkbox" style="margin: 0px;"
															${log.exception eq '2'?' checked':''} value="2" /></th>
														<td><spring:message code="accesslog.exception" /></td>
														<th><input id="btnSubmit" class="btn btn-purple btn-sm"
															type="button"
															value="<spring:message code="accesslog.query"/>" />
														</th>
													</tr>
												</table>
											</form>
										</div>
									</div>
									<div class="row">
										<div class="col-xs-12">
											<div>
												<table id="logTable" style="width: 100%"
													class="table table-striped table-bordered table-hover">
													<thead>
														<tr>
															<!-- <th>操作菜单</th> -->
															<th width="10%"><spring:message code="accesslog.user"/></th>
															<th width="12%"><spring:message code="accesslog.company"/></th>
															<th width="10%"><spring:message code="accesslog.department"/></th>
															<th width="10%">URI</th>
															<th width="20%"><spring:message code="accesslog.params"/></th>
															<th width="10%"><spring:message code="accesslog.submission"/></th>
															<th width="10%"><spring:message code="accesslog.userip"/></th>
															<th width="10%"><spring:message code="accesslog.date"/></th>
															<th width="8%"><spring:message code="accesslog.exceptiondetails"/></th>
														</tr>
													</thead>
													<tbody>
													</tbody>
												</table>
											</div>
											<!-- /.table-responsive -->
										</div>
										<!-- /span -->
									</div>
								</div>
							</div>
							<!-- /row -->

							<!-- PAGE CONTENT ENDS -->
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>





				<!-- PAGE CONTENT ENDS -->
			</div>
			<!-- /.page-content -->
		</div>
		<!-- /.main-content -->

	</div>
	<!-- /.main-container-inner -->

	<a href="#" id="btn-scroll-up"
		class="btn-scroll-up btn btn-sm btn-inverse"> <i
		class="icon-double-angle-up icon-only bigger-110"></i>
	</a>
	</div>
	<!-- /.main-container -->


	<!-- 模态框（Modal） -->
	<div class="modal fade" id="configModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 1000px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="modalLabel"><spring:message code="accesslog.exceptiondetails"/></h4>
				</div>
				<div class="modal-body" id="modalBody"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">
						<spring:message code="study.management.view.close" />
					</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
</body>
<script type="text/javascript">
	var title = "${log.title}";
	var createById = "${log.createBy.id}";
	var requestUri = "${log.requestUri}";
	var beginDate = '<fmt:formatDate value="${log.beginDate}" pattern="yyyy-MM-dd"/>';
	var endDate = '<fmt:formatDate value="${log.endDate}" pattern="yyyy-MM-dd"/>';
	var exception = "${log.exception}";

	$(function() {
		$("#logTable").dataTable({
			"bSort" : false,
			"bFilter" : false,
			"bServerSide" : true,
			"oLanguage": dataTableLanguage,
			"sAjaxSource" : "${ctxbi }/sys/log/getLoglist",
			"aoColumns" : [
			/*        {
			"mData" : "title"
			},
			 */{
				"mData" : "createByName"
			}, {
				"mData" : "createByCompanyName"
			}, {
				"mData" : "createByOfficeName"
			}, {
				"mData" : "requestUri"
			}, {
				"mData" : "params"
			}, {
				"mData" : "method"
			}, {
				"mData" : "remoteAddr"
			}, {
				"mData" : "createDate"
			}, {
				"mData" : "id",
				"mRender" : function(data, type, full) {
					return "<a class='green' style='cursor:pointer;' onclick='showModal(\"" + data + "\")'><i class='icon-search bigger-130'></i></a>";
				}
			} ],
			"fnServerData" : function(url, aoData, fnCallback) {
				var iDisplayStart;
				var iDisplayLength;
				for ( var i in aoData) {
					if (aoData[i].name == 'iDisplayStart') {
						iDisplayStart = aoData[i].value;
					}
					if (aoData[i].name == 'iDisplayLength') {
						iDisplayLength = aoData[i].value;
					}
				}

				$.post(url, {
					'start' : iDisplayStart,
					'length' : iDisplayLength,
					'title' : title,
					'createById' : createById,
					'requestUri' : requestUri,
					'beginDate' : beginDate+" 00:00:00",
					'endDate' : endDate+" 24:00:00",
					'exception' : exception
				}, function(resp) {
					fnCallback(resp);
				})
			}
		});

		$("#btnSubmit").click(function(){
			if($("#beginDate").val()>$("#endDate").val()){
				$.simplyToast('<spring:message code="accesslog.startDateError"/>', 'warning');
			}else{
				$("#logForm").submit();
			}
		})
	})

	function showModal(data) {
		$("#modalBody").load("${ctxbi}/sys/log/logException?id=" + data);
		$("#configModal").modal("show");

	}
</script>
</html>