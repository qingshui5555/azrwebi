<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" buffer="1024kb"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>
<script src="${ctxStatic }/echarts/${style }.js"></script>
<c:set value="<%=(int)(Math.random()*1000) %>" var="treatment_journey_random" />

<center id="treatment_journey_spin_${treatment_journey_random}">
		<i class='icon-spinner icon-spin blue bigger-250'></i>
	</center>
	<div class="col-xs-12">
		<div class="row" id="treatment_journey_title_${treatment_journey_random}" align="center"></div>
	</div>

	<div class="row" id="filter_${treatment_journey_random}" style="display: none;">
		<div class="col-xs-8" style="text-align: left;">
			<span style="background-color: #c9449d; margin: 0px 5px; border-radius: 25px;">&nbsp;&nbsp;&nbsp;&nbsp;</span>
			<spring:message code="treatment.journey.one" />
			<span style="background-color: #4486c7; margin: 0px 5px; border-radius: 25px;">&nbsp;&nbsp;&nbsp;&nbsp;</span>
			<spring:message code="treatment.journey.two" />
			<span style="background-color: #c98643; margin: 0px 5px; border-radius: 25px;">&nbsp;&nbsp;&nbsp;&nbsp;</span>
			<spring:message code="treatment.journey.three" />
			<span style="background-color: #4f4f92; margin: 0px 5px; border-radius: 25px;">&nbsp;&nbsp;&nbsp;&nbsp;</span>
			<spring:message code="treatment.journey.four" />
		</div>
		<div class="">
			<button class="btn btn-purple btn-sm pull-right" style="display: none;" id="scatter_button_${treatment_journey_random}">
				<i class="icon-reply icon-only"></i>
			</button>
		</div>
	</div>

	<div id="treatment_journey_scatter_${treatment_journey_random}" class="treatment_journey_scatter"></div>
	<div id="treatment_journey_graphic_${treatment_journey_random}" class="treatment_journey_graphic" style="display: none; margin-left: -20%"></div>

