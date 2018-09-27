<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" buffer="1024kb"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>
<script src="${ctxStatic }/echarts/${style }.js"></script>
<c:set value="<%=(int)(Math.random()*1000) %>" var="biomarkerTestRandom" />
<center id="biomarkerTestBarCenter_${biomarkerTestRandom}">
	<i class='icon-spinner icon-spin blue bigger-250'></i>
</center>
<div class="chartCont" id="chartCont_${biomarkerTestRandom}">
  <div class="row">
    <div class="col-xs-12">
      <div id="biomarkerTestBar_${biomarkerTestRandom }" style="height: ${viewChartHeight}px;"></div>
    </div>
  </div>
</div>
<center id="subBiomarkerTestBarCenter_${biomarkerTestRandom}" style="display: none;">
  <i class='icon-spinner icon-spin blue bigger-250'></i>
</center>
<div class="chartCont" id="subChartCont_${biomarkerTestRandom}" style="display: none;">
  <div class="row">
    <div class="col-xs-12">
      <div id="subBiomarkerTestBar_${biomarkerTestRandom }" style="height: ${viewChartHeight}px;"></div>
    </div>
  </div>
  <div class="row">
    <div class="col-xs-12">
      <div id="subBiomarkerTestPie_${biomarkerTestRandom }" style="height: ${viewChartHeight}px;"></div>
    </div>
  </div>
</div>
<script>
var filterWidgetMainStr_${biomarkerTestRandom} = 
	 '<div class="row">'
	+'	<div class="col-xs-12">'
 	+'		<h5 class="header smaller lighter blue no-margin">'
	+'			<spring:message code="biomarkertest.title.chart.type" />'
	+'			:'
	+'		</h5>' 
	+'		<div class="orderByGroup">'
	+'			<select id="chartTypeSelect_${biomarkerTestRandom}"'
	+'				onchange="changeChartType_${biomarkerTestRandom}(this)">'
	+'					<option value="pie"><spring:message code="demographic.chart.pie" /></option>'
     +'					<option value="bar"><spring:message code="demographic.chart.bar" /></option>'
	+'			</select>'
	+'		</div>'
	+'	</div>'
	+'</div>'
    + '<div class="row">'
     +'	<div class="col-xs-12">'
     +'		<h5 class="header smaller lighter blue no-margin">'
     +'			<spring:message code="biomarkertest.title.times" />'
     +'			:'
     +'		</h5>'
     +'		<div class="orderByGroup">'
     +'			<select id="timesSelect_${biomarkerTestRandom}"'
     +'				onchange="getDataByEvaluation_${biomarkerTestRandom}()">'
     +'				<option value=""><spring:message code="treatment.journey.all" /></option>'
     <c:forEach var="time" items="${timesList}">
     +'					<option value="${time}">${time}</option>'
     </c:forEach>
     +'			</select>'
     +'		</div>'
     +'	</div>'
     +'</div>'
	+'<div class="row">'
	+'	<div class="col-xs-12">'
	+'		<h5 class="header smaller lighter blue no-margin">'
	+'			<spring:message code="biomarkertest.title.expression" />'
	+'			:'
	+'		</h5>'
	+'		<div class="orderByGroup">'
	+'			<select id="expressionSelect_${biomarkerTestRandom}"'
	+'				onchange="getDataByEvaluation_${biomarkerTestRandom}()">'
					<c:forEach var="evaluation" items="${evaluationList}" varStatus="status">
	+'					<option value="${evaluation}">${evaluation}</option>'
					</c:forEach>
	+'			</select>'
	+'		</div>'
	+'	</div>'
	+'</div>'
	+'<div class="row">'
	+'	<div class="col-xs-12">'
	+'		<h5 class="header smaller lighter blue no-margin">'
	+'			<spring:message code="biomarkertest.bar.x" />'
	+'			:'
	+'		</h5>'
	+'		<div class="orderByGroup">'
				<c:forEach var="biomarker" items="${biomarkerList}" varStatus="status">
	+'				<div class="checkbox"><label>'
	+'					<input class="ace" type="checkbox" name="biomarker_${biomarkerTestRandom}" value="${biomarker}" style="width: 1px; height: 1px;">'
	+'					<span class="lbl">${biomarker}</span>'
	+'				</label></div>'
				</c:forEach>
	+'		</div>'
	+'	</div>'
	+'</div>';
	
	var $filterWidgetMain_${biomarkerTestRandom};
	
var measureType_${biomarkerTestRandom}='${measureType}';


