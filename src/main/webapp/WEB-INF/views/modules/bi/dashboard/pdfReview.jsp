<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" buffer="1024kb"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:import url="/bi/layout/resource" />
<title><spring:message code="layout.head.analytics" /></title>
</head>
<body>
	<div class="main-container">
		<c:forEach items="${dashBoardShow.showTabList}" var="showTab"
			varStatus="tabSta">
			<div class="row hide">
				<div class="col-sm-12 text-left">
					<h4 class="">${tabSta.index+1}、&nbsp;${showTab.tab.name }</h4>
				</div>
			</div>
			<c:forEach items="${showTab.viewList }" var="view"
				varStatus="viewSta">
				<div class="row">
					<div class="col-sm-12">
						<div class="widget-box transparent">
							<div class="widget-header widget-header-flat">
								<h4 class="lighter">
									<i class="icon-dashboard"></i> ${view.viewName }
								</h4>
							</div>
							<div class="widget-body">
								<div class="widget-main padding-4" data-viewId="${view.viewId }"
									name="div_view" data-tabId="${showTab.tab.id}">
									<i class='icon-spinner icon-spin blue bigger-250'></i>
								</div>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
		</c:forEach>
	</div>
</body>
<script type="text/javascript">
	$(function() {
		var url = window.location.href;
		var paramStr = url.substring(url.indexOf("?"));
		var viewUrl = "${ctxbi}/view/";
		$("div[name='div_view']").each(function(i, n) {
			var viewId = $(this).attr("data-viewId");
			
			paramStr=paramStr.replace(/tabId=\w*\&/,"tabId="+$(this).attr("data-tabId")+"&");
			console.log(paramStr);
			console.log($(this).attr("data-tabId"));
			$(this).load(viewUrl + viewId + paramStr);
		}); 
		//alert(paramStr);
	});

	var requestParam = {
		studyId : '${dashBoardShow.study.guid}',
		cohorts : [],
		groups : [],
		patientId : '',
		tabId : '',
		viewId : ''
	};

	function toParamUrl() {
		var urlParam = "studyId=#[studyId}&patientId=#[patientId}&tabId=#[tabId}&viewId=#[viewId}#[cohorts}#[groups}";
		urlParam = urlParam.replace("#[studyId}", requestParam.studyId);
		urlParam = urlParam.replace("#[patientId}", requestParam.patientId);
		urlParam = urlParam.replace("#[tabId}", requestParam.tabId);
		urlParam = urlParam.replace("#[viewId}", requestParam.viewId);
		var cohorts = "";
		$.each(requestParam.cohorts, function(i, n) {
			cohorts += "&cohorts=" + n;
		});
		if (cohorts == "") {
			cohorts = "&cohorts=";
		}
		urlParam = urlParam.replace("#[cohorts}", cohorts);

		var groups = "";
		$.each(requestParam.groups, function(i, n) {
			groups += "&groups=" + n;
		});
		if (groups == "") {
			groups = "&groups=";
		}

		urlParam = urlParam.replace("#[groups}", groups);
		urlParam += "&patientCount=" + $("#patientCount").val();
		return urlParam;
	}

	console.log(requestParam);
</script>
</html>