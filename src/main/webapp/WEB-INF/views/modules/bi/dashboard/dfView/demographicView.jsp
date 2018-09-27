<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" buffer="1024kb"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>
<script src="${ctxStatic }/echarts/${style }.js"></script>
<c:set value="<%=(int) (Math.random() * 1000)%>" var="demographicRandom" />

<center id="${alias}NoData_${demographicRandom}">
	<i class='icon-spinner icon-spin blue bigger-250'></i>
</center>

<div id="${alias}BarDiv_${demographicRandom}">
	<c:forEach items="${demographicDataMap}" var="data" varStatus="vs">
		<div id="${fn:replace(data.key, " ", "")}_bar_${demographicRandom}" style="height: 85px;">${data.key}</div>
		<script>
            var ${alias}_bar_${demographicRandom}_${vs.index}_chart = echarts.init(document.getElementById('${fn:replace(data.key, " ", "")}_bar_${demographicRandom}'),'myStyle');
            var ${alias}_bar_${demographicRandom}_${vs.index}_option = {
                <c:if test="${fn:length(data.value)==0}">
                title:{
                    text:'<spring:message code="dashboard.data.empty"/>',
                    textStyle:{
                        fontWeight:'normal',
                        fontSize:'12'
                    },
                    top: '35%',
                    left:'center'
                },
                </c:if>
                tooltip : {
                    show:<c:if test="${isPad eq '1'}">false</c:if><c:if test="${isPad ne '1'}">true</c:if>,
                    formatter:' {b}</br>{a}:{c}%'
                },
                legend: {
                    left:'150px',
					right:'100px',
                    data:[
                        <c:forEach var="map" items="${data.value}" varStatus="status">
							<c:if test="${map.value>0}">
							'${map.key}'
							<c:if test="${!status.end}">,</c:if>
							</c:if>
                        </c:forEach>
                    ],
					type:'scroll'
                },
                grid:{
                    left:'150px'
                },
                calculable : true,
                xAxis : [
                    {
                        type : 'value',
                        show : false,
                        min :0,
                        max :100
                    }
                ],
                yAxis : [
                    {
                        axisLine : {    // 轴线
                            show: false
                        },
                        axisTick :{
                            show : false
                        },
                        type : 'category',
                        data : ['${demographicDataUnitMap[data.key]}'.length>20?'${demographicDataUnitMap[data.key]}'.substring(0,'${demographicDataUnitMap[data.key]}'.lastIndexOf(' '))+'\n'+'${demographicDataUnitMap[data.key]}'.substring('${demographicDataUnitMap[data.key]}'.lastIndexOf(' '),'${demographicDataUnitMap[data.key]}'.length):'${demographicDataUnitMap[data.key]}'],
                        splitLine: {
                            show: false
                        }
                    }
                ],
                series : [
                    <c:forEach var="map" items="${data.value}" varStatus="status">
                    	{
							name:'${map.key}',
							type:'bar',
							stack: '总量',
							itemStyle : {
							    normal: {
							        label : {
							            show:
											<c:if test="${map.value/demographicDataSizeMap[data.key] > 0.0501}">true</c:if>
											<c:if test="${map.value/demographicDataSizeMap[data.key] < 0.0501}">false</c:if>
                                    		, position: 'insideRight',formatter:'{c} %'}
                                    		<c:if test="${map.key eq 'Missing'}">, color : 'gray'</c:if>
							    }},
                        	data:[<fmt:formatNumber value="${map.value/demographicDataSizeMap[data.key] * 100}" pattern="##.#" minFractionDigits="1" />]
                    	}
                    	<c:if test="${!status.end}">,</c:if>
                    </c:forEach>
                ]
            };

            ${alias}_bar_${demographicRandom}_${vs.index}_chart.setOption(${alias}_bar_${demographicRandom}_${vs.index}_option);
		</script>
	</c:forEach>
</div>

