/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loansystem.control;

import com.loansystem.UI.client.ExistingLoanRequestPanel;
import com.loansystem.UI.client.NewLoanRequestPanel;
import com.loansystem.backend.model.LoanInsertRequest;
import com.loansystem.backend.model.LoanTabModel;
import com.loansystem.db.dao.LoanHome;
import com.loansystem.db.dao.LoanStatusHome;
import com.loansystem.hibernate.HibernateUtil;
import com.loansystem.model.Client;
import com.loansystem.model.Loan;
import com.loansystem.model.LoanOffer;
import com.loansystem.model.LoanStatus;
import com.loansystem.service.LoanService;
import com.loansystem.service.LoanServiceImpl;
import com.loansystem.util.DateUtil;
import com.loansystem.view.LoanTabView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author antonve
 */
public class LoanTabController {

    private static final Log log = LogFactory.getLog(LoanTabController.class);

    
    NewLoanRequestPanel newLoanRequestPanel;
    ExistingLoanRequestPanel existingLoanRequestPanel;
    LoanTabView loanTabView;
    LoanTabModel loanTabModel;
    HibernateUtil hu;
    Client client;
    LoanService loanService;

    public LoanTabController(LoanTabView loanTabView, LoanTabModel loanTabModel, Client client) {
        this.loanTabModel = loanTabModel;
        this.loanTabView = loanTabView;
        this.client = client;
        this.hu = hu;
        loanTabView.addLoanStateChangeToPendingListener(new LoanStateChangeToPendingListener());
        loanTabView.addLoanStateChangeToRejectedListener(new LoanStateToRejectedListener());
        loanTabView.addLoanStateChangeToPayedListener(new LoanStateToPayedListener());
        this.loanTabView.setVisible(true);
    }

    private class LoanStateChangeToPendingListener implements ActionListener {

        public LoanStateChangeToPendingListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //ask if sure to take the loan  
            int rowIndex = loanTabView.getNewLoanRequestPanel().getjTable1().getSelectedRow();

            String s = loanTabView.getNewLoanRequestPanel().displayRowValues(rowIndex);

            log.info("HERE");

            String[] choices = {"No", "Yes"};
            int choiceResposne = JOptionPane.showOptionDialog(
                    null // Center in window.
                    , s // Message
                    , "" // Title in titlebar
                    , JOptionPane.YES_NO_OPTION // Option type
                    , JOptionPane.PLAIN_MESSAGE // messageType
                    , null // Icon (none)
                    , choices // Button text as above.
                    , "Yes" // Default button's label
                    );

            switch (choiceResposne) {
                case 0:
                    JOptionPane.showMessageDialog(loanTabView.getNewLoanRequestPanel(), "Y've chosen NO " + rowIndex);
                    break;
                case 1:

                    LoanOffer selectedLoanOffer = loanTabView.getNewLoanRequestPanel().getSelectedLoanOffer(rowIndex);
                    LoanService loanService = new LoanServiceImpl();
                    loanService.createNewLoan(client, selectedLoanOffer);
                    loanTabView.removeNewLoanTab(client);
                    reValidateLastLoan();
                    loanTabView.showExisitingLoanTab(client);
                    
                    JOptionPane.showMessageDialog(loanTabView.getNewLoanRequestPanel(), "Thanks, your request has been recorded for confirmation" + rowIndex);
                    break;
                case -1:
                    //... Both the quit button (3) and the close box(-1) handled here.
                    System.exit(0);     // It would be better to exit loop, but...
                default:
                    //... If we get here, something is wrong.  Defensive programming.
                    JOptionPane.showMessageDialog(null, "Unexpected response " + choiceResposne);
            }



        }
    }
    
    private class LoanStateToRejectedListener implements ActionListener {

        public LoanStateToRejectedListener() 
        {
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            log.info("LoanStateChangeToPayedListener actionPerformed");

            loanService = new LoanServiceImpl();
            loanService.removeExistingLoanRequest(client);

            loanTabView.removeExisitingLoanTab(client);
            loanTabView.showNewLoanTab(client);
            //loanTabView.pack();
            //loanTabView.repaint();
        }
    }

    public void reValidateLastLoan() {
        LoanHome loanHome = new LoanHome();
        Loan loan = loanHome.findLastLoanForClient(client);
        loanTabView.getExistingLoanRequestPanel().getjLabel8().setText(loan.getDebt());
        loanTabView.getExistingLoanRequestPanel().getjLabel9().setText(loan.getDueDate());
    }

    private class LoanStateToPayedListener implements ActionListener {

        public LoanStateToPayedListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            log.info("LoanStateChangeToPayedListener actionPerformed");

            loanService = new LoanServiceImpl();
            loanService.removeExistingLoanRequest(client);

            loanTabView.removeExisitingLoanTab(client);
            loanTabView.showNewLoanTab(client);
            //loanTabView.pack();
            //loanTabView.repaint();
        }
    }
}
