/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loansystem.control;

import javax.swing.JPanel;

/**
 *
 * @author antonve
 */
public interface FrameBuilder {
    
    
    public JPanel addNewLoanRequestTab();
    public JPanel addLoanRequestTab();
    public JPanel addLoanPaybackTab();
    public JPanel addLoanPostponeRequestTab();
    public JPanel addMyLoansTab();
    
}
