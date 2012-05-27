/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loansystem.service;

import com.loansystem.backend.model.LoanInsertRequest;

import com.loansystem.backend.model.LoanTabModel;
import com.loansystem.classificator.ClientStatusClassificator;
import com.loansystem.db.dao.ClientGroupHome;
import com.loansystem.db.dao.ClientHistoryHome;
import com.loansystem.db.dao.ClientHome;
import com.loansystem.db.dao.ClientStatusHome;
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
import com.loansystem.model.ClientGroup;
import com.loansystem.model.ClientHistory;
import com.loansystem.model.ClientStatus;
import com.loansystem.model.Loan;
import com.loansystem.model.LoanHistory;
import com.loansystem.model.LoanHistoryCollection;
import com.loansystem.model.LoanOffer;
import com.loansystem.model.LoanStatus;
import com.loansystem.model.PostponeRequest;
import com.loansystem.model.PostponeRequestStatus;
import com.loansystem.util.DateUtil;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        LoanHistory loanHistory = new LoanHistory(insertLoan, loanStatus);





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
        LoanHistoryCollection loanHistory = new LoanHistoryCollection();
        ArrayList<LoanHistory> loanHistoryList = loanHistoryHome.findByLoanId(lastLoan, null);
        //loanHistory.addAll(lastLoan.getLoanHistory());
        //LoanHistory loanHistoryLast = loanHistory.get(0);

        Transaction transaction = null;

        //log.info("LoanServiceImpl : removeExistingLoanRequest error occured " + lastLoan.getDueDate() );

        try {
            HibernateUtil.getSessionFactory().getCurrentSession().close();
            Session session = HibernateUtil.openNewSession();
            transaction = session.beginTransaction();

            //lastLoan.getLoanHistory().get(0).setLoan(null); works
            //lastLoan.setLoanHistory(null);
            loanHistoryHome.delete(loanHistoryList.get(0), session);

            loanHome.delete(lastLoan, session);



            //loanHistoryLast.setLoan(null);
            //loanHistoryHome.delete(loanHistoryLast, session);


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
    public int createPostponeRequest(LoanTabModel loanTabModel, PostponeRequest postponeRequest) {
        int result = 0;
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            Loan lastLoan = loanTabModel.getLastLoan();
            /*saveLoanWithStatus(lastLoan, LoanStatusInterface.POSTPONED, session, "Postpone requested created");*/

            PostponeRequestHome postponeRequestHome = new PostponeRequestHome();
            result = postponeRequestHome.savePostponeRequest(postponeRequest, session);

            lastLoan.setPostponeRequest(postponeRequest);
            mergeLoan(lastLoan, session);

            //postponeRequest.setEmployeeId(null);


            tx.commit();

            return result;
        } catch (Exception e) {
            log.error("createPostponeRequest() " + e.getMessage());
            return 0;
        }
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


        //LoanStatus lastStatus = test.findById(String.valueOf(statusId));
        //update loan in case of of postponeRequest.Requested
        if (dueDate != null && sum != null) {
            /* loanTabModel.getLastLoan().setDueDate(dueDate);
            loanTabModel.getLastLoan().setDebt(sum);*/
            //loanTabModel.getLastLoan().setLoanStatus(lastStatus);
            LoanHome loanHome = new LoanHome();
            loanHome.merge(loanTabModel.getLastLoan(), session);
            result = 1;
        }

        LoanHistory loanHistory = updateLoanHistory(loanTabModel.getLastLoan(), "Loan postpone staus change to " + postponeRequestStatusId);

        saveLoanHistory(loanHistory, session);
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
    public Loan getLoanById(Loan selectedLoan, Session session) {
        LoanHome loanHome = new LoanHome();
        Loan loan = loanHome.findById(selectedLoan.getLoanId(), session);
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
    public void saveLoanWithStatus(Loan selectedLoan, int loanStatus, Session session, String comment) {
        Session sessionLoc = null;
        Transaction transaction = null;
        if (session == null) {
            sessionLoc = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = sessionLoc.beginTransaction();
        } else {
            sessionLoc = session;
        }
        LoanStatus selectedStatus = getStatusById(loanStatus);
        selectedLoan.setLoanStatus(selectedStatus);
        LoanHistory loanHistory = updateLoanHistory(selectedLoan, comment);

        try {
            mergeLoan(selectedLoan, sessionLoc);
            saveLoanHistory(loanHistory, sessionLoc);
            if (session == null) {
                transaction.commit();
            }
        } catch (Exception e) {
            log.fatal("saveLoanWithStatus : Failed to insert data");
            transaction.rollback();
        }



    }

    private LoanHistory updateLoanHistory(Loan selectedLoan, String comment) {
        LoanHistory loanHistory = new LoanHistory();
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

    @Override
    public void updateLoanHistoryForLoan(LoanTabModel loanTabModel, int loanStatusId) {
        Transaction transaction = null;
        Loan lastLoan = loanTabModel.getLastLoan();
        Client client = lastLoan.getClient();
        ClientHistory clientHistory1 = null;

        LoanStatus loanStatus = getStatusById(loanStatusId);


        LoanHistory loanHistory = new LoanHistory(lastLoan, loanStatus);

        //ClientHistory clientHistory = new ClientHistory();

        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();





            if (loanStatusId == LoanStatusInterface.PAYED_BACK) {

                ClientHistoryHome clientHistoryHome = new ClientHistoryHome();
                ClientHistory clientHistory = null;
                if (client.getClientHistory() != null && client.getClientHistory().size() > 0) {
                    clientHistory = client.getClientHistory().get(0); //find last client history
                } else {
                    clientHistory = new ClientHistory();
                    clientHistory.setClient(client);
                    clientHistory.setClientGroup(client.getClientGroup());
                    clientHistory.setClientStatus(client.getClientStatus());
                }
                LoanHistoryHome loanHistoryHome = new LoanHistoryHome();
                ArrayList<LoanHistory> loanHistory1 = loanHistoryHome.findByLoanId(lastLoan, session);


                double rating = calculateRating(loanHistory1, lastLoan);

                clientHistory.setNewRating(rating);
                clientHistory.setDate(DateUtil.dateFormat.format(new Date()));
                saveClientHistory(clientHistory, session);
                String clientHistoryComment = "rating changed";
                client.setRating(rating + "");


                if (client.getClientGroup().getMaxRating() < rating) {
                    ClientGroup newClientGroup = new ClientGroup();
                    ClientGroupHome clientGroupHome = new ClientGroupHome();
                    newClientGroup = clientGroupHome.findByRating(rating, session);
                    if (newClientGroup != null) {
                        client.setClientGroup(newClientGroup);
                        clientHistory1 = clientHistoryHome.findByClient(client, session);
                        clientHistory1.setDate(DateUtil.dateFormat.format(new Date()));

                        clientHistory1.setClientGroup(newClientGroup);
                    }

                }



            }
            mergeClient(client, session);
            lastLoan.setLoanStatus(loanStatus);
            saveLoanHistory(loanHistory, session);
            mergeLoan(lastLoan, session);
            if (clientHistory1 != null) {
                saveClientHistory(clientHistory1, session);
            }
            /* if(loanStatusId == LoanStatusInterface.SENT_TO_DEBT_COLLECTION) {
            
            }*/
            /*saveClientHistory()
            mergeClient()*/


            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
            log.error("updateLoanHistoryForLoan " + e);
        }




    }

    private void mergeClientHistory(ClientHistory clientHistory, Session session) {
        ClientHistoryHome clientHistoryHome = new ClientHistoryHome();
        clientHistoryHome.merge(clientHistory, session);
    }

    private void mergeClient(Client client, Session session) {
        ClientHome clientHome = new ClientHome();
        clientHome.merge(client, session);
    }

    private void saveClientHistory(ClientHistory clientHistory, Session session) {
        ClientHistoryHome clientHistoryHome = new ClientHistoryHome();
        clientHistoryHome.save(clientHistory, session);
    }

    private double calculateRating(ArrayList<LoanHistory> loanHistory, Loan lastLoan) {
        double newRating = 0.0F;
        double lastClientRating = Double.parseDouble(lastLoan.getClient().getRating());
        double loanOfferRatingBonus = Double.parseDouble(lastLoan.getLoanOffer().getRatingBonus());
        /*
        I. Esli u loana est' pojavljaetsa Postpone request to : 
        1) V slu4ae accepta postpone requesta loan status menjaetsa na POSTPONED.
        2) V slu4ae rejecta, canela postpone reuqesta loan status ostaetsa loan issued no menjaestsa na OVERDUE po nastupleniju datq vqplatq.
        3) V slu4ae kogda postpone rquest status ostaetsa requestd(TL ne uspvaet prinjat' reshenie v otnoshenii nego) i client vqpla4ivaet nesmotrja na eto dolg to newRating = lastClientRating + loanOfferRatingBonus;
        
        V slu4ajah(1,2) newRating = (lastClientRating) + (loanOfferRatingBonus / 2);
        
        II. takie ze pravila deistvujut k loanu bez postpone reuqesta: 
        1) Loan issued : newRating = lastClientRating + loanOfferRatingBonus;
        2) OVERDUE : newRating = (lastClientRating) + (loanOfferRatingBonus / 2);
        
        V slu4ae (2) debt = summa nabezavshaja po u4etu standartnoi formulq za dannqi period((data vqplatq)-(loanIssued date)) + 10 euro	(kommisija)
        
        III. Esli Loan v statuse OVERDUE perevalivaet za srok loanOffer/2 to loan status menjaetsa na SENT_TO_DEBT_COLLECTION. 
        
        
        IV. Maksimalno vomznoq srok na kotorqi mozno vzjat' postpone request = loanOffer/2; 
         */
        if (lastLoan.getPostponeRequest() != null) {
            switch (Integer.parseInt(lastLoan.getPostponeRequest().getPostponeRequestStatus().getId())) {
                case com.loansystem.classificator.PostponeRequestStatus.ACCEPTED:
                    newRating = lastClientRating + loanOfferRatingBonus / 2;
                    break;
                case com.loansystem.classificator.PostponeRequestStatus.REQUESTED:
                    newRating = lastClientRating + loanOfferRatingBonus;
                    break;
                case com.loansystem.classificator.PostponeRequestStatus.REJECTED:
                    newRating = calculateRatingAfterLoanStatus(loanHistory, lastLoan);
                    break;
                case com.loansystem.classificator.PostponeRequestStatus.CANCELED:
                    newRating = calculateRatingAfterLoanStatus(loanHistory, lastLoan);
                    break;

            }
        } else {
            newRating = calculateRatingAfterLoanStatus(loanHistory, lastLoan);
        }

        return newRating;
    }

    private double calculateRatingAfterLoanStatus(ArrayList<LoanHistory> loanHistory, Loan lastLoan) {
        double newRating = 0.0F;
        double lastClientRating = Double.parseDouble(lastLoan.getClient().getRating());
        double loanOfferRatingBonus = Double.parseDouble(lastLoan.getLoanOffer().getRatingBonus());

        switch (Integer.parseInt(lastLoan.getLoanStatus().getLoanStatusId())) {
            case LoanStatusInterface.ISSUED:
                newRating = lastClientRating + loanOfferRatingBonus;
                break;
            case LoanStatusInterface.OVERDUE:
                newRating = lastClientRating + loanOfferRatingBonus / 2;
                break;

        }
        return newRating;
    }

    @Override
    public void savePostponedLoanWithStatus(Loan selectedLoan, int postponeStatusId, Session session) {

        PostponeRequest postponeRequest = selectedLoan.getPostponeRequest();
        Session sessionLoc = null;
        Transaction transaction = null;
        if (session == null) {
            sessionLoc = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = sessionLoc.beginTransaction();
        } else {
            sessionLoc = session;
        }
        PostponeRequestStatus selectedStatus = getPostponeStatusById(postponeStatusId);


        if (postponeStatusId == com.loansystem.classificator.PostponeRequestStatus.ACCEPTED) {
            try {
                //vqnesti v metod uze est'?
                Date currenDueDate = DateUtil.dateFormat.parse(selectedLoan.getDueDate());
                Date newDueDate = DateUtil.getDatePlusDays(currenDueDate, postponeRequest.getPeriodDays());
                String newDueDateString = DateUtil.dateFormat.format(newDueDate);
                selectedLoan.setDueDate(newDueDateString + "");
                saveLoanWithStatus(selectedLoan, LoanStatusInterface.POSTPONED, sessionLoc, "Loan is postponed");
            } catch (ParseException ex) {
                Logger.getLogger(LoanServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (postponeStatusId == com.loansystem.classificator.PostponeRequestStatus.REJECTED) {
            //vqnesti v metod uze est'?

            saveLoanWithStatus(selectedLoan, LoanStatusInterface.ISSUED, sessionLoc, "Loan postpone is rejected");
        }
        selectedLoan.getPostponeRequest().setPostponeRequestStatus(selectedStatus);
        PostponeRequestHome postponeRequestHome = new PostponeRequestHome();
        postponeRequestHome.merge(selectedLoan.getPostponeRequest(), sessionLoc);


        try {
            //mergeLoan(selectedLoan, sessionLoc);
            //saveLoanHistory(loanHistory, sessionLoc);
            if (session == null) {
                transaction.commit();
            }
        } catch (Exception e) {
            log.fatal("savePostponedLoanWithStatus : Failed to insert data");
            transaction.rollback();
        }
    }

    private PostponeRequestStatus getPostponeStatusById(int postponeStatusId) {
        PostponeRequestStatusHome postponeRequestStatusHome = new PostponeRequestStatusHome();
        PostponeRequestStatus postponeRequestStatus = postponeRequestStatusHome.findById(postponeStatusId + "");
        return postponeRequestStatus;
    }

    @Override
    public void saveClientWithStatus(Client client, int statusId, Session session) {

        Session sessionLoc = null;
        Transaction transaction = null;
        try {
            if (session == null) {
                sessionLoc = HibernateUtil.getSessionFactory().getCurrentSession();
                transaction = sessionLoc.beginTransaction();
            } else {
                sessionLoc = session;
            }
            ClientHistory clientHistory = client.getClientHistory().get(0); //find last client history
            ClientHistory clientHistory1 = new ClientHistory();
            ClientHistoryHome clientHistoryHome = new ClientHistoryHome();

            ClientStatus clientStatus = getClientStatusById(statusId, sessionLoc);
            client.setClientStatus(clientStatus);
            double rating = 0;


            if (statusId == ClientStatusClassificator.BLACKLISTED) {
                rating = -1;
            }


            clientHistory.setNewRating(rating);
            clientHistory.setDate(DateUtil.dateFormat.format(new Date()));
            String clientHistoryComment = "client status changed to blacklisted";
            saveClientHistory(clientHistory, session);

            client.setRating(rating + "");



            ClientGroup newClientGroup = new ClientGroup();
            ClientGroupHome clientGroupHome = new ClientGroupHome();
            newClientGroup = clientGroupHome.findByName("Blacklisted", session);
            if (newClientGroup != null) {
                client.setClientGroup(newClientGroup);
                clientHistory1 = clientHistoryHome.findByClient(client, session);
                clientHistory1.setDate(DateUtil.dateFormat.format(new Date()));

                clientHistory1.setClientGroup(newClientGroup);
            }


            ClientHome clientHome = new ClientHome();
            clientHome.merge(client, sessionLoc);

            if (session == null) {
                transaction.commit();
            }
        } catch (Exception e) {
            log.fatal("saveLoanWithStatus : Failed to insert data");
            transaction.rollback();
        }

    }

    private ClientStatus getClientStatusById(int statusId, Session session) {
        ClientStatusHome clientStatusHome = new ClientStatusHome();
        ClientStatus clientStatus = clientStatusHome.findById(statusId + "", session);
        return clientStatus;
    }

    @Override
    public ArrayList<LoanHistory> getLoanHistory(Loan currentLoan) {
        LoanHistoryHome loanHistoryHome = new LoanHistoryHome();
        ArrayList<LoanHistory> loanHistory = loanHistoryHome.findByLoanId(currentLoan, null);
        return loanHistory;
    }

    @Override
    public void removeExistingPostponeRequest(LoanTabModel loanTabModel, Client client) {
        Session sessionLoc = null;
        Loan lastLoan = loanTabModel.getLastLoan();
        PostponeRequest postponeRequest = lastLoan.getPostponeRequest();
        try {
            sessionLoc = HibernateUtil.createRequieredSession(sessionLoc);
           
            
            removeExisitingPostponeRequest(postponeRequest, sessionLoc);
            lastLoan.setPostponeRequest(null); //removing postpone request
            mergeLoan(lastLoan, sessionLoc);
            sessionLoc.getTransaction().commit();


        } catch (Exception E) {
            log.fatal("removeExistingPostponeRequest : Failed to remove postpone request " + E);
            sessionLoc.getTransaction().rollback();
        }
    }

    private void removeExisitingPostponeRequest(PostponeRequest postponeRequest, Session sessionLoc) {
        PostponeRequestHome postponeRequestHome = new PostponeRequestHome();
        postponeRequestHome.delete(postponeRequest, sessionLoc);
    }
}
