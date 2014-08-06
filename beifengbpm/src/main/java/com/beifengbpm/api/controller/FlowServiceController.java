package com.beifengbpm.api.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sun.org.mozilla.javascript.internal.ast.WithStatement;

import com.beifengbpm.api.entity.ProcessIns;
import com.beifengbpm.api.entity.WorkItem;
import com.beifengbpm.api.service.ProcessInsService;
import com.beifengbpm.api.service.TodoTableService;
import com.beifengbpm.api.service.WorkItemService;
import com.beifengbpm.controller.BaseController;
import com.beifengbpm.entity.FlowUser;
import com.beifengbpm.service.FlowUserService;
import com.sun.corba.se.impl.ior.WireObjectKeyTemplate;
@Controller
public class FlowServiceController extends BaseController {

	@Autowired
	private WorkItemService workItemService;
	@Autowired
	private FlowUserService flowUserService;
	@Autowired
	private ProcessInsService processInsService;
	@Autowired
	private TodoTableService todoTableService;
	
	@RequestMapping("/startProcess.do")
	public void startProcess(HttpServletRequest request,HttpServletResponse response){
		try {
			request.setCharacterEncoding("UTF-8");
			String modelId=request.getParameter("modelId");
			String data = request.getParameter("data");
			Map dataMap = new HashMap();
			String [] datas=data.split("##");
			for (int i = 0; i < datas.length; i++) {
				String [] s = datas[i].split("is");
				if (s.length>1) {
					dataMap.put(s[0], s[1]);
				}
			}
			String sysId = request.getParameter("sysId");
			String processinsId = workItemService.startProcess(modelId, dataMap, sysId);
			writetoPage(response, processinsId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/takeusertoFlow.do")
	public void tabkeusertoFlow(HttpServletRequest request,HttpServletResponse response){
		try {
			request.setCharacterEncoding("UTF-8");
			FlowUser user=new FlowUser();
			user.setFlowuserId(request.getParameter("userId"));
			user.setFlowloginname(request.getParameter("loginname"));
			user.setFlowusername(request.getParameter("username"));
			flowUserService.addUser(user);
			writetoPage(response, user.getFlowuserId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/completeworkitem.do")
	public void completeworkitem(HttpServletRequest request,HttpServletResponse response){
		try {
			request.setCharacterEncoding("UTF-8");
			String userId = request.getParameter("userId");
			String flowmodelname = request.getParameter("flowmodelname");
			String modelId = request.getParameter("modelId");
			String todoid  = request.getParameter("todoid");
			String workitemId=request.getParameter("workitemId");
			String processinsId=request.getParameter("processinsId");
			String data=request.getParameter("data");
			Map datamap=new HashMap();
			String [] datas=data.split("##");
			for (int i = 0; i < datas.length; i++) {
				String [] s= datas[i].split("is");
				if(s.length>1){
					datamap.put(s[0], s[1]);
				}
			}
			String sysId=request.getParameter("sysId");
			workItemService.completeworkitem(userId, flowmodelname, modelId, todoid, workitemId, processinsId, datamap,sysId);
			writetoPage(response, "complete");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/queryworkitem.do")
	public void queryworkitem(HttpServletRequest request,HttpServletResponse response){
		try {
			request.setCharacterEncoding("UTF-8");
			String workitemId=request.getParameter("workitemId");
			WorkItem item= workItemService.queryItemById(workitemId);
			String s=item.getWorkitemId()+"##"+item.getWorkitemname()+"##"+item.getWorkitemtype()+"##"+item.getFormId();
			writetoPage(response, s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/flowredgreen.do")
	public void flowredgreen(HttpServletRequest request,HttpServletResponse response){
		try {
			request.setCharacterEncoding("UTF-8");
			String processinsId=request.getParameter("processinsId");
			String data = workItemService.flowredgreen(processinsId);
			writetoPage(response, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/queryallprocess.do")
	public void queryallprocess(HttpServletRequest request,HttpServletResponse response){
		try {
			request.setCharacterEncoding("UTF-8");
			String query= request.getParameter("query");
			int start = Integer.parseInt(request.getParameter("start"));
			int limit = Integer.parseInt(request.getParameter("limit"));
			int totalCount= processInsService.totalAllProcess(query);
			List list = processInsService.queryAllProcess(query, start, limit);
			StringBuffer sb= new StringBuffer();
			sb.append("{'totalCount':'").append(totalCount).append("','process':[");
			for (int i = 0; i < list.size(); i++) {
				Object [] os = (Object [])list.get(i);
				ProcessIns r = (ProcessIns) os[0];
				WorkItem w=(WorkItem) os[1];
				sb.append("{'processinsId':'").append(r.getProcessinsId()).append("','businessmodule':'").append(r.getBusinessmodule()).append("','createusername':'").append(r.getCreateusername()).append("','workitemId':'").append(r.getWorkitemId()).append("','workitemname':'").append(w.getWorkitemname()).append("'},");
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
	
	
	@RequestMapping("/signFlow.do")
	public void signFlow(HttpServletRequest request,HttpServletResponse response){
		try{
			request.setCharacterEncoding("UTF-8");
			String id=request.getParameter("todoid");
			todoTableService.signFlow(id);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
