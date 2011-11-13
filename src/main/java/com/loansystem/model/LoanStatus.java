package com.loansystem.model;

// default package
// Generated Nov 13, 2011 9:49:21 PM by Hibernate Tools 3.4.0.CR1

/**
 * LoanStatus generated by hbm2java
 */
public class LoanStatus implements java.io.Serializable {

	private String id;
	private String name;
	private String description;

	public LoanStatus() {
	}

	public LoanStatus(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public LoanStatus(String id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
