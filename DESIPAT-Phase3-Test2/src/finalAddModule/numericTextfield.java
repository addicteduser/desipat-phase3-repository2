/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package finalAddModule;

import java.awt.BorderLayout;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Aram
 */
public class numericTextfield extends JTextField {
   

@Override
protected Document createDefaultModel()
{
return new NumericDocument();
}

private static class NumericDocument extends PlainDocument
{
// The regular expression to match input against (zero or more digits)
private final static Pattern DIGITS = Pattern.compile("\\d*");

@Override
public void insertString(int offs, String str, AttributeSet a) throws BadLocationException
{
// Only insert the text if it matches the regular expression
if (str != null && DIGITS.matcher(str).matches())
super.insertString(offs, str, a);
}
}
}
    

