/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loansystem.control;

import com.loansystem.UI.employee.PendingLoanRequestsTab;
import com.loansystem.backend.model.PendingLoansTabModel;
import com.loansystem.db.dao.LoanHome;
import com.loansystem.enums.LoanStatusEnum;
import com.loansystem.model.Loan;
import com.loansystem.service.LoanService;
import com.loansystem.service.LoanServiceImpl;
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
        /*pendingLoansTabModel.addRemoveLoanRequestListener(new removeLoanRequestListener());
        pendingLoansTabModel.addRemovePostponeRequestListener(new RemovePostponeRequestListener());*/
    }
    
    private void showPendingLoanControls() {
        pendingLoansTabModel.getPendingLoanControls().setVisible(true);
    }
    
     private void showPendingLoanDetailedViewPanel() {
        pendingLoansTabModel.getPendingLoanDetailedViewPanel().setVisible(true);
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
            showPendingLoanDetailedViewPanel();
            showClientLoansPanel();
            showPendingLoanControls();
        }
    }

        private void fillPendingLoanDetailedViewPanel() {
            pendingLoansTabModel.fillPendingLoanDetailedViewPanel(pendingLoansTabModel.getSelectedLoan());
        }
        
        private void showClientLoansPanel() {
            pendingLoansTabModel.showClientLoansPanel(pendingLoansTabModel.getSelectedLoan());
        }

       
    }
    
}
