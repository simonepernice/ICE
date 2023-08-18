/*
 *     ICE (Interval Calculator for Engineer) is a programmable calculator working on intervals.
 *     Copyright (C) 2009  Simone Pernice
 * 
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * ConsoleEngCalculatorSearch.java
 *
 * Created on Sep 30, 2011, 11:35:31 PM
 */

package engcalculatorConsole.findReplace;

import java.awt.Color;
import java.awt.event.KeyEvent;
import javax.imageio.ImageIO;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import javax.swing.JDialog;
import java.awt.Frame;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
public final class EngCalculatorConsoleFind extends javax.swing.JDialog {

    private JTextComponent jTextComponentoSearchArea;
    /** Creates new form ConsoleEngCalculatorSearch
     * @param searchArea */
    public EngCalculatorConsoleFind(JTextComponent searchArea, JDialog parent) {
        super (parent, true);
        setLocationRelativeTo(parent);
        init (searchArea);
    }
    
    public EngCalculatorConsoleFind(JTextComponent searchArea, Frame parent) {
        super (parent, true);
        setLocationRelativeTo(parent);
        init (searchArea);
    }    
    
    private void init (JTextComponent searchArea) {
        initComponents();
        
        jButtonNext.setMnemonic(KeyEvent.VK_N);
        jButtonPrevious.setMnemonic(KeyEvent.VK_P);
        
        this.jTextComponentoSearchArea = searchArea;

        try {
            setIconImage(ImageIO.read(getClass().getResourceAsStream("resources/icon.png")));
        } catch (Exception ex) {
            //It was not possible to set the proper icon...
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextFieldTextToSearch = new javax.swing.JTextField();
        jButtonNext = new javax.swing.JButton();
        jButtonPrevious = new javax.swing.JButton();
        jButtonClose = new javax.swing.JButton();
        jCheckBoxIgnoreCase = new javax.swing.JCheckBox();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(engcalculatorConsole.EngCalculatorConsoleApp.class).getContext().getResourceMap(EngCalculatorConsoleFind.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setLocationByPlatform(true);
        setName("Form"); // NOI18N

        jTextFieldTextToSearch.setText(resourceMap.getString("jTextFieldTextToSearch.text")); // NOI18N
        jTextFieldTextToSearch.setName("jTextFieldTextToSearch"); // NOI18N

        jButtonNext.setMnemonic('N');
        jButtonNext.setText(resourceMap.getString("jButtonNext.text")); // NOI18N
        jButtonNext.setName("jButtonNext"); // NOI18N
        jButtonNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNextActionPerformed(evt);
            }
        });

        jButtonPrevious.setMnemonic('P');
        jButtonPrevious.setText(resourceMap.getString("jButtonPrevious.text")); // NOI18N
        jButtonPrevious.setName("jButtonPrevious"); // NOI18N
        jButtonPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPreviousActionPerformed(evt);
            }
        });

        jButtonClose.setMnemonic('C');
        jButtonClose.setText(resourceMap.getString("jButtonClose.text")); // NOI18N
        jButtonClose.setName("jButtonClose"); // NOI18N
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });

        jCheckBoxIgnoreCase.setText(resourceMap.getString("jCheckBoxIgnoreCase.text")); // NOI18N
        jCheckBoxIgnoreCase.setName("jCheckBoxIgnoreCase"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldTextToSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonNext)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonPrevious)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 190, Short.MAX_VALUE)
                        .addComponent(jButtonClose))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jCheckBoxIgnoreCase)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextFieldTextToSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBoxIgnoreCase)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonNext)
                    .addComponent(jButtonPrevious)
                    .addComponent(jButtonClose))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed
        setVisible(false);
    }//GEN-LAST:event_jButtonCloseActionPerformed

    private void jButtonNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNextActionPerformed
        search (true, jTextFieldTextToSearch.getText(), jCheckBoxIgnoreCase.isSelected());
    }//GEN-LAST:event_jButtonNextActionPerformed

    private void jButtonPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPreviousActionPerformed
        search (false, jTextFieldTextToSearch.getText(), jCheckBoxIgnoreCase.isSelected());
    }//GEN-LAST:event_jButtonPreviousActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonNext;
    private javax.swing.JButton jButtonPrevious;
    private javax.swing.JCheckBox jCheckBoxIgnoreCase;
    private javax.swing.JTextField jTextFieldTextToSearch;
    // End of variables declaration//GEN-END:variables

    private final static DefaultHighlighter.DefaultHighlightPainter HIGHLIGHTER = new DefaultHighlighter.DefaultHighlightPainter(Color.ORANGE);
    
    private void search (boolean forward, String search, boolean ignoreCase) {
        String output = jTextComponentoSearchArea.getText();
        if (output == null) return;
        if (ignoreCase) {
            output = output.toLowerCase();
            search = search.toLowerCase();
        }
        if (search.length() != 0) {
            int position;
            if (forward) position = output.indexOf(search, jTextComponentoSearchArea.getCaretPosition());
            else position = output.substring(0, jTextComponentoSearchArea.getCaretPosition()).lastIndexOf(search);
            
            //This is a patch because highlight does not work on windows on the main windows (JTextPane) because it counts the newlines as two chars \r\n
            //Count the number of new lines in windows and only on JTextPane
            int newlines = 0;
            if ((jTextComponentoSearchArea instanceof JTextPane) && System.getProperty("os.name").toLowerCase().contains("win")) {                
                int p = output.indexOf("\n");
                while (p != -1 && p <= position) {
                    ++p;
                    p = output.indexOf("\n", p);
                    ++ newlines;
                }
            }    
            
            position -= newlines; //subtrace new lines because highlight does not work good
            
            if (position > -1) {
                Highlighter h = jTextComponentoSearchArea.getHighlighter();
                h.removeAllHighlights();
                
                try {
                    h.addHighlight(position, position + search.length(), HIGHLIGHTER);
                } catch (BadLocationException ex) {
                    //not possible to highlight
                }
                
                position += newlines; //add new lines because caret position works fine
                
                if (search.length()>1) jTextComponentoSearchArea.setCaretPosition(position + search.length()/2);
                else {
                    if(forward) jTextComponentoSearchArea.setCaretPosition(position + search.length());
                    else jTextComponentoSearchArea.setCaretPosition(position);
                }
            }
        }
    }
    
    @Override
    public void setVisible (boolean vis) {
        String se = jTextComponentoSearchArea.getSelectedText();
        if (se != null) jTextFieldTextToSearch.setText(se);
        super.setVisible(vis);        
    }
}