package com.beifengbpm.api.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="HAVETODOTABLE")
public class HaveTodoTable implements Serializable {
	@Id
	@Column(name="HAVETODOID")
	private String havetodoId;
	private String workitemId;
	private String userId;
	private String modulename;
	private String workitemname;
	private String processinsId;
	private String processtime;
	private String sysId;
	private int todostate;
	public String getHavetodoId() {
		return havetodoId;
	}
	public void setHavetodoId(String havetodoId) {
		this.havetodoId = havetodoId;
	}
	public String getWorkitemId() {
		return workitemId;
	}
	public void setWorkitemId(String workitemId) {
		this.workitemId = workitemId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getModulename() {
		return modulename;
	}
	public void setModulename(String modulename) {
		this.modulename = modulename;
	}
	public String getWorkitemname() {
		return workitemname;
	}
	public void setWorkitemname(String workitemname) {
		this.workitemname = workitemname;
	}
	public String getProcessinsId() {
		return processinsId;
	}
	public void setProcessinsId(String processinsId) {
		this.processinsId = processinsId;
	}
	public String getProcesstime() {
		return processtime;
	}
	public void setProcesstime(String processtime) {
		this.processtime = processtime;
	}
	public String getSysId() {
		return sysId;
	}
	public void setSysId(String sysId) {
		this.sysId = sysId;
	}
	public int getTodostate() {
		return todostate;
	}
	public void setTodostate(int todostate) {
		this.todostate = todostate;
	}
}
