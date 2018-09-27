<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>

<c:set var="leve_1_menu_list" value="${fns:getMenuListByParentId('1')}" />
<c:set var="firstMenu" value="true" />
<div class="sidebar menu-min" id="sidebar">
	<ul class="nav nav-list">
		<c:forEach items="${leve_1_menu_list }" var="leve_1_menu" varStatus="leve_1_sta">
		
		<li class="" data-menu-active="${leve_3_menu.id }">
			<c:set var="leve_2_menu_list" value="${fns:getMenuListByParentId(leve_1_menu.id)}" />
			<c:set var="is_have_leve_2_menu_list" value="${fn:length(leve_2_menu_list) != 0 }" />
			<a href="#" class="dropdown-toggle">
				<i class="${leve_1_menu.icon }"></i> 
				<span class="menu-text"><spring:message code="${leve_1_menu.remarks }" text="${leve_1_menu.name }"/></span> 
				<b class="arrow <c:if test="${is_have_leve_2_menu_list }">icon-angle-down</c:if>"></b>
			</a>
			<c:if test="${is_have_leve_2_menu_list }">
			<ul class="submenu">
				<c:forEach items="${leve_2_menu_list }" var="leve_2_menu" varStatus="leve_2_sta">
				
				<c:set var="leve_3_menu_list" value="${fns:getMenuListByParentId(leve_2_menu.id)}" />
				<c:set var="is_have_leve_3_menu_list" value="${fn:length(leve_3_menu_list) != 0 }" />	
				<li class=""  data-menu-active="${leve_2_menu.id }">
					<a href="<c:if test="${is_have_leve_3_menu_list }">#</c:if><c:if test="${not is_have_leve_3_menu_list }">${ctx }${leve_2_menu.href}</c:if>" class="dropdown-toggle">
						<i class="icon-double-angle-right"></i>
						<spring:message code="${leve_2_menu.remarks }" text="${leve_2_menu.name }"/>
						<b class="arrow <c:if test="${is_have_leve_3_menu_list }">icon-angle-down</c:if>"></b>
					</a>
					<c:if test="${is_have_leve_3_menu_list }">
					<ul class="submenu">
						<c:forEach items="${leve_3_menu_list }" var="leve_3_menu" varStatus="leve_3_sta">
						
						<li class="" data-menu-active="${leve_3_menu.id }">
							<a href="${ctx }${leve_3_menu.href }"> 
								<i class="icon-leaf"></i>
								<spring:message code="${leve_3_menu.remarks }" text="${leve_3_menu.name }"/>
							</a>
						</li>
						
						</c:forEach>
					</ul>
					</c:if>
				</li>
				
				</c:forEach>
			</ul>
			</c:if>
		</li>
		
		</c:forEach>
	</ul>
	<!-- /.nav-list -->
	<div class="sidebar-collapse" id="sidebar-collapse" style="display: none;">
		<i class="icon-double-angle-left" data-icon1="icon-double-angle-left"
			data-icon2="icon-double-angle-right"></i>
	</div>
	<script type="text/javascript">
	$(function(){
		var choose_data_menu_active = "${param.dataMenuActive}";
		var choose_menu = $("li[data-menu-active='"+choose_data_menu_active+"']");
		choose_menu.each(function(){
			var leve_1_menu_li = $(this);
			var leve_2_menu_li = $(this).parent().parent();
			var leve_3_menu_li = leve_2_menu_li.parent().parent();
			leve_1_menu_li.addClass("active open");
			leve_2_menu_li.addClass("active open");
			leve_3_menu_li.addClass("active open");
		});
	});
	</script>
</div>