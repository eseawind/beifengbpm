package com.beifengbpm.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.beifengbpm.entity.FlowUser;

public class BaseController {
	public void writetoPage(HttpServletResponse response,String str){
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(str);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public FlowUser getUser(HttpServletRequest request){
		HttpSession session = request.getSession();
		return (FlowUser) session.getAttribute("USER");
	}
}
