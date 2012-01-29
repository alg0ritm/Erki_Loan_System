package com.loansystem.util;
import javax.swing.*;
import java.awt.*;

/** A JPanel that combines a JLabel and a JTextField.
 *  1998-99 Marty Hall, http://www.apl.jhu.edu/~hall/java/
 */

public class JLabeledTextField extends JPanel {
  private JLabel label;
  private JTextField textField;

  public JLabeledTextField(String labelString, String textFieldString,
			   LayoutManager layout) {
    super();
    setLayout(layout);
    label = new JLabel(labelString);
    textField = new JTextField(textFieldString);
    add(label);
    add(textField);
  }
 
  public JLabeledTextField(String labelString, String textFieldString) {
    this(labelString, textFieldString, new FlowLayout(FlowLayout.LEFT));
  }

  public JLabel getJLabel() {
    return(label); 
  }

  public JTextField getJTextField() { 
    return(textField);
  }

  public String getText() {
    return(getJTextField().getText());
  }

  public void setText(String textFieldString) {
    getJTextField().setText(textFieldString);
  }

  public void setFonts(Font f) {
    getJLabel().setFont(f);
    getJTextField().setFont(f);
  }

  public void setEnabled(boolean status) {
   getJLabel().setEnabled(status);
   getJTextField().setEnabled(status);
   super.setEnabled(status);
  }
}
