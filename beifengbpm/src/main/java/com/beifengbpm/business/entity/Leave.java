package com.beifengbpm.business.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="LEAVE")
public class Leave implements Serializable{

	@Id
	@Column(name="LEAVEID")
	private String leaveId;
	private String leavename;
	private String starttime;
	private String endtime;
	private int day;
	public String getLeaveId() {
		return leaveId;
	}
	public void setLeaveId(String leaveId) {
		this.leaveId = leaveId;
	}
	public String getLeavename() {
		return leavename;
	}
	public void setLeavename(String leavename) {
		this.leavename = leavename;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getCreatename() {
		return createname;
	}
	public void setCreatename(String createname) {
		this.createname = createname;
	}
	public String getCreateuserId() {
		return createuserId;
	}
	public void setCreateuserId(String createuserId) {
		this.createuserId = createuserId;
	}
	public String getProcessinsId() {
		return processinsId;
	}
	public void setProcessinsId(String processinsId) {
		this.processinsId = processinsId;
	}
	public String getLeavestate() {
		return leavestate;
	}
	public void setLeavestate(String leavestate) {
		this.leavestate = leavestate;
	}
	private String createtime;
	private String createname;
	private String createuserId;
	private String processinsId;
	private String leavestate;
}