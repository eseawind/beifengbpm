package com.beifengbpm.api.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 流程实例表
 * */
@Entity
@Table(name="PROCESSINS")
public class ProcessIns implements Serializable{
	@Id
	@Column(name="PROCESSINSID")
	private String processinsId;
	private String flowmodelId;
	private String workitemId;
	private String businessmodule;
	private String createusername;
	public String getProcessinsId() {
		return processinsId;
	}
	public void setProcessinsId(String processinsId) {
		this.processinsId = processinsId;
	}
	public String getFlowmodelId() {
		return flowmodelId;
	}
	public void setFlowmodelId(String flowmodelId) {
		this.flowmodelId = flowmodelId;
	}
	public String getWorkitemId() {
		return workitemId;
	}
	public void setWorkitemId(String workitemId) {
		this.workitemId = workitemId;
	}
	public String getBusinessmodule() {
		return businessmodule;
	}
	public void setBusinessmodule(String businessmodule) {
		this.businessmodule = businessmodule;
	}
	public String getCreateusername() {
		return createusername;
	}
	public void setCreateusername(String createusername) {
		this.createusername = createusername;
	}
	
}
