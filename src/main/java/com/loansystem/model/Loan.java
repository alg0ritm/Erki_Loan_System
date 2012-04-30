package com.loansystem.model;

// default package
import com.loansystem.util.DateUtil;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Generated Nov 13, 2011 9:49:21 PM by Hibernate Tools 3.4.0.CR1
/**
 * Loan generated by hbm2java
 */
public class Loan implements java.io.Serializable {

    private int loanId;
    private String dueDate;
    private String baseDueDate;
    private String apr;
    private String debt;
    private Client client;
    private LoanStatus loanStatus;
    private String employeeId;
    private PostponeRequest postponeRequest;
    private LoanOffer loanOffer;
    private List<LoanHistory> loanHistory;
    private final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    

    public Loan() {
    }

    public Loan(int id, String dueDate, String baseDueDate, String apr, String debt, int clientId, LoanStatus loanStatus, LoanOffer loanOffer, List<LoanHistory> loanHistory, PostponeRequest postponeRequest) {
        this.loanId = id;
        this.dueDate = dueDate;
        this.baseDueDate = baseDueDate;
        this.apr = apr;
        this.debt = debt;
        this.client = client;
        this.loanStatus = loanStatus;
        this.loanOffer = loanOffer;
        this.loanHistory = loanHistory;
        this.postponeRequest = postponeRequest;
    }

    public Loan(int id, String dueDate, String baseDueDate, String apr, String debt, int clientId,
            String employeeId, LoanStatus loanStatus, LoanOffer loanOffer, PostponeRequest postponeRequest, List<LoanHistory> loanHistory) {
        this.loanId = id;
        this.dueDate = dueDate;
        this.baseDueDate = baseDueDate;
        this.apr = apr;
        this.debt = debt;
        this.client = client;
        this.employeeId = employeeId;
        this.loanStatus = loanStatus;
        this.loanOffer = loanOffer;
        this.postponeRequest = postponeRequest;
        this.loanHistory = loanHistory;
    }

    //custom constructor
    public Loan(Client client, LoanOffer loanOffer, LoanStatus loanStatus) {
        //log.info("LoanInsertRequest " + loanStatus);

        Date now = new Date();

        Date insertDate = DateUtil.getDatePlusDays(now, Integer.parseInt(loanOffer.getPeriod()));
        

        //get current date time with Date()
        float initialSum = Float.parseFloat(loanOffer.getSum());
        float apr = Float.parseFloat(loanOffer.getApr());
        int days = Integer.parseInt(loanOffer.getPeriod());
       

        //Loan Offer.Sum * (1 + Loan Offer.APR* Loan Offer.periodDays/(100 * 365)
        float newSum = initialSum * (1 + apr * days / (100 * 365));

        //setDateFormat(dateFormat);
        this.client = client;
        this.loanOffer = loanOffer;
        this.apr = loanOffer.getApr();
        this.dueDate = dateFormat.format(insertDate);
        this.baseDueDate = dateFormat.format(insertDate);
        this.debt = newSum+"";
        this.loanStatus = loanStatus;

    }

    public List<LoanHistory> getLoanHistory() {
        return loanHistory;
    }

    public void setLoanHistory(List<LoanHistory> loanHistory) {
        this.loanHistory = loanHistory;
    }


    public LoanOffer getLoanOffer() {
        return loanOffer;
    }

    public void setLoanOffer(LoanOffer loanOffer) {
        this.loanOffer = loanOffer;
    }

    public int getLoanId() {
        return this.loanId;
    }

    public void setLoanId(int id) {
        this.loanId = id;
    }

    public String getDueDate() {
        return this.dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getBaseDueDate() {
        return this.baseDueDate;
    }

    public void setBaseDueDate(String baseDueDate) {
        this.baseDueDate = baseDueDate;
    }

    public String getApr() {
        return this.apr;
    }

    public void setApr(String apr) {
        this.apr = apr;
    }

    public String getDebt() {
        return this.debt;
    }

    public void setDebt(String debt) {
        this.debt = debt;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getEmployeeId() {
        return this.employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public LoanStatus getLoanStatus() {
        return this.loanStatus;
    }

    public void setLoanStatus(LoanStatus loanStatus) {
        this.loanStatus = loanStatus;
    }

    public PostponeRequest getPostponeRequest() {
        return this.postponeRequest;
    }

    public void setPostponeRequest(PostponeRequest postponeRequest) {
        this.postponeRequest = postponeRequest;
    }
}
