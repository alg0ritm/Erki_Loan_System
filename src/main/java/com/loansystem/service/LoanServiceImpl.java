/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loansystem.service;

import com.loansystem.backend.model.LoanInsertRequest;

import com.loansystem.backend.model.LoanTabModel;
import com.loansystem.db.dao.ClientHome;
import com.loansystem.db.dao.LoanHistoryHome;
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
import com.loansystem.model.LoanHistory;
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

        LoanHistoryHome loanHistoryHome = new LoanHistoryHome();

        Loan insertLoan = new Loan(client, loanOffer1, loanStatus);
        LoanHistory loanHistory = new LoanHistory(insertLoan, loanStatus, "Default comment");





        LoanHome loanHome = new LoanHome();
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction transaction = session.beginTransaction();
            loanHome.insertLoan(insertLoan, session);
            loanHistoryHome.insertLoanHistory(loanHistory, session);
            transaction.commit();
            insertLoan = loanHome.findById(insertLoan.getLoanId(), null);
            

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

        LoanHistoryHome loanHistoryHome = new LoanHistoryHome();
        ArrayList<LoanHistory> loanHistory = new ArrayList<LoanHistory>();
        loanHistory.addAll(lastLoan.getLoanHistory());
        LoanHistory loanHistoryLast = loanHistory.get(0);

        Transaction transaction = null;

        //log.info("LoanServiceImpl : removeExistingLoanRequest error occured " + lastLoan.getDueDate() );

        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();

            //lastLoan.setLoanHistory(null);
            loanHome.delete(lastLoan, session);
            
            //loanHistoryLast.setLoan(null);
            loanHistoryHome.delete(loanHistoryLast, session);


            transaction.commit();

        } catch (Exception e) {
            log.info("LoanServiceImpl : removeExistingLoanRequest error occured " + e);
            transaction.rollback();
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
    public void mergeLoan(Loan selectedLoan, Session session) {
        LoanHome loanHome = new LoanHome();
        loanHome.merge(selectedLoan, session);
    }

    public void saveLoanHistory(LoanHistory loanHistory, Session session) {
        LoanHistoryHome loanHistoryHome = new LoanHistoryHome();
        loanHistoryHome.save(loanHistory, session);

    }

    @Override
    public void saveLoanWithStatus(Loan selectedLoan, int loanStatus) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        LoanStatus selectedStatus = getStatusById(loanStatus);
        selectedLoan.setLoanStatus(selectedStatus);
        LoanHistory loanHistory = updateLoanHistory(selectedLoan);

        try {
            mergeLoan(selectedLoan, session);
            saveLoanHistory(loanHistory, session);
            transaction.commit();
        } catch (Exception e) {
            log.fatal("saveLoanWithStatus : Failed to insert data");
            transaction.rollback();
        }



    }

    private LoanHistory updateLoanHistory(Loan selectedLoan) {
        LoanHistory loanHistory = new LoanHistory();
        loanHistory.setComment("");
        Date dateCreated = new Date();
        String dueDateString = DateUtil.dateFormat.format(dateCreated);
        loanHistory.setDate(dueDateString);
        loanHistory.setLoanStatus(selectedLoan.getLoanStatus());
        loanHistory.setLoan(selectedLoan);

        return loanHistory;
    }

    @Override
    public ArrayList<Loan> getLoansByStatus(int loanStatus) {
        LoanHome loanHome = new LoanHome();
        LoanStatus loanStatus1 = getStatusById(loanStatus);
        ArrayList<Loan> loans = loanHome.getLoansByStatus(loanStatus1, null);
        return loans;
    }

    public ArrayList<Loan> getPostponedLoansByStatus(int loanStatus) {
        LoanHome loanHome = new LoanHome();
        LoanStatus loanStatus1 = getStatusById(loanStatus);
        ArrayList<Loan> loans = loanHome.getLoansByStatus(loanStatus1, null);
        return loans;
    }

    @Override
    public PostponeRequestStatus getPostponeRequestStatusById(int id) {
        PostponeRequestStatusHome postponeRequestStatusHome = new PostponeRequestStatusHome();
        PostponeRequestStatus postponeRequestStatus = postponeRequestStatusHome.findById(id + "");
        return postponeRequestStatus;
    }

    @Override
    public ArrayList<Loan> getPostponedLoansByStatus(PostponeRequestStatus postponeRequestStatus) {
        ArrayList<Loan> postponeRequestedLoans = null;
        LoanHome loanHome = new LoanHome();
        postponeRequestedLoans = loanHome.getPostponedLoans(postponeRequestStatus.getId(), null);
        return postponeRequestedLoans;
    }
}
