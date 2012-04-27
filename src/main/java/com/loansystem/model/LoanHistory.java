package com.loansystem.model;

// default package
import java.util.Date;

// Generated Nov 13, 2011 9:49:21 PM by Hibernate Tools 3.4.0.CR1
/**
 * LoanHistory generated by hbm2java
 */
public class LoanHistory implements java.io.Serializable {

    private int loanHistoryId;
    private String comment;
    private String date; //Date decide sql or java
    private Loan loan;
    private LoanStatus loanStatus;

    public LoanHistory(int loanHistoryId, String comment, String date, Loan loan, LoanStatus loanStatus) {
        this.loanHistoryId = loanHistoryId;
        this.comment = comment;
        this.date = date;
        this.loan = loan;
        this.loanStatus = loanStatus;
    }

    public LoanHistory() {
        
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getLoanHistoryId() {
        return loanHistoryId;
    }

    public void setLoanHistoryId(int loanHistoryId) {
        this.loanHistoryId = loanHistoryId;
    }

    public LoanStatus getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(LoanStatus loanStatus) {
        this.loanStatus = loanStatus;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public String getDate() {
        return date;
    }
}
