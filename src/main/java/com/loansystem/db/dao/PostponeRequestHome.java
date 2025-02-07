package com.loansystem.db.dao;

// default package
// Generated Nov 13, 2011 9:49:24 PM by Hibernate Tools 3.4.0.CR1
import com.loansystem.hibernate.HibernateUtil;
import com.loansystem.model.PostponeRequest;
import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class PostponeRequest.
 * @see .PostponeRequest
 * @author Hibernate Tools
 */
public class PostponeRequestHome {

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

    public void persist(PostponeRequest transientInstance) {
        log.debug("persisting PostponeRequest instance");
        try {
            sessionFactory.getCurrentSession().persist(transientInstance);
            log.debug("persist successful");
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void attachDirty(PostponeRequest instance) {
        log.debug("attaching dirty PostponeRequest instance");
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(PostponeRequest instance) {
        log.debug("attaching clean PostponeRequest instance");
        try {
            sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void delete(PostponeRequest persistentInstance, Session session) {
        Session sessionLoc = HibernateUtil.createRequieredSession(session);
        log.debug("deleting PostponeRequest instance");
        try {
            sessionLoc.delete(persistentInstance);
            if(session==null) {
                sessionLoc.getTransaction().commit();
            }
            log.debug("delete successful");
            
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public PostponeRequest merge(PostponeRequest detachedInstance, Session session) {
        Session sessionLoc = null;
        if (session == null) {
            sessionLoc = sessionFactory.getCurrentSession();
            Transaction transaction = sessionLoc.beginTransaction();
        } else {
            sessionLoc = session;
        }


        log.debug("merging PostponeRequest instance");
        try {
            PostponeRequest result = (PostponeRequest) sessionLoc.merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public PostponeRequest findById(java.lang.String id) {
        log.debug("getting PostponeRequest instance with id: " + id);
        try {
            PostponeRequest instance = (PostponeRequest) sessionFactory.getCurrentSession().get("PostponeRequest", id);
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

    public List findByExample(PostponeRequest instance) {
        log.debug("finding PostponeRequest instance by example");
        try {
            List results = sessionFactory.getCurrentSession().createCriteria("PostponeRequest").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public int savePostponeRequest(PostponeRequest postponeRequest, Session session) {
        Session sessionLoc = HibernateUtil.createRequieredSession(session);
        
        //loginClient = (Client) session.createCriteria(Client.class).add(Restrictions.eq("mail", loginText)).add(Restrictions.eq("password", passwordText)).uniqueResult();
        try {
            //SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            //Session session = sessionFactory.getCurrentSession();
            //Transaction tx = session.beginTransaction();
            log.info("save Postpone Request : before save");
            sessionLoc.save(postponeRequest);

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
}
