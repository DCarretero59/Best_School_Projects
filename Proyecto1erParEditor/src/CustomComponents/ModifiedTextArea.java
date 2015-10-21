/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CustomComponents;

import java.awt.event.KeyEvent;
import javax.swing.JTextArea;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class ModifiedTextArea extends JTextArea {

    public ModifiedTextArea() {
        super();
        super.setLineWrap(true);
    }

    @Override
    protected Document createDefaultModel() {
        return new UpperCaseDocument();
    }

    class UpperCaseDocument extends PlainDocument {

        @Override
        public void insertString(int offs, String str, AttributeSet a)
                throws BadLocationException {

            if (str == null) {
                return;
            }

            char[] chars = str.toCharArray();
            boolean ok = true;
/*
            for (int i = 0; i < chars.length; i++) {
                for (int j = 44; j <= 57; j++) {
                    if (chars[i] == (char) j) {
                        ok = true;
                    }
                }
                for (int j = 64; j <= 90; j++) {
                    if (chars[i] == (char) j) {
                        ok = true;
                    }
                }
                for (int j = 95; j <= 122; j++) {
                    if (chars[i] == (char) j) {
                        ok = true;
                    }
                }
                for (int j = 128; j <= 165; j++) {
                    if (chars[i] == (char) j) {
                        ok = true;
                    }
                }
                for (int j = 181; j <= 183; j++) {
                    if (chars[i] == (char) j) {
                        ok = true;
                    }
                }
                for (int j = 198; j <= 199; j++) {
                    if (chars[i] == (char) j) {
                        ok = true;
                    }
                }

                for (int j = 209; j <= 212; j++) {
                    if (chars[i] == (char) j) {
                        ok = true;
                    }
                }
                for (int j = 224; j <= 237; j++) {
                    if (chars[i] == (char) j) {
                        ok = true;
                    }
                }
                if (chars[i] == (char) 32) {
                    ok = true;
                }
                if (chars[i] == (char) 40) {
                    ok = true;
                }
                if (chars[i] == (char) 41) {
                    ok = true;
                }
                if (chars[i] == (char) 39) {
                    ok = true;
                }

            }
*/
            if (ok) {
                super.insertString(offs, new String(chars), a);
            }

        }
    }
}
