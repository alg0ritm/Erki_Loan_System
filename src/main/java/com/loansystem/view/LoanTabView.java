/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loansystem.view;

import com.loansystem.UI.client.ExistingLoanRequestPanel;
import com.loansystem.UI.client.LoanRequestCTab;
import com.loansystem.UI.client.NewLoanRequestPanel;
import com.loansystem.hibernate.HibernateUtil;
import com.loansystem.model.Client;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JPanel;
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
    SessionFactory sf =  HibernateUtil.getSessionFactory();
    private JPanel activePanel;

    public LoanTabView(ExistingLoanRequestPanel existingLoanRequestPanel, NewLoanRequestPanel newLoanRequestPanel )
    {
        
        this.getContainerListeners();
        this.existingLoanRequestPanel = existingLoanRequestPanel;
        this.newLoanRequestPanel = newLoanRequestPanel;
        this.revalidate();
        
        
    }

    public ExistingLoanRequestPanel getExistingLoanRequestPanel() {
        return existingLoanRequestPanel;
    }

    public void setExistingLoanRequestPanel(ExistingLoanRequestPanel existingLoanRequestPanel) {
        this.existingLoanRequestPanel = existingLoanRequestPanel;
    }

    public NewLoanRequestPanel getNewLoanRequestPanel() {
        return newLoanRequestPanel;
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
        existingLoanRequestPanel.addRejectListener(mal);
    }
    
     public void addLoanStateChangeToPayedListener(ActionListener mal) {
        setActiveView(existingLoanRequestPanel);
        log.info("VIEW : addLoanStateChangeToPayedListener registered ");
        existingLoanRequestPanel.addPayBackListener(mal);
    }

    public JPanel getActivePanel() {
        return activePanel;
    }

    public void setActivePanel(JPanel activePanel) {
        this.activePanel = activePanel;
    }

    public void addLoanStateChangeToPendingListener(ActionListener mal) {
        log.info("VIEW : LoanStateChangeToPendingListener registered ");
        newLoanRequestPanel.addLoginListener(mal);
    }

    public void removeExisitingLoanTab(Client client) {
        //this.removeAll();
	//this.repaint();	
        existingLoanRequestPanel.setVisible(false);
    }

    public void showNewLoanTab(Client client) {
        //newLoanRequestPanel = new NewLoanRequestPanel(client, true); 
        //existingLoanRequestPanel.setVisible(true);
        newLoanRequestPanel.setVisible(true);
        //this.revalidate();
        //this.repaint();
    }

    public void removeNewLoanTab(Client client) {
       newLoanRequestPanel.setVisible(false);
    }

    public void showExisitingLoanTab(Client client) {
       existingLoanRequestPanel.setVisible(true);
    }

    private void setActiveView(JPanel activePanel) {
        this.activePanel = activePanel; 
    }
}
