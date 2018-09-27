<%@page import="com.movit.rwe.modules.bi.base.entity.enums.ViewTypeEnum"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" buffer="1024kb"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="layout.head.analytics" /></title>
	<c:import url="/bi/layout/resource" />
<style type="text/css">
.highlight {
	background-color: #FDECA8 !important;
}
</style>
</head>
<body>
	<div class="main-container" id="main-container">
		<script type="text/javascript">
            try {
                ace.settings.check('main-container', 'fixed')
            } catch (e) {
            }
		</script>
		<div class="main-container-inner">
			<a class="menu-toggler" id="menu-toggler" href="#"> <span class="menu-text"></span></a>
			<c:import url="/bi/layout/sidebar">
				<c:param name="dataMenuActive" value="acd9325b2dea40c2b3b5505a2d842a64" />
			</c:import>
			<div class="main-content">
				<div class="breadcrumbs" id="breadcrumbs">
					<ul class="breadcrumb">
						<li><i class="icon-home home-icon"></i><a href="#"><spring:message code="layout.menu.name.system.configuration" /></a></li>
						<li class="active"><spring:message code="layout.menu.name.etl.config"/></li>
					</ul>
				</div>

				<div class="page-content">
					<div class="row">
						<div class="col-xs-12">
							<table id="etl_result_config_table" class="table table-striped table-bordered table-hover">
								<thead id="etl_result_config_thead">
								<th><spring:message code="etl.result.config.id"/></th>
								<th><spring:message code="etl.result.config.subject"/></th>
								<th><spring:message code="etl.result.config.study.id"/></th>
								<th><spring:message code="etl.result.config.name"/></th>
								<th><spring:message code="etl.result.config.depend"/></th>
								<th><spring:message code="etl.result.config.value"/></th>
								<th><spring:message code="etl.result.config.create.on"/></th>
								<th><spring:message code="etl.result.config.version"/></th>
								<th><spring:message code="etl.result.config.operation"/></th>
								</thead>
								<tbody id="etl_result_config_tbody">
								<c:forEach items="${etlResultConfigDataList}" var="data" varStatus="vs">
									<tr>
										<td data-key='<spring:message code="etl.result.config.id"/>' data-value='${data.id}'>${data.id}</td>
										<td data-key='<spring:message code="etl.result.config.subject"/>' data-value='${data.subject}'>${data.subject}</td>
										<td data-key='<spring:message code="etl.result.config.study.id"/>' data-value='${data.studyId}'>${data.studyId}</td>
										<td data-key='<spring:message code="etl.result.config.name"/>' data-value='${data.keyName}'>${data.keyName}</td>
										<td data-key='<spring:message code="etl.result.config.depend"/>' data-value='${data.keyDepend}'>${data.keyDepend}</td>
										<td data-key='<spring:message code="etl.result.config.value"/>' data-value='${data.keyValue}'>${data.keyValue}</td>
										<td data-key='<spring:message code="etl.result.config.create.on"/>' data-value='<fmt:formatDate value="${data.createOn }" pattern="yyyy-MM-dd" />'><fmt:formatDate value="${data.createOn }" pattern="yyyy-MM-dd" /></td>
										<td data-key='<spring:message code="etl.result.config.version"/>' data-value='${data.version}'>${data.version}</td>
										<td>
											<a class="green btn-xs" style="cursor: pointer;" onclick="editRow(${data.id}, this)"><i class="icon-pencil bigger-130"></i></a>
											<a class="red btn-xs" style="cursor: pointer;" onclick="removeRow(${data.id}, this)"><i class="icon-trash bigger-130"></i></a>
										</td>
									</tr>
								</c:forEach>
								</tbody>
							</table>
						</div>
					</div>

					<div class="row">
						<div class="row col-xs-12" id="etl_result_template_div">
							<div style="padding-top: 30px;">
								<div class="col-xs-3">
									<spring:message code="etl.config.templateDownload" />:
								</div>
								<div class="col-xs-9">
									<a style="cursor: pointer;" onclick="template_download();">Template.xlsx</a>
								</div>
							</div>
							<div style="padding-top: 30px;">
								<div class="col-xs-3">
									<spring:message code="etl.config.upload" />:
								</div>
								<div class="col-xs-8">
									<form action="${ctxbi}/etl/config/import" method="post" enctype="multipart/form-data" id="import_excel_form">
										<input type="file" id="excel_file_input" name="excel_file" value="1">
									</form>
								</div>
								<div class="col-xs-1">
									<button class="btn btn-purple btn-sm" type="submit" form="import_excel_form">
										<i class="icon-ok smaller-10"></i>
										<spring:message code="etl.config.submit" />
									</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>



