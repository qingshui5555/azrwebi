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
						<li><a
							href="${ctxbi }/sys/studyManagment/tabList?dashboardId=${dashboardId}&studyName=${studyName}"><spring:message
									code="study.management.tab.list" /></a></li>
						<li class="active"><spring:message
								code="study.management.view.list" /></li>
					</ul>
					<!-- .breadcrumb -->
				</div>
				<div class="page-content">
					<!-- PAGE CONTENT BEGINS -->

					<div class="row" style="height:80px;">
						<div class="col-xs-12">
							<div class="row">
								<div class="col-xs-12">
									<h3 class="header smaller lighter blue">
										<spring:message code="study.management.view.customize" />
										(For Tab ${tabName})
									</h3>
								</div>
							</div>
							<div class="row" >
								<div class="col-xs-12">
									<div class="row">
										<div class="col-xs-12">
											<form class="form-inline">
												<label for="form-field-select-1" class="inline"><spring:message
														code="study.management.view.select" />:</label> <select
													id="viewSelect">
													<option value="0"></option>
													<c:forEach items="${viewList}" var="view">
														<option value="${view.id}"
															data-type="<%=ViewTypeEnum.Default_View.getTypeId() %>">(<%=ViewTypeEnum.Default_View.getTypeName()%>)${view.name }
														</option>
													</c:forEach>
													<c:forEach items="${daViewList}" var="view">
														<option value="${view.id}"
															data-type="<%=ViewTypeEnum.Da_View.getTypeId() %>">(<%=ViewTypeEnum.Da_View.getTypeName()%>)${view.name }
														</option>
													</c:forEach>
												</select> <label for="form-field-select-1" class="inline"><spring:message
														code="study.management.view.column" />:</label> <select
													id="sizeSelect">
													<option value="12">1</option>
													<option value="6">2</option>
													<option value="4">3</option>
												</select>

												<button type="button" class="btn btn-purple btn-sm"
													onclick="addView('${tabId}','${dashboardId}');">
													<i class="icon-plus icon-on-right bigger-110"></i>
													<spring:message code="study.management.view.add" />
												</button>
											</form>
										</div>
									</div>
									<br>
									<!-- /.table-responsive -->
								</div>
								<!-- /span -->
							</div>
						</div>
					</div>
					<!-- /row -->

					<!-- PAGE CONTENT ENDS -->



					<div class="row">
						<div class="col-xs-12">
							<div class="row" id="sortView">
								<c:forEach items="${tabViewList }" var="view">
									<div class="col-sm-${view.size} widget-container-span"
										data-id="${view.tabViewId}">
										<div class="widget-box">
											<div class="widget-header">
												<h5 id="${view.tabViewId}_td">${empty view.alias?view.name:view.alias}</h5>
												<div class="widget-toolbar">
													<a style="cursor: pointer;"
														onclick="openModal('${view.name }','${view.tabViewId}','${ctx}${view.configUrl}',${view.configFlag },'${view.viewChartHeight}');"
														title='<spring:message code="study.management.view.config"/>'>
														<i class="icon-cog"></i>
													</a> <a style="cursor: pointer;"
														onclick="dropView('${view.tabViewId}',this)"
														title='<spring:message code="study.management.view.remove"/>'>
														<i class="icon-remove"></i>
													</a>
												</div>
											</div>

											<div class="widget-body">
												<div class="widget-main">
													<p>
														<spring:message code="study.management.view.type" />
														：
														<spring:message
															code="${view.viewType==0?'study.management.view.default':'study.management.view.data.analysis'}" />
													</p>
													<p>
														<spring:message code="study.management.view.alias" />
														：${view.alias }
													</p>
													<p>
														<spring:message
															code="study.management.view.height" />
														：${view.viewChartHeight}
													</p>
												</div>
											</div>
										</div>
									</div>
								</c:forEach>
							</div>
						</div>
					</div>



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
					<h4 class="modal-title" id="modalLabel"></h4>
				</div>
				<div class="modal-body">
					<div class="form-group no-padding row">
						<div class="col-sm-3">
							<spring:message code="study.management.view.alias" />
							：<input id="alias" type="text" >
						</div>
						<div class="col-sm-9">
							<spring:message code="study.management.view.height" />
							<!-- ：<input id="viewChartHeight" type="text"
								onkeyup="value=value.replace(/[^\d.]/g,'')"> -->
							：<select id="viewChartHeight" >
							<option value=''>&nbsp;<spring:message code="dashboard.select.choose" /></option>
							<option value=300>300px</option>
							<option value=350>350px</option>
							<option value=400>400px</option>
							<option value=450>450px</option>
							<option value=500>500px</option>
							</select>
							
						</div>
					</div>
					<center id="modalBodySpin" style="display: none;">
						<i class='icon-spinner icon-spin blue bigger-250'></i>
					</center>
					<div id="modalBody"></div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">
						<spring:message code="study.management.view.close" />
					</button>
					<button type="button" class="btn btn-purple"
						onclick="submitConfig();">
						<spring:message code="study.management.view.submit" />
					</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
