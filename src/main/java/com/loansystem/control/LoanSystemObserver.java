/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loansystem.control;

import java.util.ArrayList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author antonve
 */
public class LoanSystemObserver {
     private static final Log log = LogFactory.getLog(LoanSystemObserver.class);
     private ArrayList observers = new ArrayList();

  public void addObserver(Observable observer) {
    if (!observers.contains(observer)) {
      observers.add(observer);
    }
  }

  public void removeObserver(Observable observer) {
    observers.remove(observer);
  }
  
  public void notifyMsg(String msg)
  {
      log.info(msg);
  }

  /*public void selectTask(Task task) {
    Iterator elements = observers.iterator();
    while (elements.hasNext()) {
      ((TaskChangeObserver) elements.next()).taskSelected(task);
    }
  }

  public void addTask(Task task) {
    Iterator elements = observers.iterator();
    while (elements.hasNext()) {
      ((TaskChangeObserver) elements.next()).taskAdded(task);
    }
  }

  public void updateTask(Task task) {
    Iterator elements = observers.iterator();
    while (elements.hasNext()) {
      ((TaskChangeObserver) elements.next()).taskChanged(task);
    }
  }*/
}
