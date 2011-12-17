/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loansystem.control;

import com.loansystem.UI.client.ClientFrameBasic1;
import com.loansystem.UI.client.LoanPostponeRequestCTab;
import com.loansystem.UI.client.LoanRequestCTab;
import com.loansystem.UI.client.MyLoansTab;
import com.loansystem.UI.client.NewLoanRequestPanel;
import com.loansystem.model.Client;
import com.loansystem.model.Loan;
import com.loansystem.model.LoanHistory;
import com.loansystem.model.LoanOffer;
import com.loansystem.model.LoanStatus;
import com.loansystem.service.LoanOfferService;
import com.loansystem.service.LoanOfferServiceImpl;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JPanel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
public class FrameBuilderImpl implements FrameBuilder {

    private static final Log log = LogFactory.getLog(FrameBuilderImpl.class);
    private static LoanStatusEnum LoanStatusEnum;
    private static Client client;
    private static Loan loan;
    private static LoanHistory loanHistory;
    private static Set<LoanOffer> loanOffers;

    public enum LoanStatusEnum {

        PENDING,
        REJECTED,
        POSTPONE_REQUESTED,
        OVERDUE,
        ISSUED,
        PAYED_BACK,
        POSTPONED,
        SENT_TO_DEBT_COLLECTION;

        public void createFrame(Loan loan) {
            if (equals(PENDING)) {
                createRequestedFrame();
            }
            if (equals(REJECTED)) {
                createRejectedFrame();
            }
            if (equals(POSTPONE_REQUESTED)) {
                createPostponeReuqestedFrame();
            }
            if (equals(OVERDUE)) {
                createOverdueFrame();
            }
            if (equals(ISSUED)) {
                createIssuedFrame();
            }
            if (equals(PAYED_BACK)) {
                createPayedBackFrame();
            }
            if (equals(POSTPONED)) {
                createPostponedFrame();
            }
            if (equals(SENT_TO_DEBT_COLLECTION)) {
                createSentToDebtColletionFrame();
            }

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
            JPanel[] loanRequestCTabPanels = new JPanel[1];
            ArrayList<LoanOffer> availLoanOffers = new ArrayList<LoanOffer>();
            log.info("LOANOFFERS SIZE" + loanOffers.size());
            loanRequestCTabPanels[0] = new NewLoanRequestPanel(loanOffers);
            JPanel[] panels = new JPanel[2];

            panels[0] = new LoanRequestCTab(loanRequestCTabPanels);
            panels[1] = new MyLoansTab();
            ClientFrameBasic1 basicFrame = new ClientFrameBasic1(panels);
        }
    }

    public FrameBuilderImpl(Loan loan) {
        if (loan != null) {
            this.loan = loan;
            this.client = loan.getClient();
            this.loanOffers = client.getCientGroup().getLoanOffers();

            //this.loanOffer = loan.get
            String lastStatus = null;

            LoanStatus lastLoanStatus = loan.getLoanStatus();
            if (lastLoanStatus.getName() != null) {
                lastStatus = lastLoanStatus.getName();
                log.info("LAST LOAN STATUS " + lastStatus.toUpperCase());
                try {
                    LoanStatusEnum elem = LoanStatusEnum.valueOf(lastStatus.toUpperCase());
                    log.info(elem.name());
                    elem.createFrame(loan);
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
