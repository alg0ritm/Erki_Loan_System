package com.loansystem.model;

// default package
// Generated Nov 13, 2011 9:49:21 PM by Hibernate Tools 3.4.0.CR1

/**
 * Client generated by hbm2java
 */
public class Client implements java.io.Serializable {

	private int clientId;
	private CientGroup cientGroup;
	private ClientStatus clientStatus;
	private String persCode;
	private String name;
	private String lastName;
	private String password;
	private String mail;
	private String rating;

	public Client() {
	}

	public Client(int clientId, CientGroup cientGroup, ClientStatus clientStatus, String persCode, String name,
			String lastName, String password, String mail, String rating) {
		this.clientId = clientId;
		this.cientGroup = cientGroup;
		this.clientStatus = clientStatus;
		this.persCode = persCode;
		this.name = name;
		this.lastName = lastName;
		this.password = password;
		this.mail = mail;
		this.rating = rating;
	}

	public int getClientId() {
		return this.clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public CientGroup getCientGroup() {
		return this.cientGroup;
	}

	public void setCientGroup(CientGroup cientGroup) {
		this.cientGroup = cientGroup;
	}

	public ClientStatus getClientStatus() {
		return this.clientStatus;
	}

	public void setClientStatus(ClientStatus clientStatus) {
		this.clientStatus = clientStatus;
	}

	public String getPersCode() {
		return this.persCode;
	}

	public void setPersCode(String persCode) {
		this.persCode = persCode;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getRating() {
		return this.rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

}
