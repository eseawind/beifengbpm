package com.beifengbpm.api.entity;
/**
 * 流程模型文件定义
 * */
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table
public class FlowModel implements Serializable {
	
	@Id
	@Column(name="FLOWMODEL")
	private String flowmodelId;
	private String flowmodelname;
	private String flowmodelfile;
	public String getFlowmodelId() {
		return flowmodelId;
	}
	public void setFlowmodelId(String flowmodelId) {
		this.flowmodelId = flowmodelId;
	}
	public String getFlowmodelname() {
		return flowmodelname;
	}
	public void setFlowmodelname(String flowmodelname) {
		this.flowmodelname = flowmodelname;
	}
	public String getFlowmodelfile() {
		return flowmodelfile;
	}
	public void setFlowmodelfile(String flowmodelfile) {
		this.flowmodelfile = flowmodelfile;
	}
	
}
