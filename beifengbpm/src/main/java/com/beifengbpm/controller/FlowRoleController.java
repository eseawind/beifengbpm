package com.beifengbpm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.beifengbpm.entity.FlowRole;
import com.beifengbpm.service.FlowRoleService;
import com.beifengbpm.tools.StringUtils;

@Controller
public class FlowRoleController {
	@Autowired
	private FlowRoleService flowroleService;
	
	@RequestMapping("/queryRoleList.do")
	public void queryRoleList(HttpServletRequest request,HttpServletResponse response){
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			int start = Integer.parseInt(request.getParameter("start"));
			int limit = Integer.parseInt(request.getParameter("limit"));
			String query = request.getParameter("query");
			if (StringUtils.isNotEmpty(query)) {
				query=new String(query.getBytes("ISO-8859-1"),"utf-8");
				}
			int totalCount = flowroleService.queryRoleListCount(query);
			List<FlowRole> list = flowroleService.queryRoleList(query, start, limit);
			StringBuffer sb=new StringBuffer();
			sb.append("{'totalCount':'").append(totalCount).append("','roles':[");
			for (FlowRole r : list) {
				sb.append("{'flowroleId':'").append(r.getFlowroleId()).append("','flowrolename':'").append(r.getFlowrolename()).append("','flowrolemark':'").append(r.getFlowroleremark()).append("'},");
			}
			String s= "";
			if (list.size()>0) {
				s=sb.substring(0,sb.length()-1)+"]}";
			} else {
				s=sb+"]}";
			}
			PrintWriter out=response.getWriter();
			out.println(s);
			out.flush();
			out.close();		
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/addrole.do")
	public void addrole(HttpServletRequest request,HttpServletResponse response){
		try{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			FlowRole role = new FlowRole();
			role.setFlowrolename(request.getParameter("flowrolename"));
			role.setFlowroleremark(request.getParameter("flowroleremark").replaceAll("\n", "<br>"));
			flowroleService.addRole(role);
			PrintWriter out=response.getWriter();
			out.print("addok");
			out.flush();
			out.close();
		}catch(Exception exception){
			exception.printStackTrace();
		}
	}
	
	@RequestMapping("/updaterole.do")
	public void updaterole(HttpServletRequest request,HttpServletResponse response){
		try{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			FlowRole role=new FlowRole();
			role.setFlowroleId(request.getParameter("flowroleId"));
			role.setFlowrolename(request.getParameter("flowrolename"));
			role.setFlowroleremark(request.getParameter("flowroleremark").replaceAll("\n", "<br>"));
			flowroleService.updateRole(role);
			PrintWriter out=response.getWriter();
			out.print("updateok");
			out.flush();
			out.close();
		}catch(Exception exception){
			exception.printStackTrace();
		}
	}
	
	@RequestMapping("/deleteRole.do")
	public void deleteRole(HttpServletRequest request,HttpServletResponse response){
		try{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			FlowRole role=new FlowRole();
			role.setFlowroleId(request.getParameter("flowroleId"));
			flowroleService.deleteRole(role);
			PrintWriter out=response.getWriter();
			out.print("deleteok");
			out.flush();
			out.close();
		}catch(Exception exception){
			exception.printStackTrace();
		}
	}
	
	@RequestMapping("/queryAllRole.do")
	public void queryAllRole(HttpServletRequest request,HttpServletResponse response){
		try{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			List<FlowRole> list = flowroleService.queryAllRole();
			int totalCount = flowroleService.queryRoleListCount(null);
			StringBuffer sb = new StringBuffer();
			sb.append("{'totalCount':'").append(totalCount).append("','roles':[");
			for (int i = 0; i < list.size(); i++) {
				FlowRole role = list.get(i);
				sb.append("{'flowroleId':'").append(role.getFlowroleId()).append("','flowrolename':'").append(role.getFlowrolename()).append("'}");
				if(i<list.size()-1){
					sb.append(",");
				}
			}
			String s=sb+"]}";
			PrintWriter out=response.getWriter();
			out.print(s);
			out.flush();
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
}
