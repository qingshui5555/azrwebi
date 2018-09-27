<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" buffer="1024kb"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>
<script src="${ctxStatic }/echarts/${style }.js"></script>
<c:set value="<%=(int)(Math.random()*1000) %>" var="payer_cost_random" />
<div class="row" id="main_container_${payer_cost_random}" style="height:${empty viewChartHeight?1000:viewChartHeight*2}px;">
	<div class="col-xs-12">
		<div id="payer_cost_total_title_${payer_cost_random}" style="text-align: center;width:84%">
			<div style='font-size:18px;font-weight:bold;margin-bottom:15px;'><spring:message code='payer.cost.title.distribution'/></div>
		</div>
		<center id="payer_cost_total_spin_${payer_cost_random}">
			<i class='icon-spinner icon-spin blue bigger-250'></i>
		</center>
		<div id="payer_cost_total_${payer_cost_random}" style="text-align: center;height:${empty viewChartHeight?400:viewChartHeight}px;"></div>

		<div id="payer_cost_detail_title_${payer_cost_random}" style="text-align: center;width:84%">
			<div style='font-size:18px;font-weight:bold;margin-bottom:15px;'><spring:message code='payer.cost.title.breakdown'/></div>
		</div>
		<div id="payer_cost_detail_legend_${payer_cost_random}" style="text-align: left;width:84%"></div>
		<center id="payer_cost_detail_spin_${payer_cost_random}">
			<i class='icon-spinner icon-spin blue bigger-250'></i>
		</center>
		<div id="payer_cost_detail_${payer_cost_random}" style="text-align: center;height:${empty viewChartHeight?400:viewChartHeight}px;"></div>
	</div>

</div>

