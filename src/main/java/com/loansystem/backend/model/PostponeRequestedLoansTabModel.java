/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loansystem.backend.model;

import com.loansystem.UI.client.MyLoansPanel;
import com.loansystem.UI.employee.PendingLoanControlsPanel;
import com.loansystem.UI.employee.PendingLoanDetailedViewPanel;
import com.loansystem.UI.employee.PendingLoanRequestsPanel;
import com.loansystem.model.Employee;
import com.loansystem.model.Loan;
import com.loansystem.model.User;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Antonve
 */
public class PostponeRequestedLoansTabModel {
    
    private User user;
    private Employee employee;
    private ArrayList<Loan> postponeRequestedLoans;
    private ArrayList<Loan> clientLoans;
    private PendingLoanRequestsPanel postponeRequestedLoansPanel;
    private PendingLoanDetailedViewPanel postponeRequestedLoanDetailedViewPanel;
    private PendingLoanControlsPanel postponeRequestedLoanControlsPanel;
    private MyLoansPanel clientLoansPanel;
    private Loan selectedLoan;

    public Loan getSelectedLoan() {
        return selectedLoan;
    }

    public ArrayList<Loan> getClientLoans() {
        return postponeRequestedLoans;
    }

    public ArrayList<Loan> getPostponeRequestedLoans() {
        return postponeRequestedLoans;
    }

    public void setPostponeRequestedLoans(ArrayList<Loan> postponeRequestedLoans) {
        this.postponeRequestedLoans = postponeRequestedLoans;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setClientLoans(ArrayList<Loan> clientLoans) {
        this.clientLoans = clientLoans;
    }

    public void setPostponeRequestedLoansPanel(PendingLoanRequestsPanel postponeRequestedLoansPanel) {
       this.postponeRequestedLoansPanel = postponeRequestedLoansPanel;
    }

    public void setPostponeRequestedLoanDetailedViewPanel(PendingLoanDetailedViewPanel postponeRequestedLoanDetailedViewPanel) {
        this.postponeRequestedLoanDetailedViewPanel = postponeRequestedLoanDetailedViewPanel;
    }

    public void setPostponeRequestedLoanControlsPanel(PendingLoanControlsPanel postponeRequestedLoanControlsPanel) {
       this.postponeRequestedLoanControlsPanel = postponeRequestedLoanControlsPanel;
    }

    public MyLoansPanel getClientLoansPanel() {
        return clientLoansPanel;
    }

    public PendingLoanControlsPanel getPostponeRequestedLoanControlsPanel() {
        return postponeRequestedLoanControlsPanel;
    }

    public PendingLoanDetailedViewPanel getPostponeRequestedLoanDetailedViewPanel() {
        return postponeRequestedLoanDetailedViewPanel;
    }

    public PendingLoanRequestsPanel getPostponeRequestedLoansPanel() {
        return postponeRequestedLoansPanel;
    }

    public void setClientLoansPanel(MyLoansPanel clientLoansPanel) {
       this.clientLoansPanel = clientLoansPanel;
    }

    public ArrayList<JPanel> getCreatedPanels() {
         ArrayList<JPanel> createdPanels = new ArrayList<JPanel>();
        /*if(testTable!=null) {
            createdPanels.add(testTable);
        }
         if(testLabelForm!=null) {
            createdPanels.add(testLabelForm);
        }*/
        if(postponeRequestedLoansPanel!=null) {
            createdPanels.add(postponeRequestedLoansPanel);
        }
        if(postponeRequestedLoanDetailedViewPanel!=null) {
            createdPanels.add(postponeRequestedLoanDetailedViewPanel);
        }
        if(postponeRequestedLoanControlsPanel!=null) {
            createdPanels.add(postponeRequestedLoanControlsPanel);
        }
        if(clientLoansPanel!=null) {
            createdPanels.add(clientLoansPanel);
        }
        return createdPanels;
    }

    public void addPostponeRequestedLoanEntryClickedListener(MouseAdapter pendingLoanEntryClickedListener) {
        this.postponeRequestedLoansPanel.addPostponeRequestedLoanEntryClickedListener(pendingLoanEntryClickedListener);
    }
    
    public Loan getSelectedLoan(int row) {
        Loan loan = postponeRequestedLoansPanel.getSelectedLoan(row);
        return loan;
    }

    public void setSelectedLoan(Loan selectedLoan) {
       this.selectedLoan = selectedLoan;
    }

    public void fillPostponedLoanDetailedViewPanel(Loan selectedLoan) {
        this.postponeRequestedLoanDetailedViewPanel.fillPendingLoanDetailedViewPanel(selectedLoan);
    }

    public void showClientLoansPanel(boolean visibility) {
        this.clientLoansPanel.setVisible(visibility);
    }

    public void addLoanRequestAcceptedListner(ActionListener loanRequestAcceptedListner) {
        postponeRequestedLoanControlsPanel.getAcceptRequestButton().addActionListener(loanRequestAcceptedListner);
    }

    public void removeUnnecessaryLoanFromTable(ArrayList<Loan> pendingLoans) {
        postponeRequestedLoansPanel.removeUnnecessaryLoanFromTable(pendingLoans);
    }

    public void addLoanRequestRejectedListner(ActionListener  loanRequestRejectedListner) {
       postponeRequestedLoanControlsPanel.getRejectRequestButton().addActionListener(loanRequestRejectedListner);
    }


    
    
    
    
}
