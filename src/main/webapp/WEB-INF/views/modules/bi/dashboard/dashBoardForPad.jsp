<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" buffer="1024kb"%>
<%@ taglib prefix="shiro" uri="/WEB-INF/tlds/shiros.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<%@ taglib prefix="fnc" uri="/WEB-INF/tlds/fnc.tld" %>
<%@ taglib prefix="sys" tagdir="/WEB-INF/tags/sys" %>
<%@ taglib prefix="act" tagdir="/WEB-INF/tags/act" %>
<%@ taglib prefix="cms" tagdir="/WEB-INF/tags/cms" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}/"/>
<c:set var="ctxbi" value="${pageContext.request.contextPath}/bi"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>

<%@ taglib prefix="fnbi" uri="/WEB-INF/tlds/bi.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="layout.head.analytics" /></title>
<c:import url="/bi/layout/resource" />
	<link rel="stylesheet" href="${ctxStatic }/assets/css/skin_black.css" />
	<script src="${ctxStatic }/echarts/black.js"></script>
<%@ include
	file="/WEB-INF/views/modules/bi/da/daValidInputDataModal.jsp"%>
<script type="text/javascript" src="${ctxStatic}/hammer/hammer.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/hammer/jquery.hammer.js"></script>
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

.carousel-indicators{
    display: none;
}

#dashboardCarousel .left{
	top: -49px;
	right: 55px;
	left: auto;
	border-radius: 4px;
	border: 1px solid gray;
    width: 30px;
	height: 30px;
    font-size: 18px;
    background-image: linear-gradient(to right,rgba(0,0,0,0.9) 0,rgba(0,0,0,0.5) 100%);
}

#dashboardCarousel .right{
	top: -49px;
	right: 24px;
	left: auto;
	border-radius: 4px;
	border: 1px solid gray;
	width: 30px;
	height: 30px;
	font-size: 18px;
	background-image: linear-gradient(to right,rgba(0,0,0,0.9) 0,rgba(0,0,0,0.5) 100%);
 }

.carousel-inner > .item {
    -webkit-transition: -webkit-transform .2s ease-in-out;
    -o-transition: -o-transform .2s ease-in-out;
    transition: transform .2s ease-in-out;
    display: block;
    height: 0;
    overflow-y: auto;
}

.carousel-inner>.active{
    height: 100%;
    overflow-y:visible;
}

