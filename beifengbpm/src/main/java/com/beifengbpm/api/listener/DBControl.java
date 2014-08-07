package com.beifengbpm.api.listener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import oracle.net.ns.Communication;


public class DBControl {
	private Statement stmt;
	private Connection conn;
	private ResultSet rs;
	private static String url;
	private static String driver;
	private static String username;
	private static String password;
	static{
		if (null==url||url.equals("")) {
			ResourceBundle rb = ResourceBundle.getBundle("com.beifengbpm.api.listener.jdbc");
			url=rb.getString("url");
			driver=rb.getString("driver");
			username=rb.getString("username");
			password=rb.getString("password");
		}
	}
	public Connection getConn(){
		try {
			Class.forName(driver);
			conn= DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		return conn;
	}
	
	public int setData(String sql){
		int flag=0;
		try {
			if(null==conn||conn.isClosed()){
				conn=getConn();
			}
			stmt=conn.createStatement();
			flag=stmt.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public ResultSet getData(String sql){
		try {
			if (null==conn||conn.isClosed()) {
				conn=getConn();
			}
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public void close(){
		try {
			if (conn!=null||!conn.isClosed()) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		DBControl db=new DBControl();
		String sql="select * from workitem";
		ResultSet rs=db.getData(sql);
		try {
			while(rs.next()){
				System.out.println(rs.getString("workitemname"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

}
