package com.beifengbpm.dao;

import java.sql.SQLException;
import java.util.List;

public interface CommonDao {
	
	public void add(Object obj) throws SQLException;
	
	public void del(Object obj) throws SQLException;
	
	public void update(Object obj) throws SQLException;
	
	public Object queryObject(String hql) throws SQLException;
	
	public List<?> queryList(String hql) throws SQLException;
	
	public List<?> queryList(String hql,Object [] objs) throws SQLException;
	
	public Object queryObject(String hql,Object [] objs) throws SQLException;
	
	public List<?> queryListforPage(String hql,int start,int limit) throws SQLException;
	
	public List queryBySQL(final String sql) throws SQLException;
	
	public void executeBySQL(final String sql) throws SQLException;
	
}
