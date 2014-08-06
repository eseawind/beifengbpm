package com.beifengbpm.api.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	
	
	//TODO  //根据流程实例ID获得已办列表
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

































