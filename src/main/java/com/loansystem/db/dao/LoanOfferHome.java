package com.loansystem.db.dao;

// default package
// Generated Nov 13, 2011 9:49:24 PM by Hibernate Tools 3.4.0.CR1
import com.loansystem.hibernate.HibernateUtil;
import com.loansystem.model.Client;
import com.loansystem.model.LoanOffer;
import java.util.ArrayList;
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
 * Home object for domain model class LoanOffer.
 * @see .LoanOffer
 * @author Hibernate Tools
 */
public class LoanOfferHome {

    private static final Log log = LogFactory.getLog(LoanOfferHome.class);
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

    public void persist(LoanOffer transientInstance) {
        log.debug("persisting LoanOffer instance");
        try {
            sessionFactory.getCurrentSession().persist(transientInstance);
            log.debug("persist successful");
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void attachDirty(LoanOffer instance) {
        log.debug("attaching dirty LoanOffer instance");
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(LoanOffer instance) {
        log.debug("attaching clean LoanOffer instance");
        try {
            sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void delete(LoanOffer persistentInstance) {
        log.debug("deleting LoanOffer instance");
        try {
            sessionFactory.getCurrentSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public LoanOffer merge(LoanOffer detachedInstance) {
        log.debug("merging LoanOffer instance");
        try {
            LoanOffer result = (LoanOffer) sessionFactory.getCurrentSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public LoanOffer findById(java.lang.String id) {
        log.debug("getting LoanOffer instance with id: " + id);
        try {
            LoanOffer instance = (LoanOffer) sessionFactory.getCurrentSession().get("LoanOffer", id);
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

    public List findByExample(LoanOffer instance) {
        log.debug("finding LoanOffer instance by example");
        try {
            List results = sessionFactory.getCurrentSession().createCriteria("LoanOffer").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    //public ArrayList<LoanOffer> getAvailableLoanOffers()
    public ArrayList<LoanOffer> getAvailableLoanOffers(Client client) {
        session = sessionFactory.getCurrentSession();
        //session.beginTransaction();
        ArrayList<LoanOffer> avLoanOffersList = (ArrayList<LoanOffer>) session.createCriteria(LoanOffer.class).add(Restrictions.eq("client_group_id", client.getClientGroup().getGroupId())).list();
        session.close();
        return avLoanOffersList;
    }

    public LoanOffer findBy4Parameters(LoanOffer loanOffer) {
        log.debug("getting last Loan instance for LoanOffer  " + loanOffer.getSum());
        Session session = null;
        LoanOffer instance = null;
        /*<id name="offerId" type="string">
        <column name="offer_id" length="2000000000" />
        <generator class="assigned" />
        </id>
        <property name="sum" type="string">
        <column name="sum" length="2000000000" not-null="true" />
        </property>
        <property name="period" type="string">
        <column name="period" length="2000000000" not-null="true" />
        </property>
        <property name="apr" type="string">
        <column name="apr" length="2000000000" not-null="true" />
        </property>
        <property name="ratingBonus" type="string">
        <column name="rating_bonus" length="2000000000" not-null="true" />
        </property>
        <property name="ratingDrop" type="string">
        <column name="rating_drop" length="2000000000" not-null="true" />
        </property>
        <property name="statusId" type="string">
        <column name="status_id" length="2000000000" not-null="true" />
        </property>
        <many-to-one class="com.loansystem.model.ClientGroup" fetch="select" name="clientGroup">
        <column length="2000000000" name="rowid" not-null="true" unique="true"/>
        </many-to-one>*/
        //Create dynamic search create after not fully initilaized object 
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            instance = (LoanOffer) session.createCriteria("com.loansystem.model.LoanOffer").add(Restrictions.eq("sum", loanOffer.getSum())).add(Restrictions.eq("period", loanOffer.getPeriod())).add(Restrictions.eq("apr", loanOffer.getApr())).add(Restrictions.eq("ratingBonus", loanOffer.getRatingBonus())).addOrder(Order.desc("offerId")).setMaxResults(1).uniqueResult();
            if (instance == null) {
                log.debug("get successful, no instance found");
            } else {
                log.debug("get successful, instance found");
            }

        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        } finally {
            session.close();
        }
        return instance;
    }
}
