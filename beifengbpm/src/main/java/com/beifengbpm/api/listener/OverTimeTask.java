package com.beifengbpm.api.listener;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

public class OverTimeTask extends TimerTask {

	private static boolean isRunning = false;
	@Override
	public void run() {
		if(!isRunning){
			isRunning=!isRunning;
			DBControl db=new DBControl();
			String sql = "SELECT * FROM TODOTABLE WHERE OVERTIMESTATE=0 AND OVERTIMETIME IS NOT NULL";
			ResultSet rs = db.getData(sql);
			try {
				while (rs.next()) {
					String overtimetime = rs.getString("OVERTIME");
					Date now = new Date();
					SimpleDateFormat  sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date d2=sdf.parse(overtimetime);
					boolean flag=now.after(d2);
					System.out.println("=flag================="+flag);
					int result = now.compareTo(d2);
					if(flag){
						//超时了
						System.out.println("流程超时");
						String id=rs.getString("ID");
						String updatesql="UPDATE TODOTABLE SET OVERTIMESTATE=1 WHERE ID='"+id+"'";
						DBControl db2=new DBControl();
						db2.setData(updatesql);
						db2.close();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				db.close();
				isRunning=!isRunning;
			}
		}
	}

}
