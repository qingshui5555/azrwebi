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
	<c:import url="/bi/layout/head" />
	<div class="main-container" id="main-container">
		<script type="text/javascript">
			try {
				ace.settings.check('main-container', 'fixed')
			} catch (e) {
			}
		</script>
		<div class="main-container-inner">
			<a class="menu-toggler" id="menu-toggler" href="#"> <span class="menu-text"></span>
			</a>
			<c:import url="/bi/layout/sidebar">
				<c:param name="dataMenuActive" value="29" />
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
							<div class="tabbable tabbable-tabdrop">
								<ul class="nav nav-tabs" id="tab_ul" style="margin-right:1px;">
									<c:forEach items="${etlTabList}" var="etlTab">
										<li data-name="${etlTab}"><a data-toggle="tab" style="cursor: pointer;"><i class="pink icon-dashboard bigger-110"></i> ${etlTab} </a></li>
									</c:forEach>
								</ul>
								<div class="tab-content" style="overflow: auto;" id="etl_tab_content">
									<div class="tab-pane in active">
										<table id="etlConfigDataTable" class="table table-striped table-bordered table-hover">
											<thead id="etlConfigThead"></thead>
											<tbody id="etlConfigTbody"></tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
						<div class="row col-xs-12" id="etlTemplateDiv" style="display: none;">
							<div style="padding-top: 30px;">
								<div class="col-xs-3">
									<spring:message code="etl.config.templateDownload" />:
								</div>
								<div class="col-xs-9">
									<a style="cursor: pointer;" onclick="templateDownload();">Template.xlsx</a>
								</div>
							</div>
							<div style="padding-top: 30px;">
								<div class="col-xs-3">
									<spring:message code="etl.config.upload" />:
								</div>
								<div class="col-xs-8">
									<form action="${ctxbi}/etlConfig/importExcel" method="post" enctype="multipart/form-data" id="importExcelForm">
										<input type="file" id="excelFileInput" name="excelFile" value="1">
									</form>
								</div>
								<div class="col-xs-1">
									<button class="btn btn-purple btn-sm" type="submit" form="importExcelForm">
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
	var etlConfigDataTable;

	$(function() {
		$('#excelFileInput').ace_file_input({
			no_file : '<spring:message code="etl.config.noFile"/> ...',
			btn_choose : '<spring:message code="etl.config.choose"/>',
			btn_change : '<spring:message code="etl.config.change"/>',
			droppable : false,
			onchange : null,
			thumbnail : false
		});

		$("#tab_ul li").click(function() {
			var tabName = $(this).attr("data-name");
			$("#tab_ul li").removeClass("active");
			$(this).addClass("active");

			$.post('${ctxbi}/etlConfig/loadEtlConfigData', {
				'tabName' : tabName
			}, function(data) {
				if (data.success) {
					$("#tableNameHidden").val(tabName);
					initEtlConfig(data.etlConfigDataList);
					$("#etlTemplateDiv").show();
				}
			})
		})

		$("#tab_ul li:first").click();
		
		$("#tab_ul").tabdrop();
	})

	function initEtlConfig(etlConfigDataList) {
		if (etlConfigDataTable != undefined) {
			etlConfigDataTable.fnDestroy();
		}
		$("#etlConfigThead").html("");
		$("#etlConfigTbody").html("");

		if (etlConfigDataList.length == 0) {
			return;
		}

		var etlConfigTheadTr = "<tr>";
		for ( var key in etlConfigDataList[0]) {
			etlConfigTheadTr += "<th>" + key + "</th>";
		}
		etlConfigTheadTr += "<th><spring:message code='etl.config.operation'/></th></tr>";
		$("#etlConfigThead").append($(etlConfigTheadTr));
		for ( var index in etlConfigDataList) {

			var rowId;
			var etlConfigTbodyTr = "<tr>";

			for ( var key in etlConfigDataList[index]) {

				var value = etlConfigDataList[index][key];

				if (key == "id") {
					rowId = value;
				}

				if (!isNaN(value) && value.toString().length > 12) {
					etlConfigTbodyTr += "<td data-key='"+key+"' data-value='"+value+"'>" + formatDate(new Date(value)) + "</td>";
				} else {
					etlConfigTbodyTr += "<td data-key='"+key+"' data-value='"+value+"'>" + value + "</td>";
				}

			}

			etlConfigTbodyTr += '<td>';
			etlConfigTbodyTr += '<a class="green btn-xs" style="cursor: pointer;" onclick="editRow(' + rowId + ',this)"><i class="icon-pencil bigger-130"></i></a>';
			etlConfigTbodyTr += '<a class="red btn-xs" style="cursor: pointer;" onclick="removeRow(' + rowId + ',this)"><i class="icon-trash bigger-130"></i></a>';
			etlConfigTbodyTr += '</td>';

			etlConfigTbodyTr += "</tr>";

			$("#etlConfigTbody").append(etlConfigTbodyTr);
		}

		initDataTable();
	}

	function initDataTable() {
        etlConfigDataTable = $("#etlConfigDataTable").dataTable({
            bSort : false,
            "oLanguage" : dataTableLanguage,
            "scrollX": true,
            "scrollCollapse": true
        });

        if(haveScrollBar()){
            var etlTableWidth = $("#etlConfigDataTable").width();
            $("#etlConfigDataTable_wrapper").find(".row").width(etlTableWidth);
        }
	}

	function haveScrollBar(){
		var obj = document.getElementById("etl_tab_content");
		if(obj.scrollWidth > obj.clientWidth){
			return true;
		} else {
		    return false;
		}
	}

	function templateDownload() {
		window.open('${ctxStatic }/etlconfig/etl_config.xlsx');
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
					$.post("${ctxbi}/etlConfig/removeConfigRow", {
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
			var value = $(n).attr("data-value");

			if (key != undefined) {
				var htmlValue = $(n).html();
				jsonData[key] = htmlValue.replace(/<br>/g, '');
			}
		})

		$.post('${ctxbi}/etlConfig/updateConfigRow', {
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
			if (!isNaN(value) && value.toString().length > 12) {
				$(n).html(formatDate(new Date(parseInt(value))));
			} else {
				$(n).html(value);
			}
		})
	}

	function formatDate(date) {
		var f = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
		return f;
	}
</script>
</html>