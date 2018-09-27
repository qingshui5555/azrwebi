<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" buffer="1024kb"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>
<script src="${ctxStatic }/echarts/${style }.js"></script>
<script src="${ctxStatic }/echarts/world.js"></script>
<style type="text/css">
    .seOption{
        max-width: 200px;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }
</style>

<c:set value="<%=(int)(Math.random()*1000) %>" var="world_map_random" />

<center id="world_map_spin_${world_map_random}">
	<i class='icon-spinner icon-spin blue bigger-250'></i>
</center>

<div id="filter_${world_map_random}" style="height:  ${empty viewChartHeight?500:viewChartHeight*2}px;">
	<div class="col-sm-12">
		<div id="world_map_div_${world_map_random}" style="height:  ${empty viewChartHeight?700:viewChartHeight*1.5}px; display: none;"></div>
	</div>
</div>

<script>
var filter_widget_main_str_${world_map_random}=
	'<div class="row">'
    +'	<div class="col-xs-12">'
    +'		<h5 class="header smaller lighter blue no-margin">'
    +'			<spring:message code="geographic.distribution.filter.title.type"/>'
    +'			:'
    +'		</h5>'
    +'		<div class="orderByGroup">'
    +'			<select id="count_type_select_${world_map_random}" style="width:150px">'
    +'				<option title="<spring:message code="geographic.distribution.type.option.mean"/>" value="<spring:message code="geographic.distribution.type.option.avg"/>"><spring:message code="geographic.distribution.type.option.mean"/></option>'
    +'				<option title="<spring:message code="geographic.distribution.type.option.min"/>" value="<spring:message code="geographic.distribution.type.option.min"/>"><spring:message code="geographic.distribution.type.option.min"/></option>'
    +'				<option title="<spring:message code="geographic.distribution.type.option.max"/>" value="<spring:message code="geographic.distribution.type.option.max"/>"><spring:message code="geographic.distribution.type.option.max"/></option>'
	+'		  	</select>'
    +'		</div>'
    +'	</div>'
    +'</div>'
    +'<div class="row">'
    +'	<div class="col-xs-12">'
    +'		<h5 class="header smaller lighter blue no-margin">'
    +'			<spring:message code="geographic.distribution.filter.title.indicator"/>'
    +'			:'
    +'		</h5>'
    +'		<div class="orderByGroup">'
    +'			<select id="indicator_select_${world_map_random}" style="width:150px">'
    <c:forEach items="${worldMapIndicatorMap}" var="data" varStatus="status">
    +'				<option class="seOption" title="${data.value}" value="${data.key}">${data.key}</option>'
	</c:forEach>
    +'			</select>'
    +'		</div>'
    +'	</div>'
    +'</div>'
	+'<div class="space-8"></div>'
	+'<div class="row">'
	+'	<div class="col-xs-12">'
	+'		<button id="search_button" onclick="init_world_map_${world_map_random}()" type="button" class="btn btn-purple btn-sm"><spring:message code="tab.update"/></button>'
	+'	</div>'
	+'</div>';

	var $filter_widget_main_${world_map_random};
	var $unit_${world_map_random};
	
	$(function() {
        $filter_widget_main_${world_map_random} = $("#filter_${world_map_random}").closest(".widget-box").find(".widget-main:first");
        $filter_widget_main_${world_map_random}.html(filter_widget_main_str_${world_map_random});
        $filter_widget_main_${world_map_random}.closest('ul.dropdown-menu').on('click', '[data-stopPropagation]', function (e) {
			e.stopPropagation();
		});

        init_world_map_${world_map_random}();

		$("#start_date_select_${world_map_random}").datepicker().on('changeDate', function(){
			setTimeout(function(){
				$(".datepicker-dropdown").hide();
				$(".date-picker").blur();
				$("#filter_${world_map_random}").closest(".widget-box")
				.find(".dropdown-toggle").dropdown('toggle');
			},100);
		});

		$("#end_date_select_${world_map_random}").datepicker().on('changeDate', function(e){
			setTimeout(function(){
				$(".datepicker-dropdown").hide();
				$(".date-picker").blur();
				$("#filter_${world_map_random}").closest(".widget-box")
				.find(".dropdown-toggle").dropdown('toggle');
			},100);
		}); 

		$('.date-picker').datepicker({
			autoclose : false
		}).next().on(ace.click_event, function() {
			$(this).prev().focus();
		});
	})

	function init_world_map_${world_map_random}() {

		$("#world_map_spin_${world_map_random}").html("<i class='icon-spinner icon-spin blue bigger-250'></i>");
		$("#world_map_spin_${world_map_random}").show();
		$("#world_map_div_${world_map_random}").hide();
		
		$.post("${pageContext.request.contextPath}/bi/dfView/worldMap/getData?" + toParamUrl(), {
            "countType" : $("#count_type_select_${world_map_random}").val(),
            "indicator" : $("#indicator_select_${world_map_random}").val(),
			"startDate" : $("#start_date_select_${world_map_random}").val(),
			"endDate" : $("#end_date_select_${world_map_random}").val()
		}, function(data) {
			if (data.success) {
				$("#world_map_spin_${world_map_random}").hide();
				$("#world_map_div_${world_map_random}").show();
                var min = 0, max = 0;
                var series_data = new Array();
                for (var i = 0; i < data.worldMapDataList.length; i++) {
                    var recruitment = data.worldMapDataList[i].value;
                    min = min > recruitment ? recruitment : min;
                    max = max < recruitment ? recruitment : max;
                    var region_name = data.worldMapDataList[i].name.toLowerCase();
                    region_name = region_name.replace(/\b\w+\b/g, function (word) {
                        return word.substring(0, 1).toUpperCase() + word.substring(1);
                    });
                    $unit_${world_map_random} = data.worldMapDataList[i].unit;
                    series_data.push({name: region_name, value: recruitment.toFixed(2)});
                }

                var world_map_chart = echarts.init(document.getElementById('world_map_div_${world_map_random}'),'myStyle');
				var world_map_option = {
				    title: {
                        text: $("#count_type_select_${world_map_random} option:selected").text() + ' ' + $("#indicator_select_${world_map_random}").val(),
                        left: 'center'
					},
                    tooltip: {
                        trigger: 'item',
                        formatter: function (params) {
                            if(params.name == undefined || params.name == ''){
                                return params.seriesName;
							} else {
                            	return params.seriesName + '<br/>Country : ' + params.name + '<br/>' + $("#count_type_select_${world_map_random} option:selected").text() + ' ' + $("#indicator_select_${world_map_random}").val() +': ' + params.value + ' ' + $unit_${world_map_random};
							}
                        }
                    },
                    visualMap: {
                        show: true,
                        min: min,
                        max: max,
                        text: ['High', 'Low'],
                        realtime: false,
                        calculable: true,
                        inRange: {
                            color: ['#ccc1f9', '#f38593', '#ff3300']
                        }
                    },
                    series: [{
                            name: '<spring:message code="geographic.distribution.title" text="Region recruitment overview"/>',
                            type: 'map',
                            mapType: 'world',
                            roam: true,
                            label: {
                                emphasis: {
                                    textStyle: {
                                        color: '#ffcbd4'
                                    }
                                }
                            },
                            itemStyle: {
                                emphasis: {
                                    areaColor: '#ff563c',
                                }
                            },
                            data: series_data
                        }
                    ]
                };
                world_map_chart.setOption(world_map_option, true);

			} else {
				$("#world_map_div_${world_map_random}").hide();
				$("#world_map_spin_${world_map_random}").show();
				$("#world_map_spin_${world_map_random}").html("<spring:message code='dashboard.data.empty'/>");
			}
		})
	}

</script>
