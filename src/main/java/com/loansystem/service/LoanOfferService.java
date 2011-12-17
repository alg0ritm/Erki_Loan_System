/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loansystem.service;

import com.loansystem.model.Client;
import com.loansystem.model.LoanOffer;
import java.util.ArrayList;

/**
 *
 * @author antonve
 */
public interface LoanOfferService {
    public ArrayList<LoanOffer> getAvailableLoanOffers(Client client);
    
    
}