function hideMore_${biomarkerTestRandom}(e){
	$(e).next().show();
	e.style.display='none';
}

  $(function(){
      $("#subBiomarkerTestBar_${biomarkerTestRandom}").parent().parent().css({"width":"100%","height":"0","overflow-y":"auto"});
	  //初始化toolbar中的下拉数据
	  $filterWidgetMain_${biomarkerTestRandom} = $("#chartCont_${biomarkerTestRandom}").closest(".widget-box").find(".widget-main:first");
	  $filterWidgetMain_${biomarkerTestRandom}.html(filterWidgetMainStr_${biomarkerTestRandom});
	  //取消下拉filter点击自动隐藏的功能
	  $filterWidgetMain_${biomarkerTestRandom}.closest('ul.dropdown-menu')
			.on('click', '[data-stopPropagation]', function (e) {
				e.stopPropagation();
			});
		
	  $('input:checkbox[name=biomarker_${biomarkerTestRandom}]').change(function() {
		  getDataByBiomarker_${biomarkerTestRandom}();
      });  
	  //默认选中所以的复选框
	  $("input:checkbox[name=biomarker_${biomarkerTestRandom}]").attr("checked",true);
	  //加载数据
	  $("#sizeListA_${biomarkerTestRandom}").show();
	  $("#sizeListMore_${biomarkerTestRandom}").hide();
	  getData_${biomarkerTestRandom}("biomarkerTestBar");
  }); 

function changeChartType_${biomarkerTestRandom}(e) {
  //切换pie图和bar图
  if(e.value=='pie'){
    $("#subBiomarkerTestBar_${biomarkerTestRandom}").parent().parent().css({"width":"100%","height":"0","overflow-y":"auto"});
    $("#subBiomarkerTestPie_${biomarkerTestRandom}").parent().parent().css({"width":"100%","height":"100%","overflow-y":"*"});
  }else{
    $("#subBiomarkerTestPie_${biomarkerTestRandom}").parent().parent().css({"width":"100%","height":"0","overflow-y":"auto"});
    $("#subBiomarkerTestBar_${biomarkerTestRandom}").parent().parent().css({"width":"100%","height":"100%","overflow-y":"*"});
  }
}

  //根据 Expression 筛选数据
  function getDataByEvaluation_${biomarkerTestRandom}() {
	  delay_till_last('id', function() {//注意 id 是唯一的
		  getData_${biomarkerTestRandom}("biomarkerTestBar");
	    }, 1500);
  }
  var _timer_${biomarkerTestRandom} = {};
  function delay_till_last(id, fn, wait) {
      if (_timer_${biomarkerTestRandom}[id]) {
          window.clearTimeout(_timer_${biomarkerTestRandom}[id]);
          delete _timer_${biomarkerTestRandom}[id];
      }
   
      return _timer_${biomarkerTestRandom}[id] = window.setTimeout(function() {
          fn();
          delete _timer_${biomarkerTestRandom}[id];
      }, wait);
  }
