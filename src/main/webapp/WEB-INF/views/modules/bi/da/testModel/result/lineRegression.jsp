<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>
<%-- <c:forEach items="${testModel.summary }" var="obj" varStatus="sta">
<div class="row" style="margin-top: 5px;">
	<div class="col-xs-3">
		${obj.key }
	</div>
	<div class="col-xs-9">
		<c:forEach items="${obj.value }" var="subObj">
			${subObj }
		</c:forEach>
	</div>
</div>
</c:forEach> --%>

<%-- <div class="row" style="margin-top: 5px;">
	<div class="col-xs-3">
		Estimate
	</div>
	<div class="col-xs-9">
	<c:forEach items="${testModel.summary.estimate }" var="obj">
		${obj }&nbsp;&nbsp;
	</c:forEach>
	</div>
	<div class="col-xs-3">
		Std. Error
	</div>
	<div class="col-xs-9">
	<c:forEach items="${testModel.summary.stdError }" var="obj">
		${obj }&nbsp;&nbsp;
	</c:forEach>
	</div>
	<div class="col-xs-3">
		t value
	</div>
	<div class="col-xs-9">
	<c:forEach items="${testModel.summary.tValue }" var="obj">
		${obj }&nbsp;&nbsp;
	</c:forEach>
	</div>
	<div class="col-xs-3">
		Pr(>|t|)
	</div>
	<div class="col-xs-9">
	<c:forEach items="${testModel.summary.pValue }" var="obj">
		${obj }&nbsp;&nbsp;
	</c:forEach>
	</div>
</div> --%>

<div class="row" style="margin-top: 5px;">
<div class="col-xs-12">
	<table>
		<thead>
			<tr>
				<th width="80px">Estimate</th>
				<th width="80px">Std. Error</th>
				<th width="80px">t value</th>
				<th width="80px">Pr(>|t|)</th>
				<th width="80px"></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${testModel.summary.pValue }" var="obj" varStatus="sta">
				<tr>
					<td>${fnbi:keepTwoDecimal(testModel.summary.estimate[sta.index]) }</td>
					<td>${fnbi:keepTwoDecimal(testModel.summary.stdError[sta.index]) }</td>
					<td>${fnbi:keepTwoDecimal(testModel.summary.tValue[sta.index]) }</td>
					<td>${fnbi:keepTwoDecimal(testModel.summary.pValue[sta.index]) }</td>
					<td>${testModel.summary.SignificanceLevel[sta.index] }</td>
					
				</tr>
				
			</c:forEach>
			<tr><td colspan="5">${testModel.summary.significanceCodes }</td></tr>
		</tbody>
	</table>
</div>
</div>
