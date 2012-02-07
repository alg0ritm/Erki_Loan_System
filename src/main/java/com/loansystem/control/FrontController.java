/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loansystem.control;

//public NewLoanRequestPanel newLoanRequestPanel;
import com.loansystem.view.LoanSystemView;
import com.loansystem.UI.LoginForm;
import com.loansystem.UI.client.ClientFrameBasic1;
import com.loansystem.UI.client.ExistingLoanRequestControls;
import com.loansystem.UI.client.ExistingLoanRequestPanel;
import com.loansystem.UI.client.LoanPostponeRequestCTab;
import com.loansystem.UI.client.LoanRequestCTab;
import com.loansystem.UI.client.MyLoansTab;
import com.loansystem.UI.client.NewLoanRequestPanel;
import com.loansystem.UI.client.PostponeRequestPanel;
import com.loansystem.backend.model.LoanSystemModel;
import com.loansystem.backend.model.LoanTabModel;
import com.loansystem.backend.model.UserLoginInput;
import com.loansystem.control.FrameBuilderImpl.LoanStatusEnum;
import com.loansystem.db.dao.ClientHome;
import com.loansystem.hibernate.HibernateUtil;
import com.loansystem.model.Client;
import com.loansystem.model.Loan;
import com.loansystem.model.LoanHistory;
import com.loansystem.validator.LoginValidator;
import com.loansystem.view.LoanTabView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author antonve
 */
public class FrontController {

    private static final Log log = LogFactory.getLog(LoanSystemObserver.class);
    public LoanSystemView cview;
    public LoanSystemModel cmodel;
    public static LoanSystemObserver cobserver;
    private UserLoginInput userLoginInput;
    public HibernateUtil hu;
    private Client loginClient;

    public FrontController(LoanSystemView view, LoanSystemModel model, LoanSystemObserver observer) {
        cview = view;
        cmodel = model;
        cobserver = observer;


        cview.addLoginListener(new LoginListener());
        //cview.addClearListener(new ClearListener());
    }

    ////////////////////////////////////////// inner class MultiplyListener
    /** When a mulitplication is requested.
     *  1. Get the user input number from the View.
     *  2. Call the model to mulitply by this number.
     *  3. Get the result from the Model.
     *  4. Tell the View to display the result.
     * If there was an error, tell the View to display it.
     */
    class LoginListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            ArrayList<Loan> loansList;
            LoanHistory loanHistory = null;
            Loan loan = null;
            Session session = null;

