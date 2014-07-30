package com.beifengbpm.vo;

import java.util.List;

public class DepartmentUserNode {
	private String id;
	private String text;
	private String cls;
	private  boolean checked;
	private  boolean leaf;
	private List<DepartmentUserNode> children;
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
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	public List<DepartmentUserNode> getChildren() {
		return children;
	}
	public void setChildren(List<DepartmentUserNode> children) {
		this.children = children;
	}
}
