/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NewLoanRequestPanel.java
 *
 * Created on Dec 11, 2011, 4:42:28 PM
 */
package com.loansystem.UI.client;

import com.loansystem.classificator.LoanStatusClassificator;
import com.loansystem.util.JLabeledTextField;
import com.loansystem.classificator.NewLoanRequestConstants;
import com.loansystem.control.FrameBuilderImpl.LoanStatusEnum;
import com.loansystem.db.dao.LoanStatusHome;
import com.loansystem.hibernate.HibernateUtil;
import com.loansystem.model.Client;
import com.loansystem.model.Loan;
import com.loansystem.model.LoanOffer;
import com.loansystem.model.LoanStatus;
import com.loansystem.util.DateUtil;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author antonve
 */
public class NewLoanRequestPanel extends javax.swing.JPanel {

    private static final Log log = LogFactory.getLog(NewLoanRequestPanel.class);
    private List<LoanOffer> loanOffersList;
    private final Client client;
    private Session session;
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    /** Creates new form NewLoanRequestPanel */
    public NewLoanRequestPanel(Client client, boolean visibility) {
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        initComponents();
        this.client = client;
        this.loanOffersList = client.getCientGroup().getLoanOffers();
        Map<String, String> newLoanRequestTable = new HashMap<String, String>();
        //newLoanRequestTable.put(NewLoanRequestConstants.COL_1, Loan.class.getDeclaredField(TOOL_TIP_TEXT_KEY));
        LoanOffer currentLoanOffer;
        int i = 0;
        Object[][] tableObject = new Object[loanOffersList.size()][4];

        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        Iterator loanOfferSet = loanOffersList.iterator();
        while (loanOfferSet.hasNext()) {

            currentLoanOffer = (LoanOffer) loanOfferSet.next();
            tableObject[i][0] = currentLoanOffer.getSum();
            tableObject[i][1] = currentLoanOffer.getPeriod();
            tableObject[i][2] = currentLoanOffer.getRatingBonus();
            tableObject[i][3] = currentLoanOffer.getApr();

            i++;
        }
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                tableObject,
                new String[]{
                    NewLoanRequestConstants.COL_1, NewLoanRequestConstants.COL_2, NewLoanRequestConstants.COL_3, NewLoanRequestConstants.COL_4
                }));
        this.setVisible(visibility);
        session.close();

    }

    public NewLoanRequestPanel() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public String displayRowValues(int rowIndex) {
        int columns = jTable1.getColumnCount();
        String s = "Are you sure your choice is the loan with next parameters: \n";
        for (int col = 0; col < columns; col++) {
            String columnName = jTable1.getColumnName(col);
            Object o = jTable1.getValueAt(rowIndex, col);



            /*switch(colName)
            {
            case: NewLoanRequestConstants
            }*/
            s += columnName + " : " + o.toString() + "\n";
            log.info(s);

        }

        return s;


        /* JOptionPane.showOptionDialog();
        DisableListener.addEnabler(dialogTypeButtons[3], buttonLabelsField); */

        //loan.set
    }
    
    public LoanOffer getSelectedLoanOffer(int rowIndex) {
         int columns = jTable1.getColumnCount();
        // String s = "Are you sure your choice is the loan with next parameters: \n";
        LoanOffer selectedLoanOffer = new LoanOffer();
        String[] loanOfferParams = new String[4];
        selectedLoanOffer.setSum((String)jTable1.getValueAt(rowIndex, 0));
        selectedLoanOffer.setPeriod((String)jTable1.getValueAt(rowIndex, 1));
        selectedLoanOffer.setRatingBonus((String)jTable1.getValueAt(rowIndex, 2));
        selectedLoanOffer.setApr((String)jTable1.getValueAt(rowIndex, 3));
        
        log.info("SELECTED LOAN OFFER " + selectedLoanOffer.getSum() + " " + selectedLoanOffer.getPeriod());


        return selectedLoanOffer;
    }

    public JButton getjButton1() {
        return jButton1;
    }

    public void setjButton1(JButton jButton1) {
        this.jButton1 = jButton1;
    }

    public JLabel getjLabel1() {
        return jLabel1;
    }

    public void setjLabel1(JLabel jLabel1) {
        this.jLabel1 = jLabel1;
    }

    public JScrollPane getjScrollPane1() {
        return jScrollPane1;
    }

    public void setjScrollPane1(JScrollPane jScrollPane1) {
        this.jScrollPane1 = jScrollPane1;
    }

    public JTable getjTable1() {
        return jTable1;
    }

    public void setjTable1(JTable jTable1) {
        this.jTable1 = jTable1;
    }

    public List<LoanOffer> getLoanOffersList() {
        return loanOffersList;
    }

    public void setLoanOffersList(List<LoanOffer> loanOffersList) {
        this.loanOffersList = loanOffersList;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void addLoginListener(ActionListener mal) {
        jButton1.addActionListener(mal);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setName("jTable1"); // NOI18N
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setText("Available Loan Offers");
        jLabel1.setName("jLabel1"); // NOI18N

        jButton1.setText("Request a loan");
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(127, 127, 127)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(517, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(19, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

    }//GEN-LAST:event_jButton1ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
