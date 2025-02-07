package com.loansystem.model;

// default package
// Generated Nov 13, 2011 9:49:21 PM by Hibernate Tools 3.4.0.CR1

/**
 * PostponeRequest generated by hbm2java
 */
public class PostponeRequest implements java.io.Serializable {

	private int postponeRequestId;
	private PostponeRequestStatus postponeRequestStatus;
	private String date;
	private String employeeId;
	private String comment;
        private int periodDays;

	public PostponeRequest() {
	}

    public int getPeriodDays() {
        return periodDays;
    }

    public void setPeriodDays(int periodDays) {
        this.periodDays = periodDays;
    }

    public PostponeRequestStatus getPostponeRequestStatus() {
        return postponeRequestStatus;
    }

    public void setPostponeRequestStatus(PostponeRequestStatus postponeRequestStatus) {
        this.postponeRequestStatus = postponeRequestStatus;
    }

    public int getPostponeRequestId() {
        return postponeRequestId;
    }

    public void setPostponeRequestId(int postponeRequestId) {
        this.postponeRequestId = postponeRequestId;
    }

	public PostponeRequest(int postponeRequestId, PostponeRequestStatus postponeRequestStatus, String date, int periodDays) {
		this.postponeRequestId = postponeRequestId;
		this.postponeRequestStatus = postponeRequestStatus;
		this.date = date;
                this.periodDays = periodDays;
	}

	public PostponeRequest(int postponeRequestId, PostponeRequestStatus postponeRequestStatus, String date, String employeeId, String comment, int periodDays) {
		this.postponeRequestId = postponeRequestId;
		this.postponeRequestStatus = postponeRequestStatus;
		this.date = date;
		this.employeeId = employeeId;
		this.comment = comment;
                this.periodDays = periodDays;
	}

	public PostponeRequestStatus getPostponeRequestStatusId() {
		return this.postponeRequestStatus;
	}

	public void setStatusId(PostponeRequestStatus postponeRequestStatus) {
		this.postponeRequestStatus = postponeRequestStatus;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getEmployeeId() {
		return this.employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
