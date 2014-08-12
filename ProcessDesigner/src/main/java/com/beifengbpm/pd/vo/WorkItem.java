package com.beifengbpm.pd.vo;

import java.io.Serializable;

public class WorkItem implements Serializable{
	private String workitemId;
	private String flowmodelId;
	private String workitemname;
	private int workitemtype;
	private String fromId;
	private String formId;
	private String toId;
	private String condition;
	public String getWorkitemId() {
		return workitemId;
	}
	public void setWorkitemId(String workitemId) {
		this.workitemId = workitemId;
	}
	public String getFlowmodelId() {
		return flowmodelId;
	}
	public void setFlowmodelId(String flowmodelId) {
		this.flowmodelId = flowmodelId;
	}
	public String getWorkitemname() {
		return workitemname;
	}
	public void setWorkitemname(String workitemname) {
		this.workitemname = workitemname;
	}
	public int getWorkitemtype() {
		return workitemtype;
	}
	public void setWorkitemtype(int workitemtype) {
		this.workitemtype = workitemtype;
	}
	public String getFromId() {
		return fromId;
	}
	public void setFromId(String fromId) {
		this.fromId = fromId;
	}
	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}
	public String getToId() {
		return toId;
	}
	public void setToId(String toId) {
		this.toId = toId;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getWorkitemuserId() {
		return workitemuserId;
	}
	public void setWorkitemuserId(String workitemuserId) {
		this.workitemuserId = workitemuserId;
	}
	private String workitemuserId;
	
}
