/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loansystem.service;

import com.loansystem.backend.model.LoanInsertRequest;
import com.loansystem.db.dao.ClientHome;
import com.loansystem.db.dao.LoanHome;
import com.loansystem.db.dao.LoanOfferHome;
import com.loansystem.db.dao.LoanStatusHome;
import com.loansystem.model.Client;
import com.loansystem.model.Loan;
import com.loansystem.model.LoanOffer;
import com.loansystem.model.LoanStatus;
import com.loansystem.util.DateUtil;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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

    public int createNewLoan(Client client, LoanOffer loanOffer) {
        LoanStatus loanStatus = new LoanStatus();
        LoanStatusHome loanStatusHome = new LoanStatusHome();
        loanStatus = loanStatusHome.findByName("Pending");
        
        LoanOfferHome loanLoanOfferHome = new LoanOfferHome();
        LoanOffer loanOffer1  =  loanLoanOfferHome.findBy4Parameters(loanOffer);
        
        Loan insertLoan = new Loan(client, loanOffer1, loanStatus);
        
        
        LoanHome loanHome = new LoanHome();
        try {
            loanHome.insertLoan(insertLoan);
        }
        catch(Exception e)
        {
            log.info("LoanServiceImpl : createNewLoan failed " + e.getStackTrace() );
        }
        
        
        //LoanStatus instance = (LoanStatus) session.createCriteria("LoanStatus").add(Restrictions.eq("name", "Pending"));
        return 1;
    }

    @Override
    public int removeExistingLoanRequest(Client client) {
        /*ClientHome clientHome = new ClientHome();
        clientHome.*/
        LoanHome loanHome = new LoanHome();
        Loan lastLoan = loanHome.findLastLoanForClient(client);
        
        try {
            loanHome.delete(lastLoan);
        }catch(Exception e)
        {
            log.info("LoanServiceImpl : removeExistingLoanRequest error occured "+ e );
            return 0;
        }
        return 1;
        
    }
}
