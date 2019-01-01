package com.wangzy.ellacicy.domain;

import java.util.Date;


/**
 * 工作明细
 * @author wangzy
 *
 */
public class WorkHistory {
	
	private String workName;
	private long terminalNo;
	private Date date;
	private String workType;
	private String phone;
	private String secondPhone;
	private String note;
	
	private String fileName;
	
	
	public String getWorkName() {
		return workName;
	}
	public void setWorkName(String workName) {
		this.workName = workName;
	}
	public long getTerminalNo() {
		return terminalNo;
	}
	public void setTerminalNo(long terminalNo) {
		this.terminalNo = terminalNo;
	}
	public String getWorkType() {
		return workType;
	}
	public void setWorkType(String workType) {
		this.workType = workType;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSecondPhone() {
		return secondPhone;
	}
	public void setSecondPhone(String secondPhone) {
		this.secondPhone = secondPhone;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
	

}
