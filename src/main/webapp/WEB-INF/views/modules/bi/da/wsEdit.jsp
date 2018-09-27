<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" buffer="1024kb"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="layout.head.analytics" /></title>
<c:import url="/bi/layout/resource" />
<%@ include file="/WEB-INF/views/modules/bi/da/daValidInputDataModal.jsp" %>
<style type="text/css">
select:not(.chosen-select){min-width:50px}
select:not(.chosen-select){max-width:150px}
</style>
</head>
<body>
	<%-- <c:import url="/bi/layout/foot" /> --%>

	<c:import url="/bi/layout/head" />
	<div class="main-container" id="main-container">
		<script type="text/javascript">
			try {
				ace.settings.check('main-container', 'fixed')
			} catch (e) {
			}
		</script>
		<div class="main-container-inner">
			<a class="menu-toggler" id="menu-toggler" href="#"> <span
				class="menu-text"></span>
			</a>
			<c:import url="/bi/layout/sidebar">
				<c:param name="dataMenuActive" value="29" />
			</c:import>
			<div class="main-content">
				<div class="page-content">
					<!-- PAGE CONTENT BEGINS -->
					<div class="row">
						<div class="col-xs-12">
						<%-- <div class="col-xs-12">
						<div class="col-xs-1" style="margin-top: 5px;">
								<spring:message code='da.index.ta'/>
							</div>
							<div class="col-xs-2">
								<select id="da_sel_ta" class="form-control">
									<option value="Choose"><spring:message code='da.index.choose'/></option>
									<c:forEach items="${taList }" var="ta" varStatus="sta">
									<option value="${ta.id }">${ta.taName }</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-xs-1" style="margin-top: 5px;">
								<spring:message code='da.index.study'/>
							</div>
							<div class="col-xs-2">
								<select id="da_sel_study" class="form-control">
									<option value="Choose"><spring:message code='da.index.choose'/></option>
								</select>
							</div>
							<div class="col-xs-2" style="margin-top: 5px;">
								<spring:message code='da.index.statisticalapproach'/>
							</div>
							<div class="col-xs-2">
								<select id="da_sel_test" class="form-control">
									<option value="Choose"><spring:message code='da.index.choose'/></option>
									<optgroup label="<spring:message code='da.index.normaldistribution'/>">
										<c:forEach items="${testArray }" var="test" varStatus="sta">
											<c:if test="${test.type.code == 1 }">
												<option value="${test.name }">${test.name }</option>
											</c:if>
										</c:forEach>
									</optgroup>
										<optgroup label="<spring:message code='da.index.skeweddistribution'/>">
											<c:forEach items="${testArray }" var="test" varStatus="sta">
												<c:if test="${test.type.code == 2 }">
													<option value="${test.name }">${test.name }</option>
												</c:if>
											</c:forEach>
										</optgroup>
								</select>
							</div>
						</div> --%>
							<br>
							<table class="whiteTable">
								<tr>
									<th><spring:message code='da.index.ta'/></th>
									<td>
										<select id="da_sel_ta" class="form-control">
											<option value="Choose"><spring:message code='da.index.choose'/></option>
											<c:forEach items="${taList }" var="ta" varStatus="sta">
											<option value="${ta.id }">${ta.taName }</option>
											</c:forEach>
										</select>
									</td>
									<th><spring:message code='da.index.study'/></th>
									<td>
										<select id="da_sel_study" class="form-control">
											<option value="Choose"><spring:message code='da.index.choose'/></option>
											<%-- <c:forEach items="${studyList }" var="study" varStatus="sta">
											<option value="${study.id }">${study.studyName }</option>
											</c:forEach> --%>
										</select>
									</td>
									<th><spring:message code='da.index.statisticalapproach'/></th>
									<td>
										<select id="da_sel_test" class="form-control">
											<option value="Choose"><spring:message code='da.index.choose'/></option>
											<optgroup label="<spring:message code='da.index.normaldistribution'/>">
												<c:forEach items="${testArray }" var="test" varStatus="sta">
													<c:if test="${test.type.code == 1 }">
														<option value="${test.name }">${test.name }</option>
													</c:if>
												</c:forEach>
											</optgroup>
											<optgroup label="<spring:message code='da.index.skeweddistribution'/>">
												<c:forEach items="${testArray }" var="test" varStatus="sta">
												<c:if test="${test.type.code == 2 }">
														<option value="${test.name }">${test.name }</option>
													</c:if>
												</c:forEach>
											</optgroup>
											
										</select>
									</td>
									<th style="display: none;"></th>
									<td style="display: none;"></td>
								</tr>
							</table>
							<div class="row" name="data_model_choose_div">
								<div class="col-xs-12">
									<h3 class="header smaller lighter blue"><spring:message code='da.index.datamodelchoose'/></h3>
								</div>
							</div>
							<div class="row" name="data_model_choose_div">
								<div class="col-xs-12" id="data_model_choose_div_view">
									<spring:message code='da.index.pleasechooseaapproach'/>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12">
									<h3 class="header smChooseer lighter blue"><spring:message code='da.index.cohortchoose'/></h3>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12">
									<div class="table-responsive" id="cohorts_chosen_div">
										<spring:message code='da.index.pleasechooseastudy'/>
									</div>
									<!-- /.table-responsive -->
								</div>
								<!-- /span -->
							</div>
							<div class="row">
								<div class="col-xs-10" style="padding-right: 0px;margin-right: 0px;">
									<h3 class="header smaller lighter blue">
									<spring:message code='da.index.analysisparamterfromsampledata'/>
									</h3>
								</div>
								<div class="col-xs-2" style="padding-left: 0px;margin-left: 0px;" align="right">
									<h3 class="header smaller lighter blue">
										<button type="button" class="btn btn-purple btn-minier" id="data_frame_query_bt" style="margin-top: 1px;margin-bottom: 1px;">
											<spring:message code='da.index.query'/>
										</button>
										<button type="button" class="btn btn-purple btn-minier" id="data_frame_show_bt">
											<spring:message code='da.index.dataframe.fold'/>
										</button>
										<button type="button" class="btn btn-purple btn-minier" id="data_frame_hide_bt" style="display: none;">
											<spring:message code='da.index.dataframe.show'/>
										</button>
									</h3>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12">
									<div class="table-responsive" id="data_frame_div">
										<spring:message code='da.index.pleaseclickquerybuttonforscaningdata'/>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12">
									<h3 class="header smaller lighter blue">
										<spring:message code='da.index.choosetestmodel'/>
									</h3>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12">
									<div class="table-responsive" id="test_model_choose_div">
										<spring:message code='da.index.pleasechooseaapproach'/>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12">
									<h3 class="header smaller lighter blue">
										<spring:message code='da.index.testextendattset'/>
									</h3>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12">
									<div class="table-responsive" id="test_extend_att_div">
										<spring:message code='da.index.pleasechooseaapproach'/>
									</div>
								</div>
							</div>
							<hr>
							<div class="row">
								<div class="col-xs-12" >
										<button type="button" id="calculation_bt">
											<spring:message code='da.index.calculation'/>
										</button>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12" id="results_div">
									
								</div>
							</div>
							<div class="row" id="save_to_work_spance_div" style="display: none;">
								<div class="col-xs-12" align="right">
										<button type="button" class="btn btn-purple btn-sm" id="save_to_work_spance_bt">
											<spring:message code='da.index.savetoworkspace'/>
										</button>
								</div>
							</div>
						</div>
					</div>
					<!-- /.col -->
				</div>
				
				<!-- 保存测试弹出界面 -->
				<div class="modal fade" id="da_save_modal" tabindex="-1" role="dialog" 
				   aria-labelledby="myModalLabel" aria-hidden="true">
				   <div class="modal-dialog">
				      <div class="modal-content">
				         <div class="modal-header">
				            <button type="button" class="close" 
				               data-dismiss="modal" aria-hidden="true">
				                  &times;
				            </button>
				            <h4 class="modal-title" id="myModalLabel">
				            	<spring:message code='da.index.dataresultsave'/>
				            </h4>
				         </div>
				         <div class="modal-body">
							<div class="row">
								<div class="col-xs-3">
									<label><spring:message code='da.index.testname'/></label>
								</div>
								<div class="col-xs-9">
									<span id="da_save_modal_test_name_span"></span>
								</div>
							</div>
							<div class="row" style="margin-top: 5px;">
								<div class="col-xs-3">
									<label><spring:message code='da.index.title'/></label>
								</div>
								<div class="col-xs-9">
									<input type="text" id="da_save_modal_title_txt" class="form-control">
								</div>
							</div>
							<div class="row" style="margin-top: 5px;">
								<div class="col-xs-3">
									<label><spring:message code='da.index.desc'/></label>
								</div>
								<div class="col-xs-9">
									<textarea type="text" id="da_save_modal_desc_ta" class="autosize-transition form-control"></textarea>
								</div>
							</div>
				         </div>
				         <div class="modal-footer">
				            <button type="button" class="btn btn-default" data-dismiss="modal">
								<spring:message code='da.index.close'/>
				            </button>
				            <button type="button" class="btn btn-purple" id="da_save_modal_save_bt">
				              	 <spring:message code='da.index.save'/>
				            </button>
				         </div>
				      </div><!-- /.modal-content -->
				</div>
				
				<!-- PAGE CONTENT ENDS -->
			</div>


			<!-- /.page-content -->
		</div>
		<!-- /.main-content -->

	</div>
	<!-- /.main-container-inner -->

	<a href="#" id="btn-scroll-up"
		class="btn-scroll-up btn btn-sm btn-inverse"> <i
		class="icon-double-angle-up icon-only bigger-110"></i>
	</a>
	</div>
	<!-- /.main-container -->

	<script type="text/javascript">
		var invokeParams={
				taId:'',
				testName:'',
				studyId:'',
				modelMetaId:'',
				modelColumnIds:[],
				cohortIds:[],
				groupIds:[],
				testModelId:'',
				extAtt:{},
				formula:'',
				formulaSymbol:[]
				
		}
		var ta2studies = {}; 
		<c:forEach items="${taList}" var="ta" varStatus="ta_sta">
		var studies_${ta.id } = [];
		<c:forEach items="${studyList }" var="study" varStatus="study_sta">
		<c:if test="${ta.id eq study.taId }">studies_${ta.id }.push({studyName:'${study.studyName }',studyId:'${study.id }'});</c:if>
		</c:forEach>
		ta2studies['${ta.id }']=studies_${ta.id };
		</c:forEach>
		
		function removeStudySel(){
			$("#da_sel_study").html("<option value='Choose'><spring:message code='da.index.choose'/></option>");
		}
		
		function initStudySelByTaId(taId){
			if(taId=="Choose"){
				return false;
			}
			var studies = ta2studies[taId];
			$.each(studies,function(i,n){
				$("#da_sel_study").append("<option value='"+n.studyId+"'>"+n.studyName+"</option>");
			});
		}
		
		/**
		 *	初始化模型选择IDS
		 */
		function refulshModelMeta(){
			var modelMetaId = $("#model_meta_sel").val();
			invokeParams.modelMetaId = modelMetaId;
			if(getChosenColumn==undefined){
				return "<spring:message code='da.index.pleasechooseaapproach'/>";
			}
			invokeParams.modelColumnIds = getChosenColumn();
			return true;
		}
		
		/**
		 *	初始化cohorts选择IDS
		 */
		function refulshCohorts(){
			var chosen = getCohortsChosenInfo();
			invokeParams.groupIds = [];
			invokeParams.cohortIds = [];
			$.each(chosen,function(i,n){
				if(n.data_type==0){
					invokeParams.groupIds.push(n.id);
				}else if(n.data_type==1){
					invokeParams.cohortIds.push(n.id);
				}
			});
		}
		
		function refulshFormula(){
			invokeParams.formula = getFormula();
			console.log(invokeParams.formula);
		}
		
		function reflushFormulaSymbol(){
			invokeParams.formulaSymbol = getFormulaSymbol();
			console.log(invokeParams.formulaSymbol);
		}
		
