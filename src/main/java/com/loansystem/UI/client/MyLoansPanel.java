/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MyLoansPanel.java
 *
 * Created on Apr 8, 2012, 11:07:14 PM
 */
package com.loansystem.UI.client;

import com.loansystem.backend.model.MyLoansTabModel;
import com.loansystem.backend.model.PendingLoansTabModel;
import com.loansystem.backend.model.PostponeRequestedLoansTabModel;
import com.loansystem.classificator.LoanStatusClassificator;
import com.loansystem.classificator.MyLoansTabConstants;
import com.loansystem.util.JLabeledTextField;
import com.loansystem.classificator.NewLoanRequestConstants;
import com.loansystem.db.dao.LoanStatusHome;
import com.loansystem.hibernate.HibernateUtil;
import com.loansystem.model.Client;
import com.loansystem.model.Loan;
import com.loansystem.model.LoanHistory;
import com.loansystem.model.LoanOffer;
import com.loansystem.model.LoanStatus;
import com.loansystem.model.PostponeRequest;
import com.loansystem.service.LoanService;
import com.loansystem.service.LoanServiceImpl;
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
import javax.swing.BorderFactory;
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
public class MyLoansPanel extends javax.swing.JPanel {

    private static final Log log = LogFactory.getLog(NewLoanRequestPanel.class);
    private List<Loan> loansList;
    private Client client;
    private Session session;
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private JTable table;

    /** Creates new form MyLoansPanel */
    public MyLoansPanel(MyLoansTabModel myLoansTabModel, boolean visibility) {
        //initComponents();

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        
        initComponents();
        setBorder(BorderFactory.createTitledBorder("My previous loans"));
        this.client = myLoansTabModel.getClient();
        this.loansList = client.getLoans();
        //newLoanRequestTable.put(NewLoanRequestConstants.COL_1, Loan.class.getDeclaredField(TOOL_TIP_TEXT_KEY));
        createTable(loansList);
        this.setVisible(visibility);
        session.close();
    }

    public MyLoansPanel(PendingLoansTabModel pendingLoansTabModel, boolean visibility) {
        initComponents();
        setBorder(BorderFactory.createTitledBorder("Previous client loans"));
        this.loansList = pendingLoansTabModel.getPendingLoans();
        createTable(loansList);
        setVisible(visibility);
    }
    
     public MyLoansPanel(PostponeRequestedLoansTabModel  postponeRequestedLoansTabModel, boolean visibility) {
        initComponents();
        setBorder(BorderFactory.createTitledBorder("Previous client loans"));
        this.loansList = postponeRequestedLoansTabModel.getClientLoans();
        createTable(loansList);
        setVisible(visibility);
    }
    
   

    public void createTable(List<Loan> loansList) {
        Loan currentLoan;
        int i = 0;
        Object[][] tableObject = new Object[loansList.size()][6];

        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        Iterator loanOfferSet = loansList.iterator();
        while (loanOfferSet.hasNext()) {



            currentLoan = (Loan) loanOfferSet.next();
            String loanStatus = currentLoan.getLoanStatus().getDescription();

            tableObject[i][0] = currentLoan.getDebt();

            tableObject[i][1] = loanStatus;
            /*if (currentLoan.getLoanHistory().size() > 0) {
                //tableObject[i][2] = currentLoan.getLoanHistory().get(0).getDate();
            } else {*/
            tableObject[i][2] = currentLoan.getDueDate();;
            //}
            LoanService loanService = new LoanServiceImpl();
            ArrayList<LoanHistory> loanHistory  = loanService.getLoanHistory(currentLoan);
            tableObject[i][3] = loanHistory.get(0).getDate();
            tableObject[i][4] = currentLoan.getLoanOffer().getApr();

            i++;
        }
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                tableObject,
                new String[]{
                    MyLoansTabConstants.COL_SUM, MyLoansTabConstants.COL_STATUS, MyLoansTabConstants.COL_DUE_DATE, MyLoansTabConstants.COL_LAST_EVENT_DATE, "APR"
                }));


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

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5"
            }
        ));
        jTable1.setName("jTable1"); // NOI18N
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 536, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(189, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    public void fillClientLoansPanel(ArrayList<Loan> clientLoans) {
        createTable(clientLoans);
    }
}
