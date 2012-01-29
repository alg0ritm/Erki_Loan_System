/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loansystem.view;

import com.loansystem.UI.LoginForm;
import com.loansystem.control.LoanSystemObserver;
import com.loansystem.control.Observable;
import java.awt.event.ActionListener;

/**
 *
 * @author antonve
 */
public class LoanSystemView implements Observable {

    private LoginForm loginForm;
    private LoanSystemObserver observer;
    
    public LoanSystemView(LoanSystemObserver observer) {
        this.observer = observer;
        loginForm = new LoginForm(this.observer);
        loginForm.setVisible(true);
    }

    public LoginForm getLoginView() {
        return loginForm;
    }
    
    

    void showError(String string) {
        throw new UnsupportedOperationException("Not yet implemented");
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
        loginForm.addLoginListener(mal);
    }
}
