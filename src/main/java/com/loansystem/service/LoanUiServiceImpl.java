/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loansystem.service;

import com.loansystem.UI.client.ExistingLoanRequestControls;
import com.loansystem.UI.client.ExistingLoanRequestPanel;
import com.loansystem.UI.client.NewLoanRequestPanel;
import com.loansystem.UI.client.PostponeRequestPanel;
import com.loansystem.backend.model.LoanTabModel;
import com.loansystem.model.Client;
import javax.swing.JPanel;

/**
 *
 * @author antonve
 */
public class LoanUiServiceImpl implements LoanUiService {

    public ExistingLoanRequestPanel createExistingLoanRequestPanel(Client loginClient, LoanTabModel loanTabModel ) {
        

        ExistingLoanRequestPanel existingLoanRequestPanel = new ExistingLoanRequestPanel(loanTabModel, true); //client to upper level
        //NewLoanRequestPanel newLoanRequestPanel = new NewLoanRequestPanel(loginClient, false);
        PostponeRequestPanel postponeRequestPanel = new PostponeRequestPanel(false, loanTabModel);
        ExistingLoanRequestControls existingLoanRequestControls = new ExistingLoanRequestControls(loanTabModel);
        
        loanTabModel.setExisitingLoanRequestPanel(existingLoanRequestPanel);
        //loanTabModel.setNewLoanRequestPanel(newLoanRequestPanel);
        loanTabModel.setExistingLoanRequestControls(existingLoanRequestControls);
        loanTabModel.setPostponeRequestPanel(postponeRequestPanel);
        
        
        
        return existingLoanRequestPanel;
    }
}
