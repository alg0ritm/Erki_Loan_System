/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loansystem.backend.model;

import com.loansystem.UI.client.ExistingLoanRequestControls;
import com.loansystem.UI.client.ExistingLoanRequestPanel;
import com.loansystem.UI.client.NewLoanRequestPanel;
import com.loansystem.UI.client.PostponeRequestPanel;
import com.loansystem.model.Client;
import com.loansystem.model.Loan;
import com.loansystem.model.LoanOffer;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author antonve
 */
public class LoanTabModel {

    private Loan lastLoan;
    private Client client;
    private LoanOffer loanOffer;
    private ExistingLoanRequestPanel existingLoanRequestPanel;
    private NewLoanRequestPanel newLoanRequestPanel;
    private ExistingLoanRequestControls existingLoanRequestControls;
    private PostponeRequestPanel postponeRequestPanel;

    public LoanTabModel(Client loginClient) {
        this.client = loginClient;
        if (client.getLoans().size() > 0) {
            this.lastLoan = client.getLoans().get(0);
            this.loanOffer = lastLoan.getLoanOffer();
        }
    }

    public Client getClient() {
        return client;
    }

    public ExistingLoanRequestPanel getExistingLoanRequestPanel() {
        return existingLoanRequestPanel;
    }

    public void setExistingLoanRequestPanel(ExistingLoanRequestPanel existingLoanRequestPanel) {
        this.existingLoanRequestPanel = existingLoanRequestPanel;
    }

    public LoanOffer getLoanOffer() {
        return loanOffer;
    }

    public ExistingLoanRequestControls getExistingLoanRequestControls() {
        return existingLoanRequestControls;
    }

    public NewLoanRequestPanel getNewLoanRequestPanel() {
        return newLoanRequestPanel;
    }

    public PostponeRequestPanel getPostponeRequestPanel() {
        return postponeRequestPanel;
    }

    public void setLoanOffer(LoanOffer loanOffer) {
        this.loanOffer = loanOffer;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Loan getLastLoan() {
        return lastLoan;
    }

    public void setLastLoan(Loan lastLoan) {
        this.lastLoan = lastLoan;
    }

    public void setExisitingLoanRequestPanel(ExistingLoanRequestPanel existingLoanRequestPanel) {
        this.existingLoanRequestPanel = existingLoanRequestPanel;
    }

    public void setNewLoanRequestPanel(NewLoanRequestPanel newLoanRequestPanel) {
        this.newLoanRequestPanel = newLoanRequestPanel;
    }

    public void setExistingLoanRequestControls(ExistingLoanRequestControls existingLoanRequestControls) {
        this.existingLoanRequestControls = existingLoanRequestControls;
    }

    public void setPostponeRequestPanel(PostponeRequestPanel postponeRequestPanel) {
        this.postponeRequestPanel = postponeRequestPanel;
    }

    public ArrayList<JPanel> getCreatedPanels() {
        ArrayList<JPanel> panels = new ArrayList<JPanel>();
        if(existingLoanRequestPanel!=null)
            panels.add(existingLoanRequestPanel);
        if(newLoanRequestPanel!=null)
            panels.add(newLoanRequestPanel);
        if(existingLoanRequestControls!=null)
            panels.add(existingLoanRequestControls);
        if(postponeRequestPanel!=null)
            panels.add(postponeRequestPanel);
        
        return panels;
        
    }
}
