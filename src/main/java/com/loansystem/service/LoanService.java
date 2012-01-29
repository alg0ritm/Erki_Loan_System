/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loansystem.service;

import com.loansystem.backend.model.LoanInsertRequest;
import com.loansystem.model.Client;
import com.loansystem.model.Loan;
import com.loansystem.model.LoanOffer;
import java.util.ArrayList;

/**
 *
 * @author antonve
 */
public interface LoanService {
    public ArrayList<LoanOffer> getAvailableLoanOffers(Client client);
    public int createNewLoan(Client client, LoanOffer loanOffer);
    
    public int removeExistingLoanRequest(Client client);
    
}
