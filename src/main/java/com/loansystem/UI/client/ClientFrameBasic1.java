/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ClientFrameBasic.java
 *
 * Created on Nov 27, 2011, 4:14:22 PM
 */
package com.loansystem.UI.client;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author antonve
 */
public class ClientFrameBasic1 extends JFrame {
    
    private static final Log log = LogFactory.getLog(ClientFrameBasic1.class);

    public static JFrame basicFrame;
    JPanel[] panels;
    
    public static JPanel newLoanRequestPanel;
    public static JPanel exisitingLoanRequestPanel;
    public static JPanel myLoansTab;

    /** Creates new form ClientFrameBasic */
    public ClientFrameBasic1(JPanel[] panels) {
        //if no sent to debt collection show notification
        initComponents(panels);
    }
    
    
    public void initComponents(JPanel[] panels)
    {
        if(panels == null)
        {
            JOptionPane.showMessageDialog(this, "Sorry we doesn't allow login for clients with blacklisted status");
        }
        basicFrame = new JFrame("LOGGED USER FRAME");
        basicFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        log.info("Creating basic frame with tabs");
        
        this.panels = new JPanel[panels.length];
        this.panels = panels;
        
        jTabbedPane1 = new JTabbedPane();
        
        for(int i=0; i<panels.length; i++) {
           if(panels[i].getName().contains("MyLoans")) {
               myLoansTab = panels[i];
           }
           if(panels[i].getName().contains("Active")) {
               exisitingLoanRequestPanel = panels[i];
           }
           jTabbedPane1.add(panels[i]);
        }
        
        /*if(panels.length>1)
        {
            newLoanRequestPanel = panels[1];
            jTabbedPane1.add(newLoanRequestPanel);

            exisitingLoanRequestPanel = panels[0];
            jTabbedPane1.add(exisitingLoanRequestPanel);
        }
        else {
           exisitingLoanRequestPanel = panels[0];
            jTabbedPane1.add(exisitingLoanRequestPanel); 
        }*/
        
       
        
        basicFrame.add(jTabbedPane1);
        basicFrame.setSize(600, 600);
        basicFrame.setVisible(true);
        basicFrame.setResizable(true);
        log.info("Created basic frame with tabs");
    }
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setName("jTabbedPane1"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 607, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>                        

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientFrameBasic1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientFrameBasic1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientFrameBasic1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientFrameBasic1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {

                basicFrame.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify                     
    private  JTabbedPane jTabbedPane1;
    // End of variables declaration                   
}
