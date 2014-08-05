package com.beifengbpm.api.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 工作项：流程元素定义
 * */
@Entity
@Table(name="WORKITEM")
public class WorkItem  implements Serializable{

		@Id
		@Column(name="WORKITEMID")
		private String workitemId;
		private String flowmodelId;
		private String workitemname;
		/**
		 * 0开始
		 * 1结束
		 * 2连接线
		 * 3手工活动
		 * 4同步活动
		 * 5路由活动
		 */
		private int workitemtype;
		private String formId;
		private String fromId;
		private String toId;
		private String condition;
		private String workitemuserId;
		/**
		 * 超时间隔时间
		 */
		private String overtime;
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
		public String getFormId() {
			return formId;
		}
		public void setFormId(String formId) {
			this.formId = formId;
		}
		public String getFromId() {
			return fromId;
		}
		public void setFromId(String fromId) {
			this.fromId = fromId;
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
		public String getOvertime() {
			return overtime;
		}
		public void setOvertime(String overtime) {
			this.overtime = overtime;
		}
}
