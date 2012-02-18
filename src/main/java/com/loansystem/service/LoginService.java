/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loansystem.service;

import com.loansystem.backend.model.UserLoginInput;
import com.loansystem.model.Employee;

/**
 *
 * @author antonve
 */
public interface LoginService {
    public Employee findEmplyeeByNamePassword(UserLoginInput userLoginInput);
    
}
