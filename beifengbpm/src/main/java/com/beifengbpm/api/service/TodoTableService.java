package com.beifengbpm.api.service;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import com.beifengbpm.api.entity.HaveTodoTable;
import com.beifengbpm.api.entity.TodoTable;

public interface TodoTableService {
	public void addTodoInfo(TodoTable todo) throws SQLException;

	public int totalCountBySelf(String userId, String sysId, String query)
			throws SQLException, UnsupportedEncodingException;

	public List<TodoTable> queryListBySelf(String userId, String sysId,
			String query, int start, int limit) throws SQLException,
			UnsupportedEncodingException;;

	public int totalCountforHaveTodo(String userId, String query)
			throws SQLException, UnsupportedEncodingException;;

	public List<HaveTodoTable> queryListBySelfforHaveTodo(String userId,
			String query, int start, int limit) throws SQLException,
			UnsupportedEncodingException;

	public HaveTodoTable queryHavetodoById(String havetodoId)
			throws SQLException;

	public int totalCountforHaveTodo(String processinsId) throws SQLException,
			UnsupportedEncodingException;

	public List queryListBySelfforHaveTodo(String processinsId, int start,
			int limit) throws SQLException, UnsupportedEncodingException;

	public HaveTodoTable queryByProcessandWorkItem(String userId,
			String workitemId, String processinsId) throws SQLException;

	public List querthavetodoUserList(String processinsId, String workitemId,
			int start, int limit) throws SQLException;

	public int totalhavetodoUserList(String processinsId, String workitemId)
			throws SQLException;

	public TodoTable queryByProcess(String processinsId) throws SQLException;

	public void signFlow(String id) throws SQLException;

	public TodoTable queryTodoById(String id) throws SQLException;

	public void updateTodoInfo(TodoTable todo) throws SQLException;

	public void deleteTodo(TodoTable todo) throws SQLException;

}