            try {
                cobserver.notifyMsg("event has been held");

                SessionFactory sessionFactory = hu.getSessionFactory();
                session = sessionFactory.openSession();
                //session.beginTransaction();
                try {
                    String loginText = cview.getLoginView().getjTextField1().getText();
                    String passwordText = cview.getLoginView().getjTextField2().getText();
                    UserLoginInput userLoginInput = new UserLoginInput(loginText, passwordText);
                    //setUserLoginInput(userLoginInput);
                    loginClient = new Client();
                    loansList = new ArrayList<Loan>();
                    ClientHome clientHome = new ClientHome();
                    loginClient = clientHome.findByMailPassword(loginText, passwordText);



                    //check if client logged in
                    if (loginClient != null) {
                        //getting last loan status & loan history object for logging in client
                        ArrayList<LoanHistory> loanHistoryList = new ArrayList<LoanHistory>();
                        log.info("INSIDE");
                        //example query
                        loanHistoryList = (ArrayList<LoanHistory>) session.createCriteria(LoanHistory.class).addOrder(Order.desc("rowId")).setMaxResults(1).createCriteria("loan").add(Restrictions.eq("client", loginClient)).createCriteria("loanStatus").list();

                        Iterator it = loanHistoryList.iterator();

                        while (it.hasNext()) {
                            loanHistory = (LoanHistory) it.next();
                            log.info(loanHistory.getDate());
                            log.info(loanHistory.getLoan().getLoanStatus().getDescription());
                            log.info(loanHistory.getLoan().getLoanStatus().getName());
                        }

                        loan = (Loan) session.createCriteria(Loan.class).add(Restrictions.eq("client", loginClient)).addOrder(Order.desc("id")).setMaxResults(1).uniqueResult();
                        //.createCriteria("loanStatus");


                        //log.info("INITIAL CONTEXT" + new InitialContext().lookup("java:hibernate/SessionFactory"));

                        //ex query
                        loansList = (ArrayList<Loan>) session.createCriteria(Loan.class).add(Restrictions.eq("client", loginClient)).addOrder(Order.desc("id")).setMaxResults(1).createCriteria("loanStatus").list();
                    }
                    //TODO add emloyee login
                    log.info("TO FRAME BUILDER ");

                    FrameBuilderImpl clientFrame = new FrameBuilderImpl(loginClient);
                    LoanStatusEnum loanStatus = clientFrame.getLoanStatus();
                    createLoanStatusSpecificFrame(loanStatus);
                    log.info("SOME 1");
                    log.info("FROM FRAME BUILDER ");
                    cview.getLoginView().setVisible(false);

                } catch (Exception ex) {
                    log.error(ex);
                } finally {
                    session.close();
                    log.info("closing session");
                }
            } catch (Exception ex) {
                log.error("jButton1ActionPerformed" + ex);
            }
            LoginValidator loginValidator = new LoginValidator();
            /*String[] userInput = new String[2];
            int authUser = 0;
            
            
            
            try {
            // userLoginInput = cview.getLoginView().getUserLoginInput();
            JTextField login1 = cview.getLoginView().getjTextField1();
            log.info("Login info acuired from loginView : " + login1.getText());
            //log.info("Login info acuired from loginView : " + userLoginInput.getLogin() );
            // authUser = cmodel.getLoginModel().authUser(userInput);
            
            }
            
            catch (NumberFormatException nfex
            
            
            ) {
            cview.showError("Login Failed: '" + userInput + "'");
            }
            }
            }//end inner class MultiplyListener*/
//////////////////////////////////////////// inner class ClearListener
            /**  1. Reset model.
             *   2. Reset View.
             */
            /*class ClearListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
            m_model.reset();
            m_view.reset();
            }
            }*/// end inner class ClearListener
        }

        private void createLoanStatusSpecificFrame(LoanStatusEnum loanStatusEnum) {
            createFrame(loanStatusEnum);
        }
    }

    public void createFrame(LoanStatusEnum loanStatusEnum) {
        if (loanStatusEnum.equals(LoanStatusEnum.PENDING)) {
            createPendingFrame();
        }
        if (loanStatusEnum.equals(LoanStatusEnum.REJECTED)) {
            createRejectedFrame();
        }
        if (loanStatusEnum.equals(LoanStatusEnum.POSTPONE_REQUESTED)) {
            createPostponeReuqestedFrame();
        }
        if (loanStatusEnum.equals(LoanStatusEnum.OVERDUE)) {
            createOverdueFrame();
        }
        if (loanStatusEnum.equals(LoanStatusEnum.ISSUED)) {
            createIssuedFrame();
        }
        if (loanStatusEnum.equals(LoanStatusEnum.PAYED_BACK)) {
            createPayedBackFrame();
        }
        if (loanStatusEnum.equals(LoanStatusEnum.POSTPONED)) {
            createPostponedFrame();
        }
        if (loanStatusEnum.equals(LoanStatusEnum.SENT_TO_DEBT_COLLECTION)) {
            createSentToDebtColletionFrame();
        }

    }

    //vqnesti metodq v controller
    public void createPendingFrame() {
        LoanTabModel loanTabModel = new LoanTabModel(loginClient);
        JPanel[] loanRequestCTabPanels = new JPanel[4];
        ExistingLoanRequestPanel existingLoanRequestPanel = new ExistingLoanRequestPanel(loginClient, true); //client to upper level
        NewLoanRequestPanel newLoanRequestPanel = new NewLoanRequestPanel(loginClient, false);
        PostponeRequestPanel postponeRequestPanel = new PostponeRequestPanel(false, loanTabModel);
        ExistingLoanRequestControls existingLoanRequestControls = new ExistingLoanRequestControls();
        loanRequestCTabPanels[0] = existingLoanRequestPanel;
        loanRequestCTabPanels[1] = newLoanRequestPanel;
        loanRequestCTabPanels[2] = existingLoanRequestControls;
        loanRequestCTabPanels[3] = postponeRequestPanel;

        JPanel[] panels = new JPanel[1];


        LoanTabView loanTabView = new LoanTabView(existingLoanRequestPanel, newLoanRequestPanel, existingLoanRequestControls, postponeRequestPanel);
        LoanTabController loanTabController = new LoanTabController(loanTabView, loanTabModel, loginClient);




        panels[0] = new LoanRequestCTab(loanRequestCTabPanels);

        ClientFrameBasic1 basicFrame = new ClientFrameBasic1(panels);


        /*JPanel[] loanRequestCTabPanels = new JPanel[1];
        log.info("LOANOFFERS SIZE" + loanOffers.size());
        loanRequestCTabPanels[0] = new NewLoanRequestPanel(client);
        JPanel[] panels = new JPanel[2];
        
        panels[0] = new LoanRequestCTab(loanRequestCTabPanels);
        panels[1] = new MyLoansTab();
        ClientFrameBasic1 basicFrame = new ClientFrameBasic1(panels);*/
    }

    public void createRequestedFrame() {
        JPanel[] loanRequestCTabPanels = new JPanel[1];

        loanRequestCTabPanels[0] = new NewLoanRequestPanel();
        JPanel[] panels = new JPanel[2];

        panels[0] = new LoanRequestCTab(loanRequestCTabPanels);
        panels[1] = new MyLoansTab();
        ClientFrameBasic1 basicFrame = new ClientFrameBasic1(panels);
    }

    public void createRejectedFrame() {
        JPanel[] loanRequestCTabPanels = new JPanel[1];
        loanRequestCTabPanels[0] = new NewLoanRequestPanel();
        JPanel[] panels = new JPanel[2];

        panels[0] = new LoanRequestCTab(loanRequestCTabPanels);
        panels[1] = new MyLoansTab();
        ClientFrameBasic1 basicFrame = new ClientFrameBasic1(panels);

    }

    public void createRepaidFrame() {
    }

    public void createIssuedFrame() {
        JPanel[] panels = new JPanel[2];
        panels[0] = new LoanPostponeRequestCTab();
        ClientFrameBasic1 basicFrame = new ClientFrameBasic1(panels);
    }

    public void createPostponedFrame() {
        JPanel[] loanRequestCTabPanels = new JPanel[1];
        loanRequestCTabPanels[0] = new NewLoanRequestPanel();
        JPanel[] panels = new JPanel[2];

        panels[0] = new LoanRequestCTab(loanRequestCTabPanels);
        panels[1] = new MyLoansTab();
        ClientFrameBasic1 basicFrame = new ClientFrameBasic1(panels);
    }

    public void createSentToDebtColletionFrame() {
        JPanel[] panels = null;
        ClientFrameBasic1 basicFrame = new ClientFrameBasic1(panels);
    }

    private void createPostponeReuqestedFrame() {
        JPanel[] loanRequestCTabPanels = new JPanel[1];
        loanRequestCTabPanels[0] = new NewLoanRequestPanel();
        JPanel[] panels = new JPanel[2];

        panels[0] = new LoanRequestCTab(loanRequestCTabPanels);
        panels[1] = new MyLoansTab();
        ClientFrameBasic1 basicFrame = new ClientFrameBasic1(panels);
    }

    private void createOverdueFrame() {
        JPanel[] loanRequestCTabPanels = new JPanel[1];
        loanRequestCTabPanels[0] = new NewLoanRequestPanel();
        JPanel[] panels = new JPanel[2];

        panels[0] = new LoanRequestCTab(loanRequestCTabPanels);
        panels[1] = new MyLoansTab();
        ClientFrameBasic1 basicFrame = new ClientFrameBasic1(panels);
    }

    private void createPayedBackFrame() {
        LoanTabModel loanTabModel = new LoanTabModel(loginClient);
        log.info("LOANOFFERS SIZE" + loginClient.getCientGroup().getLoanOffers().size());
        JPanel[] loanRequestCTabPanels = new JPanel[4];
        ExistingLoanRequestPanel existingLoanRequestPanel = new ExistingLoanRequestPanel(loginClient, false); //client to upper level
        NewLoanRequestPanel newLoanRequestPanel = new NewLoanRequestPanel(loginClient, true);
        PostponeRequestPanel postponeRequestPanel = new PostponeRequestPanel(false, loanTabModel);
        ExistingLoanRequestControls existingLoanRequestControls = new ExistingLoanRequestControls();
        loanRequestCTabPanels[0] = existingLoanRequestPanel;
        loanRequestCTabPanels[1] = newLoanRequestPanel;
        loanRequestCTabPanels[2] = existingLoanRequestControls;
        loanRequestCTabPanels[3] = postponeRequestPanel;

        JPanel[] panels = new JPanel[2];


        LoanTabView loanTabView = new LoanTabView(existingLoanRequestPanel, newLoanRequestPanel, existingLoanRequestControls, postponeRequestPanel);
        LoanTabController loanTabController = new LoanTabController(loanTabView, loanTabModel, loginClient);




        panels[0] = new LoanRequestCTab(loanRequestCTabPanels);
        panels[1] = new MyLoansTab();

        ClientFrameBasic1 basicFrame = new ClientFrameBasic1(panels);
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        /*try {
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
        javax.swing.UIManager.setLookAndFeel(info.getClassName());
        break;
        
        
        }
        }
        } catch (ClassNotFoundException ex) {
        java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
        java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
        java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
        java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                //LoanSystemView loanSystemView = new LoanSystemView();
                LoanSystemObserver cobserver = new LoanSystemObserver();
                LoanSystemView loanSystemView = new LoanSystemView(cobserver);
                LoanSystemModel loanSystemModel = new LoanSystemModel();
                FrontController frontController = new FrontController(loanSystemView, loanSystemModel, cobserver);
                SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
                Session session;
                //session = sessionFactory.openSession();
                //session.beginTransaction();
                HibernateUtil hu = new HibernateUtil();

                //LoginForm loginForm = new LoginForm(cobserver);
                //cobserver.addObserver(loginForm);
                //new LoginForm(cobserver).setVisible(true);
            }
        });
    }
}
