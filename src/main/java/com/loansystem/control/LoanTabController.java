/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loansystem.control;

import com.loansystem.UI.client.ExistingLoanRequestPanel;
import com.loansystem.UI.client.NewLoanRequestPanel;
import com.loansystem.UI.client.PostponeRequestPanel;
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
import com.loansystem.view.LoanPostponeView;
import com.loansystem.view.LoanTabView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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

    private class LoanStateChangeToPostponedListener implements ActionListener {

        public LoanStateChangeToPostponedListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            loanService = new LoanServiceImpl();
            Loan lastLoan = loanService.getLastLoan(client);
            postponeRequestPanel = loanTabView.getPostponeRequestPanel();
            loanTabView.showPostponeControls();
            //LoanPostponeView loanPostponeView = new LoanPostponeView(lastLoan);
            //showNotification("Loan", lastLoan);
        }

        private void showNotification(String string, Loan lastLoan) {
            
        }
    }
    NewLoanRequestPanel newLoanRequestPanel;
    ExistingLoanRequestPanel existingLoanRequestPanel;
    PostponeRequestPanel postponeRequestPanel;
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
        loanTabView.addLoanStateChangeToPostponedListener(new LoanStateChangeToPostponedListener());
        loanTabView.addSliderListener(new SliderStateChangedListener());
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
                    Loan lastLoan  = reValidateLastLoan();
                    loanTabModel.setLastLoan(lastLoan);
                    loanTabView.showExistingLoanTabControls(client);
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

        public LoanStateToRejectedListener() {
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

    public Loan reValidateLastLoan() {
        LoanHome loanHome = new LoanHome();
        Loan loan = loanHome.findLastLoanForClient(client);
        
        loanTabView.getExistingLoanRequestPanel().getjLabel8().setText(loan.getDebt());
        loanTabView.getExistingLoanRequestPanel().getjLabel9().setText(loan.getDueDate());
        return loan;
    }

    private class LoanStateToPayedListener implements ActionListener {

        public LoanStateToPayedListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            log.info("LoanStateChangeToPayedListener actionPerformed");

            loanService = new LoanServiceImpl();
            loanService.removeExistingLoanRequest(client);
            
            loanTabView.removeExistingLoanTabControls(client);
            
           // loanTabView.removeExisitingLoanTab(client);
            
            loanTabView.showNewLoanTab(client);
            //loanTabView.pack();
            //loanTabView.repaint();
        }
    }
    
    
    private class SliderStateChangedListener implements ChangeListener {

        public SliderStateChangedListener() {
        }

        @Override
        public void stateChanged(ChangeEvent e) {

            JSlider source = (JSlider) e.getSource();
            Loan lastLoan = loanTabModel.getLastLoan();
            LoanOffer loanOffer = loanTabModel.getLastLoan().getLoanOffer();
            float initialSum = Float.parseFloat(loanOffer.getSum());
            float percentSum = initialSum / 100;
            String initialDueDate = lastLoan.getDueDate();

            if (!source.getValueIsAdjusting()) {
                int fps = (int) source.getValue();
                log.info("Current Days Selected: " + fps);

                //String oldDueDate = lastLoan.getDueDate();
                Date oldDate = new Date(initialDueDate);
                log.info("Current old Date : " + oldDate);
                Date newDueDate = DateUtil.getDatePlusDays(oldDate, fps);
                log.info("Current Due  Date : " + newDueDate);
                float newSum = initialSum + percentSum * (Float.parseFloat(loanOffer.getPeriod()) + fps);
                log.info("Current sum : " + newSum);

                postponeRequestPanel.getjLabel3().setText(String.valueOf(newDueDate));
                postponeRequestPanel.getjLabel4().setText(String.valueOf(newSum));

            }
        }
    }
}
