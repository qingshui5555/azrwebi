<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" buffer="1024kb"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>
<script src="${ctxStatic }/echarts/${style }.js"></script>
<c:set value="<%=(int)(Math.random()*1000) %>" var="questionnaireRandom" />   
<!-- <center id="questionnaireBarCenter">
	<i class='icon-spinner icon-spin blue bigger-250'></i>
</center> -->
<div class="chartCont" id="chartCont_${questionnaireRandom}">
  <div class="row">
    <div class="col-xs-12">
      <div id="questionnaireView_${questionnaireRandom }" style="height: ${viewChartHeight}px;"></div>
    </div>
   <%--
     <div class="col-xs-2">
      <div class="widget-box ">
        <div class="widget-header widget-header-small header-color-blue">
          <h5><spring:message code="patientEvent.filter.list"/> <i class="icon-filter"></i></h5>
        </div>
        <div class="widget-body">
           <div class="widget-main form">
            <!--/.row--> 
            <div class="row">
              <div class="col-xs-12">
                   <h5 class="header smaller lighter blue no-margin"><spring:message code='questionnaire.filtet.title.type'/>:</h5>
								<div class="orderByGroup">
									<c:forEach var="type" items="${typeList}" varStatus="status" begin="0" end="5" step="1">
									    <c:if test="${status.index==0}">
												<input type="radio" name="questionnaire_${questionnaireRandom }" data-type="questionnaire" style="margin-top: 0px; margin-right: 5px;" checked="checked" value="${type}">
												    <span title="${type}">${fn:substring(type,0, 15)}</span><br />
										</c:if>
										
										<c:if test="${status.index!=0}">
												<input type="radio" name="questionnaire_${questionnaireRandom }" style="margin-top: 1px; margin-right: 5px;" value="${type}">&nbsp;&nbsp;${type}<br />
										</c:if>
										
									    <c:if test="${status.index==5}">
									         <a style="cursor: pointer;" id="sizeListA_${questionnaireRandom }" style="margin-top: 1px; margin-right: 5px;" onclick="hideMore_${questionnaireRandom }(this)">More&gt;&gt;</a>
									    </c:if>
									</c:forEach>
									<c:if test="${fn:length(typeList)>6}">
									    <div class="orderByGroup" id="sizeListMore_${questionnaireRandom }"  style="display:none;margin-top:-5px">
									        <c:forEach var="type" items="${typeList}" varStatus="status" begin="6"  step="1">
												<input type="radio" name="questionnaire_${questionnaireRandom }" style="margin-top: 1px; margin-right: 5px;" value="${type}">&nbsp;&nbsp;${type}<br />
											</c:forEach>
									    </div>
									</c:if>
								</div>
							</div>
            </div>
            <!--/.row-->
            
            <!--/.row--> 
            <div class="row">
              <div class="col-xs-12">
                   <h5 class="header smaller lighter blue no-margin"><spring:message code='questionnaire.filtet.title.cohort'/>:</h5>
								<div class="orderByGroup">
									<c:forEach var="patientGroupName" items="${patientGroupNameMap}" varStatus="status" begin="0" end="5" step="1">
										<input type="checkbox" name="cohort_${questionnaireRandom }" value="${patientGroupName.key}">&nbsp;&nbsp;${patientGroupName.value}<br />
									    <c:if test="${status.index==5}">
									         <a style="cursor: pointer;" id="sizeListA_${questionnaireRandom }" onclick="hideMore_${questionnaireRandom }(this)">More&gt;&gt;</a>
									    </c:if>
									</c:forEach>
									<c:if test="${fn:length(patientGroupNameMap)>6}">
									    <div class="orderByGroup" id="sizeListMore_${questionnaireRandom }"  style="display:none;margin-top:-5px">
									        <c:forEach var="patientGroupName" items="${patientGroupNameMap}" varStatus="status" begin="6"  step="1">
												<input type="checkbox" name="cohort_${questionnaireRandom }" value="${patientGroupName.key}">&nbsp;&nbsp;${patientGroupName.value}<br />
											</c:forEach>
									    </div>
									</c:if>
								</div>
							</div>
            </div>
            <!--/.row-->
          </div>
        </div>
      </div>
    </div>
    --%>
  </div>
</div>

