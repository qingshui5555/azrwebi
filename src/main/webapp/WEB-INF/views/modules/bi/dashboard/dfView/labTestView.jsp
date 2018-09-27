<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" buffer="1024kb"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>
<script src="${ctxStatic }/echarts/${style }.js"></script>
<c:set value="<%=(int) (Math.random() * 1000)%>" var="labTestRandom" />

<center id="labTestCenter_${labTestRandom}" style="">
	<i class='icon-spinner icon-spin blue bigger-250'></i>
</center>
<c:if test="${labTestIndicatorMap!=null}">
	<div class="chartCont" id="chartCont_${labTestRandom}">
		<div class="row">
			<div class="col-xs-12">
				<div id="labTestChart_${labTestRandom}" style="height: ${viewChartHeight}px"></div>
			</div>
		</div>
	</div>

	<br />
	<script>
		var filterWidgetMainStr_${labTestRandom} =
		 '<div class="row">'
		+'	<div class="col-xs-12">'
		+'		<h5 class="header smaller lighter blue no-margin">'
		+'			<spring:message code="labtest.filter.indicator.labtest" />'
		+'			:'
		+'		</h5>'
		+'		<div class="orderByGroup" style="font-size:14px">'
					<c:forEach items="${labTestIndicatorMap}" var="data" varStatus="status" begin="0" end="5" step="1">
						<c:if test="${status.index==0}">
		+'				<div class="radio"><label>'
		+'					<input type="radio" class="ace" data-type="labTest" checked="checked"'
		+'						value="${data.key}" name="labTest_${labTestRandom}"'
		+'						style="margin-top: 1px; margin-right: 5px;">'
		+'					<span title="${data.key}" class="lbl">${data.value}</span>'
		+'				</label></div>'
						</c:if>
						<c:if test="${status.index!=0&&status.index<5}">
		+'				<div class="radio"><label>'
		+'					<input type="radio" class="ace" data-type="labTest"'
		+'						value="${data.key}" name="labTest_${labTestRandom}"'
		+'						>'
		+'					<span title="${data.key}" class="lbl">${data.value}</span>'
		+'				</label></div>'
						</c:if>
						 <c:if test="${status.index==5}">
		+'		        	 <a style="cursor: pointer;" id="sizeListLabTest_${labTestRandom}" onclick="hideMore_${labTestRandom}(this)">More&gt;&gt;</a>'
						 </c:if>
					</c:forEach>
					<c:if test="${fn:length(labTestIndicatorMap)>5}">
		+'			    <div class="orderByGroup" id="sizeListLabTestMore_${labTestRandom}"  style="display:none;margin-top:-5px">'
							<c:forEach var="data" items="${labTestIndicatorMap}" varStatus="status" begin="5"  step="1">
		+'					<div class="radio"><label>'
		+'						<input type="radio" class="ace" data-type="labtest"'
		+'						value="${data.key}" name="labTest_${labTestRandom}"'
		+'						style="margin-top: 1px; margin-right: 5px;">'
		+'					<span title="${data.key}" class="lbl">${data.value}</span>'
		+'					</label></div>'
							</c:forEach>
		+'			    </div>'
					</c:if>
		+'		</div>'
		+'	</div>'
		+'</div>';
	</script>
</c:if>

