<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
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
<form action="queren.do" method="post">
	<input type="hidden" id="workitemId" value="${workitemId }" name="workitemId">
    <input type="hidden" id="id" value="${id }" name="id">
    <input type="hidden" id="processinsId" value="${processinsId }" name="processinsId">
    <input type="hidden" id="leaveId" value="${leave.leaveId }" name="leaveId">
    <input type="hidden" id="createuserId" value="${leave.createuserId }" name="createuserId">
    <table width="100%">
    	<tr>
    		<td>
    			<table width="80%" align="center" border="1">
    				<tr>
    					<td>请假事由：</td>
    					<td>${leave.leavename }</td>
    					<td>请假人：</td>
    					<td>${leave.createname }</td>
    				</tr>
    				<tr>
    					<td>开始时间：</td>
    					<td>${leave.starttime }</td>
    					<td>结束时间：</td>
    					<td>${leave.endtime }</td>
    				</tr>
    				<tr>
    					<td>请假天数：</td>
    					<td>${leave.day }</td>
    					<td>请假时间：</td>
    					<td>${leave.createtime }</td>
    				</tr>
    				<tr>
    					<td colspan="4"><input type="submit" name="option" value="确认"></td>
    				</tr>
    			</table>
    		</td>
    	</tr>
    	<tr>
    		<td>
    			<table width="80%" align="center" border="1">
    				<th>审批操作</th>
    				<th>审批节点</th>
    				<th>审批人</th>
    				<th>审批时间</th>
    				<th>审批意见</th>
    				<c:forEach items="${appList}" var="app">
    					<tr>
    						<td>${app.approvationoption }</td>
    						<td>${app.workitemname }</td>
    						<td>${app.approvationusername }</td>
    						<td>${app.approvationtime }</td>
    						<td>${app.approvation }</td>
    					</tr>
    				</c:forEach>
    			</table>
    		</td>
    	</tr>
    </table>
 </form>
    
  </body>
</html>
