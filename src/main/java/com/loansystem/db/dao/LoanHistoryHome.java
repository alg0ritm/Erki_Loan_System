package com.loansystem.db.dao;

// default package
// Generated Nov 13, 2011 9:49:24 PM by Hibernate Tools 3.4.0.CR1
import com.loansystem.hibernate.HibernateUtil;
import com.loansystem.model.Loan;
import com.loansystem.model.LoanHistory;
import java.util.ArrayList;
import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * Home object for domain model class LoanHistory.
 * @see .LoanHistory
 * @author Hibernate Tools
 */
public class LoanHistoryHome {

    private static final Log log = LogFactory.getLog(LoanHistoryHome.class);
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

    public void persist(LoanHistory transientInstance) {
        log.debug("persisting LoanHistory instance");
        try {
            sessionFactory.getCurrentSession().persist(transientInstance);
            log.debug("persist successful");
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void attachDirty(LoanHistory instance) {
        log.debug("attaching dirty LoanHistory instance");
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(LoanHistory instance) {
        log.debug("attaching clean LoanHistory instance");
        try {
            sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

     public void delete(LoanHistory persistentInstance, Session session) {
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

    public LoanHistory merge(LoanHistory detachedInstance) {
        log.debug("merging LoanHistory instance");
        try {
            LoanHistory result = (LoanHistory) sessionFactory.getCurrentSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public LoanHistory findById(int loanHistoryId, Session session) {
        Session sessionLoc = createRequieredSession(session);
        log.debug("getting LoanHistory instance with id: " + loanHistoryId);
        try {
            LoanHistory instance = (LoanHistory) sessionLoc.get("com.loansystem.model.LoanHistory", loanHistoryId);
            if (instance == null) {
                log.debug("get successful, no instance found");
            } else {
                log.debug("get successful, instance found");
            }
            if(session==null)
                sessionLoc.getTransaction().commit();
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(LoanHistory instance) {
        log.debug("finding LoanHistory instance by example");
        try {
            List results = sessionFactory.getCurrentSession().createCriteria("LoanHistory").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public void save(LoanHistory instance, Session session) {
        log.debug("saveing LoanHistory instance");

        Session sessionLoc = null;
        Transaction transaction = null;

        if (session == null) {
            sessionLoc = sessionFactory.getCurrentSession();
            transaction = sessionLoc.beginTransaction();
        } else {
            sessionLoc = session;
        }
        try {
            log.info("before save");
            session.save(instance);
            if (session == null) {
                transaction.commit();
            }

        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public int insertLoanHistory(LoanHistory loanHistory, Session session) {
        Session sessionLoc = createRequieredSession(session);
        try {
            log.info("before save");
            sessionLoc.save(loanHistory);
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

    public ArrayList<LoanHistory> findByLoanId(Loan loan, Session session) {
        Session sessionLoc = createRequieredSession(session);
        ArrayList<LoanHistory> loanHistoryList = new ArrayList<LoanHistory>();
        log.debug("getting LoanHistory instance with id: " + loan);
        try {
            loanHistoryList.addAll(sessionLoc.createCriteria(LoanHistory.class).add(Restrictions.eq("loan", loan)).list());
            /*LoanHistory instance = (LoanHistory) sessionLoc.get("com.loansystem.model.LoanHistory", loanId);*/
            if (loanHistoryList.size() == 0) {
                log.debug("get successful, no instance found");
            } else {
                log.debug("get successful, instance found");
            }
            
            if(session==null)
                sessionLoc.getTransaction().commit();
            return loanHistoryList;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
}
