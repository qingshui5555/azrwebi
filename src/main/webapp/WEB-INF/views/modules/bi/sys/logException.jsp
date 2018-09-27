<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%request.setAttribute("strEnter", "\n");request.setAttribute("strTab", "\t");%>
<c:choose>
	<c:when test="${empty logException}">
		<spring:message code="accesslog.noexception" />
	</c:when>
	<c:otherwise>
		${fn:replace(fn:replace(fns:escapeHtml(logException), strEnter, '<br/>'), strTab, '&nbsp; &nbsp; ')}
	</c:otherwise>
</c:choose>