<div id="${alias}PieDiv_${demographicRandom}">
	<c:forEach items="${demographicDataMap}" var="data" varStatus="vs">
		<div class="row" style="padding-bottom: 80px">
			<div id="${fn:replace(data.key, " ", "")}_pie_${demographicRandom}"
				 class="col-xs-6"
				 style="height: ${empty data.value?85:360}px;"></div>
			<c:if test="${!empty data.value }">
				<div class="table-responsive col-xs-3"  style="top:<c:if test="${isPad eq 1}">110px</c:if><c:if test="${isPad ne 1}">80px</c:if>;">
					<table class="table table-striped table-bordered table-hover dataTable">
						<thead>
						<tr style="background-color: #f2f4f8">
							<th><spring:message code="${data.key}" text="${data.key}"/></th>
							<th><spring:message code="demographic.title.count" /></th>
						</tr>
						</thead>
						<tbody>
						<c:forEach var="map" items="${data.value}" varStatus="status">
							<c:if test="${map.value>0}">
								<tr>
									<td>${map.key }</td>
									<td>${map.value}</td>
								</tr>
							</c:if>
						</c:forEach>
						</tbody>
					</table>
				</div>
			</c:if>
		</div>
		<script>
			var ${alias}_pie_${demographicRandom}_${vs.index}_chart = echarts.init(document.getElementById('${fn:replace(data.key, " ", "")}_pie_${demographicRandom}'),'myStyle');

            var ${alias}_pie_${demographicRandom}_${vs.index}_option = {
                title : {
                    text:'${data.key}<c:if test="${fn:length(data.value)==0}">(<spring:message code="dashboard.data.empty"/>)</c:if>',
                    right:'0%'
                },
                tooltip : {
                    show:<c:if test="${isPad eq '1'}">false</c:if><c:if test="${isPad ne '1'}">true</c:if>,
                },
                legend: {
                    top:'10%',
                    left:'right',
                    data:[
                        <c:forEach var="map" items="${data.value}" varStatus="status">
								<c:if test="${map.value>0}">
                        		'${map.key}'
                        		<c:if test="${!status.end}">,</c:if>
                        		</c:if>
                        </c:forEach>
                    ]
                },
                series : [
                    {
                        name: ['${data.key}'.length>10?'${data.key}'.substring(0,10)+'...':'${data.key}'],
                        type:'pie',
                        radius : <c:if test="${isPad eq '1'}">'100'</c:if><c:if test="${isPad ne '1'}">'130'</c:if>,
                        center: ['62%', '62%'],
                        label:{
                            normal:{
                                position:'outside',
                                formatter:'{b}:{d}%'
                            }
                        },
                        labelLine: {
                            normal: {
                                show: true,
                                length: 3,
                                length2: 3
                            }
                        },
                        data:[
                            <c:forEach var="map" items="${data.value}" varStatus="status">
                                    <c:if test="${map.value>0}">
                            		    {value:'${map.value}',name:'${map.key}'}<c:if test="${!status.end}">,</c:if>
                                    </c:if>
                            </c:forEach>
                        ]
                    }
                ]
            };
            //使用刚指定的配置项和数据显示图表。
            ${alias}_pie_${demographicRandom}_${vs.index}_chart.setOption(${alias}_pie_${demographicRandom}_${vs.index}_option);

		</script>
	</c:forEach>
</div>

