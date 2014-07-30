package com.beifengbpm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.beifengbpm.entity.FlowRole;
import com.beifengbpm.entity.FlowUser;
import com.beifengbpm.service.FlowRoleService;
import com.beifengbpm.service.FlowUserService;
import com.beifengbpm.tools.StringUtils;
@Controller
public class FlowUserController {

	@Autowired
	private FlowUserService flowuserService;

	@Autowired
	private FlowRoleService flowroleService;

	@RequestMapping("/addflowuser.do")
	public void addflowuser(HttpServletResponse response,
			HttpServletRequest request) {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			FlowUser user = new FlowUser();
			user.setDepartmentId(request.getParameter("departmentId"));
			user.setFlowloginname(request.getParameter("flowloginname"));
			user.setFlowroleId(request.getParameter("flowroleId"));
			user.setFlowusername(request.getParameter("flowusername"));
			user.setFlowuserpassword("8888");
			flowuserService.addUser(user);
			PrintWriter out = response.getWriter();
			out.print("addok");
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

	@RequestMapping("/queryUserListByDepartment.do")
	public void queryUserListByDepartment(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			String departmentId = request.getParameter("departmentId");
			int start = Integer.parseInt(request.getParameter("start"));
			int limit = Integer.parseInt(request.getParameter("limint"));
			String query = request.getParameter("query");
			if (StringUtils.isNotEmpty(query)) {
				query = new String(query.getBytes("ISO-8859-1"), "UTF-8");
			}
			int totalCount = flowuserService
					.queryUserCount(query, departmentId);
			List list = flowuserService.queryUserList(query, departmentId,
					start, limit);
			StringBuffer sb = new StringBuffer();
			sb.append("{totalCount':'").append(totalCount)
					.append("','users':[");
			for (int i = 0; i < list.size(); i++) {
				Object[] os = (Object[]) list.get(i);
				sb.append("{'flowuserId':'").append(os[0])
						.append("','flowusername':'").append(os[3])
						.append("','flowloginname':'").append(os[4])
						.append("','departmentname':'").append(os[7])
						.append("','flowrolename':'")
						.append(os[11] == null ? "" : os[11])
						.append("','flowroleId':'")
						.append(os[2] == null ? "" : os[2]).append("'},");
				
			}
			String s = "";
			if(list.size()>0){
				s=sb.substring(0,sb.length()-1)+"]}";
			}else{
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
	@RequestMapping("/deleteusers.do")
	public void deleusers(HttpServletRequest request,HttpServletResponse response){
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			String sql = "DELETE FROM FLOWUSER WHERE FLOWUSERID IN ("+request.getParameter("ids")+")";
			flowuserService.deleteUserBySQL(sql);
			PrintWriter out = response.getWriter();
			out.print("deleteok");
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
	@RequestMapping("/updateuser.do")
	public void updateuser(HttpServletResponse response,HttpServletRequest request){
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			FlowUser user = flowuserService.queryUserById(request.getParameter("flowuserId"));
			user.setFlowloginname(request.getParameter("flowloginname"));
			user.setFlowusername(request.getParameter("flowusername"));
			String flowrolename = request.getParameter("flowrolename");
			if (StringUtils.isNotEmpty(flowrolename)) {
				FlowRole role = flowroleService.queryByName(flowrolename);
				if (role!=null) {
					user.setFlowroleId(role.getFlowroleId());
				}
			}
			flowuserService.updateUser(user);
			PrintWriter out = response.getWriter();
			out.print("updateok");
			out.flush();
			out.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping("/updateUsersDepart.do")
	public void updateUsersDepart(HttpServletRequest request,HttpServletResponse response){
		try{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			String id = request.getParameter("id");
			String departmentid = request.getParameter("droptarget");
			String sql = "UPDATE FLOWUSER SET DEPARTMENTID='"+departmentid+"' WHERE FLOWUSERID IN ("+id+")";
			flowuserService.deleteUserBySQL(sql);
			PrintWriter out  = response.getWriter();
			out.print("updateok");
			out.flush();
			out.close();
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@RequestMapping("/updateuserRole.do")
	public void updateuserRole(HttpServletResponse response,HttpServletRequest request){
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			String ids = request.getParameter("ids");
			String flowroleId = request.getParameter("flowroleId");
			String sql = "UPDATE FLOWUSER SET FLOWROLEID='"+flowroleId+"'WHERE FLOWUSERID IN ("+ids+")";
			flowuserService.deleteUserBySQL(sql);
			PrintWriter out  = response.getWriter();
			out.print("updateok");
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
	@RequestMapping("/resetRole.do")
	public void resetRole(HttpServletResponse response,HttpServletRequest request){
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			String ids = request.getParameter("ids");
			String sql = "UPDATE FLOWUSER SET FLOWROLEID=NULL WHERE FLOWUSERID IN ("+ids+")";
			flowuserService.deleteUserBySQL(sql);
			PrintWriter out=response.getWriter();
			out.print("updateok");
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
	@RequestMapping("islogin.do")
	public String islogin(ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		String result = "";
		if (session.getAttribute("USER") != null) {
			model.put("USER", session.getAttribute("USER"));
			result = "main";
		} else {
			result = "login";
		}
		return result;
	}

	@RequestMapping("login.do")
	public String login(HttpServletResponse reponse, HttpServletRequest request) {
		try {
			request.setCharacterEncoding("UTF-8");
			String flowloginname = request.getParameter("flowloginname");
			String flowuserpassword = request.getParameter("flowuserpassword");
			FlowUser user = flowuserService.loginUser(flowloginname,
					flowuserpassword);
			if (user != null) {
				HttpSession session = request.getSession();
				session.setAttribute("USER", user);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "redirect:/islogin.do";
	}

}
