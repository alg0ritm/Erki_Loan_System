package com.loansystem.db.dao;

// default package
// Generated Nov 13, 2011 9:49:24 PM by Hibernate Tools 3.4.0.CR1
import com.loansystem.backend.model.LoanInsertRequest;
import com.loansystem.classificator.PostponeRequestStatus;
import com.loansystem.enums.LoanStatusEnum;
import com.loansystem.hibernate.HibernateUtil;
import com.loansystem.model.Client;
import com.loansystem.model.Loan;
import com.loansystem.model.LoanStatus;
import com.loansystem.model.PostponeRequest;
import java.util.ArrayList;
import java.util.List;
import javax.naming.InitialContext;
import org.hibernate.Transaction;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * Home object for domain model class Loan.
 * @see .Loan
 * @author Hibernate Tools
 */
public class LoanHome {

    private static final Log log = LogFactory.getLog(LoanHome.class);
    private final SessionFactory sessionFactory = getSessionFactory();

    protected SessionFactory getSessionFactory() {
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            //session = sessionFactory.openSession();

            return sessionFactory;

            //return (SessionFactory) new InitialContext().lookup("SessionFactory");
        } catch (Exception e) {
            log.error("Could not locate SessionFactory in JNDI", e);
            throw new IllegalStateException("Could not locate SessionFactory in JNDI");
        }
    }

    public void persist(Loan transientInstance) {
        log.debug("persisting Loan instance");
        try {
            sessionFactory.getCurrentSession().persist(transientInstance);
            log.debug("persist successful");
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void attachDirty(Loan instance) {
        log.debug("attaching dirty Loan instance");
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(Loan instance) {
        log.debug("attaching clean Loan instance");
        try {
            sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void delete(Loan persistentInstance, Session session) {
        Session sessionLoc = createRequieredSession(session);
        log.debug("deleting Loan instance");
        try {
            sessionLoc.delete(persistentInstance);
            if(session==null) {
                log.debug("delete successful");
                sessionLoc.getTransaction().commit();
            }
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            sessionLoc.getTransaction().rollback();
            throw re;
        }
    }

    public Loan merge(Loan detachedInstance, Session session) {

        Session sessionLoc = createRequieredSession(session);
        log.debug("merging Loan instance");
        try {
            Loan result = (Loan) sessionLoc.merge(detachedInstance);
            log.debug("merge successful");
            if (session == null) {
                sessionLoc.getTransaction().commit();
            }
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public Loan findById(int id, Session session) {
        log.debug("getting Loan instance with id: " + id);
        Session sessionLoc = null;
        if (session == null) {
            sessionLoc = sessionFactory.getCurrentSession();
            Transaction transaction = sessionLoc.beginTransaction();
        } else {
            sessionLoc = session;
        }
        try {
            Loan instance = (Loan) sessionLoc.get("com.loansystem.model.Loan", id);
            if (instance == null) {
                log.debug("get successful, no instance found");
            } else {
                log.debug("get successful, instance found");
            }
            return instance;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }

    }

    public List findByExample(Loan instance) {
        log.debug("finding Loan instance by example");
        try {
            List results = sessionFactory.getCurrentSession().createCriteria("Loan").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public int insertLoan(Loan loan, Session session) {

        Session sessionLoc = createRequieredSession(session);
        try {
            log.info("before save");
            sessionLoc.save(loan);
            if (session == null) {
                sessionLoc.getTransaction().commit();
            }
            //session.close();
        } catch (Exception ex) {
            log.error(ex);
            return 0;
            //session.close();
        }

        return 1;

    }

    /*public Loan findLastLoanForClient(Client client) {
    log.debug("getting last Loan instance for Client  " + client.getLastName());
    Session session = null;
    try {
    session = sessionFactory.getCurrentSession();
    session.beginTransaction();
    Loan instance = (Loan) session.createCriteria("com.loansystem.model.Loan")
    .add(Restrictions.eq("client", client))
    .addOrder(Order.desc("loanId")).setMaxResults(1).uniqueResult();
    if (instance == null) {
    log.debug("get successful, no instance found");
    } else {
    log.debug("get successful, instance found");
    }
    return instance;
    } catch (RuntimeException re) {
    log.error("get failed", re);
    throw re;
    } finally {
    session.close();
    }
    }*/
    public ArrayList<Loan> getLoansByStatus(LoanStatus loanStatus, Session session) {
        log.debug("merging Loan instance");
        Session sessionLoc = null;
        if (session == null) {
            sessionLoc = sessionFactory.getCurrentSession();
            Transaction transaction = sessionLoc.beginTransaction();
        } else {
            sessionLoc = session;
        }
        try {
            ArrayList<Loan> results = new ArrayList<Loan>();
            // = sessionLoc.createCriteria("Loan").add(Example.create(instance)).list();
            results = (ArrayList<Loan>) sessionLoc.createCriteria(Loan.class).add(Restrictions.eq("loanStatus", loanStatus)).list();
            log.debug("merge successful");
            return results;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public ArrayList<Loan> getPostponedLoans(String postponeRequestStatus, Session session) {
        log.debug("returning Loans with postone requests");
        Session sessionLoc = null;
        if (session == null) {
            sessionLoc = sessionFactory.getCurrentSession();
            Transaction transaction = sessionLoc.beginTransaction();
        } else {
            sessionLoc = session;
        }
        try {
            ArrayList<Loan> results = new ArrayList<Loan>();
            results = (ArrayList<Loan>) sessionLoc.createCriteria(Loan.class).createCriteria("postponeRequest", "pr").createCriteria("postponeRequestStatus", "prs").add(Restrictions.eq("id", postponeRequestStatus)).list();
            // = sessionLoc.createCriteria("Loan").add(Example.create(instance)).list();
             /*results = (ArrayList<Loan>) sessionLoc.createCriteria(Loan.class).add(Restrictions.eq("postponeRequest", postponeRequest)).list();*/
            log.debug("merge successful");
            return results;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    private Session createRequieredSession(Session session) {
        Session sessionLoc = null;
        if (session == null) {
            sessionLoc = sessionFactory.getCurrentSession();
            Transaction transaction = sessionLoc.beginTransaction();
        } else {
            sessionLoc = session;
        }
        return sessionLoc;
    }
}
