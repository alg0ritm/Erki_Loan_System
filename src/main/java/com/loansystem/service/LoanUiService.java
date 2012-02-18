/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loansystem.service;

import com.loansystem.UI.client.ExistingLoanRequestPanel;
import com.loansystem.backend.model.LoanTabModel;
import com.loansystem.model.Client;
import javax.swing.JPanel;

/**
 *
 * @author antonve
 */
public interface LoanUiService {
    public ExistingLoanRequestPanel createExistingLoanRequestPanel(Client loginClient, LoanTabModel loanTabModel);
}