// 		checkType:
// 			all:检查所有
// 			dataFrame:只确保dataFrame查询所需参数被检查
// 			testModel：只确保new test model 所需参数被检查
		function checkAll(checkType){
		//function checkAll(dataFrameQuery){
			if(checkType==undefined){
				checkType = "all";
			}
			
			//检查TA选择没有
			if(invokeParams.taId==undefined || invokeParams.taId=="Choose" ||invokeParams.taId==""){
				$.simplyToast("<spring:message code='da.index.pleasechooseata'/>", 'danger');
				return false;
			}
			//检查study选择没有
			if(invokeParams.studyId==undefined || invokeParams.studyId=="Choose" ||invokeParams.studyId==""){
				$.simplyToast("<spring:message code='da.index.pleasechooseastudy'/>", 'danger');
				return false;
			}
			//检查test方法选择没有
			if(invokeParams.testName==undefined || invokeParams.testName=="Choose" ||invokeParams.testName==""){
				$.simplyToast("<spring:message code='da.index.pleasechooseaapproach'/>", 'danger');
				return false;
			}
			//检查数据模型及相关column选择没有
			////数据模型数量啥的对不对
			var checkResult = checkColumnsCount();
			if(checkResult!=true){
				$.simplyToast(checkResult, 'danger');
				return false;
			}
			////刷新数据到本地请求对象
			checkResult = refulshModelMeta();
			if(checkResult!=true){
				$.simplyToast(checkResult, 'danger');
				return false;
			}
			if(invokeParams.modelMetaId==undefined || invokeParams.modelMetaId=="Choose" ||invokeParams.modelMetaId==""){
				$.simplyToast("<spring:message code='da.index.pleasechooseadatamodel'/>", 'danger');
				return false;
			}
			if(invokeParams.modelColumnIds==undefined || invokeParams.modelColumnIds.length==0){
				$.simplyToast("<spring:message code='da.index.pleasechoosetheanalysisparameter'/>", 'danger');
				return false;
			}

			//检查相关conhorts选择没有
			////先检查数据
			checkResult = checkCohortsChosenInfo();
			if(checkResult!=true){
				$.simplyToast(checkResult, 'danger');
				return false;
			}
			////刷新数据到本地页面缓存
			refulshCohorts();
			if((invokeParams.cohortIds==undefined || invokeParams.cohortIds.length==0) && (invokeParams.groupIds==undefined || invokeParams.groupIds.length==0)){
				$.simplyToast("<spring:message code='da.index.pleasechoosethecohorts'/>", 'danger');
				return false;
			}
			
			//如果只是查询dataframe
			if(checkType=="all"){
				//检查测试模型选择没有
				checkResult = checkTestModel();
				if(checkResult!=true){
					$.simplyToast(checkResult, 'danger');
					return false;
				}
				refulshTestModel();
				
				//检查需要设置的额外配置属性配置没有
				////先检查数据
				if(checkExtendAtt==undefined){

				}else{
					checkResult = checkExtendAtt();
					if(checkResult!=true){
						$.simplyToast(checkResult, 'danger');
						return false;
					}
					////刷新数据到本地页面缓存 由于各个test方法的扩展参数内容都是动态的 因此不能做统一的校验处理，各个test方法的校验都在相应的test_extendAtt jsp中实现
					refulshExtendAtt();
				}
			}
			
			//保存公式到本地缓存
			refulshFormula();
			reflushFormulaSymbol();
			return true;
			
		}
		
		function reflushTestModelDiv(){
			//$("#test_model_choose_div").html("<i class='icon-spinner icon-spin blue bigger-150'></i>");
			$("#test_model_choose_div").load("${ctxbi}/da/model/choose/"+invokeParams.testName);
		}
		
		$(function(){
			$("#da_sel_ta").on("change",function(){
				var chooseTaId = $(this).val();
				if(chooseTaId == invokeParams.taId ){
					return false;
				}
				invokeParams.taId = chooseTaId;
				removeStudySel();
				initStudySelByTaId(invokeParams.taId);
				invokeParams.studyId = '';
			});
			$("#da_sel_study").on("change",function(){
				var chooseStudyId = $(this).val();
				invokeParams.studyId = chooseStudyId;
				$("#cohorts_chosen_div").load("${ctxbi}/da/cohorts?studyId="+chooseStudyId);
			});
			$("#da_sel_test").on("change",function(){
				reflushTest();
			});
			$("#data_frame_query_bt").on("click",function(){
				var result = checkAll("dataFrame");
				if(result){
					console.log(invokeParams);
					$("#data_frame_div").html("<i class='icon-spinner icon-spin blue bigger-150'></i>");
					$("#data_frame_div").load("${ctxbi}/da/dataframe",invokeParams);
				}
			});
			$("#data_frame_show_bt,#data_frame_hide_bt").on("click",function(){
				$("#data_frame_div").slideToggle("fast");
				$("#data_frame_show_bt").toggle();
				$("#data_frame_hide_bt").toggle();
			});
			$("#calculation_bt").on("click",function(){
				//先隐藏按钮
				$("#save_to_work_spance_div").hide();
				//检查数据模型先
				var checkResult = checkAll("all");
				console.log(invokeParams);
				if(checkResult){
					$("#results_div").html("<i class='icon-spinner icon-spin blue bigger-150'></i>");
					$("#results_div").load("${ctxbi}/da/execute",invokeParams,function(){
						var errorMarker = $("#testErrorMarker");//test错误标识，是一个没内容的div，在/az-rwe-bi/src/main/webapp/WEB-INF/views/modules/bi/da/result/error.jsp中
						if(errorMarker==undefined || errorMarker[0]==undefined){
							console.log("没有出错，显示保存按钮");
							$("#save_to_work_spance_div").show();
						}else{
							console.log("出现错误");
							$("#save_to_work_spance_div").hide();
						}
					});
				}
			});
			$("#save_to_work_spance_bt").on("click",function(e){
				e.preventDefault();
				$("#da_save_modal_test_name_span").html(invokeParams.testName);
				$( "#da_save_modal" ).modal();
			});
			$("#da_save_modal_save_bt").on("click",function(e){
				
				var title = $("#da_save_modal_title_txt").val();
				var desc = $("#da_save_modal_desc_ta").val();
				
				if($.trim(title)=='' || title == undefined){
					$.simplyToast("<spring:message code='da.index.thetitlecannotbeempty'/>", 'danger');
					return false;
				}
				
				invokeParams.title=title;
				invokeParams.remarks=desc;
				invokeParams.daViewId = '${workSpaceDaView.id}';
				$.post("${ctxbi}/ws/update",
						invokeParams,
						function(data){
							if(data.isSuccess){
								//window.location.reload();
								//跳转到workspace页面
								window.location.href='${ctxbi}/ws/list';
							}else{
								$.simplyToast(data.message, 'danger');
							}
						},
						"json");
				
			});
			
			reflushInvokeParams();
			reflushTest();
			reflushCohortsPage();
		});
		
		function reflushInvokeParams(){
			invokeParams.taId='${workSpaceDaView.invoke.taId}';
			invokeParams.testName='${workSpaceDaView.invoke.testName}';
			invokeParams.studyId='${workSpaceDaView.invoke.studyId}';
			invokeParams.modelMetaId='${workSpaceDaView.invoke.modelMeta.id}';
			<c:forEach items="${workSpaceDaView.invoke.chosenParams }" var="dataModelParamChosen" varStatus="sta">
				invokeParams.modelColumnIds.push('${dataModelParamChosen.column.id }');
			</c:forEach>
			<c:forEach items="${workSpaceDaView.invoke.conhortChosen.cohorts }" var="cohort" varStatus="sta">
				invokeParams.cohortIds.push('${cohort }');
			</c:forEach>
			<c:forEach items="${workSpaceDaView.invoke.conhortChosen.groups }" var="group" varStatus="sta">
				invokeParams.groupIds.push('${group }');
			</c:forEach>
			invokeParams.testModelId='${workSpaceDaView.invoke.testModelId }';
			<c:forEach items="${workSpaceDaView.invoke.extAtt }" var="att"  varStatus="sta">
				invokeParams.extAtt.${att.key }='${att.value }';
			</c:forEach>
			invokeParams.formula = "${workSpaceDaView.invoke.formula }";
		}
		
		<c:if test="${not empty workSpaceDaView}">
		//将其赋值到相应控件上
		$("#da_sel_ta").val('${workSpaceDaView.invoke.taId}');
		//初始化study数据
		initStudySelByTaId('${workSpaceDaView.invoke.taId}');
		$("#da_sel_study").val('${workSpaceDaView.invoke.studyId}');
		$("#da_sel_test").val('${workSpaceDaView.invoke.testName}');
		$("#da_save_modal_title_txt").val('${workSpaceDaView.name}');
		$("#da_save_modal_desc_ta").val('${workSpaceDaView.name}');
		$("#da_test_model_sel").val('${workSpaceDaView.invoke.testModelId }');
		</c:if>
		
		function reflushTest(){
			var chooseTestName = $("#da_sel_test").val();
			if(chooseTestName=="Choose"){
				invokeParams.testName = '';
				$("#data_model_choose_div_view").html("<spring:message code='da.index.pleasechooseaapproach'/>");
				$("#test_extend_att_div").html("<spring:message code='da.index.pleasechooseaapproach'/>");
				$("#test_model_choose_div").html("<spring:message code='da.index.pleasechooseaapproach'/>");
			}else{
				invokeParams.testName = chooseTestName;
				$("#data_model_choose_div_view").html("<i class='icon-spinner icon-spin blue bigger-150'></i>");
				$("#data_model_choose_div_view").load("${ctxbi}/da/paramset?testName="+invokeParams.testName + "&studyId="+$("#da_sel_study").val(),function(){reflushTestParameter();});
				
				$("#test_extend_att_div").html("<i class='icon-spinner icon-spin blue bigger-150'></i>");
				//$("#test_extend_att_div").load("${ctxbi}/da/extendAtt?testName="+invokeParams.testName);
				$("#test_extend_att_div").load("${ctxbi}/da/extendAtt?testName="+invokeParams.testName+"&daViewId="+'${workSpaceDaView.id}');
				
				$("#test_model_choose_div").html("<i class='icon-spinner icon-spin blue bigger-150'></i>");
				$("#test_model_choose_div").load("${ctxbi}/da/model/choose/"+invokeParams.testName,function(){$("#da_test_model_sel").val('${workSpaceDaView.invoke.testModelId }'); });
				
				
			}
			
		};
		//将已选择的指标刷新到chosen控件中
		function reflushTestParameter(){
			//初始化数据模型选择
			$("#model_meta_sel").val(invokeParams.modelMetaId);
			//初始化对应模型指标,初始化预先选中的指标
			refushModelColumnInfo('edit');
		};
		//将已选择的指标刷新到公式的选项中
		function reflushTestFormulaParameter(){
			//初始化数据模型选择
			$("#model_meta_sel").val(invokeParams.modelMetaId);
			
			//如果公式属性不为空则按照公式的字符串取生成数据模型的选择情况
			if(invokeParams.formula){
				
				//调用公式页面，取前几个指标初始化公式的参数选项
				for(var j=0;j<invokeParams.modelColumnIds.length-1;j++){
					//跳过第一个，因为paramset页面初始化时就会有一个，只需要补全缺少的个数
					if(j!=0){
						addParam();
					}
					var paramSelectDom = $("#model_column_param_div").find("select[name='model_column_sels']")[j];
					$(paramSelectDom).val(invokeParams.modelColumnIds[j]);
					
				}
				//将公式中所有的列名全部取出，剩余符号
				var operationSymbols = invokeParams.formula;
				//获取modelColumn的所有值
				var $selectColumn = $("#model_column_param_div").find("select[name='model_column_sels']:first");
				$selectColumn.find("option").each(function(){
					var opColumnName = $(this).attr("data-columnName");
					operationSymbols = operationSymbols.replace(opColumnName,"");
				});
				operationSymbols = operationSymbols.replace("~","");
				operationSymbols = operationSymbols.replace(" ","");
				console.log("operationSymbols length:"+operationSymbols.length);
				console.log("operationSymbols:"+operationSymbols);
				//根据符号下拉选择的位置来获取operationSymbol的值，并设置当前下拉的选中值
				$("#model_column_param_div").find("select[name='model_column_symbol_sels']").each(function(m,obj){
					var simpleSymnol = operationSymbols.charAt(m);
					$(this).val(simpleSymnol);
				});
				
				//初始化公式的结果选项
				var $resultSelect = $("#model_column_result_div").find("select");
				$resultSelect.val(invokeParams.modelColumnIds[invokeParams.modelColumnIds.length-1]);
			}else{
				//调用公式页面，取前几个指标初始化公式的参数选项
				for(var j=0;j<invokeParams.modelColumnIds.length-1;j++){
					//跳过第一个，因为paramset页面初始化时就会有一个，只需要补全缺少的个数
					if(j!=0){
						addParam();
					}
					var paramSelectDom = $("#model_column_param_div").find("select[name='model_column_sels']")[j];
					$(paramSelectDom).val(invokeParams.modelColumnIds[j]);
				}
				//初始化公式的结果选项
				var $resultSelect = $("#model_column_result_div").find("select");
				$resultSelect.val(invokeParams.modelColumnIds[invokeParams.modelColumnIds.length-1]);
			}
			
		};
		//初始化cohort页面
		function reflushCohortsPage(){
			$("#cohorts_chosen_div").load("${ctxbi}/da/cohorts?studyId="+invokeParams.studyId,function(){relushCohortsChosen('edit');});
		}
		//初始化cohort选择
		function relushCohortsChosen(type){
			if(type='edit'){
				//$("#cohorts_chosen_sel").val(invokeParams.groupIds);
				//$("#cohorts_chosen_sel").val(invokeParams.cohortIds);
				//$("#cohorts_chosen_sel").trigger("chosen:updated");
				
				for(var i=0;i<invokeParams.cohortIds.length;i++){
					$("#cohorts_chosen_sel>option[data-type=1][value="+invokeParams.cohortIds[i]+"]").attr('selected','selected');
				}
				$("#cohorts_chosen_sel").trigger("chosen:updated");
				
			}

		}
		
	</script>
</body>
</html>