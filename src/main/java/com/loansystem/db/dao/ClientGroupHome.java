package com.loansystem.db.dao;

// default package
// Generated Nov 13, 2011 9:49:24 PM by Hibernate Tools 3.4.0.CR1
import com.loansystem.hibernate.HibernateUtil;
import com.loansystem.model.ClientGroup;
import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * Home object for domain model class ClientGroup.
 * @see .ClientGroup
 * @author Hibernate Tools
 */
public class ClientGroupHome {

    private static final Log log = LogFactory.getLog(ClientGroupHome.class);
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

    public void persist(ClientGroup transientInstance) {
        log.debug("persisting ClientGroup instance");
        try {
            sessionFactory.getCurrentSession().persist(transientInstance);
            log.debug("persist successful");
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void attachDirty(ClientGroup instance) {
        log.debug("attaching dirty ClientGroup instance");
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(ClientGroup instance) {
        log.debug("attaching clean ClientGroup instance");
        try {
            sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void delete(ClientGroup persistentInstance) {
        log.debug("deleting ClientGroup instance");
        try {
            sessionFactory.getCurrentSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public ClientGroup merge(ClientGroup detachedInstance) {
        log.debug("merging ClientGroup instance");
        try {
            ClientGroup result = (ClientGroup) sessionFactory.getCurrentSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public ClientGroup findById(java.lang.String id) {
        log.debug("getting ClientGroup instance with id: " + id);
        try {
            ClientGroup instance = (ClientGroup) sessionFactory.getCurrentSession().get("ClientGroup", id);
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

    public List findByExample(ClientGroup instance) {
        log.debug("finding ClientGroup instance by example");
        try {
            List results = sessionFactory.getCurrentSession().createCriteria("ClientGroup").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public ClientGroup findByRating(double rating, Session session) {
        Session sessionLoc = HibernateUtil.createRequieredSession(session);
        ClientGroup instance = null;
        try {
            log.info("before save");
            instance = (ClientGroup) session.createCriteria("com.loansystem.model.ClientGroup").add(Restrictions.gt("minRating",rating))
                    //.addOrder(Order.asc("maxRating"))
                    .setMaxResults(1).uniqueResult();
            //session.close();
        } catch (Exception ex) {
            log.error(ex);
            return null;
            //session.close();
        }

        return instance;

    }

    public ClientGroup findByName(String name, Session session) {
        Session sessionLoc = HibernateUtil.createRequieredSession(session);
        ClientGroup instance = null;
        try {
            log.info("before save");
            instance = (ClientGroup) sessionLoc.createCriteria("com.loansystem.model.ClientGroup").add(Restrictions.gt("name",name))
                    //.addOrder(Order.asc("maxRating"))
                    .setMaxResults(1).uniqueResult();
            //session.close();
            if(session==null) {
                sessionLoc.getTransaction().commit();
            }
        } catch (Exception ex) {
            log.error(ex);
            return null;
            //session.close();
        }

        return instance;
    }
}
