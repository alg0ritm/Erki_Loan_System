package com.loansystem.db.dao;

// default package
// Generated Nov 13, 2011 9:49:24 PM by Hibernate Tools 3.4.0.CR1
import com.loansystem.hibernate.HibernateUtil;
import com.loansystem.model.Client;
import com.loansystem.model.ClientHistory;
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
 * Home object for domain model class ClientHistory.
 * @see .ClientHistory
 * @author Hibernate Tools
 */
public class ClientHistoryHome {

    private static final Log log = LogFactory.getLog(ClientHistoryHome.class);
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

    public void persist(ClientHistory transientInstance, Session session) {
        Session sessionLoc = HibernateUtil.createRequieredSession(session);
        log.debug("persisting ClientHistory instance");
        try {
            sessionLoc.persist(transientInstance);
            if (session == null) {
                log.debug("persist successful");
                sessionLoc.getTransaction().commit();
            }
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void attachDirty(ClientHistory instance) {
        log.debug("attaching dirty ClientHistory instance");
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(ClientHistory instance) {
        log.debug("attaching clean ClientHistory instance");
        try {
            sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void save(ClientHistory clientHistory, Session session) {
        Session sessionLoc = HibernateUtil.createRequieredSession(session);
        log.debug("persisting ClientHistory instance");
        try {
            sessionLoc.save(clientHistory);
            if (session == null) {
                log.debug("persist successful");
                sessionLoc.getTransaction().commit();
            }
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void delete(ClientHistory persistentInstance) {
        log.debug("deleting ClientHistory instance");
        try {
            sessionFactory.getCurrentSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public ClientHistory merge(ClientHistory detachedInstance, Session session) {
        Session sessionLoc = createRequieredSession(session);

        log.debug("merging ClientHistory instance");
        try {
            ClientHistory result = (ClientHistory) sessionLoc.merge(detachedInstance);
            if (session == null) {
                sessionLoc.getTransaction().commit();
            }
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public ClientHistory findById(ClientHistory id) {
        log.debug("getting ClientHistory instance with id: " + id);
        try {
            ClientHistory instance = (ClientHistory) sessionFactory.getCurrentSession().get("ClientHistory", id);
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

    public List findByExample(ClientHistory instance) {
        log.debug("finding ClientHistory instance by example");
        try {
            List results = sessionFactory.getCurrentSession().createCriteria("ClientHistory").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public ClientHistory findByClient(Client client, Session session) {
        Session sessionLoc = HibernateUtil.createRequieredSession(session);
        ClientHistory clientHistory = null;
        log.debug("persisting ClientHistory instance");
        try {
            clientHistory = (ClientHistory) sessionLoc.createCriteria(ClientHistory.class).add(Restrictions.eq("client", client)).addOrder(Order.desc("date")).setMaxResults(1).uniqueResult();
        } catch (RuntimeException re) {
            if(session==null) {
                sessionLoc.getTransaction().commit();
            }
            log.error("persist failed", re);
            throw re;
        }
        return clientHistory;
    }
}
