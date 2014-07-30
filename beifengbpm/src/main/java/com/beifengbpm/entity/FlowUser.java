package com.beifengbpm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="FLOWUSER")
public class FlowUser implements Serializable {
	@Id
	@Column(name="FLOWUSERID")
	private String flowuserId;
	private String departmentId;
	private String flowroleId;
	private String flowusername;
	private String flowloginname;
	private String flowuserpassword;
	public String getFlowuserId() {
		return flowuserId;
	}
	public void setFlowuserId(String flowuserId) {
		this.flowuserId = flowuserId;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getFlowroleId() {
		return flowroleId;
	}
	public void setFlowroleId(String flowroleId) {
		this.flowroleId = flowroleId;
	}
	public String getFlowusername() {
		return flowusername;
	}
	public void setFlowusername(String flowusername) {
		this.flowusername = flowusername;
	}
	public String getFlowloginname() {
		return flowloginname;
	}
	public void setFlowloginname(String flowloginname) {
		this.flowloginname = flowloginname;
	}
	public String getFlowuserpassword() {
		return flowuserpassword;
	}
	public void setFlowuserpassword(String flowuserpassword) {
		this.flowuserpassword = flowuserpassword;
	}

}
