<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="/WEB-INF/views/include/bitaglib.jsp"%>
<c:set value="<%=(int)(Math.random()*1000) %>" var="treatment_random" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><spring:message code="layout.head.analytics" /></title>
<script type="text/javascript">
	window.jQuery || document.write("<script src='${ctxStatic }/assets/js/jquery-2.0.3.min.js'>"+"<"+"/script>");
</script>
	<script src='${ctxStatic }/assets/js/typeahead-bs2.min.js'></script>
	<link href="${pageContext.request.contextPath}/static/assets/css/base.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div id="tp_local_search_div_${treatment_random}" class="input-group" style="display: none;">
		<input type="text" class="form-control search-query" data-provide="typeahead" autocomplete="off"
			placeholder='<spring:message code="treatment.path.code"/>' id="tp_local_search_input_${treatment_random}" /> <span
			class="input-group-btn">
			<button type="button" class="btn btn-purple btn-sm"
				id="tp_local_search_button_${treatment_random}">
				<i class="icon-search icon-on-right bigger-110"></i>
				<spring:message code="study.management.search"/>
			</button>
		</span>
	</div>
	<br>
	<div class="chart text-center" id="no_chart_div_${treatment_random}">
		<span class="icon-rwe-lg-no-chart"></span>
	</div>
	<div class="alert alert-warning" id="error_chart_show_div_${treatment_random}" style="display: none; margin-top: 10px; margin-bottom: 10px;">
		<span id="error_chart_show_info_${treatment_random}"></span> <br>
	</div>
	<div class="chartShowDiv row" style="display: none;" id="treatment_chart_show_div_${treatment_random}">
		<div class="chartShow-left">
			<div id="treatment_path_${treatment_random}" style="height: ${empty viewChartHeight?500:viewChartHeight}px;"></div>
		</div>
	</div>
	<div class="chartShowDiv row" style="display: none;" id="lab_test_chart_show_div_${treatment_random}">
		<div class="chartShow-left">
			<div id="lab_test_${treatment_random}" style="height: ${empty viewChartHeight?500:viewChartHeight}px;"></div>
		</div>
	</div>
</body>

