/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loansystem.enums;

/**
 *
 * @author antonve
 */
public interface LoanStatusInterface {

    public static final int PENDING = 1;
    public static final int REJECTED = 2;
    public static final int POSTPONE_REQUESTED = 3;
    public static final int OVERDUE = 4;
    public static final int ISSUED = 5;
    public static final int PAYED_BACK = 6;
    public static final int POSTPONED = 7;
    public static final int SENT_TO_DEBT_COLLECTION = 8;
}