//根据 Expression 筛选数据
  function getDataByBiomarker_${biomarkerTestRandom}() {
	  delay_till_last('id', function() {//注意 id 是唯一的
		  getData_${biomarkerTestRandom}("biomarkerTestBar");
	    }, 1500);
	  
  }
  var tempBiomarkerArr_${biomarkerTestRandom};
  //chartId = biomarkerTestBar
  function getData_${biomarkerTestRandom}(chartId, subBiomakers, prefBiomarker) {
	  $("#" + chartId + "Center_${biomarkerTestRandom}").html("<i class='icon-spinner icon-spin blue bigger-250'></i>");
	  $("#" + chartId + "Center_${biomarkerTestRandom}").show();
      $("#chartCont_${biomarkerTestRandom}").show();
      if(subBiomakers != null && subBiomakers != "" && subBiomakers != undefined){
          $("#subChartCont_${biomarkerTestRandom}").show();
      }else{
          $("#subChartCont_${biomarkerTestRandom}").hide();
      }
	  var expressionSelect = $("#expressionSelect_${biomarkerTestRandom}").val();
	  //获取次数
	  var times = $("#timesSelect_${biomarkerTestRandom}").val();
	  var spCodesTemp = "";
      if(prefBiomarker == null || prefBiomarker == "" || prefBiomarker == undefined){
          $('input:checkbox[name=biomarker_${biomarkerTestRandom}]:checked').each(function(i){
              if(0==i){
                  spCodesTemp = $(this).val();
              }else{
                  spCodesTemp += (","+$(this).val());
              }
          });
      }else{
          spCodesTemp = subBiomakers;
      }

	  // biomarkerTestChart
	  var biomarkerTestChart = echarts.init(document.getElementById(chartId + '_${biomarkerTestRandom }'),'myStyle');
	
	  var xScale=0;
		
	  biomarkerTestChart.on("dataZoom",function(params){
			var start=params.start;
			var end=params.end;
			xScale=(end-start)/100;
	  })
	  var option = "";
	  $.post("${pageContext.request.contextPath}/bi/dfView/biomarkerTest/getData",{studyId:'${studyId}',cohortIds:'${cohorts}',evaluation:expressionSelect,biomarkers:spCodesTemp,times:times,measureType:measureType_${biomarkerTestRandom}},function(data){
		  // options
		  var x = data.x;
		  var y = new Array();
		  //用于转换数据，需要显示勾选中的基因数据，即后台没有查询出对应基因的数据时此基因也需要显示
		  var yy = data.y;

		  biomarkerArr = new Array();
		  var labTestData = {};
		  if(subBiomakers == null || subBiomakers == "" || subBiomakers == undefined){
              $("input[name='biomarker_${biomarkerTestRandom}']:checked").each(function(){
                var biomarker = $(this).val();
                biomarkerArr.push(biomarker);
                labTestData[biomarker]=",";
                y.push(0);
              });
              tempBiomarkerArr_${biomarkerTestRandom} = biomarkerArr;
          }else{
            var subBiomakerArr = subBiomakers.split(",");
            for(var i=0; i<subBiomakerArr.length; i++){
              var biomarker = subBiomakerArr[i];
              biomarkerArr.push(biomarker);
              labTestData[biomarker]=",";
              y.push(0);
            }
          }
		  //基因相同，则获取对应数据下标来替换y中的0值
		  for(var m in biomarkerArr){
			  var baBiomarker = biomarkerArr[m];
			  for(var n in x){
				  var xBiomarker = x[n].split(",")[0];
				  if(baBiomarker==xBiomarker){
					  y[m]=yy[n];
					  break;
				  }
			  }
		  }
		  if(x.length==0){
		      if(prefBiomarker == null || prefBiomarker == "" || prefBiomarker == undefined){
                  //只有在没有配置基因数据的情况下才显示没有数据
                  var biomarkerInputLength = ${fn:length(biomarkerList)};
                  if(biomarkerInputLength==0){
                      $("#" + chartId + "Center_${biomarkerTestRandom}").html("<spring:message code='dashboard.data.empty'/>");
                      <%--$("#" + chartId + "_${biomarkerTestRandom }").hide();--%>
                      return;
                  }
              }else{
                  $("#" + chartId + "Center_${biomarkerTestRandom}").html(prefBiomarker + " " + "<spring:message code='dashboard.data.empty'/>");
                  <%--$("#" + chartId + "_${biomarkerTestRandom }").hide();--%>
                  return;
              }

			  //没有数据filter清空
			  //$filterWidgetMain_${biomarkerTestRandom}.html("");
			  //没有数据的情况下继续执行
			  //return;

			  //$("input[name='biomarker_${biomarkerTestRandom}']:checked").each(function(){
			  //    biomarkerArr.push($(this).val());
			  //});
		  }else{
			  //var biomarkerArr = new Array();
			  //var labTestData = {};
			  for(i=0;i<x.length;i++) {
				  var biomarker = x[i].split(",")[0];
				  //biomarkerArr.push(biomarker);
				  var curAmount = x[i].split(",")[1];
				  var sumAmount = x[i].split(",")[2];
				  //biomarkerTestBar中当柱状图中值为0时柱状图顶部总数不显示
				  if(curAmount=='0'){
					  sumAmount='';
				  }
				  labTestData[biomarker]=curAmount+","+sumAmount;

			  }
		  }

          if(subBiomakers == null || subBiomakers == "" || subBiomakers == undefined){
              biomarkerTestChart.on("click", function(param){
                  $("#subBiomarkerTestBarCenter_${biomarkerTestRandom}").html("<i class='icon-spinner icon-spin blue bigger-250'></i>").show();
                  $("#subChartCont_${biomarkerTestRandom}").hide();
                  $("#subBiomarkerTestBar_${biomarkerTestRandom}").empty();
                  $("#subBiomarkerTestPie_${biomarkerTestRandom}").empty();
                  var biomarker = tempBiomarkerArr_${biomarkerTestRandom}[param.dataIndex];
                  //根据biomarker获得subBiomarkers
                  $.post("${pageContext.request.contextPath}/bi/dfView/biomarkerTest/getSubData",{studyId:'${studyId}',biomarker:biomarker,measureType:measureType_${biomarkerTestRandom}},function(data){
                      getData_${biomarkerTestRandom}("subBiomarkerTestBar", data, biomarker);
                  },"text");
              });
          }

        var titleText = "";
          if(prefBiomarker == null || prefBiomarker == "" || prefBiomarker == undefined){
              titleText = '<spring:message code="biomarkertest"/>（'+ "<spring:message code='biomarkertest.title.${measureType}' text='${measureType}'/>" +'）';
          }else{
              titleText = prefBiomarker + " " + '<spring:message code="biomarkertest.title.chart.sub.type" />';
          }
		  option = {
		    title : {
				text : titleText,
				left : 'center'
			},
		    grid: {
		        left: '4%',
		        right: '8%',
		        top: '10%',
		        height : '72%',
		        containLabel: true
		    },
		    tooltip : {

				     triggerOn : 'mousemove',
					 formatter: function (obj) {
				                var seriesName = obj.seriesName;
				                var name = obj.name;
				                var value = obj.value;
				                var part = labTestData[name].split(",")[0];
				                var sum = labTestData[name].split(",")[1];
				            	return '<spring:message code="biomarkertest.bar.y" arguments="'+expressionSelect+'"/>: '+ value + '% <br/> <spring:message code="biomarkertest.bar.x"/> : ' + name+' <br/> <spring:message code="biomarkertest.tip.number"/>(' + $("#expressionSelect_${biomarkerTestRandom}").val() + ') : '+part+' <br/> <spring:message code="biomarkertest.tip.totalNumber"/> : '+sum;
				        }

		    },
		    xAxis : [
		        {
		            type : 'category',
		            name : "<spring:message code='biomarkertest.bar.x'/>",
		            data : biomarkerArr,
		            axisLabel : {
		            	 rotate : 45,
						interval : function(index,value){
							var length=parseInt(biomarkerArr.length*xScale);
							if(length>8){
								var showIndex=parseInt(length/8);
								if(index%showIndex==0){
									return value;
								}
							}else{
								return value;
							}
						}
					}
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value',
		            name : "<spring:message code='biomarkertest.bar.y' arguments='"+expressionSelect+"'/>",
		            axisLabel:{
		            	formatter:function (value, index) {
		            	    return value+"%";
		            	}
		            }
		        }
		    ],
		    series : [
		        {
		            type:'bar',
		            barWidth:50,
		            name:"<spring:message code='biomarkertest.title.percentage.population'/>",
		            data:y,
		            label:{
		            	normal:{
		            		show:true,
		            		position: 'top',
		            		formatter:function(data){
				                var name = data.name;
				                return labTestData[name].split(",")[1];
		            		}
		            	}
		            },
		        }

		    ],
		    dataZoom : {
				type : 'slider',
				show : biomarkerArr.length<11?false:true,
				endValue:10
			}
		};
		  $("#" + chartId + "Center_${biomarkerTestRandom}").hide();
		  // 使用刚指定的配置项和数据显示图表。
		  biomarkerTestChart.setOption(option);

        if(prefBiomarker != null && prefBiomarker != "" && prefBiomarker != undefined){

          var dataArr = [];
          for(var i=0; i<biomarkerArr.length; i++){
            var obj = {};
            obj.value = y[i];
            obj.name = biomarkerArr[i];
            dataArr[i] = obj;
          }

          var biomarkerTestPie = echarts.init(document.getElementById('subBiomarkerTestPie_${biomarkerTestRandom }'),'myStyle');

          var pieOption = {
            title : {
              text: titleText,
              right: 'center'
            },
            tooltip : {
              triggerOn : 'mousemove',
//              formatter: "{b} : {d}%",
                formatter: function (obj) {
                    var seriesName = obj.seriesName;
                    var name = obj.name;
                    var value = obj.value;
                    var part = labTestData[name].split(",")[0];
                    var sum = labTestData[name].split(",")[1];
                    return '<spring:message code="biomarkertest.bar.y" arguments="'+expressionSelect+'"/>: '+ value + '% <br/> <spring:message code="biomarkertest.bar.x"/> : ' + name+' <br/> <spring:message code="biomarkertest.tip.number"/>(' + $("#expressionSelect_${biomarkerTestRandom}").val() + ') : '+part+' <br/> <spring:message code="biomarkertest.tip.totalNumber"/> : '+sum;
                }
            },
            series : [
              {
                type:'pie',
                radius : '130',
                center: ['50%', '50%'],
                name:titleText,
                data:dataArr,
                labelLine: {
                  normal: {
                    show: true,
                    length: 3,
                    length2: 3
                  }
                }
              }
            ],
            legend: {
              top: '10%',
              left:'center',
              data:biomarkerArr
            }
          };
          //使用刚指定的配置项和数据显示图表。
          biomarkerTestPie.setOption(pieOption);
        }
		},"json")
  }
</script>
<input type="hidden" id="patientIds" value="${patientIds}">
