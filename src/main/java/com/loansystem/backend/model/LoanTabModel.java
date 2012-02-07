/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loansystem.backend.model;

import com.loansystem.model.Client;
import com.loansystem.model.Loan;
import com.loansystem.model.LoanOffer;

/**
 *
 * @author antonve
 */
public class LoanTabModel {
    
    private Loan lastLoan;
    private Client client;
    private LoanOffer loanOffer;

    public LoanTabModel(Client loginClient) {
        this.client = loginClient;
        this.lastLoan = client.getLoans().get(0);
        this.loanOffer = lastLoan.getLoanOffer();
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

   

    public Loan getLastLoan() {
        return lastLoan;
    }

    public void setLastLoan(Loan lastLoan) {
        this.lastLoan = lastLoan;
    }
    
}
