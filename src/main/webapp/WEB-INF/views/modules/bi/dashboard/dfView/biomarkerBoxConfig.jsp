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

	<table id="geneTable"
		class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<th><spring:message code="biomarker.boxplot.choose" /></th>
				<th><spring:message code="biomarker.boxplot.name" /></th>
				<th><spring:message code="biomarker.boxplot.type" /></th>
				<th><spring:message code="biomarker.boxplot.intensityType" /></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${biomarkerTestMap}" var="map">
				<tr class="biomarkerTestClass">
					<td><input type="checkbox" data-id="${map.key}"></td>
					<td data-id="${map.key}_key">${map.key}</td>
					<td><spring:message code="biomarker.boxplot.biomarker"/></td>
					<td><select data-id="${map.key}_value">
							<c:forEach items="${map.value}" var="result">
								<option data-value="${map.key}_${result}">${result }</option>
							</c:forEach>
					</select></td>
				</tr>
			</c:forEach>

			<c:forEach items="${indicatorList}" var="indicator" varStatus="vs">
				<tr class="labTestClass">
					<td><input type="checkbox" data-id="${vs.index}"
						data-value="${indicator}"></td>
					<td data-id="${vs.index }_key">${indicator}</td>
					<td><spring:message code="biomarker.boxplot.labtest"/></td>
					<td>${indicator}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</form>

<script type="text/javascript">
	var patientGroupIdArr = new Array();

	var geneObjArr = new Array();

	var labTestArr = new Array();
	var configJson = ${biomarkerBoxConfigJson};

	if (configJson != '') {

		var datasourceConfigJsonObj = configJson;

		patientGroupIdArr = datasourceConfigJsonObj.patientGroupIdArr == undefined ? new Array() : datasourceConfigJsonObj.patientGroupIdArr;

		geneObjArr = datasourceConfigJsonObj.geneObjArr == undefined ? new Array() : datasourceConfigJsonObj.geneObjArr;

		labTestArr = datasourceConfigJsonObj.labTestArr == undefined ? new Array() : datasourceConfigJsonObj.labTestArr;

		for ( var i in patientGroupIdArr) {
			$(":checkbox[value='" + patientGroupIdArr[i] + "']").attr("checked", true);
		}

		for ( var i in geneObjArr) {
			$(".biomarkerTestClass :checkbox[data-id='" + geneObjArr[i].name + "']").attr("checked", true);
			$("option[data-value='" + geneObjArr[i].name + "_" + geneObjArr[i].resultType + "']").attr("selected", true);
		}

		for ( var i in labTestArr) {
			$(".labTestClass :checkbox[data-value='" + labTestArr[i] + "']").attr("checked", true);
		}
	}

	$(function() {

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

		$("#geneTable").on('click', '.biomarkerTestClass td :checkbox', function() {
			var id = $(this).attr('data-id');
			if ($(this).is(':checked')) {
				geneObjArr.push(initGeneObj(id));
			} else {
				for ( var i in geneObjArr) {
					if (geneObjArr[i].name == id) {
						geneObjArr.splice(i, 1);
						break;
					}
				}
			}
		})

		$("#geneTable").on('change', '.biomarkerTestClass td select', function() {
			var id = $(this).attr("data-id").split("_")[0];
			if ($(".biomarkerTestClass :checkbox[data-id='" + id + "']").is(':checked')) {
				for ( var i in geneObjArr) {
					if (geneObjArr[i].name == id) {
						geneObjArr.splice(i, 1);
						break;
					}
				}
				geneObjArr.push(initGeneObj(id));
			}
		})

		$("#geneTable").on('change', '.labTestClass td :checkbox', function() {
			var id = $(this).attr('data-id');
			var indicator = initLabTest(id)
			if ($(this).is(':checked')) {
				labTestArr.push(indicator);
			} else {
				for ( var i in labTestArr) {
					if (labTestArr[i] == indicator) {
						labTestArr.splice(i, 1);
						break;
					}
				}
			}
		})

		var patientGroupTable = $('#patientGroupTable').dataTable({
			"oLanguage" : dataTableLanguage
		});

		var geneTable = $('#geneTable').dataTable({
			"oLanguage" : dataTableLanguage
		});
	})

	function initGeneObj(id) {
		var geneObj = new Object();
		geneObj.name = $(".biomarkerTestClass td[data-id='" + id + "_key']").html();
		geneObj.resultType = $(".biomarkerTestClass select[data-id='" + id + "_value']").val();
		return geneObj;
	}

	function initLabTest(id) {
		return $(".labTestClass td[data-id='" + id + "_key']").html();
	}

	submitConfig = function() {
		var configJsonObj = new Object();
		if (patientGroupIdArr.length == 0 || (geneObjArr.length == 0 && labTestArr.length == 0)) {

			if (patientGroupIdArr.length==0)
				$.simplyToast('<spring:message code="biomarker.boxplot.nopatientGroup"/>', 'danger');
			else
				$.simplyToast('<spring:message code="biomarker.boxplot.nogeneorlabtest"/>', 'danger');
			return;
		}
		
		if(geneObjArr.length >1 && labTestArr.length >1 ){
			$.simplyToast('<spring:message code="biomarker.boxplot.two"/>', 'danger');
			return;
		}

		configJsonObj.patientGroupIdArr = patientGroupIdArr;
		configJsonObj.geneObjArr = geneObjArr;
		configJsonObj.labTestArr = labTestArr;
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
