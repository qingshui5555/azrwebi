<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" buffer="1024kb"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>
<script src="${ctxStatic }/echarts/${style }.js"></script>
<script src="${ctxStatic }/assets/js/dataTool.js"></script>
<c:set value="<%=(int)(Math.random()*1000) %>" var="biomarker_box_plot_random" />

<center id="biomarker_box_plot_spin_${biomarker_box_plot_random}">
	<i class='icon-spinner icon-spin blue bigger-250'></i>
</center>

<c:if test="${fn:length(geneObjArr)==0&&fn:length(labTestArr)==0}">
	<center><spring:message code='dashboard.data.empty'/></center>
	<script>
		var filter_widget_main_str_${biomarker_box_plot_random} = "";
	</script>
</c:if>

<c:if test="${fn:length(geneObjArr)>0}" >
<div id="biomarker_chart_${biomarker_box_plot_random}" style="height: ${empty viewChartHeight?600:viewChartHeight}px;">
	<div class="col-sm-12">
		<div id="biomarker_div_${biomarker_box_plot_random}" style="height: ${empty viewChartHeight?600:viewChartHeight}px;"></div>
	</div>
</div>
<script>
	var filter_widget_main_str_${biomarker_box_plot_random} =
		 '<div class="row" id="biomarker_filter_div_${biomarker_box_plot_random}">'
		+'	<div class="col-xs-12">'
 		+'		<h5 class="header smaller lighter blue no-margin">'
		+'			<spring:message code="labtest.filter.indicator.biomarker" />'
		+'			:'
		+'		</h5>' 
		+'		<div class="">'
					<c:forEach items="${geneObjArr }" var="genObj">
		+'			<div class="radio"><label>'
		+'				<input type="radio" data-type="genObj" class="ace" '
		+'					value="${genObj.name }_${genObj.resultType}" '
		+'					name="biomarker_radio_${biomarker_box_plot_random}" '
		+'					style="margin-top: 1px; margin-right: 5px;">'
		+'				<span title="${genObj.name }" class="lbl">${fn:substring(genObj.name, 0, 16)}${fn:length(genObj.name)>16?'...':''}</span>'
		+'			</label></div>'
					</c:forEach>
		+'		</div>'
		+'	</div>'
		+'</div>';
</script>
</c:if>

<c:if test="${fn:length(labTestArr)>0}">
<div id="lab_test_chart_${biomarker_box_plot_random}" style="height: ${empty viewChartHeight?600:viewChartHeight}px;">
	<div class="col-sm-12">
		<div id="lab_test_div_${biomarker_box_plot_random}" style="height: ${empty viewChartHeight?600:viewChartHeight}px;"></div>
	</div>
</div>
<script>
	var filter_widget_main_str_${biomarker_box_plot_random} =
		 '<div class="row" id="lab_test_filter_div_${biomarker_box_plot_random}">'
		+'	<div class="col-xs-12">'
 		+'		<h5 class="header smaller lighter blue no-margin"><spring:message code="labtest.filter.indicator.labtest"/>:</h5>'
 		+'		<div class="">'
					<c:forEach items="${labTestArr }" var="labTest">
		+'			<div class="radio"><label>'
		+'				<input type="radio" class="ace" data-type="labTest" value="${labTest }"'
		+'					name="lab_test_radio_${biomarker_box_plot_random}"'
		+'					style="margin-top: 1px; margin-right: 5px;">'
		+'				<span title="${labTest }" class="lbl">${fn:substring(labTest, 0, 16)}${fn:length(labTest)>16?'...':''}</span>'
		+'			</label></div>'
					</c:forEach>
		+'		</div>'
		+'	</div>'
		+'</div>';
</script>

</c:if>

<c:forEach items="${patientGroupNameMap}" var="map">
	<input type="hidden" id="pateint_group_name_${biomarker_box_plot_random}_${map.key}" value="${map.value }" />
</c:forEach>

