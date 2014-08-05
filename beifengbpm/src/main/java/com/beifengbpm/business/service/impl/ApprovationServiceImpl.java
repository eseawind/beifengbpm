package com.beifengbpm.business.service.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.beifengbpm.business.entity.Approvation;
import com.beifengbpm.business.service.ApprovationService;
import com.beifengbpm.dao.CommonDao;
import com.beifengbpm.tools.StringUtils;

public class ApprovationServiceImpl implements ApprovationService {

	private CommonDao commonDao;
	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}
	@Override
	public void addApprovation(Approvation app) throws SQLException {
		app.setApprovationId(StringUtils.getkeys());
		app.setApprovationtime(StringUtils.parseDateToString(
				"yyyy-MM-dd HH:mm:ss", new Date()));
		commonDao.add(app);
	}

	public Approvation queryById(String approvationId) throws SQLException {
		String hql = "FROM APPROVATION WHERE APPROVATIONID=?";
		return (Approvation)commonDao.queryObject(hql, new String[] { approvationId });
	}

	public List<Approvation> queryListByBusiness(String businessId)
			throws SQLException {
		String hql = "FROM APPROVATION WHERE BUSINESSID=? ORDER BY APPROVATIONTIME";
		return (List<Approvation>)commonDao.queryList(hql, new String[] { businessId });
	}
	public List<Approvation> queryListByProcessandWorkItem(String processinsId,String workitemId) throws SQLException {
		String hql = "FROM APPROVATION WHERE PROCESSINSID=? AND WORKITEMID=? ORDER BY APPROVATIONTIME";
		return (List<Approvation>)commonDao.queryList(hql, new String[]{processinsId,workitemId});
	}
}
