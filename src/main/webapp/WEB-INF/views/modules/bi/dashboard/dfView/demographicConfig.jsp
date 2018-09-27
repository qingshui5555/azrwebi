<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" buffer="1024kb"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>
<form id="configForm">
	<div class="row">
		<div class="col-xs-6">
			<label for="form-field-select-1" class="inline"><spring:message code="etl.title.indicator" />:</label>
		</div>
	</div>
	<br>
	<table id="indicator" class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<th><spring:message code="etl.table.th.choose" /></th>
				<th><spring:message code="etl.table.th.order" /></th>
				<th><spring:message code="etl.table.th.type" /></th>
				<th><spring:message code="etl.table.th.indicator" /></th>
				<th><spring:message code="etl.table.th.description" /></th>
				<th><spring:message code="etl.table.th.unit" /></th>
				<th><spring:message code="etl.table.th.upper" /></th>
				<th><spring:message code="etl.table.th.lower" /></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${demographicConfigList}" var="config" varStatus="status">
				<tr>
					<td><input type="checkbox" name="chooseDemographic"  value="demographic_${config.configId}"></td>
					<td><input type="text"  name="order" onkeyup="value=value.replace(/[^\d.]/g,'')" value="${config.configId}" style="width:80px"></td>
					<td>${config.subject}</td>
					<td>${config.keyName}</td>
					<td>${config.description}</td>
					<td>
						<select style="width:80px">
							<c:forEach items="${config.unitSet}" var="val" >
								<option value="${val}">${val}</option>
							</c:forEach>
						</select>
					</td>
					<td><input type="text"  name="lowerRef" onkeyup="value=value.replace(/[^\d.]/g,'')" value="${config.lowerRef}" style="width:80px"></td>
					<td><input type="text"  name="upperRef" onkeyup="value=value.replace(/[^\d.]/g,'')" value="${config.upperRef}" style="width:80px"></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</form>