<script>
	var selected_value;
	var selected_text;
	$(function(){
		var $filter_widget_main_${biomarker_box_plot_random} = $("#biomarker_box_plot_div_${biomarker_box_plot_random}").closest(".widget-box").find(".widget-main:first");
        $filter_widget_main_${biomarker_box_plot_random}.html(filter_widget_main_str_${biomarker_box_plot_random});
        $filter_widget_main_${biomarker_box_plot_random}.closest('ul.dropdown-menu')
			.on('click', '[data-stopPropagation]', function (e) {
			    e.stopPropagation();
			});

		<c:if test="${fn:length(geneObjArr)==0&&fn:length(labTestArr)==0}">
			$("#biomarker_box_plot_spin_${biomarker_box_plot_random}").hide();
		</c:if>
		
		
		$("#biomarker_filter_div_${biomarker_box_plot_random} :radio").click(function(){
			$("#biomarker_box_plot_spin_${biomarker_box_plot_random}").show();
			$("#biomarker_div_${biomarker_box_plot_random}").html("");
			var type = $(this).attr("data-type");
			var value = $(this).val();

			var	titleText = 'Biomarker Box Plot(' + value.split("_")[1] + ')';
			var yAxisName = value.split("_")[1];
			$.post("${ctxbi}/dfView/biomarkerBox/getData", {
				"studyId": requestParam.studyId,
				"patientGroupIdArr": ${patientGroupIdArr},
				"type": type,
				"value": value
			}, function(data) {
				if(data.success){
                    selected_value = value.split("_")[0];
                    selected_text = value.split("_")[1];
                    init_box_plot_data_${biomarker_box_plot_random}(data.scoreList, titleText, yAxisName, "biomarker_div_${biomarker_box_plot_random}",'biomarker', value.split("_")[1]);
					$("#biomarker_box_plot_spin_${biomarker_box_plot_random}").hide();
				}
			}) 
		})
		
		$("#biomarker_filter_div_${biomarker_box_plot_random} :radio:first").click();
		
		$("#lab_test_filter_div_${biomarker_box_plot_random} :radio").click(function(){
			$("#biomarker_box_plot_spin_${biomarker_box_plot_random}").show();
			$("#lab_test_${biomarker_box_plot_random}").html("");
			
			var type = $(this).attr("data-type");
			var value = $(this).val();
			
			var	titleText = 'Lab Test Plot(' + value + ')';
			var	yAxisName = value;
			$.post("${ctxbi}/dfView/biomarkerBox/getData", {
				"studyId" : requestParam.studyId,
				"patientGroupIdArr": ${patientGroupIdArr},
				"type": type,
				"value": value
			}, function(data) {
				if(data.success){
                    selected_value = value.split("_")[0];
                    selected_text = value.split("_")[1];
                    init_box_plot_data_${biomarker_box_plot_random}(data.scoreList, titleText, yAxisName, "lab_test_div_${biomarker_box_plot_random}", 'labTest', value);
					$("#biomarker_box_plot_spin_${biomarker_box_plot_random}").hide();
				}
			}) 
		})
		
		$("#lab_test_filter_div_${biomarker_box_plot_random} :radio:first").click();
	})
	
	function init_box_plot_data_${biomarker_box_plot_random}(scoreList, titleText, yAxisName, divId, type, resultType){
		var patientGroupIdArr = ${patientGroupIdArr};
		var xAxis = new Array();
		var boxplotData = new Array();
		var dataArr = new Array();
		
		for(var i in patientGroupIdArr){
			boxplotData.push(new Array());
			xAxis.push($("#pateint_group_name_${biomarker_box_plot_random}_" + patientGroupIdArr[i]).val());
		}

		for(var i in scoreList){
			var  groupids = scoreList[i].groupids;
			for(var a=0; a<patientGroupIdArr.length; a++){
				if(groupids.indexOf(patientGroupIdArr[a]) != -1){
					boxplotData[a].push(scoreList[i].score);
					dataArr.push([a,scoreList[i].score,scoreList[i].code]);
				}
			}
		}
		init_box_plot_chart_${biomarker_box_plot_random}(xAxis, boxplotData, dataArr, titleText, yAxisName, divId, type, resultType);
	}
	
	
	function init_box_plot_chart_${biomarker_box_plot_random}(xAxis, boxplotData, dataArr, titleText, yAxisName, divId, type, resultType){
		var biomarker_box_plot_chart = echarts.init(document.getElementById(divId),'myStyle');
		var data = echarts.dataTool.prepareBoxplotData(boxplotData);
		var boxDataArray = new Array();
		for(var i=0; i<data.boxData.length; i++){
			var tmp = new Array();
			for(var j=0; j<data.boxData[i].length; j++){
				var floorValue = Math.round(data.boxData[i][j]*100)/100;
				tmp.push(floorValue);
			}
			boxDataArray.push(tmp);
		}
		var biomarker_box_plot_option = {
			title: [{
				text: titleText,
				left: 'center'
			}],
			tooltip: {
				trigger: 'item',
				axisPointer: {
					type: 'shadow'
				}
			},
			grid: {
				left: '10%',
				right: '10%',
				bottom: '15%'
			},
			xAxis: {
				type: 'category',
				data: xAxis,
				boundaryGap: true,
				nameGap: 30,
				splitArea: {
					show: false
				},
				axisLabel: {
					formatter: function(param){
						var result = '';
						for(var i=0,len=param.length; i<len; i++){
						    result += param[i];
						    if(i > 14 && i % 14 == 1){ 
						    	result += '\n';
						    }
						}
						return result;
					}
				},
				splitLine: {
					show: false
				}
			},
			yAxis : {
				type: 'value',
				name: yAxisName,
				splitArea: {
					show: true
				}
			},
			series: [{
				name: 'boxplot',
				type: 'boxplot',
				data: boxDataArray,
				z: 0,
				tooltip: {
					formatter: function(param) {
					    return [ '<spring:message code="biomarker.boxplot.experiment"/> ' + param.name + ': ', '<spring:message code="biomarker.boxplot.upper"/>: ' + param.data[4].toFixed(2), 'Q1: ' + param.data[1].toFixed(2), '<spring:message code="biomarker.boxplot.median"/>: ' + param.data[2].toFixed(2), 'Q3: ' + param.data[3].toFixed(2), '<spring:message code="biomarker.boxplot.lower"/>: ' + param.data[0].toFixed(2) ].join('<br/>')
					}
				}
			},{
				name: selectedValue,
				type: 'scatter',
				data: dataArr,
				symbolSize: 7,
				itemStyle: {
					normal: {
						color : "#478fca"
					}
				},
				tooltip: {
					formatter: function(param) {
						var formatterStr = "";
						if(type == 'biomarker'){
							formatterStr += '<spring:message code="biomarker.boxplot.biomarker"/>:' + param.seriesName + '<br>';
						} else {
							formatterStr += '<spring:message code="biomarker.boxplot.labtest"/>:' + param.seriesName + '<br>';
						}
						formatterStr += '<spring:message code="biomarker.boxplot.cohort"/>:' + param.name + '<br>';
						var paramData = param.data;
						var currentIndex = 0;
						formatterStr += '<spring:message code="biomarker.boxplot.patientcode"/>:';
						var patientCodeArray = new Array();
						for(var i=0; i<dataArr.length; i++){
							var currentArr = dataArr[i];
							if(currentArr[0] == paramData[0] && currentArr[1] == paramData[1]){
							    formatterStr += currentArr[2];
								currentIndex++;
								if(currentIndex%5 == 0){
									formatterStr += '<br>';
								} else {
									formatterStr += ',';
								}
							}
						}
						if(formatterStr.charAt(formatterStr.length - 1)==','){
							formatterStr = formatterStr.substring(0,formatterStr.length-1);
							formatterStr += "<br>";
						}
						formatterStr += resultType + ":" + param.value[1];
						return formatterStr;
					}
				}
			}]
		};

        biomarker_box_plot_chart.setOption(biomarker_box_plot_option);
	}
</script>