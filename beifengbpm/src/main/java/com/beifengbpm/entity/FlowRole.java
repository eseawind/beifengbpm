package com.beifengbpm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="FLOWROLE")
public class FlowRole implements Serializable{
	@Id
	@Column(name="FLOWROLEID")
	private String flowroleId;
	private String flowrolename;
	public String getFlowroleId() {
		return flowroleId;
	}
	public void setFlowroleId(String flowroleId) {
		this.flowroleId = flowroleId;
	}
	public String getFlowrolename() {
		return flowrolename;
	}
	public void setFlowrolename(String flowrolename) {
		this.flowrolename = flowrolename;
	}
	public String getFlowroleremark() {
		return flowroleremark;
	}
	public void setFlowroleremark(String flowroleremark) {
		this.flowroleremark = flowroleremark;
	}
	private String flowroleremark;
	
	
}
