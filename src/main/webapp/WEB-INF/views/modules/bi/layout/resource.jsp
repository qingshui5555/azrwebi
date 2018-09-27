<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>
<head>
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon" />
</head>
<link href="${ctxStatic }/assets/css/bootstrap.min.css" rel="stylesheet" />
<link rel="stylesheet" href="${ctxStatic }/assets/css/font-awesome.min.css" />

<!--[if IE 7]>
  <link rel="stylesheet" href="${ctxStatic }/assets/css/font-awesome-ie7.min.css" />
<![endif]-->

<!-- page specific plugin styles -->
<link rel="stylesheet" href="${ctxStatic }/assets/css/jquery-ui-1.10.3.custom.min.css" />
<link rel="stylesheet" href="${ctxStatic }/assets/css/jquery.gritter.css" />
<link rel="stylesheet" href="${ctxStatic }/assets/css/chosen.css" />

<!-- fonts -->
<link rel="stylesheet" href="${ctxStatic }/assets/css/ace-fonts.css" />

<!-- ace styles -->
<link rel="stylesheet" href="${ctxStatic }/assets/css/ace.min.css" />
<link rel="stylesheet" href="${ctxStatic }/assets/css/ace-rtl.min.css" />
<link rel="stylesheet" href="${ctxStatic }/assets/css/ace-skins.min.css" />
 
<!--[if lte IE 8]>
  <link rel="stylesheet" href="${ctxStatic }/assets/css/ace-ie.min.css" />
<![endif]-->

<!-- added for AZ styles -->
<link rel="stylesheet" href="${ctxStatic }/assets/css/base.css" />
<link rel="stylesheet" href="${ctxStatic }/assets/css/skin_${style }.css" />

<script src="${ctxStatic }/assets/js/ace-extra.min.js"></script>

<!--调用D3-->
<script src="${ctxStatic }/assets/js/d3.v3.4.8.js"></script>

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
<script src="${ctxStatic }/assets/js/html5shiv.js"></script>
<script src="${ctxStatic }/assets/js/respond.min.js"></script>
<![endif]-->


<!-- basic scripts --> 

<!--[if !IE]> -->

<script type="text/javascript">
	window.jQuery || document.write("<script src='${ctxStatic }/assets/js/jquery-2.0.3.min.js'>"+"<"+"/script>");
</script>

<!-- <![endif]--> 

<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='${ctxStatic }/assets/js/jquery-1.10.2.min.js'>"+"<"+"/script>");
</script>
<![endif]--> 

<script type="text/javascript">
	if("ontouchend" in document) document.write("<script src='${ctxStatic }/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
</script> 
<script src="${ctxStatic }/assets/js/bootstrap.min.js"></script> 
<script src="${ctxStatic }/assets/js/typeahead-bs2.min.js"></script> 

<!-- page specific plugin scripts --> 
<script src="${ctxStatic }/assets/js/jquery-ui-1.10.3.custom.min.js"></script> 
<script src="${ctxStatic }/assets/js/bootbox.min.js"></script> 
<script src="${ctxStatic }/assets/js/chosen.jquery.min.js"></script> 

<!-- ace scripts --> 
<script src="${ctxStatic }/assets/js/ace-elements.min.js"></script> 
<script src="${ctxStatic }/assets/js/ace.min.js"></script> 
<script src="${ctxStatic }/assets/js/spin.min.js"></script> 
<!-- inline scripts related to this page --> 
<%-- <script src="${ctxStatic }/assets/js/echarts.min.js"></script> --%>
<%-- <script src="${ctxStatic }/echarts/echarts3.js"></script> --%>
    <script src="${ctxStatic}/echarts/echarts.min.js"></script>
<%-- <script src="${ctxStatic }/echarts/echarts3.2.3.js"></script> --%>
<script src="${ctxStatic }/assets/js/dataTool.js"></script><!--调用echarts-->

<!-- jquery validation -->
<link href="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.min.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery-validation/1.11.1/localization/messages_<spring:message code="message_language"/>.js"></script>


<script src="${ctxStatic}/assets/js/jquery.dataTables.min.js"></script> 
<script src="${ctxStatic}/assets/js/jquery.dataTables.bootstrap.js"></script>
<script src="${ctxStatic}/Highcharts/js/highcharts.js" type="text/javascript"></script>
<script src="${ctxStatic}/Highcharts/js/highcharts-more.js" type="text/javascript"></script>
<script src="${ctxStatic}/Highcharts/js/modules/exporting.js" type="text/javascript"></script>
<script>
    var dataTableLanguage = {
        "sProcessing":   "<spring:message code="layout.data.table.processing"/>",
        "sLengthMenu":   "<spring:message code="layout.data.table.length.menu"/>",
        "sZeroRecords":  "<spring:message code="layout.data.table.zero.record"/>",
        "sInfo":         "<spring:message code="layout.data.table.info"/>",
        "sInfoEmpty":    "<spring:message code="layout.data.table.info.empty"/>",
        "sInfoFiltered": "<spring:message code="layout.data.table.info.filter"/>",
        "sInfoPostFix":  "",
        "sSearch":       "<spring:message code="layout.data.table.search"/>",
        "sUrl":          "",
        "sEmptyTable":     "<spring:message code="layout.data.table.empty"/>",
        "sLoadingRecords": "<spring:message code="layout.data.table.loading"/>",
        "sInfoThousands":  ",",
        "oPaginate": {
            "sFirst":    "<spring:message code="layout.data.table.first"/>",
            "sPrevious": "<spring:message code="layout.data.table.previous"/>",
            "sNext":     "<spring:message code="layout.data.table.next"/>",
            "sLast":     "<spring:message code="layout.data.table.last"/>"
        },
        "oAria": {
            "sSortAscending":  "<spring:message code="layout.data.table.sort.asc"/>",
            "sSortDescending": "<spring:message code="layout.data.table.sort.desc"/>"
        }
    }
    
    var globalColor='${style}';
</script>


<link href="${ctxStatic}/simply-toast/simply-toast.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/simply-toast/simply-toast.js" type="text/javascript"></script>

<link rel="stylesheet" href="${ctxStatic }/assets/css/datepicker.css" />
<script src="${ctxStatic }/assets/js/date-time/bootstrap-datepicker.min.js"></script>

<link rel="stylesheet" href="${ctxStatic }/My97DatePicker/skin/WdatePicker.css" />
<script src="${ctxStatic }/My97DatePicker/WdatePicker.js"></script>

<link rel="stylesheet" href="${ctxStatic }/tabdrop/css/tabdrop.css" />
<script src="${ctxStatic }/tabdrop/js/bootstrap-tabdrop.js"></script>

<!-- echarts主题颜色 -->
<script src="${ctxStatic }/echarts/${style }.js"></script>
<link rel="stylesheet" href="${ctxStatic }/assets/css/iconfont.css" />


