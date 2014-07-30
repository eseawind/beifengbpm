package com.beifengbpm.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

	@Entity
	@Table(name="DEPARTMENT")
	public class Department implements Serializable{
		@Id
		@Column(name="DEPARTMENTID")
		private String departmentId;
		private String departmentname;
		private String parentId;
		private Date createtime;
		public String getDepartmentId() {
			return departmentId;
		}
		public void setDepartmentId(String departmentId) {
			this.departmentId = departmentId;
		}
		public String getDepartmentname() {
			return departmentname;
		}
		public void setDepartmentname(String departmentname) {
			this.departmentname = departmentname;
		}
		public String getParentId() {
			return parentId;
		}
		public void setParentId(String parentId) {
			this.parentId = parentId;
		}
		public Date getCreatetime() {
			return createtime;
		}
		public void setCreatetime(Date createtime) {
			this.createtime = createtime;
		}
		
		
	}

