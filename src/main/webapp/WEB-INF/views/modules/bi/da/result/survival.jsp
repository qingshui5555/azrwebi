<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>
<c:set value="<%=(int)(Math.random()*1000) %>" var="resultTestRandom" /> 
<%@ include file="/WEB-INF/views/modules/bi/da/result/daValidInputDataTestResultJs.jsp"%>

<div class="row">
	<div class="col-xs-6">
		<div class="row">
			<div class="col-xs-12">
			<img src="${rResult.resultMap.imgUrl}" width="100%"></img>
			</div>
		</div>
	</div>
	<div class="col-xs-6">
		<div class="widget-box" style="margin-bottom: 56px;">
			<div class="widget-header header-color-blue">
				<h5 class="bigger lighter">
					<i class="icon-dashboard"></i>
					<spring:message code="da.result.normal.box.title"></spring:message>
				</h5>
			</div>
			<div class="widget-body">
				<div class="widget-main no-padding">
					<table class="table table-striped table-bordered table-hover">
						<tbody>
							<tr>
								<td><spring:message code="da.result.normal.testname"></spring:message> </td>
								<td>
									${dfi.testName }
								</td>
							</tr>
							<c:if test="${not empty daView }">
							<tr>
								<td class=""><spring:message code="da.result.normal.description"></spring:message></td>
								<td>
									${daView.remarks}
								</td>
							</tr>
							</c:if>
							<tr>
								<td class=""><spring:message code="da.result.normal.choose.cohorts"></spring:message></td>
								<td>
									<div class="col-xs-9" style="padding-left: 0px;">
										<%-- <c:forEach items="${dfi.conhortChosen.cohortsObj }" var="co"  varStatus="sta">
											<c:if test="${not sta.first }">,</c:if>
											${co.groupName }（${co.patinetCount }）
										</c:forEach>
										<c:forEach items="${dfi.conhortChosen.groupsObj }" var="go"  varStatus="sta">
											,
											${go.groupName }（${go.patinetCount }）
										</c:forEach> --%>
										<c:forEach items="${dataFrameResults.resultMapForCohorts }" var="results" varStatus="sta">
											<c:if test="${not sta.first }">,</c:if>
											${results.key.groupName }(${fn:length(results.value) })
										</c:forEach>
									</div>
									<div class="col-xs-3" style="padding-right: 0px;width:100%" align="right">
										<button type="button" class="btn btn-purple btn-minier" id="valid_input_data_bt_${resultTestRandom }">
											<spring:message code='da.index.analysisparamterfromsampledata'/>
										</button>
									</div>
								</td>
							</tr>
							<tr>
								<td class=""><spring:message code="da.result.normal.choose.parameter"></spring:message></td>
								<td>
									<c:forEach items="${dfi.chosenParams }" var="cp"  varStatus="sta">
										<c:if test="${not sta.first }">,</c:if>
										${cp.column.modelColumnNameLab }
									</c:forEach>
								</td>
							</tr>
							<tr>
								<td class=""><spring:message code="da.result.normal.extend.attribute"></spring:message></td>
								<td>
									<c:forEach items="${dfi.extAtt }" var="att"  varStatus="sta">
									<div class="row">
										<div class="col-xs-2">
											${att.key }
										</div>
										<div class="col-xs-1">
											=
										</div>
										<div class="col-xs-9">
											${att.value }
										</div>
									</div>
									</c:forEach>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>