.menu-toggler{
	display: none !important;
}
#btn-scroll-up{
	display: none;
}
</style>
</head>
<body>
	<div class="main-container" id="main-container">
		<div class="main-container-inner">
			<a class="menu-toggler" id="menu-toggler" href="#"> <span
				class="menu-text"></span>
			</a>
			<div class="main-content" style="margin-left: 0;">
				<div class="page-content">
					<!-- PAGE CONTENT BEGINS -->
					<div class="row" style="background: #424e5b;">
						<div class="col-xs-12">
							<div class="row" style="margin-top: 20px; margin-bottom: 20px;">
								<div class="col-xs-3">
									<div class="radio-inline no-padding">
										<label> <input name="search_type" value="all_patients"
											autocomplete="off" type="radio" checked class="ace">
											<span class="lbl"><spring:message
													code="dashboard.filter.search.all" /></span> <span
											class="text-primary patientCount">${dashBoardShow.study.patientCount }</span>
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
											<c:forEach items="${dashBoardShow.patientGroupList }"
												var="group">
												<option data-type="${group.groupType }"
													data-id="${group.id }"
													data-patient-count="${group.patinetCount }"
													name="cohordt_choose_select"
													value="${group.groupType},${group.id},${group.patinetCount}">${group.groupName }(${group.patinetCount })</option>
											</c:forEach>
										</select>
									</div>

								</div>
							</div>


							<!-- PAGE CONTENT BEGINS -->
							<div class="row">
								<div class="col-xs-12">
									<div id="dashboardCarousel" class="carousel slide" data-wrap="true" data-interval="false">
										<!-- 轮播（Carousel）指标 -->
										<ol class="carousel-indicators" style="top: 15px;height: 30px;">
											<c:forEach items="${dashBoardShow.showTabList}" var="showTab" varStatus="vs">
												<li data-tabId="${showTab.tab.id }"
														data-tabId="${showTab.tab.id }" data-target="#dashboardCarousel" data-slide-to="${vs.index}" class="<c:if test='${vs.index eq 0}'>active</c:if>"
														data-viewIds="<c:forEach items="${showTab.viewList }" var="view" varStatus="viewSta"><c:if test="${not viewSta.first}">,</c:if>${view.viewId }</c:forEach>"
														data-viewTypes="<c:forEach items="${showTab.viewList }" var="view" varStatus="viewSta"><c:if test="${not viewSta.first}">,</c:if>${view.viewType }</c:forEach>"
														data-viewNames="<c:forEach items="${showTab.viewList }" var="view" varStatus="viewSta"><c:if test="${not viewSta.first}">,,</c:if>${view.viewName }</c:forEach>"
														data-sorts="<c:forEach items="${showTab.viewList }" var="view" varStatus="viewSta"><c:if test="${not viewSta.first}">,</c:if>${view.sort }</c:forEach>"
														data-sizes="<c:forEach items="${showTab.viewList }" var="view" varStatus="viewSta"><c:if test="${not viewSta.first}">,</c:if>${view.size }</c:forEach>"
														data-aliases="<c:forEach items="${showTab.viewList }" var="view" varStatus="viewSta"><c:if test="${not viewSta.first}">,,</c:if>${view.alias }</c:forEach>"
														data-tabViewIds="<c:forEach items="${showTab.viewList }" var="view" varStatus="viewSta"><c:if test="${not viewSta.first}">,</c:if>${view.tabId }</c:forEach>"
													</li>
											</c:forEach>
										</ol>
										<div class="carousel-inner">
											<c:forEach items="${dashBoardShow.showTabList}" var="showTab" varStatus="vs">
												<div class="item <c:if test="${vs.index eq 0}">active</c:if>" id="carouselItem_${showTab.tab.id }"></div>
											</c:forEach>
										</div>
                                        <a class="carousel-control left" href="#myCarousel"
                                           data-slide="prev">&lsaquo;
                                        </a>
                                        <a class="carousel-control right" href="#myCarousel"
                                           data-slide="next">&rsaquo;
                                        </a>
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
					<h4 class="modal-title" id="myModalLabel">Data Analysis Edit</h4>
				</div>
				<div class="modal-body" id="daview_edit_modal_body_div"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">
						<spring:message code="dashboard.da.close" />
					</button>
					<button type="button" class="btn btn-primary"
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
		studyId : '${dashBoardShow.study.id}',
		cohorts : [],
		groups : [],
		patientId : '',
		tabId : '',
		viewId : '',
		allPatientsFlag : true,
		isPad: ${isPad}
	};

	//data annalysis reload时用于参数重载的数据模型对象
	var invokeParams = {
		testName : "",
		extAtt : {}
	}

	//各tab页的数据缓存对象
	var tabContentTemp = {};

	//cohort 多选控件对象
	var chosen_sel;

    function getCookie(name)
    {
        var name_ = name + "=";
        var cookiesArray = document.cookie.split(";");
        for(var i=0;i < cookiesArray.length;i++){
            var cookie = cookiesArray[i];
            var idx = cookie.indexOf(name_);
            if (idx != -1){
                //截取_change_style=字符串后的值
                return $.trim(cookie.substring(idx+name_.length,cookie.length));
            }
        }
        return null;
    }

	$(function() {
        var changeLocale = getCookie("_change_locale");
        if(changeLocale != "${locale}"){
            document.cookie = '_change_locale=${locale};path=/';
            document.cookie = '_change_style=black;path=/';
            location.reload();
        }

//		$('#dashboardCarousel').hammer().on('swipeleft', function(){
//			$(this).carousel('next');
//		});
//		$('#dashboardCarousel').hammer().on('swiperight', function(){
//			$(this).carousel('prev');
//		});

        $(".carousel-control.left").click(function(){
            $('#dashboardCarousel').carousel('prev');
        });
        $(".carousel-control.right").click(function(){
            $('#dashboardCarousel').carousel('next');
        });

		function loadChart(){
			var tabId = $("#dashboardCarousel .carousel-inner").find("div.active").attr("id").split("_")[1];
			var obj = $("#dashboardCarousel .carousel-indicators").find("li[data-tabid=" + tabId + "]");

			if($("#dashboardCarousel .carousel-inner").find("div.active").html().trim() != ''){
				return;
			}

			var tabId = $(obj).attr("data-tabId");

			requestParam.tabId = tabId;

			//由于名称和别名中可能含有单个逗号，这里如果用单个逗号分隔会出现问题
			var viewIds = $(obj).attr("data-viewIds").split(",");
			var viewNames = $(obj).attr("data-viewNames").split(",,");
			var viewTypes = $(obj).attr("data-viewTypes").split(",");
			var viewSorts = $(obj).attr("data-sorts").split(",");
			var viewSizes = $(obj).attr("data-sizes").split(",");
			var viewAliases = $(obj).attr("data-aliases").split(",,");
			var tabViewIds = $(obj).attr("data-tabViewIds").split(",");

			$("#carouselItem_" + tabId).html("");

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
						viewContent += '&nbsp;&nbsp;<a style="cursor: pointer;margin-right:4px;" onclick="reRunAnalysis(this)" title="<spring:message code="dashboard.tooltip.rerun"/>" data-daview-id="' + n + '">' + '<i class="icon-cog"></i>' + '</a>';
					}
					viewContent += '&nbsp;&nbsp;<a style="cursor: pointer;margin-right:4px;" onclick="refreshView(this)" title="<spring:message code="dashboard.tooltip.refresh"/>" data-view-type="' + viewType + '" data-view-id="' + n + '" data-tab-view-id="' + tabViewId + '">' + '<i class="icon-refresh"></i>' + '</a>'
							+ '&nbsp;&nbsp;<a style="cursor: pointer;margin-right:4px;" onclick="fullscreen(this)" title="<spring:message code="dashboard.tooltip.enlarge"/>" status="small">' + '<i class="icon-resize-full"></i>' + '</a>'
							+ '&nbsp;&nbsp;<a style="cursor: pointer;margin-right:4px;" onclick="collapseView(this)" title="<spring:message code="dashboard.tooltip.hide"/>" status="show" data-action="collapse">' + '<i class="icon-chevron-up"></i>' + '</a>'
					if(viewType == "0"){
						//下拉列表
						viewContent +=
								'&nbsp;&nbsp;<a data-toggle="dropdown" href="#" class="dropdown-toggle" ><i class="icon-filter"></i></a><ul class="dropdown-menu"><li data-stopPropagation="true" >'
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
					$("#carouselItem_" + tabId).append(viewContent);
				}
			});

			$("#carouselItem_" + tabId).find("div[name='tab_view_divs']").each(function() {
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
		}

		$("#dashboardCarousel").on("slid.bs.carousel", function(){
			loadChart();
		});

		$("#tab_ul li").on(
				"click",
				function(event) {
					$("#tab_ul li").removeClass("active");
					$(this).addClass("active");

					if (requestParam.tabId != undefined && requestParam.tabId != '') {
						$("#tabContent_" + requestParam.tabId).css({"height":"0","overflow-y":"auto"});
					}
					var tabId = $(this).find("a").attr("data-tabId");

					var tempDiv = $("#tabContent_" + tabId);
					if (tempDiv.length != 0) {
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
								viewContent += '<a style="cursor: pointer;margin-right:4px;" onclick="reRunAnalysis(this)" title="<spring:message code="dashboard.tooltip.rerun"/>" data-daview-id="' + n + '">' + '<i class="icon-cog"></i>' + '</a>';
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

		loadChart();

		chosen_sel = $('.chosen-select').chosen();

		$("input[name='search_type']").click(function() {
			if (testSearchType()) {
				requestParam.allPatientsFlag = true;
			} else {
				requestParam.allPatientsFlag = false;
			}
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
			$("#dashboardCarousel .carousel-inner").find("div.item").html("");
			loadChart();
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
			if (checkResult == true) {
				refulshExtendAtt();
				var daViewId = $("#daview_edit_modal_daview_id_hidden").val();
				var testName = $("#daview_edit_modal_test_name_hidden").val();
				var tabViewId = $(this).attr("data-tab-view-id");
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

		function isArrayEquals() {

		}
		
	})

	function toParamUrl() {
		var urlParam = "allPatientsFlag=#[allPatientsFlag}&studyId=#[studyId}&patientId=#[patientId}&tabId=#[tabId}&viewId=#[viewId}#[cohorts}#[groups}&isPad=#[isPad}";
		urlParam = urlParam.replace("#[allPatientsFlag}", requestParam.allPatientsFlag);
		urlParam = urlParam.replace("#[studyId}", requestParam.studyId);
		urlParam = urlParam.replace("#[patientId}", requestParam.patientId);
		urlParam = urlParam.replace("#[tabId}", requestParam.tabId);
		urlParam = urlParam.replace("#[viewId}", requestParam.viewId);
		urlParam = urlParam.replace("#[isPad}", requestParam.isPad);
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
			viewUrl = "${ctxbi}/view/" + viewId + "?tabViewId=" + tabViewId;;
		} else if (viewType == '2') {
			viewUrl = "${ctxbi}/view/da/" + viewId + "?tabViewId=" + tabViewId;;
		}
		$("#viewId_" + viewId+"_"+tabViewId).html($('<center><i class="icon-spinner icon-spin blue bigger-250"></i></center>'));
		$("#viewId_" + viewId+"_"+tabViewId).load(viewUrl, toParamUrl());
	}

	function reRunAnalysis(e) {
		var daViewId = $(e).data("daview-id");
		$("#daview_edit_modal_body_div").html("<center><i class='icon-spinner icon-spin blue bigger-250'></i></center>");
		$("#daview_edit_modal_body_div").load("${ctxbi}/view/da/" + daViewId + "/edit", {
			daViewId : daViewId
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
</script>
<input type="hidden" name="patientCount" id="patientCount"
	value="${dashBoardShow.study.patientCount}">
</html>