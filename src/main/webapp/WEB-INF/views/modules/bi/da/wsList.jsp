<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" buffer="1024kb"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="layout.head.analytics" /></title>
<c:import url="/bi/layout/resource" />
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
					value="2d805f03b6294a959a5436c263c58163" />
			</c:import>
			<div class="main-content">
				<div class="breadcrumbs" id="breadcrumbs">
					<ul class="breadcrumb">
						<li><i class="icon-home home-icon"></i>
						<a><spring:message code='da.wslist.workspace'/></a></li>
						<li class="active"><spring:message code='da.wslist.myworkspace'/></li>
					</ul>
					<!-- .breadcrumb -->
				</div>
				<div class="page-content">
					<!-- PAGE CONTENT BEGINS -->
					<div class="row">
						<div class="col-xs-12">
							<div class="row">
								<div class="col-xs-12">
									<h3 class="header smaller lighter blue"><spring:message code='da.wslist.dataanalysisview'/></h3>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12">
									<div class="table-responsive">
										<table id="studyTable"
											class="table table-striped table-bordered table-hover">
											<thead>
												<tr>
													<th><spring:message code='da.index.ta'/></th>
													<th><spring:message code='da.wslist.studyname'/></th>
													<th><spring:message code='da.wslist.testname'/></th>
													<th><spring:message code='da.wslist.remarks'/></th>
													<th><spring:message code='da.wslist.datamodel'/></th>
													<th><spring:message code='da.wslist.lastmodifydate'/></th>
													<th><spring:message code='da.wslist.operation'/></th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${daViewList}" var="vo">
													<tr id="${vo.id}">
														<td>
															${vo.taName }
														</td>
														<td>${vo.studyName }</td>
														<td>${vo.invoke.testName }</td>
														<%-- <td>${vo.remarks }</td> --%>
														<td>${vo.title }</td>
														<td>${vo.invoke.modelMeta.modelName }</td>
														<td><fmt:formatDate value="${vo.updateDate }"
																pattern="yyyy-MM-dd" /></td>
														<td>
															<!-- 编辑&nbsp删除&nbsp -->
															<%-- <a class="red deleteIcon"
																	style="cursor: pointer;"
																	title='<spring:message code="study.managment.view.remove"/>'>
																	<i class="icon-remove bigger-130"></i>
															</a> --%>
															<div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
						                                      <a name="a_wslist_edit" class="green" href="${ctxbi}/ws/edit?daViewId=${vo.id}" title="<spring:message code="study.management.user.edit"/>"><i class="icon-pencil bigger-130"></i></a>
						                                      <a name="a_wslist_delete" class="red" href="javascript:void(0)" onclick="deleteWorkSpace('${vo.id}',this);" style="cursor:pointer" title="<spring:message code="study.management.user.delete"/>"><i class="icon-trash bigger-130"></i></a>
						                                    </div>
														</td>
													</tr>
												</c:forEach>
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
	
</body>
<script type="text/javascript">
	$(function() {
		var oTable1 = $('#studyTable').dataTable({
			"aoColumns" : [ {
				"bSortable" : false
			}, {
				"bSortable" : false
			}, null, null, null,null, {
				"bSortable" : false
			} ],
			"oLanguage": dataTableLanguage
		});
		
	});
	
	function deleteWorkSpace(daViewId,e){
		var deleteParams = {};
		deleteParams.daViewId = daViewId;
		
		var msg = "";
		
		$.ajax({
			url : "${ctxbi}/view/showDaViewByViewId", 
			type : "post",
			async : false,
			data : deleteParams ,
		 	success : function(result) {
				console.log(result);
				console.log(result.tabViewList);
				console.log(result.tabViewList.length);
				if(result.tabViewList.length>0){
					msg = "<spring:message code='da.wslist.thisviewhadbeenaddedtothedashboard'/><br/><spring:message code='da.wslist.confirmdeletion'/>";
				}else{
					msg = "<spring:message code='da.wslist.confirmdeletion'/>";
				}
			}
		});
		
		console.log(msg);
		
		bootbox.confirm({
			buttons : {
				confirm : {
					label : '<spring:message code="layout.menu.name.drop.ok"/>',
					className: 'btn-purple'
				},
				cancel : {
					label : '<spring:message code="layout.menu.name.drop.cancel"/>'
				}
			},
			message : msg,
			callback : function(result) {
				if (result) {
					$.post("${ctxbi}/ws/delete", 
							deleteParams ,
					 		function() {
							$.simplyToast('<spring:message code="layout.menu.name.drop.success"/>', 'success');
							//$(e).parent().parent().parent().remove();
							window.location.reload();
					})
				}
			}
		});
	}
</script>
</html>