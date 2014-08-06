package com.beifengbpm.api.service.impl;

import java.sql.SQLException;

import com.beifengbpm.api.entity.FlowModel;
import com.beifengbpm.api.service.FlowModelService;
import com.beifengbpm.dao.CommonDao;
import com.beifengbpm.tools.StringUtils;

public class FlowModelServiceImpl implements FlowModelService {
	private CommonDao commonDao;
	

	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}
	public void addModel(FlowModel model) throws SQLException {
		model.setFlowmodelId(StringUtils.getkeys());
		commonDao.add(model);
	}

	@Override
	public FlowModel queryById(String flowmodelId) throws SQLException {
		String hql = "FROM FLOWMODEL WHERE FLOWMODELID=?";
		return (FlowModel) commonDao.queryObject(hql, new String[]{flowmodelId});
	}

	@Override
	public FlowModel queryByName(String flowmodelname) throws SQLException {
		String hql = "FROM FLOWMODEL WHERE FLOWMODELNAME=?";
		return (FlowModel) commonDao.queryObject(hql, new String[]{flowmodelname});
	}

}
