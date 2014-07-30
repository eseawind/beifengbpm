package com.beifengbpm.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.beifengbpm.dao.CommonDao;
import com.beifengbpm.entity.FlowRole;
import com.beifengbpm.service.FlowRoleService;
import com.beifengbpm.tools.StringUtils;

public class FlowRoleServiceImpl implements FlowRoleService{
	private CommonDao commonDao;

	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}

	@Override
	public void addRole(FlowRole role) throws SQLException {
		commonDao.add(role);
		
	}

	@Override
	public void updateRole(FlowRole role) throws SQLException {
		commonDao.update(role);
		
	}

	@Override
	public void deleteRole(FlowRole role) throws SQLException {
		commonDao.del(role);
		
	}

	@Override
	public List<FlowRole> queryRoleList(String query, int start, int limit)
			throws SQLException {
		String hql ="FROM FLOWROLE WHERE 1=1 ";
		if(StringUtils.isNotEmpty(query)){
			hql+=" AND FLOWROLENAME LIKE '%"+query+"%' ";
		}
		return (List<FlowRole>) commonDao.queryListforPage(hql, start, limit);
	}

	@Override
	public int queryRoleListCount(String query) throws SQLException {
		String hql = "SELECT COUNT(*) FROM FLOWROLE WHERE 1=1 ";
		if(StringUtils.isNotEmpty(query)){
			hql+=" AND FLOWROLENAME LIKE '%"+query+"%' ";
		}
		return ((Long)commonDao.queryObject(hql)).intValue();
	}

	@Override
	public FlowRole queryRoleById(String flowroleId) throws SQLException {
		String hql="FROM FLOWROLE WHERE FLOWROLEID=?";
		return (FlowRole) commonDao.queryObject(hql, new String[] {flowroleId});
	}

	@Override
	public List<FlowRole> queryAllRole() throws SQLException {
		String hql = "FROM FLOWROLE";
		return (List<FlowRole>) commonDao.queryList(hql);
	}

	@Override
	public FlowRole queryByName(String flowrolename) throws SQLException {
		String hql = "FROM FLOWROLE WHERE FLOWROLENAME=?";
		return (FlowRole) commonDao.queryObject(hql,new String[] {flowrolename});
	}
	
}
