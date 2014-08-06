package com.beifengbpm.api.service.impl;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import com.beifengbpm.api.entity.ProcessIns;
import com.beifengbpm.api.service.ProcessInsService;
import com.beifengbpm.dao.CommonDao;
import com.beifengbpm.tools.StringUtils;

public class ProcessInsServiceImpl implements ProcessInsService {

	private CommonDao commonDao;

	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}

	public void addProcess(ProcessIns process) throws SQLException {
		commonDao.add(process);
	}

	@Override
	public void deleteProcess(ProcessIns process) throws SQLException {
		commonDao.del(process);
	}

	@Override
	public List queryAllProcess(String query, int start, int limit)
			throws SQLException {
		String hql="FROM PROCESSINS P,WORKITEM W WHERE P.WORKITEMID=W.WORKITEMID ";
		if(StringUtils.isNotEmpty(query)){
			try {
				query=new String(query.getBytes("ISO-8859-1"),"UTF-8");
				hql+=" AND (P.BUSINESSMODULE LIKE '%"+query+"%' OR P.CREATEUSERNAME LIKE '%"+query+"%')";
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return commonDao.queryListforPage(hql, start, limit);
	}

	public ProcessIns queryById(String processinsId) throws SQLException {
		String hql="FROM PROCESSINS WHERE PROCESSINSID=?";
		return (ProcessIns)commonDao.queryObject(hql, new String[]{processinsId});
	}

	public int totalAllProcess(String query) throws SQLException {
		String hql="SELECT COUNT(*) FROM PROCESSINS P,WORKITEM W WHERE P.WORKITEMID=W.WORKITEMID ";
		if(StringUtils.isNotEmpty(query)){
			try {
				query=new String(query.getBytes("ISO-8859-1"),"UTF-8");
				hql+=" AND (P.BUSINESSMODULE LIKE '%"+query+"%' OR P.CREATEUSERNAME LIKE '%"+query+"%')";
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return ((Long)commonDao.queryObject(hql)).intValue();
	}

	public void updateProcess(ProcessIns process) throws SQLException {
		commonDao.update(process);

	}

}
