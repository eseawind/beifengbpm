package com.beifengbpm.service;

import java.sql.SQLException;
import java.util.List;

import com.beifengbpm.entity.FlowRole;

public interface FlowRoleService {
	public void addRole(FlowRole role) throws SQLException;

	public void updateRole(FlowRole role)throws SQLException;

	public void deleteRole(FlowRole role)throws SQLException;

	public List<FlowRole> queryRoleList(String query, int start, int limit)throws SQLException;

	public int queryRoleListCount(String query)throws SQLException;

	public FlowRole queryRoleById(String flowroleId)throws SQLException;

	public List<FlowRole> queryAllRole()throws SQLException;

	public FlowRole queryByName(String flowrolename)throws SQLException;
}
