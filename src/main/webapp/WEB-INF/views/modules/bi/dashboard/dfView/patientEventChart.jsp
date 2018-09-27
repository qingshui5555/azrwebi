<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" buffer="1024kb"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>
<script src="${ctxStatic }/echarts/${style }.js"></script>
<c:set value="<%=(int)(Math.random()*1000) %>" var="patientEventRandom" />   
<center id="patientCenter_${patientEventRandom}">
	<i class='icon-spinner icon-spin blue bigger-250'></i>
</center>
<div class="chartCont" id="chartCont_${patientEventRandom}">
  <div class="row">
    <div class="col-xs-12">
      <div id="eventView_${patientEventRandom}" style="height:400px"></div>
    </div>
<%--     <div class="col-xs-2">
      <div class="widget-box">
        <div class="widget-header widget-header-small header-color-blue">
            <h5><spring:message code="patientEvent.filter.list"/> <i class="icon-filter"></i></h5>
        </div>
        <div class="widget-body">
           <div class="widget-main">
            <div class="row">
              <div class="col-xs-12">
                <h5 class="header smaller lighter blue no-margin"><spring:message code="patientEvent.filter.size" /> :</h5>
                <div class="orderByGroup">
                <select id="size_${patientEventRandom}" onchange="getsize_${patientEventRandom}()">
                        <option value="20">20</option>
                        <option value="50">50</option>
                        <option value="100">100</option>
                </select>
                </div>
            </div>
            <div class="col-xs-12">
                <h5 class="header smaller lighter blue no-margin">
                    <spring:message code="patientEvent.filter.patientCode" />
                    :
                </h5>

                <div class="orderByGroup" id="patientCodeList_${patientEventRandom}"></div>
                <a style="cursor: pointer; display: none;" id="sizeListA_${patientEventRandom}"
                        onclick="hideMore_${patientEventRandom}(this)">More&gt;&gt;</a>
                    <div class="orderByGroup" id="sizeListMore_${patientEventRandom}"  style="display: none;margin-top:-10px"></div>
            </div>

            <div class="col-xs-12">
                <h5 class="header smaller lighter blue no-margin">
                    <spring:message code="patientEvent.filter.color" /> : <spring:message code="patientEvent.filter.assessmentofresponse" />
                </h5>
                <div class="orderByGroup" id="assessmentResponseList_${patientEventRandom}">

                </div>
              </div>
            </div>
            <!--/.row--> 
            <div class="row">
              <div class="col-xs-12">
                <h5 class="header smaller lighter blue no-margin"><spring:message code="patientEvent.filter.shape"/> : <spring:message code="patientEvent.filter.eventtype"/></h5>
                <div class="orderByGroup" id="eventList_${patientEventRandom}">

                </div>
              </div>
            </div>
            <!--/.row-->
          </div>
        </div>
      </div>
    </div> --%>
  </div>
</div>

<script type="text/javascript">
var filterWidgetMainStr_${patientEventRandom} = 
	 '<div class="row">'
	+'	<div class="col-xs-12">'
 	+'		<h5 class="header smaller lighter blue no-margin">'
	+'			<spring:message code="patientEvent.filter.size" />:'
	+'		</h5>' 
	+'		<div class="orderByGroup">'
	+'			<select id="size_${patientEventRandom}"'
	+'				onchange="getsize_${patientEventRandom}()">'
	+'				<option value="20">20</option>'
	+'				<option value="50">50</option>'
	+'				<option value="100">100</option>'
	+'			</select>'
	+'		</div>'
	+'	</div>'
	+'	<div class="col-xs-12">'
	+'		<h5 class="header smaller lighter blue no-margin">'
	+'			<spring:message code="patientEvent.filter.patientCode" />:'
	+'		</h5>'
	+'		<div class="" id="patientCodeList_${patientEventRandom}"></div>'
	+'		<a style="cursor: pointer; display: none;"'
	+'			id="sizeListA_${patientEventRandom}"'
	+'			onclick="hideMore_${patientEventRandom}(this)">More&gt;&gt;</a>'
	+'		<div class="" id="sizeListMore_${patientEventRandom}"'
	+'			style="display: none; margin-top: -5px"></div>'
	+'	</div>'
	+'	<div class="col-xs-12">'
	+'		<h5 class="header smaller lighter blue no-margin">'
	+'			<spring:message code="patientEvent.filter.color" />:'
	+'			<spring:message code="patientEvent.filter.assessmentofresponse" />'
	+'		</h5>'
	+'		<div class="orderByGroup"'
	+'			id="assessmentResponseList_${patientEventRandom}"></div>'
	+'	</div>'
	+'</div>'
	+'<div class="row">'
	+'	<div class="col-xs-12">'
	+'		<h5 class="header smaller lighter blue no-margin">'
	+'			<spring:message code="patientEvent.filter.shape" />:'
	+'			<spring:message code="patientEvent.filter.eventtype" />'
	+'		</h5>'
	+'		<div class="orderByGroup" id="eventList_${patientEventRandom}">'
	+'		</div>'
	+'	</div>'
	+'</div>';