<script type="text/javascript">
	function hideMore_${labTestRandom}(e){
		$(e).next().show();
		e.style.display='none';
	}
	var $filterWidgetMain_${labTestRandom};

	$(function() {
		$filterWidgetMain_${labTestRandom} = $("#chartCont_${labTestRandom}").closest(".widget-box").find(".widget-main:first");
		try{
			$filterWidgetMain_${labTestRandom}.html(filterWidgetMainStr_${labTestRandom});
			$filterWidgetMain_${labTestRandom}.closest('ul.dropdown-menu')
			.on('click', '[data-stopPropagation]', function (e) {
				e.stopPropagation();
			});
		} catch(e) {
			$("#labTestCenter_${labTestRandom}").closest(".widget-box").find(".dropdown-toggle").hide();
		}

		 if(${labTestIndicatorMap!=null}) {
			 $('.orderByGroup input[type=radio][name=labTest_${labTestRandom}]').click(function(){
				getLabTestData_${labTestRandom}();
			 })
			 $("#sizeListLabTest_${labTestRandom}").show();
			 $("#sizeListLabTestMore_${labTestRandom}").hide();

			 getLabTestData_${labTestRandom}();
		 }

		 if(${labTestIndicatorMap==null}) {
			 $("#labTestCenter_${labTestRandom}").html("<spring:message code='dashboard.data.empty'/>");
			 $filterWidgetMain_${labTestRandom}.html("");
		 }
	});

	var labtest_${labTestRandom}=0;
	function getLabTestData_${labTestRandom}() {
		$("#labTestCenter_${labTestRandom}").show();
		$("#labTestChart_${labTestRandom}").html("");
        document.getElementById('labTestChart_${labTestRandom}').setAttribute('_echarts_instance_', '');
		var labTest = $('.orderByGroup input[type=radio][name=labTest_${labTestRandom}]:checked').val();
		$.post("${pageContext.request.contextPath}/bi/dfView/labTest/getData",
			{
				studyId : '${studyId}',
                allPatientsFlag : '${allPatientsFlag}',
				cohortIds : $("#cohortIds_${labTestRandom}").val(),
				indicator : labTest,
				configJson : $("#configJson_${labTestRandom}").val(),
			}, function(data) {
				var y = data.y;
				var x = data.x;

				var labTestData = {};
				for(i=0;i<x.length;i++) {
					  labTestData[i]=x[i];
				}

				var tooltipList = data.tooltipList//病历号
				var indicator = data.indicator;//指标名称
				var indicatorDes = data.indicatorDes;
				var upper = data.upperRef;//上限
				var lower = data.lowerRef;//下限
				var maxX = data.maxX;//横坐标的最大值
				var unitX = data.unitX;//横坐标的单位
				var resultUnit = data.resultUnit=='"null"'?' ':data.resultUnit;//纵坐标的单位
				// 基于准备好的dom，初始化echarts实例
				var lab_test_chart = echarts.init(document.getElementById('labTestChart_${labTestRandom}'),'myStyle');

				lab_test_chart.on("dataZoom",function(params){
					var start = params.start;
					var end = params.end;
					labtest_${labTestRandom} = (end-start)/100;
				})

				var max=0;
				for(var i in y){
					if(max < y[i][1]){
						max = y[i][1];
					}
				}
				max = Math.max(max, upper);

				var markLineOptUpper = initLabTestMarkLine(indicator+' upper：'+upper, maxX,upper);
				var markLineOptLower = initLabTestMarkLine(indicator+' lower：'+lower, maxX,lower);
				lab_test_option = {
					title : {
						text : indicatorDes,
						x : 'center',
						y : 0
					},
					tooltip : {
						 hideDelay : 500,
						 showDelay : 0,
						 triggerOn : 'mousemove',
						 formatter: function (obj) {
							 var value = obj.value;
							 if(value != undefined) {
								 var xyValue = obj.data.join();
								 var patientCodeArr = new Array();
								 for(var i in tooltipList){
									 var tooltip = tooltipList[i];
									 if(tooltip.indexOf(xyValue)==0){
										 var patientCode=tooltip.split(",")[2];
										 if($.inArray(patientCode, patientCodeArr)==-1){
											 patientCodeArr.push(patientCode);
										 }
									 }
								 }
								 return '<spring:message code="labtest.scatter.tip.referenceCode"/>  : ' + patientCodeArr.join()+' <br/><spring:message code="labtest.scatter.y"/> : '+ value[1] +' '+resultUnit+ ' <br/> <spring:message code="labtest.scatter.x"/> : ' + labTestData[value[0]]+''
							 }

						 }
					},
					grid : {
						left : '5%',
						right : '12%',
						top : '10%',
						height : '72%',
						containLabel : true
					},
					yAxis : {
						type : 'value',
						name : "<spring:message code='labtest.scatter.y'/> ("+resultUnit+")",
						max:max
					},
					xAxis : {
						type : 'category',
						data : x,
						name : "<spring:message code='labtest.scatter.x'/> ("+unitX+")",
						boundaryGap : false,
						axisLabel : {
							interval: function(index,value){
								var length=parseInt(x.length*labtest_${labTestRandom});
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
					dataZoom : [ {
						type : 'slider',
						show : x.length<11?false:true,
						startValue : x.length - 11
					} ],
					series : [ {
						name : 'data',
						type : 'scatter',
						data : y
					}, {
						name : 'markLine',
						type : 'scatter',
						markLine : markLineOptUpper
					}, {
						name : 'markLine2',
						type : 'scatter',
						markLine : markLineOptLower
					}]
				};
				$("#labTestCenter_${labTestRandom}").hide();
				lab_test_chart.setOption(lab_test_option);
			}, "json")
		}

	function initLabTestMarkLine(name,length,yAxis){
	    var markLine = {
			animation : false,
			tooltip : {
			    formatter : name
			},
			label : {
			    normal : {
					show:false
				}
			},
			label:{
				normal:{
					show:false
				}
			},
			lineStyle : {
				normal : {
					type : 'solid',
					color : '#ff3300'
				}
			},
		};

	    var dataArray = new Array();
		for(var i=0;i<length;i++){
			var objArray = new Array();
			var objStart = new Object();
				objStart.coord = new Array(i, yAxis);
				objStart.symbol = 'none';
				objArray.push(objStart);

			var objEnd = new Object();
				objEnd.coord = new Array(i+1,yAxis);
				objEnd.symbol = 'none';
				objArray.push(objEnd);

			dataArray.push(objArray);
		}

		markLine.data = dataArray;
		return markLine;
	}
</script>
<input type="hidden" id="cohortIds_${labTestRandom}" value="${cohortIds}">
<input type="hidden" id="configJson_${labTestRandom}" value="${configJson}">
