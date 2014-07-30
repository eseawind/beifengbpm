package com.beifengbpm.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.beifengbpm.dao.CommonDao;
import com.beifengbpm.entity.FlowUser;
import com.beifengbpm.service.FlowUserService;
import com.beifengbpm.tools.StringUtils;

public class FlowUserServiceImpl implements FlowUserService {
	
	private CommonDao commonDao;
	
	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}

	@Override
	public void addUser(FlowUser user) throws SQLException {
		String flowuserId = user.getFlowuserId();
		if(flowuserId!=null){
			String hql = "FROM FLOWUSER WHERE FLOWUSERID=?";
			FlowUser f = (FlowUser) commonDao.queryObject(hql,new String[] {flowuserId});
			if(null==f){
				user.setFlowroleId(StringUtils.getkeys());
				commonDao.add(user);
			}
		}

	}

	@Override
	public void updateUser(FlowUser user) throws SQLException {
		commonDao.update(user);

	}

	@Override
	public void deleteUser(FlowUser user) throws SQLException {
		commonDao.del(user);

	}

	@Override
	public FlowUser queryUserById(String flowuserId) throws SQLException {
		String hql="FROM FLOWUSER WHERE FLOWUSERID=?";
		return (FlowUser) commonDao.queryObject(hql, new String[] {flowuserId});
	}

	@Override
	public int queryUserCount(String query, String departmentId)
			throws SQLException {
		String hql = "SELECT COUNT(*) FROM FLOWUSER WHERE DEPARTMENTID='"+departmentId+"' ";
		if(StringUtils.isNotEmpty(query)){
			hql+=" AND (FLOWUSERNAME LIKE '%"+query+"%' OR FLOWLOGINNAME LIKE '%"+query+"%' )";
		}
		return ((Long)commonDao.queryObject(hql)).intValue();
	}

	@Override
	public List queryUserList(String query, String departmentId, int start,
			int limit) throws SQLException {
		String hql = "SELECT * FROM(SELECT T.*,ROWNUM RN FROM(SELECT * FROM FLOWUSER FU LEFT JOIN DEPARTMENT D ON (FU.DEPARTMENTID=D.DEPARTMENTID) LEFT JOIN FLOWROLE FR ON(FU.FLOWROLEID=FR.FLOWROLEID) WHERE D.DEPARTMENTID='"+departmentId+"'";
		if(StringUtils.isEmpty(query)){
			hql+=" AND (FU.FLOWUSERNAME LIKE '%"+query+"%' OR FU.FLOWLOGINNAME LIKE '%"+query+"%')";
		}
			hql+=" ) T WHERE ROWNUM<="+(start+limit)+") WHERE RN>"+start;
			return commonDao.queryBySQL(hql);
		
	}

	@Override
	public int queryUserCountByRole(String query, String flowroleId)
			throws SQLException {
		String hql = "SELECT COUNT(*) FROM FLOWUSER WHERE FLOWROLEID='"+flowroleId+"' ";
		if(StringUtils.isNotEmpty(query)){
			hql+=" AND (FLOWUSERNAME LIKE '%"+query+"' OR FLOWLOGINNAME LIKE '%"+query+"%')";
		}
		return ((Long)commonDao.queryObject(hql)).intValue();
	}

	@Override
	public List<FlowUser> queryUserListByRole(String query, String flowroleId,
			int start, int limit) throws SQLException {
		String hql = "FROM FLOWUSER WHERE FLOWROLEID='"+flowroleId+"' ";
		if(StringUtils.isNotEmpty(query)){
			hql+="AND (FLOWUSERNAME LIKE '%"+query+"%' OR FLOWLOGINNAME LIKE '%"+query+"%')";
		}
		return (List<FlowUser>) commonDao.queryListforPage(hql, start, limit);
	}

	@Override
	public void deleteUserBySQL(String sql) throws SQLException {
		commonDao.executeBySQL(sql);;

	}

	@Override
	public List<FlowUser> queryAllUserByDepartment(String departmentId)
			throws SQLException {
		String hql = "FROM FLOWUSER WHERE DEPARTMENTID=?";
		return (List<FlowUser>) commonDao.queryList(hql,new String[]{departmentId});
	}

	@Override
	public FlowUser loginUser(String flowloginname, String flowuserpassword)
			throws SQLException {
		String hql = "FROM FLOWUSER WHERE FLOWLOGINNAME=? AND FLOWUSERPASSWORD=?";
		return (FlowUser) commonDao.queryList(hql, new String[] {flowloginname,flowuserpassword});
	}

}
