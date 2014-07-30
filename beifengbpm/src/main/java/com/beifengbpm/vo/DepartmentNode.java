package com.beifengbpm.vo;

import java.io.Serializable;
import java.util.List;

public class DepartmentNode implements Serializable {
	private String id;
	private String text;
	private String cls;
	private List<DepartmentNode> children;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getCls() {
		return cls;
	}
	public void setCls(String cls) {
		this.cls = cls;
	}
	public List<DepartmentNode> getChildren() {
		return children;
	}
	public void setChildren(List<DepartmentNode> children) {
		this.children = children;
	}
}
