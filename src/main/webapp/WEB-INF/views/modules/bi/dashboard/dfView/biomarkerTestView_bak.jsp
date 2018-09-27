<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" buffer="1024kb"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>
<script src="${ctxStatic }/echarts/${style }.js"></script>
<c:set value="<%=(int)(Math.random()*1000) %>" var="biomarkerTestRandom" />   

<link
	href="${pageContext.request.contextPath}/static/assets/css/base.css"
	rel="stylesheet" type="text/css" />
<div id="tp_local_search_div" class="input-group">
	<input type="text" class="form-control search-query"
		placeholder="<spring:message code='treatment.path.code' />" id="referenceCode_${biomarkerTestRandom }" /> <span
		class="input-group-btn">
		<button type="button" class="btn btn-purple btn-sm"
			id="biomarkerTestButton_${biomarkerTestRandom}">
				<i class="icon-search icon-on-right bigger-110"></i>
				<spring:message code="study.management.search"/>
		</button>
	</span>
</div>
<br>
<div class="alert alert-warning" id="chartShowErrorDiv_${biomarkerTestRandom}" style="display: none;margin-top: 10px;margin-bottom: 10px;">
		<span id="chartShowErrorInfo"></span>
		<br>
</div>
<div id="surgeryBiomarker_${biomarkerTestRandom}" style="height:  ${empty viewChartHeight?700:viewChartHeight}px;display: none;"></div>
<script>

	var symbolArr_${biomarkerTestRandom} = [
     			'circle',
     			'rect',
     			'triangle',
     			'diamond',
     			'path://d="M165.439448 62.597687 943.022199 511.536442 165.439448 960.473151Z"',//右三角
     			'path://d="M76.30692 268.344654l0 545.101909 405.560806 145.390321L481.867726 412.95317l-107.696838-38.609343L76.30692 268.344654zM511.384993 63.786769 91.605348 216.038358l-1.163499 0.420579 222.719524 79.012541 198.22362 71.059406 417.261289-151.161768L511.384993 63.786769zM540.90226 412.95317l0 545.883715 405.561829-145.390321L946.464089 266.569218 540.90226 412.95317z"',//盒子
     			'path://d="M327.23844 128.098656C309.562037 128.104749 288.083906 140.531077 279.269013 155.847863L90.46157 483.920301C81.64519 499.239672 81.619203 524.092213 90.400217 539.424228L278.482013 867.822835C287.264509 883.157437 308.712742 895.60674 326.374199 895.629114L698.643947 896.100716C716.311575 896.123098 737.729869 883.69628 746.484567 868.341974L933.651663 540.081547C942.40568 524.728435 942.411619 499.82228 933.674423 484.468781L746.623984 155.773053C737.882537 140.412082 716.474668 127.964492 698.790236 127.970587L327.23844 128.098656Z"',//六边形
     			'path://d="M511.504208 864.088964 64.091202 224.924749l894.837268 0L511.504208 864.088964zM511.504208 864.088964"',//三角形下
     			'path://d="M512 909.994667l-61.994667-56.021333q-105.984-96-153.984-141.994667t-107.008-114.005333-80.981333-123.008-22.016-112.981333q0-98.005333 66.986667-166.016t166.997333-68.010667q116.010667 0 192 89.984 75.989333-89.984 192-89.984 100.010667 0 166.997333 68.010667t66.986667 166.016q0 77.994667-52.010667 162.005333t-112.981333 146.005333-198.997333 185.984z"',//心形
     ];

	//基因集合
	var geneSet_${biomarkerTestRandom} = null;

	//测试时间集合
	var testDateSet_${biomarkerTestRandom} = null;

	//x轴显示数据
	var xAxisValueArr_${biomarkerTestRandom} = new Array("");

	//病历号
	var referenceCode_${biomarkerTestRandom} = null;

	//节点数据
	var itemList_${biomarkerTestRandom} = null;

	//展现结果集
	var evaluationSet_${biomarkerTestRandom} = null;

	$(function() {
		
		$("#surgeryBiomarker_${biomarkerTestRandom}").closest(".widget-box").find(".dropdown-toggle").hide();
		
		
		$("#biomarkerTestButton_${biomarkerTestRandom}").click(function() {
			var head= document.getElementsByTagName('head')[0];
			var scriptEcharts= document.createElement('script');
			scriptEcharts.type= 'text/javascript';
			scriptEcharts.src= '${ctxStatic }/echarts/${style }.js';
			head.appendChild(scriptEcharts);
			
			$.post("${pageContext.request.contextPath}/bi/dfView/biomarkerTest/getBiomarkerTest", {
				"studyId" : requestParam.studyId,
				"referenceCode" : $("#referenceCode_${biomarkerTestRandom}").val()
			}, function(data) {

				if (data.success) {
					xAxisValueArr_${biomarkerTestRandom} = new Array("");
					geneSet_${biomarkerTestRandom} = data.geneSet;
					testDateSet_${biomarkerTestRandom} = data.testDateSet;
					referenceCode_${biomarkerTestRandom} = data.referenceCode;
					itemList_${biomarkerTestRandom} = data.itemList;
					evaluationSet_${biomarkerTestRandom} = data.evaluationSet;
					initChart_${biomarkerTestRandom}();
					$("#chartShowErrorDiv_${biomarkerTestRandom}").hide();
				}else{
					$("#surgeryBiomarker_${biomarkerTestRandom}").hide();
					$("#chartShowErrorDiv_${biomarkerTestRandom}").show();
					$("#chartShowErrorDiv_${biomarkerTestRandom}").html("<spring:message code='da.dataframe.noresults'/>");
				}
			})
		})
		//自动补全
		$("#referenceCode_${biomarkerTestRandom }").typeahead({
			source:function(query,process){
				 $.post('${pageContext.request.contextPath}/bi/dfView/biomarkerTest/matchPatientCode',
						{"patientCode":query, "studyId":requestParam.studyId},
						function (data) {
			            	process(data.patientCodeList);
						}
			     );
			}
		});

	});

	function initChart_${biomarkerTestRandom}() {
		$("#surgeryBiomarker_${biomarkerTestRandom}").show();
		var bioChart = echarts.init(document.getElementById('surgeryBiomarker_${biomarkerTestRandom}'),'myStyle');
		//循环用药数组
		for ( var i in testDateSet_${biomarkerTestRandom}) {
			var testDate = testDateSet_${biomarkerTestRandom}[i];
			var xAxisValue = getXAxisValue_${biomarkerTestRandom}(testDate);
			if (xAxisValueArr_${biomarkerTestRandom}.indexOf(xAxisValue) == -1)
				xAxisValueArr_${biomarkerTestRandom}.push(xAxisValue);
		}
		var bio_schema = [ {
			name : 'Date',
			index : 0,
			text : '<spring:message code="biomarkertest.view.date"/>'
		}, {
			name : 'Patient eCode',
			index : 1,
			text : '<spring:message code="biomarkertest.view.patientcode"/>'
		}, {
			name : 'Gene',
			index : 2,
			text : '<spring:message code="biomarkertest.view.gene"/>'
		}, {
			name : 'Result',
			index : 3,
			text : '<spring:message code="biomarkertest.view.result"/>'
		} ];

		var bio_Biomarker = [ "",//0
		"Positive",//1
		"Nagetive",//2
		"Neutral",//3
		];

		bio_option = {
			title : [ {
				text : '<spring:message code="biomarkertest"/>',
				left : 'center'
			}, {
				text : '<spring:message code="biomarkertest.view.patientcode"/>:' + referenceCode_${biomarkerTestRandom},
				left : 'left',
				textStyle: {
					fontWeight: 'normal',
					fontSize:15
				}
			} ],
			legend : {
				y : 'top',
				data : evaluationSet_${biomarkerTestRandom},
				top : '7%',
				itemWidth : 14,
				textStyle : {
					fontSize : 12
				}
			},
			grid : {
				x : '15%',
				bottom : '15%',
				top : '15%'
			},
			tooltip : {
				show:<c:if test="${isPad eq '1'}">false</c:if><c:if test="${isPad ne '1'}">true</c:if>,
				padding : 10,
				backgroundColor : '#222',
				borderColor : '#000',
				borderWidth : 1,
				formatter : function(params) {
					 return bio_schema[0].text + '：' + xAxisValueArr_${biomarkerTestRandom}[params.value[0]] + '<br>'
		                + bio_schema[1].text + '：' + referenceCode_${biomarkerTestRandom} + '<br>'
		                + bio_schema[2].text + '：' + params.value[2] + '<br>'
		                + bio_schema[3].text + '：' + params.value[3] + '<br>'
				}
			},
			xAxis : {
				type : 'category',
				data : xAxisValueArr_${biomarkerTestRandom},
				name : "<spring:message code='biomarkertest.view.x'/>",
				boundaryGap : false,
				splitLine : {
					show : true,
					lineStyle : {
						color : '#ddd',
						type : 'dashed'
					}
				},
				axisTick : {
					interval : 0
				},
				axisLabel : {
					interval : 0,
					rotate : 45
				}
			},
			yAxis : {
				type : 'category',
				data : geneSet_${biomarkerTestRandom},
				name : "<spring:message code='biomarkertest.view.y'/>",
				boundaryGap : false,
				splitLine : {
					show : true,
					lineStyle : {
						color : '#ddd',
						type : 'dashed'
					}
				}
			},
			//color:['#FD026C'],
			series : initSeries_${biomarkerTestRandom}()
		};
		//biomaker path end

		// 使用刚指定的配置项和数据显示图表。
		bioChart.setOption(bio_option);
	}

	//获取x轴值
	function getXAxisValue_${biomarkerTestRandom}(testDate) {
		var testDate = new Date(testDate);
		var formatDate = testDate.getFullYear() + "." + (testDate.getMonth() + 1) + "." + testDate.getDate();
		return formatDate;
	}

	function initSeries_${biomarkerTestRandom}() {
		var serie = [];
		
		//循环创建每个节点
		for ( var index in evaluationSet_${biomarkerTestRandom}) {
			/* if (geneSet[yIndex] == '') {
				continue;
			} */

			var evaluation = evaluationSet_${biomarkerTestRandom}[index];
			var item = {
				name : evaluation,
				type : 'scatter',
				symbol: symbolArr_${biomarkerTestRandom}[index],
				data : initNode_${biomarkerTestRandom}(evaluation) //创建节点
			}

			serie.push(item);
		}

		return serie;
	}

	function initNode_${biomarkerTestRandom}(evaluation) {
		var itemData = new Array();
		//循环药物数组 找出包含该药物的对象并放入数组
		for ( var i in itemList_${biomarkerTestRandom}) {
			var biomarkerTest = itemList_${biomarkerTestRandom}[i];
			if (biomarkerTest.evaluation == evaluation) {
				var node = new Object();
				var nodeValue = new Array();
				//x轴
				nodeValue.push(xAxisValueArr_${biomarkerTestRandom}.indexOf(getXAxisValue_${biomarkerTestRandom}(biomarkerTest.testDate)));
				//y轴
				nodeValue.push(geneSet_${biomarkerTestRandom}.indexOf(biomarkerTest.gene));
				//基因
				nodeValue.push(biomarkerTest.gene);
				//结果
				nodeValue.push(biomarkerTest.evaluation);
				node.value = nodeValue;
				itemData.push(node);
			}
		}
		return itemData;
	}
</script>
