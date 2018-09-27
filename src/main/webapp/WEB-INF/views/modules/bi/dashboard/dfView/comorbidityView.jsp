<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" buffer="1024kb"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>
<script src="${ctxStatic }/echarts/${style }.js"></script>
<style type="text/css">
    .seOption{
        max-width: 200px;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }
</style>

<c:set value="<%=(int)(Math.random()*1000) %>" var="comorbidity_random" />

<center id="comorbidity_spin_${comorbidity_random}">
	<i class='icon-spinner icon-spin blue bigger-250'></i>
</center>

<div id="filter_${comorbidity_random}" style="height:  ${empty viewChartHeight?1000:viewChartHeight*2}px;">
	<div class="col-sm-12">
		<div id="comorbidity_tree_map_${comorbidity_random}" style="height:  ${empty viewChartHeight?500:viewChartHeight}px; display: none;"></div>
	</div>
	<br />
	<div class="col-xs-12" id="comorbidity_bar_${comorbidity_random}" style="height:  ${empty viewChartHeight?500:viewChartHeight}px; display: none;"></div>
</div>

<script>

	//展现数据
	var comorbidity_tree_map_data_${comorbidity_random};
	var comorbidity_bar_data_${comorbidity_random};
	var comorbidity_color_arr_${comorbidity_random} = [ "#F3E5AB", "#B4DA50", "#F7D533", "#FE8C01", "#D8181C", "#5187DA" ];

	var node_name_${comorbidity_random};
	var sum_count_${comorbidity_random};

	function init_comorbidity_tree_map_${comorbidity_random}() {
		
		$("#comorbidity_spin_${comorbidity_random}").html("<i class='icon-spinner icon-spin blue bigger-250'></i>");
		$("#comorbidity_spin_${comorbidity_random}").show();
		$("#comorbidity_tree_map_${comorbidity_random}").hide();
		$("#comorbidity_bar_${comorbidity_random}").hide();
		
		$.post("${pageContext.request.contextPath}/bi/dfView/comorbidity/getData?" + toParamUrl(), {
			"startDate" : $("#start_date_select_${comorbidity_random}").val(),
			"endDate" : $("#end_date_select_${comorbidity_random}").val(),
			"indicator" : $("#drug_select_${comorbidity_random}").val(),
			"comorbidity":$("#comorbidity_select_${comorbidity_random}").val()
		}, function(data) {
			if (data.success) {
				$("#comorbidity_spin_${comorbidity_random}").hide();
				$("#comorbidity_tree_map_${comorbidity_random}").show();
                comorbidity_tree_map_data_${comorbidity_random} = data.comorbidityDataList;
                comorbidity_bar_data_${comorbidity_random} = data.comorbiditySubDataList;
                sum_count_${comorbidity_random} = data.sumCount;
                // 基于准备好的dom，初始化echarts实例
                var comorbidity_tree_map_chart = echarts.init(document.getElementById('comorbidity_tree_map_${comorbidity_random}'),'myStyle');

				var comorbidity_tree_map_option = {
					tooltip : {
						show:<c:if test="${isPad eq '1'}">false</c:if><c:if test="${isPad ne '1'}">true</c:if>,
						formatter:function(param){
							return '<spring:message code="comorbidity.tooltip.comorbidity"/>: '+param.name+'<br/><spring:message code="comorbidity.tooltip.count"/>: '+param.value;
						}
					},
					calculable : false,
					series : [ {
						name : '<spring:message code="comorbidity.tooltip.comorbidity"/>',
						type : 'treemap',
						itemStyle : {
							normal : {
								label : {
									show : true,
									formatter : "{b}",
                                    color : '#000'
								},
								borderColor : '#fff'
							},
							emphasis : {
								label : {
									show : true
								}
							},
							label : {
								show : true,
								position : 'outer'
							}
						},
						levels : get_level_option_${comorbidity_random}(),
						data : comorbidity_tree_map_data_${comorbidity_random}
					} ]
				};

                comorbidity_tree_map_chart.on("click", function(params) {
                    if (params.treePathInfo[1] == undefined) {
                        node_name_${comorbidity_random} = "非血液学毒性";
                    } else {
                        node_name_${comorbidity_random} = params.treePathInfo[1].name;
                    }
                    init_comorbidity_bar_${comorbidity_random}();
                })

                comorbidity_tree_map_chart.setOption(comorbidity_tree_map_option);
			} else {
				$("#comorbidity_tree_map_${comorbidity_random}").hide();
				$("#comorbidity_bar_${comorbidity_random}").hide();
				$("#comorbidity_spin_${comorbidity_random}").show();
				$("#comorbidity_spin_${comorbidity_random}").html("<spring:message code='dashboard.data.empty'/>");
			}
		})
	}

	function get_level_option_${comorbidity_random}() {
		return [ {
			color : comorbidity_color_arr_${comorbidity_random},
			itemStyle : {
				normal : {
					gapWidth : 5
				}
			}
		}, {
			color : comorbidity_color_arr_${comorbidity_random},
			itemStyle : {
				normal : {
					gapWidth : 1
				}
			}
		}, {
			color : comorbidity_color_arr_${comorbidity_random},
			itemStyle : {
				normal : {
					gapWidth : 1,
				}
			}
		}, {
			color : comorbidity_color_arr_${comorbidity_random},
			itemStyle : {
				normal : {
					gapWidth : 1,
				}
			}
		} ];
	}

	function init_comorbidity_bar_${comorbidity_random}() {
		var legend_data = new Array();
		var item_arr = new Array();
		var valid_data_arr = new Array();
		var max_length = 0;
		var max_sub_sum_count = 0;

		for(var i in comorbidity_tree_map_data_${comorbidity_random}){
		    var obj = comorbidity_tree_map_data_${comorbidity_random}[i];
		    for(var j in obj.children){
		        var subObj = obj.children[j];
                if(subObj.sumCount > max_sub_sum_count){
                    max_sub_sum_count = subObj.sumCount;
                }
			}
		}

		for ( var i in comorbidity_bar_data_${comorbidity_random}) {
			var obj = comorbidity_bar_data_${comorbidity_random}[i];
			if(node_name_${comorbidity_random} == obj.type){
                valid_data_arr.push(obj);

			    if(legend_data.indexOf(obj.name) == -1){
			        legend_data.push(obj.name);
                }

                if(obj.number > max_length){
                    max_length = obj.number;
				}
            }
		}

		for(var i=1; i<max_length+1; i++){
            var item = new Object();
            item.type = 'bar';
            item.stack = node_name_${comorbidity_random};
            item.barMaxWidth = '100';
            item.barWidth = '25';
            item.name = i;
            item.data = new Array();

            for(var j in legend_data){
                for(var k in valid_data_arr){
                    var flag = 0;
                    if(valid_data_arr[k].name == legend_data[j] && valid_data_arr[k].number == i){
                        var item_style = new Object();
                        var item_style_normal = new Object();
                        item_style_normal.color = comorbidity_color_arr_${comorbidity_random}[i-1];
                        item_style.normal = item_style_normal;

                        var node = new Object();
                        node.value = valid_data_arr[k].value;
                        node.itemStyle = item_style;
                        if(valid_data_arr[k].childName != undefined && valid_data_arr[k].childName.length > 0) {
                            node.name = valid_data_arr[k].name + '(' + valid_data_arr[k].childName + ')';
                        } else {
                            node.name = valid_data_arr[k].name;
						}

                        var label = new Object();
                        var normal = new Object();
                        normal.show = true;
                        normal.position = "insideBottom";
                        normal.fontSize = 10;
                        normal.color = '#000';
						if(valid_data_arr[k].value * 100/max_sub_sum_count < 1.0) {
                            normal.show = false;
                        }
                        label.normal = normal;
                        node.label = label;
                        item.data.push(node);
                        flag = 1;
                        break;
					}
				}
				if(flag == 0) {
                    item.data.push('');
                }
			}
            item_arr.push(item);
		}

        var item = new Object();
        item.type = 'bar';
        item.stack = node_name_${comorbidity_random};
        item.barMaxWidth = '100';
        item.barWidth = '25';
        item.name = i;
        item.data = new Array();

        var label = new Object();
        var normal = new Object();
        normal.show = true;
        normal.position = "insideBottom";
        normal.fontSize = 10;
        normal.color = '#000';
        label.normal = normal;
        item.label = label;

        var node_data = new Array();
        for(var j in comorbidity_tree_map_data_${comorbidity_random}){
            var obj = comorbidity_tree_map_data_${comorbidity_random}[j];
            if(obj.name == node_name_${comorbidity_random}) {
                node_data = obj.children;
                break;
            }
        }

        for(var i in legend_data){
            var legend_name = legend_data[i];
            var value = '';
            for(var j in node_data){
                var obj = node_data[j];
                if(obj.name == legend_name) {
                    value = (obj.value * 100 /sum_count_${comorbidity_random}).toFixed(2);
                    break;
                }
            }
            var node = new Object();
            node.value = 0;
            node.name = i;
            var dataLabel = new Object();
            dataLabel.show = true;
            dataLabel.position='right';
            dataLabel.formatter = value + '%';
            node.label = dataLabel;
            item.data.push(node);
        }
        item_arr.push(item);
		$("#comorbidity_bar_${comorbidity_random}").show();
		var comorbidity_bar_chart_${comorbidity_random} = echarts.init(document.getElementById('comorbidity_bar_${comorbidity_random}'),'myStyle');
		var comorbidity_bar_option_${comorbidity_random} = {
			tooltip : {
				show:<c:if test="${isPad eq '1'}">false</c:if><c:if test="${isPad ne '1'}">true</c:if>,
				axisPointer : { // 坐标轴指示器，坐标轴触发有效
					type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
				},
				formatter : function(params) {
					return ['<span>' +'<spring:message code="comorbidity.tooltip.comorbidity"/>: '+ params.name + '<br/><spring:message code="comorbidity.tooltip.count"/>: ' + params.value + ' </span>'].join('<br/>')
				}
			},
			title : {
				text : '<spring:message code="comorbidity.tooltip.comorbidity"/> ( Total Count: ' + sum_count_${comorbidity_random} + ' )'
			},
			legend : {
				data : legend_data
			},
			grid : {
				left : '6%',
				right : '14%',
				bottom : '3%',
				containLabel : true
			},
			xAxis : [{
                name : '<spring:message code="comorbidity.tooltip.count"/>',
                type : 'value',
                nameTextStyle : {
                    fontSize : 20
                }
            }],
			yAxis : [{
                type : 'category',
                data : legend_data,
                name : node_name_${comorbidity_random},
                nameGap : 10,
                axisLabel : {
                    interval: 0,
                },
                nameTextStyle : {
                    fontSize : 20
                }
            }],
			series : item_arr
		};
		comorbidity_bar_chart_${comorbidity_random}.setOption(comorbidity_bar_option_${comorbidity_random}, true);
	}

	$(function() {
        init_comorbidity_tree_map_${comorbidity_random}();
	});

</script>
