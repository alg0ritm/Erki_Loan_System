/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loansystem.backend.model;

import com.loansystem.UI.client.MyLoansPanel;
import com.loansystem.model.Client;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author antonve
 */
public class MyLoansTabModel {
    
    private MyLoansPanel myLoansPanel;

    public void setMyLoansPanel(MyLoansPanel myLoansPanel) {
        this.myLoansPanel =  myLoansPanel;
    }

    public MyLoansPanel getMyLoansPanel() {
        return myLoansPanel;
    }
    private Client client;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ArrayList<JPanel> getCreatedPanels() {
        ArrayList<JPanel> createdPanels = new ArrayList<JPanel>();
        if(myLoansPanel!=null) {
            createdPanels.add(myLoansPanel);
        }
        return createdPanels;
    }
    
}
