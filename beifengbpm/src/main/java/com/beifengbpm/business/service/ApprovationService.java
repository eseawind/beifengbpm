package com.beifengbpm.business.service;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.beifengbpm.business.entity.Approvation;
import com.beifengbpm.business.entity.Leave;

public interface ApprovationService {
public void addApprovation(Approvation app) throws SQLException;
	
	public Approvation queryById(String approvationId) throws SQLException;
	
	public List<Approvation> queryListByBusiness(String businessId) throws SQLException;
	
	public List<Approvation> queryListByProcessandWorkItem(String processinsId,String workitemId) throws SQLException;
}
