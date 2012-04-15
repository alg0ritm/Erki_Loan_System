/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MyLoansTab.java
 *
 * Created on Apr 14, 2012, 1:08:06 PM
 */
package com.loansystem.UI.client;

import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 *
 * @author Antonve
 */
public class MyLoansTab extends javax.swing.JPanel {

    /** Creates new form MyLoansTab */
    public MyLoansTab(ArrayList<JPanel> myLoansTabPanels) {
        initComponents();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS) );
        //JPanel testPanel = new NewLoanRequestPanel();
        //add(testPanel);
        for(JPanel panel : myLoansTabPanels) 
        {
            add(panel);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setName("My Loans"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
