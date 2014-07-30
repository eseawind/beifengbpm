package com.beifengbpm.service;

import java.sql.SQLException;
import java.util.List;

import com.beifengbpm.entity.FlowUser;

public interface FlowUserService {
	public void addUser(FlowUser user) throws SQLException;

	public void updateUser(FlowUser user) throws SQLException;

	public void deleteUser(FlowUser user) throws SQLException;

	public FlowUser queryUserById(String flowuserId) throws SQLException;

	public int queryUserCount(String query, String departmentId)throws SQLException;

	public List queryUserList(String query, String departmentId, int start,int limit) throws SQLException;

	public int queryUserCountByRole(String query, String flowroleId)throws SQLException;

	public List<FlowUser> queryUserListByRole(String query, String flowroleId,int start, int limit) throws SQLException;

	public void deleteUserBySQL(String sql) throws SQLException;

	public List<FlowUser> queryAllUserByDepartment(String departmentId)throws SQLException;

	public FlowUser loginUser(String flowloginname, String flowuserpassword)throws SQLException;

}
