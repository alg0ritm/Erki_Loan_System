/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loansystem.service;

import com.loansystem.backend.model.LoanInsertRequest;

import com.loansystem.backend.model.LoanTabModel;
import com.loansystem.model.Client;
import com.loansystem.model.Loan;
import com.loansystem.model.LoanOffer;
import com.loansystem.model.PostponeRequest;
import java.util.ArrayList;

/**
 *
 * @author antonve
 */
public interface LoanService {
    public ArrayList<LoanOffer> getAvailableLoanOffers(Client client);
    public Loan createNewLoan(Client client, LoanOffer loanOffer);
    
    public int removeExistingLoanRequest(Client client);

    public Loan getLastLoan(Client client);

    public int createPostponeRequest(PostponeRequest postponeRequest);

    public int updateLastLoanToPostponeRequesed(LoanTabModel loanTabModel, String dueDate, String sum);
    
}
