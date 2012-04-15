package com.loansystem.db.dao;

// default package
// Generated Nov 13, 2011 9:49:24 PM by Hibernate Tools 3.4.0.CR1
import com.loansystem.backend.model.LoanInsertRequest;
import com.loansystem.hibernate.HibernateUtil;
import com.loansystem.model.Client;
import com.loansystem.model.Loan;
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

    public void delete(Loan persistentInstance) {
        log.debug("deleting Loan instance");
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.delete(persistentInstance);
            log.debug("delete successful");
            transaction.commit();
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            transaction.rollback();
            throw re;
        }
    }

    public Loan merge(Loan detachedInstance) {
        log.debug("merging Loan instance");
        try {
            Loan result = (Loan) sessionFactory.getCurrentSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public Loan findById(java.lang.String id) {
        log.debug("getting Loan instance with id: " + id);
        try {
            Loan instance = (Loan) sessionFactory.getCurrentSession().get("Loan", id);
            if (instance == null) {
                log.debug("get successful, no instance found");
            } else {
                log.debug("get successful, instance found");
            }
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
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

    public int insertLoan(Loan loan) {

        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        //loginClient = (Client) session.createCriteria(Client.class).add(Restrictions.eq("mail", loginText)).add(Restrictions.eq("password", passwordText)).uniqueResult();
        try {
            //SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            //Session session = sessionFactory.getCurrentSession();
            //Transaction tx = session.beginTransaction();
            log.info("before save");
            session.save(loan);

            transaction.commit();
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
}
