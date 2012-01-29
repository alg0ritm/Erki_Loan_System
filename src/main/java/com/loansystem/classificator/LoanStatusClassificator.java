/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loansystem.classificator;

/**
 *
 * @author antonve
 */
public interface LoanStatusClassificator {

    public static final String PENDING = "Pending";
    public static final String REJECTED = "REJECTED";
    public static final String POSTPONE_REQUESTED = "POSTPONE_REQUESTED";
    public static final String OVERDUE = "OVERDUE";
    public static final String ISSUED = "ISSUED";
    public static final String PAYED_BACK = "PAYED_BACK";
    public static final String POSTPONED = "POSTPONED";
    public static final String SENT_TO_DEBT_COLLECTION = "SENT_TO_DEBT_COLLECTION";
}
