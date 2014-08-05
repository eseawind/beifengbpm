package com.beifengbpm.api.service;

import java.sql.SQLException;
import java.util.List;

import com.beifengbpm.api.entity.ProcessIns;

public interface ProcessInsService {

	public void addProcess(ProcessIns process) throws SQLException;

	public void deleteProcess(ProcessIns process) throws SQLException;

	public void updateProcess(ProcessIns process) throws SQLException;

	public ProcessIns queryById(String processinsId) throws SQLException;

	public List queryAllProcess(String query, int start, int limit)
			throws SQLException;

	public int totalAllProcess(String query) throws SQLException;
}
