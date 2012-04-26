/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loansystem.view;

import com.loansystem.UI.client.ExistingLoanRequestControls;
import com.loansystem.UI.client.ExistingLoanRequestPanel;
import com.loansystem.UI.client.LoanRequestCTab;
import com.loansystem.UI.client.NewLoanRequestPanel;
import com.loansystem.UI.client.PostponeRequestPanel;
import com.loansystem.backend.model.LoanTabModel;
import com.loansystem.hibernate.HibernateUtil;
import com.loansystem.model.Client;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JPanel;
import javax.swing.event.ChangeListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

/**
 *
 * @author antonve
 */
public class LoanTabView extends JPanel {

    private static final Log log = LogFactory.getLog(LoanTabView.class);
    public ExistingLoanRequestPanel existingLoanRequestPanel;
    public NewLoanRequestPanel newLoanRequestPanel;
    private PostponeRequestPanel postponeRequestPanel;
    SessionFactory sf = HibernateUtil.getSessionFactory();
    private ExistingLoanRequestControls existingLoanRequestControls;
    private JPanel activePanel;
    private final LoanTabModel loanTabModel;

    public LoanTabView(NewLoanRequestPanel newLoanRequestPanel, LoanTabModel loanTabModel) {
        setLayout(new java.awt.BorderLayout());
        this.getContainerListeners();
        this.newLoanRequestPanel = newLoanRequestPanel;
        this.loanTabModel = loanTabModel;
        //this.revalidate();
    }

    public PostponeRequestPanel getPostponeRequestPanel() {
        return postponeRequestPanel;
    }

    public void setPostponeRequestPanel(PostponeRequestPanel postponeRequestPanel) {
        this.postponeRequestPanel = postponeRequestPanel;
    }

    public LoanTabView(ExistingLoanRequestPanel existingLoanRequestPanel, NewLoanRequestPanel newLoanRequestPanel, LoanTabModel loanTabModel) {
        setLayout(new java.awt.BorderLayout());
        this.getContainerListeners();
        this.existingLoanRequestPanel = existingLoanRequestPanel;
        this.newLoanRequestPanel = newLoanRequestPanel;
        this.loanTabModel = loanTabModel;
       // this.revalidate();


    }

    public ExistingLoanRequestPanel getExistingLoanRequestPanel() {
        return loanTabModel.getExistingLoanRequestPanel();
    }

    public void setExistingLoanRequestPanel(ExistingLoanRequestPanel existingLoanRequestPanel) {
        this.existingLoanRequestPanel = existingLoanRequestPanel;
    }

    public NewLoanRequestPanel getNewLoanRequestPanel() {
       return loanTabModel.getNewLoanRequestPanel();
    }

    public void setNewLoanRequestPanel(NewLoanRequestPanel newLoanRequestPanel) {
        this.newLoanRequestPanel = newLoanRequestPanel;
    }

    public SessionFactory getSf() {
        return sf;
    }

    public void setSf(SessionFactory sf) {
        this.sf = sf;
    }

    /* public void addLoginListener(ActionListener mal) {
    loginForm.addLoginListener(mal);
    }*/
    public void addLoanStateChangeToRejectedListener(ActionListener mal) {
        setActiveView(existingLoanRequestPanel);
        log.info("VIEW : addLoanStateChangeToRejectedListener registered ");
        loanTabModel.getExistingLoanRequestControls().addRejectListener(mal);
    }

    public void addLoanStateChangeToPayedListener(ActionListener mal) {
        setActiveView(existingLoanRequestPanel);
        log.info("VIEW : addLoanStateChangeToPayedListener registered ");
        loanTabModel.getExistingLoanRequestControls().addPayBackListener(mal);
    }

    public void addLoanStateChangeToPostponedListener(ActionListener mal) {
        setActiveView(existingLoanRequestPanel);
        log.info("VIEW : addLoanStateChangeToPostponedListener registered ");
        loanTabModel.getExistingLoanRequestControls().addPostponeListener(mal);
    }

    public void addLoanPostponeConfirmedListener(ActionListener mal) {
        setActiveView(existingLoanRequestPanel);
        log.info("VIEW : addLoanPOstponeConfirmedListener registered ");
        loanTabModel.getPostponeRequestPanel().addPostponeConfirmListener(mal);
    }

    public JPanel getActivePanel() {
        return activePanel;
    }

    public void setActivePanel(JPanel activePanel) {
        this.activePanel = activePanel;
    }

    public void addLoanStateChangeToPendingListener(ActionListener mal) {
        log.info("VIEW : LoanStateChangeToPendingListener registered ");
        loanTabModel.getNewLoanRequestPanel().addLoginListener(mal);
    }

    public void removeExisitingLoanTab(Client client) {
        //this.removeAll();
        //this.repaint();	
        loanTabModel.getExistingLoanRequestPanel().setVisible(false);
    }

    public void showNewLoanTab(Client client) {
        //newLoanRequestPanel = new NewLoanRequestPanel(client, true); 
        //existingLoanRequestPanel.setVisible(true);
        loanTabModel.getNewLoanRequestPanel().setVisible(true);
        //this.revalidate();
        //this.repaint();
    }

    public void removeNewLoanTab(Client client) {
        loanTabModel.getNewLoanRequestPanel().setVisible(false);
        this.revalidate();
    }

    public void showExisitingLoanTab(Client client) {
        
        loanTabModel.getExistingLoanRequestPanel().setVisible(true);
        this.revalidate();
    }

    private void setActiveView(JPanel activePanel) {
        this.activePanel = activePanel;
    }

    public void showPostponeControls() {
        loanTabModel.getPostponeRequestPanel().setVisible(true);
        this.revalidate();
    }
    
     public void hidePostponeControls() {
        loanTabModel.getPostponeRequestPanel().setVisible(false);
        this.revalidate();
    }

    public void removeExistingLoanTabControls(Client client) {
        loanTabModel.getExistingLoanRequestPanel().setVisible(false);
        loanTabModel.getExistingLoanRequestControls().setVisible(false);
        loanTabModel.getPostponeRequestPanel().setVisible(false);
        this.revalidate();

    }

    public void showExistingLoanTabControls(Client client) {
        loanTabModel.getExistingLoanRequestPanel().setVisible(true);
        loanTabModel.getExistingLoanRequestControls().setVisible(true);
        this.revalidate();
        //postponeRequestPanel.setVisible(false);
    }

    public void addSliderListener(ChangeListener sliderStateChangedListener) {
         loanTabModel.getPostponeRequestPanel().addSliderListener(sliderStateChangedListener);
    }

    public void addRemoveLoanRequestListener(ActionListener addRemoveLoanRequestListener) {
        loanTabModel.getExistingLoanRequestControls().addRemoveRequestListener(addRemoveLoanRequestListener);
    }

    public void updateExistingLoanPanel() {
        loanTabModel.getExistingLoanRequestPanel().updateExitingLoanPanel();
    }

    public void addRemovePostponeRequestListener(ActionListener removePostponeRequestListener) {
       loanTabModel.getExistingLoanRequestControls().addRemovePostponeRequestListener(removePostponeRequestListener);
    }

    public void hideUnnecessaryButtons() {
       loanTabModel.getExistingLoanRequestControls().hideUnnecessaryButtons();
    }

}
