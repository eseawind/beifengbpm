package com.beifengbpm.api.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import sun.jdbc.odbc.OdbcDef;

import com.beifengbpm.api.entity.HaveTodoTable;
import com.beifengbpm.api.entity.TodoTable;
import com.beifengbpm.api.service.TodoTableService;
import com.beifengbpm.dao.CommonDao;
import com.beifengbpm.tools.StringUtils;

public class TodoTableServiceImpl implements TodoTableService {

	private CommonDao commonDao;
	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}

	@Override
	public void addTodoInfo(TodoTable todo) throws SQLException {
		todo.setId(StringUtils.getkeys());
		commonDao.add(todo);
	}

	@Override
	public void deleteTodo(TodoTable todo) throws SQLException {
		commonDao.del(todo);

	}

	public List querthavetodoUserList(String processinsId, String workitemId,
			int start, int limit) throws SQLException {
		String sql="SELECT * FROM(SELECT T.*,ROWNUM RN FROM( "+
		" SELECT DISTINCH H.USERID,F.FLOWUSERNAME FROM HAVETODOTABLE H,FLOWUSER F WHERE H.USERID=F.FLOWUSERID AND PROCESSINSID='"+processinsId+"' AND WORKITEMID='"+workitemId+"'"+
		" ) T WHERE ROWNUM<="+(start+limit)+
		" ) WHERE RN>"+start;
		return commonDao.queryBySQL(sql);
	}

	@Override
	public TodoTable queryByProcess(String processinsId) throws SQLException {
		String hql = "FROM TODOTABLE WHERE PROCESSINSID=?";
		return (TodoTable) commonDao.queryObject(hql, new String[]{processinsId});
	}

	@Override
	public HaveTodoTable queryByProcessandWorkItem(String userId,
			String workitemId, String processinsId) throws SQLException {
		String hql = "FROM HAVETODOTABLE WHERE WORKITEMID='"+workitemId+"' AND PROCESSINSID='"+processinsId+"' ";
		if (StringUtils.isNotEmpty(userId)) {
			hql+=" AND USERID='"+userId+"'";
		}
		return (HaveTodoTable) commonDao.queryObject(hql);
	}

	@Override
	public HaveTodoTable queryHavetodoById(String havetodoId)
			throws SQLException {
		String hql = "FROM HAVATODOTABLE WHERE HAVETODOID=?";
		return (HaveTodoTable) commonDao.queryObject(hql, new String[]{havetodoId});
	}

	public List<TodoTable> queryListBySelf(String userId, String sysId,
			String query, int start, int limit) throws SQLException,
			UnsupportedEncodingException {
		String hql = "FROM TODOTABLE WHERE USERID='"+userId+"' AND TODOSTATE<>3 AND SYSID='"+sysId+"' ";
		if (StringUtils.isNotEmpty(query)) {
			query= new String(query.getBytes("ISO-8859-1"),"UTF-8");
			hql+=" AND (MODULENAME LIKE '%"+query+"%' OR WORKITEMNAME LIKE '%"+query+"%')";
		}
		hql+=" ORDER BY CREATETIME DESC";
		return (List<TodoTable>) commonDao.queryListforPage(hql, start, limit);
	}

	public List<HaveTodoTable> queryListBySelfforHaveTodo(String userId,
			String query, int start, int limit) throws SQLException,
			UnsupportedEncodingException {
		String hql="FROM HAVETODOTABLE WHERE USERID='"+userId+"' ";
		if (StringUtils.isNotEmpty(query)) {
			query=new String(query.getBytes("ISO-8859-1"),"UTF-8");
			hql+=" AND (MODULENAME LIKE '%"+query+"%' OR WORKITEMNAME LIKE '%"+query+"%')";
		}
		hql+=" ORDER BY PROCESSTIME DESC";
		return (List<HaveTodoTable>) commonDao.queryListforPage(hql, start, limit);
	}

	@Override
	public List queryListBySelfforHaveTodo(String processinsId, int start,
			int limit) throws SQLException, UnsupportedEncodingException {
		String sql = "SELECT * FROM(SELECT T.*,ROWNUM RN FROM( "+
		" SELECT DISTINCT WORKITEMID,WORKITEMNAME FROM HAVETODOTABLE WHERE PROCESSINSID='"+processinsId+"' "+
		" ) T WHERE ROWNUM<="+(start+limit)+
		" ) WHERE RN>"+start;
		return commonDao.queryBySQL(sql);
	}

	public TodoTable queryTodoById(String id) throws SQLException {
		String hql="FROM TODOTABLE WHERE ID=?";
		
		return (TodoTable) commonDao.queryObject(hql, new String[]{id});
	}

	public void signFlow(String id) throws SQLException {
		String hql="FROM TODOTABLE WHERE ID=?";
		TodoTable todo=(TodoTable) commonDao.queryObject(hql, new String[]{id});
		if (todo.getTodostate()!=4) {
			todo.setSigntime(StringUtils.parseDateToString("yyyy-MM-dd HH:mm:ss", new Date()));
			todo.setTodostate(2);
			commonDao.update(todo);
		}
	}

	public int totalCountBySelf(String userId, String sysId, String query)
			throws SQLException, UnsupportedEncodingException {
		String hql = "SELECT COUNT(*) FROM TODOTABLE WHERE USERID='"+userId+"' AND TODOSTATE<>3 AND SYSID='"+sysId+"' ";
		if (StringUtils.isNotEmpty(query)) {
			query=new String(query.getBytes("ISO-8859-1"),"UTF-8");
			hql+=" AND (MODULENAME LIKE '%"+query+"%' OR WORKITEMNAME LIKE '%"+query+"'%)";
		}
		return ((Long)commonDao.queryObject(hql)).intValue();
	}

	public int totalCountforHaveTodo(String userId, String query)
			throws SQLException, UnsupportedEncodingException {
		String hql="SELECT COUNT(*) FROM HAVETODOTABLE WHERE USERID='"+userId+"' ";
		if(StringUtils.isNotEmpty(query)){
			query=new String(query.getBytes("ISO-8859-1"),"UTF-8");
			hql+=" AND (MODULENAME LIKE '%"+query+"%' OR WORKITEMNAME LIKE '%"+query+"%')";
		}
		return ((Long)commonDao.queryObject(hql)).intValue();
	}

	@Override
	public int totalCountforHaveTodo(String processinsId) throws SQLException,
			UnsupportedEncodingException {
		String sql="SELECT COUNT(*) FROM ( SELECT  DISTINCT WORKITEMID,WORKITEMNAME FROM HAVETODOTABLE WHERE PROCESSINSID='"+processinsId+"')";
		BigDecimal bg=(BigDecimal)commonDao.queryBySQL(sql).get(0);
		return bg.intValue();
	}

	@Override
	public int totalhavetodoUserList(String processinsId, String workitemId)
			throws SQLException {
		String sql="SELECT COUNT(*) FROM ( SELECT DISTINCT H.USERID,F.FLOWUSERNAME FROM HAVETODOTABLE H,FLOWUSER F WHERE H.USERID=F.FLOWUSERID AND PROCESSINSID='"+processinsId+"' AND WORKITEMID='"+workitemId+"')";
		BigDecimal bg=(BigDecimal)commonDao.queryBySQL(sql).get(0);
		return bg.intValue();
	}

	@Override
	public void updateTodoInfo(TodoTable todo) throws SQLException {
		commonDao.update(todo);

	}

}
