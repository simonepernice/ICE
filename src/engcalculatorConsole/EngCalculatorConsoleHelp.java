/*
 *     ICE (Interval Calculator for Engineer) is a programmable calculator working on intervals.
 *     Copyright (C) 2009  Simone Pernice
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public InfLicense as published by
 *     the Free Software Foundation, either version 3 of the InfLicense, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public InfLicense for more details.
 *
 *     You should have received a copy of the GNU General Public InfLicense
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package engcalculatorConsole;

/**
 *
 * @author Stefania Giaconia, Simone Pernice <pernice@libero.it>
 */

import engcalculatorConsole.findReplace.EngCalculatorConsoleFind;
import engcalculator.function.embedded.information.InfAbout;
import engcalculator.function.embedded.information.InfExamples;
import engcalculator.function.embedded.information.InfFeatures;
import engcalculator.function.embedded.information.InfFunctionList;
import engcalculator.function.embedded.information.InfHistory;
import engcalculator.function.embedded.information.InfInstructionsConsole;
import engcalculator.function.embedded.information.InfInstructionsKernel;
import engcalculator.function.embedded.information.InfLicense;
import engcalculator.function.embedded.information.InfTutorial;
import java.awt.event.KeyEvent;
import javax.imageio.ImageIO;

public final class EngCalculatorConsoleHelp extends javax.swing.JDialog {
    private EngCalculatorConsoleFind searchBox;

    public EngCalculatorConsoleHelp(java.awt.Frame parent) {
        super(parent);
        
        setLocationRelativeTo(parent);

        try {
            setIconImage(ImageIO.read(getClass().getResourceAsStream("resources/icon.png")));
        } catch (Exception ex) {
            //It was not possible to set the proper icon...
        }

        initComponents();
        
        jButtonFind.setMnemonic(KeyEvent.VK_F);
        
        getRootPane().setDefaultButton(jButtonClose);
        
        jComboBoxShow.setSelectedIndex(0);
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonClose = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaOutput = new javax.swing.JTextArea();
        jButtonFind = new javax.swing.JButton();
        jComboBoxShow = new javax.swing.JComboBox<String>();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(engcalculatorConsole.EngCalculatorConsoleApp.class).getContext().getResourceMap(EngCalculatorConsoleHelp.class);
        setTitle(resourceMap.getString("title")); // NOI18N
        setName("aboutBox"); // NOI18N

        jButtonClose.setMnemonic('C');
        jButtonClose.setText(resourceMap.getString("jButtonClose.text")); // NOI18N
        jButtonClose.setName("jButtonClose"); // NOI18N
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTextAreaOutput.setEditable(false);
        jTextAreaOutput.setColumns(20);
        jTextAreaOutput.setLineWrap(true);
        jTextAreaOutput.setRows(5);
        jTextAreaOutput.setText(resourceMap.getString("jTextAreaOutput.text")); // NOI18N
        jTextAreaOutput.setWrapStyleWord(true);
        jTextAreaOutput.setName("jTextAreaOutput"); // NOI18N
        jScrollPane1.setViewportView(jTextAreaOutput);

        jButtonFind.setMnemonic('F');
        jButtonFind.setText(resourceMap.getString("jButtonFind.text")); // NOI18N
        jButtonFind.setName("jButtonFind"); // NOI18N
        jButtonFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFindActionPerformed(evt);
            }
        });

        jComboBoxShow.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] { "Tutorial", "Examples", "Feature list", "Instructions Kernel", "Instructions Console", "Function list", "About ICE", "History", "License" }));
        jComboBoxShow.setName("jComboBoxShow"); // NOI18N
        jComboBoxShow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxShowActionPerformed(evt);
            }
        });

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 746, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxShow, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButtonFind)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 644, Short.MAX_VALUE)
                        .addComponent(jButtonClose, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxShow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonClose)
                    .addComponent(jButtonFind))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed
        setVisible(false);
    }//GEN-LAST:event_jButtonCloseActionPerformed

    private void jButtonFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFindActionPerformed
        if (searchBox == null) searchBox = new EngCalculatorConsoleFind (jTextAreaOutput, this);
        searchBox.setVisible(true);
    }//GEN-LAST:event_jButtonFindActionPerformed

    private void jComboBoxShowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxShowActionPerformed
        switch (jComboBoxShow.getSelectedIndex()) {
            case 0:
                jTextAreaOutput.setText(InfTutorial.TUTORIAL);      
                break;
            case 1:
                jTextAreaOutput.setText(InfExamples.EXAMPLES);      
                break;
            case 2:
                jTextAreaOutput.setText(InfFeatures.FEATURES);
                break;           
            case 3:
                jTextAreaOutput.setText(InfInstructionsKernel.INSTRUCTIONSKERNEL);
                break;
            case 4:
                jTextAreaOutput.setText(InfInstructionsConsole.INSTRUCTIONSCONSOLE);
                break;
            case 5:
                jTextAreaOutput.setText(InfFunctionList.FUNCTIONLIST);                            
                break;
            case 6:
                jTextAreaOutput.setText(InfAbout.ABOUT);        
                break;                
            case 7:
                jTextAreaOutput.setText(InfHistory.HISTORY);             
                break;                                
            case 8:
                jTextAreaOutput.setText(InfLicense.LICENSE);                     
                break;                                
        }
        jTextAreaOutput.setCaretPosition(0);           
    }//GEN-LAST:event_jComboBoxShowActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonFind;
    private javax.swing.JComboBox<String> jComboBoxShow;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaOutput;
    // End of variables declaration//GEN-END:variables
    
}
