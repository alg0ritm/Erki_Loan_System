/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loansystem.backend.model;

/**
 *
 * @author antonve
 */
public class UserLoginInput {

    private String login;
    private String password;
    
    public UserLoginInput(String login, String password) {
        this.login = login;
        this.password = password;
    }
    
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public UserLoginInput getLoginInfo()
    {
        return this;
    }
}