$("input:checkbox[name=assessmentResponse_${patientEventRandom}]").attr("checked",true);
$("input:checkbox[name=eventType_${patientEventRandom}]").attr("checked",true);
//改变获得patient-code数量
function getsize_${patientEventRandom}() {
	var size =$("#size_${patientEventRandom}").val();
	$("#eventView_${patientEventRandom}").attr("style","height:"+(size*20+100)+"px") ; 
	getPatientEventData_${patientEventRandom}(true);
	
}
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
var $filterWidgetMain_${patientEventRandom};
$(function(){  
	//初始化toolbar中的下拉数据
	$filterWidgetMain_${patientEventRandom} = $("#chartCont_${patientEventRandom}")
		.closest(".widget-box").find(".widget-main:first");
	$filterWidgetMain_${patientEventRandom}.html(filterWidgetMainStr_${patientEventRandom});
	//取消下拉filter点击自动隐藏的功能
	$filterWidgetMain_${patientEventRandom}.closest('ul.dropdown-menu')
		.on('click', '[data-stopPropagation]', function (e) {
			e.stopPropagation();
		});
	
	 getPatientEventData_${patientEventRandom}(false);
     $('input:checkbox[name=referenceCode_${patientEventRandom}]').change(function() {
    	 delay_till_last('id', function() {//注意 id 是唯一的
   		  getPatientEventData_${patientEventRandom}();
   	    }, 1500);
     });    
	
     $('input:checkbox[name=assessmentResponse_${patientEventRandom}]').change(function() {
    	 delay_till_last('id', function() {//注意 id 是唯一的
   		  getPatientEventData_${patientEventRandom}(false);
   	    }, 1500);
     }); 
     
     $('input:checkbox[name=eventType_${patientEventRandom}]').change(function() {
    	 delay_till_last('id', function() {//注意 id 是唯一的
   		  getPatientEventData_${patientEventRandom}(false);
   	    }, 1500);
     }); 
	
}); 
function hideMore_${patientEventRandom}(e){
	$(e).next().show();
	e.style.display='none';
}

