/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loansystem.util;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author antonve
 */
public class LoanUIutils {

    private static final Log log = LogFactory.getLog(LoanUIutils.class);

    public static int createQuestionPopup(String[] opts, Object requestObject, String initialQuestion) {

        String s = initialQuestion + '\n';
        try {
            Class cls = requestObject.getClass();
            log.info(cls.toString());
            //c = Class.forName(requestObject.getClass().toString());
            Method m[] = cls.getMethods();
            for (Method method : m) {
                if (isGetter(method)) {
                    String methodField = eliminateGet(method);
                    String returnValue = String.valueOf(method.invoke(requestObject, null));
                    if (returnValue != null && !returnValue.equals("null")) {
                        log.info("returnValue = " + returnValue);
                        s += methodField + " : " + returnValue + '\n';
                    }
                }
            }
            //log.info(m[0].toString());
        } catch (Exception ex) {
            Logger.getLogger(LoanUIutils.class.getName()).log(Level.SEVERE, null, ex);
        }
        String[] choices = opts;
        int choiceResposne = JOptionPane.showOptionDialog(
                null // Center in window.
                , s // Message
                , "" // Title in titlebar
                , JOptionPane.YES_NO_OPTION // Option type
                , JOptionPane.PLAIN_MESSAGE // messageType
                , null // Icon (none)
                , choices // Button text as above.
                , "Yes" // Default button's label
                );


        return choiceResposne;


        /* switch (choiceResposne) {
        case 0:
        JOptionPane.showMessageDialog(loanTabView.getNewLoanRequestPanel(), "Y've chosen NO " + rowIndex);
        break;
        case 1:
        
        LoanOffer selectedLoanOffer = loanTabView.getNewLoanRequestPanel().getSelectedLoanOffer(rowIndex);
        LoanService loanService = new LoanServiceImpl();
        loanService.createNewLoan(client, selectedLoanOffer);
        loanTabView.removeNewLoanTab(client);
        Loan lastLoan  = reValidateLastLoan();
        loanTabModel.setLastLoan(lastLoan);
        loanTabView.showExistingLoanTabControls(client);
        loanTabView.showExisitingLoanTab(client);
        
        JOptionPane.showMessageDialog(loanTabView.getNewLoanRequestPanel(), "Thanks, your request has been recorded for confirmation" + rowIndex);
        break;
        case -1:
        //... Both the quit button (3) and the close box(-1) handled here.
        System.exit(0);     // It would be better to exit loop, but...
        default:
        //... If we get here, something is wrong.  Defensive programming.
        JOptionPane.showMessageDialog(null, "Unexpected response " + choiceResposne);
        }
        
        
        
        }*/
    }

    public static boolean isGetter(Method method) {
        if (!method.getName().startsWith("get")) {
            return false;
        }
        if (method.getName().startsWith("getClass")) {
            return false;
        }
        if (method.getParameterTypes().length != 0) {
            return false;
        }
        if (void.class.equals(method.getReturnType())) {
            return false;
        }

        return true;
    }

    public static boolean isSetter(Method method) {
        if (!method.getName().startsWith("set")) {
            return false;
        }
        if (method.getParameterTypes().length != 1) {
            return false;
        }
        return true;
    }

    private static String eliminateGet(Method method) {
        log.info(method.getName().substring(method.getName().indexOf("get") + 3));
        return method.getName().substring(method.getName().indexOf("get") + 3);
    }
}
