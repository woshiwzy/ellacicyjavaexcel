package domain;

public class WorkDetail {

	public String employee;
	public String bid;
	public String completeTime;
	public String workType;
	
	public WorkDetail(String employee, String bid, String completeTime) {
		super();
		this.employee = employee;
		this.bid = bid;
		this.completeTime = completeTime;
	}

	public WorkDetail(String employee, String bid, String completeTime, String workType) {
		super();
		this.employee = employee;
		this.bid = bid;
		this.completeTime = completeTime;
		this.workType = workType;
	}
	
	
	

}
