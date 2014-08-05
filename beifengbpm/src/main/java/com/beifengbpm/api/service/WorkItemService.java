package com.beifengbpm.api.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.beifengbpm.api.entity.HaveTodoTable;
import com.beifengbpm.api.entity.WorkItem;

public interface WorkItemService {
	public void addItem(WorkItem item) throws SQLException;

	public void deleteItem(WorkItem item) throws SQLException;

	public void updateItem(WorkItem item) throws SQLException;

	public WorkItem queryItemById(String workitemId) throws SQLException;

	public List<WorkItem> queryitemListByModel(String flowmodelId)
			throws SQLException;
	
	public List<WorkItem> queryitemListByType(String flowmodelId,int workitemtype)throws SQLException;
	
	//节点之前的元素
	public List<WorkItem> queryitemListForForm(String workitemId)throws SQLException;
	//节点之后的元素
	public List<WorkItem> queryitemListForTo(String workitemId)throws SQLException;
	//根据流程实例中的工作项ID来查询工作项列表
	public List<WorkItem> queryListByprocess(String insql) throws SQLException;
	
	public WorkItem returnitemByCondition(List<WorkItem> list,Map datamap)throws SQLException;
	
	public void completeworkitem(String userId,String flowmodelname,String flowmodelId,String todoid,String workitemId,String processinsId,Map datamap,String sysId)throws SQLException;
	
	//取回
	public int retrieve(String workitemId,String processinsId,HaveTodoTable havetodo)throws SQLException;
	
	//驳回
	public void rejected(String havetodoId) throws SQLException;
	
	//转办
	public int according(String id,String userId)throws SQLException;
	
	//流程启动
	public String startProcess(String modelId,Map dateamap,String sysId) throws SQLException;
	
	//流程红绿灯功能
	public String flowredgreen(String processsId)throws SQLException;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
