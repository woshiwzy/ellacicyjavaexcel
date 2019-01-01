package com.wangzy.ellacicy.domain;

import java.util.ArrayList;
import java.util.Date;

/**
 * 机具登机表
 * 
 * @author wangzy
 */
public class DeviceRecord {

	private String manager;
	private String desc;
	private String busnessName;
	private String address;
	private String contact;
	private String contactPhone;
	private String busnessNo;
	private long deviceNo;
	private String brand;
	private String model;
	private String host;
	private String keyborad;
	private String hostAddrss;
	private Date switchDate;
	private String connectType;
	private String partBank;
	private String note;

	private ArrayList<WorkHistory> workhistories;

	public DeviceRecord() {
		workhistories = new ArrayList<>();
	}

	public void addWorkHistory(WorkHistory workhis) {
		workhistories.add(workhis);
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getBusnessName() {
		return busnessName;
	}

	public void setBusnessName(String busnessName) {
		this.busnessName = busnessName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getHostAddrss() {
		return hostAddrss;
	}

	public void setHostAddrss(String hostAddrss) {
		this.hostAddrss = hostAddrss;
	}

	public Date getSwitchDate() {
		return switchDate;
	}

	public void setSwitchDate(Date switchDate) {
		this.switchDate = switchDate;
	}

	public String getConnectType() {
		return connectType;
	}

	public void setConnectType(String connectType) {
		this.connectType = connectType;
	}

	public String getPartBank() {
		return partBank;
	}

	public void setPartBank(String partBank) {
		this.partBank = partBank;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public long getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(long deviceNo) {
		this.deviceNo = deviceNo;
	}

	public void setKeyborad(String keyborad) {
		this.keyborad = keyborad;
	}

	public String getBusnessNo() {
		return busnessNo;
	}

	public void setBusnessNo(String busnessNo) {
		this.busnessNo = busnessNo;
	}

	public String getKeyborad() {
		return keyborad;
	}

	public ArrayList<WorkHistory> getWorkhistories() {
		return workhistories;
	}

	public void setWorkhistories(ArrayList<WorkHistory> workhistories) {
		this.workhistories = workhistories;
	}
	
	

}
