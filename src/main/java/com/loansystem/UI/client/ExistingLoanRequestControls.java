/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ExistingLoanRequestControls.java
 *
 * Created on Feb 5, 2012, 2:36:22 PM
 */
package com.loansystem.UI.client;

import com.loansystem.backend.model.ButtonPlace;
import com.loansystem.backend.model.LoanTabModel;
import com.loansystem.db.dao.LoanStatusHome;
import com.loansystem.enums.LoanStatusInterface;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 *
 * @author antonve
 */
public class ExistingLoanRequestControls extends javax.swing.JPanel {

    JButton jButton1;
    JButton jButton2;
    JButton jButton3;
    LoanTabModel loanTabModel;
    /*private final ButtonPlace payButtonLocation = new ButtonPlace(0, 0);
    private final ButtonPlace removeRequestLocation = new ButtonPlace(1, 0);
    private final ButtonPlace postponeButtonLocation = new ButtonPlace(0, 1);
    private final ButtonPlace removePostponeButtonLocation = new ButtonPlace(1, 1);*/
    private JButton jButton4;

    /** Creates new form ExistingLoanRequestControls */
    public ExistingLoanRequestControls(LoanTabModel loanTabModel) {
        this.loanTabModel = loanTabModel;
        initComponents();
        setBorder(BorderFactory.createTitledBorder("Exisiting Loan Controls"));
        initComponents(this.loanTabModel);
    }

    private void initComponents(LoanTabModel loanTabModel) {


        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        jButton1.setText("Remove Loan Request");
        jButton1.setName("jButton1"); // NOI18N
        jButton1.setVisible(false);



        jButton2 = new javax.swing.JButton();
        jButton2.setText("Pay Back");
        jButton2.setName("jButton2"); // NOI18N
        jButton2.setVisible(false);
       


        jButton3.setText("Postpone payback");
        jButton3.setName("jButton3"); // NOI18N
        jButton3.setVisible(false);
        
        jButton4.setText("Cancel Postpone payback request");
        jButton4.setName("jButton4"); // NOI18N
        jButton4.setVisible(false);

        GridLayout layout = new GridLayout(2, 2);
         

        setLayout(layout);
        
        add(jButton1, 0);
        add(jButton2, 1);
        add(jButton3, 2);
        add(jButton4, 3);

        boolean showRemoveLoanRequestButton = showRemoveLoanRequestButton(loanTabModel.getLastLoan().getLoanStatus().getLoanStatusId());
        if (showRemoveLoanRequestButton) {
            jButton1.setVisible(true);
        }


        boolean showPayButton = showPayButton(loanTabModel.getLastLoan().getLoanStatus().getLoanStatusId());
        if (showPayButton) {
            jButton2.setVisible(true);
        }
        //gridLayout1 = new GridLayout(2,3,5,5); // 2 by 3; gaps of 5

        boolean showPostponeButton = showPostponeButton(loanTabModel.getLastLoan().getLoanStatus().getLoanStatusId());
        if (showPostponeButton) {
            jButton3.setVisible(true);
        }
        
        boolean showRemovePostponeRequestButton = showRemovePostponeRequestButton(loanTabModel.getLastLoan().getLoanStatus().getLoanStatusId());
        if (showRemovePostponeRequestButton) {
            jButton4.setVisible(true);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 603, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    public void addRejectListener(ActionListener mal) {
        jButton1.addActionListener(mal);
    }

    public void addPayBackListener(ActionListener payBackListener) {
        jButton2.addActionListener(payBackListener);
    }

    public void addPostponeListener(ActionListener postponeListener) {
        jButton3.addActionListener(postponeListener);
    }

    public void addRemoveRequestListener(ActionListener removeRequestListener) {
        jButton1.addActionListener(removeRequestListener);
    }
    
    public void addRemovePostponeRequestListener(ActionListener removePostponeRequestListener) {
         jButton4.addActionListener(removePostponeRequestListener);
    }

    private boolean showPayButton(String statusId) {
        int status = Integer.parseInt(statusId);

        switch (status) {
            case LoanStatusInterface.ISSUED:
            case LoanStatusInterface.POSTPONED:
            case LoanStatusInterface.OVERDUE:
                return true;
            case LoanStatusInterface.POSTPONE_REQUESTED:
            case LoanStatusInterface.SENT_TO_DEBT_COLLECTION:
            case LoanStatusInterface.REJECTED:
                return false;
            default:
                return false;
        }

    }

    private boolean showPostponeButton(String loanStatusId) {
        int status = Integer.parseInt(loanStatusId);

        switch (status) {
            case LoanStatusInterface.ISSUED:
                return true;
            case LoanStatusInterface.POSTPONE_REQUESTED:
            case LoanStatusInterface.POSTPONED:
            case LoanStatusInterface.PENDING:
                return false;
            default:
                return false;
        }
    }
    
    private boolean showRemovePostponeRequestButton(String loanStatusId) {
        int status = Integer.parseInt(loanStatusId);

        switch (status) {
           case LoanStatusInterface.POSTPONE_REQUESTED:
                return true;
            default:
                return false;
        }
    }

    private boolean showRemoveLoanRequestButton(String loanStatusId) {
        int status = Integer.parseInt(loanStatusId);

        switch (status) {
           case LoanStatusInterface.PENDING:
                return true;
            default:
                return false;
        }
    }

    
}
