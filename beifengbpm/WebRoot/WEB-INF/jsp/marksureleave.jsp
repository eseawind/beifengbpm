<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'marksureleave.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="/jscomponent/extjs/ext-all-debug.js"></script>
	<script type="text/javascript">
		function tijiao(){
			var id=document.getElementById('id').value;
			var workitemId=document.getElementById('workitemId').value;
			var processinsId=document.getElementById('processinsId').value;
			var day=document.getElementById('day').value;
			alert(day)
			Ext.Ajax.request({
				url:'/beifengbpm/makesure.do'
				method:'POST',
				params:{
					id:id,
					workitemId:workitemId,
					processinsId:processinsId,
					day:day
				},
				success:function(response,options){
					if(response.responseText=='ok'){
						Ext.Msg.alert('成功','已经确认，请等待审核！');
					}
				}
			});
		}
	</script>
  </head>
  
  <body>
<form action="makesure.do" method="post">
	<input type="hidden"id="workitemId" value="${workitemId }" name="workitemId">
    <input type="hidden"id="id" value="${id }" name="id">
    <input type="hidden"id="processinsId" value="${processinsId }" name="processinsId">
    <input type="hidden" id="day" value="${leave.day }" name="day">
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
    					<td colspan="2"><input type="submit" value="确认"></td>
    					<td colspan="2"><input type="button" value="取消"></td>
    				</tr>
    			</table>
    		</td>
    	</tr>
    </table>
 </form>
  </body>
</html>
