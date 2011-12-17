/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loansystem.service;

import com.loansystem.db.dao.LoanOfferHome;
import com.loansystem.model.Client;
import com.loansystem.model.LoanOffer;
import java.util.ArrayList;

/**
 *
 * @author antonve
 */
public class LoanOfferServiceImpl implements LoanOfferService {

    public ArrayList<LoanOffer> getAvailableLoanOffers(Client client) {
        LoanOfferHome loanOfferHome = new LoanOfferHome();
        ArrayList<LoanOffer> availableLoanOffers = null;
        availableLoanOffers = loanOfferHome.getAvailableLoanOffers(client);
        return availableLoanOffers;
    }
    
}
