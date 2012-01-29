/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loansystem.control;

import com.loansystem.UI.client.ClientFrameBasic1;
import com.loansystem.UI.client.ExistingLoanRequestPanel;
import com.loansystem.UI.client.LoanPostponeRequestCTab;
import com.loansystem.UI.client.LoanRequestCTab;
import com.loansystem.UI.client.MyLoansTab;
import com.loansystem.UI.client.NewLoanRequestPanel;
import com.loansystem.backend.model.LoanTabModel;
import com.loansystem.hibernate.HibernateUtil;
import com.loansystem.model.Client;
import com.loansystem.model.Loan;
import com.loansystem.model.LoanHistory;
import com.loansystem.model.LoanOffer;
import com.loansystem.model.LoanStatus;
import com.loansystem.service.LoanService;
import com.loansystem.service.LoanServiceImpl;
import com.loansystem.view.LoanTabView;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JPanel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/*import com.loansystem.UI.client.LoanRequestCTab;
import com.loansystem.UI.client.ClientFrameBasic1;
import com.loansystem.UI.client.LoanPostponeRequestCTab;
import com.loansystem.UI.client.MyLoansTab;
import com.loansystem.UI.client.NewLoanRequestPanel;
import com.loansystem.classificator.LoanStatusClassificator;
import com.loansystem.control.FrameBuilderImpl.LoanStatusEnum;
import com.loansystem.model.Client;
import com.loansystem.model.Loan;
import com.loansystem.model.LoanHistory;
import com.loansystem.model.LoanOffer;
import com.loansystem.model.LoanStatus;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;*/
/**
 *
 * @author antonve
 */
public class FrameBuilderImpl extends FrameBuilder {

    private static final Log log = LogFactory.getLog(FrameBuilderImpl.class);
    private static LoanStatusEnum LoanStatusEnum;
    private static Client client;
    private static Loan loan;
    private static LoanHistory loanHistory;

    public static LoanStatusEnum getLoanStatusEnum() {
        return LoanStatusEnum;
    }

    public static void setLoanStatusEnum(LoanStatusEnum LoanStatusEnum) {
        FrameBuilderImpl.LoanStatusEnum = LoanStatusEnum;
    }

    public static Client getClient() {
        return client;
    }

    public static void setClient(Client client) {
        FrameBuilderImpl.client = client;
    }

    public static Loan getLoan() {
        return loan;
    }

    public static void setLoan(Loan loan) {
        FrameBuilderImpl.loan = loan;
    }

    public static LoanHistory getLoanHistory() {
        return loanHistory;
    }

    public static void setLoanHistory(LoanHistory loanHistory) {
        FrameBuilderImpl.loanHistory = loanHistory;
    }

    public static List<LoanOffer> getLoanOffers() {
        return loanOffers;
    }

    public static void setLoanOffers(List<LoanOffer> loanOffers) {
        FrameBuilderImpl.loanOffers = loanOffers;
    }

    public LoanStatusEnum getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(LoanStatusEnum loanStatus) {
        this.loanStatus = loanStatus;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
    private static List<LoanOffer> loanOffers;
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private Session session;
    private LoanStatusEnum loanStatus;

    public enum LoanStatusEnum {

        PENDING,
        REJECTED,
        POSTPONE_REQUESTED,
        OVERDUE,
        ISSUED,
        PAYED_BACK,
        POSTPONED,
        SENT_TO_DEBT_COLLECTION;

       
    }

    public FrameBuilderImpl(Client client) {
        if (client != null) {
            try {
                session = sessionFactory.getCurrentSession();
                //session.beginTransaction();
                this.client = client;
                this.loanOffers = client.getCientGroup().getLoanOffers();
                this.loan = client.getLoans().get(0);

            } catch (Exception e) {
                log.info("No loans exist for the client");
                log.error(e);
                LoanStatusEnum elem = LoanStatusEnum.PAYED_BACK;
                //elem.createFrame();
                return;
            }
            //loans exist for the client




            //this.loanOffer = loan.get
            String lastStatus = null;

            LoanStatus lastLoanStatus = loan.getLoanStatus();
            if (lastLoanStatus.getName() != null) {
                lastStatus = lastLoanStatus.getName();
                log.info("LAST LOAN STATUS " + lastStatus.toUpperCase());
                try {
                    loanStatus = LoanStatusEnum.valueOf(lastStatus.toUpperCase());
                    log.info(loanStatus.name());
                    //elem.createFrame(); to front controller
                } catch (Exception e) {
                    log.info("Error occured when casting to enum" + e.getMessage());
                }

            }
        }

    }

    public JPanel addMyLoansTab() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
