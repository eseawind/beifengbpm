package com.beifengbpm.business.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="APPROVATION")
public class Approvation implements Serializable{
	@Id
	@Column(name="APPROVATIONID")
	private String approvationId;
	private String processinsId;
	private String businessId;
	private String approvationoption;
	private String approvation;
	private String approvationuserId;
	private String approvationusername;
	public String getApprovationId() {
		return approvationId;
	}
	public void setApprovationId(String approvationId) {
		this.approvationId = approvationId;
	}
	public String getProcessinsId() {
		return processinsId;
	}
	public void setProcessinsId(String processinsId) {
		this.processinsId = processinsId;
	}
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public String getApprovationoption() {
		return approvationoption;
	}
	public void setApprovationoption(String approvationoption) {
		this.approvationoption = approvationoption;
	}
	public String getApprovation() {
		return approvation;
	}
	public void setApprovation(String approvation) {
		this.approvation = approvation;
	}
	public String getApprovationuserId() {
		return approvationuserId;
	}
	public void setApprovationuserId(String approvationuserId) {
		this.approvationuserId = approvationuserId;
	}
	public String getApprovationusername() {
		return approvationusername;
	}
	public void setApprovationusername(String approvationusername) {
		this.approvationusername = approvationusername;
	}
	public String getApprovationtime() {
		return approvationtime;
	}
	public void setApprovationtime(String approvationtime) {
		this.approvationtime = approvationtime;
	}
	public String getWorkitemId() {
		return workitemId;
	}
	public void setWorkitemId(String workitemId) {
		this.workitemId = workitemId;
	}
	public String getWorkitemname() {
		return workitemname;
	}
	public void setWorkitemname(String workitemname) {
		this.workitemname = workitemname;
	}
	private String approvationtime;
	private String workitemId;
	private String workitemname;
}
