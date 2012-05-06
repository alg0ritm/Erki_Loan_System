/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loansystem.control;

import com.loansystem.UI.employee.PendingLoanRequestsTab;
import com.loansystem.backend.model.PendingLoansTabModel;
import com.loansystem.db.dao.LoanHome;
import com.loansystem.enums.LoanStatusEnum;
import com.loansystem.enums.LoanStatusInterface;
import com.loansystem.model.Loan;
import com.loansystem.model.LoanStatus;
import com.loansystem.service.LoanService;
import com.loansystem.service.LoanServiceImpl;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Antonve
 */
public class PendingLoansTabController {
    
    private static final Log log = LogFactory.getLog(PendingLoansTabController.class);
    
    private PendingLoansTabModel pendingLoansTabModel;
    private PendingLoanRequestsTab pendingLoansTab;


    public PendingLoansTabController(PendingLoansTabModel pendingLoansTabModel, PendingLoanRequestsTab pendingLoansTab) {
        this.pendingLoansTabModel = pendingLoansTabModel;
        this.pendingLoansTab = pendingLoansTab;
        initExisitingLoanListeners();
        
    }
    
    
    private void initExisitingLoanListeners() {
        pendingLoansTabModel.addPendingLoanEntryClickedListener(new PendingLoanEntryClickedListener());
        pendingLoansTabModel.addLoanRequestAcceptedListner(new LoanRequestAcceptedListner());
        pendingLoansTabModel.addLoanRequestRejectedListner(new LoanRequestRejectedListner());
        /*pendingLoansTabModel.addRemoveLoanRequestListener(new removeLoanRequestListener());
        pendingLoansTabModel.addRemovePostponeRequestListener(new RemovePostponeRequestListener());*/
    }
    
    private void showPendingLoanControls(boolean visibility) {
        pendingLoansTabModel.getPendingLoanControls().setVisible(visibility);
        pendingLoansTabModel.getPendingLoanControls().setButtonsVisibility(visibility);
    }
    
     private void showPendingLoanDetailedViewPanel(boolean visibility) {
        pendingLoansTabModel.getPendingLoanDetailedViewPanel().setVisible(visibility);
    }
     
    private class LoanRequestAcceptedListner implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
             Loan selectedLoan = pendingLoansTabModel.getSelectedLoan();
             LoanService loanService = new LoanServiceImpl();
             loanService.saveLoanWithStatus(selectedLoan, LoanStatusInterface.ISSUED, null, "Loan is issued");
             ArrayList<Loan> pendingLoans = new ArrayList<Loan>();
             pendingLoans = loanService.getLoansByStatus(LoanStatusInterface.PENDING);
             pendingLoansTabModel.setPendingLoans(pendingLoans);
             pendingLoansTabModel.removeUnnecessaryLoanFromTable(pendingLoans);
             showPendingLoanDetailedViewPanel(false);
             showClientLoansPanel(false);
             showPendingLoanControls(false);
             
             
             
        }
        
    }
    
    private class LoanRequestRejectedListner implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
             Loan selectedLoan = pendingLoansTabModel.getSelectedLoan();
             LoanService loanService = new LoanServiceImpl();
             loanService.saveLoanWithStatus(selectedLoan, LoanStatusInterface.REJECTED, null, "Loan is rejected");
              ArrayList<Loan> pendingLoans = new ArrayList<Loan>();
             pendingLoans = loanService.getLoansByStatus(LoanStatusInterface.PENDING);
             pendingLoansTabModel.setPendingLoans(pendingLoans);
             pendingLoansTabModel.removeUnnecessaryLoanFromTable(pendingLoans);
             showPendingLoanDetailedViewPanel(false);
             showClientLoansPanel(false);
             showPendingLoanControls(false);
        }
        
    }

    private class PendingLoanEntryClickedListener extends MouseAdapter  {
        
        @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
            
        int row = pendingLoansTabModel.getPendingLoanRequestsPanel().getPendingLoansTable().getSelectedRow();
        
        if (row >= 0) {
            Loan selectedLoan = pendingLoansTabModel.getSelectedLoan(row);
            
            LoanService loanService = new LoanServiceImpl();
            selectedLoan = loanService.getLoanById(selectedLoan);
            pendingLoansTabModel.setSelectedLoan(selectedLoan);
            fillPendingLoanDetailedViewPanel();
            showPendingLoanDetailedViewPanel(true);
            ArrayList<Loan> clientLoans = new ArrayList<Loan>();
            clientLoans.addAll(selectedLoan.getClient().getLoans());
            pendingLoansTabModel.setClientLoans(clientLoans);
            fillPendingLoanDetailedViewPanel();
            showPendingLoanDetailedViewPanel(true);
            fillClientLoansPanel();
            showClientLoansPanel(true);
            showClientLoansPanel(true);
            showPendingLoanControls(true);
        }
    }

        private void fillPendingLoanDetailedViewPanel() {
            pendingLoansTabModel.fillPendingLoanDetailedViewPanel(pendingLoansTabModel.getSelectedLoan());
        }

        private void fillClientLoansPanel() {
            pendingLoansTabModel.fillClientLoansPanel();
        }
        
       

       
    }
     private void showClientLoansPanel(boolean visibility) {
            pendingLoansTabModel.showClientLoansPanel(visibility);
        }
    
}
