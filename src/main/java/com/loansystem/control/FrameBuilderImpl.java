/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loansystem.control;

import com.loansystem.UI.client.AddNewLoanRequestTabJPanel;
import com.loansystem.UI.client.ClientFrameBasic;
import com.loansystem.UI.client.ClientFrameBasic1;
import com.loansystem.UI.client.addMyLoansTabJPanel;
import com.loansystem.classificator.LoanStatusClassificator;
import com.loansystem.control.FrameBuilderImpl.LoanStatusEnum;
import com.loansystem.model.Client;
import com.loansystem.model.Loan;
import com.loansystem.model.LoanHistory;
import com.loansystem.model.LoanStatus;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author antonve
 */
public class FrameBuilderImpl implements FrameBuilder, LoanStatusClassificator {

    private static final Log log = LogFactory.getLog(FrameBuilderImpl.class);
    private static LoanStatusEnum LoanStatusEnum;
    private Client client;
    private Loan loan;
    private LoanHistory loanHistory;

    interface GetLoanStatusEnumInterface {

        LoanStatusEnum getLoanStatusEnum(String loanStatus);
    }

    public class StringValJoiner implements GetLoanStatusEnumInterface {

        public LoanStatusEnum getLoanStatusEnum(String loanStatus) {
            //throw new UnsupportedOperationException("Not supported yet.");
            return LoanStatusEnum;
        }
    }

    public enum LoanStatusEnum {

        REQUESTED,
        REJECTED,
        ISSUED,
        REPAID,
        POSTPONED,
        SENT_TO_DEBT_COLLECTION;

        public void createFrame() {
            if (equals(REPAID)) {
                createRepaidFrame();
            }

        }

        public void createRepaidFrame() {
            JPanel[] panels = new JPanel[2];            
            panels[0] = new AddNewLoanRequestTabJPanel();          
            panels[1] = new addMyLoansTabJPanel();
            ClientFrameBasic1 basicFrame = new ClientFrameBasic1(panels);
        }

        public void createPostponedFrame() {
            JPanel[] panels = new JPanel[2];
            panels[1] = new AddNewLoanRequestTabJPanel();
            panels[2] = new addMyLoansTabJPanel();
            ClientFrameBasic basicFrame = new ClientFrameBasic(panels);
        }
    }

    public FrameBuilderImpl(LoanHistory loanHistory) {
        if (loanHistory != null) {
            this.loanHistory = loanHistory;
            this.loan = loanHistory.getLoan();
            this.client = loan.getClient();

            String lastStatus = null;

            LoanStatus lastLoanStatus = loan.getLoanStatus();
            if (lastLoanStatus.getName() != null) {
                lastStatus = lastLoanStatus.getName();
                log.info("LAST LOAN STATUS " + lastStatus.toUpperCase());
                try {
                    LoanStatusEnum elem = LoanStatusEnum.valueOf(lastStatus.toUpperCase());
                    log.info(elem.name());
                    elem.createFrame();
                } catch (Exception e) {
                    log.info("Error occured when casting to enum" + e.getMessage());
                }

            }
        }

    }

    public JPanel addNewLoanRequestTab() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public JPanel addLoanRequestTab() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public JPanel addLoanPaybackTab() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public JPanel addLoanPostponeRequestTab() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public JPanel addMyLoansTab() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
