package com.loansystem.UI;

import com.loansystem.backend.model.UserLoginInput;
import com.loansystem.control.FrameBuilder;
import com.loansystem.control.FrameBuilderImpl;
import com.loansystem.hibernate.HibernateUtil;
import com.loansystem.model.*;
import com.loansystem.validator.LoginValidator;
import com.loansystem.control.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import javax.swing.*;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * LoginForm.java
 *
 * Created on Nov 12, 2011, 3:36:11 PM
 */
/**
 *
 * @author antonve
 */
public class LoginForm extends javax.swing.JFrame implements Observable {

    private static final Log log = LogFactory.getLog(LoginForm.class);
    private LoanSystemObserver observer;
    public HibernateUtil hu;
    private UserLoginInput userLoginInput;

    public UserLoginInput getUserLoginInput() {
        return userLoginInput;
    }

    public void setUserLoginInput(UserLoginInput userLoginInput) {
        this.userLoginInput = userLoginInput;
    }

    /** Creates new form LoginForm */
    public LoginForm(LoanSystemObserver observer) {
        initComponents();
        this.observer = observer;
        
        setResizable(false);
       

    }

    public JTextField getjTextField1() {
        return jTextField1;
    }

    public void setjTextField1(JTextField jTextField1) {
        this.jTextField1 = jTextField1;
    }

    /*public void actionPerformed(ActionEvent evt) {
       
    }*/

    /*public String[] getUserInput()
    {
    return 
    }*/
    /*class LoginCredentialsListener implements ActionListener {
    
    public void actionPerformed(ActionEvent evt) {
    observer.notifyMsg("event has been held");
    }
    }//end inner class MultiplyListener
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextField1.setName("jTextField1"); // NOI18N
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton1.setText("OK");
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Login");
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText("Password");
        jLabel2.setName("jLabel2"); // NOI18N

        jPasswordField1.setName("jPasswordField1"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(107, 107, 107)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField1)
                    .addComponent(jPasswordField1))
                .addContainerGap(192, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(117, 117, 117)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap(137, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_jTextField1ActionPerformed

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

    //not used
}//GEN-LAST:event_jButton1ActionPerformed
    /**
     * @param args the command line arguments
     */
    //public static void main(String args[]) {
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
    /*java.awt.EventQueue.invokeLater(new Runnable() {
    
    public void run() {
    /*SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    Session session;
    session = sessionFactory.openSession();
    session.beginTransaction();*/
    /* HibernateUtil hu = new HibernateUtil();
    new LoginForm().setVisible(true);
    }
    });
    }*/
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

    public JPasswordField getjPasswordField1() {
        return jPasswordField1;
    }

    public void setjPasswordField1(JPasswordField jPasswordField1) {
        this.jPasswordField1 = jPasswordField1;
    }

    @Override
    public void observerActionPerformed() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateComponent() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteComponent() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void addLoginListener(ActionListener mal) {
        jButton1.addActionListener(mal);
    }
}
