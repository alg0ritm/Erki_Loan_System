package com.loansystem.model;

// default package
import java.util.List;

// Generated Feb 18, 2012 4:26:51 PM by Hibernate Tools 3.4.0.CR1
/**
 * User generated by hbm2java
 */
public class User implements java.io.Serializable {

    private int persCode;
    private int userId;
    private String name;
    private String lastName;
    private String mail;
    private String password;
    private List<Client> clients;
    private List<Employee> employees;

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public User() {
    }

    public User(int persCode, int userId, String name, String lastName, String mail, String password, List<Client> clients, List<Employee> employees) {
        this.persCode = persCode;
        this.userId = userId;
        this.name = name;
        this.lastName = lastName;
        this.mail = mail;
        this.password = password;
        this.clients = clients;
        this.employees = employees;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public int getPersCode() {
        return persCode;
    }

    public void setPersCode(int persCode) {
        this.persCode = persCode;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
