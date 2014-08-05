package com.beifengbpm.business.controller;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


import com.beifengbpm.api.entity.FlowModel;
import com.beifengbpm.api.entity.ProcessIns;
import com.beifengbpm.api.entity.TodoTable;
import com.beifengbpm.api.entity.WorkItem;
import com.beifengbpm.api.service.FlowModelService;
import com.beifengbpm.api.service.ProcessInsService;
import com.beifengbpm.api.service.TodoTableService;
import com.beifengbpm.api.service.WorkItemService;
import com.beifengbpm.business.entity.Approvation;
import com.beifengbpm.business.entity.Leave;
import com.beifengbpm.business.service.ApprovationService;
import com.beifengbpm.business.service.LeaveService;
import com.beifengbpm.controller.BaseController;
import com.beifengbpm.entity.FlowRole;
import com.beifengbpm.entity.FlowUser;
import com.beifengbpm.tools.StringUtils;

@Controller
public class LeaveController extends BaseController {
	@Autowired
	private LeaveService leaveService;

	@Autowired
	private FlowModelService flowmodelService;

	@Autowired
	private WorkItemService workitemService;

	@Autowired
	private ProcessInsService processinsService;

	@Autowired
	private TodoTableService todotableService;

	@Autowired
	private ApprovationService approvationService;
	
	
	@RequestMapping("queryLeaveList.do")
	public void queryLeaveList(HttpServletRequest request,HttpServletResponse response){
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			int start = Integer.parseInt(request.getParameter("start"));
			int limit = Integer.parseInt(request.getParameter("limint"));
			String createuserID=getUser(request).getFlowroleId();
			String query=request.getParameter("query");
			int totalCount=leaveService.totalCount(createuserID, query);
			List<Leave> list=leaveService.queryListByPage(createuserID, query, start, limit);
			StringBuffer sb=new StringBuffer();
			sb.append("{'totalCount':'").append(totalCount).append("','leaveindos':[");
			for (Leave r : list) {
				sb.append("{'leaveId':'").append(r.getLeaveId()).append("','leavename':'").append(r.getLeavename()).append("','starttime':'").append(r.getStarttime()).append("','endtime':'").append(r.getEndtime()).append("','day':'").append(r.getDay()).append("','createtime':'").append(r.getCreatetime()).append("','createname':'").append(r.getCreatename()).append("','processinsId':'").append(r.getProcessinsId()).append("','leavestate':'").append(r.getLeavestate()).append("'},");
			}
			String s="";
			if (list.size()>0) {
				s=sb.substring(0,sb.length()-1)+"]}";
			}
			PrintWriter out=response.getWriter();
			out.println(s);
			out.flush();
			out.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	@RequestMapping("/startLeave.do")
	public void startLeave(HttpServletRequest request,HttpServletResponse response){
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			FlowUser user=getUser(request);
			String leavename = request.getParameter("leavename");
			String starttime = request.getParameter("starttime");
			String endtime=request.getParameter("endtime");
			int day = Integer.parseInt(request.getParameter("day"));
			//启动一个流程
			Map dataMap=new HashMap();
			dataMap.put("user", user.getFlowuserId());
			dataMap.put("day",""+day);
			dataMap.put("createusername", user.getFlowusername());
			String processinsId= workitemService.startProcess(LeaveService.LEAVEMODELID, dataMap, "common");
			Leave leave = new Leave();
			leave.setLeavename(leavename);
			leave.setStarttime(starttime);
			leave.setEndtime(endtime);
			leave.setDay(day);
			leave.setProcessinsId(processinsId);
			leave.setCreatename(user.getFlowusername());
			leave.setCreateuserId(user.getFlowuserId());
			leave.setCreatename(StringUtils.parseDateToString("yyyy-MM-dd HH:mm:ss", new Date()));
			leave.setLeavestate("正在审批中");
			leaveService.addLeaveInfo(leave);
			writetoPage(response, "startok");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//流程启动的方法
	public String startProcess(Map dataMap) throws SQLException{
		FlowModel model = flowmodelService.queryById(LeaveService.LEAVEMODELID);
		WorkItem startItem=workitemService.queryitemListByType(model.getFlowmodelId(),0).get(0);
		List<WorkItem> seconditemList=workitemService.queryListByprocess(startItem.getToId());
		//根据参数来判断分支对应的是哪一个节点
		WorkItem item = workitemService.returnitemByCondition(seconditemList, dataMap);
		String processinsId = StringUtils.getkeys();
		ProcessIns process=new ProcessIns();
		process.setProcessinsId(processinsId);
		process.setFlowmodelId(LeaveService.LEAVEMODELID);
		process.setWorkitemId(item.getWorkitemId());
		//插入流程实例数据
		processinsService.addProcess(process);
		//插入代办数据
		//参与者变量名称
		String key=item.getWorkitemuserId();
		//参与者变量的值
		String[] users = dataMap.get(key).toString().split("\\+");
		for (int i = 0; i < users.length; i++) {
			TodoTable todo = new TodoTable();
			todo.setProcessinsId(processinsId);
			todo.setModulename("请假模块");
			todo.setTodostate(1);
			todo.setUserId(users[i]);
			todo.setWorkitemId(item.getWorkitemId());
			todo.setWorkitemname(item.getWorkitemname());
			todo.setCreatetime((StringUtils.parseDateToString("yyyy-MM-dd HH:mm:ss", new Date())));
			todotableService.addTodoInfo(todo);
		}
		return processinsId;
	
	}
	
	//TODO 去申请人页面
	
	//TODO 确认请假
	
	//TODO 到组长审批页面
	
	//TODO 组长审批
	
	//TODO 到确认页面
	
	
	//TODO 确认
	
	
	
	

}
