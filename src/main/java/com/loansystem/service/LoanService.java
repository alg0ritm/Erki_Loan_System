/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loansystem.service;

import com.loansystem.backend.model.LoanInsertRequest;

import com.loansystem.backend.model.LoanTabModel;
import com.loansystem.enums.LoanStatusEnum;
import com.loansystem.enums.LoanStatusInterface;
import com.loansystem.model.Client;
import com.loansystem.model.Loan;
import com.loansystem.model.LoanHistory;
import com.loansystem.model.LoanOffer;
import com.loansystem.model.LoanStatus;
import com.loansystem.model.PostponeRequest;
import com.loansystem.model.PostponeRequestStatus;
import java.util.ArrayList;
import org.hibernate.Session;

/**
 *
 * @author antonve
 */
public interface LoanService {

    public ArrayList<LoanOffer> getAvailableLoanOffers(Client client);

    public Loan createNewLoan(Client client, LoanOffer loanOffer);

    public int removeExistingLoanRequest(LoanTabModel loanTaModel, Client client);

    public Loan getLastLoan(Client client);

    public int createPostponeRequest(LoanTabModel loanTabModel, PostponeRequest postponeRequest);

    public PostponeRequest updateLastPostponedLoan(LoanTabModel loanTabModel, String dueDate, String sum, int postponeRequestStatus);

    public void cancelExistingPostponeRequest(LoanTabModel loanTabModel, Client client);

    public Loan getLoanById(Loan selectedLoan, Session session);

    public LoanStatus getStatusById(int loanStatus);

    public void mergeLoan(Loan selectedLoan, Session session);

    public void saveLoanWithStatus(Loan selectedLoan, int loanStatus, Session session, String comment);

    public void saveLoanHistory(LoanHistory loanHistory, Session session);

    public ArrayList<Loan> getLoansByStatus(int PENDING);

    public ArrayList<Loan> getPostponedLoansByStatus(PostponeRequestStatus postponeRequestStatus);
    
    public PostponeRequestStatus getPostponeRequestStatusById(int id);

    public void updateLoanHistoryForLoan(LoanTabModel loanTabModel, int loanStatusId);

    public void savePostponedLoanWithStatus(Loan selectedLoan, int statusId, Session session);

    public void saveClientWithStatus(Client client, int statusId, Session session);

    public ArrayList<LoanHistory> getLoanHistory(Loan currentLoan);
}