<script>
var filterWidgetMainStr_${questionnaireRandom} = 
	 '<div class="row">'
	+'	<div class="col-xs-12">'
 	+'		<h5 class="header smaller lighter blue no-margin">'
	+'			<spring:message code="questionnaire.filtet.title.type" />:'
	+'		</h5>' 
	+'		<div class="orderByGroup">'
				<c:forEach var="type" items="${typeList}" varStatus="status" begin="0" end="5" step="1">
					<c:if test="${status.index==0}">
	+'				<div class="radio"><label>'
	+'					<input class="ace" type="radio" name="questionnaire_${questionnaireRandom }" data-type="questionnaire"'
	+'						style="margin-top: 0px; margin-right: 5px;" checked="checked" value="${type}">'
	+'					<span class="lbl" title="${type}">${fn:substring(type,0, 15)}</span>'
	+'				</label></div>'
					</c:if>
					<c:if test="${status.index!=0}">
	+'				<div class="radio"><label>'
	+'					<input class="ace" type="radio" name="questionnaire_${questionnaireRandom }"'
	+'						style="margin-top: 1px; margin-right: 5px;" value="${type}">'
	+'					<span class="lbl">${type}</span>'
	+'				</label></div>'
					</c:if>
					<c:if test="${status.index==5}">
	+'					<a style="cursor: pointer;" id="sizeListA_${questionnaireRandom }"'
	+'						style="margin-top: 1px; margin-right: 5px;"'
	+'						onclick="hideMore_${questionnaireRandom }(this)">More&gt;&gt;</a>'
					</c:if>
				</c:forEach>
				<c:if test="${fn:length(typeList)>6}">
	+'				<div class="orderByGroup" id="sizeListMore_${questionnaireRandom }"'
	+'					style="display: none; margin-top: -5px">'
						<c:forEach var="type" items="${typeList}" varStatus="status" begin="6" step="1">
	+'					<div class="radio"><label>'					
	+'						<input class="ace" type="radio" name="questionnaire_${questionnaireRandom }"'
	+'							style="margin-top: 1px; margin-right: 5px;" value="${type}">'
	+'						<span class="lbl">${type}</span>'
	+'					</label></div>'
						</c:forEach>
	+'				</div>'
				</c:if>
	+'		</div>'
	+'	</div>'
	+'</div>'
	+'<div class="row">'
	+'	<div class="col-xs-12">'
	+'		<h5 class="header smaller lighter blue no-margin">'
	+'			<spring:message code="questionnaire.filtet.title.cohort" />:'
	+'		</h5>'
	+'		<div class="orderByGroup">'
				<c:forEach var="patientGroupName" items="${patientGroupNameMap}" varStatus="status" begin="0" end="5" step="1">
	+'				<div class="checkbox"><label>'
	+'				<input class="ace" type="checkbox" name="cohort_${questionnaireRandom }"'
	+'					value="${patientGroupName.key}">'
	+'				<span class="lbl">${patientGroupName.value}</span>'
	+'				</label></div>'
					<c:if test="${status.index==5}">
	+'					<a style="cursor: pointer;" id="sizeListA_${questionnaireRandom }"'
	+'						onclick="hideMore_${questionnaireRandom }(this)">More&gt;&gt;</a>'
					</c:if>
				</c:forEach>
				<c:if test="${fn:length(patientGroupNameMap)>6}">
	+'				<div class="orderByGroup" id="sizeListMore_${questionnaireRandom }"'
	+'					style="display: none; margin-top: -5px">'
						<c:forEach var="patientGroupName" items="${patientGroupNameMap}" varStatus="status" begin="6" step="1">'
	+'					<div class="checkbox"><label>'
	+'						<input class="ace" type="checkbox" name="cohort_${questionnaireRandom }"'
	+'							value="${patientGroupName.key}">'
	+'						<span class="lbl">${patientGroupName.value}</span>'
	+'					</label></div>'
						</c:forEach>
	+'				</div>'
				</c:if>
	+'		</div>'
	+'	</div>'
	+'</div>';
	
function hideMore_${questionnaireRandom }(e){
	$(e).next().show();
	e.style.display='none';
}