<script>
var filter_widget_main_str_${payer_cost_random} =
	 '<div class="row">'
	+'	<div class="col-xs-12">'
	+'		<h5 class="header smaller lighter blue no-margin">'
	+'			<spring:message code="payer.cost.filter.title.count.type"/>'
	+'			:'
	+'		</h5>' 
	+'		<div class="orderByGroup">'
	+'			<select id="filter_payer_cost_count_type_${payer_cost_random}" style="width:150px">'
	+'				<option title="<spring:message code="payer.cost.option.per.visit"/>" value="<spring:message code="payer.cost.option.per.visit.value"/>" selected="selected"><spring:message code="payer.cost.option.per.visit"/></option>'
	+'				<option title="<spring:message code="payer.cost.option.per.patient"/>" value="<spring:message code="payer.cost.option.per.patient.value"/>"><spring:message code="payer.cost.option.per.patient"/></option>'
	+'			</select>'
	+'		</div>'
	+'	</div>'
	+'</div>'
    +'<div class="row">'
    +'	<div class="col-xs-12">'
    +'		<h5 class="header smaller lighter blue no-margin">'
    +'			<spring:message code="payer.cost.filter.title.cost.type"/>'
    +'			:'
    +'		</h5>'
    +'		<div class="orderByGroup">'
    +'			<select id="filter_payer_cost_type_${payer_cost_random}" style="width:150px">'
    +'				<option title="<spring:message code="payer.cost.option.average"/>" value="<spring:message code="payer.cost.option.average.value"/>" selected="selected"><spring:message code="payer.cost.option.average"/></option>'
    +'				<option title="<spring:message code="payer.cost.option.total"/>" value="<spring:message code="payer.cost.option.total.value"/>"><spring:message code="payer.cost.option.total"/></option>'
    +'			</select>'
    +'		</div>'
    +'	</div>'
    +'</div>'
	+'<div class="row">'
	+'	<div class="col-xs-12">'
	+'		<h5 class="header smaller lighter blue no-margin">'
	+'			<spring:message code="payer.cost.filter.title.start"/>'
	+'			:'
	+'		</h5>' 
	+'		<div class="orderByGroup">'
	+'			<div class="input-group input-group-sm" style="width:150px">'
	+'				<input type="text" id="filter_start_date_${payer_cost_random}" data-stoppropagation="true"'
	+'					class="form-control date-picker" data-date-format="yyyy-mm-dd" />'
	+'				<span class="input-group-addon"> <i class="icon-calendar"></i>'
	+'				</span>'
	+'			</div>'
	+'		</div>'
	+'	</div>'
	+'</div>'
	+'<div class="row">'
	+'	<div class="col-xs-12">'
	+'		<h5 class="header smaller lighter blue no-margin">'
	+'			<spring:message code="payer.cost.filter.title.end"/>'
	+'			:'
	+'		</h5>' 
	+'		<div class="orderByGroup">'
	+'			<div class="input-group input-group-sm" style="width:150px">'
	+'				<input type="text" id="filter_end_date_${payer_cost_random}" data-stopPropagation="true"'
	+'					class="form-control date-picker" data-date-format="yyyy-mm-dd" />'
	+'				<span class="input-group-addon"> <i class="icon-calendar"></i>'
	+'				</span>'
	+'			</div>'
	+'		</div>'
	+'	</div>'
	+'</div> '
	+'<div class="space-8"></div>'
	+'<div class="row">'
	+'	<div class="col-sm-12">'
	+'		<button id="search_button" onclick="init_${payer_cost_random}()" type="button"'
	+'			class="btn btn-purple btn-sm"><spring:message code="tab.update"/></button>'
	+'	</div>'
	+'</div>';

	var $filter_widget_main_${payer_cost_random};

	$(function() {
        $filter_widget_main_${payer_cost_random} = $("#main_container_${payer_cost_random}").closest(".widget-box").find(".widget-main:first");
        $filter_widget_main_${payer_cost_random}.html(filter_widget_main_str_${payer_cost_random});
        $filter_widget_main_${payer_cost_random}.closest('ul.dropdown-menu').on('click', '[data-stopPropagation]', function (e) {
			e.stopPropagation();
		});

		$("#filter_start_date_${payer_cost_random}").datepicker().on('changeDate', function(){
			setTimeout(function(){
				$(".datepicker-dropdown").hide();
				$(".date-picker").blur();
				$("#main_container_${payer_cost_random}").closest(".widget-box")
				.find(".dropdown-toggle").dropdown('toggle');
			},100);
		});

		$("#filter_end_date_${payer_cost_random}").datepicker().on('changeDate', function(e){
			setTimeout(function(){
				$(".datepicker-dropdown").hide();
				$(".date-picker").blur();
				$("#main_container_${payer_cost_random}").closest(".widget-box")
				.find(".dropdown-toggle").dropdown('toggle');
			},100);
		});

		$('.date-picker').datepicker({
			autoclose : true
		}).next().on(ace.click_event, function() {
			$(this).prev().focus();
		});

        init_${payer_cost_random}();
	})

	function init_${payer_cost_random}(){
		init_total_${payer_cost_random}();
		init_detail_${payer_cost_random}();
	}

    function init_total_${payer_cost_random}(){
        var cost_type_text = $("#filter_payer_cost_type_${payer_cost_random} option:selected").text();
        var cost_count_type = $("#filter_payer_cost_count_type_${payer_cost_random}").val();
        var cost_type = $("#filter_payer_cost_type_${payer_cost_random}").val();
        var start_date = $("#filter_start_date_${payer_cost_random}").val();
        var end_date = $("#filter_end_date_${payer_cost_random}").val();

        $("#payer_cost_total_spin_${payer_cost_random}").show();
        $("#payer_cost_total_${payer_cost_random}").hide();
        $("#payer_cost_total_title_${payer_cost_random}").find("div").html("<spring:message code='payer.cost.title.distribution'/>"+'('+cost_type_text+')');

        $.post("${pageContext.request.contextPath}/bi/dfView/payerCost/getTotalData", {
            "studyId": requestParam.studyId,
            "cohortIds": '${cohortIds}',
            "costType": cost_type,
            "startDate": start_date,
            "endDate": end_date
        }, function(data) {
            var self_paid = 0;
            var individual_self_paid = 0;
            var other_paid = 0;
            if(data.success) {
                $("#payer_cost_total_spin_${payer_cost_random}").hide();
                $("#payer_cost_total_${payer_cost_random}").show();

                var data_array = data.children;
                for(var i in data_array){
                    var detail_cost = data_array[i];
                    self_paid += (detail_cost.selfpaid == undefined ? 0 : detail_cost.selfpaid);
                    individual_self_paid += (detail_cost.individualselfpaid == undefined ? 0 : detail_cost.individualselfpaid);
                    other_paid += (detail_cost.otherpaid == undefined ? 0 : detail_cost.otherpaid);
                }

                var denominator;
                if(cost_type == 'total' || cost_type == undefined){
                    denominator = 1;
                } else if(cost_count_type == 'visit' || cost_count_type == undefined){
                    denominator = (detail_cost.vcount == undefined ? $(":radio[name='search_type']:checked").parent().find(".patientCount").html() : detail_cost.vcount);
				} else {
                   denominator = $(":radio[name='search_type']:checked").parent().find(".patientCount").html();
                }

                var payer_cost_detail_chart_${payer_cost_random} = echarts.init(document.getElementById('payer_cost_total_${payer_cost_random}'),'myStyle');
                var payer_cost_detail_option_${payer_cost_random} = {
                    tooltip : {
                        trigger: 'item',
                        formatter: function(params){
                            return params.name+": ￥"+params.value+"<br>"+
                                '<spring:message code="payer.cost.tooltip.percentage"/>: '+params.percent+"%";
                        }
                    },
                    legend: {
                        top:50,
                        left:'left',
                        data: ['<spring:message code="payer.cost.distribution.paid.self"/>','<spring:message code="payer.cost.distribution.paid.individual.self"/>','<spring:message code="payer.cost.distribution.paid.other"/>']
                    },
                    series : [
                        {
                            name: '<spring:message code="payer.cost.filter.title.cost.type"/>',
                            type: 'pie',
                            radius : '55%',
                            center: ['50%', '60%'],
                            data:[
                                {value: (self_paid/denominator).toFixed(2), name:'<spring:message code="payer.cost.distribution.paid.self"/>'},
                                {value: (individual_self_paid/denominator).toFixed(2), name:'<spring:message code="payer.cost.distribution.paid.individual.self"/>'},
                                {value: (other_paid/denominator).toFixed(2), name:'<spring:message code="payer.cost.distribution.paid.other"/>'}
                            ],
                            label:{
                                normal:{
                                    position:'outside',
                                    formatter:'{b}: {d}%'
                                }
                            },
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                };
                payer_cost_detail_chart_${payer_cost_random}.setOption(payer_cost_detail_option_${payer_cost_random}, true);
            } else {
                $("#payer_cost_total_spin_${payer_cost_random}").html("<spring:message code='dashboard.data.empty'/>");
            }
        });
    }
	
	function init_detail_${payer_cost_random}(){
        var cost_type_text = $("#filter_payer_cost_type_${payer_cost_random} option:selected").text();
        var cost_type = $("#filter_payer_cost_type_${payer_cost_random}").val();
        var cost_count_type = $("#filter_payer_cost_count_type_${payer_cost_random}").val();
        var start_date = $("#filter_start_date_${payer_cost_random}").val();
        var end_date = $("#filter_end_date_${payer_cost_random}").val();

        $("#payer_cost_detail_spin_${payer_cost_random}").show();
        $("#payer_cost_detail_${payer_cost_random}").hide();
        $("#payer_cost_detail_legend_${payer_cost_random}").hide();
        $("#payer_cost_detail_title_${payer_cost_random}").find("div").html("<spring:message code='payer.cost.title.breakdown'/>"+'('+cost_type_text+')');

		$.post("${pageContext.request.contextPath}/bi/dfView/payerCost/getDetailData", {
			"studyId" : requestParam.studyId,
			"cohortIds" : '${cohortIds}',
			"costType" : cost_type,
			"startDate" : start_date,
			"endDate" : end_date
		}, function(data) {
			if (data.success && data.children.length > 0) {
                $("#payer_cost_detail_spin_${payer_cost_random}").hide();
				$("#payer_cost_detail_legend_${payer_cost_random}").show();
				$("#payer_cost_detail_${payer_cost_random}").show();
				$("#payer_cost_detail_legend_${payer_cost_random}").html("");
                $("#payer_cost_detail_${payer_cost_random}").html("");

				var diameter = ${empty viewChartHeight?600:viewChartHeight},
				color = d3.scale.category20c(),
				bubble = d3.layout.pack().sort(null).size([ diameter, diameter ]).padding(1.5),
				svg = d3.select("#payer_cost_detail_${payer_cost_random}").append("svg").attr("width", diameter).attr("height", diameter),
				node = svg.selectAll(".node").data(bubble.nodes(classes_${payer_cost_random}(data, cost_type, cost_count_type))
                    .filter(function(d) {
					    return !d.children;
				    }))
				    .enter().append("g").attr("class", "node").attr("transform", function(d) {
					    return "translate(" + d.x + "," + d.y + ")";
				    });
				node.append("title").text(function(d) {
					return '<spring:message code="payer.cost.filter.title.cost.type"/>: ' + d.payerCostName + "\n" + d.totalCost + "\n" + d.averageCostPerVisit + "\n" + d.averageCostPerPatient + "\n" + d.patientNumber;
				});

				node.append("circle").attr("r", function(d) {
				    return d.r;
				}).style("fill", function(d) {
					add_legend_node_${payer_cost_random}(color(d.value), d.payerCostName);
                    return color(d.value);
				});

				node.append("text")
                    .attr("dy", ".3em")
                    .style("text-anchor", "middle")
				    .text(function(d) {
					    return d.className.substring(0, d.r / 3);
				    });
				
				if(globalColor=='white'){
					$("#payer_cost_detail_${payer_cost_random} text").css("fill",'black');
                } else {
					$("#payer_cost_detail_${payer_cost_random} text").css("fill",'white');
                }
			} else {
                $("#payer_cost_detail_spin_${payer_cost_random}").html("<spring:message code='dashboard.data.empty'/>");
                $("#payer_cost_detail_spin_${payer_cost_random}").show();
                $("#payer_cost_detail_${payer_cost_random}").hide();
                $("#payer_cost_detail_legend_${payer_cost_random}").hide();
            }
		});
	}

	function classes_${payer_cost_random}(root, cost_type, cost_count_type) {
		var classes = [];
		function recurse(name, node, cost_type) {
			if (node.children) {
				node.children.forEach(function(child) {
					recurse(node.name, child, cost_type);
				})
			} else {
                var patientNumber = $(":radio[name='search_type']:checked").parent().find(".patientCount").html();
				var denominator;
				if(cost_type=='total'||cost_type==undefined){
					denominator = 1;
				} else if(cost_count_type=='visit'||cost_count_type==undefined){
                    denominator = node.vcount;
				} else {
                    denominator = patientNumber;
				}

				classes.push({
					className: node.item + ": " + "￥" + (node.amount/denominator).toFixed(2),
					payerCostName: node.item,
					value: node.amount,
					totalCost: "<spring:message code='payer.cost.filter.title.cost.type'/>: ￥" + node.amount.toFixed(2),
					averageCostPerVisit: "<spring:message code='payer.cost.average.per.visit'/>: ￥" + (node.amount/node.vcount).toFixed(2),
                    averageCostPerPatient: "<spring:message code='payer.cost.average.per.patient'/>: ￥" + (node.amount/patientNumber).toFixed(2),
					patientNumber: "<spring:message code='payer.cost.patient.number'/>: " + patientNumber,
                    visitNumber: "<spring:message code='payer.cost.visit.number'/>: " + patientNumber,
				})
			};
		}
		recurse(null, root, cost_type);
		return {
			children : classes
		};
	}
	
	function add_legend_node_${payer_cost_random}(color, name){
		var node = '<span style="background-color: ' + color + '; margin: 0px 5px; border-radius: 25px;">&nbsp;&nbsp;&nbsp;&nbsp;</span><span>' + name + '</span>';
		$("#payer_cost_detail_legend_${payer_cost_random}").append($(node));
	}
	

	
</script>

