package com.beifengbpm.api.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TODOTABLE")
public class TodoTable implements Serializable {
	@Id
	@Column(name="ID")
	private String id;
	private String workitemId;
	private String userId;
	private String modulename;
	private String workitemname;
	private String processinsId;
	/**
	 * 1 流程已经启动
	 * 2 流程结束
	 * */
	private int todostate;
	private String createtime;
	private String signtime;
	private String communicationtodoId;
	/**
	 * 具体的超时时间
	 * */
	private String overtimetime;
	public int getTodostate() {
		return todostate;
	}
	public void setTodostate(int todostate) {
		this.todostate = todostate;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getSigntime() {
		return signtime;
	}
	public void setSigntime(String signtime) {
		this.signtime = signtime;
	}
	public String getCommunicationtodoId() {
		return communicationtodoId;
	}
	public void setCommunicationtodoId(String communicationtodoId) {
		this.communicationtodoId = communicationtodoId;
	}
	public String getOvertimetime() {
		return overtimetime;
	}
	public void setOvertimetime(String overtimetime) {
		this.overtimetime = overtimetime;
	}
	public int getOvertimestate() {
		return overtimestate;
	}
	public void setOvertimestate(int overtimestate) {
		this.overtimestate = overtimestate;
	}
	public String getSysId() {
		return sysId;
	}
	public void setSysId(String sysId) {
		this.sysId = sysId;
	}
	/**
	 * 超时状态，未检测到超时的是0，超时处理过的是1
	 * */
	private int overtimestate;
	private String sysId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
}