var chart_${questionnaireRandom };
var $filterWidgetMain_${questionnaireRandom};
$(function() {
	//初始化toolbar中的下拉数据
	$filterWidgetMain_${questionnaireRandom} = $("#chartCont_${questionnaireRandom}")
		.closest(".widget-box").find(".widget-main:first");
	$filterWidgetMain_${questionnaireRandom}.html(filterWidgetMainStr_${questionnaireRandom});
	//取消下拉filter点击自动隐藏的功能
	$filterWidgetMain_${questionnaireRandom}.closest('ul.dropdown-menu')
		.on('click', '[data-stopPropagation]', function (e) {
			e.stopPropagation();
		});
	
	 $('input:checkbox[name=cohort_${questionnaireRandom }]').change(function() {
		 delay_till_last('id', function() {//注意 id 是唯一的
			 getQuestionnaireData_${questionnaireRandom }();
		    }, 1500);
     });    
	 
	 $('.orderByGroup input[type=radio][name=questionnaire_${questionnaireRandom }]').click(function(){
		     getQuestionnaireData();
		})
	 
	$("#sizeListA_${questionnaireRandom }").show();
	$("#sizeListMore_${questionnaireRandom }").hide();
	
    //默认选中所以的复选框
	$("input:checkbox[name=cohort_${questionnaireRandom }]").attr("checked",true);
	//加载数据
    getQuestionnaireData_${questionnaireRandom }();
});			


var _timer_${patientEventRandom} = {};
function delay_till_last(id, fn, wait) {
    if (_timer_${patientEventRandom}[id]) {
        window.clearTimeout(_timer_${patientEventRandom}[id]);
        delete _timer_${patientEventRandom}[id];
    }
 
    return _timer_${patientEventRandom}[id] = window.setTimeout(function() {
        fn();
        delete _timer_${patientEventRandom}[id];
    }, wait);
}

function getQuestionnaireData_${questionnaireRandom }() {
      var myChart_questionnaireView = echarts.init(document.getElementById('questionnaireView_${questionnaireRandom}'),'myStyle');
	  //文件类型
	  var questionnaireType  =$('.orderByGroup input[type=radio][name=questionnaire_${questionnaireRandom }]:checked').val(); 
	  //选中的cohort
	  var spCodesTemp = "";
	  $('input:checkbox[name=cohort_${questionnaireRandom }]:checked').each(function(i){
	       if(0==i){
	        spCodesTemp = $(this).val();
	       }else{
	        spCodesTemp += (","+$(this).val());
	       }
	  });
	  $.post("${pageContext.request.contextPath}/bi/dfView/questionnaire/getQuestionnaireData",
			  {
				studyId : '${studyId}',
				cohorts : spCodesTemp,
				type : questionnaireType,
				configJson : $("#configJson").val()
			  },function(data){
				  console.log(data);
				  if(!spCodesTemp){
					  data.x=["<spring:message code='dashboard.filter.search.all' />"];
				  }
				  console.log(data);
                  var option = {
                      title: [
                          {
                              text: data.type,
                              left: 'center'
                          }
                      ],
                      tooltip: {
                        show:<c:if test="${isPad eq '1'}">false</c:if><c:if test="${isPad ne '1'}">true</c:if>,
                          trigger: 'item',
                          axisPointer: {
                              type: 'shadow'
                          }
                      },
                      grid: {
                          left: '10%',
                          right: '15%',
                          bottom: '15%'
                      },
                      xAxis: {
                          type: 'category',
                          name: '<spring:message code="questionnaire.bar.x"/>',
                          data: data.x,
                          boundaryGap: true,
                          nameGap: 4,
                          splitArea: {
                              show: false
                          },
                          splitLine: {
                              show: false
                          }
                      },
                      yAxis: {
                          type: 'value',
                          name: '<spring:message code="questionnaire.bar.y"/>',
                          splitArea: {
                              show: true
                          }
                      },
                      series: [
                          {
                              type:'bar',
                              barWidth: '60',
                              data:data.barData,
                              tooltip: {
                                show:<c:if test="${isPad eq '1'}">false</c:if><c:if test="${isPad ne '1'}">true</c:if>,
                                  formatter: function (param) {
                                      return [
                                            '<span><spring:message code="questionnaire.bar.x"/>: ' + param.name + '</span>',
                                            '<span><spring:message code="questionnaire.tooltip.averagescore"/>: ' + param.data + '</span>'
                                      ].join('<br/>')
                                  }
                              }
                          },
                          {
                              type: 'boxplot',
                              data: data.boxData,
                              tooltip: {
                                show:<c:if test="${isPad eq '1'}">false</c:if><c:if test="${isPad ne '1'}">true</c:if>,
                                  formatter: function (param) {
                                      return [
                                           '<span><spring:message code="questionnaire.bar.x"/>: ' + param.name + '</span>',
                                           '<span><spring:message code="questionnaire.bar.errorBar"/>: '+ param.data[0] + ' - ' + param.data[4] + '</span>'
                                      ].join('<br/>')
                                  }
                              }
                          }
                      ]
                  };
                  myChart_questionnaireView.setOption(option);
	  },"json")

}

</script>
<input type="hidden" id="configJson" value="${configJson}">
