package com.loansystem.db.dao;

// default package
// Generated Nov 13, 2011 9:49:24 PM by Hibernate Tools 3.4.0.CR1
import com.loansystem.hibernate.HibernateUtil;
import com.loansystem.model.Client;
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
 * Home object for domain model class Client.
 * @see .Client
 * @author Hibernate Tools
 */
public class ClientHome {

    private static final Log log = LogFactory.getLog(ClientHome.class);
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
    
    public ClientHome() {
        //session = sessionFactory.getCurrentSession();
    }

    public void persist(Client transientInstance, Session session) {
        Session sessionLoc = HibernateUtil.createRequieredSession(session);
        log.debug("persisting Client instance");
        try {
            sessionLoc.persist(transientInstance);
            log.debug("persist successful");
            if(session==null) {
                session.getTransaction().commit();
            }
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void attachDirty(Client instance) {
        log.debug("attaching dirty Client instance");
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(Client instance) {
        log.debug("attaching clean Client instance");
        try {
            sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    

    public void delete(Client persistentInstance) {
        log.debug("deleting Client instance");
        try {
            sessionFactory.getCurrentSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public Client merge(Client detachedInstance, Session session) {
        Session sessionLoc = HibernateUtil.createRequieredSession(session);
        log.debug("merging Client instance");
        try {
            Client result = (Client) sessionLoc.merge(detachedInstance);
            log.debug("merge successful");
            if(session == null) {
                sessionLoc.getTransaction().commit();
            }
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public Client findById(int id) {
        log.debug("getting Client instance with id: " + id);
        try {
            Client instance = (Client) sessionFactory.getCurrentSession().get("Client", id);
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

    public Client findByMailPassword(String loginText, String passwordText) {
        log.debug("finding Client instance by login/password");
        Client loginClient = null;
        try {
            session = sessionFactory.getCurrentSession();
            Transaction transaction   = session.beginTransaction();
            loginClient = (Client) session.createCriteria(Client.class).add(Restrictions.eq("mail", loginText)).add(Restrictions.eq("password", passwordText)).uniqueResult();

        } catch (RuntimeException re) {
            log.error("findByMailPassword failed", re);
            throw re;
        } finally {
           session.close();
        }
        return loginClient;
    }

    public List findByExample(Client instance) {
        log.debug("finding Client instance by example");
        try {
            List results = sessionFactory.getCurrentSession().createCriteria("Client").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
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
