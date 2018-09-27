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
                    <li class="active"> <a href="${ctxbi}/sys/user/list">  <i class="blue icon-user bigger-110"></i> <spring:message code="study.management.user.ui.list"/> </a> </li>
                    <li> <a href="${ctxbi}/sys/user/form"> <i class="green icon-user bigger-110"></i> <spring:message code="study.management.user.ui.grant"/> </a> </li>
                  </ul>
                  <div class="tab-content">
                    <div id="UserList" class="tab-pane in active">
                      <div class="row">
                        <div class="col-xs-12">
                          <div class="table-responsive">
                            <table id="uselist-table" class="table table-striped table-bordered table-hover">
                              <thead>
                              <tr>
                                <th><spring:message code="study.management.user.ui.name"/></th>
                                <th><spring:message code="study.management.user.ui.study"/></th>
                                <th><spring:message code="layout.menu.name.system.role"/></th>
                                <th width="8%"><spring:message code="layout.form.operation"/></th>
                              </tr>
                              </thead>
                              <tbody>
                              <c:forEach items="${userList}" var="user">
                                <tr>
                                  <td>${user.name}</td>
                                  <td>
                                    <c:forEach items="${user.studyList}" var="study">
                                        ${study.studyName}
                                    </c:forEach>
                                  </td>
                                  <td>
                                    <c:forEach items="${user.roleList}" var="role">
                                      ${role.name}&nbsp;
                                    </c:forEach>
                                  </td>
                                  <td>
                                    <div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
                                      <a class="green" title="<spring:message code="study.management.user.edit"/>" href="${ctxbi}/sys/user/form?id=${user.id}"><i class="icon-pencil bigger-130"></i></a>
                                      <a class="red" title="<spring:message code="study.management.user.delete"/>" href="javascript:confirmDel('<spring:message code="study.management.user.ui.confirm"/>', '${ctxbi}/sys/user/delete?id=${user.id}')"><i class="icon-trash bigger-130"></i></a>
                                    </div>
                                  </td>
                                </tr>
                              </c:forEach>

                              </tbody>
                            </table>
                          </div><!-- /.table-responsive -->
                        </div><!-- /span -->
                      </div>

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
  var flag = ${message!=null};
  if(flag) {
	  $.simplyToast('${message}', 'info');
  }
  jQuery(function($) {
    var oTable1 = $('#uselist-table').dataTable( {
      "aoColumns": [
        null, null,null,
        { "bSortable": false }
      ],
      "oLanguage": dataTableLanguage
    } );
  });

  function confirmDel(message, href) {
    var r = confirm(message);
    if(r) {
      location = href;
    } else {
      return;
    }
  }
</script>
</body>
</html>