/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loansystem.service;

import com.loansystem.backend.model.UserLoginInput;
import com.loansystem.db.dao.EmployeeHome;
import com.loansystem.model.Employee;

/**
 *
 * @author antonve
 */
public class LoginServiceImpl implements LoginService {
    public Employee findEmplyeeByNamePassword(UserLoginInput userLoginInput)
    {
        EmployeeHome employeeHome = new EmployeeHome();
        Employee employee = new Employee();
        employee = employeeHome.findByNamePassword(userLoginInput);
      return null;  
    }
}
