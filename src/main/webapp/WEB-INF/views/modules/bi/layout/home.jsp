<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" buffer="1024kb"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><spring:message code="layout.head.analytics" /></title>
	<c:import url="/bi/layout/resource" />
	</head>
	<body>
		<input type="hidden" id="hdnForcePassword" value="${forcePassword}" />
		<input type="hidden" id="hdnChangePasswordUrl" value="${changePasswordUrl}" />
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
					<c:param name="dataMenuActive" value="29"/>
				</c:import>
				<div class="main-content">
					<div class="page-content">
						<!-- PAGE CONTENT BEGINS -->
	
						<div  id="home-page-loading"></div>
	
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
	</body>
	<script type="text/javascript">
		$(function(){
			var forcePassword = $("#hdnForcePassword").val();
			var changePasswordUrl = $("#hdnChangePasswordUrl").val();
			if(forcePassword == "true"){
				window.location.href = changePasswordUrl;
				return false;
			}
	        var opts = {            
	                lines: 13, // 花瓣数目
	                length: 20, // 花瓣长度
	                width: 10, // 花瓣宽度
	                radius: 30, // 花瓣距中心半径
	                corners: 1, // 花瓣圆滑度 (0-1)
	                rotate: 0, // 花瓣旋转角度
	                direction: 1, // 花瓣旋转方向 1: 顺时针, -1: 逆时针
	                color: '#5882FA', // 花瓣颜色
	                speed: 1, // 花瓣旋转速度
	                trail: 60, // 花瓣旋转时的拖影(百分比)
	                shadow: false, // 花瓣是否显示阴影
	                hwaccel: false, //spinner 是否启用硬件加速及高速旋转            
	                className: 'spinner', // spinner css 样式名称
	                zIndex: 2e9, // spinner的z轴 (默认是2000000000)
	                top: '100', // spinner 相对父容器Top定位 单位 px
	                left: 'auto'// spinner 相对父容器Left定位 单位 px
	            };

	        var spinner = new Spinner(opts);
	        spinner.spin($("#home-page-loading")[0]);
			
			$("#sidebar ul li a").each(function(i,n){
				var a_url = this;
				var reg_end_with=new RegExp("#$");   
				if(!reg_end_with.test(a_url)){
					window.location.href = a_url;
					return false;
				}
			});
			
		});
	</script>
</html>