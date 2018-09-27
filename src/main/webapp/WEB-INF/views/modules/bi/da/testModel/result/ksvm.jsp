<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>

<div class="row" style="margin-top: 5px;">
	<table>
		<thead>
			<tr>
				<th width="80px">Length</th>
				<th width="80px">Class</th>
				<th width="80px">Mode</th>
			</tr>
		</thead>
		<tbody>
			<tr>
			<c:forEach items="${testModel.summary.modelResult }" var="obj" varStatus="sta">
				<td>${obj }</td>
			</c:forEach>
			</tr>
		</tbody>
	</table>
</div>
