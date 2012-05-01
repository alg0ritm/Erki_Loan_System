/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loansystem.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.dom4j.Node;
import org.hibernate.HibernateException;
import org.hibernate.collection.PersistentCollection;
import org.hibernate.collection.PersistentSet;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.persister.collection.CollectionPersister;
import org.hibernate.usertype.UserCollectionType;

/**
 *
 * @author antonve
 */
public class LoanHistoryCollection extends 
PersistentSet implements UserCollectionType {
    
    
     public LoanHistoryCollection() {
        super();
    }
    public LoanHistoryCollection(SessionImplementor session) {
        super(session);
    }        
    public LoanHistoryCollection(SessionImplementor session, 
      Set set) {
        super(session, set);
    }   

    @Override
    public PersistentCollection instantiate(SessionImplementor si, CollectionPersister cp) throws HibernateException {
        return new LoanHistoryCollection(si);
    }
    
     @Override
    public Object instantiate(int anticipatedSize) {
        return new HashSet();  }
    
    @Override
    public Collection getOrphans(Serializable snapshot, 
      String entityName) throws HibernateException {
        final Collection orphans = super.getOrphans(snapshot, entityName);
        if(orphans != null){
            final Iterator iterator = orphans.iterator();
            while (iterator.hasNext()) {
                final Object item = iterator.next();
                if (item != null && item instanceof Node) {
                    final Node node = (Node) item;
                    if(node.getParent() == null){
                        iterator.remove();
                    }                   
                }
            }
        }         
        return orphans;
    }
    
    @Override
    public boolean contains(Object collection, Object entity) {
        Set set = (Set)collection;        
        return set.contains(entity);
    }

    // could be common for all collection implementations.
    @Override
    public Iterator getElementsIterator(Object collection) {
        return ((Set)collection).iterator();
    }

    // common for list-like collections.
    @Override
    public Object indexOf(Object collection, Object entity) {
        return null;
    }

    @Override
    public PersistentCollection wrap(SessionImplementor si, Object o) {
        return new LoanHistoryCollection(si, (Set)o);
    }


    @Override
    public Object replaceElements(Object original, Object target,
      CollectionPersister persister, Object owner,
      Map copyCache, SessionImplementor session) 
      throws HibernateException {        
        Set result = (Set) target;     
        result.clear();
        if(original != null && original instanceof Collection){
            Iterator it = ((Collection)original).iterator();
            while(it.hasNext()){
                final Object item = it.next();
                if(item != null){
                    result.add(item);
                }
            }            
        }
        return result;        
    }
    
}
