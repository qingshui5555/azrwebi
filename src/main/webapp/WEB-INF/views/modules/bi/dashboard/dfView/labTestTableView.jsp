<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="/WEB-INF/views/include/bitaglib.jsp"%>
<script src="${ctxStatic }/echarts/${style }.js"></script>
<script src='${ctxStatic }/assets/js/typeahead-bs2.min.js'></script>
<script type="text/javascript">window.jQuery || document.write("<script src='${ctxStatic }/assets/js/jquery-2.0.3.min.js'>"+"<"+"/script>");</script>
<link href="${pageContext.request.contextPath}/static/assets/css/base.css" rel="stylesheet" type="text/css" />
<c:set value="<%=(int)(Math.random()*1000) %>" var="lab_test_random" />

<c:if test="${labTestIndicatorMap != null}">
	<div class="lab_test_table_div" id="lab_test_table_div_${lab_test_random}">
	<div class="row">
		<div class="col-xs-12">
			<div class="table-responsive">
				<table id="lab_test_table" class="table table-striped table-bordered table-hover">
					<thead>
					<tr style="font-size: 20px;">
						<th style="text-align: center" bgcolor="#a9a9a9"><spring:message code="lab.test.table.column.type"/></th>
						<th style="text-align: center" bgcolor="#a9a9a9"><spring:message code="lab.test.table.column.indicator"/></th>
						<th style="text-align: center" bgcolor="#a9a9a9"><spring:message code="lab.test.table.column.popt"/></th>
						<th style="text-align: center" bgcolor="#a9a9a9"><spring:message code="lab.test.table.column.ponr"/></th>
						<th style="text-align: center" bgcolor="#a9a9a9"><spring:message code="lab.test.table.column.ac"/></th>
						<th style="text-align: center" bgcolor="#a9a9a9"><spring:message code="lab.test.table.column.ar"/></th>
					</tr>
					</thead>
					<tbody>
					<c:forEach items="${labTestTableList}" var="data" varStatus="vs">
						<tr style="text-align: center;" id="${data.indicator}">
							<td>${data.type}</td>
							<td>${data.indicator}</td>
							<td><div class="progress progress-striped">
								<c:choose>
									<c:when test="${data.popt < 50.0}">
										<div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: ${data.popt}%;">${data.popt}%</div>
									</c:when>
									<c:when test="${data.popt > 80.0}">
										<div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: ${data.popt}%;">${data.popt}%</div>
									</c:when>
									<c:otherwise>
										<div class="progress-bar progress-bar-warning" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: ${data.popt}%;">${data.popt}%</div>
									</c:otherwise>
								</c:choose>
							</div></td>
							<td><div class="progress progress-striped">
								<c:choose>
									<c:when test="${data.ponr < 50.0}">
										<div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: ${data.ponr}%;">${data.ponr}%</div>
									</c:when>
									<c:when test="${data.ponr > 80.0}">
										<div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: ${data.ponr}%;">${data.ponr}%</div>
									</c:when>
									<c:otherwise>
										<div class="progress-bar progress-bar-warning" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: ${data.ponr}%;">${data.ponr}%</div>
									</c:otherwise>
								</c:choose>
							</div></td>
							<td>${data.ac}</td>
							<td>${data.ar}</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	</div>

	<br />
	<script>
        var filter_widget_main_str_${lab_test_random} =
            '<div class="row">'
            +'	<div class="col-xs-12">'
            +'		<h5 class="header smaller lighter blue no-margin">'
            +'			<spring:message code="lab.test.filter.title" />'
            +'			:'
            +'		</h5>'
            +'		<div class="orderByGroup" style="font-size:14px">'
            <c:forEach items="${labTestIndicatorMap}" var="data" varStatus="status">
            +'				<div class="checkbox"><label>'
            +'					<input class="ace" type="checkbox" name="parameter_${lab_test_random}" value="${data.key}" style="width: 1px; height: 1px;">'
            +'					<span class="lbl">${data.value}</span>'
            +'				</label></div>'
            </c:forEach>
            +'		</div>'
            +'	</div>'
            +'</div>';
	</script>
</c:if>

<script type="text/javascript">

    var $filter_widget_main_${lab_test_random};

    $(function() {
        $filter_widget_main_${lab_test_random} = $("#lab_test_table_div_${lab_test_random}").closest(".widget-box").find(".widget-main:first");
        try{
            $filter_widget_main_${lab_test_random}.html(filter_widget_main_str_${lab_test_random});
            $filter_widget_main_${lab_test_random}.closest('ul.dropdown-menu')
                .on('click', '[data-stopPropagation]', function (e) {
                    e.stopPropagation();
                });
        } catch(e) {
            $("#lab_test_table_div_${lab_test_random}").closest(".widget-box").find(".dropdown-toggle").hide();
        }

        $("input:checkbox[name=parameter_${lab_test_random}]").attr("checked",true);

        $('input:checkbox[name=parameter_${lab_test_random}]').click(function() {
            var indicator = $(this).attr("value");
            indicator = indicator.replace(/[ ]/g,"");
            if($(this).is(':checked')){
                document.getElementById(indicator).style = "text-align: center";
            } else {
                document.getElementById(indicator).style.display = "none";
            }

            if($('input:checkbox[name=parameter_${lab_test_random}]:checked').length=='1'){
                $('input:checkbox[name=parameter_${lab_test_random}]:checked').attr("disabled","disabled");
            } else {
                $('input:checkbox[name=parameter_${lab_test_random}]:disabled').removeAttr("disabled");
            }
        });
    });

    function hide_more_${lab_test_random}(e){
        $(e).next().show();
        e.style.display='none';
    }

</script>