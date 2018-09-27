<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/bitaglib.jsp"%>
<%-- <head>
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon" />
</head> --%>
<div class="navbar navbar-default" id="navbar"> 
  <script type="text/javascript">
	  try{ace.settings.check('navbar' , 'fixed')}catch(e){}

      function changeLocale(locale) {
        document.cookie = '_change_locale=' + locale+';path=/';
        location.reload();
      }
      
      function changeStyle(style) {
          document.cookie = '_change_style=' + style+';path=/';
          location.reload();
      }
      
      function getCookie(name)
      {
    	  var name_ = name + "=";
    	  var cookiesArray = document.cookie.split(";");
    	  for(var i=0;i < cookiesArray.length;i++){
    		  var cookie = cookiesArray[i];
    		  var idx = cookie.indexOf(name_);
    		  if (idx != -1){
    			  //截取_change_style=字符串后的值
    			  return $.trim(cookie.substring(idx+name_.length,cookie.length));
    		  }
          }
       return null;
      }
      /* function getCookie(c_name){
    	  if (document.cookie.length>0){
    		  c_start=document.cookie.indexOf(c_name + "=");
    		  if (c_start!=-1){
    			  c_start=c_start + c_name.length+1;
    			  c_end=document.cookie.indexOf(";",c_start);
    			  if (c_end==-1) c_end=document.cookie.length
    			  return unescape(document.cookie.substring(c_start,c_end));
    			  }
    		  }
    	  return null
      } */
  </script>
  <div class="navbar-container" id="navbar-container">
    <div class="navbar-header pull-left"> <a href="#" class="navbar-brand"> 
    <img src="${ctxStatic }/assets/images/new-logo.png"> <small> <spring:message code="layout.head.analytics"/> </small> 
    </a><!-- /.brand --> 
    </div>
    <!-- /.navbar-header -->
    
    <div class="navbar-header pull-right" role="navigation">
      <ul class="nav ace-nav">
        <li class="light-blue">
          <a data-toggle="dropdown" href="#" class="dropdown-toggle">
            <span class="user-info"> <small><spring:message code="layout.head.welcome"/></small>
        <shiro:authenticated>
          <shiro:principal property="name"/>
        </shiro:authenticated>
        </span> <i class="icon-caret-down"></i> </a>
          <ul class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
            <li> <a href="${ctxbi}/layout/logout"> <i class="icon-off"></i> <spring:message code="layout.head.logout"/> </a> </li>
            <li class="divider"></li>
            <li> <a href="javascript:changeLocale('zh_CN')"> <i id="lang_zh" class="<spring:message code="layout.head.language.icon.zh"/>"></i>  中文 </a> </li>
            <li> <a href="javascript:changeLocale('en')"> <i id="lang_en" class="<spring:message code="layout.head.language.icon.en"/>"></i>  English </a> </li>
            <li class="divider"></li>
            <li> <a href="javascript:changeStyle('white')"> <i id="style_white" ></i><spring:message code="layout.style.theme.white"/> </a> </li>
            <li> <a href="javascript:changeStyle('black')"> <i id="style_black" ></i><spring:message code="layout.style.theme.black"/> </a> </li>
          </ul>
        </li>
      </ul>
      <!-- /.ace-nav -->  
    </div>
    <!-- /.navbar-header --> 
  </div>
  <!-- /.container --> 
</div>
<script type="text/javascript">
	var _bi_global_isLogin = true;
	function loginCheckStatus(){
		//console.log("begin:loginStatus check:_bi_global_isLogin:"+_bi_global_isLogin);
		if(_bi_global_isLogin){
			$.post("${ctxbi}/login/checkStatus",{},function(data){
				if(!data.isLogin){
					_bi_global_isLogin=false;
					bootbox.alert({
						message: "<span class='bigger-110'><spring:message code='layout.head.login.timeout'/></span>",
						callback:function(){
							window.location.reload();
						},
						buttons: 			
						{
							ok :
							{
								"label" : "<spring:message code='layout.head.login.timeout.ok'/>",
								"className" : "btn-sm btn-purple",
								"callback": function() {
									window.location.reload();
								}
							}
						}
					});
				}
			});
		}
		//console.log("end:loginStatus check:_bi_global_isLogin:"+_bi_global_isLogin);
	}
	function checkStyle(){
		var cookieStyle = getCookie("_change_style");
		if(cookieStyle=="black"){
			$("#style_black").attr("class","icon-circle");
			$("#style_white").attr("class","icon-circle-blank");
		}else{
			$("#style_white").attr("class","icon-circle");
			$("#style_black").attr("class","icon-circle-blank");
		}
	}
	$(function(){
		//检查用户是否登录
		//setInterval("loginCheckStatus();",5000);
		//检查cookie中的样式属性
		checkStyle();
	});
</script>
