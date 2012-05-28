/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PendingLoanDetailedViewPanel.java
 *
 * Created on Apr 25, 2012, 4:30:44 PM
 */
package com.loansystem.UI.employee;

import com.loansystem.backend.model.PendingLoansTabModel;
import com.loansystem.backend.model.PostponeRequestedLoansTabModel;
import com.loansystem.model.Loan;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 *
 * @author Antonve
 */
public class PendingLoanDetailedViewPanel extends javax.swing.JPanel {

    /** Creates new form PendingLoanDetailedViewPanel */
    public PendingLoanDetailedViewPanel() {
        
    }

    public JLabel getClientGroup() {
        return clientGroup;
    }

    public void setClientGroup(JLabel clientGroup) {
        this.clientGroup = clientGroup;
    }

    public JLabel getClientName() {
        return clientName;
    }

    public void setClientName(JLabel clientName) {
        this.clientName = clientName;
    }

    public JLabel getClientRating() {
        return clientRating;
    }

    public void setClientRating(JLabel clientRating) {
        this.clientRating = clientRating;
    }

    /*public JLabel getDateOfRequest() {
        return dateOfRequest;
    }

    public void setDateOfRequest(JLabel dateOfRequest) {
        this.dateOfRequest = dateOfRequest;
    }*/

    public JLabel getLoanId() {
        return loanId;
    }

    public void setLoanId(JLabel loanId) {
        this.loanId = loanId;
    }

    public JLabel getLoanSum() {
        return loanSum;
    }
    
    public JLabel getjLabel7() {
        return jLabel7;
    }

    public void setjLabel7(JLabel jLabel7) {
        this.jLabel7 = jLabel7;
    }

    public JLabel getjLabel8() {
        return jLabel8;
    }

    public void setjLabel8(JLabel jLabel8) {
        this.jLabel8 = jLabel8;
    }

    public void setLoanSum(JLabel loanSum) {
        this.loanSum = loanSum;
    }

    public PendingLoanDetailedViewPanel(PendingLoansTabModel pendingLoansTabModel, boolean visiblity) {
        initComponents();
        setBorder(BorderFactory.createTitledBorder("Pending Loan Request Details"));
        setVisible(visiblity);
        
    }
    
     public PendingLoanDetailedViewPanel(PostponeRequestedLoansTabModel postponeRequestedLoansTabModel, boolean visiblity) {
        initComponents();
        setBorder(BorderFactory.createTitledBorder("Pending Postpone Request Details"));
        setVisible(visiblity);
     }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel11 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        loanId = new javax.swing.JLabel();
        loanSum = new javax.swing.JLabel();
        clientRating = new javax.swing.JLabel();
        clientGroup = new javax.swing.JLabel();
        clientName = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        jLabel11.setText("jLabel11");
        jLabel11.setName("jLabel11"); // NOI18N

        setMaximumSize(new java.awt.Dimension(800, 150));
        setPreferredSize(new java.awt.Dimension(800, 136));

        jLabel1.setText("Loan Id");
        jLabel1.setName("loanIdLabel"); // NOI18N

        jLabel3.setText("Loan Sum");
        jLabel3.setName("loanSumLabel"); // NOI18N

        jLabel4.setText("Client Rating");
        jLabel4.setName("clientRatingLabel"); // NOI18N

        jLabel5.setText("Client Group");
        jLabel5.setName("clientGroupLabel"); // NOI18N

        jLabel6.setText("Client Name");
        jLabel6.setName("clientNameLabel"); // NOI18N

        loanId.setName("loanId"); // NOI18N

        loanSum.setName("loanSum"); // NOI18N

        clientRating.setName("clientRating"); // NOI18N

        clientGroup.setName("clientGroup"); // NOI18N

        clientName.setName("clientName"); // NOI18N

        jLabel7.setText("Days to postpone");
        jLabel7.setName("jLabel7"); // NOI18N

        jLabel8.setName("jLabel8"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(49, 49, 49)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(clientName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(clientGroup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(loanSum, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(loanId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(clientRating, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(29, 29, 29)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(603, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(loanId, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(loanSum, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clientRating, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clientGroup, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(clientName, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel clientGroup;
    private javax.swing.JLabel clientName;
    private javax.swing.JLabel clientRating;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel loanId;
    private javax.swing.JLabel loanSum;
    // End of variables declaration//GEN-END:variables

    public void fillPendingLoanDetailedViewPanel(Loan selectedLoan) {
        clientGroup.setText(selectedLoan.getClient().getClientGroup().getDescription()+"");
        clientName.setText(selectedLoan.getClient().getUser().getName()+ " " +selectedLoan.getClient().getUser().getLastName());
        clientRating.setText(selectedLoan.getClient().getRating());
        loanId.setText(selectedLoan.getLoanId()+"");
        loanSum.setText(selectedLoan.getDebt());
        if(selectedLoan.getPostponeRequest()==null) {
            jLabel7.setVisible(false);
            jLabel8.setVisible(false);
        }
        else {
            jLabel7.setVisible(true);
            jLabel8.setText(selectedLoan.getPostponeRequest().getPeriodDays()+"");
            jLabel8.setVisible(true);
            
        }
    }
}
