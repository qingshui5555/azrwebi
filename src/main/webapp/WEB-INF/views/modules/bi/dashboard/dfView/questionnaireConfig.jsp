<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" buffer="1024kb"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>
<form id="configForm">

	<table id="patientGroupTable"
		class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<th><spring:message code="biomarker.boxplot.choose" /></th>
				<th><spring:message code="biomarker.boxplot.name" /></th>
				<th><spring:message code="biomarker.boxplot.count" /></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${patientGroupList}" var="pg">
				<tr>
					<td><input type="checkbox" value="${pg.groupType==1?"C":"G" }_${pg.id}"></td>
					<td>${pg.groupName }</td>
					<td>${pg.patinetCount}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<table id="questionnaireTable"
		class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<th><spring:message code="questionnaire.th.choose" /></th>
				<th><spring:message code="questionnaire.th.type" /></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${questionnaireList}" var="questionnaire">
				<tr>
					<td><input type="checkbox" value="${questionnaire.name}"></td>
					<td>${questionnaire.name}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</form>

<script type="text/javascript">
	var patientGroupIdArr = new Array();
	var questionnaireArr = new Array();
	
	$(function() {
		
		<c:if test="${patientListSelected!=null}">//存在配置信息 勾选已经配置的cohort
		  <c:forEach var="patientId" items="${patientListSelected}">
		        patientGroupIdArr.push('${patientId}');
				$("#patientGroupTable :checkbox[value='${patientId}']").attr("checked", true);
		  </c:forEach>
		</c:if>
		  
		<c:if test="${nameListSelected!=null}">//存在配置信息 勾选已经配置的questionnaire
		  <c:forEach var="name" items="${nameListSelected}" >
		        questionnaireArr.push('${name}');
				$("#questionnaireTable :checkbox[value='${name}']").attr("checked", true);
		  </c:forEach>
		</c:if>
		
		var patientGroupTable = $('#patientGroupTable').dataTable({
			"oLanguage" : dataTableLanguage
		});
		var questionnaireTable = $('#questionnaireTable').dataTable({
			"oLanguage" : dataTableLanguage
		});
		
		$("#patientGroupTable").on('click', ':checkbox', function() {
			if ($(this).is(':checked')) {
				patientGroupIdArr.push($(this).val());
			} else {
				for ( var i in patientGroupIdArr) {
					if (patientGroupIdArr[i] == $(this).val()) {
						patientGroupIdArr.splice(i, 1);
						break;
					}
				}
			}
		})
		
		$("#questionnaireTable").on('click', ':checkbox', function() {
			if ($(this).is(':checked')) {
				questionnaireArr.push($(this).val());
			} else {
				for ( var i in questionnaireArr) {
					if (questionnaireArr[i] == $(this).val()) {
						questionnaireArr.splice(i, 1);
						break;
					}
				}
			}
		})
	})
	
	submitConfig = function() {
		var configJsonObj = new Object();
	
		if(questionnaireArr.length==0) {
			$.simplyToast('<spring:message code="questionnaire.viewList.questionnaireType"/>', 'info');
			return;
		}
		configJsonObj.patientGroupIdArr = patientGroupIdArr;
		configJsonObj.questionnaireArr = questionnaireArr;
		
		$.post("${ctx}/bi/sys/studyManagment/configTabView", {
			tabViewId : openModalTabViewId,
			alias : $("#alias").val(),
			viewChartHeight : $("#viewChartHeight").val(),
			configJson : JSON.stringify(configJsonObj).replace(/\"/g, "'")
		}, function() {
			$("#" + openModalTabViewId + "_td").html($("#alias").val());
			$.simplyToast('<spring:message code="viewList.success"/>', 'info');
			$("#configModal").modal('hide');
			location.reload();
		})
	}
</script>
