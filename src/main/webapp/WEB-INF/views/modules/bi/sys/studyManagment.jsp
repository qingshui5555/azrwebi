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
			<a class="menu-toggler" id="menu-toggler" href="#"> <span class="menu-text"></span></a>
			<c:import url="/bi/layout/sidebar">
				<c:param name="dataMenuActive" value="acd9325b2dea40c2b3b5505a2d842a64" />
			</c:import>
			<div class="main-content">
				<div class="breadcrumbs" id="breadcrumbs">
					<ul class="breadcrumb">
						<li><i class="icon-home home-icon"></i><a><spring:message code="layout.menu.name.system.configuration"/></a></li>
						<li class="active"><spring:message code="layout.menu.name.study.management"/></li>
					</ul>
				</div>
				<div class="page-content">
					<!-- PAGE CONTENT BEGINS -->

					<div class="row">
						<div class="col-xs-12">
							<br>
							<div class="row">
								<div class="col-xs-12">
									<form class="form-inline"
										action="${ctxbi}/sys/studyManagment/index" method="post">
										<label for="form-field-select-1" class="inline"><spring:message code="study.management.ta"/>:</label> <select id="form-field-select-1" name="taId">
											<option value="0" ${empty taId||taId==0?'selected':'' }><spring:message code="study.management.ta.all"/></option>
											<c:forEach items="${taList}" var="ta">
												<option value="${ta.id }" ${taId==ta.id?'selected':'' }><spring:message code="${ta.taCode }" text="${ta.taAbbrName }"/></option>
											</c:forEach>
											<option value="-1" ${taId==-1?'selected':'' }><spring:message code="option.text.empty"/></option>
										</select>
										<button type="submit" class="btn btn-purple btn-sm">
											<i class="icon-search icon-on-right bigger-110"></i>
											<spring:message code="study.management.search"/>
										</button>
									</form>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12">
									<h3 class="header smaller lighter blue"><spring:message code="study.management.study.list"/></h3>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12">
									<div class="table-responsive">
										<table id="studyTable"
											class="table table-striped table-bordered table-hover">
											<thead>
												<tr>
													<th><spring:message code="study.management.ta"/></th>
													<th><spring:message code="study.management.study.code"/></th>
													<th><spring:message code="study.management.study.name"/></th>
													<th><spring:message code="study.management.tab.num"/></th>
													<th><spring:message code="study.management.last.modify.date"/></th>
													<th><spring:message code="study.management.operation"/></th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${studyVoList}" var="vo">
													<tr>
														<td>
															<span id="${vo.id }_span"><spring:message code="${vo.remarks }" text="${vo.taName }"/></span>
														</td>
														<td>${vo.studyCode }</td>
														<td>${vo.studyName }</td>
														<td>${vo.tabNum }</td>
														<td><fmt:formatDate value="${vo.lastModifyDate }"
																pattern="yyyy-MM-dd" /></td>
														<td><div
																class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
																<a class="green" style="cursor: pointer;" onclick="editTaName('${vo.id }','${vo.taId}');" title="<spring:message code="study.management.ta.config"/>"> <i class="icon-pencil bigger-130"></i></a>
																<a class="green"
																	href="${ctxbi}/sys/studyManagment/tabList?dashboardId=${vo.dashboardId}&studyName=${vo.studyName}"
																	title="<spring:message code="study.management.config"/>"> <i
																	class="icon-cog bigger-130"></i>
																</a>
															</div></td>
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
	
	<!-- 模态框（Modal） -->
	<div class="modal fade" id="modal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="modalLabel"><spring:message code="study.management.ta"/></h4>
				</div>
				<div class="modal-body" id="modalBody">
					<select id="taSelect">
							<c:forEach items="${taList}" var="ta">
								<option value="${ta.id }" data-name="<spring:message code="${ta.taCode }" text="${ta.taAbbrName }"/>"><spring:message code="${ta.taCode }" text="${ta.taAbbrName }"/></option>
							</c:forEach>
							<option value=""><spring:message code="option.text.empty" /></option>
					</select>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="study.management.close"/>
					</button>
					<button type="button" class="btn btn-purple"
						onclick="updateStudyTaName();"><spring:message code="study.management.submit"/></button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
	<!-- /.modal -->
</body>
<script type="text/javascript">
	$(function() {
		var oTable1 = $('#studyTable').dataTable({
			"aoColumns" : [ {
				"bSortable" : false
			}, {
				"bSortable" : false
			}, null, null, null, {
				"bSortable" : false
			} ],
			"oLanguage": dataTableLanguage
		});
	})
	
	var studyId;
	
	function editTaName(id,taId){
		$("#modal").modal('show');
        $("#taSelect").val(taId);
		studyId=id;
	}
	
	function updateStudyTaName(){
		$.post("${ctx}/bi/sys/studyManagment/updateStudyTaName", {
			'studyId':studyId,
			'taId':$("#taSelect").val()
		}, function() {
            var dn = $("#taSelect :selected").attr("data-name");
            if(dn){
                $("#"+studyId+"_span").html(dn);
            }else{
                $("#"+studyId+"_span").html("");
            }

            $("#"+studyId+"_span").parent().find("a").attr("onclick", "editTaName('" + studyId + "','" + $("#taSelect").val() + "')");
			$.simplyToast('<spring:message code="viewList.success"/>', 'info');
			$("#modal").modal('hide');
			studyId=null;
		})
	}
</script>
</html>