function getPatientEventData_${patientEventRandom}(flag) {
	$("#patientCenter_${patientEventRandom}").show();
	// 基于准备好的dom，初始化echarts实例
	var myChart_eventView = echarts.init(document.getElementById('eventView_${patientEventRandom}'),'myStyle');
	var option = "";
	var spCodesTemp = "";
	  $('input:checkbox[name=referenceCode_${patientEventRandom}]:unchecked').each(function(i){
	       if(0==i){
	        spCodesTemp = $(this).val();
	       }else{
	        spCodesTemp += (","+$(this).val());
	       }
	  });
	  
	  var spAssessmentResponseTemp = "";
	  $('input:checkbox[name=assessmentResponse_${patientEventRandom}]:unchecked').each(function(i){
	       if(0==i){
	    	   spAssessmentResponseTemp = $(this).val()==''?"-1":$(this).val();
	       }else{
	    	   spAssessmentResponseTemp += (","+$(this).val());
	       }
	  });
	  
	  var spEventTypeTemp = "";
	  $('input:checkbox[name=eventType_${patientEventRandom}]:unchecked').each(function(i){
	       if(0==i){
	    	   spEventTypeTemp = $(this).val();
	       }else{
	    	   spEventTypeTemp += (","+$(this).val());
	       }
	  });
	  
	$.ajax({
		type: 'POST',
	    url: "${pageContext.request.contextPath}/bi/dfView/patientEventChart/getPatientEventChartData" ,
	    data: {studyId:'${studyId}',cohorts:$("#cohorts").val(),referenceCode:spCodesTemp,assessmentResponse:spAssessmentResponseTemp,eventType:spEventTypeTemp,other:$("#other").val(),pageNo:$("#size_${patientEventRandom}").val()} ,
	  	async:false,
	    success: function(data){
	    	$("#other").val(data.otherEvent);
	        console.log(data);
	        var y = data.y;
			var max = data.max;
			var patientCodeList = data.codeList;
			var assessmentResponseList = data.assessmentResponseList;
			var eventList = data.eventList;
			var toolTipPoint = data.toolTipPoint;
			var patientCodeListContent = $("#patientCodeList_${patientEventRandom}").html();
			var assessmentResponseListContent = $("#assessmentResponseList_${patientEventRandom}").html();
			var eventListContent = $("#eventList_${patientEventRandom}").html();
			$("#sizeListA_${patientEventRandom}").show();
			$("#sizeListMore_${patientEventRandom}").hide();
			// flag为true 表示切换了获取数据的数量  右边的 patient_code 列表更新
			if($.trim(patientCodeListContent)=="" || flag) {
				$("#patientCodeList_${patientEventRandom}").html("");
				$("#sizeListMore_${patientEventRandom}").html("");
				$("#sizeListA_${patientEventRandom}").hide();
				
				var html = "";
				for(var i = 0;i < patientCodeList.length; i++) {
					if(i<5) {
						//修改样式
						//html += "<input type='checkbox' name='referenceCode_${patientEventRandom}' value='"+patientCodeList[i]+"'>&nbsp;&nbsp;"+patientCodeList[i]+"<br />";
						html += 
							"<div class='checkbox'><label>"
							+"<input class='ace' type='checkbox' name='referenceCode_${patientEventRandom}' value='"+patientCodeList[i]+"'>"
							+"<span class='lbl'>"+patientCodeList[i]+"</span>"
							+"</label></div>";
					}else {
						$("#sizeListA_${patientEventRandom}").show();
						$("#sizeListMore_${patientEventRandom}").hide();
						//修改样式
						//$("#sizeListMore_${patientEventRandom}").append("<input type='checkbox' name='referenceCode_${patientEventRandom}' value='"+patientCodeList[i]+"'>&nbsp;&nbsp;"+patientCodeList[i]+"<br />");
						$("#sizeListMore_${patientEventRandom}").append(
								"<div class='checkbox'><label>"
								+"<input class='ace' type='checkbox' name='referenceCode_${patientEventRandom}' value='"+patientCodeList[i]+"'>"
								+"<span class='lbl'>"+patientCodeList[i]+"</span>"
								+"</label></div>");
					}
				}
				$("#patientCodeList_${patientEventRandom}").html(html);
				 $('input:checkbox[name=referenceCode_${patientEventRandom}]').change(function() {
			    	 delay_till_last('id', function() {//注意 id 是唯一的
			   		  getPatientEventData_${patientEventRandom}();
			   	    }, 1500);
			     });    
			}
	        // flag为true 表示切换了获取数据的数量  右边的 response 列表更新
	        if($.trim(assessmentResponseListContent)=="" || flag) {
	            $("#assessmentResponseList_${patientEventRandom}").html("");

	            var html = "";
	            for(var i = 0;i < assessmentResponseList.length; i++) {
	                var assessmentResponseArr = assessmentResponseList[i].split(",");
	              	//修改样式
	                html += "<div class='checkbox'><label>"
	                html += "<input class='ace' type='checkbox' name='assessmentResponse_${patientEventRandom}' value='"+assessmentResponseArr[0]+"'>";
	                html += "<span class='lbl'><i class='iconColor' style='background-color: " + assessmentResponseArr[1] + ";'></i>";
	                if(assessmentResponseArr[0].length > 15){
	                    html+="<span title='" + assessmentResponseArr[0] + "'>" + assessmentResponseArr[0].substring(0, 15) + "...</span><br>";
	                }else{
	                    html+="<span title='" + assessmentResponseArr[0] + "'>" + assessmentResponseArr[0] + "</span><br>";
	                }
	                html+="</label></div>";
	            }

	            $("#assessmentResponseList_${patientEventRandom}").html(html);
	            $('input:checkbox[name=assessmentResponse_${patientEventRandom}]').change(function() {
	                delay_till_last('id', function() {//注意 id 是唯一的
	                    getPatientEventData_${patientEventRandom}();
	                }, 1500);
	            });
	        }
	        // flag为true 表示切换了获取数据的数量  右边的 event 列表更新
	        if($.trim(eventListContent)=="" || flag) {
	            $("#eventList_${patientEventRandom}").html("");

	            var html = "";
	            for(var i = 0;i < eventList.length; i++) {
	            	//修改样式
	                html += "<div class='checkbox'><label>"
	                //html += "<input class='ace' type='checkbox' name='eventType_${patientEventRandom}' value='"+eventList[i]+"'>&nbsp;&nbsp;";
	                html += "<input class='ace' type='checkbox' name='eventType_${patientEventRandom}' value='"+eventList[i]+"'>";
	                if(eventList[i]=="Enroll"){
	                    //html += "<i class='icon iconBy_triRight'></i>Enroll<br>";
	                	html += "<span class='lbl'><i class='icon iconBy_triRight'></i>Enroll</span>";
	                }else if(eventList[i]=="Ongoing"){
	                    //html += "<i class='icon iconBy_circle'></i>Ongoing<br>";
	                	html += "<span class='lbl'><i class='icon iconBy_circle'></i>Ongoing</span>";
	                }else if(eventList[i]=="Withdrawal/Complation"){
	                    //html += "<i class='icon iconBy_cross' ></i><span title='Withdrawal/Complation'>Withdrawal/...</span><br>";
	                	html += "<span class='lbl' title='Withdrawal/Complation'><i class='icon iconBy_cross' ></i>Withdrawal/...</span>";
	                }else if(eventList[i]=="Death"){
	                    //html += "<i class='icon iconBy_triLeft'></i>Death<br>";
	                	html += "<span class='lbl'><i class='icon iconBy_triLeft'></i>Death</span>";
	                }else if(eventList[i]=="other") {
	                    //html += "<i class='icon iconBy_heart'></i>Other<br>";
	                	html += "<span class='lbl'><i class='icon iconBy_heart'></i>Other</span>";
	                }
	                html+="</label></div>";
	            }

	            $("#eventList_${patientEventRandom}").html(html);
	            $('input:checkbox[name=eventType_${patientEventRandom}]').change(function() {
	                delay_till_last('id', function() {//注意 id 是唯一的
	                    getPatientEventData_${patientEventRandom}();
	                }, 1500);
	            });
	        }
			$("input:checkbox[name=referenceCode_${patientEventRandom}]").attr("checked",true);
			$("input:checkbox[name=assessmentResponse_${patientEventRandom}]").attr("checked",true);
			$("input:checkbox[name=eventType_${patientEventRandom}]").attr("checked",true);
			option = {
					    title: {
					        text: 'Event View',
							left:'center'
					    }, 
					    grid : {
							left : '3%',
							right : '20%',
							bottom : '5px',
							top : '50px',
							containLabel : true
						},
						tooltip : {
						        formatter: function (obj) {
						            	return '<spring:message code="patientEvent.filter.eventtype"/> : '+ obj.data[2] + ' <br/> <spring:message code="patientEvent.filter.assessmentofresponse" /> : ' +  obj.data[3]+'</div>';
						        }
						    },
					    xAxis: {
					        type: 'value',
					        name : "<spring:message code='patientEvent.bar.x'/>",
					        min : 1,
					        max : max,
					        splitNumber : (data.x.length==0 || max == 2)?1:5,
							splitLine: {
					            show: false
					        }
					    },
					    yAxis: {
							type: 'category',
					        data: y,
					        name : "<spring:message code='patientEvent.bar.yAxis'/>",
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
								rotate :0,
								margin: 20
							}
					    },
						//color:['#FD026C', '#6B9494', '#A5D22D', '#4682B8', '#F5CC0A', '#B97C46', '#61a0a8', '#bda29a', '#44525d', '#c4ccd3'],
					    series:data.x
					};
			$.each(option.series,function(){
				var objMarkLine=this.markLine;
				if(objMarkLine!=undefined){
					var label={
						normal:{
							show:false
						}
					}
					objMarkLine.label=label;
				}
			});
			$("#patientCenter_${patientEventRandom}").hide();
	    }
    });  
	  
	//初始化图表配置项
	myChart_eventView.setOption(option);
    myChart_eventView.on("click", function(param){
        var patientCode = param.seriesName.split("-")[0];
        window.open("http://dev-rwe.rwebox.com/rwe-web/patientview.page?studyCode=" + $("#studyCode").val() + "&patientCode=" + patientCode + "&auth=123&random=123");
    });
}
	 
</script> 
<input type="hidden" id="studyCode" value="${studyCode}">
<input type="hidden" id="cohorts" value="${cohorts}">
<input type="hidden" id="other" value="${other}">

