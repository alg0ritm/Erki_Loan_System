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
    SessionFactory sf =  HibernateUtil.getSessionFactory();
    private final ExistingLoanRequestControls existingLoanRequestControls;

    public PostponeRequestPanel getPostponeRequestPanel() {
        return postponeRequestPanel;
    }

    public void setPostponeRequestPanel(PostponeRequestPanel postponeRequestPanel) {
        this.postponeRequestPanel = postponeRequestPanel;
    }
    private JPanel activePanel;

    public LoanTabView(ExistingLoanRequestPanel existingLoanRequestPanel, NewLoanRequestPanel newLoanRequestPanel,ExistingLoanRequestControls existingLoanRequestControls, PostponeRequestPanel postponeRequestPanel )
    {
        
        this.getContainerListeners();
        this.existingLoanRequestPanel = existingLoanRequestPanel;
        this.newLoanRequestPanel = newLoanRequestPanel;
        this.postponeRequestPanel = postponeRequestPanel;
        this.existingLoanRequestControls = existingLoanRequestControls;
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
        existingLoanRequestControls.addRejectListener(mal);
    }
    
     public void addLoanStateChangeToPayedListener(ActionListener mal) {
        setActiveView(existingLoanRequestPanel);
        log.info("VIEW : addLoanStateChangeToPayedListener registered ");
        existingLoanRequestControls.addPayBackListener(mal);
    }
     
     public void addLoanStateChangeToPostponedListener(ActionListener mal) {
        setActiveView(existingLoanRequestPanel);
        log.info("VIEW : addLoanStateChangeToPostponedListener registered ");
        existingLoanRequestControls.addPostponeListener(mal);
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

    public void showPostponeControls() {
        postponeRequestPanel.setVisible(true);
    }

    public void removeExistingLoanTabControls(Client client) {
        existingLoanRequestPanel.setVisible(false);
        existingLoanRequestControls.setVisible(false);
        postponeRequestPanel.setVisible(false);
        
    }

    public void showExistingLoanTabControls(Client client) {
        existingLoanRequestPanel.setVisible(true);
        existingLoanRequestControls.setVisible(true);
        //postponeRequestPanel.setVisible(false);
    }

    public void addSliderListener(ChangeListener sliderStateChangedListener) {
        postponeRequestPanel.addSliderListener(sliderStateChangedListener);
    }
}