</body>
<script type="text/javascript">
	$(function() {
		$('#viewTable').dataTable({
			bSort : false,
			"oLanguage": dataTableLanguage
		});
		
		$('#sortView').sortable({
	        connectWith: '.widget-container-span',
			opacity:0.8,
			revert:true,
			forceHelperSize:true,
			placeholder: 'widget-placeholder',
			forcePlaceholderSize:true,
			tolerance:'pointer',
			stop: function(){ 
				 var idArr=[];
				 $.each($(".widget-container-span"),function(i,n){
					 idArr.push($(n).attr("data-id"));
				 })
				 
				 $.post("${ctxbi}/sys/studyManagment/sortView",{'tabViewIdArr':idArr.toString(),tabId:'${tabId}'},function(){
					 
				 })
			}
	    });
	})

	function addView(tabId, dashboardId) {
		var viewId = $("#viewSelect").val();
		var size=$("#sizeSelect").val();
		
		if (viewId == 0) {
			$.simplyToast('<spring:message code="study.management.view.select.no"/>', 'warning');
			return;
		}
		var viewType=$("#viewSelect option:selected").attr("data-type");

		$.post("${ctxbi}/sys/studyManagment/addView", {
			"tabId" : tabId,
			"dashboardId" : dashboardId,
			"viewId" : viewId,
			"viewType" : viewType,
			"size":size
		}, function(data) {
			if (data.success) {
				location.reload();
			} else {
				$.simplyToast(data.info, 'danger');
			}
		})
	}

	function dropView(tabViewId, e) {
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
			message : '<spring:message code="da.wslist.confirmdeletion"/>',
			callback : function(result) {
				if (result) {
					$.post("${ctxbi}/sys/studyManagment/dropView", {
						"tabViewId" : tabViewId
					}, function(data) {
						if (data.success) {
							$.simplyToast('<spring:message code="study.management.view.remove.success"/>', 'success');
							$(e).parents(".widget-container-span").remove();
						} else {
							$.simplyToast(data.info, 'danger');
						}
					})
				}
			}
		});
	}

	var openModalTabViewId;

	function openModal(name, tabViewId, url, configFlag,viewChartHeight) {
		$("#modalBody").html("");
		openModalTabViewId = tabViewId;
		$("#alias").val($.trim($("#"+tabViewId+"_td").html()));
		//$("#viewChartHeight").val(viewChartHeight);
		//
		var optionVal;
		$("#viewChartHeight option").each(function () {
	    	var option = $(this).val();
	    	if(option==viewChartHeight){
	    		optionVal = viewChartHeight;
	    	}
		});
		if(optionVal){
			$("#viewChartHeight").val(optionVal);
		}else{
			$("#viewChartHeight").val('');
		}
		
		
		$("#modalLabel").html(name);
		if(configFlag){
			$("#modalBodySpin").show();
			$("#modalBody").load(url+"?studyId="+${dashboardId}+"&tabViewId="+tabViewId,function(){
				$("#modalBodySpin").hide();
			});
		}
		$("#configModal").modal('show');
	}

	function submitConfig() {
		var formData = {};
		var configForm = $("#configForm").serializeArray();
		$.each(configForm, function() {
			if(formData[this.name]!=undefined){
				var curVal=formData[this.name];
				var arr=new Array();
				if(typeof curVal=='string'){
					arr.push(curVal);
				}else{
					for(var i in curVal){
						arr.push(curVal[i]);
					}
				}
				arr.push(this.value);
				formData[this.name]=arr;
			}else{
				formData[this.name] = this.value;
			}
		});
		$.post("${ctx}/bi/sys/studyManagment/configTabView", {
			tabViewId : openModalTabViewId,
			alias:$("#alias").val(),
			viewChartHeight:$("#viewChartHeight").val(),
			configJson :JSON.stringify(formData).replace(/\"/g,"'")
		}, function() {
			$("#"+openModalTabViewId+"_td").html($("#alias").val());
			$.simplyToast('<spring:message code="viewList.success"/>', 'info');
			location.reload();
		})
	}
	
	
</script>
</html>