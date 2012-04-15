package com.loansystem.db.dao;

// default package
// Generated Feb 18, 2012 4:26:53 PM by Hibernate Tools 3.4.0.CR1
import com.loansystem.hibernate.HibernateUtil;
import com.loansystem.model.User;
import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;

/**
 * Home object for domain model class User.
 * @see .User
 * @author Hibernate Tools
 */
public class UserHome {

    private static final Log log = LogFactory.getLog(UserHome.class);
    private final SessionFactory sessionFactory = getSessionFactory();
    private Session session;

   protected SessionFactory getSessionFactory() {
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            return sessionFactory;
        } catch (Exception e) {
            log.error("Could not locate SessionFactory in JNDI", e);
            throw new IllegalStateException("Could not locate SessionFactory in JNDI");
        }
    }

    public void persist(User transientInstance) {
        log.debug("persisting User instance");
        try {
            sessionFactory.getCurrentSession().persist(transientInstance);
            log.debug("persist successful");
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void attachDirty(User instance) {
        log.debug("attaching dirty User instance");
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(User instance) {
        log.debug("attaching clean User instance");
        try {
            sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void delete(User persistentInstance) {
        log.debug("deleting User instance");
        try {
            sessionFactory.getCurrentSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public User merge(User detachedInstance) {
        log.debug("merging User instance");
        try {
            User result = (User) sessionFactory.getCurrentSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public User findById(int id) {
        log.debug("getting User instance with id: " + id);
        try {
            User instance = (User) sessionFactory.getCurrentSession().get("User", id);
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

    public List findByExample(User instance) {
        log.debug("finding User instance by example");
        try {
            List results = sessionFactory.getCurrentSession().createCriteria("User").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public User findByMailPassword(String loginText, String passwordText) {
        log.debug("finding Client instance by login/password");
        User loginUser = null;
        try {
            session = sessionFactory.getCurrentSession();
            Transaction transaction = session.beginTransaction();
            loginUser = (User) session.createCriteria(User.class).add(Restrictions.eq("mail", loginText)).add(Restrictions.eq("password", passwordText)).uniqueResult();

        } catch (RuntimeException re) {
            log.error("findByMailPassword failed", re);
            throw re;
        } finally {
            session.close();
        }
        return loginUser;
    }
}
