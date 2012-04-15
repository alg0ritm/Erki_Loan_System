package com.loansystem.db.dao;

// default package
// Generated Nov 13, 2011 9:49:24 PM by Hibernate Tools 3.4.0.CR1

import com.loansystem.backend.model.UserLoginInput;
import com.loansystem.model.Employee;
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
 * Home object for domain model class Employee.
 * @see .Employee
 * @author Hibernate Tools
 */
public class EmployeeHome {

	private static final Log log = LogFactory.getLog(EmployeeHome.class);
        Session session;

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(Employee transientInstance) {
		log.debug("persisting Employee instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Employee instance) {
		log.debug("attaching dirty Employee instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Employee instance) {
		log.debug("attaching clean Employee instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Employee persistentInstance) {
		log.debug("deleting Employee instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Employee merge(Employee detachedInstance) {
		log.debug("merging Employee instance");
		try {
			Employee result = (Employee) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Employee findById(java.lang.String id) {
		log.debug("getting Employee instance with id: " + id);
		try {
			Employee instance = (Employee) sessionFactory.getCurrentSession().get("Employee", id);
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

	public List findByExample(Employee instance) {
		log.debug("finding Employee instance by example");
		try {
			List results = sessionFactory.getCurrentSession().createCriteria("Employee").add(Example.create(instance))
					.list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

    public Employee findByNamePassword(UserLoginInput userLoginInput) {
        log.debug("finding Client instance by login/password");
        Employee loginEmployee = null;
        try {
            session = sessionFactory.getCurrentSession();
            Transaction transaction   = session.beginTransaction();
            loginEmployee = (Employee) session.createCriteria(Employee.class).add(Restrictions.eq("mail", userLoginInput.getLogin())).add(Restrictions.eq("password", userLoginInput.getPassword())).uniqueResult();

        } catch (RuntimeException re) {
            log.error("findByMailPassword failed", re);
            throw re;
        } finally {
           session.close();
        }
        return loginEmployee;
    }
}
