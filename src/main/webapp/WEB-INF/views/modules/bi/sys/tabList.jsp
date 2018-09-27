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
				<c:param name="dataMenuActive" value="29" />
			</c:import>
			<div class="main-content">
				<div class="breadcrumbs" id="breadcrumbs">
					<ul class="breadcrumb">
						<li><i class="icon-home home-icon"></i><a><spring:message
									code="layout.menu.name.system.configuration" /></a></li>
						<li><a href="${ctxbi }/sys/studyManagment/index"><spring:message
									code="layout.menu.name.study.management" /></a></li>
						<li class="active"><spring:message
								code="study.management.tab.list" /></li>
					</ul>
					<!-- .breadcrumb -->
				</div>
				<div class="page-content">
					<!-- PAGE CONTENT BEGINS -->

					<!-- PAGE CONTENT BEGINS -->
					<div class="row">
						<div class="col-xs-12">
							<div class="row">
								<div class="col-xs-12">
									<h3 class="header smaller lighter blue">
										<spring:message code="study.management.tab.list" />
										(For ${studyName })
									</h3>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12">
									<div class="row">
										<div class="col-xs-12">
											<form action="${ctxbi}/sys/studyManagment/createTabIndex"
												method="post">
												<input type="hidden" name="dashboardId"
													value="${dashboardId}">
												<button type="submit" class="btn btn-purple btn-sm">
													<i class="icon-plus icon-on-right bigger-110"></i>
													<spring:message code="study.management.tab.create" />
												</button>
											</form>
										</div>
									</div>
									<br>
									<div class="table-responsive">
										<table id="tabTable"
											class="table table-striped table-bordered table-hover">
											<thead>
												<tr>
													<th><spring:message code="study.management.tab.name" /></th>
													<th><spring:message code="tab.input.icon"/></th>
													<th><spring:message code="study.management.sequence.num" /></th>
													<th><spring:message code="study.management.create.date" /></th>
													<th><spring:message code="study.management.update.date" /></th>
													<th><spring:message code="study.management.operation" /></th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${tabList }" var="tab" varStatus="vs">
													<tr>
														<td><input type="hidden" class="tabId"
															name="${tab.id}" value="${tab.id}" />${tab.name }</td>
														<td><i class="${tab.icon }"></i></td>	
														<td>${tab.sort }</td>
														<td><fmt:formatDate value="${tab.createDate }"
																pattern="yyyy-MM-dd" /></td>
														<td><fmt:formatDate value="${tab.updateDate }"
																pattern="yyyy-MM-dd" /></td>
														<td>
															<div
																class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
																<a class="green"
																	href="${ctxbi}/sys/studyManagment/modifyTab?tabId=${tab.id}&tabName=${tab.name }&dashboardId=${tab.dashboardId}&studyName=${studyName }"
																	style="cursor: pointer;"
																	title="<spring:message code="study.management.tab.edit"/>">
																	<i class="icon-pencil bigger-130"></i>
																</a> <a class="green"
																	href="${ctxbi}/sys/studyManagment/viewList?tabId=${tab.id}&tabName=${tab.name }&dashboardId=${tab.dashboardId}&studyName=${studyName }"
																	style="cursor: pointer;"
																	title="<spring:message code="study.management.tab.config"/>">
																	<i class="icon-cog bigger-130"></i>
																</a> <a class="red" onclick="dropTab('${tab.id}',this)"
																	style="cursor: pointer;"
																	title="<spring:message code="study.management.tab.remove"/>">
																	<i class="icon-trash bigger-130"></i>
																</a> <a class="blue" onclick="upTab('${tab.id}',this)"
																	style="cursor: pointer;"
																	title="<spring:message code="study.management.tab.up"/>"
																	> <i
																	class="icon-arrow-up bigger-130"></i>
																</a> <a class="blue" onclick="downTab('${tab.id}',this)"
																	style="cursor: pointer;"
																	title="<spring:message code="study.management.tab.down"/>"
																	> <i
																	class="icon-arrow-down bigger-130"></i>
																</a>
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
		$('#tabTable').dataTable({
			bSort : false,
			"oLanguage" : dataTableLanguage
		});
	})

	function dropTab(tabId, e) {
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
			message : '<spring:message code="study.management.tab.sure.remove"/>',
			callback : function(result) {
				if (result) {
					$.post("${ctxbi}/sys/studyManagment/dropTab", {
						"tabId" : tabId
					}, function() {
						$.simplyToast('<spring:message code="layout.menu.name.drop.success"/>', 'success');
						$(e).parents("tr").remove();
						location.reload();
					})
				}
			}
		});
	}

	function upTab(tabId, e) {
		//当前点击对象tr
		var currentTr = $(e).parents("tr");
		var tabSort = currentTr.find("td:eq(2)").html();

		//当前点击对象tr的上一个兄弟tr
		var prevTr = currentTr.prev();

		if (prevTr.length == 0) {
			$.simplyToast('<spring:message code="study.management.tab.top"/>', 'warning');
			return;
		}

		var prevTabId = prevTr.find("input[class='tabId']").val();
		var prevTabSort = prevTr.find("td:eq(2)").html();

		$.post("${ctxbi}/sys/studyManagment/sortTab", {
			"tabId" : tabId,
			"tabSort" : tabSort,
			"prevTabId" : prevTabId,
			"prevTabSort" : prevTabSort
		}, function() {
			//html上的sort交换
			currentTr.find("td:eq(2)").html(prevTabSort);
			prevTr.find("td:eq(2)").html(tabSort);

			//html元素交换
			currentTr.insertBefore(prevTr);
		})
	}

	function downTab(tabId, e) {
		//当前点击对象tr
		var currentTr = $(e).parents("tr");
		var tabSort = currentTr.find("td:eq(2)").html();
		
		//当前点击对象tr的上一个兄弟tr
		var nextTr = currentTr.next();

		if (nextTr.length == 0) {
			$.simplyToast('<spring:message code="study.management.tab.bottom"/>', 'warning');
			return;
		}

		var nextTabId = nextTr.find("input[class='tabId']").val();
		var nextTabSort = nextTr.find("td:eq(2)").html();

		$.post("${ctxbi}/sys/studyManagment/sortTab", {
			"tabId" : nextTabId,
			"tabSort" : nextTabSort,
			"prevTabId" : tabId,
			"prevTabSort" : tabSort
		}, function() {
			//html上的sort交换
			currentTr.find("td:eq(2)").html(nextTabSort);
			nextTr.find("td:eq(2)").html(tabSort);

			//html元素交换
			nextTr.insertBefore(currentTr);
		})
	}
</script>
</html>