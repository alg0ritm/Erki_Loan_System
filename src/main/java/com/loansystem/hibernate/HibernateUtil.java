package com.loansystem.hibernate;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate Utility class with a convenient method to get Session Factory object.
 *
 * @author antonve
 */
public class HibernateUtil {

    private static SessionFactory sessionFactory;

    static {
        try {
            // Create the SessionFactory from hibernate.cfg.xml 
            sessionFactory = new Configuration().configure().buildSessionFactory();

        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed 
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public Session openNewSession() {
        return sessionFactory.openSession();
    }

    /**
     * Returns a session from the session context. If there is no session in the context it opens a session,
     * stores it in the context and returns it.
     * This factory is intended to be used with a hibernate.cfg.xml
     * including the following property <property
     * name="current_session_context_class">thread</property> This would return
     * the current open session or if this does not exist, will create a new
     * session
     * 
     * @return the session
     */
    public Session getCurrentLoadedSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * closes the session factory
     */
    public static void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
        sessionFactory = null;

    }
    
    public static Session createRequieredSession(Session session) {
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
