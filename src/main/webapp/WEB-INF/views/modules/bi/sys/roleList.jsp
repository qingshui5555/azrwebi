<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" buffer="1024kb"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title><spring:message code="layout.head.analytics" /></title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
                    <li class="active"> <a href="${ctxbi}/sys/role/list"> <i class="blue icon-user bigger-110"></i> <spring:message code="study.management.role.ui.list"/> </a> </li>
                    <li> <a href="${ctxbi}/sys/role/form"> <i class="green icon-user bigger-110"></i> <spring:message code="study.management.role.ui.add"/> </a> </li>
                  </ul>
                  <div class="tab-content">
                    <div id="RoleList" class="tab-pane in active">
                      <div class="row">
                        <div class="col-xs-12">
                          <div class="table-responsive">
                            <table id="rolelist-table" class="table table-striped table-bordered table-hover">
                              <thead>
                              <tr>
                                <th><spring:message code="study.management.role.ui.name"/></th>
                                <th><spring:message code="study.management.role.ui.description"/></th>
                                <th><spring:message code="layout.form.operation"/></th>
                              </tr>
                              </thead>
                              <tbody>
                              <c:forEach items="${list}" var="role">
                                <tr id="${role.id}">
                                  <td>${role.name}</td>
                                  <td>${role.remarks}</td>
                                    <td>
                                     <div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
                                    <c:if test="${role.roleType != 'assignment'}">
                                    <c:if test="${(role.sysData eq fns:getDictValue('是', 'yes_no', '1') && fns:getUser().admin)||!(role.sysData eq fns:getDictValue('是', 'yes_no', '1'))}">
                                      <a class="green" title="<spring:message code="study.management.role.edit"/>" href="${ctxbi}/sys/role/form?id=${role.id}">
                                        <i class="icon-pencil bigger-130"></i>
                                      </a>
                                    </c:if>
                                    <a name="a_rolelist_delete" class="red"  title="<spring:message code="study.management.role.delete"/>" style="cursor:pointer">
                                      <i class="icon-trash bigger-130"></i>
                                    </a>
                                    </c:if>
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
<script type="text/javascript">
var flag = ${message!=null};
if(flag) {
	  $.simplyToast('${message}', 'info');
}
  $(document).ready(function(){
    jQuery(function($) {
      $('#rolelist-table').dataTable( {
        "aoColumns": [
          null, null,
          { "bSortable": false }
        ],
        "oLanguage": dataTableLanguage
      } );
    });
    
    deleteRole();
  });

  function confirmDel(message, href) {
    var r = confirm(message);
    if(r) {
      location = href;
    } else {
      return;
    }
  }
  
  function deleteRole(){
		$("a[name='a_rolelist_delete']").on("click",function(){
			$tr = $(this).parent().parent().parent();
			var deleteParams = {};
			deleteParams.id = $tr.attr("id");
			
			bootbox.confirm({
				buttons : {
					confirm : {
						label : '<spring:message code="layout.menu.name.drop.ok"/>',
						className: 'btn-purple'
					},
					cancel : {
						label : '<spring:message code="layout.menu.name.drop.cancel"/>'
					}
				},
				message : "<spring:message code='da.wslist.confirmdeletion'/>",
				callback : function(result) {
					if (result) {
						$.post("${ctxbi}/sys/role/delete", 
								deleteParams ,
						 		function() {
								$.simplyToast('<spring:message code="layout.menu.name.drop.success"/>', 'success');
								$tr.remove();
						})
					}
				}
			});
			
		});
  }
</script>
</body>
</html>