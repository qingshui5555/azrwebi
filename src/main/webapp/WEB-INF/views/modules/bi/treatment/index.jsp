
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="/WEB-INF/views/include/bitaglib.jsp"%>
<c:set value="<%=(int)(Math.random()*1000) %>" var="treatmentRandom" />   

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><spring:message code="layout.head.analytics" /></title>
<!-- inline scripts related to this page    -->                                               						 
<script type="text/javascript">
	window.jQuery || document.write("<script src='${ctxStatic }/assets/js/jquery-2.0.3.min.js'>"+"<"+"/script>");
	//(window.echarts&&window.echarts.version=='3.2.3') || document.write("<script src='${ctxStatic }/echarts/echarts3.min.js'>"+"<"+"/script>");
	
	//var head= document.getElementsByTagName('head')[0];
	//var scriptEcharts= document.createElement('script');
	//scriptEcharts.type= 'text/javascript';
	//scriptEcharts.src= '${ctxStatic }/echarts/echarts3.min.js';
	//head.appendChild(scriptEcharts);
</script> 
<%-- <script src='${ctxStatic }/echarts/echarts3.min.js'></script> --%>
	<script src="${ctxStatic}/echarts/echarts.min.js"></script>
<script src='${ctxStatic }/assets/js/typeahead-bs2.min.js'></script>
<%-- <script src="${ctxStatic }/echarts/${style }.js"></script> --%>
<link
	href="${pageContext.request.contextPath}/static/assets/css/base.css"
	rel="stylesheet" type="text/css" />

</head>
<body>

	
	<div id="tp_local_search_div_${treatmentRandom}" class="input-group"
		style="display: none;">
		<input type="text" class="form-control search-query" data-provide="typeahead" autocomplete="off"
			placeholder='<spring:message code="treatment.path.ecode"/>' id="tp_local_search_input_${treatmentRandom}" /> <span
			class="input-group-btn">
			<button type="button" class="btn btn-purple btn-sm"
				id="tp_local_search_button_${treatmentRandom}">
				<i class="icon-search icon-on-right bigger-110"></i>
				<spring:message code="study.management.search"/>
			</button>
		</span>
	</div>


	<br>

	<div class="chart text-center" id="noChartDiv_${treatmentRandom}">
		<span class="icon-rwe-lg-no-chart"></span>
	</div>
	<div class="alert alert-warning" id="chartShowErrorDiv_${treatmentRandom}"
		style="display: none; margin-top: 10px; margin-bottom: 10px;">
		<span id="chartShowErrorInfo_${treatmentRandom}"></span> <br>
	</div>
	<div class="chartShowDiv row" style="display: none;" id="chartShowDiv_${treatmentRandom}">
		<div class="chartShow-left">
			<div id="treatmentpath_${treatmentRandom}" style="height: ${empty viewChartHeight?500:viewChartHeight}px;"></div>
		</div>
<%-- 		<div class="chartShow-right">
			<div id="filterDiv_${treatmentRandom}">
				<div class="widget-header widget-header-small header-color-blue">
					<h5>
						<spring:message code="treatment.path.filterList"/><i class="icon-filter"></i>
					</h5>
				</div>
				<div class="widget-body">
					<div class="widget-main">
						<div class="row">
							<div class="col-xs-12">
								<h5 class="header smaller lighter blue no-margin">
									<spring:message code="treatment.path.shape"/>：<select id="shapeSelect_${treatmentRandom}" style="width:150px;">
										<option value="0">--</option>
										<option value="1"><spring:message code="treatment.path.drug"/></option>
										<option value="2"><spring:message code="treatment.path.count"/></option>
										<option value="3"><spring:message code="treatment.path.effect"/></option>
									</select>
								</h5>
								<div class="orderByGroup">
									<div id="shapeList_${treatmentRandom}"></div>
									<a style="cursor: pointer; display: none;" id="shapeListA_${treatmentRandom}"
										onclick="hideMore_${treatmentRandom}(this)">More&gt;&gt;</a>
									<div id="shapeListMore_${treatmentRandom}" style="display: none;"></div>
								</div>
							</div>
						</div>
						<!--/.row-->
						<div class="row">
							<div class="col-xs-12">
								<h5 class="header smaller lighter blue no-margin">
									<spring:message code="treatment.path.color"/>：<select id="colorSelect_${treatmentRandom}" style="width:150px;">
										<option value="0">--</option>
										<option value="1"><spring:message code="treatment.path.drug"/></option>
										<option value="2"><spring:message code="treatment.path.count"/></option>
										<option value="3"><spring:message code="treatment.path.effect"/></option>
									</select>
								</h5>
								<div class="orderByGroup">
									<div id="colorList_${treatmentRandom}"></div>
									<a style="cursor: pointer; display: none;" id="colorListA_${treatmentRandom}"
										onclick="hideMore_${treatmentRandom}(this)">More&gt;&gt;</a>
									<div id="colorListMore_${treatmentRandom}" style="display: none;"></div>
								</div>
							</div>
						</div>
						<!--/.row-->
						<div class="row">
							<div class="col-xs-12">
								<h5 class="header smaller lighter blue no-margin">
									<spring:message code="treatment.path.size"/>：<select id="sizeSelect_${treatmentRandom}" style="width:150px;">
										<option value="0">--</option>
										<option value="1"><spring:message code="treatment.path.drug"/></option>
										<option value="2"><spring:message code="treatment.path.count"/></option>
										<option value="3"><spring:message code="treatment.path.effect"/></option>
									</select>
								</h5>
								<div class="orderByGroup">
									<div id="sizeList_${treatmentRandom}"></div>
									<a style="cursor: pointer; display: none;" id="sizeListA_${treatmentRandom}"
										onclick="hideMore_${treatmentRandom}(this)">More&gt;&gt;</a>
									<div id="sizeListMore_${treatmentRandom}" style="display: none;"></div>
								</div>
							</div>
						</div>
						<!--/.row-->
					</div>
				</div>
			</div>
		</div>--%>
	</div>
