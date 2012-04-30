/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loansystem.control;

import com.loansystem.UI.client.ClientFrameBasic1;
import com.loansystem.UI.client.ExistingLoanRequestPanel;
import com.loansystem.UI.client.Experemimental;
import com.loansystem.UI.client.LoanRequestCTab;
import com.loansystem.UI.client.MyLoansTab;
import com.loansystem.UI.client.NewLoanRequestPanel;
import com.loansystem.UI.client.PostponeRequestPanel;
import com.loansystem.backend.model.LoanInsertRequest;
import com.loansystem.backend.model.LoanTabModel;
import com.loansystem.classificator.PostponeRequestStatus;
import com.loansystem.db.dao.LoanHome;
import com.loansystem.db.dao.LoanStatusHome;
import com.loansystem.db.dao.PostponeRequestHome;
import com.loansystem.db.dao.PostponeRequestStatusHome;
import com.loansystem.enums.LoanStatusEnum;
import com.loansystem.enums.LoanStatusInterface;
import com.loansystem.hibernate.HibernateUtil;
import com.loansystem.model.Client;
import com.loansystem.model.Loan;
import com.loansystem.model.LoanOffer;
import com.loansystem.model.LoanStatus;
import com.loansystem.model.PostponeRequest;
import com.loansystem.service.LoanService;
import com.loansystem.service.LoanServiceImpl;
import com.loansystem.service.LoanUiService;
import com.loansystem.service.LoanUiServiceImpl;
import com.loansystem.util.DateUtil;
import com.loansystem.util.LoanUIutils;
import com.loansystem.view.LoanPostponeView;
import com.loansystem.view.LoanTabView;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
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

    private class RemovePostponeRequestListener implements ActionListener {

        public RemovePostponeRequestListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            log.info("RemovePostponeRequestListener actionPerformed");

            loanService = new LoanServiceImpl();
            //loanService.cancelExistingPostponeRequest(loanTabModel, client);


            loanTabView.removeExistingLoanTabControls(client);
            String[] opts = {"No", "Yes"};

            PostponeRequest postponeRequst = loanTabModel.getLastLoan().getPostponeRequest(); //create postpone reuqest in db
            int response = LoanUIutils.createQuestionPopup(opts, postponeRequst, "Are You sure to cancel postpone request with next parameters?");

            // postponeRequest.setComment("Postpone cancel is requested");
            switch (response) {
                case 1:
                    postponeRequst.setComment("Postpone cancel is rejected");
                    loanTabModel.getLastLoan().setPostponeRequest(postponeRequst);
                    loanTabView.hideUnnecessaryButtons();
                    PostponeRequest postponeRequestUpd = loanService.updateLastPostponedLoan(loanTabModel, loanTabModel.getLastLoan().getDueDate(), loanTabModel.getLastLoan().getLoanOffer().getSum(), PostponeRequestStatus.CANCELED);
                    loanTabModel.getLastLoan().setPostponeRequest(postponeRequestUpd);
                    break;
                default:


            }
            // loanTabView.removeExisitingLoanTab(client);
            loanTabView.showExisitingLoanTab(client);
            loanTabView.showExistingLoanTabControls(client);
            //loanTabView.showNewLoanTab(client);
            //loanTabView.pack();
            //loanTabView.repaint();

        }
    }
    

    private class removeLoanRequestListener implements ActionListener {

        public removeLoanRequestListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            log.info("RemoveLoanRequestListener actionPerformed");

            loanService = new LoanServiceImpl();

            String[] opts = {"No", "Yes"};
            PostponeRequest postponeRequst = loanTabModel.getLastLoan().getPostponeRequest();
            /*int response = LoanUIutils.createQuestionPopup(opts, postponeRequest, "Are You sure you want to cancel initiated postpone request?");*/
            loanService.removeExistingLoanRequest(loanTabModel, client);

            loanTabView.removeExistingLoanTabControls(client);

            // loanTabView.removeExisitingLoanTab(client);
            loanTabView.hideUnnecessaryButtons();

            loanTabView.showNewLoanTab(client);
            //loanTabView.pack();
            //loanTabView.repaint();

        }
    }

    private class LoanPostponeRequestedListener implements ActionListener {

        public LoanPostponeRequestedListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {




            loanService = new LoanServiceImpl();
            PostponeRequest postponeRequest = new PostponeRequest();
            String sum = loanTabModel.getPostponeRequestPanel().getjLabel4().getText();
            String dueDate = loanTabModel.getPostponeRequestPanel().getjLabel3().getText();
            //PostponeRequestStatus
            PostponeRequestStatusHome postponeRequestStatusHome = new PostponeRequestStatusHome();
            com.loansystem.model.PostponeRequestStatus postponeRequestStatus = postponeRequestStatusHome.findById(PostponeRequestStatus.REQUESTED + "");
            postponeRequest.setDate(dueDate);
            postponeRequest.setStatusId(postponeRequestStatus);

            String[] opts = {"No", "Yes"};
            int response = LoanUIutils.createQuestionPopup(opts, postponeRequest, "Are You sure to postpone request with next parameters?");

            postponeRequest.setComment("Postpone is requested");
            switch (response) {
                case 1: //ok 
                    loanService.createPostponeRequest(postponeRequest);
                    loanTabView.hidePostponeControls();
                    /*this.loanId = id;
                    this.dueDate = dueDate;
                    this.baseDueDate = baseDueDate;
                    this.apr = apr;
                    this.debt = debt;
                    this.client = client;
                    this.employeeId = employeeId;
                    this.loanStatus = loanStatus;
                    this.loanOffer = loanOffer;
                    this.postponeRequestId = postponeRequestId;
                    this.loanHistory = loanHistory;*/

                    loanTabModel.getLastLoan().setPostponeRequest(postponeRequest);
                    loanService.updateLastPostponedLoan(loanTabModel, dueDate, sum, PostponeRequestStatus.REQUESTED);
                    loanTabView.hideUnnecessaryButtons();
                    loanTabView.updateExistingLoanPanel();


                    /*jLabel8.setText(loan.getDebt());
                    jLabel9.setText(loan.getDueDate());
                    jLabel10.setText(loan.getLoanStatus().getDescription());
                    loanTabModel.updateLastLoan();
                     */

                    break;
                default:
                    break;

            }

            //loanService.createPostponeRequest(postponeRequest);








        }
    }

    private class LoanStateChangeToPostponedListener implements ActionListener {

        public LoanStateChangeToPostponedListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            loanService = new LoanServiceImpl();
            Loan lastLoan = loanService.getLastLoan(client);
            postponeRequestPanel = loanTabView.getPostponeRequestPanel();
            loanTabView.showPostponeControls();
            // Experemimental exp = new Experemimental();

            //ClientFrameBasic1.exisitingLoanRequestPanel.removeAll();

            //LoanPostponeView loanPostponeView = new LoanPostponeView(lastLoan);
            //showNotification("Loan", lastLoan);
        }

        private void showNotification(String string, Loan lastLoan) {
        }
    }
    
    private class ChooseOtherLoanOfferListener implements ActionListener {
        public ChooseOtherLoanOfferListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
           log.info("RemoveLoanRequestListener actionPerformed");

            //loanService = new LoanServiceImpl();

            //String[] opts = {"No", "Yes"};
            //PostponeRequest postponeRequst = loanTabModel.getLastLoan().getPostponeRequest();
            /*int response = LoanUIutils.createQuestionPopup(opts, postponeRequest, "Are You sure you want to cancel initiated postpone request?");*/
            //loanService.removeExistingLoanRequest(loanTabModel, client);

            loanTabView.removeExistingLoanTabControls(client);

            // loanTabView.removeExisitingLoanTab(client);
            loanTabView.hideUnnecessaryButtons();

            loanTabView.showNewLoanTab(client);
            //loanTabView.pack();
            //loanTabView.repaint();
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
        initNewLoanListeners();
        if (loanTabView.getExistingLoanRequestPanel() != null) {
            initExisitingLoanListeners();
        }


        // loanTabView.addLoanStateChangeToRejectedListener(new LoanStateToRejectedListener());

        this.loanTabView.setVisible(true);
    }

    private void initNewLoanListeners() {
        loanTabView.addLoanStateChangeToPendingListener(new LoanStateChangeToPendingListener());
    }

    private void initExisitingLoanListeners() {
        loanTabView.addLoanStateChangeToPayedListener(new LoanStateToPayedListener());
        loanTabView.addRemoveLoanRequestListener(new removeLoanRequestListener());
        loanTabView.addRemovePostponeRequestListener(new RemovePostponeRequestListener());
        loanTabView.addLoanStateChangeToPostponedListener(new LoanStateChangeToPostponedListener());
        loanTabView.addSliderListener(new SliderStateChangedListener());
        loanTabView.addLoanPostponeConfirmedListener(new LoanPostponeRequestedListener());
        loanTabView.addLoanPostponeCancelListener(new LoanPostponeCancelListener());
        loanTabView.addChooseOtherLoanOfferListener(new ChooseOtherLoanOfferListener());

    }
    
    private class LoanPostponeCancelListener implements ActionListener {
        
        public LoanPostponeCancelListener() {
            
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            loanTabView.hidePostponeControls();
        }
        
    }

    private class LoanStateChangeToPendingListener implements ActionListener {

        public LoanStateChangeToPendingListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //ask if sure to take the loan  
            int rowIndex = loanTabView.getNewLoanRequestPanel().getjTable1().getSelectedRow();
            if(rowIndex<0) {
                JOptionPane.showMessageDialog(loanTabView.getNewLoanRequestPanel(), "Y've not chosen any loan offer");
              return;  
            }

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
                    Loan lastLoan = null;
                    lastLoan = loanService.createNewLoan(client, selectedLoanOffer);
                    loanTabModel.setLastLoan(lastLoan);

                    LoanUiService loanUiService = new LoanUiServiceImpl();
                    existingLoanRequestPanel = loanUiService.createExistingLoanRequestPanel(client, loanTabModel);
                    loanTabView.setExistingLoanRequestPanel(existingLoanRequestPanel);
                    loanTabView.removeNewLoanTab(client);
                    loanTabModel.setLastLoan(lastLoan);
                    loanTabView.showExistingLoanTabControls(client);
                    loanTabView.showExisitingLoanTab(client);



                    ArrayList<JPanel> loanRequestCTabPanels = loanTabModel.getCreatedPanels();
                    //loanTabModel.getExistingLoanRequestPanel()

                    ClientFrameBasic1.exisitingLoanRequestPanel.removeAll();

                    for (JPanel panel : loanRequestCTabPanels) {
                        ClientFrameBasic1.exisitingLoanRequestPanel.add(panel);
                    }


                    ClientFrameBasic1.exisitingLoanRequestPanel.validate();
                    ClientFrameBasic1.exisitingLoanRequestPanel.repaint();
                    initExisitingLoanListeners();

                    loanTabView = new LoanTabView(existingLoanRequestPanel, newLoanRequestPanel, loanTabModel);

                    /*ArrayList<JPanel> loanRequestCTabPanels = new ArrayList<JPanel>();
                    loanRequestCTabPanels = loanTabModel.getCreatedPanels();
                    
                    
                    
                    
                    
                    JPanel[] panels = new JPanel[2];
                    
                    ClientFrameBasic1.basicFrame.getContentPane().removeAll();
                    ClientFrameBasic1.basicFrame.getContentPane().invalidate();
                    //ClientFrameBasic1.basicFrame.setVisible(false);
                    ClientFrameBasic1.basicFrame.pack();
                    ClientFrameBasic1.basicFrame.validate();
                    //ClientFrameBasic1.basicFrame.repaint();
                    
                    
                    panels[0] = new LoanRequestCTab(loanRequestCTabPanels);
                    panels[1] = new MyLoansTab();
                    
                    
                    ClientFrameBasic1.basicFrame.getContentPane().add(panels[0]);
                    ClientFrameBasic1.basicFrame.getContentPane().add(panels[1]);
                    //ClientFrameBasic1.basicFrame.getContentPane().revalidate();
                    ClientFrameBasic1.basicFrame.pack();
                    ClientFrameBasic1.basicFrame.validate();
                    ClientFrameBasic1.basicFrame.repaint();
                    //ClientFrameBasic1.basicFrame.revalidate();
                    
                    //ClientFrameBasic1 basic2 = new ClientFrameBasic1(panels);*/



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
            loanService.removeExistingLoanRequest(loanTabModel, client);

            loanTabView.removeExisitingLoanTab(client);
            loanTabView.showNewLoanTab(client);
            //loanTabView.pack();
            //loanTabView.repaint();
        }
    }

    public Loan reValidateLastLoan() {
        LoanHome loanHome = new LoanHome();
        //Loan loan = loanHome.findLastLoanForClient(client);

        /* loanTabView.getExistingLoanRequestPanel().getjLabel8().setText(loan.getDebt());
        loanTabView.getExistingLoanRequestPanel().getjLabel9().setText(loan.getDueDate());
        return loan;*/
        return null;
    }

    private class LoanStateToPayedListener implements ActionListener {

        public LoanStateToPayedListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            log.info("RemoveLoanRequestListener actionPerformed");

            loanService = new LoanServiceImpl();
            loanService.removeExistingLoanRequest(loanTabModel, client);

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
                String dueDateString = DateUtil.dateFormat.format(newDueDate);
                log.info("Current Due  Date : " + newDueDate);
                float newSum = initialSum + percentSum * (Float.parseFloat(loanOffer.getPeriod()) + fps);
                log.info("Current sum : " + newSum);

                loanTabModel.getPostponeRequestPanel().getjLabel3().setText(String.valueOf(dueDateString));
                loanTabModel.getPostponeRequestPanel().getjLabel4().setText(String.valueOf(newSum));

            }
        }
    }
}
