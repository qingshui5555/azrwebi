<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" buffer="1024kb"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>

<div class="row">
	<div class="col-xs-12">
		<spring:message code="biomarker.config.measuretype" />
		：<select id="measureSelect">
			<c:forEach items="${biomarkerTestConfigMap}" var="map" varStatus="vs">
				<option value="${vs.index}" data-type="${map.key}">${map.key}</option>
			</c:forEach>
		</select>
	</div>
</div>

<br>

<form id="configForm">
	<c:forEach items="${biomarkerTestConfigMap}" var="biomarkerMap" varStatus="vs">
		<table id="measure_${vs.index}" data-type="${biomarkerMap.key}"
			class="table table-striped table-bordered table-hover">
			<thead>
				<tr>
					<th><spring:message code="biomarker.config.choose" /></th>
					<th><spring:message code="biomarker.config.biomarker" /></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${biomarkerMap.value}" var="biomarker">
					<tr>
						<td><input type="checkbox" value="${biomarker}"></td>
						<td>${biomarker}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:forEach>

</form>

<script type="text/javascript">
	var configJson = ${biomarkerTestConfigJson};
	
	var selectMeasureArr=new Array();
	
	if (configJson != '') {

		var measureType = configJson.measureType;

		var measureData = configJson.measureData==undefined?new Array:configJson.measureData;

		$("#measureSelect option[data-type='"+measureType+"']").attr('selected',true);
		
		for(var i in measureData){
			var data=measureData[i];
			$("table[data-type='"+measureType+"']").find(":checkbox[value='"+data+"']").attr('checked',true);
		}
		
		var selectMeasure=new Object();
		selectMeasure.name=measureType;
		selectMeasure.data=measureData;
		selectMeasureArr.push(selectMeasure);
	}

	
	$(function() {
		
		var measureSelectValue=$("#measureSelect").val();
		
		
		$('#configForm table').dataTable({
			"oLanguage" : dataTableLanguage
		});
		
		initBiomarkerTable(measureSelectValue);
		
		
		$("#measureSelect").change(function(){
			initBiomarkerTable(this.value)
		})
		

		$("#configForm table").on('click', ':checkbox', function() {
			var dataType=$(this).parents("table").attr("data-type");
			var value=$(this).val();
			
			if ($(this).is(':checked')) {
				
				pushMeasureData(value,dataType);
				
			} else {
				deleteMeasureData(value,dataType);
			}
			
		})

	})

	function initBiomarkerTable(index){
		$('#configForm div[id$="_wrapper"]').hide();
		
		$('#measure_'+index+'_wrapper').show(); 
		
	}
	
	function pushMeasureData(value,dataType){
			
		var flag=false;
		
		for(var i in selectMeasureArr){
			if(selectMeasureArr[i].name==dataType){
				selectMeasureArr[i].data.push(value);
				flag=true;
				break;
			}
		}
		
		if(!flag){
			var selectMeasure=new Object();
			selectMeasure.name=dataType;
			selectMeasure.data=new Array();
			selectMeasure.data.push(value);
			selectMeasureArr.push(selectMeasure);
		}
		
	}
	
	function deleteMeasureData(value,dataType){
		
		for(var i in selectMeasureArr){
			var selectMeasure=selectMeasureArr[i];

			if(selectMeasure.name==dataType){
				for(var a in selectMeasure.data){
					var measure=selectMeasure.data[a];
					if (measure == value) {
						selectMeasure.data.splice(a, 1);
						break;
					}
				}
			}
		}
	}
	
	function getMeasureData(measureType){
		
		var dataArray=new Array();
		
		for(var i in selectMeasureArr){
			var selectMeasure=selectMeasureArr[i];

			if(selectMeasure.name==measureType){
				dataArray=selectMeasure.data;
				break;
			}
		}
		return dataArray;
	}

	submitConfig = function() {
		var configJsonObj = new Object();
		
		var measureType=$("table[id^='measure']:visible").attr('data-type');
		var measureData=getMeasureData(measureType);
		
		configJsonObj.measureType=measureType;
		configJsonObj.measureData=measureData;
		
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
