/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loansystem.backend.model;

import com.loansystem.UI.client.MyLoansPanel;
import com.loansystem.UI.employee.PendingLoanControlsPanel;
import com.loansystem.UI.employee.PendingLoanDetailedView;
import com.loansystem.UI.employee.PendingLoanDetailedViewPanel;
import com.loansystem.UI.employee.PendingLoanRequestsPanel;
import com.loansystem.UI.employee.TestLabelForm;
import com.loansystem.UI.employee.TestTable;
import com.loansystem.model.Employee;
import com.loansystem.model.Loan;
import com.loansystem.model.User;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Antonve
 */
public class PendingLoansTabModel {
    
    private User user;
    private Loan selectedLoan;
    private PendingLoanDetailedViewPanel pendingLoanDetailedViewPanel;
    private TestTable testTable;
    private TestLabelForm testLabelForm;
    private ArrayList<Loan> clientLoans;

    public ArrayList<Loan> getClientLoans() {
        return clientLoans;
    }


    public TestLabelForm getTestLabelForm() {
        return testLabelForm;
    }

    public void setTestLabelForm(TestLabelForm testLabelForm) {
        this.testLabelForm = testLabelForm;
    }

    public TestTable getTestTable() {
        return testTable;
    }

    public void setTestTable(TestTable testTable) {
        this.testTable = testTable;
    }

    public PendingLoanDetailedViewPanel getPendingLoanDetailedViewPanel() {
        return pendingLoanDetailedViewPanel;
    }

    public PendingLoanDetailedView getPendingLoanDetailedView() {
        return pendingLoanDetailedView;
    }
    private PendingLoanDetailedView pendingLoanDetailedView;

    public MyLoansPanel getClientLoansPanel() {
        return clientLoansPanel;
    }
    
    private MyLoansPanel clientLoansPanel;

    public Loan getSelectedLoan() {
        return selectedLoan;
    }

    public User getUser() {
        return user;
    }
    private Employee employee;
    private PendingLoanRequestsPanel pendingLoanRequestsPanel;
    
    private PendingLoanControlsPanel pendingLoanControls;
    private ArrayList<Loan> pendingLoans;

    public ArrayList<Loan> getPendingLoans() {
        return pendingLoans;
    }

    public PendingLoanControlsPanel getPendingLoanControls() {
        return pendingLoanControls;
    }

    public void setPendingLoanControls(PendingLoanControlsPanel pendingLoanControls) {
        this.pendingLoanControls = pendingLoanControls;
    }

   

    public void setPendingLoanDetailedView(PendingLoanDetailedView pendingLoanDetailedView) {
        this.pendingLoanDetailedView = pendingLoanDetailedView;
    }

    public PendingLoanRequestsPanel getPendingLoanRequestsPanel() {
        return pendingLoanRequestsPanel;
    }

    public void setPendingLoanRequestsPanel(PendingLoanRequestsPanel pendingLoanRequestsPanel) {
        this.pendingLoanRequestsPanel = pendingLoanRequestsPanel;
        //this.testTable = pendingLoanRequestsPanel;
    }
    

   

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

   

    public ArrayList<JPanel> getCreatedPanels() {
         ArrayList<JPanel> createdPanels = new ArrayList<JPanel>();
        if(testTable!=null) {
            createdPanels.add(testTable);
        }
         if(testLabelForm!=null) {
            createdPanels.add(testLabelForm);
        }
        if(pendingLoanRequestsPanel!=null) {
            createdPanels.add(pendingLoanRequestsPanel);
        }
        if(pendingLoanDetailedViewPanel!=null) {
            createdPanels.add(pendingLoanDetailedViewPanel);
        }
        if(pendingLoanControls!=null) {
            createdPanels.add(pendingLoanControls);
        }
        if(clientLoansPanel!=null) {
            createdPanels.add(clientLoansPanel);
        }
        return createdPanels;
    }

    public void setPendingLoans(ArrayList<Loan> pendingLoans) {
        this.pendingLoans = pendingLoans;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addPendingLoanEntryClickedListener(MouseListener pendingLoanEntryClickedListener) {
        this.pendingLoanRequestsPanel.getPendingLoansTable().addMouseListener(pendingLoanEntryClickedListener);
    }

    public Loan getSelectedLoan(int row) {
        Loan loan = pendingLoanRequestsPanel.getSelectedLoan(row);
        return loan;
    }

    public void setSelectedLoan(Loan selectedLoan) {
       this.selectedLoan = selectedLoan;
    }

    public void setClientLoansPanel(MyLoansPanel clientLoansPanel) {
       this.clientLoansPanel = clientLoansPanel;
    }

    public void fillPendingLoanDetailedViewPanel(Loan selectedLoan) {
        this.pendingLoanDetailedViewPanel.fillPendingLoanDetailedViewPanel(selectedLoan);
    }

    public void setPendingLoanDetailedViewPanel(PendingLoanDetailedViewPanel pendingLoanDetailedViewPanel) {
          this.pendingLoanDetailedViewPanel = pendingLoanDetailedViewPanel;
    }

    public void showClientLoansPanel(boolean visibility) {
        clientLoansPanel.setVisible(visibility);
    }

    public void addLoanRequestAcceptedListner(ActionListener loanRequestAcceptedListner) {
        pendingLoanControls.getAcceptRequestButton().addActionListener(loanRequestAcceptedListner);
    }
    
    public void addLoanRequestRejectedListner(ActionListener loanRequestRejectedListner) {
        pendingLoanControls.getRejectRequestButton().addActionListener(loanRequestRejectedListner);
    }

    public void removeUnnecessaryLoanFromTable(ArrayList<Loan> loans) {
        
       pendingLoanRequestsPanel.removeUnnecessaryLoanFromTable(loans);
    }

    public void setClientLoans(ArrayList<Loan> clientLoans) {
        this.clientLoans = clientLoans;
    }

    public void fillClientLoansPanel() {
        clientLoansPanel.fillClientLoansPanel(clientLoans);
    }
    
}
