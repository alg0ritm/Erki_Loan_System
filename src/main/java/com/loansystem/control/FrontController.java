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
import com.loansystem.UI.client.MyLoansPanel;
import com.loansystem.UI.client.MyLoansTab;
import com.loansystem.UI.client.NewLoanRequestPanel;
import com.loansystem.UI.client.PostponeRequestPanel;
import com.loansystem.backend.model.LoanModel;
import com.loansystem.backend.model.LoanSystemModel;
import com.loansystem.backend.model.LoanTabModel;
import com.loansystem.backend.model.MyLoansTabModel;
import com.loansystem.backend.model.UserLoginInput;
import com.loansystem.db.dao.ClientHome;
import com.loansystem.db.dao.UserHome;
import com.loansystem.enums.LoanStatusEnum;
import com.loansystem.enums.LoanStatusInterface;
import com.loansystem.enums.UserType;
import com.loansystem.hibernate.HibernateUtil;
import com.loansystem.model.Client;
import com.loansystem.model.Employee;
import com.loansystem.model.Loan;
import com.loansystem.model.LoanHistory;
import com.loansystem.model.LoanOffer;
import com.loansystem.model.LoanStatus;
import com.loansystem.model.User;
import com.loansystem.service.LoanUiService;
import com.loansystem.service.LoanUiServiceImpl;
import com.loansystem.service.LoginService;
import com.loansystem.service.LoginServiceImpl;
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
    //TODO : create service/utils for creating frames
    private void createIssuedFrame() {
        LoanTabModel loanTabModel = new LoanTabModel(loginClient);
        ArrayList<JPanel> loanRequestCTabPanels = new ArrayList<JPanel>();


        JPanel[] panels = new JPanel[2];

        LoanUiService loanUiService = new LoanUiServiceImpl();
        ExistingLoanRequestPanel existingLoanRequestPanel = loanUiService.createExistingLoanRequestPanel(loginClient, loanTabModel);


        NewLoanRequestPanel newLoanRequestPanel = new NewLoanRequestPanel(loginClient, false);

        loanTabModel.setNewLoanRequestPanel(newLoanRequestPanel);


        LoanTabView loanTabView = new LoanTabView(existingLoanRequestPanel, newLoanRequestPanel, loanTabModel);
        LoanTabController loanTabController = new LoanTabController(loanTabView, loanTabModel, loginClient);

        loanRequestCTabPanels = loanTabModel.getCreatedPanels();
        
        MyLoansTabModel myLoansTabModel = new MyLoansTabModel();
        myLoansTabModel.setClient(loginClient);
        
        
        MyLoansPanel  myLoansPanel = new MyLoansPanel(myLoansTabModel, true);
        myLoansTabModel.setMyLoansPanel(myLoansPanel);
        
        ArrayList<JPanel> myLoansTabPanels = new ArrayList<JPanel>();
        myLoansTabPanels = myLoansTabModel.getCreatedPanels();

        panels[0] = new MyLoansTab(myLoansTabPanels);
        panels[1] = new LoanRequestCTab(loanRequestCTabPanels);

        ClientFrameBasic1 basicFrame = new ClientFrameBasic1(panels);
    }

    private void createPostponeReuqestedFrame() {
         LoanTabModel loanTabModel = new LoanTabModel(loginClient);
        ArrayList<JPanel> loanRequestCTabPanels = new ArrayList<JPanel>();


        JPanel[] panels = new JPanel[2];

        LoanUiService loanUiService = new LoanUiServiceImpl();
        ExistingLoanRequestPanel existingLoanRequestPanel = loanUiService.createExistingLoanRequestPanel(loginClient, loanTabModel);


        NewLoanRequestPanel newLoanRequestPanel = new NewLoanRequestPanel(loginClient, false);

        loanTabModel.setNewLoanRequestPanel(newLoanRequestPanel);


        LoanTabView loanTabView = new LoanTabView(existingLoanRequestPanel, newLoanRequestPanel, loanTabModel);
        LoanTabController loanTabController = new LoanTabController(loanTabView, loanTabModel, loginClient);

        loanRequestCTabPanels = loanTabModel.getCreatedPanels();
        
        MyLoansTabModel myLoansTabModel = new MyLoansTabModel();
        myLoansTabModel.setClient(loginClient);
        
        
        MyLoansPanel  myLoansPanel = new MyLoansPanel(myLoansTabModel, true);
        myLoansTabModel.setMyLoansPanel(myLoansPanel);
        
        ArrayList<JPanel> myLoansTabPanels = new ArrayList<JPanel>();
        myLoansTabPanels = myLoansTabModel.getCreatedPanels();

        panels[0] = new MyLoansTab(myLoansTabPanels);
        panels[1] = new LoanRequestCTab(loanRequestCTabPanels);

        ClientFrameBasic1 basicFrame = new ClientFrameBasic1(panels);
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
            boolean hibernateTest = true;
            LoanModel loanModel;
            int frameType = 0;


            try {
                cobserver.notifyMsg("event has been held");

                SessionFactory sessionFactory = hu.getSessionFactory();
                session = sessionFactory.openSession();

                User user = null;

                ArrayList<Client> clientsList = new ArrayList<Client>();
                //clientsList
                
                String loginText = cview.getLoginView().getjTextField1().getText();
                String passwordText = cview.getLoginView().getjTextField2().getText();
                
                UserHome userHome = new UserHome();
                user = userHome.findByMailPassword(loginText, passwordText);
                
                log.info("User " + user.getPersCode());
                log.info("User " + user.getClients().size());
                log.info("User " + user.getEmployees().size());

                if (user.getEmployees().size() == 1) {
                    log.info("employee logged in");
                    frameType = UserType.EMPLOYEE;

                } else if (user.getClients().size() == 1) {
                    log.info("client logged in");
                    frameType = UserType.CLIENT;
                    String lastStatus = null;
                    LoanStatus lastLoanStatus = null;
                    loginClient = user.getClients().get(0);
                    int loanStatus = 0;

                    try {
                        session = sessionFactory.getCurrentSession();
                        loanModel = new LoanModel();

                        ArrayList<LoanOffer> loanOffers = new ArrayList<LoanOffer>();

                        loanOffers.addAll(user.getClients().get(0).getCientGroup().getLoanOffers());

                        if (user.getClients().get(0).getLoans().size() > 0) {
                            loan = user.getClients().get(0).getLoans().get(0);
                        }

                    } catch (Exception ex) {
                        log.info("No loans exist for the client");
                        log.error(ex);

                    }


                    

                    if (loan != null) {
                        lastLoanStatus = loan.getLoanStatus();
                    }
                    if (lastLoanStatus != null) { //loan exisits
                        lastStatus = lastLoanStatus.getLoanStatusId();
                        log.info("LAST LOAN STATUS " + lastStatus.toUpperCase());
                        try {

                            loanStatus = Integer.parseInt(lastStatus);
                            log.info(loanStatus);
                            //elem.createFrame(); to front controller
                        } catch (Exception exc) {
                            log.info("Error occured when casting to enum" + exc.getMessage());
                        }

                    } else { //loan doesn't exist
                        log.info("NO LOAN EXIST FOR THE CLIENT");
                        loanStatus = LoanStatusInterface.PAYED_BACK;
                    }
                    createLoanStatusSpecificFrame(loanStatus);
                } else {
                    log.info("GHOST logged in");
                }



                try {
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

        }

        private void createLoanStatusSpecificFrame(int loanStatus) {
            createFrame(loanStatus);
        }
    }

    public void createFrame(int loanStatus) {
        if (loanStatus == LoanStatusInterface.PENDING) {
            createPendingFrame();
        }
        if (loanStatus == LoanStatusInterface.POSTPONE_REQUESTED) {
            createPostponeReuqestedFrame();
        }
        /*if (loanStatusEnum.equals(LoanStatusEnum.REJECTED)) {
        createRejectedFrame();
        }
       
        if (loanStatusEnum.equals(LoanStatusEnum.OVERDUE)) {
        createOverdueFrame();
        }*/
        if (loanStatus == LoanStatusInterface.ISSUED) {
            createIssuedFrame();
        }
        if (loanStatus == LoanStatusInterface.PAYED_BACK || loanStatus == LoanStatusInterface.SENT_TO_DEBT_COLLECTION) {
            createPayedBackFrame();
        }
        /*if (loanStatusEnum.equals(LoanStatusEnum.POSTPONED)) {
        createPostponedFrame();
        }
        if (loanStatusEnum.equals(LoanStatusEnum.SENT_TO_DEBT_COLLECTION)) {
        createSentToDebtColletionFrame();
        }*/

    }

    public void createPendingFrame() {
        LoanTabModel loanTabModel = new LoanTabModel(loginClient);
        ArrayList<JPanel> loanRequestCTabPanels = new ArrayList<JPanel>();


        JPanel[] panels = new JPanel[2];

        LoanUiService loanUiService = new LoanUiServiceImpl();
        ExistingLoanRequestPanel existingLoanRequestPanel = loanUiService.createExistingLoanRequestPanel(loginClient, loanTabModel);


        NewLoanRequestPanel newLoanRequestPanel = new NewLoanRequestPanel(loginClient, false);

        loanTabModel.setNewLoanRequestPanel(newLoanRequestPanel);


        LoanTabView loanTabView = new LoanTabView(existingLoanRequestPanel, newLoanRequestPanel, loanTabModel);
        LoanTabController loanTabController = new LoanTabController(loanTabView, loanTabModel, loginClient);

        loanRequestCTabPanels = loanTabModel.getCreatedPanels();
        
        MyLoansTabModel myLoansTabModel = new MyLoansTabModel();
        myLoansTabModel.setClient(loginClient);
        
        
        MyLoansPanel  myLoansPanel = new MyLoansPanel(myLoansTabModel, true);
        myLoansTabModel.setMyLoansPanel(myLoansPanel);
        
        ArrayList<JPanel> myLoansTabPanels = new ArrayList<JPanel>();
        myLoansTabPanels = myLoansTabModel.getCreatedPanels();

        panels[0] = new MyLoansTab(myLoansTabPanels);
        panels[1] = new LoanRequestCTab(loanRequestCTabPanels);

        ClientFrameBasic1 basicFrame = new ClientFrameBasic1(panels);


        /*JPanel[] loanRequestCTabPanels = new JPanel[1];
        log.info("LOANOFFERS SIZE" + loanOffers.size());
        loanRequestCTabPanels[0] = new NewLoanRequestPanel(client);
        JPanel[] panels = new JPanel[2];
        
        panels[0] = new LoanRequestCTab(loanRequestCTabPanels);
        panels[1] = new MyLoansTab();
        ClientFrameBasic1 basicFrame = new ClientFrameBasic1(panels);*/
    }

    /* public void createRequestedFrame() {
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
    }*/
    private void createPayedBackFrame() {
        LoanTabModel loanTabModel = new LoanTabModel(loginClient);
        log.info("LOANOFFERS SIZE" + loginClient.getCientGroup().getLoanOffers().size());
        LoanUiService loanUiService = new LoanUiServiceImpl();
        /*ExistingLoanRequestPanel existingLoanRequestPanel = loanUiService.createExistingLoanRequestPanel(loginClient, loanTabModel);*/


        NewLoanRequestPanel newLoanRequestPanel = new NewLoanRequestPanel(loginClient, true);
        loanTabModel.setNewLoanRequestPanel(newLoanRequestPanel);
        LoanTabView loanTabView = new LoanTabView(newLoanRequestPanel, loanTabModel);
        LoanTabController loanTabController = new LoanTabController(loanTabView, loanTabModel, loginClient);
        ArrayList<JPanel> loanRequestCTabPanels = new ArrayList<JPanel>();
        loanRequestCTabPanels = loanTabModel.getCreatedPanels();
        
        MyLoansTabModel myLoansTabModel = new MyLoansTabModel();
        myLoansTabModel.setClient(loginClient);
        
        
        MyLoansPanel  myLoansPanel = new MyLoansPanel(myLoansTabModel, true);
        myLoansTabModel.setMyLoansPanel(myLoansPanel);
        
        ArrayList<JPanel> myLoansTabPanels = new ArrayList<JPanel>();
        myLoansTabPanels = myLoansTabModel.getCreatedPanels();
        
        JPanel[] panels = new JPanel[2];

        panels[0] = new MyLoansTab(myLoansTabPanels);
        panels[1] = new LoanRequestCTab(loanRequestCTabPanels);

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
