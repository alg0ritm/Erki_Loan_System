package com.loansystem.model;

// default package
// Generated Nov 13, 2011 9:49:21 PM by Hibernate Tools 3.4.0.CR1
/**
 * Employee generated by hbm2java
 */
public class Employee implements java.io.Serializable {

    private int emloyeeId;
    private int userId;
    private int employeeType;

    public Employee() {
    }

    public Employee(int userId, String name, int employeeType) {
        this.emloyeeId = emloyeeId;
        this.userId = userId;
        this.employeeType = employeeType;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEmloyeeId() {
        return emloyeeId;
    }

    public void setEmloyeeId(int emloyeeId) {
        this.emloyeeId = emloyeeId;
    }

    public int getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(int employeeType) {
        this.employeeType = employeeType;
    }
}