<script>
    var filter_widget_main_str_${treatment_journey_random}=
        '<div class="row">'
		+'	<div class="col-xs-12">'
        +'		<h5 class="header smaller lighter blue no-margin">'
        +'			<spring:message code="treatment.journey.count" />'
        +'			:'
        +'		</h5>'
        +'	   	<div class="orderByGroup">'
        +'      	<select id="count_select_${treatment_journey_random}">'
        +'          	<option value="10">10</option>'
        +'          	<option value="20" selected>20</option>'
        +'          	<option value="40">40</option>'
        +'          	<option value="60">60</option>'
        +'          	<option value="100000">'
        +'          		<spring:message code="treatment.journey.all" />'
        +'         		</option>'
        +'			</select>'
        +'     	</div>'
        +'	</div>'
        +'</div>';

    var tj_margin = {
		top : 10,
		right : 20,
		bottom : 20,
		left : 20
	}, width = 960 - tj_margin.right - tj_margin.left;
	var tj_i = 0, tj_duration = 750, tj_root_${treatment_journey_random};
	var diagonal_${treatment_journey_random} = d3.svg.diagonal().projection(function(d) {
		return [ d.y, d.x ];
	});
	var d3Flag_${treatment_journey_random} = false;
	d3.select(self.frameElement).style("height", "800px");
	var div = d3.select("body").append("div").attr("class", "d3_tooltip").style("opacity", 0);

	var first_data_${treatment_journey_random} = new Array();

	var second_data_${treatment_journey_random} = new Array();

	var third_data_${treatment_journey_random} = new Array();

	var fourth_data_${treatment_journey_random} = new Array();

	var select_count_${treatment_journey_random} = 20;

	var ajax_treatment_journey_data_${treatment_journey_random};

	var node_name_${treatment_journey_random};

	$(function() {
		$filter_widget_main_${treatment_journey_random} = $("#filter_${treatment_journey_random}").closest(".widget-box").find(".widget-main:first");
        $filter_widget_main_${treatment_journey_random}.html(filter_widget_main_str_${treatment_journey_random});
        $filter_widget_main_${treatment_journey_random}.closest('ul.dropdown-menu')
		.on('click', '[data-stopPropagation]', function (e) {
			e.stopPropagation();
		});

		$("#filter_${treatment_journey_random}").closest(".widget-box").find(".dropdown-menu:first").find(".widget-box:first").css({height:"100px"});

		$("#count_select_${treatment_journey_random}").change(function() {
            select_count_${treatment_journey_random} = this.value;
			if (!d3Flag_${treatment_journey_random}) {
				first_data_${treatment_journey_random} = new Array();
				second_data_${treatment_journey_random} = new Array();
				third_data_${treatment_journey_random} = new Array();
				fourth_data_${treatment_journey_random} = new Array();
				init_scatter_${treatment_journey_random}(ajax_treatment_journey_data_${treatment_journey_random});
			} else {
				init_graphic_${treatment_journey_random}();
			}
		})

		$("#scatter_button_${treatment_journey_random}").click(function() {
			$("#treatment_journey_title_${treatment_journey_random}").html("<label style='font-size:18px;font-weight:bolder;'><spring:message code='treatment.journey.title.summary'/></label>");
			d3Flag_${treatment_journey_random} = false;
			$("#scatter_button_${treatment_journey_random}").hide();
			$("#treatment_journey_scatter_${treatment_journey_random}").show();
			$("#treatment_journey_graphic_${treatment_journey_random}").hide();
			init_scatter_${treatment_journey_random}(ajax_treatment_journey_data_${treatment_journey_random});
		})

		$.post("${ctxbi}/dfView/treatmentJourney/getData", {
			studyId : requestParam.studyId,
            cohortIds : '${cohortIds}'
		}, function(data) {
			$("#treatment_journey_title_${treatment_journey_random}").html("<label style='font-size:18px;font-weight:bolder;'><spring:message code='treatment.journey.title.summary'/></label>");
			if (data.length > 0) {
				$("#treatment_journey_spin_${treatment_journey_random}").hide();
                ajax_treatment_journey_data_${treatment_journey_random} = data;
				init_scatter_${treatment_journey_random}(data);
				$("#filter_${treatment_journey_random}").show();
			} else {
				$("#treatment_journey_title_${treatment_journey_random}").hide();
				$("#treatment_journey_spin_${treatment_journey_random}").show();
				$("#treatment_journey_spin_${treatment_journey_random}").html("<spring:message code='dashboard.data.empty'/>");
				$("#filter_${treatment_journey_random}").hide();
			}
		});

	})

    function init_scatter_${treatment_journey_random}(data) {
        init_scatter_data_${treatment_journey_random}(data);

        var maxLength = Math.max(first_data_${treatment_journey_random}.length, second_data_${treatment_journey_random}.length, third_data_${treatment_journey_random}.length, fourth_data_${treatment_journey_random}.length);

        $("#treatment_journey_scatter_${treatment_journey_random}").css("height", maxLength * 50);

        var schema = [ {
            name : 'name',
            index : 0,
            text : '<spring:message code="treatment.journey.solution" />'
        }, {
            name : 'value',
            index : 1,
            text : '<spring:message code="treatment.journey.count" />'
        }, {
            name : 'curlevel',
            index : 2,
            text : '<spring:message code="treatment.journey.level" />'
        } ];

        var itemStyle = {
            normal : {
                opacity : 0.8
            }
        };

        var series_data = [];
        if(first_data_${treatment_journey_random}.length != 0){
            series_data[0] = {
                name : '一级',
                type : 'scatter',
                itemStyle : itemStyle,
                data : first_data_${treatment_journey_random}
            };
        }

        if(second_data_${treatment_journey_random}.length != 0){
            series_data[1] = {
                name : '二级',
                type : 'scatter',
                itemStyle : itemStyle,
                data : second_data_${treatment_journey_random}
            };
        } else {
            series_data[1] = {"type":"scatter", "data":[{value: [2,0], symbolSize: 0}]};
        }

        if(third_data_${treatment_journey_random}.length != 0){
            series_data[2] = {
                name : '三级',
                type : 'scatter',
                itemStyle : itemStyle,
                data : third_data_${treatment_journey_random}
            };
        } else {
            series_data[2] = {"type":"scatter","data":[{value: [3,0], symbolSize: 0}]};
        }

        if(fourth_data_${treatment_journey_random}.length != 0){
            series_data[3] = {
                name : '四级',
                type : 'scatter',
                itemStyle : itemStyle,
                data : fourth_data_${treatment_journey_random}
            };
        } else {
            series_data[3] = {"type":"scatter","data":[{value: [4,0], symbolSize: 0}]};
        }

        var treatment_journey_chart_${treatment_journey_random} = echarts.init(document.getElementById('treatment_journey_scatter_${treatment_journey_random}'),'myStyle');

        treatment_journey_chart_${treatment_journey_random}.on("click", function(e) {
            if (e.seriesIndex == 0) {
                node_name_${treatment_journey_random} = e.name;
                $("#scatter_button_${treatment_journey_random}").show();
                $("#treatment_journey_scatter_${treatment_journey_random}").hide();
                $("#treatment_journey_graphic_${treatment_journey_random}").show();
                init_graphic_${treatment_journey_random}();
                d3Flag_${treatment_journey_random} = true;

                $("#treatment_journey_title_${treatment_journey_random}").html("<label style='font-size:18px;font-weight:bolder;'><spring:message code='treatment.journey.title.flow'/></label>");
            }
        })

        var treatment_journey_option_${treatment_journey_random} = {
            color: [ '#c9449d', '#4486c7', '#c98643', '#4f4f92' ],
            grid: {
                right : '20%',
                x: '-20%'
            },
            tooltip : {
                borderWidth : 1,
                formatter : function(obj) {
                    var value = obj.value;
                    var visit;
                    if (value[2] == 1) {
                        visit = "<spring:message code='treatment.journey.one'/>";
                    }
                    if (value[2] == 2) {
                        visit = "<spring:message code='treatment.journey.two'/>";
                    }
                    if (value[2] == 3) {
                        visit = "<spring:message code='treatment.journey.three'/>";
                    }
                    if (value[2] == 4) {
                        visit = "<spring:message code='treatment.journey.four'/>";
                    }
                    return schema[0].text + '：' + value[4] + '<br>' + schema[1].text + '：' + value[3] + '<br>' + schema[2].text + '：' + visit;
                }
            },
            xAxis : {
                type : 'value',
                show : false
            },
            yAxis : {
                type : 'value',
                show : false,
                inverse : true
            },
            series : series_data
        };

        treatment_journey_chart_${treatment_journey_random}.setOption(treatment_journey_option_${treatment_journey_random});
    }

    function init_scatter_data_${treatment_journey_random}(data) {
        first_data_${treatment_journey_random} = new Array();

        second_data_${treatment_journey_random} = new Array();

        third_data_${treatment_journey_random} = new Array();

        fourth_data_${treatment_journey_random} = new Array();

        for(var i in data) {
            var obj = data[i];
            var curLevel = obj.curLevel;
            var node = new Object();
            node.name = obj.name;
            node.value = new Array();
            node.symbolSize = Math.log(obj.value * 1000) * 1.8;
            var label = new Object();
            var normal = new Object();
            normal.show = true;
            normal.position = "right";
            normal.formatter = function(data){
            	return data.name;
            }
            var emphasis = new Object();
            emphasis.show = true;
            emphasis.position = "right";
            label.normal = normal;
            label.emphasis = emphasis;
            node.label = label;

            if (curLevel == 1) {
                if(first_data_${treatment_journey_random}.length >= select_count_${treatment_journey_random}) {
                    continue;
                }
                node.value.push(1, first_data_${treatment_journey_random}.length, curLevel, obj.value, obj.name);
                first_data_${treatment_journey_random}.push(node);
            } else if(curLevel == 2) {
                if(second_data_${treatment_journey_random}.length >= select_count_${treatment_journey_random}) {
                    continue;
                }
                node.value.push(2, second_data_${treatment_journey_random}.length, curLevel, obj.value, obj.name);
                second_data_${treatment_journey_random}.push(node);
            } else if(curLevel == 3) {
                if(third_data_${treatment_journey_random}.length >= select_count_${treatment_journey_random}) {
                    continue;
                }
                node.value.push(3, third_data_${treatment_journey_random}.length, curLevel, obj.value, obj.name);
                third_data_${treatment_journey_random}.push(node);
            } else if(curLevel == 4) {
                if(fourth_data_${treatment_journey_random}.length >= select_count_${treatment_journey_random}) {
                    continue;
                }
                node.value.push(4, fourth_data_${treatment_journey_random}.length, curLevel, obj.value, obj.name);
                fourth_data_${treatment_journey_random}.push(node);
            }
        }
    }

    function init_graphic_${treatment_journey_random}() {
        $("#treatment_journey_spin_${treatment_journey_random}").show();
        $("#treatment_journey_title_${treatment_journey_random}").hide();
        $("#filter_${treatment_journey_random}").hide();
        $("#treatment_journey_graphic_${treatment_journey_random}").hide();

        $.post("${ctxbi}/dfView/treatmentJourney/getFirstLevelData", {
        	"studyId": requestParam.studyId,
        	"max": select_count_${treatment_journey_random},
       		"cohortIds": '${cohortIds}'
       	}, function(flare){
    		$("#treatment_journey_spin_${treatment_journey_random}").hide();
			$("#treatment_journey_title_${treatment_journey_random}").show();
			$("#filter_${treatment_journey_random}").show();
			$("#treatment_journey_graphic_${treatment_journey_random}").show();

			tj_root_${treatment_journey_random} = flare;
			var height = flare.children.length * 30;
			tree = d3.layout.tree().size([ height, width ]);
			$("#treatment_journey_graphic_${treatment_journey_random} svg").remove();
			tj_svg = d3.select("#treatment_journey_graphic_${treatment_journey_random}").append("svg").attr("width", "100%").attr("height", height + tj_margin.top + tj_margin.bottom).append("g").attr("transform", "translate(" + tj_margin.left + "," + tj_margin.top + ")");

			tj_root_${treatment_journey_random}.x0 = height / 2;
			tj_root_${treatment_journey_random}.y0 = 0;

			tj_root_${treatment_journey_random}.children.forEach(collapse);
			update_${treatment_journey_random}(tj_root_${treatment_journey_random});
		});
    }

    function update_${treatment_journey_random}(source) {

        var nodes = tree.nodes(tj_root_${treatment_journey_random}).reverse(), links = tree.links(nodes);

        nodes.forEach(function(d) {
            d.y = d.depth * ($("#treatment_journey_graphic_${treatment_journey_random}").width()/5);
        });

        var node = tj_svg.selectAll("g.node").data(nodes, function(d) {
            return d.id || (d.id = ++tj_i);
        });

        var nodeEnter = node.enter().append("g").attr("class", "node").attr("transform", function(d) {
            return "translate(" + source.y0 + "," + source.x0 + ")";
        }).on("click", click_${treatment_journey_random});

        nodeEnter.append("circle").attr("r", 1e-6).style("fill", function(d) {
            return d.level
        }).on(
            "mouseover",
            function(d) {
                console.log("hasChildren:" + d.hasChildren);
                if(d.hasChildren){
                    $(this).css({"cursor": "pointer"});
                }
                var visit;
                var percentMsg;
                var avgDaysMsg;
                if (d.curLevel == 1) {
                    visit = "<spring:message code='treatment.journey.one'/>";
                    percentMsg = "<spring:message code='treatment.journey.tooltip.percent.one'/>";
                    avgDaysMsg = "<spring:message code='treatment.journey.tooltip.avg.days.one'/>";
                }
                if (d.curLevel == 2) {
                    visit = "<spring:message code='treatment.journey.two'/>";
                    percentMsg = "<spring:message code='treatment.journey.tooltip.percent.two'/>";
                    avgDaysMsg = "<spring:message code='treatment.journey.tooltip.avg.days.two'/>";
                }
                if (d.curLevel == 3) {
                    visit = "<spring:message code='treatment.journey.three'/>";
                    percentMsg = "<spring:message code='treatment.journey.tooltip.percent.three'/>";
                    avgDaysMsg = "<spring:message code='treatment.journey.tooltip.avg.days.three'/>";
                }
                if (d.curLevel == 4) {
                    visit = "<spring:message code='treatment.journey.four'/>";
                    percentMsg = "<spring:message code='treatment.journey.tooltip.percent.four'/>";
                    avgDaysMsg = "<spring:message code='treatment.journey.tooltip.avg.days.four'/>";
                }
                div.transition().duration(200).style("opacity", .9);

                var timeDays;
                if (d.timeDays == undefined) {
                    timeDays = 1;
                } else {
                    timeDays = Math.round(d.timeDays / d.value);
                }
                var count = $(":radio[name='search_type']:checked").parent().find(".patientCount").html();
                div.html(
                    '<div class="tj_pop" style="min-width:320px;width:auto;">' + '<div class="tj_title">' + d.name + '<span>' + visit + '</span></div>' + '<div class="tj_num"><div class="col-xs-6">' + (Math.round(d.value / count * 10000) / 100.00 + "%") + '</div><div class="col-xs-6">' + timeDays + '</div></div>' + '<div class="tj_note">'
                    + '<div class="col-xs-6">'+percentMsg+'</div>' + '<div class="col-xs-6">' + avgDaysMsg + '</div>' + '</div>' + '<div class="tj_patientsNo">' + d.value + ' <spring:message code="treatment.journey.tooltip.patient"/></div>' + '</div> ').style("left", (d3.event.pageX) + "px").style("top", (d3.event.pageY) + "px");
            }).on("mouseout", function(d) {
            div.transition().duration(500).style("opacity", 0);
        });

        nodeEnter.append("text").attr("x", function(d) {
            return d.children || d._children ? -10 : 10;
        }).attr("dy", ".35em").attr("text-anchor", function(d) {
            return d.children || d._children ? "end" : "start";
        }).text(function(d) {
            return d.name;
        }).style("fill-opacity", 1e-6);

        // Transition nodes to their new position.
        var nodeUpdate = node.transition().duration(tj_duration).attr("transform", function(d) {
            console.log(tj_duration + ' ' + d.y + ' ' + d.x);
            return "translate(" + d.y + "," + d.x + ")";
        });

        nodeUpdate.select("circle").attr("r", function(d) {
            return Math.log(d.value * 100);
        }).style("fill", function(d) {
            return d.level
        }).style("opacity", function(d) {
            return d._children ? 1 : 0.8;
        });

        nodeUpdate.select("text").style("fill-opacity", 1);

        // Transition exiting nodes to the parent's new position.
        var nodeExit = node.exit().transition().duration(tj_duration).attr("transform", function(d) {
            return "translate(" + source.y + "," + source.x + ")";
        }).remove();

        nodeExit.select("circle").attr("r", 1e-6);

        nodeExit.select("text").style("fill-opacity", 1e-6);

        // Update the links…
        var link = tj_svg.selectAll("path.link").data(links, function(d) {
            return d.target.id;
        });

        // Enter any new links at the parent's previous position.
        link.enter().insert("path", "g").attr("class", "link").style("stroke", function(d) {
            if(d.target.line == "fixTheme"){
                if(d.target.style == "black"){
                    return "black";
                } else {
                    return "white";
                }
            } else {
                if(d.target.style == "black"){
                    return "white";
                } else {
                    return "black";
                }
            }
            return d.target.line;
        }).attr("d", function(d) {
            var o = {
                x : source.x0,
                y : source.y0
            };
            return diagonal_${treatment_journey_random}({
                source : o,
                target : o
            });
        });

        link.transition().duration(tj_duration).attr("d", diagonal_${treatment_journey_random});

        link.exit().transition().duration(tj_duration).attr("d", function(d) {
            var o = {
                x : source.x,
                y : source.y
            };
            return diagonal_${treatment_journey_random}({
                source : o,
                target : o
            });
        }).remove();

        nodes.forEach(function(d) {
            d.x0 = d.x;
            d.y0 = d.y;
            d.r = 4.5;
        });
    }

    function collapse(d) {
        if (d.children) {
            d._children = d.children;
            d._children.forEach(collapse);
            d.children = null;
        }
    }

    function click_${treatment_journey_random}(d) {
        var curLevel = d.curLevel;
        //没有子节点和四线时取消加载画面
        if(d.hasChildren&&curLevel<4){
            $("#treatment_journey_spin_${treatment_journey_random}").show();
            $("#treatment_journey_title_${treatment_journey_random}").hide();
            $("#filter_${treatment_journey_random}").hide();
            $("#treatment_journey_graphic_${treatment_journey_random}").hide();

            $.post("${ctxbi}/dfView/treatmentJourney/getSecondData", {
                level : curLevel,
                patientIdStr : d.patientIdStr,
                studyId : requestParam.studyId,
                max : select_count_${treatment_journey_random},
                cohortIds : '${cohortIds}'
            }, function(data) {
                $("#treatment_journey_spin_${treatment_journey_random}").hide();
                $("#treatment_journey_title_${treatment_journey_random}").show();
                $("#filter_${treatment_journey_random}").show();
                $("#treatment_journey_graphic_${treatment_journey_random}").show();

                d.parent.children.forEach(collapse);
                d.children = data;
                update_${treatment_journey_random}(d);

            })
        }
    }

</script>

<style>
.treatment_journey_graphic .node circle {
	fill: #fff;
	opacity: 0.5;
}

.treatment_journey_graphic .node text {
	/*font: 10px sans-serif;*/
	fill: #95999C;
}

.treatment_journey_graphic .link {
	fill: none;
	stroke: grey;
	stroke-width: 0.5px;
    opacity: 0.3;
}

div.d3_tooltip {
	position: absolute;
	text-align: center;
	line-height: 15px;
	padding: 5px 0;
	pointer-events: none;
	z-index: 5000;
}

</style>
