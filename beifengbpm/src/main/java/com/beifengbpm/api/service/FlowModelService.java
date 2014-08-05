package com.beifengbpm.api.service;

import java.sql.SQLException;

import com.beifengbpm.api.entity.FlowModel;
import com.beifengbpm.tools.StringUtils;

public interface FlowModelService {
	public void addModel(FlowModel model) throws SQLException;
	public FlowModel queryByName(String flowmodelname)throws SQLException;
	public FlowModel queryById(String flowmodelId)throws SQLException;
}
