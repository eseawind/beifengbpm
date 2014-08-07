package com.beifengbpm.api.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.net.ns.Communication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.beifengbpm.api.entity.HaveTodoTable;
import com.beifengbpm.api.entity.TodoTable;
import com.beifengbpm.api.entity.WorkItem;
import com.beifengbpm.api.service.TodoTableService;
import com.beifengbpm.api.service.WorkItemService;
import com.beifengbpm.controller.BaseController;
@Controller
public class TodoTableController extends BaseController {
	@Autowired
	private TodoTableService todoTableService;
	@Autowired
	private WorkItemService workItemService;
	@RequestMapping("/querytodotableforuser.do")
	public void querytodotableforuser(HttpServletRequest request,HttpServletResponse response){
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			int start = Integer.parseInt(request.getParameter("start"));
			int limit = Integer.parseInt(request.getParameter("limit"));
			String query=request.getParameter("query");
			String userId=getUser(request).getFlowuserId();
			String sysId =  request.getParameter("sysId");
			int totalCount= todoTableService.totalCountBySelf(userId, sysId, query);
			List<TodoTable> list=todoTableService.queryListBySelf(userId, sysId, query, start, limit);
			StringBuffer sb=new StringBuffer();
			sb.append("{'totalCount':'").append(totalCount).append("','todoinfos':[");
			for(TodoTable r:list){
				sb.append("{'id':'").append(r.getId()).append("','workitemId':'").append(r.getWorkitemId()).append("','modulename':'").append(r.getModulename()).append("','workitemname':'").append(r.getWorkitemname()).append("','processinsId':'").append(r.getProcessinsId()).append("','todostate':'").append(r.getTodostate()).append("','createtime':'").append(r.getCreatetime()).append("'},");
			}
			String s="";
			if(list.size()>0){
				s=sb.substring(0,sb.length()-1)+"]}";
			}else{
				s=sb+"]}";
			}
			PrintWriter out=response.getWriter();
			out.println(s);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/querytodotableforusertoplatform.do")
	public void querytodotableforusertoplatform(HttpServletRequest request,HttpServletResponse response){
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			int start = Integer.parseInt(request.getParameter("start"));
			int limit =Integer.parseInt(request.getParameter("limit"));
			String query = request.getParameter("query");
			String userId= request.getParameter("userId");
			String sysId=request.getParameter("sysId");
			int totalCount=todoTableService.totalCountBySelf(userId, sysId,query);
			List<TodoTable> list=todoTableService.queryListBySelf(userId, sysId,query, start, limit);
			StringBuffer sb=new StringBuffer();
			sb.append("{'totalCount':'").append(totalCount).append("','todoinfos':[");
			for(TodoTable r:list){
				sb.append("{'id':'").append(r.getId()).append("','workitemId':'").append(r.getWorkitemId()).append("','modulename':'").append(r.getModulename()).append("','workitemname':'").append(r.getWorkitemname()).append("','processinsId':'").append(r.getProcessinsId()).append("','todostate':'").append(r.getTodostate()).append("','createtime':'").append(r.getCreatetime()).append("'},");
			}
			String s="";
			if(list.size()>0){
				s=sb.substring(0,sb.length()-1)+"]}";
			}else{
				s=sb+"]}";
			}
			PrintWriter out=response.getWriter();
			out.println(s);
			out.flush();
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/todo.do")
	public String todo(HttpServletRequest request,HttpServletResponse response){
		String url="redirect:..";
		try{
			request.setCharacterEncoding("UTF-8");
			String id=request.getParameter("id");
			String workitemId=request.getParameter("workitemId");
			String processinsId=request.getParameter("processinsId");
			WorkItem item=workItemService.queryItemById(workitemId);
			url+=item.getFormId();
			url+="?id="+id+"&processinsId="+processinsId+"&workitemId="+workitemId;
		}catch(Exception e){
			e.printStackTrace();
		}
		return url;
	}
	
	@RequestMapping("/queryhavetodolist.do")
	public void queryhavetodolist(HttpServletRequest request,HttpServletResponse response){
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			int start = Integer.parseInt(request.getParameter("start"));
			int limit = Integer.parseInt(request.getParameter("limit"));
			String query=request.getParameter("query");
			String userId=getUser(request).getFlowuserId();
			int totalCount=todoTableService.totalCountforHaveTodo(userId, query);
			List<HaveTodoTable> list = todoTableService.queryListBySelfforHaveTodo(userId, query, start, limit);
			StringBuffer sb = new StringBuffer();
			sb.append("{'totalCount':'").append(totalCount).append("','havetodoinfos':[");
			for (HaveTodoTable r : list) {
				sb.append("{'havetodoId':'").append(r.getHavetodoId()).append("','workitemId':'").append(r.getWorkitemId()).append("','modulename':'").append(r.getModulename()).append("','workitemname':'").append(r.getWorkitemname()).append("','processinsId':'").append(r.getProcessinsId()).append("','todostate':'").append(r.getTodostate()).append("','processtime':'").append(r.getProcesstime()).append("'},");
			}
			String s="";
			if(list.size()>0){
				s=sb.substring(0,sb.length()-1)+"]}";
			}else{
				s=sb+"]}";
			}
			PrintWriter out=response.getWriter();
			out.println(s);
			out.flush();
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	
	@RequestMapping("/queryhavetodolisttoplatform.do")
	public void queryhavetodolisttoplatform(HttpServletRequest request,HttpServletResponse response){
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			int start = Integer.parseInt(request.getParameter("start"));
			int limit = Integer.parseInt(request.getParameter("limit"));
			String query=request.getParameter("query");
			String userId=request.getParameter("userId");
			int totalCount =todoTableService.totalCountforHaveTodo(userId, query);
			List<HaveTodoTable> list=todoTableService.queryListBySelfforHaveTodo(userId, query, start, limit);
			StringBuffer sb = new StringBuffer();
			sb.append("{'totalCount':'").append(totalCount).append("','havetodoinfos':[");
			for (HaveTodoTable r : list) {
				sb.append("{'havetodoId':'").append(r.getHavetodoId()).append("','workitemId':'").append(r.getWorkitemId()).append("','modulename':'").append(r.getModulename()).append("','workitemname':'").append(r.getWorkitemname()).append("','processinsId':'").append(r.getProcessinsId()).append("','todostate':'").append(r.getTodostate()).append("','processtime':'").append(r.getProcesstime()).append("'},");
			}
			String s="";
			if(list.size()>0){
				s=sb.substring(0,sb.length()-1)+"]}";
			}else{
				s=sb+"]}";
			}
			PrintWriter outPrintWriter =response.getWriter();
			outPrintWriter.println(s);
			outPrintWriter.flush();
			outPrintWriter.checkError();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//流程取回
	@RequestMapping("/retrieve.do")
	public void retrive(HttpServletResponse response,HttpServletRequest request){
		try{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			String havetodoId=request.getParameter("havetodoId");
			HaveTodoTable ht=todoTableService.queryHavetodoById(havetodoId);
			int flag=workItemService.retrieve(ht.getWorkitemId(), ht.getProcessinsId(), ht);
			if (flag==1) {
				writetoPage(response, "1");
			} else {
				writetoPage(response, "0");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
    //根据流程实例ID获得已办列表
	@RequestMapping("/findhavetodoList.do")
	public void findhavetodoList(HttpServletRequest request,HttpServletResponse response){
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			int start = Integer.parseInt(request.getParameter("start"));
			int limit = Integer.parseInt(request.getParameter("limit"));
			String processinsId = request.getParameter("processinsId");
			int totalCount = todoTableService.totalCountforHaveTodo(processinsId);
			List list = todoTableService.queryListBySelfforHaveTodo(processinsId, start, limit);
			StringBuffer sb  = new StringBuffer();
			sb.append("{'totalCount':'").append(totalCount).append("','havetodoinfos':[");
			for (int i = 0; i < list.size(); i++) {
				Object [] os = (Object[]) list.get(i);
				sb.append("{'workitemId':'").append(os[0]).append("','workitemname':'").append(os[1]).append("'},");
			}
			String s = "";
			if (list.size()>0) {
				s=sb.substring(0,sb.length()-1)+"]}";
				
			}else{
				s=sb+"]}";
			}
			PrintWriter out = response.getWriter();
			out.println(s);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//判断选择驳回的节点是什么节点
	@RequestMapping("/torejected.do")
	public void torejected(HttpServletRequest request,HttpServletResponse response){
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			String workitemId= request.getParameter("workitemId");
			WorkItem item = workItemService.queryItemById(workitemId);
			writetoPage(response, item.getWorkitemtype()+"");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//流程驳回
	@RequestMapping("/rejected.do")
	public void rejected(HttpServletRequest request,HttpServletResponse response){
		try {
			request.setCharacterEncoding("UTF-8");
			request.setCharacterEncoding("UTF-8");
			String workitemId = request.getParameter("workitemId");
			String processinsId=request.getParameter("processinsId");
			String userId = request.getParameter("userId");
			HaveTodoTable htodo = todoTableService.queryByProcessandWorkItem(userId, workitemId, processinsId);
			workItemService.rejected(htodo.getHavetodoId());
			writetoPage(response, "ok");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//根据流程实例ID和工作项ID来查询同步节点所有处理过的用户
	@RequestMapping("/queryhavetouserList.do")
	public void queryhavetouserList(HttpServletRequest request,HttpServletResponse response){
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			int start = Integer.parseInt(request.getParameter("start"));
			int limit = Integer.parseInt(request.getParameter("limit"));
			String processinsId = request.getParameter("processinsId");
			String workitemId= request.getParameter("workitemId");
			int totalCount = todoTableService.totalhavetodoUserList(processinsId, workitemId);
			List list = todoTableService.querthavetodoUserList(processinsId, workitemId, start, limit);
			StringBuffer sb = new StringBuffer();
			sb.append("{'totalCount':'").append(totalCount).append("','havetodousers':[");
			for (int i = 0; i < list.size(); i++) {
				Object [] os = (Object[]) list.get(i);
				sb.append("{'userId':'").append(os[0]).append("','username':'").append(os[1]).append("'},");
			}
			String s = "";
			if (list.size()>0) {
				s=sb.substring(0,sb.length()-1)+"]}";
			}else {
				s=sb+"]}";
			}
			PrintWriter out = response.getWriter();
			out.print(s);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//流程转办
	@RequestMapping("/according.do")
	public void according(HttpServletRequest request,HttpServletResponse response){
		try{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			String id=request.getParameter("todoid");
			String userId=request.getParameter("userId");
			int flag=workItemService.according(id, userId);
			writetoPage(response, flag+"");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//根据流程实例ID获取一个待办ID
	@RequestMapping("/querytodoidbyprocess.do")
	public void querytodoidbyprocess(HttpServletRequest request,HttpServletResponse response){
		try{
			request.setCharacterEncoding("UTF-8");
			String processinsId=request.getParameter("processinsId");
			TodoTable todo=todoTableService.queryByProcess(processinsId);
			writetoPage(response, todo.getId());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	//沟通
	@RequestMapping("/communicationtoUser.do")
	public void communicationtoUser(HttpServletRequest request,HttpServletResponse response){
		try{
			request.setCharacterEncoding("UTF-8");
			//修改当前用户的待办状态
			String todoId=request.getParameter("todoId");
			TodoTable todo = todoTableService.queryTodoById(todoId);
			//沟通中的状态
			todo.setTodostate(3);
			todoTableService.updateTodoInfo(todo);
			//接受沟通的人的ID
			String userId=request.getParameter("userId");
			TodoTable todo1=new TodoTable();
			todo1.setModulename(todo.getModulename());
			todo1.setProcessinsId(todo.getProcessinsId());
			todo1.setTodostate(4);
			todo1.setUserId(userId);
			todo1.setWorkitemId(todo.getWorkitemId());
			todo1.setWorkitemname(todo.getWorkitemname());
			todo1.setCommunicationtodoId(todo.getId());
			todoTableService.addTodoInfo(todo1);
			writetoPage(response, "communication");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/communicationback.do")
	public void communicationback(HttpServletRequest request,HttpServletResponse response){
		try {
			request.setCharacterEncoding("UTF-8");
			String communicationtodoId="";
			//删除沟通人的沟通待办
			String todoId = request.getParameter("todoId");
			TodoTable todo = todoTableService.queryTodoById(todoId);
			communicationtodoId=todo.getCommunicationtodoId();
			todoTableService.deleteTodo(todo);
			//更新原来的待办状态
			todo = todoTableService.queryTodoById(communicationtodoId);
			todo.setTodostate(2);
			todoTableService.updateTodoInfo(todo);
			writetoPage(response, "communication");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//根据待办ID获取代办的状态
	@RequestMapping("/querytodostatebyId.do")
	public void querytodostatebyId(HttpServletRequest request,HttpServletResponse response){
		try{
			request.setCharacterEncoding("UTF-8");
			String todoId=request.getParameter("todoId");
			TodoTable todo=todoTableService.queryTodoById(todoId);
			writetoPage(response, todo.getTodostate()+"");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

































