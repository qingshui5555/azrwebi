<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" buffer="1024kb"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title><spring:message code="layout.head.analytics" /></title>
  <c:import url="/bi/layout/resource" />
  <script src="${ctxStatic}/assets/js/jquery.dataTables.min.js"></script>
  <script src="${ctxStatic}/assets/js/jquery.dataTables.bootstrap.js"></script>
  <script type="text/javascript">
    
     var m = 0;
     var isSub = false;//是否可以提交  //默认不可以提交
     if(${userId!=null}) {
    	 isSub = true;
     }
     $(document).ready(function(){ 
    	 $("#inputForm").validate({
    	        submitHandler: function(form){
    	        	 $("#name").val($("#name").val().trim());
    	             $("#loginName").val($("#loginName").val().trim());
    	             if($("#name").val() == "" || $("#name").val() == null) {
    	           	$.simplyToast('<spring:message code="study.management.user.ui.name.empty"/>', 'info');
    	               return false;
    	             }
    	             if($("#loginName").val() == "" || $("#loginName").val() == null) {
    	               $.simplyToast('<spring:message code="study.management.user.ui.login.name.empty"/>', 'info');
    	               return false;
    	             }
    	             var checkedRoles =[];
    	             $('input[name="roleIdList"]:checked').each(function(){
    	               checkedRoles.push($(this).val());
    	             });
	    	          if(checkedRoles.length == 0) {
	    	        	  $.simplyToast('<spring:message code="study.management.user.ui.role.empty"/>', 'info');
	    	        	  return false;
	    	          }
	    	          if(isSub) {
	    	        	  form.submit();
	    	          }else {
	    	        	  $.simplyToast('<spring:message code="study.management.user.alert.match"/>', 'info');
	    	          }
    	        },
    	        errorContainer: "#messageBox",
    	      
    	        errorPlacement: function(error, element) {
    	          $("#messageBox").text('<spring:message code="study.management.user.form.save.error"/>');
    	        }
    	      });
    		   });
     
     function getStudyIdsByLoginName() {
    	 var loginName = $("#loginName").val();
    	 if($.trim(loginName)=="") {
    		 return;
    	 }
    	 $.post("${ctxbi}/sys/user/getStudyIdsByLoginName",{loginName:loginName},function(data){
    		 var studyIds = data.info;
    		 var status = data.status;
    		 if(status=="n") {
    			 isSub = false;
    			 $.simplyToast('<spring:message code="study.management.user.alert.no"/>', 'info');
    			 return;
    		 }
    		 isSub = true;
    		 if(studyIds!="") {
    			 var studyArr = studyIds.split(",");
    			 for(i=0;i<studyArr.length;i++) {
    				 //默认选中所以的复选框
    				  $("input:checkbox[name=studyIdList][value="+studyArr[i]+"]").attr("checked",true);
    				 studyArr[i];
    			 }
    		 }
    	 },"json");
     }
  </script>
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
    <c:import url="/bi/layout/sidebar" >
      <c:param name="dataMenuActive" value="fc70bb2aebe944c5a2201bed79fba7ca"/>
    </c:import>
    <div class="main-content">
      <div class="breadcrumbs" id="breadcrumbs">
        <ul class="breadcrumb">
          <li><i class="icon-home home-icon"></i><a href="#"><spring:message code="layout.menu.name.home"/></a></li>
          <li><spring:message code="layout.menu.name.system.configuration"/></li>
          <li><spring:message code="layout.menu.name.system.user"/></li>
        </ul>
        <!-- .breadcrumb -->
      </div>
      <div class="page-content">
        <!-- PAGE CONTENT BEGINS -->

        <div class="row">
          <div class="col-xs-12">
            <!-- PAGE CONTENT BEGINS -->
            <div class="row">
              <div class="col-xs-12">
                <div class="tabbable tabs-top">
                  <ul class="nav nav-tabs">
                    <li> <a href="${ctxbi}/sys/user/list">  <i class="blue icon-user bigger-110"></i> <spring:message code="study.management.user.ui.list"/> </a> </li>
                    <li class="active"> <a href="${ctxbi}/sys/user/form"> <i class="green icon-user bigger-110"></i> <spring:message code="study.management.user.ui.grant"/> </a> </li>
                  </ul>
                  <div class="tab-content">
                    <div id="AddUser">
                      <form:form  id="inputForm" modelAttribute="user" action="${ctxbi}/sys/user/save" method="post">
                      <form:hidden path="id"/>
                      <table class="whiteTable">
                        <th colspan="2" style="color:red;" id="messageBox">${message}</th>
                        <tr>
                          <th><spring:message code="study.management.user.ui.name"/></th><td><form:input  path="name" htmlEscape="false" class="required" /></td>
                        </tr>
                        <tr>
                          <input id="oldLoginName" name="oldLoginName" type="hidden" value="${user.loginName}">
                          <th><spring:message code="study.management.user.ui.login.name"/></th><td><form:input  path="loginName" htmlEscape="false" id="loginName" onblur="getStudyIdsByLoginName()" class="required" />&nbsp;
                          </td>
                        </tr>
                        <tr>
                          <th><spring:message code="study.management.user.ui.study"/></th>
                          <td>
                            <c:forEach items="${allStudies}" var="study">
                              <div class="checkbox">
                                <label>
                                  <input name="studyIdList" type="checkbox"
                                          <c:forEach items="${user.studyList}" var="selectedStudy">
                                            <c:if test="${selectedStudy.guid == study.guid}">
                                              checked
                                            </c:if>
                                          </c:forEach>
                                         class="ace" value="${study.guid}"/>
                                  <span class="lbl">${study.studyName}</span>
                                </label>
                              </div>
                            </c:forEach>
                            <input type="hidden" name="_studyIdList" value="on"/>
                          </td>
                        </tr>
                        <tr>
                          <th><spring:message code="layout.menu.name.system.role"/></th>
                          <td>
                            <c:forEach items="${allRoles}" var="role">
                              <div class="checkbox">
                                <label>
                                  <input name="roleIdList" type="checkbox"
                                          <c:forEach items="${user.roleList}" var="selectedRole">
                                            <c:if test="${selectedRole.id == role.id}">
                                              checked
                                            </c:if>
                                          </c:forEach>
                                           <c:if test="${userId==null && role.id=='4a5d5e7b775c4a9d89610dc941d613cc'}">
                                              checked
                                            </c:if>
                                         class="ace" value="${role.id}"/>
                                  <span class="lbl">${role.name}</span>
                                </label>
                              </div>
                            </c:forEach>
                            <input type="hidden" name="_roleIdList" value="on"/>
                            <!--
                            <div class="checkbox">
                              <label>
                                <input name="form-field-checkbox" type="checkbox" class="ace" />
                                <span class="lbl">Study</span>
                              </label>
                            </div> -->
                          </td>
                        </tr>
                        <tr>
                          <th colspan="2"><button class="btn btn-purple" type="submit"><spring:message code="layout.form.submit"/></button></th>
                        </tr>
                      </table>
                      </form:form>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <!-- /row -->
          </div>
          <!-- /.col -->
        </div>
        <!-- PAGE CONTENT ENDS -->
      </div>
      <!-- /.page-content -->
    </div>
    <!-- /.main-content -->

  </div>
  <!-- /.main-container-inner -->

  <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
    <i class="icon-double-angle-up icon-only bigger-110"></i>
  </a>
</div>
<!-- /.main-container -->
<script type="text/javascript">


  jQuery(function($) {
    var oTable1 = $('#uselist-table').dataTable( {
      "aoColumns": [
        null, null,null,
        { "bSortable": false }
      ] } );
  });
</script>
</body>
</html>