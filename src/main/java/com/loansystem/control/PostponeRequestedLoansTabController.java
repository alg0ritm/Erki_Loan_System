/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loansystem.control;

import com.loansystem.UI.employee.PendingLoanRequestsTab;
import com.loansystem.backend.model.PostponeRequestedLoansTabModel;
import com.loansystem.enums.LoanStatusInterface;
import com.loansystem.model.Client;
import com.loansystem.model.Loan;
import com.loansystem.model.PostponeRequestStatus;
import com.loansystem.service.LoanService;
import com.loansystem.service.LoanServiceImpl;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;

/**
 *
 * @author Antonve
 */
public class PostponeRequestedLoansTabController {

    private PostponeRequestedLoansTabModel postponeRequestedLoansTabModel;

    PostponeRequestedLoansTabController(PostponeRequestedLoansTabModel postponeRequestedLoansTabModel, PendingLoanRequestsTab pendingLoansTab) {
        this.postponeRequestedLoansTabModel = postponeRequestedLoansTabModel;
        //this.pendingLoansTab = pendingLoansTab;
        initExisitingLoanListeners();
    }

    private void initExisitingLoanListeners() {
        postponeRequestedLoansTabModel.addPostponeRequestedLoanEntryClickedListener(new PostponeRequestedLoanEntryClickedListener());
        postponeRequestedLoansTabModel.addLoanRequestAcceptedListner(new LoanRequestAcceptedListner());
        postponeRequestedLoansTabModel.addLoanRequestRejectedListner(new LoanRequestRejectedListner());
        /*pendingLoansTabModel.addRemoveLoanRequestListener(new removeLoanRequestListener());
        pendingLoansTabModel.addRemovePostponeRequestListener(new RemovePostponeRequestListener());*/
    }

    private void showPendingLoanControls(boolean visibility) {
        postponeRequestedLoansTabModel.getPostponeRequestedLoanControlsPanel().setVisible(visibility);
        postponeRequestedLoansTabModel.getPostponeRequestedLoanControlsPanel().setButtonsVisibility(visibility);
    }

    private void showPendingLoanDetailedViewPanel(boolean visibility) {
        postponeRequestedLoansTabModel.getPostponeRequestedLoanDetailedViewPanel().setVisible(visibility);
    }

    private class LoanRequestAcceptedListner implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Loan selectedLoan = postponeRequestedLoansTabModel.getSelectedLoan();
            selectedLoan.setEmployeeId(postponeRequestedLoansTabModel.getEmployee().getEmloyeeId());
            LoanService loanService = new LoanServiceImpl();
            loanService.savePostponedLoanWithStatus(selectedLoan, com.loansystem.classificator.PostponeRequestStatus.ACCEPTED, null);
            ArrayList<Loan> pendingLoans = new ArrayList<Loan>();
            PostponeRequestStatus postponeRequestStatus = loanService.getPostponeRequestStatusById(com.loansystem.classificator.PostponeRequestStatus.REQUESTED);
            pendingLoans = loanService.getPostponedLoansByStatus(postponeRequestStatus);
            postponeRequestedLoansTabModel.setPostponeRequestedLoans(pendingLoans);
            postponeRequestedLoansTabModel.removeUnnecessaryLoanFromTable(pendingLoans);
            showPendingLoanDetailedViewPanel(false);
            showClientLoansPanel(false);
            showPendingLoanControls(false);



        }
    }

    private class LoanRequestRejectedListner implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Loan selectedLoan = postponeRequestedLoansTabModel.getSelectedLoan();
            selectedLoan.setEmployeeId(postponeRequestedLoansTabModel.getEmployee().getEmloyeeId());
            LoanService loanService = new LoanServiceImpl();
            //loanService.saveLoanWithStatus(selectedLoan, LoanStatusInterface.REJECTED, null);
            loanService.savePostponedLoanWithStatus(selectedLoan, com.loansystem.classificator.PostponeRequestStatus.REJECTED, null);
            PostponeRequestStatus postponeRequestStatus = loanService.getPostponeRequestStatusById(com.loansystem.classificator.PostponeRequestStatus.REQUESTED);

            ArrayList<Loan> pendingLoans = new ArrayList<Loan>();
            pendingLoans = loanService.getPostponedLoansByStatus(postponeRequestStatus);
            postponeRequestedLoansTabModel.setPostponeRequestedLoans(pendingLoans);
            postponeRequestedLoansTabModel.removeUnnecessaryLoanFromTable(pendingLoans);
            showPendingLoanDetailedViewPanel(false);
            showClientLoansPanel(false);
            showPendingLoanControls(false);
        }
    }

    private class PostponeRequestedLoanEntryClickedListener extends MouseAdapter {

        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {

            int row = postponeRequestedLoansTabModel.getPostponeRequestedLoansPanel().getPendingLoansTable().getSelectedRow();

            if (row >= 0) {
                Loan selectedLoan = postponeRequestedLoansTabModel.getSelectedLoan(row);

                LoanService loanService = new LoanServiceImpl();
                selectedLoan = loanService.getLoanById(selectedLoan, null);
                postponeRequestedLoansTabModel.setSelectedLoan(selectedLoan);
                ArrayList<Loan> clientLoans = new ArrayList<Loan>();
                clientLoans.addAll(selectedLoan.getClient().getLoans());
                postponeRequestedLoansTabModel.setClientLoans(clientLoans);
                fillPendingLoanDetailedViewPanel();
                showPendingLoanDetailedViewPanel(true);
                fillClientLoansPanel();
                showClientLoansPanel(true);
                showPendingLoanControls(true);
            }
        }

        private void fillPendingLoanDetailedViewPanel() {
            postponeRequestedLoansTabModel.fillPostponedLoanDetailedViewPanel(postponeRequestedLoansTabModel.getSelectedLoan());
        }
    }

    private void showClientLoansPanel(boolean visibility) {
        postponeRequestedLoansTabModel.showClientLoansPanel(visibility);
    }

    private void fillClientLoansPanel() {
        postponeRequestedLoansTabModel.fillClientLoansPanel();
    }
}