<script type="text/javascript">
    var indicatorArr = new Array();
    var formArr = new Array();
    var arr = new Array();
    var Demographic = {};

    $(function() {
        $("#viewChartHeight").val("${viewChartHeight}");
		debugger;
        <c:if test="${configVoList!=null}">
			<c:forEach var="var" items="${configVoList}" varStatus="status">
				var id = '${var.id}';
        		var order = '${var.order}';
        		var indicator = '${var.indicator}';
				var resultUnit = '${var.resultUnit}';
				var lowerRef = '${var.lowerRef}';
				var upperRef = '${var.upperRef}';
				$("#indicator :checkbox[value='" + id + "']").attr("checked", true);
        		$("#indicator :checkbox[value='" + id + "']").parent().next().find("input").val(order);
				$("#indicator :checkbox[value='" + id + "']").parent().next().next().next().next().find("select").val(resultUnit);
				$("#indicator :checkbox[value='" + id + "']").parent().next().next().next().next().next().find("input").val(lowerRef);
				$("#indicator :checkbox[value='" + id + "']").parent().next().next().next().next().next().next().find("input").val(upperRef);
			</c:forEach>
        	$('input:checkbox[name=chooseDemographic]:checked').each(
            function(i) {
                id = $(this).val();
                order = $(this).parent().next().find("input").val();
                type = $(this).parent().next().next().text();
                indicator = $(this).parent().next().next().next().text();
                description = $(this).parent().next().next().next().next().text();
                resultUnit = $(this).parent().next().next().next().next().next().find("select").val();
                lowerRef = $(this).parent().next().next().next().next().next().next().find("input").val();
                upperRef = $(this).parent().next().next().next().next().next().next().next().find("input").val();
                value = id + "&" + order + "&" + type + "&" + indicator + "&" + description + "&" + resultUnit + "&" + lowerRef + "&" + upperRef;
                indicatorArr.push(value);
            });
        </c:if>

        $('#indicator').dataTable({
            "aoColumnDefs": [
                { "sWidth": "20%", "aTargets": [ 0 ] },
                { "sWidth": "20%", "aTargets": [ 2 ] }
            ],
            "oLanguage" : dataTableLanguage
        });

        $("#indicator").on('click', ':checkbox', function() {
            id = $(this).val();
            order = $(this).parent().next().find("input").val();
            type = $(this).parent().next().next().text();
            indicator = $(this).parent().next().next().next().text();
            description = $(this).parent().next().next().next().next().text();
            resultUnit = $(this).parent().next().next().next().next().next().find("select").val();
            lowerRef = $(this).parent().next().next().next().next().next().next().find("input").val();
            upperRef = $(this).parent().next().next().next().next().next().next().next().find("input").val();
            value = id + "&" + order + "&" + type + "&" + indicator + "&" + description + "&" + resultUnit + "&" + lowerRef + "&" + upperRef;
            if ($(this).is(':checked')) {
                indicatorArr.push(value);
            } else {
                for ( var i in indicatorArr) {
                    if (indicatorArr[i] == value) {
                        indicatorArr.splice(i, 1);
                        break;
                    }
                }
            }
        })

        //文本框
        $('#indicator').on('keyup',':text', function () {
            id = $(this).parent().parent().find("td:first-child").find("input").val();
            if($("#indicator :checkbox[value='" + id + "']").is(':checked')) {
                for ( var i in indicatorArr) {
                    var arrRow = indicatorArr[i].split("&");
                    tempId = arrRow[0];
                    if(tempId == id ){
                        order = $(this).parent().parent().find("td:first-child").next().find("input").val();
                        type = $(this).parent().parent().find("td:first-child").next().next().text();
                        indicator = $(this).parent().parent().find("td:first-child").next().next().next().text();
                        description = $(this).parent().parent().find("td:first-child").next().next().next().next().text();
                        resultUnit = $(this).parent().parent().find("td:first-child").next().next().next().next().next().find("select").val();
                        lowerRef = $(this).parent().parent().find("td:first-child").next().next().next().next().next().next().find("input").val();
                        upperRef = $(this).parent().parent().find("td:first-child").next().next().next().next().next().next().next().find("input").val();
                        value = id + "&" + order + "&" + type + "&" + indicator + "&" + description + "&" + resultUnit + "&" + lowerRef + "&" + upperRef;
                        indicatorArr[i] = value;
                    }
                }
            }
        });

        //下拉框
        $('#indicator').on('change','td select',function () {
            id = $(this).parent().parent().find("td:first-child").find("input").val();
            if($("#indicator :checkbox[value='" + id + "']").is(':checked')) {
                for ( var i in indicatorArr) {
                    var arrRow = indicatorArr[i].split("&");
                    tempId = arrRow[0];
                    if(tempId==id){
                        order = $(this).parent().parent().find("td:first-child").next().find("input").val();
                        type = $(this).parent().parent().find("td:first-child").next().next().text();
                        indicator = $(this).parent().parent().find("td:first-child").next().next().next().text();
                        description = $(this).parent().parent().find("td:first-child").next().next().next().next().text();
                        resultUnit = $(this).parent().parent().find("td:first-child").next().next().next().next().next().find("select").val();
                        lowerRef = $(this).parent().parent().find("td:first-child").next().next().next().next().next().next().find("input").val();
                        upperRef = $(this).parent().parent().find("td:first-child").next().next().next().next().next().next().next().find("input").val();
                        value = id + "&" + order + "&" + type + "&" + indicator + "&" + description + "&" + resultUnit + "&" + lowerRef + "&" + upperRef;
                        indicatorArr[i] = value;
                    }
                }
            }
        });
    })

    function submitConfig() {
        //单位
        var unit = {};

        var height = $("#viewChartHeight").val();
        if($.trim(height)=='') {
            $.simplyToast('<spring:message code="labtest.alert.height"/>', 'info');
            return;
        }
        //chart 高度
        var heightData = {};
        heightData["height"] = height;
        console.log(indicatorArr);

        //循环已经勾选的内容数组
        for ( var i in indicatorArr) {
            var arrRow = indicatorArr[i].split("&");
            id = arrRow[0];
            order = arrRow[1];
            type = arrRow[2];
            indicator = arrRow[3];
            description = arrRow[4];
            resultUnit = arrRow[5];
            lowerRef = arrRow[6];
            upperRef = arrRow[7];

			var idArr = id.split("_");
            var demographicData = {};
            demographicData["order"] = order;
            demographicData["id"] = idArr[0] + '_' + order;
            demographicData["subject"] = type;
            demographicData["indicator"] = indicator;
            demographicData["description"] = description;
            demographicData["resultUnit"] = (resultUnit==null || resultUnit == 'null') ? "" : resultUnit;
            demographicData["lowerRef"] = lowerRef;
            demographicData["upperRef"] = upperRef;
			arr.push(demographicData);
        }

        Demographic["demographicConfigList"] = arr;
        formArr.push(unit);
        formArr.push(Demographic);
        $.post("${ctx}/bi/sys/studyManagment/configTabView", {
            tabViewId : openModalTabViewId,
            alias : $("#alias").val(),
            viewChartHeight : $("#viewChartHeight").val(),
            configJson : JSON.stringify(formArr).replace(/\"/g, "'")
        }, function() {
            $("#" + openModalTabViewId + "_td").html($("#alias").val());
            $.simplyToast('<spring:message code="viewList.success"/>', 'info');
            $("#configModal").modal('hide');
            location.reload();
        })

    }
</script>