</body>
<script>
 var filterWidgetMainStr_${treatmentRandom} = 
	 '<div class="row">'
	+'	<div class="col-xs-12">'
	+'		<h5 class="header smaller lighter blue no-margin">'
	+'			<spring:message code="treatment.path.shape"/>：'
	+'			<select id="shapeSelect_${treatmentRandom}" style="width:150px;">'
	+'				<option value="0">--</option>'
	+'				<option value="1"><spring:message code="treatment.path.drug"/></option>'
	+'				<option value="2"><spring:message code="treatment.path.count"/></option>'
	+'				<option value="3" selected="selected"><spring:message code="treatment.path.effect"/></option>'
	+'			</select>'
	+'		</h5>'
	+'		<div class="orderByGroup" style="font-size:14px;">'
	+'			<div id="shapeList_${treatmentRandom}"></div>'
	+'			<a style="cursor: pointer; display: none;" id="shapeListA_${treatmentRandom}"'
	+'				onclick="hideMore_${treatmentRandom}(this)">More&gt;&gt;</a>'
	+'			<div id="shapeListMore_${treatmentRandom}" style="display: none;"></div>'
	+'		</div>'
	+'	</div>'
	+'</div>'
	+'<div class="row">'
	+'	<div class="col-xs-12">'
	+'		<h5 class="header smaller lighter blue no-margin">'
	+'			<spring:message code="treatment.path.color"/>：'
	+'			<select id="colorSelect_${treatmentRandom}" style="width:150px;">'
	+'				<option value="0">--</option>'
	+'				<option value="1" selected="selected"><spring:message code="treatment.path.drug"/></option>'
	+'				<option value="2"><spring:message code="treatment.path.count"/></option>'
	+'				<option value="3"><spring:message code="treatment.path.effect"/></option>'
	+'			</select>'
	+'		</h5>'
	+'		<div class="orderByGroup" style="font-size:14px;">'
	+'			<div id="colorList_${treatmentRandom}"></div>'
	+'			<a style="cursor: pointer; display: none;" id="colorListA_${treatmentRandom}"'
	+'				onclick="hideMore_${treatmentRandom}(this)">More&gt;&gt;</a>'
	+'			<div id="colorListMore_${treatmentRandom}" style="display: none;"></div>'
	+'		</div>'
	+'	</div>'
	+'</div>'
	+'<div class="row">'
	+'	<div class="col-xs-12">'
	+'		<h5 class="header smaller lighter blue no-margin">'
	+'			<spring:message code="treatment.path.size"/>：'
	+'			<select id="sizeSelect_${treatmentRandom}" style="width:150px;">'
	+'				<option value="0">--</option>'
	+'				<option value="1"><spring:message code="treatment.path.drug"/></option>'
	+'				<option value="2"><spring:message code="treatment.path.count"/></option>'
	+'				<option value="3"><spring:message code="treatment.path.effect"/></option>'
	+'			</select>'
	+'		</h5>'
	+'		<div class="orderByGroup" style="font-size:14px;">'
	+'			<div id="sizeList_${treatmentRandom}"></div>'
	+'			<a style="cursor: pointer; display: none;" id="sizeListA_${treatmentRandom}"'
	+'				onclick="hideMore_${treatmentRandom}(this)">More&gt;&gt;</a>'
	+'			<div id="sizeListMore_${treatmentRandom}" style="display: none;"></div>'
	+'		</div>'
	+'	</div>'
	+'</div>' ;


	var symbolArr = [
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

	var colorArr = [ '#FD026C', '#6B9494', '#A5D22D', '#4682B8', '#F5CC0A', '#B97C46', '#61a0a8', '#bda29a', '#44525d', '#c4ccd3', '#CD69C9', '#CAE1FF', '#8968CD' ];

	var iconClassArr = [ 'iconBy_circle', 'iconBy_rect', 'iconBy_tri', 'iconBy_dimond', 'iconBy_arrow', 'iconBy_cross', 'iconBy_triRight', 'iconBy_star', 'iconBy_crosssArw', 'iconBy_arwDown',
			'iconBy_arwLeft', 'iconBy_arwRight', 'iconBy_arwTop', 'iconBy_box', 'iconBy_pill', 'iconBy_hexagon', 'iconBy_triDown' ]

	//1:药物形状分类 2:剂量形状分类
	var shapeIndex_${treatmentRandom} = 0;

	//1:药物颜色分类 2:剂量颜色分类
	var colorIndex_${treatmentRandom} = 0;

	//1:药物大小分类 2:剂量大小分类
	var sizeIndex_${treatmentRandom} = 0;

	//后台传的用药数组值
	var voList_${treatmentRandom} = null;

	//治疗集合
	var treatmentGuidSet_${treatmentRandom} = null;

	//治疗的最后一次用药集合 用于显示label标签
	var treatmentLastMedDosingScheduleDateSet_${treatmentRandom} = null;

	//药名数组
	var drugSet_${treatmentRandom} = null;

	//计量数组
	var dosingSet_${treatmentRandom} = null;
	
	//疗效
	var curativeEffectSet_${treatmentRandom}=null;

	//x轴数据
	var xAxisValueArr_${treatmentRandom} = new Array("");

	//y轴数据
	var yAxisValueArr_${treatmentRandom} = null;

	//后台数据
	var ajaxData_${treatmentRandom} = null;
	
	var xScale_${treatmentRandom}=0;
	
	var datazoomStart_${treatmentRandom};
	
	var datazoomEnd_${treatmentRandom};
	
	var resizeFlag_${treatmentRandom}=false;
	var $filterWidgetMain_${treatmentRandom};
	var style_${treatmentRandom} = '${style}';
	$(function() {
 		//初始化toolbar中的下拉数据
		$filterWidgetMain_${treatmentRandom} = $("#chartShowDiv_${treatmentRandom}")
			.closest(".widget-box").find(".widget-main:first");
		$filterWidgetMain_${treatmentRandom}.html(filterWidgetMainStr_${treatmentRandom});
		//取消下拉filter点击自动隐藏的功能
		$filterWidgetMain_${treatmentRandom}.closest('ul.dropdown-menu')
			.on('click', '[data-stopPropagation]', function (e) {
				e.stopPropagation();
			});
		
		//初始化主要参数，患者ID、studyId;
		
		var tp_local_studyId = '';
		var tp_local_patientId = '';
		var tp_local_patientCode = '';
		
		try{
			tp_local_studyId = studyId;
		}catch(ex){
			tp_local_studyId = '${studyId}';
		}
		
		try{
			tp_local_patientId = patientId;
		}catch(ex){
			tp_local_patientId = '${patientId}';
		}
		

		//初始化数据和视图
		function init_${treatmentRandom}(flag) {
			//判断后台数据是否已读取到内存中，没有则去后台读取
			if (ajaxData_${treatmentRandom} == null||flag) {
				resizeFlag_${treatmentRandom}=false;
				getAjaxData_${treatmentRandom}();
				datazoomStart_${treatmentRandom}=undefined;
				datazoomEnd_${treatmentRandom}=undefined;
			} else {
				resizeFlag_${treatmentRandom}=true;
				initChart_${treatmentRandom}();
			}
		}
		
		$("#tp_local_search_button_${treatmentRandom}").click(function(){
			var inpatient_num = $("#tp_local_search_input_${treatmentRandom}").val();
			if($.trim(inpatient_num)!=''){
				tp_local_patientCode = inpatient_num;
				init_${treatmentRandom}(true);
			}
		});
		
		$("#tp_local_search_input_${treatmentRandom}").typeahead({
			source:function(query,process){
				 $.post('${pageContext.request.contextPath}/treatment/fuzzyQueryPatientCode',
						{'code':query,'studyId':tp_local_studyId},
						function (data) {
			            	process(data.patientCodeList);
						}
			     );
			}
		});
		
		if($.trim(tp_local_patientCode)==''&&${needChoosePatient}){
			$("#tp_local_search_div_${treatmentRandom}").show();
			$(".chartShow-left").addClass("col-xs-12");
			$(".chartShow-right").addClass("col-xs-2");
		}else{
			$(".chartShow-left").removeClass("col-xs-12");
			$(".chartShow-right").removeClass("col-xs-2");
			init_${treatmentRandom}(true);
		}
		

		$("#shapeSelect_${treatmentRandom}").change(function() {
			shapeIndex_${treatmentRandom} = $(this).val();
			init_${treatmentRandom}();
		})

		$("#colorSelect_${treatmentRandom}").change(function() {
			colorIndex_${treatmentRandom} = $(this).val();
			init_${treatmentRandom}();
		})

		$("#sizeSelect_${treatmentRandom}").change(function() {
			sizeIndex_${treatmentRandom} = $(this).val();
			init_${treatmentRandom}();
		})

		function initChart_${treatmentRandom}() {
			// 基于准备好的dom，初始化echarts实例
			treatmentPath = echarts.init(document.getElementById('treatmentpath_${treatmentRandom}'),'myStyle');
			
			treatmentPath.on("dataZoom",function(params){
				var start=params.start;
				var end=params.end;
				xScale_${treatmentRandom}=(end-start)/100;
				
				datazoomStart_${treatmentRandom}= start;
				datazoomEnd_${treatmentRandom}= end;
			})
			
			//后台传的用药数组值
			voList_${treatmentRandom} = ajaxData_${treatmentRandom}.voList;

			//治疗集合
			treatmentGuidSet_${treatmentRandom} = ajaxData_${treatmentRandom}.treatmentGuidSet;

			//治疗的最后一次用药集合 用于显示label标签
			treatmentLastMedDosingScheduleDateSet_${treatmentRandom} = ajaxData_${treatmentRandom}.treatmentLastMedDosingScheduleDateSet;

			//药名数组
			drugSet_${treatmentRandom} = ajaxData_${treatmentRandom}.drugSet;

			//计量数组
			dosingSet_${treatmentRandom} = ajaxData_${treatmentRandom}.dosingSet;
			
			//疗效
			curativeEffectSet_${treatmentRandom}=ajaxData_${treatmentRandom}.curativeEffectSet;

			initNodeShapeAndColorDiv_${treatmentRandom}();
	
			xAxisValueArr_${treatmentRandom}=new Array("");
			//循环用药数组
			for ( var i in voList_${treatmentRandom}) {
				var medDosingSechdule = voList_${treatmentRandom}[i];
				var xAxisValue = getXAxisValue_${treatmentRandom}(medDosingSechdule);
				if (xAxisValueArr_${treatmentRandom}.indexOf(xAxisValue) == -1)
					xAxisValueArr_${treatmentRandom}.push(xAxisValue);

			}

			//y轴去重 去除重复的用药
			yAxisValueArr_${treatmentRandom} = drugSet_${treatmentRandom};
			
			innerDatazoomStart=xAxisValueArr_${treatmentRandom}.length>11?(xAxisValueArr_${treatmentRandom}.length-11)/xAxisValueArr_${treatmentRandom}.length*100:0;

			// 指定treatmentpath图表的配置项和数据
			var schema = [ {
				name : 'date',
				index : 0,
				text : '<spring:message code="treatment.path.date"/>'
			}, {
				name : 'name',
				index : 1,
				text : '<spring:message code="treatment.path.name"/>'
			}, {
				name : 'drug',
				index : 2,
				text : '<spring:message code="treatment.path.drug"/>'
			}, {
				name : 'count',
				index : 3,
				text : '<spring:message code="treatment.path.count"/>'
			}, {
				name : 'effect',
				index : 4,
				text : '<spring:message code="treatment.path.effect"/>'
			}, {
				name : 'frequency',
				index : 5,
				text : '<spring:message code="treatment.path.frequency"/>'
			} , {
				name : 'cycle',
				index : 6,
				text : '<spring:message code="treatment.path.cycle"/>'
			} , {
				name : 'endDate',
				index : 7,
				text : '<spring:message code="treatment.path.endDate"/>'
			} , {
				name : 'radiotherapyTarget',
				index : 8,
				text : '<spring:message code="treatment.path.radiotherapyTarget"/>'
			} , {
				name : 'radiotherapyFrequency',
				index : 9,
				text : '<spring:message code="treatment.path.radiotherapyFrequency"/>'
			} ];

			if(style_${treatmentRandom}=='black'){
				path_option = {
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
							data : yAxisValueArr_${treatmentRandom},
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
								//连线不显示详细信息
								if (params.value == undefined)
									return 0;
								
										
								if(params.value[2]=='Radiotherapy'){
									return schema[0].text + '：' + xAxisValueArr_${treatmentRandom}[params.value[0]] + '<br>' + schema[1].text + '：' + params.value[3] + '<br>' + schema[2].text + '：' + params.value[2] + '<br>'
									+ schema[3].text + '：' + params.value[4] + '<br>' + schema[4].text + '：' + (params.value[5] == undefined ? "" : params.value[5]) + '<br>' + schema[9].text + '：'
									+ ((params.value[7] == undefined ? "" : params.value[7]) + (params.value[8] == undefined ? "" : params.value[8]))+'<br>'
									+ schema[6].text + '：'+(params.value[9]== undefined ? "" :+params.value[9])+'<br>'
									+ schema[7].text + '：'+ params.value[10]+'<br>'
									+ schema[8].text + '：'+ params.value[11];
								}else{
									return schema[0].text + '：' + xAxisValueArr_${treatmentRandom}[params.value[0]] + '<br>' + schema[1].text + '：' + params.value[3] + '<br>' + schema[2].text + '：' + params.value[2] + '<br>'
									+ schema[3].text + '：' + params.value[4] + '<br>' + schema[4].text + '：' + (params.value[5] == undefined ? "" : params.value[5]) + '<br>' + schema[5].text + '：'
									+ ((params.value[7] == undefined ? "" : params.value[7]) + (params.value[8] == undefined ? "" : params.value[8]))+'<br>'
									+ schema[6].text + '：'+(params.value[9]== undefined ? "" :+params.value[9])+'<br>'
									+ schema[7].text + '：'+ params.value[10]+'<br>';
								}
							}
						},
						xAxis : {
							type : 'category',
							data : xAxisValueArr_${treatmentRandom},
							name:'<spring:message code="treatment.path.dosingDate"/>',
							nameGap: 4,
							boundaryGap : false,
							splitLine : {
								show : true,
								lineStyle : {
									//color : '#ddd',
									type : 'dashed'
								}
							},
							/* axisTick : {
								interval : 0
							}, */
							axisLabel : {
								textStyle: {
				                    color: '#eee'
				                },
								interval: function(index,value){
									var length=parseInt(xAxisValueArr_${treatmentRandom}.length*xScale_${treatmentRandom});
									if(length>8){
										var showIndex=parseInt(length/8);
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
							data : yAxisValueArr_${treatmentRandom},
							boundaryGap : false,
							name:'<spring:message code="treatment.path.drug"/>',
							splitLine : {
								show : false,
								lineStyle : {
									//color : '#ddd',
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
						series : initSeries_${treatmentRandom}(yAxisValueArr_${treatmentRandom}),
						dataZoom : {
							type : 'slider',
							show : true,
							textStyle: {
				                color: '#eee'
				            },
				            dataBackgroundColor: '#6B668F',
				            fillerColor: 'rgba(182,162,222,0.2)',
				            handleColor: '#A78ADE',
							//startValue : xAxisValueArr_${treatmentRandom}.length - 11,
							start:(resizeFlag_${treatmentRandom}==true&&datazoomStart_${treatmentRandom}!=undefined)?datazoomStart_${treatmentRandom}:innerDatazoomStart,
							end:(resizeFlag_${treatmentRandom}==true&&datazoomEnd_${treatmentRandom}!=undefined)?datazoomEnd_${treatmentRandom}:100
						}
					};
			}else{
				path_option = {
						title : {
							text : '<spring:message code="treatment.path"/>',
							left : 'center'
						},
						legend : {
							show : false,
							y : 'top',
							data : yAxisValueArr_${treatmentRandom},
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
								//连线不显示详细信息
								if (params.value == undefined)
									return 0;
								
										
								if(params.value[2]=='Radiotherapy'){
									return schema[0].text + '：' + xAxisValueArr_${treatmentRandom}[params.value[0]] + '<br>' + schema[1].text + '：' + params.value[3] + '<br>' + schema[2].text + '：' + params.value[2] + '<br>'
									+ schema[3].text + '：' + params.value[4] + '<br>' + schema[4].text + '：' + (params.value[5] == undefined ? "" : params.value[5]) + '<br>' + schema[9].text + '：'
									+ ((params.value[7] == undefined ? "" : params.value[7]) + (params.value[8] == undefined ? "" : params.value[8]))+'<br>'
									+ schema[6].text + '：'+(params.value[9]== undefined ? "" :+params.value[9])+'<br>'
									+ schema[7].text + '：'+ params.value[10]+'<br>'
									+ schema[8].text + '：'+ params.value[11];
								}else{
									return schema[0].text + '：' + xAxisValueArr_${treatmentRandom}[params.value[0]] + '<br>' + schema[1].text + '：' + params.value[3] + '<br>' + schema[2].text + '：' + params.value[2] + '<br>'
									+ schema[3].text + '：' + params.value[4] + '<br>' + schema[4].text + '：' + (params.value[5] == undefined ? "" : params.value[5]) + '<br>' + schema[5].text + '：'
									+ ((params.value[7] == undefined ? "" : params.value[7]) + (params.value[8] == undefined ? "" : params.value[8]))+'<br>'
									+ schema[6].text + '：'+(params.value[9]== undefined ? "" :+params.value[9])+'<br>'
									+ schema[7].text + '：'+ params.value[10]+'<br>';
								}
							}
						},
						xAxis : {
							type : 'category',
							data : xAxisValueArr_${treatmentRandom},
							name:'<spring:message code="treatment.path.dosingDate"/>',
							nameGap: 4,
							boundaryGap : false,
							splitLine : {
								show : true,
								lineStyle : {
									//color : '#ddd',
									type : 'dashed'
								}
							},
							/* axisTick : {
								interval : 0
							}, */
							axisLabel : {
								interval: function(index,value){
									var length=parseInt(xAxisValueArr_${treatmentRandom}.length*xScale_${treatmentRandom});
									if(length>8){
										var showIndex=parseInt(length/8);
										if(index%showIndex==0){
											return value;
										}
									}else{
										return value;
									}
								}, 
								margin: 8,
								rotate : 45
							}
						},
						yAxis : {
							type : 'category',
							data : yAxisValueArr_${treatmentRandom},
							boundaryGap : false,
							name:'<spring:message code="treatment.path.drug"/>',
							splitLine : {
								show : false,
								lineStyle : {
									//color : '#ddd',
									type : 'dashed'
								}
							}
						},
						series : initSeries_${treatmentRandom}(yAxisValueArr_${treatmentRandom}),
						dataZoom : {
							type : 'slider',
							show : true,
							//startValue : xAxisValueArr_${treatmentRandom}.length - 11,
							start:(resizeFlag_${treatmentRandom}==true&&datazoomStart_${treatmentRandom}!=undefined)?datazoomStart_${treatmentRandom}:innerDatazoomStart,
							end:(resizeFlag_${treatmentRandom}==true&&datazoomEnd_${treatmentRandom}!=undefined)?datazoomEnd_${treatmentRandom}:100
						}
					};
			}
			
			treatmentPath.setOption(path_option);
		}

		function getAjaxData_${treatmentRandom}() {
			$.post("${pageContext.request.contextPath}/treatment/getTreatmentPath", {
				"patientCode" : tp_local_patientCode,
				"patientId" : tp_local_patientId,
				"studyId" : tp_local_studyId
			}, function(data) {
				ajaxData_${treatmentRandom} = data;
				if (data.success) {

					$("#chartShowDiv_${treatmentRandom}").show();
					$("#noChartDiv_${treatmentRandom}").hide();
					$("#chartShowErrorDiv_${treatmentRandom}").hide();
					
					initChart_${treatmentRandom}();
				} else {
					$("#chartShowDiv_${treatmentRandom}").hide();
					if(${needChoosePatient}){
						$("#chartShowErrorDiv_${treatmentRandom}").show();
						$("#chartShowErrorInfo_${treatmentRandom}").html("<spring:message code='da.dataframe.noresults'/>");
						$("#noChartDiv_${treatmentRandom}").hide();
					}else{
						$("#chartShowErrorDiv_${treatmentRandom}").hide();
						$("#noChartDiv_${treatmentRandom}").show();
					}
					
				}
			});
		}

	})

	//根据药物获取x轴值
	function getXAxisValue_${treatmentRandom}(medDosingSechdule) {
		var dosingDate = new Date(medDosingSechdule.dosingDate);
		var formatDate = dosingDate.getFullYear() + "." + (dosingDate.getMonth() + 1) + "." + dosingDate.getDate();
		var treatmentSolution = medDosingSechdule.treatmentSolution;
		if(treatmentSolution!='')
			return formatDate + "(" + treatmentSolution + ")";
		else
			return formatDate;
	}

	function initSeries_${treatmentRandom}() {
		var serie = [];

		//循环创建每个节点
		for ( var yIndex in yAxisValueArr_${treatmentRandom}) {
			if (yAxisValueArr_${treatmentRandom}[yIndex] == '') {
				continue;
			}

			var drug = yAxisValueArr_${treatmentRandom}[yIndex];
			var item = {
				name : drug,
				type : 'scatter',
				data : initNode_${treatmentRandom}(yIndex, drug), //创建节点
				//symbol:symbolArr[yIndex],
				label : {
					normal : {
						show : true,
						position : 'top',
						formatter : function(obj) {
							if (obj.value[6])
								return obj.value[5] == undefined ? " " : obj.value[5];

							return " ";
						},
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
					data : initMarkLineData_${treatmentRandom}(yIndex, drug)
				//创建连线
				}
			}
			serie.push(item);
		}

		return serie;
	}

	//初始化节点  yIndex:y轴坐标 drug:药物 
	function initNode_${treatmentRandom}(yIndex, drug) {
		var itemData = new Array();
		//循环药物数组 找出包含该药物的对象并放入数组
		for ( var i in voList_${treatmentRandom}) {
			var medDosingSchedule = voList_${treatmentRandom}[i];
			if (medDosingSchedule.drug == drug) {
				var node = new Object();

				var nodeValue = new Array();
				//x轴
				nodeValue.push(xAxisValueArr_${treatmentRandom}.indexOf(getXAxisValue_${treatmentRandom}(medDosingSchedule)));
				//y轴
				nodeValue.push(parseInt(yIndex));
				//药物
				nodeValue.push(drug);
				//方案名称
				nodeValue.push(medDosingSchedule.treatmentSolution);
				//计量
				nodeValue.push(medDosingSchedule.dosing + medDosingSchedule.dosingUnit);
				//疗效
				nodeValue.push(medDosingSchedule.treatmentCurativeEffect==""?" ":medDosingSchedule.treatmentCurativeEffect);
				//是否要显示label标签 判断该药物是否为治疗的最后一次用药
				if (treatmentLastMedDosingScheduleDateSet_${treatmentRandom}.indexOf(medDosingSchedule.dosingDate) != -1) {
					nodeValue.push(true);
				} else {
					nodeValue.push(false);
				}
				//用药频率
				nodeValue.push(medDosingSchedule.frequency);
				//频率单位 
				nodeValue.push(medDosingSchedule.frequencyUnit);
				//用药周期
				nodeValue.push(medDosingSchedule.treatmentCycle);
				
				//结束时间
				if(medDosingSchedule.treatmentEndDate!=undefined){
					var treatmentEndDate = new Date(medDosingSchedule.treatmentEndDate);
					var formatTreatmentEndDate = treatmentEndDate.getFullYear() + "." + (treatmentEndDate.getMonth() + 1) + "." + treatmentEndDate.getDate();
					nodeValue.push(formatTreatmentEndDate);
				}else{
					nodeValue.push("");
				}
				
				//部位
				nodeValue.push(medDosingSchedule.target);
				
				node.value = nodeValue;
				initNodeShapeAndColor_${treatmentRandom}(node, drug, medDosingSchedule.dosing,medDosingSchedule.treatmentCurativeEffect);

				itemData.push(node);
			}
		}
		return itemData;
	}

	//初始化连线  yIndex:y轴坐标 drug:药物
	function initMarkLineData_${treatmentRandom}(yIndex, drug) {
		var markLineData = new Array();

		var treatmentLineArr = getTreatmentLineArr_${treatmentRandom}();

		for ( var i in voList_${treatmentRandom}) {

			var medDosingSchedule = voList_${treatmentRandom}[i];
			if (medDosingSchedule.drug == drug) {
				for ( var index in treatmentLineArr) {

					var treatmentLine = treatmentLineArr[index];

					if (treatmentLine.guid == medDosingSchedule.treatmentId) {
						var node = new Object();
						node.coord = [ xAxisValueArr_${treatmentRandom}.indexOf(getXAxisValue_${treatmentRandom}(medDosingSchedule)), parseInt(yIndex) ];
						node.symbol = 'none';
						if (treatmentLine.arr.length > 1) {
							treatmentLine.arr.pop();
						}
						treatmentLine.arr.push(node);
					}
				}

			}
		}

		for ( var i in treatmentLineArr) {
			var dataArr = treatmentLineArr[i].arr;

			if (dataArr.length > 1) {
				markLineData.push(dataArr);
			}
		}

		return markLineData;
	}

	//获取治疗连线对象数组 treatmentGuidSet_${treatmentRandom}:治疗主键集合
	function getTreatmentLineArr_${treatmentRandom}() {

		var treatmentLineArr = new Array();

		for ( var i in treatmentGuidSet_${treatmentRandom}) {
			//构建治疗连线对象
			var treatmentLine = new Object();
			treatmentLine.guid = treatmentGuidSet_${treatmentRandom}[i];
			treatmentLine.arr = new Array();
			treatmentLineArr.push(treatmentLine);
		}

		return treatmentLineArr;
	}

	//初始化节点颜色和形状
	function initNodeShapeAndColor_${treatmentRandom}(node, drug, dosing,treatmentCurativeEffect) {
		//判断形状是根据药物还是计量
		if (shapeIndex_${treatmentRandom} == 1) {
			var index=loopMinData_${treatmentRandom}(drugSet_${treatmentRandom}.indexOf(drug),symbolArr.length-1);
			node.symbol = symbolArr[index];
		} else if (shapeIndex_${treatmentRandom} == 2){
			var index=loopMinData_${treatmentRandom}(dosingSet_${treatmentRandom}.indexOf(dosing),symbolArr.length-1);
			node.symbol = symbolArr[index];
		}else if(shapeIndex_${treatmentRandom}==3){
			var index=loopMinData_${treatmentRandom}(curativeEffectSet_${treatmentRandom}.indexOf(treatmentCurativeEffect),symbolArr.length-1);
			node.symbol = symbolArr[index];
		}
		else{
			node.symbol = symbolArr[0];
		}

		if (colorIndex_${treatmentRandom} == 1) {
			var index=loopMinData_${treatmentRandom}(drugSet_${treatmentRandom}.indexOf(drug) - 1,colorArr.length-1);
			node.itemStyle = {
				normal : {
					color : colorArr[index]
				}
			};
		} else if(colorIndex_${treatmentRandom} == 2){
			var index=loopMinData_${treatmentRandom}(dosingSet_${treatmentRandom}.indexOf(dosing),colorArr.length-1);
			node.itemStyle = {
				normal : {
					color : colorArr[index]
				}
			};
		}else if(colorIndex_${treatmentRandom} == 3){
			var index=loopMinData_${treatmentRandom}(curativeEffectSet_${treatmentRandom}.indexOf(treatmentCurativeEffect),colorArr.length-1);
			node.itemStyle = {
				normal : {
					color : colorArr[index]
				}
			};
		}else{
			node.itemStyle = {
					normal : {
						color : colorArr[0]
					}
				};
		}

		//判断大小是根据药物还是计量
		if (sizeIndex_${treatmentRandom} == 1) {
				node.symbolSize=drugSet_${treatmentRandom}.indexOf(drug)%6*8+1;
		} else if(sizeIndex_${treatmentRandom} == 2){
			if(dosingSet_${treatmentRandom}.length<=1){
				node.symbolSize = 1;
			}else{
				var aveDosing=(dosingSet_${treatmentRandom}[0]+dosingSet_${treatmentRandom}[dosingSet_${treatmentRandom}.length-1])/4;
				node.symbolSize=Math.ceil(dosing/aveDosing)*8;
			}
		}else if(sizeIndex_${treatmentRandom} == 3){
			node.symbolSize=Math.ceil((curativeEffectSet_${treatmentRandom}.indexOf(treatmentCurativeEffect)+1)/2)*8;
		}else{
			node.symbolSize = 8;
		}
	}

	//初始化节点颜色和形状的div
	function initNodeShapeAndColorDiv_${treatmentRandom}() {
		shapeIndex_${treatmentRandom} = $("#shapeSelect_${treatmentRandom}  option:selected").val();
		colorIndex_${treatmentRandom} = $("#colorSelect_${treatmentRandom}  option:selected").val();
		sizeIndex_${treatmentRandom} = $("#sizeSelect_${treatmentRandom}  option:selected").val();
		//默认形状选择3和颜色选择1
		//这里由于要求rwe-web调用此页面时给定一个默认值
		if(!shapeIndex_${treatmentRandom}){
			shapeIndex_${treatmentRandom}=3;
		}
		if(!colorIndex_${treatmentRandom}){
			colorIndex_${treatmentRandom}=1;
		}
		//判断形状是根据药物还是计量
		if (shapeIndex_${treatmentRandom} == 1) {
			$("#shapeList_${treatmentRandom}").html("");
			$("#shapeListMore_${treatmentRandom}").html("");
			$("#shapeListA_${treatmentRandom}").hide();
			$("#shapeListMore_${treatmentRandom}").hide();
			for ( var i in drugSet_${treatmentRandom}) {
				if (drugSet_${treatmentRandom}[i] == "") {
					continue;
				}
				var index=loopMinData_${treatmentRandom}(i,symbolArr.length-1);
				if(i<6){
					$("#shapeList_${treatmentRandom}").append('<i class="icon '+iconClassArr[index]+'"></i>' + drugSet_${treatmentRandom}[i] + '<br>');
				}else{
					$("#shapeListA_${treatmentRandom}").show();
					$("#shapeListMore_${treatmentRandom}").append('<i class="icon '+iconClassArr[index]+'"></i>' + drugSet_${treatmentRandom}[i] + '<br>');
				}
			}

		} else if(shapeIndex_${treatmentRandom} == 2){
			$("#shapeList_${treatmentRandom}").html("");
			$("#shapeListMore_${treatmentRandom}").html("");
			$("#shapeListA_${treatmentRandom}").hide();
			$("#shapeListMore_${treatmentRandom}").hide();
			for ( var i in dosingSet_${treatmentRandom}) {
				var index=loopMinData_${treatmentRandom}(i,symbolArr.length-1);
				if(i<5){
					$("#shapeList_${treatmentRandom}").append('<i class="icon '+iconClassArr[index]+'"></i>' + dosingSet_${treatmentRandom}[i] + '<br>');
				}else{
					$("#shapeListA_${treatmentRandom}").show();
					$("#shapeListMore_${treatmentRandom}").append('<i class="icon '+iconClassArr[index]+'"></i>' + dosingSet_${treatmentRandom}[i] + '<br>');
				}
			}
		} else if(shapeIndex_${treatmentRandom} == 3){
			$("#shapeList_${treatmentRandom}").html("");
			$("#shapeListMore_${treatmentRandom}").html("");
			$("#shapeListA_${treatmentRandom}").hide();
			$("#shapeListMore_${treatmentRandom}").hide();
			for ( var i in curativeEffectSet_${treatmentRandom}) {
				var index=loopMinData_${treatmentRandom}(i,symbolArr.length-1);
				if(i<5){
					$("#shapeList_${treatmentRandom}").append('<i class="icon '+iconClassArr[index]+'"></i>' + curativeEffectSet_${treatmentRandom}[i] + '<br>');
				}else{
					$("#shapeListA_${treatmentRandom}").show();
					$("#shapeListMore_${treatmentRandom}").append('<i class="icon '+iconClassArr[index]+'"></i>' + curativeEffectSet_${treatmentRandom}[i] + '<br>');
				}
			}
		}else{
			$("#shapeList_${treatmentRandom}").html("");
			$("#shapeListMore_${treatmentRandom}").html("");
			$("#shapeListA_${treatmentRandom}").hide();
			$("#shapeListMore_${treatmentRandom}").hide();
			for ( var i in drugSet_${treatmentRandom}) {
				if (drugSet_${treatmentRandom}[i] == "") {
					continue;
				}
				var index=loopMinData_${treatmentRandom}(i,symbolArr.length-1);
				if(i<6){
					$("#shapeList_${treatmentRandom}").append('<i class="icon '+iconClassArr[0]+'"></i>' + drugSet_${treatmentRandom}[i] + '<br>');
				}else{
					$("#shapeListA_${treatmentRandom}").show();
					$("#shapeListMore_${treatmentRandom}").append('<i class="icon '+iconClassArr[0]+'"></i>' + drugSet_${treatmentRandom}[i] + '<br>');
				}
			}
		}
		

		if (colorIndex_${treatmentRandom} == 1) {
			$("#colorList_${treatmentRandom}").html("");
			$("#colorListMore_${treatmentRandom}").html("");
			$("#colorListA_${treatmentRandom}").hide();
			$("#colorListMore_${treatmentRandom}").hide();
			for ( var i in drugSet_${treatmentRandom}) {
				if (drugSet_${treatmentRandom}[i] == "") {
					continue;
				}
				var index=loopMinData_${treatmentRandom}(i,colorArr.length-1);
				if(i<6){
					$("#colorList_${treatmentRandom}").append('<i class="icon iconBg_'+i+'" ></i>' + drugSet_${treatmentRandom}[i] + '<br>');
				}else{
					$("#colorListA_${treatmentRandom}").show();
					$("#colorListMore_${treatmentRandom}").append('<i class="icon iconBg_'+i+'" ></i>' + drugSet_${treatmentRandom}[i] + '<br>');
				}
			}

		} else if(colorIndex_${treatmentRandom} == 2){
			$("#colorList_${treatmentRandom}").html("");
			$("#colorListMore_${treatmentRandom}").html("");
			$("#colorListA_${treatmentRandom}").hide();
			$("#colorListMore_${treatmentRandom}").hide();
			for ( var i in dosingSet_${treatmentRandom}) {
				var index=loopMinData_${treatmentRandom}(i,colorArr.length-1);
				if(i<5){
					$("#colorList_${treatmentRandom}").append('<i class="icon iconBg_' + (index+1) + '" ></i>' + dosingSet_${treatmentRandom}[i] + '<br>');
				}else{
					$("#colorListA_${treatmentRandom}").show();
					$("#colorListMore_${treatmentRandom}").append('<i class="icon iconBg_' + (index+1) + '" ></i>' + dosingSet_${treatmentRandom}[i] + '<br>');
				}
			}
		}else if(colorIndex_${treatmentRandom} == 3){
			$("#colorList_${treatmentRandom}").html("");
			$("#colorListMore_${treatmentRandom}").html("");
			$("#colorListA_${treatmentRandom}").hide();
			$("#colorListMore_${treatmentRandom}").hide();
			for ( var i in curativeEffectSet_${treatmentRandom}) {
				var index=loopMinData_${treatmentRandom}(i,colorArr.length-1);
				if(i<5){
					$("#colorList_${treatmentRandom}").append('<i class="icon iconBg_' + (index+1) + '" ></i>' + curativeEffectSet_${treatmentRandom}[i] + '<br>');
				}else{
					$("#colorListA_${treatmentRandom}").show();
					$("#colorListMore_${treatmentRandom}").append('<i class="icon iconBg_' + (index+1) + '" ></i>' + curativeEffectSet_${treatmentRandom}[i] + '<br>');
				}
			}
		}else{
			$("#colorList_${treatmentRandom}").html("");
			$("#colorListMore_${treatmentRandom}").html("");
			$("#colorListA_${treatmentRandom}").hide();
			$("#colorListMore_${treatmentRandom}").hide();
			for ( var i in drugSet_${treatmentRandom}) {
				if (drugSet_${treatmentRandom}[i] == "") {
					continue;
				}
				var index=loopMinData_${treatmentRandom}(i,colorArr.length-1);
				if(i<6){
					$("#colorList_${treatmentRandom}").append('<i class="icon iconBg_'+1+'" ></i>' + drugSet_${treatmentRandom}[i] + '<br>');
				}else{
					$("#colorListA_${treatmentRandom}").show();
					$("#colorListMore_${treatmentRandom}").append('<i class="icon iconBg_'+1+'" ></i>' + drugSet_${treatmentRandom}[i] + '<br>');
				}
			}
		}

		if (sizeIndex_${treatmentRandom} == 1) {
			$("#sizeList_${treatmentRandom}").html("");
			$("#sizeListMore_${treatmentRandom}").html("");
			$("#sizeListA_${treatmentRandom}").hide();
			$("#sizeListMore_${treatmentRandom}").hide();
			for ( var i in drugSet_${treatmentRandom}) {
				if (drugSet_${treatmentRandom}[i] == "") {
					continue;
				}
				if(i<6){
					$("#sizeList_${treatmentRandom}").append('<span>' + i + '</span> - ' + drugSet_${treatmentRandom}[i] + '<br>');
				}else{
					$("#sizeListA_${treatmentRandom}").show();
					$("#sizeListMore_${treatmentRandom}").append('<span>' + i + '</span> - ' + drugSet_${treatmentRandom}[i] + '<br>');
				}
			}

		} else if (sizeIndex_${treatmentRandom} == 3) {
			$("#sizeList_${treatmentRandom}").html("");
			$("#sizeListMore_${treatmentRandom}").html("");
			$("#sizeListA_${treatmentRandom}").hide();
			$("#sizeListMore_${treatmentRandom}").hide();
			for ( var i in curativeEffectSet_${treatmentRandom}) {
				if(i<6){
					$("#sizeList_${treatmentRandom}").append('<span>' + (parseInt(i) + 1) + '</span> - ' + curativeEffectSet_${treatmentRandom}[i] + '<br>');
				}else{
					$("#sizeListA_${treatmentRandom}").show();
					$("#sizeListMore_${treatmentRandom}").append('<span>' + (parseInt(i) + 1) + '</span> - ' + curativeEffectSet_${treatmentRandom}[i] + '<br>');
				}
			}

		}else {
			$("#sizeList_${treatmentRandom}").html("");
			$("#sizeListMore_${treatmentRandom}").html("");
			$("#sizeListA_${treatmentRandom}").hide();
			$("#sizeListMore_${treatmentRandom}").hide();
			for ( var i in dosingSet_${treatmentRandom}) {
				if(i<5){
					$("#sizeList_${treatmentRandom}").append('<span>' + (parseInt(i) + 1) + '</span> - ' + dosingSet_${treatmentRandom}[i] + '<br>');
				}else{
					$("#sizeListA_${treatmentRandom}").show();
					$("#sizeListMore_${treatmentRandom}").append('<span>' + (parseInt(i) + 1) + '</span> - ' + dosingSet_${treatmentRandom}[i] + '<br>');
				}
			}
		}

	}

	function treatmentPathResize() {
		treatmentPath.resize();
	}

	function loopMinData_${treatmentRandom}(data, len) {
		var i=parseInt(data);
		return i - len * (Math.floor(i / len));
	}
	
	function hideMore_${treatmentRandom}(e){
		$(e).next().show();
		e.style.display='none';
	}
</script>
</html>