<script>
 var filter_widget_main_str_${treatment_random} =
	 '<div class="row">'
	+'	<div class="col-xs-12">'
	+'		<h5 class="header smaller lighter blue no-margin">'
	+'			<spring:message code="treatment.path.shape"/>：'
	+'			<select id="shapeSelect_${treatment_random}" style="width:150px;">'
	+'				<option value="0">--</option>'
	+'				<option value="1"><spring:message code="treatment.path.drug"/></option>'
	+'				<option value="2"><spring:message code="treatment.path.dosage"/></option>'
	+'			</select>'
	+'		</h5>'
	+'		<div class="orderByGroup" style="font-size:14px;">'
	+'			<div id="shapeList_${treatment_random}"></div>'
	+'			<a style="cursor: pointer; display: none;" id="shapeListA_${treatment_random}"'
	+'				onclick="hideMore_${treatment_random}(this)">More&gt;&gt;</a>'
	+'			<div id="shapeListMore_${treatment_random}" style="display: none;"></div>'
	+'		</div>'
	+'	</div>'
	+'</div>'
	+'<div class="row">'
	+'	<div class="col-xs-12">'
	+'		<h5 class="header smaller lighter blue no-margin">'
	+'			<spring:message code="treatment.path.color"/>：'
	+'			<select id="colorSelect_${treatment_random}" style="width:150px;">'
	+'				<option value="0">--</option>'
	+'				<option value="1" selected="selected"><spring:message code="treatment.path.drug"/></option>'
	+'				<option value="2"><spring:message code="treatment.path.dosage"/></option>'
	+'			</select>'
	+'		</h5>'
	+'		<div class="orderByGroup" style="font-size:14px;">'
	+'			<div id="colorList_${treatment_random}"></div>'
	+'			<a style="cursor: pointer; display: none;" id="colorListA_${treatment_random}"'
	+'				onclick="hideMore_${treatment_random}(this)">More&gt;&gt;</a>'
	+'			<div id="colorListMore_${treatment_random}" style="display: none;"></div>'
	+'		</div>'
	+'	</div>'
	+'</div>'
	+'<div class="row">'
	+'	<div class="col-xs-12">'
	+'		<h5 class="header smaller lighter blue no-margin">'
	+'			<spring:message code="treatment.path.size"/>：'
	+'			<select id="sizeSelect_${treatment_random}" style="width:150px;">'
	+'				<option value="0">--</option>'
	+'				<option value="1"><spring:message code="treatment.path.drug"/></option>'
	+'				<option value="2"><spring:message code="treatment.path.dosage"/></option>'
	+'			</select>'
	+'		</h5>'
	+'		<div class="orderByGroup" style="font-size:14px;">'
	+'			<div id="sizeList_${treatment_random}"></div>'
	+'			<a style="cursor: pointer; display: none;" id="sizeListA_${treatment_random}"'
	+'				onclick="hideMore_${treatment_random}(this)">More&gt;&gt;</a>'
	+'			<div id="sizeListMore_${treatment_random}" style="display: none;"></div>'
	+'		</div>'
	+'	</div>'
	+'</div>' ;


	var symbol_arr = [
			'circle',
			'rect',
			'triangle',
			'diamond',
			'arrow',
			'path://d="M472,185H287V0H185v185H0v102h185v185h102V287h185V185z"',
			'path://d="M165.439448 62.597687 943.022199 511.536442 165.439448 960.473151Z"',//右三角
			'path://d="M501.050329 871.84555C501.050329 871.84555 245.422002 1008.897959 245.422002 1008.897959 214.111543 1025.659565 177.518135 998.553337 183.492569 963.010987 183.492569 963.010987 232.311439 672.78073 232.311439 672.78073 234.690148 658.674428 230.098685 644.263872 220.030658 634.251163 220.030658 634.251163 13.221201 428.71404 13.221201 428.71404-12.087165 403.543971 1.88084 359.676136 36.870002 354.476166 36.870002 354.476166 322.702411 312.157259 322.702411 312.157259 336.587438 310.082803 348.619285 301.176471 354.842653 288.342501 354.842653 288.342501 482.656817 24.250393 482.656817 24.250393 498.312046-8.083464 543.535192-8.083464 559.190422 24.250393 559.190422 24.250393 687.004585 288.342501 687.004585 288.342501 693.227954 301.176471 705.232141 310.082803 719.144827 312.157259 719.144827 312.157259 1004.949577 354.476166 1004.949577 354.476166 1039.966398 359.676136 1053.934403 403.543971 1028.598378 428.71404 1028.598378 428.71404 821.788921 634.251163 821.788921 634.251163 811.720894 644.263872 807.12943 1 658.674428 809.50814 672.78073 809.50814 672.78073 858.32701 963.010987 858.32701 963.010987 864.329104 998.553337 827.735696 1025.659565 796.425237 1008.897959 796.425237 1008.897959 540.769251 871.84555 540.769251 871.84555 528.350173 865.179631 513.497066 865.179631 501.050329 871.84555Z"',//五角星
			'path://d="M819.380706 683.610353l-42.526118-42.646588 99.026824-98.846118-333.763765 0 0 333.763765 98.846118-99.026824 42.646588 42.526118-171.610353 171.971765-171.610353-171.971765 42.646588-42.526118 98.846118 99.026824 0-333.763765-333.763765 0 99.026824 98.846118-42.526118 42.646588-171.971765-171.610353 171.971765-171.610353 42.526118 42.646588-99.026824 98.846118 333.763765 0 0-333.763765-98.846118 99.026824-42.646588-42.526118 171.610353-171.971765 171.610353 171.971765-42.646588 42.526118-98.846118-99.026824 0 333.763765 333.763765 0-99.026824-98.846118 42.526118-42.646588 171.971765 171.610353-171.971765 171.610353z"',//十字箭头
			'path://d="M957.142857 475.428571q0 30.285714-21.142857 51.428571l-372 372.571429q-22.285714 21.142857-52 21.142857-30.285714 0-51.428571-21.142857l-372-372.571429q-21.714286-20.571429-21.714286-51.428571 0-30.285714 21.714286-52l42.285714-42.857143q22.285714-21.142857 52-21.142857 30.285714 0 51.428571 21.142857l168 168 0-402.285714q0-29.714286 21.714286-51.428571t51.428571-21.714286l73.142857 0q29.714286 0 51.428571 21.714286t21.714286 51.428571l0 402.285714 168-168q21.142857-21.142857 51.428571-21.142857 29.714286 0 52 21.142857l42.857143 42.857143q21.142857 22.285714 21.142857 52z"',//下箭头
			'path://d="M931.892721 473.255581l0 73.094762c0 19.98517-6.2831 37.688367-18.839067 51.963489-11.993149 14.274098-27.987425 21.12104-47.972595 21.12104l-402.00584 0 167.310563 167.893848c14.275121 13.70207 21.704324 30.832216 21.704324 51.390437s-7.429203 37.688367-21.704324 51.390437l-42.825364 43.398416c-14.275121 14.275121-31.405267 21.131273-51.40067 21.131273-19.98517 0-37.115316-6.856152-51.963489-21.131273l-371.746676-372.319727c-14.275121-14.275121-21.131273-31.405267-21.131273-51.390437s6.856152-37.115316 21.131273-51.963489l371.746676-371.173624c14.275121-14.275121 31.978319-21.704324 51.963489-21.704324 19.995403 0 37.125549 7.429203 51.40067 21.704324l42.825364 42.252313c14.275121 14.275121 21.704324 31.978319 21.704324 51.963489 0 19.98517-7.429203 37.688367-21.704324 51.963489l-167.310563 167.320796 402.00584 0c19.98517 0 35.979446 7.41897 47.972595 21.694091C925.609621 436.131055 931.892721 453.271434 931.892721 473.255581z"',//左箭头
			'path://d="M861.692 557.525 536.014 883.204c-12.007 11.506-28.516 18.51-45.525 18.51s-33.018-7.004-45.025-18.51l-37.521-37.521c-12.006-12.007-19.01-28.516-19.01-45.525s7.004-33.519 19.01-45.525l146.581-146.58L202.33 608.053c-36.02 0-58.532-30.017-58.532-64.035l0-64.035c0-34.019 22.512-64.035 58.532-64.035l352.193 0L407.943 268.867c-12.006-11.506-19.01-28.016-19.01-45.025s7.004-33.519 19.01-45.025l37.521-37.521c12.007-12.006 28.016-19.01 45.025-19.01s33.519 7.004 45.525 19.01l325.679 325.679c12.006 11.506 18.51 28.015 18.51 45.024S873.698 545.519 861.692 557.525z"',//右箭头
			'path://d="M901.5 549.5q0 25.5-18.5 45l-37.5 37.5q-19 19-45.5 19-27 0-45-19l-147-146.5 0 352q0 26-18.75 42.25t-45.25 16.25l-64 0q-26.5 0-45.25-16.25t-18.75-42.25l0-352-147 146.5q-18 19-45 19t-45-19l-37.5-37.5q-19-19-19-45 0-26.5 19-45.5l325.5-325.5q17.5-18.5 45-18.5 27 0 45.5 18.5l325.5 325.5q18.5 19.5 18.5 45.5z"',//上箭头
			'path://d="M76.30692 268.344654l0 545.101909 405.560806 145.390321L481.867726 412.95317l-107.696838-38.609343L76.30692 268.344654zM511.384993 63.786769 91.605348 216.038358l-1.163499 0.420579 222.719524 79.012541 198.22362 71.059406 417.261289-151.161768L511.384993 63.786769zM540.90226 412.95317l0 545.883715 405.561829-145.390321L946.464089 266.569218 540.90226 412.95317z"',//盒子
			'path://d="M874.069333 149.973333c-99.84-99.797333-262.229333-99.797333-362.112 0l-361.984 362.026667c-99.797333 99.84-99.84 262.229333 0 362.026667s262.186667 99.797333 362.026667 0l362.069333-362.026667c99.754667-99.84 99.84-262.229333 0-362.026667zM813.738667 210.304c66.517333 66.56 66.517333 174.848 0 241.365333l-181.077333 180.992-241.322667-241.322667 180.992-181.034667c66.602667-66.56 174.848-66.56 241.408 0z"',//药片
			'path://d="M327.23844 128.098656C309.562037 128.104749 288.083906 140.531077 279.269013 155.847863L90.46157 483.920301C81.64519 499.239672 81.619203 524.092213 90.400217 539.424228L278.482013 867.822835C287.264509 883.157437 308.712742 895.60674 326.374199 895.629114L698.643947 896.100716C716.311575 896.123098 737.729869 883.69628 746.484567 868.341974L933.651663 540.081547C942.40568 524.728435 942.411619 499.82228 933.674423 484.468781L746.623984 155.773053C737.882537 140.412082 716.474668 127.964492 698.790236 127.970587L327.23844 128.098656Z"',//六边形
			'path://d="M511.504208 864.088964 64.091202 224.924749l894.837268 0L511.504208 864.088964zM511.504208 864.088964"',//三角形下
			'path://d="M512 909.994667l-61.994667-56.021333q-105.984-96-153.984-141.994667t-107.008-114.005333-80.981333-123.008-22.016-112.981333q0-98.005333 66.986667-166.016t166.997333-68.010667q116.010667 0 192 89.984 75.989333-89.984 192-89.984 100.010667 0 166.997333 68.010667t66.986667 166.016q0 77.994667-52.010667 162.005333t-112.981333 146.005333-198.997333 185.984z"',//心形
	];

	var color_arr = ['#FD026C', '#6B9494', '#A5D22D', '#4682B8', '#F5CC0A', '#B97C46', '#61a0a8', '#bda29a', '#44525d', '#c4ccd3', '#CD69C9', '#CAE1FF', '#8968CD'];

	var icon_class_arr = ['iconBy_circle', 'iconBy_rect', 'iconBy_tri', 'iconBy_dimond', 'iconBy_arrow', 'iconBy_cross', 'iconBy_triRight', 'iconBy_star', 'iconBy_crosssArw', 'iconBy_arwDown', 'iconBy_arwLeft', 'iconBy_arwRight', 'iconBy_arwTop', 'iconBy_box', 'iconBy_pill', 'iconBy_hexagon', 'iconBy_triDown']

	//1:药物形状分类 2:剂量形状分类
	var shape_index_${treatment_random} = 0;

	//1:药物颜色分类 2:剂量颜色分类
	var color_index_${treatment_random} = 0;

	//1:药物大小分类 2:剂量大小分类
	var size_index_${treatment_random} = 0;

	//后台传的用药数组值
	var treatment_list_${treatment_random} = null;

	//治疗集合
	var treatmentGuidSet_${treatment_random} = null;

	//药名数组
	var drug_set_${treatment_random} = null;

	//计量数组
	var dosage_set_${treatment_random} = null;

	//x轴数据
	var x_axis_value_arr_${treatment_random} = new Array("");

	//y轴数据
	var y_axis_value_arr_${treatment_random} = null;

 	var y_axis_lab_test_value_arr_${treatment_random} = null;

 	var tool_tip_lab_test_value_arr_${treatment_random} = null;
	//后台数据
	var ajax_data_${treatment_random} = null;
	
	var x_scale_${treatment_random} = 0;
	
	var data_zoom_start_${treatment_random};
	
	var data_zoom_end_${treatment_random};
	
	var resize_flag_${treatment_random} = false;

	var $filter_widget_main_${treatment_random};

	var style_${treatment_random} = '${style}';

	$(function() {
 		//初始化toolbar中的下拉数据
        $filter_widget_main_${treatment_random} = $("#treatment_chart_show_div_${treatment_random}").closest(".widget-box").find(".widget-main:first");
        $filter_widget_main_${treatment_random}.html(filter_widget_main_str_${treatment_random});

		//取消下拉filter点击自动隐藏的功能
        $filter_widget_main_${treatment_random}.closest('ul.dropdown-menu').on('click', '[data-stopPropagation]',
			function (e) {
				e.stopPropagation();
			});
		
		//初始化主要参数，患者病历号、studyId;
		var tp_local_studyId = '';
		var tp_local_patientCode = '';
		
		try{
			tp_local_studyId = studyId;
		} catch(ex){
			tp_local_studyId = '${studyId}';
		}

		//初始化数据和视图
		function init_${treatment_random}(flag) {
			//判断后台数据是否已读取到内存中，没有则去后台读取
			if (ajax_data_${treatment_random} == null || flag) {
				resize_flag_${treatment_random} = false;
				get_ajax_data_${treatment_random}();
                data_zoom_start_${treatment_random} = undefined;
				data_zoom_end_${treatment_random} = undefined;
			} else {
				resize_flag_${treatment_random} = true;
				init_chart_${treatment_random}();
			}
		}
		
		$("#tp_local_search_button_${treatment_random}").click(function(){
			var inpatient_num = $("#tp_local_search_input_${treatment_random}").val();
			if($.trim(inpatient_num)!=''){
				tp_local_patientCode = inpatient_num;
				init_${treatment_random}(true);
			}
		});
		
		$("#tp_local_search_input_${treatment_random}").typeahead({
			source:function(query, process){
				 $.post('${ctxbi}/dfView/treatment/matchPatientCode',
						{'patientCode':query, 'studyId':tp_local_studyId},
						function (data) {
			            	process(data.patientCodeList);
						}
			     );
			}
		});
		
		if($.trim(tp_local_patientCode)=='' && ${needChoosePatient}){
			$("#tp_local_search_div_${treatment_random}").show();
			$(".chartShow-left").addClass("col-xs-12");
			$(".chartShow-right").addClass("col-xs-2");
		} else {
			$(".chartShow-left").removeClass("col-xs-12");
			$(".chartShow-right").removeClass("col-xs-2");
			init_${treatment_random}(true);
		}
		

		$("#shapeSelect_${treatment_random}").change(function() {
			shape_index_${treatment_random} = $(this).val();
			init_${treatment_random}();
		})

		$("#colorSelect_${treatment_random}").change(function() {
			color_index_${treatment_random} = $(this).val();
			init_${treatment_random}();
		})

		$("#sizeSelect_${treatment_random}").change(function() {
			size_index_${treatment_random} = $(this).val();
			init_${treatment_random}();
		})

		function init_chart_${treatment_random}() {
            //后台传的用药数组值
            treatment_list_${treatment_random} = ajax_data_${treatment_random}.treatmentList;

            //治疗集合
            treatmentGuidSet_${treatment_random} = ajax_data_${treatment_random}.treatmentGuidSet;

            //药名数组
            drug_set_${treatment_random} = ajax_data_${treatment_random}.drugSet;

            //计量数组
            dosage_set_${treatment_random} = ajax_data_${treatment_random}.dosageSet;

            init_node_shape_color_div_${treatment_random}();
			//x轴
            x_axis_value_arr_${treatment_random} = new Array("");
			//y轴去重 去除重复的用药
            y_axis_value_arr_${treatment_random} = drug_set_${treatment_random};

            y_axis_lab_test_value_arr_${treatment_random} = ajax_data_${treatment_random}.yAxisLabTestValueList;

            tool_tip_lab_test_value_arr_${treatment_random} = ajax_data_${treatment_random}.toolTipLabTestValueList;

			//循环用药数组
            for ( var i in treatment_list_${treatment_random}) {
                var treatment = treatment_list_${treatment_random}[i];
                var x_axis_value = get_x_axis_value_${treatment_random}(treatment);
                if (x_axis_value_arr_${treatment_random}.indexOf(x_axis_value) == -1)
                    x_axis_value_arr_${treatment_random}.push(x_axis_value);

            }
            // 指定treatment path图表的配置项和数据
            var schema = [ {
                name : 'date',
                index : 0,
                text : '<spring:message code="treatment.path.date"/>'
            }, {
                name : 'name',
                index : 1,
                text : '<spring:message code="treatment.path.type"/>'
            }, {
                name : 'drug',
                index : 2,
                text : '<spring:message code="treatment.path.drug"/>'
            }, {
                name : 'count',
                index : 3,
                text : '<spring:message code="treatment.path.dosage"/>'
            }, {
                name : 'endDate',
                index : 4,
                text : '<spring:message code="treatment.path.endDate"/>'
            } ];

            var inner_data_zoom_start = x_axis_value_arr_${treatment_random}.length>11?(x_axis_value_arr_${treatment_random}.length-11)/x_axis_value_arr_${treatment_random}.length*100:0;

			var treatment_path_chart = echarts.init(document.getElementById('treatment_path_${treatment_random}'),'myStyle');
            treatment_path_chart.on("dataZoom",function(params){
				var start = params.start;
				var end = params.end;
				x_scale_${treatment_random} = (end-start)/100;
				
				data_zoom_start_${treatment_random} = start;
				data_zoom_end_${treatment_random} = end;
			})
			var treatment_path_option;
			if(style_${treatment_random}=='black'){
                treatment_path_option = {
					textStyle: {
			       		color: '#eee'
					},
					title : {
						textStyle: {
							color: '#eee'
						},
						text : '<spring:message code="treatment.path"/>',
						left : 'center'
					},
					legend : {
						show : false,
						y : 'top',
						data : y_axis_value_arr_${treatment_random},
						top : '5%',
						itemWidth : 14,
						textStyle : {
							color : '#000',
							fontSize : 12
						}
					},
					grid : {
						x : '20%',
						bottom : '22%',
						top : '18%'
					},
					tooltip : {
						show:<c:if test="${isPad eq '1'}">false</c:if><c:if test="${isPad ne '1'}">true</c:if>,
						padding : 10,
						backgroundColor : '#222',
						borderColor : '#000',
						borderWidth : 1,
						formatter : function(params) {
							if (params.value == undefined) {
								return 0;
							} else {
								return schema[0].text + '：' + params.value[0] + '<br>' + schema[1].text + '：' + params.value[2] + '<br>' + schema[2].text + '：' + params.value[3] + '<br>' + schema[3].text + '：' + params.value[4] + '<br>' + schema[4].text + '：' + params.value[5];
							}
						}
					},
					xAxis : {
						type : 'category',
						data : x_axis_value_arr_${treatment_random},
						name:'<spring:message code="treatment.path.dosingDate"/>',
						nameGap: 4,
						boundaryGap : false,
						splitLine : {
							show : true,
							lineStyle : {
								type : 'dashed'
							}
						},
						axisLabel : {
							textStyle: {
								color: '#eee'
							},
							interval: function(index, value){
								var length = parseInt(x_axis_value_arr_${treatment_random}.length * x_scale_${treatment_random});
								if(length > 8){
									var showIndex = parseInt(length / 8);
									if(index%showIndex==0){
										return value;
									}
								}else{
									return value;
								}
							},
							margin: 8,
							rotate : 45
						},
						axisLine: {
							lineStyle: {
								color: '#eee'
							}
						},
						axisTick: {
							lineStyle: {
								color: '#eee'
							}
						}
					},
					yAxis : {
						type : 'category',
						data : y_axis_value_arr_${treatment_random},
						boundaryGap : false,
						name:'<spring:message code="treatment.path.drug"/>',
						splitLine : {
							show : false,
							lineStyle : {
								type : 'dashed'
							}
						},
						axisLine: {
							lineStyle: {
								color: '#eee'
							}
						},
						axisTick: {
							lineStyle: {
								color: '#eee'
							}
						},
						axisLabel: {
							textStyle: {
								color: '#eee'
							}
						}
					},
					series : init_series_${treatment_random}(),
					dataZoom : {
						type : 'slider',
						show : true,
						textStyle: {
							color: '#eee'
						},
						dataBackgroundColor: '#6B668F',
						fillerColor: 'rgba(182,162,222,0.2)',
						handleColor: '#A78ADE',
						start: (resize_flag_${treatment_random} == true && data_zoom_start_${treatment_random} != undefined) ? data_zoom_start_${treatment_random} : inner_data_zoom_start,
						end: (resize_flag_${treatment_random} == true&& data_zoom_end_${treatment_random} != undefined) ? data_zoom_end_${treatment_random} : 100
					}
				};
			} else {
                treatment_path_option = {
					title : {
						text : '<spring:message code="treatment.path"/>',
						left : 'center'
					},
					legend : {
						show : false,
						y : 'top',
						data : y_axis_value_arr_${treatment_random},
						top : '5%',
						itemWidth : 14,
						textStyle : {
							color : '#000000',
							fontSize : 12
						}
					},
					grid : {
						x : '20%',
						bottom : '22%',
						top : '18%'
					},
					tooltip : {
						show:<c:if test="${isPad eq '1'}">false</c:if><c:if test="${isPad ne '1'}">true</c:if>,
						padding : 10,
						backgroundColor : '#222',
						borderColor : '#000',
						borderWidth : 1,
						formatter : function(params) {
							if (params.value == undefined) {
								return 0;
							} else {
                                    return schema[0].text + '：' + params.value[0] + '<br>' + schema[1].text + '：' + params.value[2] + '<br>' + schema[2].text + '：' + params.value[3] + '<br>' + schema[3].text + '：' + params.value[4] + '<br>' + schema[4].text + '：' + params.value[5];
							}
						}
					},
					xAxis : {
						type : 'category',
						data : x_axis_value_arr_${treatment_random},
						name:'<spring:message code="treatment.path.dosingDate"/>',
						nameGap: 4,
						boundaryGap : false,
						splitLine : {
							show : false,
							lineStyle : {
								type : 'dashed'
							}
						},
						axisLabel : {
							interval: function(index, value){
								var length = parseInt(x_axis_value_arr_${treatment_random}.length * x_scale_${treatment_random});
								if(length > 8){
									var showIndex = parseInt(length / 8);
									if(index%showIndex==0){
										return value;
									}
								} else {
									return value;
								}
							},
							margin: 8,
							rotate : 45
						}
					},
					yAxis : {
						type : 'category',
						data : y_axis_value_arr_${treatment_random},
						boundaryGap : false,
						name:'<spring:message code="treatment.path.drug"/>',
						splitLine : {
							show : true,
							lineStyle : {
								type : 'dashed'
							}
						}
					},
					series : init_series_${treatment_random}(),
					dataZoom : {
						type : 'slider',
						show : true,
                        start: (resize_flag_${treatment_random} == true && data_zoom_start_${treatment_random} != undefined) ? data_zoom_start_${treatment_random} : inner_data_zoom_start,
                        end: (resize_flag_${treatment_random} == true&& data_zoom_end_${treatment_random} != undefined) ? data_zoom_end_${treatment_random} : 100
					}
				};
			}

            treatment_path_chart.setOption(treatment_path_option);
		}

		function get_ajax_data_${treatment_random}() {
			$.post("${ctxbi}/dfView/treatment/getTreatmentPath", {
				"patientCode" : tp_local_patientCode,
				"studyId" : tp_local_studyId
			}, function(data) {
				ajax_data_${treatment_random} = data;
				if (data.success) {
					$("#treatment_chart_show_div_${treatment_random}").show();
                    $("#lab_test_chart_show_div_${treatment_random}").show();
					$("#no_chart_div_${treatment_random}").hide();
					$("#error_chart_show_div_${treatment_random}").hide();
					
					init_chart_${treatment_random}();
				} else {
					$("#treatment_chart_show_div_${treatment_random}").hide();
                    $("#lab_test_chart_show_div_${treatment_random}").hide();
					if(${needChoosePatient}){
						$("#error_chart_show_div_${treatment_random}").show();
						$("#error_chart_show_info_${treatment_random}").html("<spring:message code='da.dataframe.noresults'/>");
						$("#no_chart_div_${treatment_random}").hide();
					} else {
						$("#error_chart_show_div_${treatment_random}").hide();
						$("#no_chart_div_${treatment_random}").show();
					}
					
				}
			});
		}

	})

	//根据药物获取x轴值
	function get_x_axis_value_${treatment_random}(treatment) {
        var x_axis = new Date(treatment.visitOn),
            month = '' + (x_axis.getMonth() + 1),
            day = '' + x_axis.getDate(),
            year = x_axis.getFullYear();
        return [year, month, day].join('.');
	}

	function init_series_${treatment_random}() {
		var series = [];

		//循环创建每个节点
		for ( var y_index in y_axis_value_arr_${treatment_random}) {
			if (y_axis_value_arr_${treatment_random}[y_index] == '') {
				continue;
			}

			var drug_name = y_axis_value_arr_${treatment_random}[y_index];
			var item = {
				name : drug_name,
				type : 'scatter',
				data : init_node_${treatment_random}(y_index, drug_name),
				label : {
					normal : {
						show : false,
						position : 'top',
						formatter : 'PD',
					}
				},
				markLine : {
					animation : false,
					lineStyle : {
						normal : {
							type : 'solid'
						}
					},
					label:{
						normal:{
							show:false
						}
					},
					data : init_mark_line_data_${treatment_random}(y_index, drug_name)
				}
			}
            series.push(item);
		}

		return series;
	}

	//初始化节点  yIndex: y轴坐标 drug:药物
	function init_node_${treatment_random}(y_index, drug_name) {
		var item_data = new Array();
		//循环药物数组 找出包含该药物的对象并放入数组
		for ( var i in treatment_list_${treatment_random}) {
			var treatment = treatment_list_${treatment_random}[i];
			if (treatment.drugName == drug_name) {
				var node = new Object();

				var node_value = new Array();
				//x轴
                node_value.push(get_x_axis_value_${treatment_random}(treatment));
                //y轴
                node_value.push(y_axis_value_arr_${treatment_random}[y_index]);
                //药物类型
                node_value.push(treatment.therapyType);
				//药物
                node_value.push(drug_name);
				//计量
                node_value.push(treatment.dailyDose);
                //结束时间
                if(treatment.endDate != undefined){
                    var treatment_end_date = new Date(treatment.endDate),
                        month = '' + (treatment_end_date.getMonth() + 1),
                        day = '' + treatment_end_date.getDate(),
                        year = treatment_end_date.getFullYear();
                    node_value.push([year, month, day].join('.'));
                } else {
                    node_value.push("");
                }

				node.value = node_value;

				init_node_shape_color_${treatment_random}(node, drug_name, treatment.dailyDose);

				item_data.push(node);
			}
		}
		return item_data;
	}

	//初始化连线  yIndex:y轴坐标 drug:药物
	function init_mark_line_data_${treatment_random}(y_index, drug_name) {
		var mark_line_data = new Array();

		var treatment_line_arr = get_treatment_line_arr_${treatment_random}();
		for ( var i in treatment_list_${treatment_random}) {
			var treatment = treatment_list_${treatment_random}[i];
			if (treatment.drugName == drug_name) {
				for ( var index in treatment_line_arr) {

					var treatment_line = treatment_line_arr[index];

					if (treatment_line.guid == treatment.groupId) {
						var node = new Object();
						node.coord = [ x_axis_value_arr_${treatment_random}.indexOf(get_x_axis_value_${treatment_random}(treatment)), parseInt(y_index) ];
						node.symbol = 'none';
						if (treatment_line.arr.length > 1) {
                            treatment_line.arr.pop();
						}
                        treatment_line.arr.push(node);
					}
				}

			}
		}

		for ( var i in treatment_line_arr) {
			var data_arr = treatment_line_arr[i].arr;

			if (data_arr.length > 1) {
                mark_line_data.push(data_arr);
			}
		}

		return mark_line_data;
	}

	//获取治疗连线对象数组 treatmentGuidSet_${treatment_random}:治疗主键集合
	function get_treatment_line_arr_${treatment_random}() {
		var treatment_line_arr = new Array();
		for ( var i in treatmentGuidSet_${treatment_random}) {
			//构建治疗连线对象
			var treatment_line = new Object();
            treatment_line.guid = treatmentGuidSet_${treatment_random}[i];
            treatment_line.arr = new Array();
            treatment_line_arr.push(treatment_line);
		}

		return treatment_line_arr;
	}

	//初始化节点颜色和形状
	function init_node_shape_color_${treatment_random}(node, drug_name, dosage) {
		//判断形状是根据药物还是计量
		if (shape_index_${treatment_random} == 1) {
			var index = loop_min_data_${treatment_random}(drug_set_${treatment_random}.indexOf(drug_name), symbol_arr.length-1);
			node.symbol = symbol_arr[index];
		} else if (shape_index_${treatment_random} == 2){
			var index=loop_min_data_${treatment_random}(dosage_set_${treatment_random}.indexOf(dosage),symbol_arr.length-1);
			node.symbol = symbol_arr[index];
		}
		else{
			node.symbol = symbol_arr[0];
		}

		if (color_index_${treatment_random} == 1) {
			var index = loop_min_data_${treatment_random}(drug_set_${treatment_random}.indexOf(drug_name) - 1, color_arr.length-1);
			node.itemStyle = {
				normal : {
					color : color_arr[index]
				}
			};
		} else if(color_index_${treatment_random} == 2){
			var index = loop_min_data_${treatment_random}(dosage_set_${treatment_random}.indexOf(dosage),color_arr.length-1);
			node.itemStyle = {
				normal : {
					color : color_arr[index]
				}
			};
		} else {
			node.itemStyle = {
				normal : {
					color : color_arr[0]
				}
			};
		}

		//判断大小是根据药物还是计量
		if (size_index_${treatment_random} == 1) {
				node.symbolSize=drug_set_${treatment_random}.indexOf(drug_name)%6*8+1;
		} else if(size_index_${treatment_random} == 2){
			if(dosage_set_${treatment_random}.length<=1){
				node.symbolSize = 1;
			} else {
				var ave_dosage=(dosage_set_${treatment_random}[0]+dosage_set_${treatment_random}[dosage_set_${treatment_random}.length-1])/4;
				node.symbolSize=Math.ceil(dosage/ave_dosage)*8;
			}
		}else{
			node.symbolSize = 8;
		}
	}

	//初始化节点颜色和形状的div
	function init_node_shape_color_div_${treatment_random}() {
		shape_index_${treatment_random} = $("#shapeSelect_${treatment_random}  option:selected").val();
		color_index_${treatment_random} = $("#colorSelect_${treatment_random}  option:selected").val();
		size_index_${treatment_random} = $("#sizeSelect_${treatment_random}  option:selected").val();
		//默认形状选择3和颜色选择1
		//这里由于要求rwe-web调用此页面时给定一个默认值
		if(!shape_index_${treatment_random}){
            shape_index_${treatment_random} = 1;
		}
		if(!color_index_${treatment_random}){
            color_index_${treatment_random} = 1;
		}
		//判断形状是根据药物还是计量
		if (shape_index_${treatment_random} == 1) {
			$("#shapeList_${treatment_random}").html("");
			$("#shapeListMore_${treatment_random}").html("");
			$("#shapeListA_${treatment_random}").hide();
			$("#shapeListMore_${treatment_random}").hide();
			for ( var i in drug_set_${treatment_random}) {
				if (drug_set_${treatment_random}[i] == "") {
					continue;
				}
				var index = loop_min_data_${treatment_random}(i, symbol_arr.length-1);
				if(i<6){
					$("#shapeList_${treatment_random}").append('<i class="icon '+icon_class_arr[index]+'"></i>' + drug_set_${treatment_random}[i] + '<br>');
				}else{
					$("#shapeListA_${treatment_random}").show();
					$("#shapeListMore_${treatment_random}").append('<i class="icon '+icon_class_arr[index]+'"></i>' + drug_set_${treatment_random}[i] + '<br>');
				}
			}
		} else if(shape_index_${treatment_random} == 2){
			$("#shapeList_${treatment_random}").html("");
			$("#shapeListMore_${treatment_random}").html("");
			$("#shapeListA_${treatment_random}").hide();
			$("#shapeListMore_${treatment_random}").hide();
			for ( var i in dosage_set_${treatment_random}) {
				var index = loop_min_data_${treatment_random}(i, symbol_arr.length-1);
				if(i<5){
					$("#shapeList_${treatment_random}").append('<i class="icon '+icon_class_arr[index]+'"></i>' + dosage_set_${treatment_random}[i] + '<br>');
				}else{
					$("#shapeListA_${treatment_random}").show();
					$("#shapeListMore_${treatment_random}").append('<i class="icon '+icon_class_arr[index]+'"></i>' + dosage_set_${treatment_random}[i] + '<br>');
				}
			}
		} else {
			$("#shapeList_${treatment_random}").html("");
			$("#shapeListMore_${treatment_random}").html("");
			$("#shapeListA_${treatment_random}").hide();
			$("#shapeListMore_${treatment_random}").hide();
			for ( var i in drug_set_${treatment_random}) {
				if (drug_set_${treatment_random}[i] == "") {
					continue;
				}
				var index = loop_min_data_${treatment_random}(i, symbol_arr.length-1);
				if(i<6){
					$("#shapeList_${treatment_random}").append('<i class="icon '+icon_class_arr[0]+'"></i>' + drug_set_${treatment_random}[i] + '<br>');
				}else{
					$("#shapeListA_${treatment_random}").show();
					$("#shapeListMore_${treatment_random}").append('<i class="icon '+icon_class_arr[0]+'"></i>' + drug_set_${treatment_random}[i] + '<br>');
				}
			}
		}
		

		if (color_index_${treatment_random} == 1) {
			$("#colorList_${treatment_random}").html("");
			$("#colorListMore_${treatment_random}").html("");
			$("#colorListA_${treatment_random}").hide();
			$("#colorListMore_${treatment_random}").hide();
			for ( var i in drug_set_${treatment_random}) {
				if (drug_set_${treatment_random}[i] == "") {
					continue;
				}
				var index = loop_min_data_${treatment_random}(i, color_arr.length-1);
				if(i<6){
					$("#colorList_${treatment_random}").append('<i class="icon iconBg_'+i+'" ></i>' + drug_set_${treatment_random}[i] + '<br>');
				}else{
					$("#colorListA_${treatment_random}").show();
					$("#colorListMore_${treatment_random}").append('<i class="icon iconBg_'+i+'" ></i>' + drug_set_${treatment_random}[i] + '<br>');
				}
			}

		} else if(color_index_${treatment_random} == 2){
			$("#colorList_${treatment_random}").html("");
			$("#colorListMore_${treatment_random}").html("");
			$("#colorListA_${treatment_random}").hide();
			$("#colorListMore_${treatment_random}").hide();
			for ( var i in dosage_set_${treatment_random}) {
				var index = loop_min_data_${treatment_random}(i, color_arr.length-1);
				if(i<5){
					$("#colorList_${treatment_random}").append('<i class="icon iconBg_' + (index+1) + '" ></i>' + dosage_set_${treatment_random}[i] + '<br>');
				}else{
					$("#colorListA_${treatment_random}").show();
					$("#colorListMore_${treatment_random}").append('<i class="icon iconBg_' + (index+1) + '" ></i>' + dosage_set_${treatment_random}[i] + '<br>');
				}
			}
		} else {
			$("#colorList_${treatment_random}").html("");
			$("#colorListMore_${treatment_random}").html("");
			$("#colorListA_${treatment_random}").hide();
			$("#colorListMore_${treatment_random}").hide();
			for ( var i in drug_set_${treatment_random}) {
				if (drug_set_${treatment_random}[i] == "") {
					continue;
				}
				var index=loopMinData_${treatment_random}(i, color_arr.length-1);
				if(i<6){
					$("#colorList_${treatment_random}").append('<i class="icon iconBg_'+1+'" ></i>' + drug_set_${treatment_random}[i] + '<br>');
				}else{
					$("#colorListA_${treatment_random}").show();
					$("#colorListMore_${treatment_random}").append('<i class="icon iconBg_'+1+'" ></i>' + drug_set_${treatment_random}[i] + '<br>');
				}
			}
		}

		if (size_index_${treatment_random} == 1) {
			$("#sizeList_${treatment_random}").html("");
			$("#sizeListMore_${treatment_random}").html("");
			$("#sizeListA_${treatment_random}").hide();
			$("#sizeListMore_${treatment_random}").hide();
			for ( var i in drug_set_${treatment_random}) {
				if (drug_set_${treatment_random}[i] == "") {
					continue;
				}
				if(i<6){
					$("#sizeList_${treatment_random}").append('<span>' + i + '</span> - ' + drug_set_${treatment_random}[i] + '<br>');
				}else{
					$("#sizeListA_${treatment_random}").show();
					$("#sizeListMore_${treatment_random}").append('<span>' + i + '</span> - ' + drug_set_${treatment_random}[i] + '<br>');
				}
			}

		} else {
			$("#sizeList_${treatment_random}").html("");
			$("#sizeListMore_${treatment_random}").html("");
			$("#sizeListA_${treatment_random}").hide();
			$("#sizeListMore_${treatment_random}").hide();
			for ( var i in dosage_set_${treatment_random}) {
				if(i<5){
					$("#sizeList_${treatment_random}").append('<span>' + (parseInt(i) + 1) + '</span> - ' + dosage_set_${treatment_random}[i] + '<br>');
				}else{
					$("#sizeListA_${treatment_random}").show();
					$("#sizeListMore_${treatment_random}").append('<span>' + (parseInt(i) + 1) + '</span> - ' + dosage_set_${treatment_random}[i] + '<br>');
				}
			}
		}

	}

	function loop_min_data_${treatment_random}(data, len) {
		var i = parseInt(data);
		return i - len * (Math.floor(i / len));
	}

</script>
</html>