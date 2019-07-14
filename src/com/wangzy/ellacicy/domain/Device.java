package com.wangzy.ellacicy.domain;

import java.util.ArrayList;

/**
 * 机具
 * @author wangzy
 *
 */
public class Device {

	private long terminalNo;
	private long busnessNo;
	private String partBank;
	private String busnessName;
	
	private ArrayList<WorkHistory> workhistry;
	
	public Device() {
		workhistry=new ArrayList<>();
	}
	
	public void addHistory(WorkHistory wh) {
		workhistry.add(wh);
	}
	
	public long getTerminalNo() {
		return terminalNo;
	}
	public void setTerminalNo(long terminalNo) {
		this.terminalNo = terminalNo;
	}
	public long getBusnessNo() {
		return busnessNo;
	}
	public void setBusnessNo(long busnessNo) {
		this.busnessNo = busnessNo;
	}
	public String getPartBank() {
		return partBank;
	}
	public void setPartBank(String partBank) {
		this.partBank = partBank;
	}
	public String getBusnessName() {
		return busnessName;
	}
	public void setBusnessName(String busnessName) {
		this.busnessName = busnessName;
	}
	public ArrayList<WorkHistory> getWorkhistry() {
		return workhistry;
	}
	public void setWorkhistry(ArrayList<WorkHistory> workhistry) {
		this.workhistry = workhistry;
	}
	
	    	
	
	
	
	
}