<script>
	var filterWidgetMainStr_${demographicRandom} =
        '<div class="row">'
        +'	<div class="col-xs-12">'
        +'		<h5 class="header smaller lighter blue no-margin">'
        +'			<spring:message code="demographic.filter.title.chart"/>'
        +'			:'
        +'		</h5>'
        +'		<div class="orderByGroup">'
        +'			<select onchange="${alias}ChangeChartType(this)">'
        +'				<option title="<spring:message code="demographic.chart.bar"/>" value="Bar"><spring:message code="demographic.chart.bar"/></option>'
        +'				<option title="<spring:message code="demographic.chart.pie"/>" value="Pie"><spring:message code="demographic.chart.pie"/></option>'
        +'			</select>'
        +'		</div>'
        +'	</div>'
        +'</div>'
        +'<div class="row">'
        +'	<div class="col-xs-12">'
        +'		<h5 class="header smaller lighter blue no-margin">'
        +'			<spring:message code="demographic.filter.title.parameter"/>'
        +'			:'
        +'		</h5>'
        +'		<div class="orderByGroup">'
        <c:forEach var="data" items="${demographicIndicatorMap}" varStatus="vs">
        +'				<div class="checkbox"><label>'
        +'					<input class="ace" type="checkbox" name="parameter_${demographicRandom}" value="${data.key}" style="width: 1px; height: 1px;">'
        +'					<span class="lbl">${data.value.description}</span>'
        +'				</label></div>'
        </c:forEach>
        +'		</div>'
        +'	</div>'
        +'</div>';

    $(function(){
        $("#${alias}PieDiv_${demographicRandom}").css({"height":"0","overflow-y":"auto"});
        $filterWidgetMain_${demographicRandom} = $("#${alias}NoData_${demographicRandom}").closest(".widget-box").find(".widget-main:first");
        $filterWidgetMain_${demographicRandom}.html(filterWidgetMainStr_${demographicRandom});
        $filterWidgetMain_${demographicRandom}.closest('ul.dropdown-menu')
            .on('click', '[data-stopPropagation]', function (e) {
                e.stopPropagation();
            });

        $('input:checkbox[name=parameter_${demographicRandom}]').click(function() {
            var indicator = $(this).attr("value");
            indicator = indicator.replace(/[ ]/g,"");
            if($(this).is(':checked')){
                $("#"+indicator+"_bar_${demographicRandom}").show();
                $("#"+indicator+"_pie_${demographicRandom}").parent().show();
            } else {
                $("#"+indicator+"_bar_${demographicRandom}").hide();
                $("#"+indicator+"_pie_${demographicRandom}").parent().hide();
            }

            if($('input:checkbox[name=parameter_${demographicRandom}]:checked').length=='1'){
                $('input:checkbox[name=parameter_${demographicRandom}]:checked').attr("disabled","disabled");
            } else {
                $('input:checkbox[name=parameter_${demographicRandom}]:disabled').removeAttr("disabled");
            }
        });

        //默认选中所以的复选框
        $("input:checkbox[name=parameter_${demographicRandom}]").attr("checked",true);

        $("#${alias}NoData_${demographicRandom}").closest(".widget-box")
            .find(".dropdown-menu:first").find(".widget-box:first").css({height:"400px"});

        $("#${alias}NoData_${demographicRandom}").hide();

        $(".dataTable").dataTable({
            "bFilter": false,
            "bSort" : false,
            "aLengthMenu" : [10],
            "bLengthChange":false,
            "bInfo" : false,
            "oLanguage": dataTableLanguage
        });

        //隐藏表格多余头部，没有分页时分页部分隐藏
        $(".dataTables_wrapper").each(function(){
            //隐藏表格多余头部
            var $table = $(this);
            $table.find("div:first").hide();
            //调整分页部分样式
            $table.find(".row").last().find("div:first").remove();
            $table.find(".row").last().find("div:first").removeClass("col-sm-6").addClass("col-sm-12");
        });
    })

    function ${alias}ChangeChartType(e){
        if(e.value=='Pie'){
            $("#${alias}BarDiv_${demographicRandom}").css({"height":"0","overflow-y":"auto"});
            $("#${alias}PieDiv_${demographicRandom}").css({"height":"100%","overflow-y":"*"});
        }else{
            $("#${alias}PieDiv_${demographicRandom}").css({"height":"0","overflow-y":"auto"});
            $("#${alias}BarDiv_${demographicRandom}").css({"height":"100%","overflow-y":"*"});
        }
    }

</script>
