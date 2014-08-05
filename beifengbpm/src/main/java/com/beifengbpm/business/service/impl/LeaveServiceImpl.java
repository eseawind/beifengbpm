package com.beifengbpm.business.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.beifengbpm.business.entity.Leave;
import com.beifengbpm.business.service.LeaveService;
import com.beifengbpm.dao.CommonDao;
import com.beifengbpm.tools.StringUtils;

public class LeaveServiceImpl implements LeaveService {
	private CommonDao commonDao;

	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}

	public void addLeaveInfo(Leave leave) throws SQLException {
		leave.setLeaveId(StringUtils.getkeys());
		commonDao.add(leave);
	}

	public List<Leave> queryListByUser(String createuserId) throws SQLException {
		String hql = "FROM LEAVE WHERE CREATEUSERID=?";
		return (List<Leave>) commonDao.queryList(hql, new String[] { createuserId });
	}

	public Leave querybyId(String leaveId) throws SQLException {
		String hql = "FROM LEAVE WHERE LEAVEID=?";
		return (Leave) commonDao.queryObject(hql, new String[] { leaveId });

	}

	public List<Leave> queryListByPage(String createuserId, String query,
			int start, int limit) throws Exception {
		String hql = "FROM LEAVE WHERE CREATEUSERID='" + createuserId + "'";
		if (StringUtils.isNotEmpty(query)) {
			query = new String(query.getBytes("ISO-8859-1"), "UTF-8");
			hql = " and LEAVENAME LIKE '%" + query + "%'";
		}
		return (List<Leave>) commonDao.queryListforPage(hql, start, limit);
	}

	public int totalCount(String createuserId, String query) throws Exception {
		String hql = "SELECT COUNT(*) FROM LEAVE WHERE CREATEUSERID='"
				+ createuserId + "'";
		if (StringUtils.isNotEmpty(query)) {
			hql = " AND LEAVENAME LIKE '%" + query + "%'";
		}
		return ((Long) commonDao.queryObject(hql)).intValue();
	}

	public Leave queryByProcessInsId(String processinsId) throws SQLException {
		String hql = "FROM LEAVE WHERE PROCESSINSID=?";
		return (Leave) commonDao.queryObject(hql, new String[] { processinsId });

	}

	public void updateLeaveInfo(Leave leave) throws SQLException {
		commonDao.update(leave);
	}

}
