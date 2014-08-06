package com.beifengbpm.api.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.beifengbpm.api.entity.FlowModel;
import com.beifengbpm.api.entity.HaveTodoTable;
import com.beifengbpm.api.entity.ProcessIns;
import com.beifengbpm.api.entity.TodoTable;
import com.beifengbpm.api.entity.WorkItem;
import com.beifengbpm.api.entity.WorkitemState;
import com.beifengbpm.api.service.WorkItemService;
import com.beifengbpm.dao.CommonDao;
import com.beifengbpm.tools.BeifengBPMScriptEngine;
import com.beifengbpm.tools.StringUtils;

public class WorkItemServiceImpl implements WorkItemService {
	private CommonDao commonDao;

	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}

	public int according(String id, String userId) throws SQLException {
		int flag=0;
		String sql="UPDATE TODOTABLE SET USERID='"+userId+"' WHERE ID='"+id+"'";
		commonDao.executeBySQL(sql);
		flag=1;
		return flag;
	}

	public void addItem(WorkItem item) throws SQLException {
		item.setWorkitemId(StringUtils.getkeys());
		commonDao.add(item);
	}


	@Override
	public void deleteItem(WorkItem item) throws SQLException {
		commonDao.del(item);

	}

	public String flowredgreen(String processsId) throws SQLException {
		String sql1="select h.WORKITEMNAME,f.FLOWUSERNAME from HAVETODOTABLE h,FLOWUSER f where h.USERID=f.FLOWUSERID and PROCESSINSID='"+processsId+"' order by h.PROCESSTIME";
		String sql2="select t.WORKITEMNAME,f.FLOWUSERNAME,t.TODOSTATE from TODOTABLE t,FLOWUSER f where t.USERID=f.FLOWUSERID and PROCESSINSID='"+processsId+"'";
		String data="[";
		List list1=commonDao.queryBySQL(sql1);
		for(int i=0;i<list1.size();i++){
			Object [] os=(Object [])list1.get(i);
			String s="{'workitemname':'"+os[0]+"','username':'"+os[1]+"','state':'已处理','signstate':'已签收'},";
			data+=s;
		}
		List list2=commonDao.queryBySQL(sql2);
		for(int i=0;i<list2.size();i++){
			Object [] os=(Object [])list2.get(i);
			String state=String.valueOf(os[2]);
			String signstate="";
			if(state.equals("1")){
				signstate="未签收";
			}else if(state.equals("2")){
				signstate="已签收";
			}
			String s="{'workitemname':'"+os[0]+"','username':'"+os[1]+"','state':'未处理','signstate':'"+signstate+"'},";
			data+=s;
		}
		if(data.length()>1){
			data=data.substring(0,data.length()-1)+"]";
		}
		return data;
	}
	

	public WorkItem queryItemById(String workitemId) throws SQLException {
		String hql = "FROM WORKITEM WHERE WORKITEMID=?";
		return (WorkItem) commonDao.queryObject(hql,
				new String[] { workitemId });
	}

	public List<WorkItem> queryListByprocess(String insql) throws SQLException {
		String hql = "FROM WORKITEM WHERE WORKITEMID IN(" + insql + ")";
		return (List<WorkItem>) commonDao.queryList(hql);
	}

	public List<WorkItem> queryitemListByModel(String flowmodelId)
			throws SQLException {
		String hql = "FROM WORKITEM WHERE FLOWMODELID=?";
		return (List<WorkItem>) commonDao.queryList(hql,
				new String[] { flowmodelId });
	}

	public List<WorkItem> queryitemListByType(String flowmodelId,
			int workitemtype) throws SQLException {
		String hql = "FROM WORKITEM WHERE FLOWMODELID='" + flowmodelId
				+ "' AND WORKITEMTYPE=" + workitemtype;
		return (List<WorkItem>) commonDao.queryList(hql);
	}

	public List<WorkItem> queryitemListForForm(String workitemId)
			throws SQLException {
		WorkItem item = queryItemById(workitemId);
		String hql = "FROM WORKITEM WHERE WORKITEMID IN(" + item.getFormId()
				+ ")";
		return (List<WorkItem>) commonDao.queryList(hql);
	}

	public List<WorkItem> queryitemListForTo(String workitemId)
			throws SQLException {
		WorkItem item = queryItemById(workitemId);
		String hql = "FROM WORKITEM WHERE WORKITEMID IN(" + item.getToId()
				+ ")";
		return (List<WorkItem>) commonDao.queryList(hql);
	}

	// 根据分支列表来返回具体执行的分支的下一个节点
	public WorkItem returnitemByCondition(List<WorkItem> list, Map datamap)
			throws SQLException {
		WorkItem item = null;
		if (list.size() == 1) {
			item = list.get(0);
		} else {
			for (WorkItem w : list) {
				if (BeifengBPMScriptEngine.findValueByCondition(w
						.getCondition(), datamap)) {
					item = w;
					break;
				}
			}
		}
		item = queryListByprocess(item.getToId()).get(0);
		return item;
	}
	
	public List<WorkItem> returnitemByLuyou(List<WorkItem> list,Map datamap) throws SQLException{
		List<WorkItem> list1=new ArrayList<WorkItem>();
		for(WorkItem w:list){
			list1.add(queryListByprocess(w.getToId()).get(0));
		}
		return list1;
	}
	
	public void completeworkitem(String userId,String flowmodelname,String flowmodelId,String todoid,String workitemId,String processinsId,Map dataMap,String sysId)throws SQLException{
		//首先获得当前所在节点,然后根据节点的类型来进行判断
		WorkItem nowitem=queryItemById(workitemId);
		List<WorkItem> list=queryListByprocess(nowitem.getToId());
		List<WorkItem> nextitemlist=null;
		if(nowitem.getWorkitemtype()==5){
			//首先判断当前节点是否是路由,如果是路由节点,那么获取的是一个列表
			nextitemlist=returnitemByLuyou(list, dataMap);
		}
		if(nextitemlist!=null&&nextitemlist.size()>1){
			//路由后并发的情况,分别给多个节点插入代办
			for(WorkItem w:nextitemlist){
				String userkey=w.getWorkitemuserId();
				String [] users=dataMap.get(userkey).toString().split("\\+");
				for(int i=0;i<users.length;i++){
					TodoTable todo = new TodoTable();
					todo.setId(StringUtils.getkeys());
					todo.setProcessinsId(processinsId);
					todo.setModulename(flowmodelname);
					todo.setTodostate(1);
					todo.setUserId(users[i]);
					todo.setWorkitemId(w.getWorkitemname());
					todo.setWorkitemname(w.getWorkitemname());
					//设置超时状态为0,未超时
					todo.setOvertimestate(0);
					todo.setSysId(sysId);
					Calendar calendar=Calendar.getInstance();
					todo.setCreatetime(StringUtils.parseDateToString("yyyy-MM-dd HH:mm:ss", calendar.getTime()));
					//根据流程工作项的定义,判断是否需要给代办添加具体的超时时间
					if (w.getOvertime()!=null) {
						int overtime=Integer.parseInt(w.getOvertime());
						calendar.add(Calendar.SECOND, overtime);
						todo.setOvertimetime(StringUtils.parseDateToString("yyyy-MM-dd HH:mm:ss", calendar.getTime()));
					}
					commonDao.add(todo);
				}
			}
			//添加标示位记录
			WorkitemState ws = new WorkitemState();
			ws.setStateId(StringUtils.getkeys());
			ws.setProcessinsId(processinsId);
			commonDao.add(ws);
			return;
		}
		//返回下一个节点
		WorkItem realItem = returnitemByCondition(list, dataMap);
		//首先判断是否是结束了并发情况,或者是否还存在并发,如果有并发,那么使用todoid来删除代办
		String statehql="FROM WORKITEMSTATE WHERE PROCESSINSID=?";
		WorkitemState ws=(WorkitemState) commonDao.queryObject(statehql, new String[]{processinsId});
		if(ws!=null){
			//并发情况,使用todoid来进行删除
			if (nowitem.getWorkitemtype()==3) {
				String deletetodosql="DELETE FORM TODOTABLE WHERE PROCESSINSID='"+processinsId+"' AND WORKITEMID='"+nowitem.getWorkitemId()+"'";
				commonDao.executeBySQL(deletetodosql);
			}else if (nowitem.getWorkitemtype()==4) {
				//同步关系,只删除当前用户的该流程实例的待办
				String deletetodosql = "DELETE FROM TODOTABLE WHERE PROCESSINSID='"+processinsId+"' AND USERID='"+userId+"' AND WORKITEMID='"+nowitem.getWorkitemId()+"'";
				commonDao.executeBySQL(deletetodosql);
			}
			//判断当前实例是否还有未处理的待办,如果有,那么并发尚未结束,如果没有,并发结束,删除标识数据
			List<TodoTable> nowtodoList = (List<TodoTable>) commonDao.queryList("FROM TODOTABLE WHERE PROCESSINSID=?",new String[]{processinsId});
			if (nowtodoList==null||nowtodoList.size()==0) {
				//删除标识数据
				commonDao.executeBySQL("DELETE FROM WORKITEMSTATE WHERE PROCESSINSID='"+processinsId+"'");
			}
		}else {
			//删除原有待办,如果是手工活动(竞争关系),删除该流程实例的全部待办,如果是同步关系,那么只删除处理人的待办
			if(nowitem.getWorkitemtype()==3){
				String deletetodosql = "DELETE FROM TODOTABLE WHERE PROCEEEINSID='"+processinsId+"'";
				commonDao.executeBySQL(deletetodosql);
			}else if(nowitem.getWorkitemtype()==4){
				//同步关系,只删除当前用户的该流程实例的待办
				String deletetodosql = "DELETE FROM TODOTABLE WHERE PROCESSINSID='"+processinsId+"' AND USERID='"+userId+"'";
				commonDao.executeBySQL(deletetodosql);
			}
		}
		//首先要根据当前节点的类型来判断流程是否向下走
		if (nowitem.getWorkitemtype()==4) {
			String querytodohql="FROM TODOTABLE WHERE PROCESSINSID=?";
			List<TodoTable> todolist = (List<TodoTable>) commonDao.queryList(querytodohql, new String[]{processinsId});
			if(todolist.size()>0){
				//当前节点的待办没有全部处理,流程不向下走
				//添加已办记录
				HaveTodoTable htodo = new HaveTodoTable();
				htodo.setHavetodoId(StringUtils.getkeys());
				htodo.setModulename(flowmodelname);
				htodo.setProcessinsId(processinsId);
				htodo.setProcesstime(StringUtils.parseDateToString("yyyy-MM-dd HH:mm:ss", new Date()));
				htodo.setTodostate(1);
				htodo.setUserId(userId);
				htodo.setWorkitemId(workitemId);
				htodo.setSysId(sysId);
				commonDao.add(htodo);
				return;
			}
		}
		//在这里,需要判断一下,如果当前realitem的类型是手动活动或其他活动,那么添加新的待办,如果是结束节点,那么不再添加待办,其他操作正常.
		//添加新待办,给每一个被放到条件中的参与者都添加对应的待办记录,realitem不是结束节点
		if(realItem.getWorkitemtype()==3||realItem.getWorkitemtype()==4){
			String userkey = realItem.getWorkitemuserId();
			String [] users=dataMap.get(userkey).toString().split("\\+");
			for (int i = 0; i < users.length; i++) {
				TodoTable todo = new TodoTable();
				todo.setId(StringUtils.getkeys());
				todo.setProcessinsId(processinsId);
				todo.setModulename(flowmodelname);
				todo.setTodostate(1);
				todo.setUserId(users[i]);
				todo.setWorkitemId(realItem.getWorkitemId());
				todo.setWorkitemname(realItem.getWorkitemname());
				//设置超时状态为0,未超时
				todo.setOvertimestate(0);
				todo.setSysId(sysId);
				Calendar calendar = Calendar.getInstance();
				todo.setCreatetime(StringUtils.parseDateToString("yyyy-MM-dd HH:mm:ss", calendar.getTime()));
				//根据流程工作项的定义,判断是否需要给待办添加具体的超时时间
				if (realItem.getOvertime()!=null) {
					int overtime = Integer.parseInt(realItem.getOvertime());
					calendar.add(Calendar.SECOND, overtime);
					todo.setOvertimetime(StringUtils.parseDateToString("yyyy-MM-dd HH:mm:ss", calendar.getTime()));
				}
				commonDao.add(todo);
			}
		}else if (realItem.getWorkitemtype()==5) {
			//首先判断是否是并发的情况,从标志位中进行处理
			ws=(WorkitemState) commonDao.queryObject(statehql, new String[]{processinsId});
			if(ws!=null){
				System.out.println("并发尚未结束，流程不流转");
			}else{
				//不是并发情况，或者是并发已经结束，下一个点是路由，继续流转
				completeworkitem(userId, flowmodelname, flowmodelId, todoid, realItem.getWorkitemId(), processinsId, dataMap,sysId);
			}
		}
		if (realItem.getWorkitemtype()!=5) {
			//如果当期节点是路由节点,那么不修改流程实例
			String findprocesshql = "FROM PROCESSINS WHERE PROCESSINSID='"+processinsId+"'";
			System.out.println("update111111111111111111");
			ProcessIns processIns  = (ProcessIns) commonDao.queryObject(findprocesshql);
			processIns.setWorkitemId(realItem.getWorkitemId());
			commonDao.update(processIns);
		}
		//添加已办记录
		HaveTodoTable htodo = new HaveTodoTable();
		htodo.setHavetodoId(StringUtils.getkeys());
		htodo.setModulename(flowmodelname);
		htodo.setProcessinsId(processinsId);
		htodo.setProcesstime(StringUtils.parseDateToString("yyyy-MM-dd HH:mm:ss", new Date()));
		htodo.setTodostate(1);
		htodo.setUserId(userId);
		htodo.setWorkitemId(workitemId);
		htodo.setWorkitemname(nowitem.getWorkitemname());
		htodo.setSysId(sysId);
		commonDao.add(htodo);
	}
	
	
	public void rejected(String havetodoId) throws SQLException {
		// 根据被驳回的节点的已办id取到被驳回节点的已办信息
		String havetodohql="FROM HAVETODOTABLE WHERE HAVETODOID=?";
		HaveTodoTable htodo = (HaveTodoTable) commonDao.queryObject(havetodohql, new String[]{havetodoId});
		//删除当前的待办
		String deletetodosql="DELETE FROM TODOTABLE WHERE PROCESSINSID='"+htodo.getProcessinsId()+"'";
		commonDao.executeBySQL(deletetodosql);
		//给驳回节点的驳回人添加待办
		TodoTable todo = new TodoTable();
		todo.setId(StringUtils.getkeys());
		todo.setProcessinsId(htodo.getProcessinsId());
		todo.setModulename(htodo.getModulename());
		todo.setTodostate(1);
		todo.setUserId(htodo.getUserId());
		todo.setWorkitemId(htodo.getWorkitemId());
		todo.setWorkitemname(htodo.getWorkitemname());
		todo.setCreatetime(StringUtils.parseDateToString("yyyy-MM-dd HH:mm:ss", new Date()));
		commonDao.add(todo);
		//修改流程实例信息,修改当前的工作项
		String updateprocesssql="UPDATE PROCESSINS SET WORKITEMID='"+htodo.getWorkitemId()+"' WHERE PROCESSINSID='"+htodo.getProcessinsId()+"'";
		commonDao.executeBySQL(updateprocesssql);
	}

	@Override
	public int retrieve(String workitemId, String processinsId,
			HaveTodoTable havetodo) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String startProcess(String modelId, Map dateamap, String sysId)
			throws SQLException {
		String hql="FROM FLOWMODEL WHERE FLOWMODELID=?";
		FlowModel model = (FlowModel) commonDao.queryObject(hql, new String[]{modelId});
		WorkItem startItem = queryitemListByType(model.getFlowmodelId(), 0).get(0);
		List<WorkItem> seconditemlist=queryListByprocess(startItem.getToId());
		//根据参数来判断分支对应的是哪一个节点
		WorkItem item = returnitemByCondition(seconditemlist, dateamap);
		String processinsId= StringUtils.getkeys();
		ProcessIns process = new ProcessIns();
		process.setProcessinsId(processinsId);
		process.setFlowmodelId(modelId);
		process.setWorkitemId(item.getWorkitemId());
		process.setBusinessmodule(model.getFlowmodelname());
		process.setCreateusername(dateamap.get("createusername").toString());
		//插入流程实例数据
		commonDao.add(process);
		//插入待办数据,参与者变量名称
		String key = item.getWorkitemuserId();
		//参与者变量的值
		String [] users = dateamap.get(key).toString().split("\\+");
		for(int i=0;i<users.length;i++){
			TodoTable todo=new TodoTable();
			todo.setProcessinsId(processinsId);
			todo.setModulename(model.getFlowmodelname());
			todo.setTodostate(1);
			todo.setUserId(users[i]);
			todo.setWorkitemId(item.getWorkitemId());
			todo.setWorkitemname(item.getWorkitemname());
			todo.setCreatetime(StringUtils.parseDateToString("yyyy-MM-dd HH:mm:ss", new Date()));
			todo.setId(StringUtils.getkeys());
			todo.setSysId(sysId);
			commonDao.add(todo);
		}
		return processinsId;
	}

	@Override
	public void updateItem(WorkItem item) throws SQLException {
		commonDao.update(item);

	}

}
