/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loansystem.control;

//public NewLoanRequestPanel newLoanRequestPanel;
import com.loansystem.view.LoanSystemView;
import com.loansystem.UI.client.ClientFrameBasic1;
import com.loansystem.UI.client.ExistingLoanRequestPanel;
import com.loansystem.UI.client.LoanRequestCTab;
import com.loansystem.UI.client.MyLoansPanel;
import com.loansystem.UI.client.MyLoansTab;
import com.loansystem.UI.client.NewLoanRequestPanel;
import com.loansystem.UI.employee.PendingLoanControlsPanel;
import com.loansystem.UI.employee.PendingLoanDetailedView;
import com.loansystem.UI.employee.PendingLoanDetailedViewPanel;
import com.loansystem.UI.employee.PendingLoanRequestsPanel;
import com.loansystem.UI.employee.PendingLoanRequestsTab;
import com.loansystem.UI.employee.PendingLoansPanel;
import com.loansystem.UI.employee.TestLabelForm;
import com.loansystem.UI.employee.TestTable;
import com.loansystem.backend.model.LoanModel;
import com.loansystem.backend.model.LoanSystemModel;
import com.loansystem.backend.model.LoanTabModel;
import com.loansystem.backend.model.MyLoansTabModel;
import com.loansystem.backend.model.PendingLoansTabModel;
import com.loansystem.backend.model.PostponeRequestedLoansTabModel;
import com.loansystem.backend.model.UserLoginInput;
import com.loansystem.classificator.ClientStatusClassificator;
import com.loansystem.classificator.EmployeeType;
import com.loansystem.classificator.PostponeRequestStatus;
import com.loansystem.db.dao.ClientHome;
import com.loansystem.db.dao.LoanHistoryHome;
import com.loansystem.db.dao.LoanHome;
import com.loansystem.db.dao.LoanStatusHome;
import com.loansystem.db.dao.PostponeRequestStatusHome;
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
import com.loansystem.model.PostponeRequest;
import com.loansystem.model.User;
import com.loansystem.service.LoanService;
import com.loansystem.service.LoanServiceImpl;
import com.loansystem.service.LoanUiService;
import com.loansystem.service.LoanUiServiceImpl;
import com.loansystem.util.DateUtil;
import com.loansystem.util.LoanUIutils;
import com.loansystem.validator.LoginValidator;
import com.loansystem.view.LoanTabView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JPanel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

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
        boolean overdue = checkIfOverdue(loanTabModel.getLastLoan());

        ///save all data that will tell that loan is overdue
        if (overdue) {
            
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
          
            //Transaction transaction = session.beginTransaction();

            LoanService loanService = new LoanServiceImpl();
            loanService.saveLoanWithStatus(loanTabModel.getLastLoan(), LoanStatusInterface.OVERDUE, session, "Loan issued");
            session.getTransaction().commit();
            Loan lastLoan = loanService.getLoanById(loanTabModel.getLastLoan(), null);
            loanTabModel.setLastLoan(lastLoan);
            createOverdueFrame();
            return;
        }

        
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


        MyLoansPanel myLoansPanel = new MyLoansPanel(myLoansTabModel, true);
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


        MyLoansPanel myLoansPanel = new MyLoansPanel(myLoansTabModel, true);
        myLoansTabModel.setMyLoansPanel(myLoansPanel);

        ArrayList<JPanel> myLoansTabPanels = new ArrayList<JPanel>();
        myLoansTabPanels = myLoansTabModel.getCreatedPanels();

        panels[0] = new MyLoansTab(myLoansTabPanels);
        panels[1] = new LoanRequestCTab(loanRequestCTabPanels);

        ClientFrameBasic1 basicFrame = new ClientFrameBasic1(panels);
    }

    private void createRejectedFrame() {
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


        MyLoansPanel myLoansPanel = new MyLoansPanel(myLoansTabModel, true);
        myLoansTabModel.setMyLoansPanel(myLoansPanel);

        ArrayList<JPanel> myLoansTabPanels = new ArrayList<JPanel>();
        myLoansTabPanels = myLoansTabModel.getCreatedPanels();

        panels[0] = new MyLoansTab(myLoansTabPanels);
        panels[1] = new LoanRequestCTab(loanRequestCTabPanels);

        ClientFrameBasic1 basicFrame = new ClientFrameBasic1(panels);
    }

    private void createOverdueFrame() {
        LoanTabModel loanTabModel = new LoanTabModel(loginClient);
        boolean debtCollection = checkIfDebtCollection(loanTabModel.getLastLoan());

        ///save all data that will tell that loan is overdue
        if (debtCollection) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction transaction = session.beginTransaction();

            LoanService loanService = new LoanServiceImpl();
            loanService.saveLoanWithStatus(loanTabModel.getLastLoan(), LoanStatusInterface.SENT_TO_DEBT_COLLECTION, session, "sent to debt collection");
             Loan lastLoan = loanService.getLoanById(loanTabModel.getLastLoan(), null);
                Client client = lastLoan.getClient();
          
               
                
            loanService.saveClientWithStatus(client, ClientStatusClassificator.BLACKLISTED, session);
             session.getTransaction().commit();
           
            loanTabModel.setLastLoan(lastLoan);

        
         
            //set client to blacklisted

        }
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


        MyLoansPanel myLoansPanel = new MyLoansPanel(myLoansTabModel, true);
        myLoansTabModel.setMyLoansPanel(myLoansPanel);

        ArrayList<JPanel> myLoansTabPanels = new ArrayList<JPanel>();
        myLoansTabPanels = myLoansTabModel.getCreatedPanels();

        panels[0] = new MyLoansTab(myLoansTabPanels);
        panels[1] = new LoanRequestCTab(loanRequestCTabPanels);

        ClientFrameBasic1 basicFrame = new ClientFrameBasic1(panels);
    }

    private boolean checkIfOverdue(Loan lastLoan) {
        Date now = new Date();
        Date loanDueDate = null;
        try {
            loanDueDate = DateUtil.dateFormat.parse(lastLoan.getDueDate());
            Date now1 = DateUtil.getDatePlusDays(loanDueDate, 1);
            if (now1.compareTo(now) <= 0) {
                return true;
            }
        } catch (Exception e) {
            log.debug("checkIfOverdue() some error occured" + e);
            return true;
        }
        return false;
    }

    private boolean checkIfDebtCollection(Loan lastLoan) {
        Date now = new Date();
        Date loanDueDate = null;
        try {
            loanDueDate = DateUtil.dateFormat.parse(lastLoan.getDueDate());
            Date now1 = DateUtil.getDatePlusDays(loanDueDate, 14); // after 2 weeks loan is sent to debt collection
            if (now1.compareTo(now) <= 0) {
                return true;
            }
        } catch (Exception e) {
            log.debug("checkIfDebtCollection() some error occured" + e);
            return true;
        }
        return false;
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
                char[] passwordText = cview.getLoginView().getjPasswordField1().getPassword();

                String password = String.valueOf(passwordText);

                UserHome userHome = new UserHome();
                user = userHome.findByMailPassword(loginText, password);
                if (user != null) {
                    if (user.getEmployees().size() == 1) {
                        log.info("employee logged in");
                        frameType = UserType.EMPLOYEE;
                        createEmployeeSpecificFrame(user);
                        try {
                            cview.getLoginView().setVisible(false);
                        } catch (Exception ex) {
                            log.error(ex);
                        } finally {
                            session.close();
                            log.info("closing session");
                        }

                    } else if (user.getClients().size() == 1) {
                        if (Integer.parseInt(user.getClients().get(0).getClientStatus().getId())
                                != ClientStatusClassificator.BLACKLISTED) {
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

                                loanOffers.addAll(user.getClients().get(0).getClientGroup().getLoanOffers());

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
                             session.close();
                            createLoanStatusSpecificFrame(loan);
                            try {
                                cview.getLoginView().setVisible(false);
                            } catch (Exception ex) {
                                log.error(ex);
                            } finally {
                                session.close();
                                log.info("closing session");
                            }
                        } else {
                            LoanUIutils.createWarningPopup("blacklisted client", "user with status " + user.getClients().get(0).getClientStatus().getName() + " is not allowed to login");
                            log.info("Blackilisted client logged in");
                        }

                    } else {
                        log.info("GHOST logged in");
                    }


                } else {
                    LoanUIutils.createWarningPopup("invalid login", "user with specified credentials was not found in database");
                    log.info("GHOST logged in");
                }
            } catch (Exception ex) {
                log.error("jButton1ActionPerformed" + ex);
            }
        }

        private void createEmployeeSpecificFrame(User user) {
            switch (user.getEmployees().get(0).getEmployeeType()) {
                case EmployeeType.CAM:
                    createCAMFrame(user);
                    break;
                case EmployeeType.TL:
                    createTLFrame(user);
                    break;

            }
        }

        private void createCAMFrame(User user) {



            PendingLoansTabModel pendingLoansTabModel = new PendingLoansTabModel();
            pendingLoansTabModel.setUser(user);
            pendingLoansTabModel.setEmployee(user.getEmployees().get(0));


            ArrayList<Loan> pendingLoans = new ArrayList<Loan>();
            LoanHome loanHome = new LoanHome();
            LoanStatusHome loanStatusHome = new LoanStatusHome();
            LoanStatus pendingLoanStatus = loanStatusHome.findById(LoanStatusInterface.PENDING + "");
            pendingLoans = loanHome.getLoansByStatus(pendingLoanStatus, null);
            pendingLoansTabModel.setPendingLoans(pendingLoans);

            PendingLoanRequestsPanel pendingLoansPanel = new PendingLoanRequestsPanel(pendingLoansTabModel, true);
            //TestTable testTable = new TestTable(true);

            PendingLoanDetailedViewPanel pendingLoanDetailedViewPanel = new PendingLoanDetailedViewPanel(pendingLoansTabModel, false);
            //TestLabelForm testLabelForm = new TestLabelForm(true);
            PendingLoanControlsPanel pendingLoanControlsPanel = new PendingLoanControlsPanel(pendingLoansTabModel, false);
            MyLoansPanel clientLoansPanel = new MyLoansPanel(pendingLoansTabModel, false);

            pendingLoansTabModel.setPendingLoanRequestsPanel(pendingLoansPanel);
            pendingLoansTabModel.setPendingLoanControls(pendingLoanControlsPanel);
            pendingLoansTabModel.setPendingLoanDetailedViewPanel(pendingLoanDetailedViewPanel);
            pendingLoansTabModel.setClientLoansPanel(clientLoansPanel);

            ArrayList<JPanel> pendingLoansTabPanels = new ArrayList<JPanel>();
            pendingLoansTabPanels = pendingLoansTabModel.getCreatedPanels();

            PendingLoanRequestsTab pendingLoansTab = new PendingLoanRequestsTab(pendingLoansTabPanels);

            PendingLoansTabController pendingLoansTabController = new PendingLoansTabController(pendingLoansTabModel, pendingLoansTab);



            JPanel[] camPanels = new JPanel[1];
            camPanels[0] = pendingLoansTab;


            ClientFrameBasic1 basicFrame = new ClientFrameBasic1(camPanels);





            //PendingLoansTabView pendingLoansTabView = new PendingLoansTabView();



        }

        private void createTLFrame(User user) {
            PostponeRequestedLoansTabModel postponeRequestedLoansTabModel = new PostponeRequestedLoansTabModel();
            postponeRequestedLoansTabModel.setUser(user);
            postponeRequestedLoansTabModel.setEmployee(user.getEmployees().get(0));


            ArrayList<Loan> postponeRequestedLoans = new ArrayList<Loan>();
            LoanHome loanHome = new LoanHome();
            LoanService loanService = new LoanServiceImpl();
            PostponeRequestStatusHome postponeRequestStatusHome = new PostponeRequestStatusHome();
            com.loansystem.model.PostponeRequestStatus postponeRequestStatus = new com.loansystem.model.PostponeRequestStatus();
            postponeRequestStatus = postponeRequestStatusHome.findById(PostponeRequestStatus.REQUESTED + "");

            //ArrayList<Loan> clientLoans = loanService.getClientLoans()


            postponeRequestedLoans = loanService.getPostponedLoansByStatus(postponeRequestStatus);
            postponeRequestedLoansTabModel.setPostponeRequestedLoans(postponeRequestedLoans);
            /*PostponeRequestStatusHome postponeRequestStatusHome = new PostponeRequestStatusHome();
            LoanStatus pendingLoanStatus = loanStatusHome.findById(LoanStatusInterface.PENDING+"");
            pendingLoans = loanHome.getLoansByStatus(pendingLoanStatus, null);
            pendingLoansTabModel.setPendingLoans(pendingLoans);*/

            PendingLoanRequestsPanel postponeRequestedLoansPanel = new PendingLoanRequestsPanel(postponeRequestedLoansTabModel, true);
            //TestTable testTable = new TestTable(true);

            PendingLoanDetailedViewPanel postponeRequestedLoanDetailedViewPanel = new PendingLoanDetailedViewPanel(postponeRequestedLoansTabModel, false);
            //TestLabelForm testLabelForm = new TestLabelForm(true);
            PendingLoanControlsPanel postponeRequestedLoanControlsPanel = new PendingLoanControlsPanel(postponeRequestedLoansTabModel, false);
            MyLoansPanel clientLoansPanel = new MyLoansPanel(postponeRequestedLoansTabModel, false);

            postponeRequestedLoansTabModel.setPostponeRequestedLoansPanel(postponeRequestedLoansPanel);
            postponeRequestedLoansTabModel.setPostponeRequestedLoanDetailedViewPanel(postponeRequestedLoanDetailedViewPanel);
            postponeRequestedLoansTabModel.setPostponeRequestedLoanControlsPanel(postponeRequestedLoanControlsPanel);
            postponeRequestedLoansTabModel.setClientLoansPanel(clientLoansPanel);

            ArrayList<JPanel> pendingLoansTabPanels = new ArrayList<JPanel>();
            pendingLoansTabPanels = postponeRequestedLoansTabModel.getCreatedPanels();

            PendingLoanRequestsTab pendingLoansTab = new PendingLoanRequestsTab(pendingLoansTabPanels);

            PostponeRequestedLoansTabController pendingLoansTabController = new PostponeRequestedLoansTabController(postponeRequestedLoansTabModel, pendingLoansTab);



            JPanel[] camPanels = new JPanel[1];
            camPanels[0] = pendingLoansTab;


            ClientFrameBasic1 basicFrame = new ClientFrameBasic1(camPanels);
        }
    }

    private void createLoanStatusSpecificFrame(Loan loan) {
        PostponeRequest postponeRequest = loan.getPostponeRequest();
        int loanStatus = Integer.parseInt(loan.getLoanStatus().getLoanStatusId());



        if (postponeRequest != null) {
            if (loanStatus != LoanStatusInterface.PAYED_BACK && loanStatus != LoanStatusInterface.OVERDUE /*&& loanStatus != LoanStatusInterface.SENT_TO_DEBT_COLLECTION*/) {
                try {
                    switch (Integer.parseInt(postponeRequest.getPostponeRequestStatus().getId())) {
                        case PostponeRequestStatus.ACCEPTED:
                            createIssuedFrame();
                            break;
                        case PostponeRequestStatus.CANCELED:
                            createPostponeReuqestedFrame();
                            break;
                        case PostponeRequestStatus.REJECTED:
                            createIssuedFrame();
                            break;
                        case PostponeRequestStatus.REQUESTED:
                            createPostponeReuqestedFrame();
                            break;

                    }

                } catch (Exception e) {
                    log.error(e);
                }
            } else {
                createFrame(loanStatus);
            }
        } else {
            createFrame(loanStatus);
        }
    }

    public void createFrame(int loanStatus) {
        if (loanStatus == LoanStatusInterface.PENDING) {
            createPendingFrame();
        }

        if (loanStatus == LoanStatusInterface.REJECTED) {
            createRejectedFrame();
        }

        if (loanStatus == LoanStatusInterface.OVERDUE) {
            createIssuedFrame();
        }
        if (loanStatus == LoanStatusInterface.ISSUED) {
            createIssuedFrame();
        }
        if (loanStatus == LoanStatusInterface.PAYED_BACK) {
            createPayedBackFrame();
        }
        /*if (loanStatusEnum.equals(LoanStatusEnum.POSTPONED)) {
        createPostponedFrame();
        }*/
        /*if (loanStatus == LoanStatusInterface.SENT_TO_DEBT_COLLECTION) {
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


        MyLoansPanel myLoansPanel = new MyLoansPanel(myLoansTabModel, true);
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
        log.info("LOANOFFERS SIZE" + loginClient.getClientGroup().getLoanOffers().size());
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


        MyLoansPanel myLoansPanel = new MyLoansPanel(myLoansTabModel, true);
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
