package com.beifengbpm.business.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.beifengbpm.business.entity.Leave;
import com.beifengbpm.tools.StringUtils;

public interface LeaveService {
	// 写死了
public final static String LEAVEMODELID="8cdb4c34-8831-44ac-87e3-78225e1328ef";
	
	public void addLeaveInfo(Leave leave) throws SQLException;
	
	public void updateLeaveInfo(Leave leave) throws SQLException;
	
	public Leave querybyId(String leaveId) throws SQLException;
	
	public List<Leave> queryListByUser(String createuserId) throws SQLException;
	
	public List<Leave> queryListByPage(String createuserId,String query,int start,int limit) throws Exception;
	
	public int totalCount(String createuserId,String query) throws Exception;
	
	public Leave queryByProcessInsId(String processinsId) throws SQLException;

}
