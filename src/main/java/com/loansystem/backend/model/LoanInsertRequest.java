/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loansystem.backend.model;

import com.loansystem.model.Client;
import com.loansystem.model.Loan;
import com.loansystem.model.LoanOffer;
import com.loansystem.model.LoanStatus;
import com.loansystem.service.LoanServiceImpl;
import com.loansystem.util.DateUtil;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author antonve
 */
public class LoanInsertRequest {

    private final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private static final Log log = LogFactory.getLog(LoanInsertRequest.class);
    private Client client;
    private LoanOffer loanOffer;
    private String apr;
    private String dueDate;
    private String baseDueDate;
    private String debt;
    private LoanStatus loanStatus;

    public LoanInsertRequest(Client client,  LoanOffer loanOffer,  LoanStatus loanStatus) {
        log.info("LoanInsertRequest " + loanStatus);

        Date now = new Date();

        Date insertDate = DateUtil.getDatePlusDays(now, Integer.parseInt(loanOffer.getPeriod()));


        //get current date time with Date()


        //setDateFormat(dateFormat);
        this.client=client;
        this.loanOffer=loanOffer;
        this.apr=loanOffer.getApr();
        this.dueDate = dateFormat.format(insertDate);
        this.baseDueDate = dateFormat.format(insertDate);
        this.debt = loanOffer.getSum();
        this.loanStatus = loanStatus;

    }

    /* insertLoan.setClient(client);
    insertLoan.setLoanOffer(loanOffer);
    insertLoan.setApr(loanOffer.getApr());
    insertLoan.setDueDate(dateFormat.format(insertDate));
    insertLoan.setBaseDueDate(dateFormat.format(insertDate));
    insertLoan.setDebt(loanOffer.getSum());
    insertLoan.setLoanStatus(loanStatus);*/
    public String getApr() {
        return apr;
    }

    public void setApr(String apr) {
        this.apr = apr;
    }

    public String getBaseDueDate() {
        return baseDueDate;
    }

    public void setBaseDueDate(String baseDueDate) {
        this.baseDueDate = baseDueDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getDebt() {
        return debt;
    }

    public void setDebt(String debt) {
        this.debt = debt;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public LoanOffer getLoanOffer() {
        return loanOffer;
    }

    public void setLoanOffer(LoanOffer loanOffer) {
        this.loanOffer = loanOffer;
    }

    public LoanStatus getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(LoanStatus loanStatus) {
        this.loanStatus = loanStatus;
    }
}
