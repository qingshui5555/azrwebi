<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" buffer="1024kb"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="layout.head.analytics" /></title>
<c:import url="/bi/layout/resource" />
<%@ include
	file="/WEB-INF/views/modules/bi/da/daValidInputDataModal.jsp"%>
<style type="text/css">
.max {
	width: 100%;
	position: fixed;
	z-index: 10000;
	height: 100%;
	left: 0px;
	top: 0px;
	padding-left: 0px;
	margin: 0px;
	bottom: 0px;
	right: 0px;
	background: #fff;
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
					value="study_${dashBoardShow.study.guid}" />
			</c:import>
			<div class="main-content">
				<div class="breadcrumbs" id="breadcrumbs">
					<ul class="breadcrumb">
						<li><i class="icon-home home-icon"></i> <spring:message
								code="layout.menu.name.ta" /></li>
						<li><spring:message code="${ dashBoardShow.ta.remarks}" text="${dashBoardShow.ta.taName }"/></li>
						<li class="active">${dashBoardShow.study.studyName }</li>
					</ul>
					<!-- .breadcrumb -->
				</div>
				<div class="page-content">
					<!-- PAGE CONTENT BEGINS -->

					<div class="row">
						<div class="col-xs-12">
							<h5 class="font-lg pdfCont">
								<i class="icon-list-alt"></i>
								<spring:message code="dashboard.info.study" />
								: ${dashBoardShow.study.studyName }
							</h5>

							<div class="row" style="margin-top: 20px; margin-bottom: 20px;">
								<div class="col-xs-4">
									<div class="radio-inline no-padding">
										<label> <input name="search_type" value="all_patients"
											autocomplete="off" type="radio" checked class="ace">
											<span class="lbl"><spring:message
													code="dashboard.filter.search.all" /></span> <span
											class="text-primary patientCount">${dashBoardShow.study.patientCount}</span>
											<spring:message code="dashboard.filter.search.patient" />
										</label>
									</div>
								</div>
								<div class="col-xs-8">
									<div class="mutiple_cont">
										<div class="radio-inline"
											style="vertical-align: top; margin-right: 10px;">
											<label> <input name="search_type" autocomplete="off"
												value="choose_groups" type="radio" class="ace"> <span
												class="lbl"><spring:message
														code="dashboard.filter.search.cohort" />(<span
													class="text-primary patientCount"
													id="cohort_choose_patient_count">0</span>&nbsp;<spring:message
														code="dashboard.filter.search.patient" />)</span>
											</label>
										</div>
										<select multiple="multiple" class="width-60 chosen-select"
											id="cohordt_choose" autocomplete="off"
											data-placeholder="<spring:message code="dashboard.filter.search.select.cohort"/>">
											<c:forEach items="${dashBoardShow.patientGroupList }" var="group">
												<option data-type="${group.groupType }"
													data-id="${group.guid }"
													data-patient-count="${group.groupCount }"
													name="cohordt_choose_select"
													value="${group.groupType},${group.guid},${group.groupCount}">${group.groupName }(${group.groupCount })</option>
											</c:forEach>
										</select>
									</div>

								</div>
							</div>


							<!-- PAGE CONTENT BEGINS -->
							<div class="row">
								<div class="col-xs-12">
									<div class="tabbable tabbable-tabdrop">
										<ul class="nav nav-tabs" id="tab_ul"
											style="margin-right: 1px;">
											<c:forEach items="${dashBoardShow.showTabList}" var="showTab">
												<li data-tabId="${showTab.tab.id }"><a
													data-tabId="${showTab.tab.id }" data-toggle="tab"
													data-viewIds="<c:forEach items="${showTab.viewList }" var="view" varStatus="viewSta"><c:if test="${not viewSta.first}">,</c:if>${view.viewId }</c:forEach>"
													data-viewTypes="<c:forEach items="${showTab.viewList }" var="view" varStatus="viewSta"><c:if test="${not viewSta.first}">,</c:if>${view.viewType }</c:forEach>"
													data-viewNames="<c:forEach items="${showTab.viewList }" var="view" varStatus="viewSta"><c:if test="${not viewSta.first}">,,</c:if>${view.viewName }</c:forEach>"
													data-sorts="<c:forEach items="${showTab.viewList }" var="view" varStatus="viewSta"><c:if test="${not viewSta.first}">,</c:if>${view.sort }</c:forEach>"
													data-sizes="<c:forEach items="${showTab.viewList }" var="view" varStatus="viewSta"><c:if test="${not viewSta.first}">,</c:if>${view.size }</c:forEach>"
													data-aliases="<c:forEach items="${showTab.viewList }" var="view" varStatus="viewSta"><c:if test="${not viewSta.first}">,,</c:if>${view.alias }</c:forEach>"
													data-tabViewIds="<c:forEach items="${showTab.viewList }" var="view" varStatus="viewSta"><c:if test="${not viewSta.first}">,</c:if>${view.tabId }</c:forEach>"
													href="${ctxbi}/dashBoard/tab/${showTab.tab.id }?studyId=${dashBoardShow.study.guid}">
														<!-- <i class="pink icon-dashboard bigger-110"></i> -->
														<i class="${showTab.tab.icon }"></i>
														${showTab.tab.name }
												</a></li>
											</c:forEach>
										</ul>
										<div class="tab-content">
											<%-- <div class="tab-pane in active" id="tabContent">
												<c:if
													test="${empty dashBoardShow.showTabList or fn:length(dashBoardShow.showTabList)==0 }">
													<div class="alert alert-warning">
														<span> <spring:message code="dashboard.tip.notab" />
														</span>
													</div>
												</c:if>
											</div> --%>



											<div class="row">
												<div class="col-xs-12" id="tabContentDiv">
													<%--<div class="row" id="tabContent"></div>--%>
												</div>
												<div id="tabContentDivForLoading" class="col-xs-12" style="height:0px;overflow-y :auto">
												</div>
											</div>

										</div>




									</div>
								</div>
							</div>
							<!-- /row -->
							<!-- PAGE CONTENT ENDS -->
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->

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




	<div class="modal fade" id="daview_edit_modal" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel"><spring:message code="dashboard.modal.analysis.edit" /></h4>
				</div>
				<div class="modal-body" id="daview_edit_modal_body_div"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">
						<spring:message code="dashboard.da.close" />
					</button>
					<button type="button" class="btn btn-purple"
						id="daview_edit_modal_reload_bt">
						<spring:message code="dashboard.da.rerun" />
					</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
</body>
<script type="text/javascript">
	var requestParam = {
		studyId : '${dashBoardShow.study.guid}',
		cohorts : [],
		groups : [],
		patientId : '',
		tabId : '',
		viewId : '',
		allPatientsFlag : true
	};

	//data annalysis reload时用于参数重载的数据模型对象
	//本地缓存保存上一次的扩展参数和执行方法名
	var invokeParams = {
		testName : "",
		extAtt : {}
	}

	//各tab页的数据缓存对象
	var tabContentTemp = {};

	//cohort 多选控件对象
	var chosen_sel;

	var lang = '${lang}';
	
	var heightTemp = {};
	$(function() {
		//如果是从rwe跳转过来则需要判断语言类型进行切换
		if(lang){
			if(lang=="zh"||lang=="zh_CN"){
				$("#lang_zh").attr("class","icon-circle");
				$("#lang_en").attr("class","icon-circle-blank");
			}else{
				$("#lang_en").attr("class","icon-circle");
				$("#lang_zh").attr("class","icon-circle-blank");
			}
			document.cookie = '_change_locale=' + lang+';path=/';
			location.href="${ctxbi}/dashBoard/${redirectStudyId}";
		}
		//view点击切换tab事件

		$("#tab_ul li").on(
				"click",
				function(event) {
					$("#tab_ul li").removeClass("active");
					$(this).addClass("active");

					console.log("requestParam.tabId:"+requestParam.tabId);
					if (requestParam.tabId != undefined && requestParam.tabId != '') {
						//$("#tabContent_" + requestParam.tabId).hide();
						//setTimeout('$("#tabContent_'+ requestParam.tabId+'").appendTo($("#tabContentDivForLoading"))',200);
						//$("#tabContent_" + requestParam.tabId).appendTo($("#tabContentDivForLoading"));
						//heightTemp["tabContent_" + requestParam.tabId]=$("#tabContent_" + requestParam.tabId).height();
						//$("#tabContent_" + requestParam.tabId).height(0);
						$("#tabContent_" + requestParam.tabId).css({"height":"0","overflow-y":"auto"});
					}
					var tabId = $(this).find("a").attr("data-tabId");
					console.log("tabId:"+requestParam.tabId);
					var tempDiv = $("#tabContent_" + tabId);
					if (tempDiv.length != 0) {
						//$("#tabContent_" + tabId).show();
						//$("#tabContent_" + tabId).appendTo($("#tabContentDiv"));
						//$("#tabContent_" + tabId).height(heightTemp["tabContent_" + tabId]);
						$("#tabContent_" + tabId).css({"height":"100%","overflow-y":"*"});
						requestParam.tabId = tabId;
						return false;
					} else {
						$('<div class="row" id="tabContent_' + tabId + '">').appendTo($("#tabContentDiv"));
						requestParam.tabId = tabId;
					}
					//由于名称和别名中可能含有单个逗号，这里如果用单个逗号分隔会出现问题
					var viewIds = $(this).find("a").attr("data-viewIds").split(",");
					var viewNames = $(this).find("a").attr("data-viewNames").split(",,");
					var viewTypes = $(this).find("a").attr("data-viewTypes").split(",");
					var viewSorts = $(this).find("a").attr("data-sorts").split(",");
					var viewSizes = $(this).find("a").attr("data-sizes").split(",");
					var viewAliases = $(this).find("a").attr("data-aliases").split(",,");
					var tabViewIds = $(this).find("a").attr("data-tabViewIds").split(",");

					$("#tabContent_" + tabId).html("");

					$("#sortView").html("");
					$.each(viewIds, function(i, n) {
						if (n != '') {
							var viewUrl = "${ctxbi}/view/" + n;
							var viewDivId = "viewId_" + n;
							var viewName = viewNames[i];
							var viewType = viewTypes[i];
							var viewSort = viewSorts[i];
							var viewSize = viewSizes[i];
							var viewAliase = viewAliases[i];
							var tabViewId = tabViewIds[i];
							//当加入相同的view时，viewDivId会重复,此时用viewDivId+"_"+tabViewId方式
							viewDivId = viewDivId+"_"+tabViewId;
							
							var setBt = "";
							/* if (viewType == "2") {
								setBt = "<a class='btn btn-minier btn-purple' name='daview_edit_a' data-daview-id='"+n+"'> <i class='icon-edit bigger-20'></i> <spring:message code="dashboard.da.rerun" /> </a>";
							} */

							/* $("#tabContent").append(
									"<div class='row'><div class='col-xs-10 blue'><h4> " + viewName + " </h4></div><div class='col-xs-2' align='right'>" + setBt
											+ "</div></div><div class='hr hr1 hr-dotted'></div><div class='row'><div class='col-xs-12' name='tab_view_divs' data-view-type='"+viewType+"' data-view-id='"+n+"' id='"+viewDivId+"'><center><i class='icon-spinner icon-spin blue bigger-250'></i></center></div></div>");
							 */

							var viewContent = '<div class="col-sm-'+viewSize+' widget-container-span">' + '<div class="widget-box" style="overflow:auto">' + '<div class="widget-header">' + '<h5>' + (viewAliase == "" ? viewName : viewAliase) + '</h5>' + '<div class="widget-toolbar" style="padding-top:10px;height:37px;">';
							if (viewType == "2") {
								viewContent += '<a style="cursor: pointer;margin-right:4px;" onclick="reRunAnalysis(this)" title="<spring:message code="dashboard.tooltip.rerun"/>" data-daview-id="' + n + '" data-tab-view-id="' + tabViewId + '">' + '<i class="icon-cog"></i>' + '</a>';
							}
							viewContent += '<a style="cursor: pointer;margin-right:4px;" onclick="refreshView(this)" title="<spring:message code="dashboard.tooltip.refresh"/>" data-view-type="' + viewType + '" data-view-id="' + n + '" data-tab-view-id="' + tabViewId + '">' + '<i class="icon-refresh"></i>' + '</a>'
									+ '<a style="cursor: pointer;margin-right:4px;" onclick="fullscreen(this)" title="<spring:message code="dashboard.tooltip.enlarge"/>" status="small">' + '<i class="icon-resize-full"></i>' + '</a>'
									+ '<a style="cursor: pointer;margin-right:4px;" onclick="collapseView(this)" title="<spring:message code="dashboard.tooltip.hide"/>" status="show" data-action="collapse">' + '<i class="icon-chevron-up"></i>' + '</a>' 
							if(viewType == "0"){
									//下拉列表
								viewContent += 
									'<a data-toggle="dropdown" href="#" class="dropdown-toggle" ><i class="icon-filter"></i></a><ul class="dropdown-menu"><li data-stopPropagation="true" >'
									+'<div class="widget-box " style="width:200px;height:300px;overflow:auto">'
/* 									+'	<div class="widget-header widget-header-small header-color-blue">'
									+'		<h5>'
									+'			<spring:message code="patientEvent.filter.list" />'
									+'			<i class="icon-filter"></i>'
									+'		</h5>'
									+'	</div>' */
									+'	<div class="widget-body">'
									+'		<div class="widget-main form">'
/* 									+'			<div class="row">'
									+'				<div class="col-xs-12">'
									+'					<h5 class="header smaller lighter blue no-margin">'
									+'						<spring:message code="biomarkertest.title.expression"/>:'
									+'					</h5>'
									+'					<div class="orderByGroup">'
									+'						<select>'
									+'							<option>1</option>'
									+'							<option>2</option>'
									+'							<option>3</option>'
									+'						</select>'
									+'					</div>'
									+'				</div>'
									+'			</div>'
									+'			<div class="row">'
									+'				<div class="col-xs-12">'
									+'					<h5 class="header smaller lighter blue no-margin">'
									+'						<spring:message code="biomarkertest.bar.x" />:'
									+'					</h5>'
									+'					<div class="orderByGroup">'
									+'						<div class="checkbox">'
									+'							<label>'
									+'								<input type="checkbox" class="ace" >'
									+'								<span class="lbl"> choice 1</span>'
									+'							</label>'
									+'						</div>'
									+'						<div class="checkbox">'
									+'							<label>'
									+'								<input type="checkbox" class="ace" >'
									+'								<span class="lbl"> choice 2</span>'
									+'							</label>'
									+'						</div>'
									+'						<div class="checkbox">'
									+'							<label>'
									+'								<input type="checkbox" class="ace" >'
									+'								<span class="lbl"> choice 3</span>'
									+'							</label>'
									+'						</div>'
									+'					</div>'
									+'				</div>'
									+'			</div>' */
									+'		</div>'
									+'	</div>'
									+'</div>'
									+ '</li></ul>'
							}
									////////////////
							viewContent += 
									  '</div>' + '</div>' + '<div class="widget-body">'
									+ '<div class="widget-main" style="height:auto" name="tab_view_divs" data-view-type="'+viewType+'" data-view-id="'+n+'" id="'+viewDivId+'" data-tab-view-id="'+tabViewId+'">' + '<center><i class="icon-spinner icon-spin blue bigger-250"></i></center>' + '</div>' + '</div>' + '</div>' + '</div>';
							$("#tabContent_" + tabId).append(viewContent);
						}
					});

					$("#tabContent_" + tabId).find("div[name='tab_view_divs']").each(function() {
						var viewId = $(this).attr("data-view-id");
						var viewType = $(this).attr("data-view-type");
						var tabViewId = $(this).attr("data-tab-view-id");
						var viewUrl = '';
						if (viewType == '0') {
							viewUrl = "${ctxbi}/view/" + viewId + "?tabViewId=" + tabViewId;
						} else if (viewType == '2') {
							viewUrl = "${ctxbi}/view/da/" + viewId + "?tabViewId=" + tabViewId;
						}
						$("#viewId_" + viewId+"_"+tabViewId).html($('<center><i class="icon-spinner icon-spin blue bigger-250"></i></center>'));
						$(this).load(viewUrl, toParamUrl());
					});
				});
		
		$("#tab_ul li:first").click();

		$("#tab_ul").tabdrop();

		chosen_sel = $('.chosen-select').chosen();

		$("input[name='search_type']").click(function() {
			if (testSearchType()) {
				requestParam.allPatientsFlag = true;
			} else {
				requestParam.allPatientsFlag = false;
			}
			$("#tabContentDiv").html("");
			refushCurTab();
		});

		//判断查询方式 返回true则说明查询所有，false说明查询指定cohort和group
		function testSearchType() {
			var type = $("input[name='search_type']:checked").val();
			return "all_patients" == type;
		}

		//检测cohort下拉选择结果
		////注册检查事件
		$(chosen_sel).on("change", function() {
			processChooseCohorts();
		});

		////查看所有选中的分组
		function processChooseCohorts() {
			var choose_option = $("#cohort_choose_patient_count");
			var patient_count_all = 0;
			requestParam.groups = [];
			requestParam.cohorts = [];

			var chosen_sel_value = chosen_sel.val();
			if (chosen_sel_value != null) {
				for (var i = 0; i < chosen_sel_value.length; i++) {
					var attrArr = chosen_sel_value[i].split(",");
					var data_type = attrArr[0];
					var data_id = attrArr[1];
					var patient_count = attrArr[2];
					patient_count_all += Number(patient_count);

					//判断分组类型
					if (data_type = 0) {
						//是group分组
						requestParam.groups.push(data_id);
					} else if (data_type = 1) {
						//是cohart分组
						requestParam.cohorts.push(data_id);
					}
				}
				;
			}

			////去后台统计选择conhort&group下的所有用户数量
			choose_option.html("<i class='icon-spinner icon-spin blue bigger-50'></i>");
			$.post("${ctxbi}/dashBoard/" + requestParam.studyId + "/patientsCount", toCountParamUrl(), function(data) {
				$("#cohort_choose_patient_count").html(data.patientsCount);
			}, "json");
			if (!testSearchType()) {
				//如果是根据cohorts刷新当前tab
				refushCurTab();
			}
		}
		////将选中分组信息刷新到页面json对象中，并刷新选中人数
		function refushCohortChooseInfo(choose_indexs) {
			var patient_count_all = 0;
			requestParam.groups = [];
			requestParam.cohorts = [];
			var options = $("option[name='cohordt_choose_select']");
			$.each(choose_indexs, function(i, n) {
				var choose_option = options[n];
				var data_type = $(choose_option).attr("data-type");
				var data_id = $(choose_option).attr("data-id");
				var patient_count = $(choose_option).attr("data-patient-count");
				patient_count_all += Number(patient_count);

			});

		}

		////刷新当前打开的tab
		function refushCurTab() {
			$("#tabContentDiv").html("");
			//如果filter变化过  缓存数据需要清空
			tabContentTemp = {};
			var curTabId = requestParam.tabId;
			requestParam.tabId = '';
			$("li[data-tabId='" + curTabId + "']").click();
		}

		//对于data analysis view 可以修改其测试参数进行修改
		$("a[name='daview_edit_a']").on("click", function() {
			var daViewId = $(this).data("daview-id");
			$("#daview_edit_modal_body_div").html("<center><i class='icon-spinner icon-spin blue bigger-250'></i></center>");
			$("#daview_edit_modal_body_div").load("${ctxbi}/view/da/" + daViewId + "/edit", {
				daViewId : daViewId
			});
			$("#daview_edit_modal").modal();
		});
		//对于data analysis view 调整后的测试参数进行结果的重载
		$("#daview_edit_modal_reload_bt").on("click", function() {
			if (checkExtendAtt == undefined) {
				$.simplyToast("请确认加载了参数调整页面", 'danger');
				return false;
			}
			var checkResult = checkExtendAtt();
			if (checkResult) {
				refulshExtendAtt();
				var daViewId = $("#daview_edit_modal_daview_id_hidden").val();
				var tabViewId = $("#daview_edit_modal_tabview_id_hidden").val();
				var testName = $("#daview_edit_modal_test_name_hidden").val();
				var viewDivId = "viewId_" + daViewId+"_"+tabViewId;
				var url = "${ctxbi}/da/execute/" + daViewId + "/extendAtt";
				invokeParams.testName = testName;
				console.log(invokeParams);
				$("#" + viewDivId).html("<center><i class='icon-spinner icon-spin blue bigger-250'></i></center>");
				$("#" + viewDivId).load(url, invokeParams);

				$("#daview_edit_modal").modal("hide");
			} else {
				$.simplyToast(checkResult, 'danger');
			}
		});

		//导出PDF触发
		$("#exportPDF").click(function() {
			var url = "${ctxbi}/dashBoard/export/pdf/review?" + toParamUrl();
			window.open(url);
			// 			$.post(
			// 				"${ctxbi}/dashBoard/export/pdf",
			// 				{
			// 					htmlCodeStr:encodeURIComponent(htmlCode)
			// 				},
			// 				function(data){
			// 				}
			// 			);
		});

		function isArrayEquals() {

		}
		
	})

	function toParamUrl() {
		var urlParam = "allPatientsFlag=#[allPatientsFlag}&studyId=#[studyId}&patientId=#[patientId}&tabId=#[tabId}&viewId=#[viewId}#[cohorts}#[groups}";
		urlParam = urlParam.replace("#[allPatientsFlag}", requestParam.allPatientsFlag);
		urlParam = urlParam.replace("#[studyId}", requestParam.studyId);
		urlParam = urlParam.replace("#[patientId}", requestParam.patientId);
		urlParam = urlParam.replace("#[tabId}", requestParam.tabId);
		urlParam = urlParam.replace("#[viewId}", requestParam.viewId);
		var cohorts = "";
		var groups = "";
		if (!requestParam.allPatientsFlag) {
			$.each(requestParam.cohorts, function(i, n) {
				cohorts += "&cohorts=" + n;
			});
			if (cohorts == "") {
				cohorts = "&cohorts=";
			}

			$.each(requestParam.groups, function(i, n) {
				groups += "&groups=" + n;
			});
			if (groups == "") {
				groups = "&groups=";
			}
		}
		urlParam = urlParam.replace("#[cohorts}", cohorts);
		urlParam = urlParam.replace("#[groups}", groups);

		urlParam += "&patientCount=" + $("#patientCount").val();
		return urlParam;
	}

	function toCountParamUrl() {
		var urlParam = "studyId=#[studyId}#[cohorts}#[groups}";
		urlParam = urlParam.replace("#[studyId}", requestParam.studyId);
		var cohorts = "";
		var groups = "";
		$.each(requestParam.cohorts, function(i, n) {
			cohorts += "&cohorts=" + n;
		});
		if (cohorts == "") {
			cohorts = "&cohorts=";
		}

		$.each(requestParam.groups, function(i, n) {
			groups += "&groups=" + n;
		});
		if (groups == "") {
			groups = "&groups=";
		}
		urlParam = urlParam.replace("#[cohorts}", cohorts);
		urlParam = urlParam.replace("#[groups}", groups);
		return urlParam;
	}

	function fullscreen(e) {
		if ($(e).attr("status") == 'small') {
			$(e).attr("status", "full");
			$(e).attr("title", "<spring:message code='dashboard.tooltip.deflate'/>")
			$(e).children("i").attr("class", "icon-resize-small");
			$(e).parents(".widget-box").addClass("max");
			$("#navbar").hide();
			$("#tab_ul").hide();
		} else {
			$(e).attr("status", "small");
			$(e).attr("title", "<spring:message code='dashboard.tooltip.enlarge'/>")
			$(e).children("i").attr("class", "icon-resize-full");
			$(e).parents(".widget-box").removeClass("max");
			$("#navbar").show();
			$("#tab_ul").show();
		}
	}

	function refreshView(e) {
		var viewId = $(e).attr("data-view-id");
		var viewType = $(e).attr("data-view-type");
		var tabViewId = $(e).attr("data-tab-view-id");
		console.log("viewId:"+viewId+",viewType:"+viewType+",tabViewId:"+tabViewId);
		var viewUrl = '';
		if (viewType == '0') {
			viewUrl = "${ctxbi}/view/" + viewId + "?tabViewId=" + tabViewId;
		} else if (viewType == '2') {
			viewUrl = "${ctxbi}/view/da/" + viewId + "?tabViewId=" + tabViewId;
			//刷新本地缓存
			clearInvokeParams();
		}
		$("#viewId_" + viewId+"_"+tabViewId).html($('<center><i class="icon-spinner icon-spin blue bigger-250"></i></center>'));
		$("#viewId_" + viewId+"_"+tabViewId).load(viewUrl, toParamUrl());
	}

	function reRunAnalysis(e) {
		var daViewId = $(e).data("daview-id");
		var tabViewId = $(e).data("tab-view-id");
		$("#daview_edit_modal_body_div").html("<center><i class='icon-spinner icon-spin blue bigger-250'></i></center>");
		$("#daview_edit_modal_body_div").load("${ctxbi}/view/da/" + daViewId + "/edit", {
			//daViewId : daViewId
			"daViewId" : daViewId, "tabViewId" : tabViewId
		});
		$("#daview_edit_modal").modal();
	}

	function collapseView(e) {
		var status = $(e).attr("status");
		if (status == "show") {
			$(e).attr("title", "<spring:message code='dashboard.tooltip.show'/>");
			$(e).attr("status", "hide");
		} else {
			$(e).attr("title", "<spring:message code='dashboard.tooltip.hide'/>");
			$(e).attr("status", "show");
		}
	}
	
	function stopPropagation(e){
		e.stopPropagation();
	}
	
	function clearInvokeParams(){
			invokeParams.testName = "";
			invokeParams.extAtt = {};
		}
</script>
<input type="hidden" name="patientCount" id="patientCount"
	value="${dashBoardShow.study.patientCount}">
</html>