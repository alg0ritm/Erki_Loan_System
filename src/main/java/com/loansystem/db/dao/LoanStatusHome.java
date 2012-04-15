package com.loansystem.db.dao;

// default package
// Generated Nov 13, 2011 9:49:24 PM by Hibernate Tools 3.4.0.CR1
import com.loansystem.hibernate.HibernateUtil;
import com.loansystem.model.LoanStatus;
import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;

/**
 * Home object for domain model class LoanStatus.
 * @see .LoanStatus
 * @author Hibernate Tools
 */
public class LoanStatusHome {

    private static final Log log = LogFactory.getLog(LoanStatusHome.class);
    private final SessionFactory sessionFactory = getSessionFactory();
    private Session session;

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

    public void persist(LoanStatus transientInstance) {
        log.debug("persisting LoanStatus instance");
        try {
            sessionFactory.getCurrentSession().persist(transientInstance);
            log.debug("persist successful");
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void attachDirty(LoanStatus instance) {
        log.debug("attaching dirty LoanStatus instance");
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(LoanStatus instance) {
        log.debug("attaching clean LoanStatus instance");
        try {
            sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void delete(LoanStatus persistentInstance) {
        log.debug("deleting LoanStatus instance");
        try {
            sessionFactory.getCurrentSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public LoanStatus merge(LoanStatus detachedInstance) {
        log.debug("merging LoanStatus instance");
        try {
            LoanStatus result = (LoanStatus) sessionFactory.getCurrentSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public LoanStatus findById(java.lang.String id) {
        log.debug("getting LoanStatus instance with id: " + id);
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            LoanStatus instance = (LoanStatus) session.get("com.loansystem.model.LoanStatus", id);
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

    public LoanStatus findByName(java.lang.String name) {
        log.debug("getting LoanStatus instance with Name: " + name);
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            LoanStatus instance = (LoanStatus) session.createCriteria("com.loansystem.model.LoanStatus").add(Restrictions.eq("name", name)).uniqueResult();
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
    }

    public List findByExample(LoanStatus instance) {
        log.debug("finding LoanStatus instance by example");
        try {
            List results = sessionFactory.getCurrentSession().createCriteria("LoanStatus").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }
}
