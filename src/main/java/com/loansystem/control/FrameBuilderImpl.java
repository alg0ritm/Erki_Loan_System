/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loansystem.control;

import com.loansystem.classificator.LoanStatusClassificator;
import com.loansystem.model.Client;
import javax.swing.JPanel;

/**
 *
 * @author antonve
 */
public class FrameBuilderImpl implements FrameBuilder, LoanStatusClassificator {


    enum LoanStatus {

        LAST_REQUESTED {

            public JPanel addNewMyLoansTab() {
                return addNewMyLoansTab();
            }
        },
        LAST_REJECTED {

            public JPanel addNewMyLoansTab() {
                return addNewMyLoansTab();
            }

            public JPanel addNewLoanRequestTab() {
                return addNewLoanRequestTab();
            }
        },
        LAST_ISSUED {

            public JPanel addMyLoansTab() {
                return addMyLoansTab();
            }

            public JPanel addLoanPaybackTab() {
                return addLoanPaybackTab();
            }

            public JPanel addLoanPostponeRequestTab() {
                return addLoanPostponeRequestTab();
            }
        },
        LAST_REPAID {

            public JPanel addMyLoansTab() {
                return addMyLoansTab();
            }

            public JPanel addLoanRequestTab() {
                return addLoanRequestTab();
            }
        },
        LAST_POSTPONED {

            public JPanel addMyLoansTab() {
                return addMyLoansTab();
            }

            public JPanel addLoanPaybackTab() {
                return addLoanPaybackTab();
            }
        },
        SENT_TO_DEBT_COLLECTION {
            public JPanel addMyLoansTab() {
                return addMyLoansTab();
            }
            
        }
    }

    public FrameBuilderImpl(Client Client) {
        int hasActiveLoan;

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
