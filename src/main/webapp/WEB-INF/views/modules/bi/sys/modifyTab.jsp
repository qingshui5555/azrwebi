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
						<li><i class="icon-home home-icon"></i><a><spring:message code="layout.menu.name.system.configuration"/></a></li>
						<li><a href="${ctxbi }/sys/studyManagment/index"><spring:message code="layout.menu.name.study.management"/></a></li>
						<li class="active">Update Tab</li>
					</ul>
					<!-- .breadcrumb -->
				</div>
				<div class="page-content">
					<!-- PAGE CONTENT BEGINS -->
					<div class="row">
						<div class="col-xs-12">
							<div class="row">
								<div class="col-xs-12">
									<h3 class="header smaller lighter blue">Update Tab</h3>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12">
									<form action="${ctxbi}/sys/studyManagment/updateTab" method="post" id="createTabForm">
										<table class="whiteTable">
											<input type="hidden" name="id" value="${tab.id }">
											<input type="hidden" name="dashboardId" value="${dashboardId }">
											<tr>
												<th><spring:message code="tab.input.name"/></th>
												<td><input type="text" name="tabName" id="tabName" value="${tab.name }" style="width: 100px"></td>
											</tr>
											<tr>
												<th><spring:message code="tab.input.icon"/></th>
												<td>
													<select id="iconId" name="iconId" class="iconfont">
													<c:forEach items="${tabIconList }" var="tabIcon">
														<c:choose >
															<c:when test="${tab.icon==tabIcon.id }">
																<option selected="selected" value="${tabIcon.id }">${tabIcon.name }</option>
															</c:when>
															<c:otherwise>
																<option value="${tabIcon.id }">${tabIcon.name }</option>
															</c:otherwise>
														</c:choose>
													</c:forEach>
													</select>
												</td>
											</tr>
											<tr>
												<th><spring:message code="tab.input.sequence"/></th>
												<td><input type="number" value="${tab.sort }" name="sort" id="sort" style="width: 100px" ></td>
											</tr>
											<tr>
												<th></th>
												<td>
													<button type="submit" class="btn btn-purple btn-sm">
														<spring:message code="tab.update"/></button>
													<button type="button" class="btn btn-sm" onclick="history.back();"><spring:message code="tab.cancel"/></button>
												</td>
											</tr>
										</table>
									</form>
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
		/* var oTable1 = $('#studyTable').dataTable({
			"aoColumns" : [ {
				"bSortable" : false
			}, {
				"bSortable" : false
			}, null, null, null, {
				"bSortable" : false
			} ]
		}); */
		
		$("#createTabForm").submit(function(){
			var tabName=$("#tabName").val();
			var sort=$("#sort").val();
			if($.trim(tabName)==''){
				$.simplyToast('<spring:message code="tab.input.name.danger"/>', 'danger');
				$("#tabName").focus();
				return false;
			}
			if(isNaN(x)){
				$.simplyToast('<spring:message code="tab.input.sequence.danger"/>', 'danger');
				$("#sort").focus();
				return fal
			}
			
			return true;
		})
	})
</script>
</html>