<script>

	$(function() {
		$('#excel_file_input').ace_file_input({
			no_file : '<spring:message code="etl.config.noFile"/> ...',
			btn_choose : '<spring:message code="etl.config.choose"/>',
			btn_change : '<spring:message code="etl.config.change"/>',
			droppable : false,
			onchange : null,
			thumbnail : false
		});

        var oTable1 = $('#etl_result_config_table').dataTable({
            "aoColumns" : [ null, null, null, {
                "bSortable" : false
            }, {
                "bSortable" : false
            }, {
                "bSortable" : false
            }, null, null, {
                "bSortable" : false
            } ],
            "oLanguage": dataTableLanguage
        });
	})

	function template_download() {
		window.open('${ctxStatic }/etlconfig/etl_result_config.xlsx');
	}

	function removeRow(id, e){
		bootbox.confirm({
			buttons : {
				confirm : {
					label : '<spring:message code="layout.menu.name.drop.ok"/>',
					className: 'btn-purple'
				},
				cancel : {
					label : '<spring:message code="layout.menu.name.drop.cancel"/>'
				}
			},
			message : '<spring:message code="da.wslist.confirmdeletion"/>',
			callback : function(result) {
				if (result) {
					$.post("${ctxbi}/etl/config/row/remove", {
						'id' : id
					}, function(data) {
						if (data.success) {
							$(e).parent().parent().remove();
						}
					})
				}
			}
		});
	}

	function editRow(id, e) {
		$(e).parent().parent().find("td:not(:first)").attr("contenteditable", true);
		$(e).parent().parent().find("td:last").attr("contenteditable", false);
		$(e).parent().parent().find("td").addClass("highlight");
		$(e).after('<a class="btn-xs" style="cursor: pointer;" onclick="updateRow(' + id + ',this)"><i class="icon-ok bigger-130"></i></a>' + '<a class="red" style="cursor: pointer;" onclick="cancelRow(' + id + ',this)"><i class="icon-remove bigger-130"></i></a>');
		$(e).hide();
	}

	function updateRow(id, e) {
		var jsonData = {};
		$.each($(e).parent().parent().find("td"), function(i, n) {
			var key = $(n).attr("data-key");
			if (key != undefined) {
				var htmlValue = $(n).html();
				jsonData[key] = htmlValue.replace(/<br>/g, '');
			}
		})

		$.post('${ctxbi}/etl/config/row/update', {
			'jsonData' : JSON.stringify(jsonData).replace(/\"/g, "'")
		}, function(data) {
			if (data.success) {
				$(e).prev().show();
				$(e).hide();
				$(e).next().hide();
				$(e).parent().parent().find("td:not(:first)").attr("contenteditable", false);
				$(e).parent().parent().find("td").removeClass("highlight");
				$.each($(e).parent().parent().find("td"), function(i, n) {
					$(n).attr("data-value", $(n).html());
				})
			}
		})
	}

	function cancelRow(id, e) {
		$(e).hide();
		$(e).prev().hide();
		$(e).prev().prev().show();
		$(e).parent().parent().find("td:not(:first)").attr("contenteditable", false);
		$(e).parent().parent().find("td").removeClass("highlight");
		$.each($(e).parent().parent().find("td"), function(i, n) {
			var value = $(n).attr("data-value");
			$(n).html(value);
		})
	}

</script>
</html>