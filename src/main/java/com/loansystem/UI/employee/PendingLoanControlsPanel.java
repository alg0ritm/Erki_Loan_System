/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PendingLoanControlsPanel.java
 *
 * Created on Apr 25, 2012, 4:31:07 PM
 */
package com.loansystem.UI.employee;

import com.loansystem.backend.model.LoanTabModel;
import com.loansystem.backend.model.PendingLoansTabModel;
import com.loansystem.backend.model.PostponeRequestedLoansTabModel;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 *
 * @author Antonve
 */
public class PendingLoanControlsPanel extends javax.swing.JPanel {
    
    private JButton jButton1;
    private JButton jButton2;
    private JButton jButton3;
    private LoanTabModel loanTabModel;

    /** Creates new form PendingLoanControlsPanel */
    public PendingLoanControlsPanel() {
        initComponents();
    }

    public PendingLoanControlsPanel(PendingLoansTabModel pendingLoansTabModel, boolean visibility) {
        this.loanTabModel = loanTabModel;
        initComponents();
        setBorder(BorderFactory.createTitledBorder("Pending Loan Controls"));
        setButtonsVisibility(false);
        
       /* jButton2 = new javax.swing.JButton();
        jButton2.setText("Reject Loan Request");
        jButton2.setName("jButton2"); // NOI18N
        jButton2.setVisible(false);*/
        
        //initComponents(this.loanTabModel);
        setVisible(visibility);
    }
    
     public PendingLoanControlsPanel(PostponeRequestedLoansTabModel postponeRequestedLoansTabModel, boolean visibility) {
        this.loanTabModel = loanTabModel;
        initComponents();
        setBorder(BorderFactory.createTitledBorder("Pending Postpone Request Loan Controls"));
        setButtonsVisibility(false);
        
       /* jButton2 = new javax.swing.JButton();
        jButton2.setText("Reject Loan Request");
        jButton2.setName("jButton2"); // NOI18N
        jButton2.setVisible(false);*/
        
        //initComponents(this.loanTabModel);
        setVisible(visibility);
    }

    private void initComponents(LoanTabModel loanTabModel) {


        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        //jButton3 = new javax.swing.JButton();

        jButton1.setText("Accept Loan Request");
        jButton1.setName("jButton1"); // NOI18N
       



       /* jButton3.setText("Postpone payback");
        jButton3.setName("jButton3"); // NOI18N
        jButton3.setVisible(false);*/

        /*jButton4.setText("Cancel Postpone payback request");
        jButton4.setName("jButton4"); // NOI18N
        jButton4.setVisible(false);*/

        GridLayout layout = new GridLayout(2, 1);


        setLayout(layout);

        add(jButton1, 0);
        add(jButton2, 1);
        //add(jButton3, 2);
        //add(jButton4, 3);
        
       
        

       
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        AcceptRequestButton = new javax.swing.JButton();
        RejectRequestButton = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(584, 89));

        AcceptRequestButton.setText("Accept Loan Request");
        AcceptRequestButton.setName("AcceptRequestButton"); // NOI18N

        RejectRequestButton.setText("Reject Loan Request");
        RejectRequestButton.setName("RejectRequestButton"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AcceptRequestButton, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 222, Short.MAX_VALUE)
                .addComponent(RejectRequestButton, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AcceptRequestButton)
                    .addComponent(RejectRequestButton))
                .addContainerGap(32, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AcceptRequestButton;
    private javax.swing.JButton RejectRequestButton;
    // End of variables declaration//GEN-END:variables

    public JButton getAcceptRequestButton() {
        return AcceptRequestButton;
    }

    public void setAcceptRequestButton(JButton AcceptRequestButton) {
        this.AcceptRequestButton = AcceptRequestButton;
    }

    public JButton getRejectRequestButton() {
        return RejectRequestButton;
    }

    public void setRejectRequestButton(JButton RejectRequestButton) {
        this.RejectRequestButton = RejectRequestButton;
    }


    public void setButtonsVisibility(boolean b) {
        AcceptRequestButton.setVisible(b);
        RejectRequestButton.setVisible(b);
    }
}
