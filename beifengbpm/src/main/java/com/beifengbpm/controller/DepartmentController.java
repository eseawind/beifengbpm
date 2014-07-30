package com.beifengbpm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.beifengbpm.entity.Department;
import com.beifengbpm.entity.FlowUser;
import com.beifengbpm.service.DepartmentService;
import com.beifengbpm.service.FlowUserService;
import com.beifengbpm.tools.StringUtils;
import com.beifengbpm.vo.DepartmentNode;
import com.beifengbpm.vo.DepartmentUserNode;
@Controller
public class DepartmentController {
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private FlowUserService flowUserService;

	@RequestMapping("/adddepartment.do")
	public void adddepartment(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
			Department depart = new Department();
			depart.setDepartmentId(StringUtils.getkeys());
			depart.setDepartmentname(request.getParameter("departmentname"));
			String parentId = request.getParameter("parentId").trim();
			depart.setParentId(parentId);
			depart.setCreatetime(new Date());
			departmentService.addDepartment(depart);
			response.setCharacterEncoding("UTF-8");
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

	@RequestMapping("/queryDeparttree.do")
	public void queryDeparttree(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			List<Department> list = departmentService.queryAllDeparts();
			List<DepartmentNode> nodelist = new ArrayList<>();
			for (Department d : list) {
				if (null == d.getParentId()) {
					DepartmentNode node = new DepartmentNode();
					node.setId("root");
					node.setText(d.getDepartmentname());
					node.setCls("folder");
					nodelist.add(node);
					list.remove(d);
					break;
				}
			}
			nodelist = queryDepartmentTree(nodelist, list);
			JSONArray json = JSONArray.fromObject(nodelist);
			PrintWriter out = response.getWriter();
			out.println(json.toString());
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

	private List<DepartmentNode> queryDepartmentTree(
			List<DepartmentNode> nodelist, List<Department> list) {
		for (int i = 0; i < nodelist.size(); i++) {
			DepartmentNode node = nodelist.get(i);
			List<DepartmentNode> children = new ArrayList<DepartmentNode>();
			for (int j = 0; j < list.size(); j++) {
				Department d = list.get(j);
				if (node.getId().trim().equals(d.getParentId())) {
					DepartmentNode dn = new DepartmentNode();
					dn.setId(d.getDepartmentId());
					dn.setText(d.getDepartmentname());
					dn.setCls("folder");
					children.add(dn);
					queryDepartmentTree(children, list);
				}
			}
			node.setChildren(children);
		}
		return nodelist;
	}

	@RequestMapping("/updatedepartment.do")
	public void updatedepartment(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			Department d = departmentService.queryDepartmentById(request
					.getParameter("parentId"));
			d.setDepartmentname(request.getParameter("departmentname"));
			departmentService.updateDepartment(d);
			PrintWriter out = response.getWriter();
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

	@RequestMapping("/deleteDepartment.do")
	public void deleteDepartment(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			String result = "";
			String parentId = request.getParameter("id");
			if (departmentService.ishaveChildrenDepartment(parentId)) {
				result = "deletemore";
			} else {
				Department depart = new Department();
				depart.setDepartmentId(parentId);
				departmentService.deleteDepartment(depart);
				result = "deleteok";
			}
			PrintWriter out = response.getWriter();
			out.print(result);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/queryDepartandUser.do")
	public void queryDepartandUser(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			List<Department> list = departmentService.queryAllDeparts();
			List<DepartmentUserNode> nodelist = new ArrayList<DepartmentUserNode>();
			for (Department d : list) {
				if (null == d.getParentId()) {
					DepartmentUserNode node = new DepartmentUserNode();
					node.setId("root");
					node.setText(d.getDepartmentname());
					node.setCls("floder");
					nodelist.add(node);
					list.remove(d);
					break;
				}
			}
			nodelist = queryDepartmentUserTree(nodelist, list);
			JSONArray json = JSONArray.fromObject(nodelist);
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<DepartmentUserNode> queryDepartmentUserTree(
			List<DepartmentUserNode> nodelist, List<Department> list)
			throws SQLException {
		for (int i = 0; i < nodelist.size(); i++) {
			DepartmentUserNode node = nodelist.get(i);
			List<DepartmentUserNode> children = new ArrayList<DepartmentUserNode>();
			for (int j = 0; j < list.size(); j++) {
				Department d = list.get(j);
				if (node.getId().trim().equals(d.getParentId())) {
					DepartmentUserNode dn = new DepartmentUserNode();
					dn.setId(d.getDepartmentId());
					dn.setText(d.getDepartmentname());
					dn.setCls("floder");
					children.add(dn);
					queryDepartmentUserTree(children, list);
				}
			}
			List<FlowUser> userlist = flowUserService
					.queryAllUserByDepartment(node.getId());
			for (FlowUser user : userlist) {
				DepartmentUserNode dun = new DepartmentUserNode();
				dun.setId(user.getFlowroleId());
				dun.setText(user.getFlowusername());
				dun.setLeaf(true);
				children.add(dun);
			}
			node.setChildren(children);
		}
		return nodelist;
	}

}
