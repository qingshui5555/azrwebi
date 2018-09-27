<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" buffer="1024kb"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>
<script src="${ctxStatic }/echarts/${style }.js"></script>
<c:set value="<%=(int) (Math.random() * 1000)%>" var="survival_curve_random" />

<div id="filter_${survival_curve_random}"></div>

<center id="survival_curve_spin_${survival_curve_random}">
	<i class='icon-spinner icon-spin blue bigger-250'></i>
</center>

<div class="row">
	<div class="col-xs-12" id="survival_curve_div_${survival_curve_random}" style="height:${empty viewChartHeight?600:viewChartHeight}px"></div>
</div>

<div class="row" id="survival_curve_table_div_${survival_curve_random}"></div>

<script>
	var filter_widget_main_str_${survival_curve_random} =
		 '<div class="row">'
		+'	<div class="col-xs-12">'
		+'		<h5 class="header smaller lighter blue no-margin">'
		+'			<spring:message code="survival.curve.filter.title.type"/>'
		+'			:'
		+'		</h5>' 
		+'		<div class="orderByGroup">'
		+'			<select id="countType_${survival_curve_random}">'
		+'				<option value=""><spring:message code="survival.curve.option.survival"/></option>'
		+'				<option value="<spring:message code="survival.curve.option.first.pd.value"/>"><spring:message code="survival.curve.option.first.pd"/></option>'
		+'			</select>'
		+'		</div>'
		+'	</div>'
		+'	<div id="timeTypeDiv_${survival_curve_random}" class="col-xs-12">'
		+'		<h5 class="header smaller lighter blue no-margin">'
		+'			<spring:message code="survival.curve.filter.title.time"/>'
		+'			:'
		+'		</h5>' 
		+'		<div class="orderByGroup">'
		+'			<select id="timeType_${survival_curve_random}">'
        +'				<option value="<spring:message code="survival.curve.option.year.value"/>"><spring:message code="survival.curve.option.year"/></option>'
		+'				<option value="<spring:message code="survival.curve.option.month.value"/>"><spring:message code="survival.curve.option.month"/></option>'
		+'			</select>'
		+'		</div>'
		+'	</div>'
		+'</div>'
        +'<div class="space-8"></div>'
        +'<div class="row">'
        +'	<div class="col-xs-12">'
        +'		<button id="search_button" onclick="init_survival_curve_${survival_curve_random}()" type="button" class="btn btn-purple btn-sm"><spring:message code="tab.update"/></button>'
        +'	</div>'
        +'</div>';

	$(function(){
		var $filter_widget_main_${survival_curve_random} = $("#filter_${survival_curve_random}").closest(".widget-box").find(".widget-main:first");
		$filter_widget_main_${survival_curve_random}.html(filter_widget_main_str_${survival_curve_random});
		$filter_widget_main_${survival_curve_random}.closest('ul.dropdown-menu')
            .on('click', '[data-stopPropagation]', function (e) {
                e.stopPropagation();
            });
		
		init_survival_curve_${survival_curve_random}();

	});
	
	function init_survival_curve_${survival_curve_random}(){
        $("#survival_curve_spin_${survival_curve_random}").html("<i class='icon-spinner icon-spin blue bigger-250'></i>");
        $("#survival_curve_spin_${survival_curve_random}").show();
        $("#survival_curve_div_${survival_curve_random}").hide();
        $("#survival_curve_table_div_${survival_curve_random}").hide();

		var countType = $("#countType_${survival_curve_random}").val();
		var timeType = $("#timeType_${survival_curve_random}").val();
        var curveTitle='';
        var curveXAxis='';
        var curveYAxis='';
        if(countType=='<spring:message code="survival.curve.option.first.pd.value"/>') {
            curveTitle = '<spring:message code="survival.curve.title.first.pd"/>';
            curveYAxis = '<spring:message code="survival.curve.axis.y.first.pd"/>';
        } else {
            curveTitle = '<spring:message code="survival.curve.title.five.year"/>';
            curveYAxis = '<spring:message code="survival.curve.axis.y.rate"/>';
        }

        if(timeType == '<spring:message code="survival.curve.option.month.value"/>'){
            curveXAxis = '<spring:message code="survival.curve.axis.x.cycle"/>(<spring:message code="survival.curve.option.month"/>)';
        } else {
            curveXAxis = '<spring:message code="survival.curve.axis.x.cycle"/>(<spring:message code="survival.curve.option.year"/>)';
        }

		$.post("${pageContext.request.contextPath}/bi/dfView/survivalCurve/getData", {
		    "studyId" : "${studyId}",
		    "cohorts" : "${cohortIds}",
		    "countType" : countType,
		    "timeType" : timeType,
            "allPatientsFlag" : ${allPatientsFlag}
		}, function(data){
            if(data.success){
                $("#survival_curve_spin_${survival_curve_random}").hide();
                $("#survival_curve_div_${survival_curve_random}").show();

				var legend_data = new Array();
				var xAxis_data = new Array();
				var series_data = new Array();
				var survival_curve_table_${survival_curve_random} = '<table style="margin-left:11%;width:92%">';
				var survival_curve_table_th_${survival_curve_random} = '<tr></tr>';
				var survival_curve_table_tr_${survival_curve_random} = '';
				for(var i = 0; i < data.survivalCurveDataList.length; i++){
					var scData = data.survivalCurveDataList[i];
					legend_data.push(scData['name']);
					survival_curve_table_tr_${survival_curve_random} += '<tr>';

					var item = new Object();
					item.type = 'line';
					item.step = true;
					item.symbolSize = 10;
					item.name = scData['name'];
					item.data = new Array();
					var width = (100.0/(scData['number']+1)).toFixed(2);
					for(var j=0; j<scData['number'] + 1; j++ ){
						var value_name = 'survivalPer_' + j;
						var death_name = 'death_' + j;

						if(i == 0) {
							xAxis_data.push(j);
							if(j == 0){
								survival_curve_table_th_${survival_curve_random} += '<td style=" width:'+ width +'%;border-top: none; word-break:break-all; overflow:hidden;">'+curveYAxis+'</td>';
							} else {
								survival_curve_table_th_${survival_curve_random} += '<td style=" width:'+ width +'%;border-top: none; word-break:break-all; overflow:hidden;">'+death_name+'</td>';
							}
						}

						var node = new Object();
						node.total = scData['survivalSum'];
						node.year = j;
						if(j == 0){
							node.value = 0;
							node.death = 0;
							survival_curve_table_tr_${survival_curve_random} += '<td style=" width:'+ width +'%;border-top: none; word-break:break-all; overflow:hidden;">' + scData['name'] + '</td>';
						} else {
							node.value = scData[value_name];
							node.death = scData[death_name];
							survival_curve_table_tr_${survival_curve_random} += '<td style=" width:'+ width +'%;border-top: none; word-break:break-all; overflow:hidden;">' + scData[value_name] + '%</td>';
						}
						item.data.push(node);
					}
					series_data.push(item);
					survival_curve_table_tr_${survival_curve_random} += '</tr>';
				}

				var survival_curve_option_${survival_curve_random} = {
					title: {
						text: curveTitle,
						left: 'center'
					},
					tooltip: {
						show: <c:if test="${isPad eq '1'}">false</c:if><c:if test="${isPad ne '1'}">true</c:if>,
						formatter: function(params){
							return '<spring:message code="survival.curve.tooltip.total"/>:'+ params.data.total + '<br>' +
								'<spring:message code="survival.curve.tooltip.death.number"/>:' + params.data.death + '<br>' +
								'<spring:message code="survival.curve.axis.y.rate"/>:' + params.data.value + '%<br>'+
								curveXAxis + ':' + params.data.year;
						}
					},
					legend: {
						data: legend_data,
						left: 'left',
						top: 30
					},
					grid: {
						left: '10%',
						top: 100,
						bottom: '3%',
						containLabel: true
					},
					xAxis: [{
						type: 'category',
						boundaryGap: false,
						name: curveXAxis,
						data: xAxis_data,
					}],
					yAxis: {
						type: 'value',
						max: '100',
						min: 'dataMin',
						name: curveYAxis+'(%)',
						splitLine: {
							show: false
						}
					},
					series: series_data
				};
				var survival_curve_chart_${survival_curve_random} = echarts.init(document.getElementById('survival_curve_div_${survival_curve_random}'),'myStyle');
				survival_curve_chart_${survival_curve_random}.setOption(survival_curve_option_${survival_curve_random}, true);


                survival_curve_table_${survival_curve_random} += survival_curve_table_th_${survival_curve_random} + survival_curve_table_tr_${survival_curve_random} + '</table>';
                $("#survival_curve_table_div_${survival_curve_random}").html($(survival_curve_table_${survival_curve_random}));
                $("#survival_curve_table_div_${survival_curve_random}").show();
			} else {
                $("#survival_curve_table_div_${survival_curve_random}").hide();
                $("#survival_curve_div_${survival_curve_random}").hide();
                $("#survival_curve_spin_${survival_curve_random}").show();
                $("#survival_curve_spin_${survival_curve_random}").html("<spring:message code='dashboard.data.empty'/>");
			}
		});
	}

</script>