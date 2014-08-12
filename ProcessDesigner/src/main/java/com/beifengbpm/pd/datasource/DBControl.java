package com.beifengbpm.pd.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class DBControl {

	private Statement stmt;
	private Connection conn;
	private ResultSet rs;
	private static String url;
	private static String driver;
	private static String username;
	private static String password;
	
	static{
		if(null==url || url.equals("")){
			ResourceBundle rb=ResourceBundle.getBundle("com.beifengbpm.pd.datasource.jdbc");
			url=rb.getString("url");
			driver=rb.getString("driver");
			username=rb.getString("username");
			password=rb.getString("password");
		}
	}
	
	public Connection getConn(){
		try{
			Class.forName(driver);
			conn=DriverManager.getConnection(url,username,password );
		}catch(Exception e){
			System.out.println(e.getMessage());
			return null;
		}
		return conn;
	}

	public int setData(String sql){
		int flag=0;
		try {
			if(null==conn || conn.isClosed()){
				conn=getConn();
			}
			stmt=conn.createStatement();
			flag=stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
	
	public ResultSet getData(String sql){
		try {
			if(null==conn || conn.isClosed()){
				conn=getConn();
			}
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	public void close(){
		try {
			if(conn!=null || !conn.isClosed()){
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		DBControl db=new DBControl();
		String sql="create user bctest identified by p8888 default tablespace USERS";
		String sql1="grant connect,resource to bctest";
		db.setData(sql);
		db.setData(sql1);
		db.close();
	}
}
