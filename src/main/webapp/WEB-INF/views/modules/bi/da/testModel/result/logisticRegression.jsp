<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>

<div class="row" style="margin-top: 5px;">
	<table>
		<thead>
			<tr>
				<th width="80px">Estimate</th>
				<th width="80px">Std. Error</th>
				<th width="80px">z value</th>
				<th width="80px">Pr(>|z|)</th>
				<th width="80px"></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${testModel.summary.pValue }" var="obj" varStatus="sta">
				<tr>
					<td>${fnbi:keepTwoDecimal(testModel.summary.estimate[sta.index]) }</td>
					<td>${fnbi:keepTwoDecimal(testModel.summary.stdError[sta.index]) }</td>
					<td>${fnbi:keepTwoDecimal(testModel.summary.zValue[sta.index]) }</td>
					<td>${fnbi:keepTwoDecimal(testModel.summary.pValue[sta.index]) }</td>
					<td>${testModel.summary.SignificanceLevel[sta.index] }</td>
					
				</tr>
				
			</c:forEach>
			<tr><td colspan="5">${testModel.summary.significanceCodes }</td></tr>
		</tbody>
	</table>
</div>
