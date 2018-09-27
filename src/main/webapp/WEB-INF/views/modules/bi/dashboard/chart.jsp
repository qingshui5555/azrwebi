<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

	<script type="text/javascript" src="${ctxStatic }/jquery/jquery-1.9.1.js"></script>
	
	<!-- basic styles -->

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

	<!-- ace settings handler -->
	
	<script src="${ctxStatic }/assets/js/ace-extra.min.js"></script>
	<script src="${ctxStatic }/assets/js/d3.v3.4.8.min.js"></script>
	<script src="${ctxStatic }/assets/js/dimple.v2.1.6.min.js"></script>
	
	
	<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	 
	<!--[if lt IE 9]>
	<script src="${ctxStatic }/assets/js/html5shiv.js"></script>
	<script src="${ctxStatic }/assets/js/respond.min.js"></script>
	<![endif]-->

	<title><spring:message code="layout.head.analytics" /></title>
	
	<script type="text/javascript">
		var config={};
		
		config.token='${cip.token}';
		config.studyId='${cip.studyId}';
		config.patientId='${cip.patientId}';
		config.chartId='${cip.chartId}';
		config.cohorts=[];
		<c:if test="${not empty cip.cohorts  }" >
			<c:forEach items="${cip.cohorts}" var="cohort"  varStatus="sta">
		config.cohorts[${sta.index}]='${cohort }';
			</c:forEach>
		</c:if>
	</script>
</head>
<body>

	<%-- String token;${cip.token }</br>
	
	String studyId;${cip.studyId }</br>
	
	String patientId;${cip.patientId }</br>
	
	String[] cohorts;
	<c:if test="${not empty cip.cohorts  }" >
		<c:forEach items="${cip.cohorts}" var="cohort">
			${cohort },
		</c:forEach>
	</c:if>
	</br>
	
	String chartId;${cip.chartId }</br>
	
	
	name:${chart.name }</br>
	title:${chart.title }</br>
	content:${chart.content }</br> --%>
	${chart.title }
	</br>
	
	
	
	
	
	${chart.content }
	
	

</body>

</html>