/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loansystem.backend.model;

import com.loansystem.enums.UserType;
import com.loansystem.model.Loan;
import com.loansystem.model.LoanOffer;
import com.loansystem.model.LoanStatus;

/**
 *
 * @author antonve
 */
public class LoanModel {
    
    UserType userType;
    LoanOffer loanOffer;
    Loan loan;
    LoanStatus loanStatus;

    public LoanStatus getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(LoanStatus loanStatus) {
        this.loanStatus = loanStatus;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public LoanOffer getLoanOffer() {
        return loanOffer;
    }

    public void setLoanOffer(LoanOffer loanOffer) {
        this.loanOffer = loanOffer;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
    
}
