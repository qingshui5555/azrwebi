<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>
<c:forEach items="${viewList }" var="view">
	<c:import url="${ctxbi }/view/${view.id }" >
		<c:param name="vip" value="${vip }"/>
	</c:import>
</c:forEach>