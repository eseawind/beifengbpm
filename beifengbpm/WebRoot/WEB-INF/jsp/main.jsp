<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>工作流中间件后台</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--EXTJS基础CSS  -->
	<link rel="stylesheet" type="text/css" href="/jscomponent/extjs/desktop/css/ext-all.css">
	<link rel="stylesheet" type="text/css" href="/jscomponent/extjs/desktop/css/desktop.css">  
  	
  	<!-- EXTJS基础JS -->
  	<script type="text/javascript" src="/jscomponent/extjs/ext-all-debug.js"></script>
	<script type="text/javascript" src="/jscomponent/extjs/ext-lang-zh_CN.js"></script>
  
  	<!-- 扩展JS -->
	<script type="text/javascript" src="/jscomponent/extjs/ux/form/SearchField.js"></script>
	
	<!-- core js -->
	<script type="text/javascript" src="./js/core/Module.js"></script>
	<script type="text/javascript" src="./js/core/Video.js"></script>
	<script type="text/javascript" src="./js/core/Wallpaper.js"></script>
	<script type="text/javascript" src="./js/core/FitAllLayout.js"></script>
	<script type="text/javascript" src="./js/core/StartMenu.js"></script>
	<script type="text/javascript" src="./js/core/TaskBar.js"></script>
	<script type="text/javascript" src="./js/core/ShortcutModel.js"></script>
	<script type="text/javascript" src="./js/core/Desktop.js"></script>
	<script type="text/javascript" src="./js/core/App.js"></script>
	<!-- module js -->
	<script type="text/javascript" src="./modules/BogusMenuModule.js"></script>
	<script type="text/javascript" src="./modules/BogusModule.js"></script>
	<script type="text/javascript" src="./modules/Notepad.js"></script>
	<script type="text/javascript" src="./modules/SystemStatus.js"></script>
	<script type="text/javascript" src="./modules/TabWindow.js"></script>
	<script type="text/javascript" src="./modules/VideoWindow.js"></script>
	<script type="text/javascript" src="./modules/WallpaperModel.js"></script>
	
	<!-- 自定义的module -->
	<script type="text/javascript" src="./modules/Department.js"></script>
	<script type="text/javascript" src="./modules/FlowRole.js"></script>
	<script type="text/javascript" src="./modules/Leave.js"></script>
	<script type="text/javascript" src="./modules/Todo.js"></script>
	<!-- setting js -->
	<script type="text/javascript" src="/jscomponent/extjs/desktop/Settings.js"></script>
	
	<script type="text/javascript">
		Ext.Loader.setConfig({enabled:true});
		Ext.Loader.setPath({
			'Ext.ux.desktop':'js',
			MyDesktop:''
		});
		Ext.require('MyDesktop.App');
		var myDesktopApp;
		Ext.onReady(function(){
			myDesktopApp=new MyDesktop.App();
		});
	</script>
	
  </head>
  
  <body>
    <input type="hidden" id="adminuserId" value="">
    <input type="hidden" id="adminrealname" value="${USER.flowusername }">
    <input type="hidden" id="flowuserId" value="${USER.flowuserId }">
    <input type="hidden" id="departmentId" value="2b5ecdd3-12a8-4356-9514-bb643074148c">
  </body>
</html>
