/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loansystem.service;

import com.loansystem.backend.model.LoanInsertRequest;

import com.loansystem.backend.model.LoanTabModel;
import com.loansystem.db.dao.ClientHome;
import com.loansystem.db.dao.LoanHome;
import com.loansystem.db.dao.LoanOfferHome;
import com.loansystem.db.dao.LoanStatusHome;
import com.loansystem.db.dao.PostponeRequestHome;
import com.loansystem.db.dao.PostponeRequestStatusHome;
import com.loansystem.enums.LoanStatusEnum;
import com.loansystem.enums.LoanStatusInterface;
import com.loansystem.hibernate.HibernateUtil;
import com.loansystem.model.Client;
import com.loansystem.model.Loan;
import com.loansystem.model.LoanOffer;
import com.loansystem.model.LoanStatus;
import com.loansystem.model.PostponeRequest;
import com.loansystem.model.PostponeRequestStatus;
import com.loansystem.util.DateUtil;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author antonve
 */
public class LoanServiceImpl implements LoanService {

    private static final Log log = LogFactory.getLog(LoanServiceImpl.class);
    private final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public ArrayList<LoanOffer> getAvailableLoanOffers(Client client) {
        LoanOfferHome loanOfferHome = new LoanOfferHome();
        ArrayList<LoanOffer> availableLoanOffers = null;
        availableLoanOffers = loanOfferHome.getAvailableLoanOffers(client);
        return availableLoanOffers;
    }

    public Loan createNewLoan(Client client, LoanOffer loanOffer) {
        LoanStatus loanStatus = new LoanStatus();
        LoanStatusHome loanStatusHome = new LoanStatusHome();
        loanStatus = loanStatusHome.findByName("Pending");

        LoanOfferHome loanLoanOfferHome = new LoanOfferHome();
        LoanOffer loanOffer1 = loanLoanOfferHome.findBy4Parameters(loanOffer);

        Loan insertLoan = new Loan(client, loanOffer1, loanStatus);


        LoanHome loanHome = new LoanHome();
        try {
            loanHome.insertLoan(insertLoan);
        } catch (Exception e) {

            log.info("LoanServiceImpl : createNewLoan failed " + e.getStackTrace());
            return null;
        }


        //LoanStatus instance = (LoanStatus) session.createCriteria("LoanStatus").add(Restrictions.eq("name", "Pending"));
        return insertLoan;
    }

    @Override
    public int removeExistingLoanRequest(LoanTabModel loanTabModel, Client client) {
        /*ClientHome clientHome = new ClientHome();
        clientHome.*/
        LoanHome loanHome = new LoanHome();
        Loan lastLoan = loanTabModel.getLastLoan();

        //log.info("LoanServiceImpl : removeExistingLoanRequest error occured " + lastLoan.getDueDate() );

        try {
            loanHome.delete(lastLoan);
        } catch (Exception e) {
            log.info("LoanServiceImpl : removeExistingLoanRequest error occured " + e);
            return 0;
        }
        return 1;

    }

    @Override
    public Loan getLastLoan(Client client) {
        LoanHome loanHome = new LoanHome();
        //Loan lastLoan = loanHome.findLastLoanForClient(client);
        return null;

        //return createPostponedLoan(lastLoan);
    }

    private int createPostponedLoan(Loan lastLoan) {
        return 0;
    }

    @Override
    public int createPostponeRequest(PostponeRequest postponeRequest) {

        PostponeRequestHome postponeRequestHome = new PostponeRequestHome();
        int result = postponeRequestHome.savePostponeRequest(postponeRequest);
        return result;
    }

    @Override
    @Transactional
    public PostponeRequest updateLastPostponedLoan(LoanTabModel loanTabModel, String dueDate, String sum, int postponeRequestStatusId) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        int result = 0;
        PostponeRequest postponeRequest = null;
        session.beginTransaction();
        postponeRequest = loanTabModel.getLastLoan().getPostponeRequest();
        if (postponeRequest == null) {
            postponeRequest = new PostponeRequest();
        }
        PostponeRequestStatusHome postponeRequestStatusHome = new PostponeRequestStatusHome();
        PostponeRequestStatus postponeRequestStatus = postponeRequestStatusHome.findById(String.valueOf(postponeRequestStatusId));
        postponeRequest.setStatusId(postponeRequestStatus);
        PostponeRequestHome postponeRequestHome = new PostponeRequestHome();
        postponeRequestHome.merge(postponeRequest, session);


        LoanStatusHome test = new LoanStatusHome();
        //LoanStatus lastStatus = test.findById(String.valueOf(statusId));
        //update loan in case of of postponeRequest.Requested
        if (dueDate != null && sum != null) {
            loanTabModel.getLastLoan().setDueDate(dueDate);
            loanTabModel.getLastLoan().setDebt(sum);
            //loanTabModel.getLastLoan().setLoanStatus(lastStatus);
            LoanHome loanHome = new LoanHome();
            loanHome.merge(loanTabModel.getLastLoan(), session);
            result = 1;
        }
        /*postponeRequest = null;
        int statusId = postponeRequest.getPostponeRequestId();*/
        tx.commit();
        return postponeRequest;
    }

    @Override
    public void cancelExistingPostponeRequest(LoanTabModel loanTabModel, Client client) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Loan getLoanById(Loan selectedLoan) {
        LoanHome loanHome = new LoanHome();
        Loan loan = loanHome.findById(selectedLoan.getLoanId(), null);
        return loan;
    }

    @Override
    public LoanStatus getStatusById(int loanStatus) {
        LoanStatusHome loanStatusHome = new LoanStatusHome();
        LoanStatus loanStatus1 = loanStatusHome.findById(loanStatus + "");
        return loanStatus1;
    }

    @Override
    public void merge(Loan selectedLoan, Session session ) {
        LoanHome loanHome = new LoanHome();
        loanHome.merge(selectedLoan, session);
    }

    @Override
    public void saveLoanWithStatus(Loan selectedLoan) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        LoanStatus selectedStatus = getStatusById(LoanStatusInterface.ISSUED);
        selectedLoan.setLoanStatus(selectedStatus);
        merge(selectedLoan, session);
    }
}
