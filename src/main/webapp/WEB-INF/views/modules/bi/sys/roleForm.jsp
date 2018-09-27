<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" buffer="1024kb"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title><spring:message code="layout.head.analytics" /></title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <%-- <%@include file="/WEB-INF/views/modules/bi/layout/resource.jsp" %> --%>
  <c:import url="/bi/layout/resource" />
  <%@include file="/WEB-INF/views/include/treeview.jsp" %>
</head>
<body>
<%-- <c:import url="/bi/layout/foot" /> --%>
<%@include file="/WEB-INF/views/modules/bi/layout/head.jsp" %>
<div class="main-container" id="main-container">
  <script type="text/javascript">
    try {
      ace.settings.check('main-container', 'fixed')
    } catch (e) {
    }

    $(document).ready(function(){
      var setting = {check:{enable:true,nocheckInherit:true},view:{selectedMulti:false},
        data:{simpleData:{enable:true}},callback:{beforeClick:function(id, node){
          tree.checkNode(node, !node.checked, true, true);
          return false;
        }}};

      // 用户-菜单
      var zNodes=[
		<c:forEach items="${menuList}" var="menu">
			<c:if test="${not empty menu.parent.id}">
			{id:"${menu.id}", pId:"${not empty menu.parent.id?menu.parent.id:0}", name:"${menu.name}"},
			</c:if>
		</c:forEach>];
      // 初始化树结构
      var tree = $.fn.zTree.init($("#menuTree"), setting, zNodes);
      // 不选择父节点
      tree.setting.check.chkboxType = { "Y" : "ps", "N" : "s" };
      // 默认选择节点
      var ids = "${role.menuIds}".split(",");
      for(var i=0; i<ids.length; i++) {
        var node = tree.getNodeByParam("id", ids[i]);
        try{tree.checkNode(node, true, false);}catch(e){}
      }
      // 默认展开全部节点
      tree.expandAll(true);
      $("#inputForm").validate({
        submitHandler: function(form){
          var ids = [], nodes = tree.getCheckedNodes(true);
          if(nodes.length==0) {
        	  $.simplyToast('<spring:message code="layout.form.role.error"/>', 'info');
        	  return;
          }
          for(var i=0; i<nodes.length; i++) {
            ids.push(nodes[i].id);
          }
          $("#menuIds").val(ids);
          form.submit();
        },
        errorContainer: "#messageBox",
       
        errorPlacement: function(error, element) {
          $("#messageBox").text('<spring:message code="layout.form.input.error"/>');
          if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
            error.appendTo(element.parent().parent());
          } else {
            error.insertAfter(element);
          }
        }
      });
    });


  </script>
  <div class="main-container-inner">
    <a class="menu-toggler" id="menu-toggler" href="#"> <span
            class="menu-text"></span>
    </a>
    <%-- <%@include file="/WEB-INF/views/modules/bi/layout/sidebar.jsp" %> --%>
    <c:import url="/bi/layout/sidebar" >
      <c:param name="dataMenuActive" value="a845166899ba4d14ad5aeb51574aa8ab"/>
    </c:import>
    <div class="main-content">
      <div class="breadcrumbs" id="breadcrumbs">
        <ul class="breadcrumb">
          <li><i class="icon-home home-icon"></i><a href="#"><spring:message code="layout.menu.name.home"/></a></li>
          <li><spring:message code="layout.menu.name.system.configuration"/></li>
          <li><spring:message code="layout.menu.name.system.role"/></li>
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
                    <li> <a href="${ctxbi}/sys/role/list"> <i class="blue icon-user bigger-110"></i> <spring:message code="study.management.role.ui.list"/> </a> </li>
                    <li class="active"> <a href="${ctxbi}/sys/role/form"> <i class="green icon-user bigger-110"></i> <spring:message code="study.management.role.ui.add"/> </a> </li>
                  </ul>
                  <div class="tab-content">
                    <!-- 添加角色 -->
                    <div id="AddRole" >
                      <form:form  id="inputForm" modelAttribute="role" action="${ctxbi}/sys/role/save" method="post">
                        <form:hidden path="id"/>
                        <table class="whiteTable">
                          <tr>
                            <th colspan="2" style="color:red;" id="messageBox">${message}</th>
                          </tr>
                          <tr>
                            <th><spring:message code="study.management.role.ui.name"/></th>
                            <td>
                              <input id="oldName" name="oldName" type="hidden" value="${role.name}">
                              <form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
                            </td>
                          </tr>
                          <tr>
                            <th><spring:message code="study.management.role.ui.description"/></th><td><form:input path="remarks"  htmlEscape="false" maxlength="50" class="required" /></td>
                          </tr>
                          <tr>
                            <th><spring:message code="study.management.role.ui.permission"/></th>
                            <td><div id="menuTree" class="ztree"></div><form:hidden path="menuIds"/></td>
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

            <!-- PAGE CONTENT ENDS -->
          </div>
          <!-- /.page-content -->
        </div>
        <!-- /.main-content -->
      </div>
      <!-- /.main-container-inner -->
    </div>
    <!-- /.main-container -->
  </div>
</div>
</body>
</html>