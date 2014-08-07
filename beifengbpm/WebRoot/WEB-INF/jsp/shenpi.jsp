<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>确认请假</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  <form action="shenpido.do" method="post">
	<input type="hidden" id="workitemId" value="${workitemId }" name="workitemId">
    <input type="hidden" id="id" value="${id }" name="id">
    <input type="hidden" id="processinsId" value="${processinsId }" name="processinsId">
    <table width="100%">
    	<tr>
    		<td>
    			<table width="80%" align="center" border="1">
    				<tr>
    					<td colspan="4"><input type="submit" name="option" value="确认"></td>
    				</tr>
    			</table>
    		</td>
    	</tr>
    </table>
 </form>
  </body>
</html>
