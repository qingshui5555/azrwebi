<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>
<c:if test="${not empty results }">
<table id="data_frame_table" class="table table-striped table-bordered table-hover">
	<thead>
		<tr>
			<th><spring:message code='da.dataframe.patientcode'/></th>
			<%-- <th><spring:message code='da.dataframe.cohort'/></th> --%>
			<th>Group</th>
			<c:forEach items="${results.dataModelParamChosenList }" var="dataModelParamChosen" varStatus="status">
				<c:choose>
					<c:when test="${dataModelParamChosen.modelColumnName =='group_column_'}">
						<th>${dataModelParamChosen.modelColumnName }</th>
					</c:when>
					<c:otherwise>
						<th>${dataModelParamChosen.modelColumnNameLab }
						<c:if test="${not empty dataModelParamChosen.column.modelColumnUnit }">
						(${dataModelParamChosen.column.modelColumnUnit })
						</c:if>
						</th>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</tr>
	</thead>
	<tbody>
	<c:forEach items="${results.resultMapForCohorts }" var="results" varStatus="mSta">
		<c:forEach items="${results.value }" var="result" varStatus="sta">
		<tr>
			<td>${result.patientCode }</td>
			<td>${results.key.groupName }</td>
			<c:forEach items="${result.paramDataList }" var="columnData" varStatus="status">
				<td>${columnData.dataValue }</td>
			</c:forEach>
		</tr>
		</c:forEach>
	</c:forEach>
	</tbody>
</table>
</c:if>
<c:if test="${empty results || empty results.resultMapForCohorts or fn:length(results.resultMapForCohorts)==0 }">
<div class="row">
	<div class="col-xs-12 blue">
		<h4><spring:message code='da.dataframe.noresults'/></h4>
	</div>
</div>
</c:if>
<script>
$(function(){
	//如果页面选择了多个group列，则删除多余的group列只保留一列
	$("#data_frame_table th").each(function(i,obj){
		var title = $(obj).html();
		console.log(title);
		if(title=="group_column_"){
			//删除thead中多余group列
			$(obj).hide();
			//删除tbody中多余group列
			$("#data_frame_table tr td:nth-child("+(i+1)+")").hide();
		}
	});
	
	$('#data_frame_table').dataTable({
	"oLanguage": dataTableLanguage});
});
</script>