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
package engcalculatorConsole.ecConsolePlotDisplay.ecPlotOption;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */
import engcalculatorConsole.ecConsolePlotDisplay.EngCalculatorConsolePlotDisplay;
import engcalculatorConsole.iceJPanel.SaveLoadToTextData;
import engcalculatorConsole.iceJPanel.OutputTextData;
import engcalculatorConsole.iceJPanel.InputTextData;
import engcalculatorConsole.iceJPanel.CartesianPlane;
import engcalculator.interval.Interval;
import engcalculator.interval.ListIntervals;
import engcalculatorConsole.ecConsolePlotDisplay.Drawing;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public final class EngCalculatorConsolePlotOptions extends javax.swing.JDialog implements SaveLoadToTextData {

    private final DoubleInputVerifier doubleInputVerifier = new DoubleInputVerifier();
    private final IntegerInputVerifier integerInputVerifier = new IntegerInputVerifier();
    private final EngCalculatorConsolePlotDisplay cecPlotDisplay;
    private final static double EXTRAZOOMPERCENTAGE = 5.;
    private final static double EXTRAZOOMPOWER = 1.;
    private final static int NUMBEROFTICKS = 10;
    private final static int SIGNIFICANTDIGITS = 3;
    private final static int QUANTIZATION = 5;

    public EngCalculatorConsolePlotOptions(java.awt.Frame parent) {
        super(parent);
        setLocationRelativeTo(parent);

        initComponents();

        getRootPane().setDefaultButton(jButtonOK);

        jComboBoxAxesColor.setSelectedIndex(0);//black
        jComboBoxGridColor.setSelectedIndex(6);//light gray
        jComboBoxBackgroundColor.setSelectedIndex(11);//white

        jFileChooser = new JFileChooser();

        cecPlotDisplay = (EngCalculatorConsolePlotDisplay) parent;
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelScale = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldXMin = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldXMax = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextFieldXTick = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextFieldYMax = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextFieldYTick = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldYMin = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jTextFieldCMin = new javax.swing.JTextField();
        jTextFieldCMax = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextFieldVectNumb = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jTextFieldVectMax = new javax.swing.JTextField();
        jButtonOK = new javax.swing.JButton();
        jButtonFitZoom = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanelAxesColor = new javax.swing.JPanel();
        jComboBoxAxesColor = new javax.swing.JComboBox<String>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jComboBoxGridColor = new javax.swing.JComboBox<String>();
        jPanelGridColor = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jComboBoxBackgroundColor = new javax.swing.JComboBox<String>();
        jPanelBackgroundColor = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jCheckBoxFitX = new javax.swing.JCheckBox();
        jCheckBoxFitY = new javax.swing.JCheckBox();
        jCheckBoxFitColor = new javax.swing.JCheckBox();
        jCheckBoxFitYTick = new javax.swing.JCheckBox();
        jCheckBoxFitVector = new javax.swing.JCheckBox();
        jCheckBoxFitXTick = new javax.swing.JCheckBox();
        jButtonSavePicture = new javax.swing.JButton();
        jButtonSave = new javax.swing.JButton();
        jButtonLoad = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jCheckBoxAxes = new javax.swing.JCheckBox();
        jCheckBoxGrid = new javax.swing.JCheckBox();
        jCheckBoxTickLabels = new javax.swing.JCheckBox();
        jCheckBoxDrawLabels = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        jCheckBoxXLog = new javax.swing.JCheckBox();
        jCheckBoxYLog = new javax.swing.JCheckBox();
        jCheckBoxColorLog = new javax.swing.JCheckBox();
        jCheckBoxVectorLog = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(engcalculatorConsole.EngCalculatorConsoleApp.class).getContext().getResourceMap(EngCalculatorConsolePlotOptions.class);
        setTitle(resourceMap.getString("title")); // NOI18N
        setName("Variable Shower"); // NOI18N

        jPanelScale.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanelScale.border.title"))); // NOI18N
        jPanelScale.setName("jPanelScale"); // NOI18N

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        jTextFieldXMin.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldXMin.setText(resourceMap.getString("jTextFieldXMin.text")); // NOI18N
        jTextFieldXMin.setInputVerifier(doubleInputVerifier);
        jTextFieldXMin.setName("jTextFieldXMin"); // NOI18N

        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        jTextFieldXMax.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldXMax.setText(resourceMap.getString("jTextFieldXMax.text")); // NOI18N
        jTextFieldXMax.setInputVerifier(doubleInputVerifier);
        jTextFieldXMax.setName("jTextFieldXMax"); // NOI18N

        jLabel11.setText(resourceMap.getString("jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N

        jTextFieldXTick.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldXTick.setText(resourceMap.getString("jTextFieldXTick.text")); // NOI18N
        jTextFieldXTick.setInputVerifier(doubleInputVerifier);
        jTextFieldXTick.setName("jTextFieldXTick"); // NOI18N

        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N

        jTextFieldYMax.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldYMax.setText(resourceMap.getString("jTextFieldYMax.text")); // NOI18N
        jTextFieldYMax.setInputVerifier(doubleInputVerifier);
        jTextFieldYMax.setName("jTextFieldYMax"); // NOI18N

        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N

        jTextFieldYTick.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldYTick.setText(resourceMap.getString("jTextFieldYTick.text")); // NOI18N
        jTextFieldYTick.setInputVerifier(doubleInputVerifier);
        jTextFieldYTick.setName("jTextFieldYTick"); // NOI18N

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        jTextFieldYMin.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldYMin.setText(resourceMap.getString("jTextFieldYMin.text")); // NOI18N
        jTextFieldYMin.setInputVerifier(doubleInputVerifier);
        jTextFieldYMin.setName("jTextFieldYMin"); // NOI18N

        jLabel13.setText(resourceMap.getString("jLabel13.text")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N

        jLabel14.setText(resourceMap.getString("jLabel14.text")); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N

        jTextFieldCMin.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldCMin.setText(resourceMap.getString("jTextFieldCMin.text")); // NOI18N
        jTextFieldCMin.setInputVerifier(doubleInputVerifier);
        jTextFieldCMin.setName("jTextFieldCMin"); // NOI18N

        jTextFieldCMax.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldCMax.setText(resourceMap.getString("jTextFieldCMax.text")); // NOI18N
        jTextFieldCMax.setInputVerifier(doubleInputVerifier);
        jTextFieldCMax.setName("jTextFieldCMax"); // NOI18N

        jLabel12.setText(resourceMap.getString("jLabel12.text")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N

        jTextFieldVectNumb.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldVectNumb.setText(resourceMap.getString("jTextFieldVectNumb.text")); // NOI18N
        jTextFieldVectNumb.setInputVerifier(integerInputVerifier);
        jTextFieldVectNumb.setName("jTextFieldVectNumb"); // NOI18N

        jLabel15.setText(resourceMap.getString("jLabel15.text")); // NOI18N
        jLabel15.setName("jLabel15"); // NOI18N

        jTextFieldVectMax.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldVectMax.setText(resourceMap.getString("jTextFieldVectMax.text")); // NOI18N
        jTextFieldVectMax.setInputVerifier(doubleInputVerifier);
        jTextFieldVectMax.setName("jTextFieldVectMax"); // NOI18N

        javax.swing.GroupLayout jPanelScaleLayout = new javax.swing.GroupLayout(jPanelScale);
        jPanelScale.setLayout(jPanelScaleLayout);
        jPanelScaleLayout.setHorizontalGroup(
            jPanelScaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelScaleLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelScaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelScaleLayout.createSequentialGroup()
                        .addGroup(jPanelScaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel13)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelScaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldCMin, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldYMin, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldXMin, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelScaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelScaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldYMax, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldXMax, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldCMax, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelScaleLayout.createSequentialGroup()
                        .addGroup(jPanelScaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelScaleLayout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldXTick, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel10))
                            .addGroup(jPanelScaleLayout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldVectNumb, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel15)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelScaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldVectMax)
                            .addComponent(jTextFieldYTick, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(82, Short.MAX_VALUE))
        );

        jPanelScaleLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jTextFieldCMax, jTextFieldCMin, jTextFieldVectMax, jTextFieldVectNumb, jTextFieldXMax, jTextFieldXMin, jTextFieldXTick, jTextFieldYMax, jTextFieldYMin, jTextFieldYTick});

        jPanelScaleLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel10, jLabel11, jLabel12, jLabel13, jLabel14, jLabel15, jLabel6, jLabel7, jLabel8, jLabel9});

        jPanelScaleLayout.setVerticalGroup(
            jPanelScaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelScaleLayout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addGroup(jPanelScaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelScaleLayout.createSequentialGroup()
                        .addGroup(jPanelScaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldXMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelScaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldYMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelScaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldCMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelScaleLayout.createSequentialGroup()
                        .addGroup(jPanelScaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldXMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelScaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldYMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelScaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldCMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelScaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelScaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldVectMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextFieldVectNumb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelScaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelScaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldYTick, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextFieldXTick, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        jPanelScaleLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jTextFieldCMax, jTextFieldCMin, jTextFieldVectMax, jTextFieldVectNumb, jTextFieldXMax, jTextFieldXMin, jTextFieldXTick, jTextFieldYMax, jTextFieldYMin, jTextFieldYTick});

        jPanelScaleLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel10, jLabel11, jLabel12, jLabel13, jLabel14, jLabel15, jLabel6, jLabel7, jLabel8, jLabel9});

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(engcalculatorConsole.EngCalculatorConsoleApp.class).getContext().getActionMap(EngCalculatorConsolePlotOptions.class, this);
        jButtonOK.setAction(actionMap.get("closeDrawBox")); // NOI18N
        jButtonOK.setMnemonic('O');
        jButtonOK.setText(resourceMap.getString("jButtonOK.text")); // NOI18N
        jButtonOK.setName("jButtonOK"); // NOI18N
        jButtonOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOKActionPerformed(evt);
            }
        });

        jButtonFitZoom.setMnemonic('F');
        jButtonFitZoom.setText(resourceMap.getString("jButtonFitZoom.text")); // NOI18N
        jButtonFitZoom.setName("jButtonFitZoom"); // NOI18N
        jButtonFitZoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFitZoomActionPerformed(evt);
            }
        });

        jButtonCancel.setMnemonic('C');
        jButtonCancel.setText(resourceMap.getString("jButtonCancel.text")); // NOI18N
        jButtonCancel.setName("jButtonCancel"); // NOI18N
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel1.border.title"))); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N

        jPanelAxesColor.setBackground(resourceMap.getColor("jPanelAxesColor.background")); // NOI18N
        jPanelAxesColor.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelAxesColor.setName("jPanelAxesColor"); // NOI18N

        javax.swing.GroupLayout jPanelAxesColorLayout = new javax.swing.GroupLayout(jPanelAxesColor);
        jPanelAxesColor.setLayout(jPanelAxesColorLayout);
        jPanelAxesColorLayout.setHorizontalGroup(
            jPanelAxesColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 39, Short.MAX_VALUE)
        );
        jPanelAxesColorLayout.setVerticalGroup(
            jPanelAxesColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 31, Short.MAX_VALUE)
        );

        jComboBoxAxesColor.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] { "BLACK", "BLUE", "CYAN", "DARK_GRAY", "GRAY", "GREEN", "LIGHT_GRAY", "MAGENTA", "ORANGE", "PINK", "RED", "WHITE", "YELLOW" }));
        jComboBoxAxesColor.setName("jComboBoxAxesColor"); // NOI18N
        jComboBoxAxesColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxAxesColorActionPerformed(evt);
            }
        });

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jComboBoxGridColor.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] { "BLACK", "BLUE", "CYAN", "DARK_GRAY", "GRAY", "GREEN", "LIGHT_GRAY", "MAGENTA", "ORANGE", "PINK", "RED", "WHITE", "YELLOW" }));
        jComboBoxGridColor.setName("jComboBoxGridColor"); // NOI18N
        jComboBoxGridColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxGridColorActionPerformed(evt);
            }
        });

        jPanelGridColor.setBackground(resourceMap.getColor("jPanelGridColor.background")); // NOI18N
        jPanelGridColor.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelGridColor.setName("jPanelGridColor"); // NOI18N

        javax.swing.GroupLayout jPanelGridColorLayout = new javax.swing.GroupLayout(jPanelGridColor);
        jPanelGridColor.setLayout(jPanelGridColorLayout);
        jPanelGridColorLayout.setHorizontalGroup(
            jPanelGridColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 39, Short.MAX_VALUE)
        );
        jPanelGridColorLayout.setVerticalGroup(
            jPanelGridColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 31, Short.MAX_VALUE)
        );

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jComboBoxBackgroundColor.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] { "BLACK", "BLUE", "CYAN", "DARK_GRAY", "GRAY", "GREEN", "LIGHT_GRAY", "MAGENTA", "ORANGE", "PINK", "RED", "WHITE", "YELLOW" }));
        jComboBoxBackgroundColor.setName("jComboBoxBackgroundColor"); // NOI18N
        jComboBoxBackgroundColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxBackgroundColorActionPerformed(evt);
            }
        });

        jPanelBackgroundColor.setBackground(resourceMap.getColor("jPanelBackgroundColor.background")); // NOI18N
        jPanelBackgroundColor.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelBackgroundColor.setName("jPanelBackgroundColor"); // NOI18N

        javax.swing.GroupLayout jPanelBackgroundColorLayout = new javax.swing.GroupLayout(jPanelBackgroundColor);
        jPanelBackgroundColor.setLayout(jPanelBackgroundColorLayout);
        jPanelBackgroundColorLayout.setHorizontalGroup(
            jPanelBackgroundColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 39, Short.MAX_VALUE)
        );
        jPanelBackgroundColorLayout.setVerticalGroup(
            jPanelBackgroundColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 31, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBoxAxesColor, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxGridColor, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxBackgroundColor, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelAxesColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanelGridColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanelBackgroundColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel3, jLabel4, jLabel5});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jPanelAxesColor, jPanelBackgroundColor, jPanelGridColor});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jComboBoxAxesColor, jComboBoxBackgroundColor, jComboBoxGridColor});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanelAxesColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanelGridColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanelBackgroundColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jComboBoxAxesColor, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jComboBoxGridColor, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(15, 15, 15)
                            .addComponent(jComboBoxBackgroundColor, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(15, 15, 15)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jComboBoxAxesColor, jComboBoxBackgroundColor, jComboBoxGridColor, jLabel3, jLabel4, jLabel5, jPanelAxesColor, jPanelBackgroundColor, jPanelGridColor});

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel3.border.title"))); // NOI18N
        jPanel3.setName("jPanel3"); // NOI18N

        jCheckBoxFitX.setSelected(true);
        jCheckBoxFitX.setText(resourceMap.getString("jCheckBoxFitX.text")); // NOI18N
        jCheckBoxFitX.setName("jCheckBoxFitX"); // NOI18N

        jCheckBoxFitY.setSelected(true);
        jCheckBoxFitY.setText(resourceMap.getString("jCheckBoxFitY.text")); // NOI18N
        jCheckBoxFitY.setName("jCheckBoxFitY"); // NOI18N

        jCheckBoxFitColor.setSelected(true);
        jCheckBoxFitColor.setText(resourceMap.getString("jCheckBoxFitColor.text")); // NOI18N
        jCheckBoxFitColor.setName("jCheckBoxFitColor"); // NOI18N

        jCheckBoxFitYTick.setSelected(true);
        jCheckBoxFitYTick.setText(resourceMap.getString("jCheckBoxFitYTick.text")); // NOI18N
        jCheckBoxFitYTick.setName("jCheckBoxFitYTick"); // NOI18N

        jCheckBoxFitVector.setSelected(true);
        jCheckBoxFitVector.setText(resourceMap.getString("jCheckBoxFitVector.text")); // NOI18N
        jCheckBoxFitVector.setName("jCheckBoxFitVector"); // NOI18N

        jCheckBoxFitXTick.setSelected(true);
        jCheckBoxFitXTick.setText(resourceMap.getString("jCheckBoxFitXTick.text")); // NOI18N
        jCheckBoxFitXTick.setName("jCheckBoxFitXTick"); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBoxFitX)
                    .addComponent(jCheckBoxFitY)
                    .addComponent(jCheckBoxFitColor)
                    .addComponent(jCheckBoxFitYTick)
                    .addComponent(jCheckBoxFitVector)
                    .addComponent(jCheckBoxFitXTick))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jCheckBoxFitColor, jCheckBoxFitX, jCheckBoxFitY, jCheckBoxFitYTick});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCheckBoxFitX)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBoxFitY)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBoxFitColor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBoxFitVector)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBoxFitXTick)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBoxFitYTick)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jCheckBoxFitColor, jCheckBoxFitX, jCheckBoxFitY, jCheckBoxFitYTick});

        jButtonSavePicture.setAction(actionMap.get("closeDrawBox")); // NOI18N
        jButtonSavePicture.setText(resourceMap.getString("jButtonSavePicture.text")); // NOI18N
        jButtonSavePicture.setName("jButtonSavePicture"); // NOI18N
        jButtonSavePicture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSavePictureActionPerformed(evt);
            }
        });

        jButtonSave.setAction(actionMap.get("closeDrawBox")); // NOI18N
        jButtonSave.setText(resourceMap.getString("jButtonSave.text")); // NOI18N
        jButtonSave.setName("jButtonSave"); // NOI18N
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });

        jButtonLoad.setAction(actionMap.get("closeDrawBox")); // NOI18N
        jButtonLoad.setText(resourceMap.getString("jButtonLoad.text")); // NOI18N
        jButtonLoad.setName("jButtonLoad"); // NOI18N
        jButtonLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLoadActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel2.border.title"))); // NOI18N
        jPanel2.setName("jPanel2"); // NOI18N

        jCheckBoxAxes.setSelected(true);
        jCheckBoxAxes.setText(resourceMap.getString("jCheckBoxAxes.text")); // NOI18N
        jCheckBoxAxes.setName("jCheckBoxAxes"); // NOI18N

        jCheckBoxGrid.setSelected(true);
        jCheckBoxGrid.setText(resourceMap.getString("jCheckBoxGrid.text")); // NOI18N
        jCheckBoxGrid.setName("jCheckBoxGrid"); // NOI18N

        jCheckBoxTickLabels.setSelected(true);
        jCheckBoxTickLabels.setText(resourceMap.getString("jCheckBoxTickLabels.text")); // NOI18N
        jCheckBoxTickLabels.setName("jCheckBoxTickLabels"); // NOI18N

        jCheckBoxDrawLabels.setSelected(true);
        jCheckBoxDrawLabels.setText(resourceMap.getString("jCheckBoxDrawLabels.text")); // NOI18N
        jCheckBoxDrawLabels.setName("jCheckBoxDrawLabels"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBoxAxes)
                    .addComponent(jCheckBoxGrid)
                    .addComponent(jCheckBoxTickLabels)
                    .addComponent(jCheckBoxDrawLabels))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jCheckBoxAxes, jCheckBoxDrawLabels, jCheckBoxGrid, jCheckBoxTickLabels});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCheckBoxAxes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBoxGrid)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBoxTickLabels)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBoxDrawLabels)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jCheckBoxAxes, jCheckBoxDrawLabels, jCheckBoxGrid, jCheckBoxTickLabels});

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel4.border.title"))); // NOI18N
        jPanel4.setName("jPanel4"); // NOI18N

        jCheckBoxXLog.setText(resourceMap.getString("jCheckBoxXLog.text")); // NOI18N
        jCheckBoxXLog.setName("jCheckBoxXLog"); // NOI18N

        jCheckBoxYLog.setText(resourceMap.getString("jCheckBoxYLog.text")); // NOI18N
        jCheckBoxYLog.setName("jCheckBoxYLog"); // NOI18N

        jCheckBoxColorLog.setText(resourceMap.getString("jCheckBoxColorLog.text")); // NOI18N
        jCheckBoxColorLog.setName("jCheckBoxColorLog"); // NOI18N

        jCheckBoxVectorLog.setText(resourceMap.getString("jCheckBoxVectorLog.text")); // NOI18N
        jCheckBoxVectorLog.setName("jCheckBoxVectorLog"); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jCheckBoxXLog)
                .addGap(18, 18, 18)
                .addComponent(jCheckBoxYLog)
                .addGap(18, 18, 18)
                .addComponent(jCheckBoxColorLog)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBoxVectorLog)
                .addGap(0, 194, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBoxXLog)
                    .addComponent(jCheckBoxYLog)
                    .addComponent(jCheckBoxColorLog)
                    .addComponent(jCheckBoxVectorLog))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonSavePicture)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonLoad)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonOK)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonFitZoom)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonCancel)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanelScale, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 12, Short.MAX_VALUE))))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButtonLoad, jButtonSave, jButtonSavePicture});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jPanel1, jPanelScale});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jPanel2, jPanel3});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelScale, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonCancel)
                    .addComponent(jButtonFitZoom)
                    .addComponent(jButtonOK)
                    .addComponent(jButtonSavePicture)
                    .addComponent(jButtonSave)
                    .addComponent(jButtonLoad))
                .addGap(10, 10, 10))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButtonCancel, jButtonFitZoom, jButtonLoad, jButtonOK, jButtonSave, jButtonSavePicture});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jPanel3, jPanelScale});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jPanel1, jPanel2});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonFitZoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFitZoomActionPerformed
        setCartesianPlaneStatusFromFields();
        
        cecPlotDisplay.getFunctionDrawer().getCartesianPlane().zoomFit(cecPlotDisplay.getDrawings(), 
                                                                            jCheckBoxFitX.isSelected(), 
                                                                            jCheckBoxFitY.isSelected(), 
                                                                            jCheckBoxFitColor.isSelected(), 
                                                                            jCheckBoxFitVector.isSelected(),
                                                                            jCheckBoxFitXTick.isSelected(),
                                                                            jCheckBoxFitYTick.isSelected());
        
        setCartesianPlaneStatusIntoFields(false);
        
    }//GEN-LAST:event_jButtonFitZoomActionPerformed

    private void setCartesianPlaneStatusFromFields() {
        CartesianPlane cp = cecPlotDisplay.getFunctionDrawer().getCartesianPlane();
        
        cp.setxMax(Double.parseDouble(jTextFieldXMax.getText()));
        cp.setxMin(Double.parseDouble(jTextFieldXMin.getText()));
        
<<<<<<< OURS
            if (up || down) shifted=Math.round(t);
            else shifted = (long) t;
        }
        return shifted/magnitude;
    }
       
=======
        cp.setyMax(Double.parseDouble(jTextFieldYMax.getText()));
        cp.setyMin(Double.parseDouble(jTextFieldYMin.getText()));
        
        cp.setcMax(Double.parseDouble(jTextFieldCMax.getText()));
        cp.setcMin(Double.parseDouble(jTextFieldCMin.getText()));
        
        cp.setnVect(Integer.parseInt(jTextFieldVectNumb.getText()));
        cp.setMaxVect(Double.parseDouble(jTextFieldVectMax.getText()));
        
        cp.setxTick(Double.parseDouble(jTextFieldXTick.getText()));
        cp.setyTick(Double.parseDouble(jTextFieldYTick.getText()));
        
        cp.setxLog(jCheckBoxXLog.isSelected()); 
        cp.setyLog(jCheckBoxYLog.isSelected());
        cp.setvLog(jCheckBoxVectorLog.isSelected());
        cp.setcLog(jCheckBoxColorLog.isSelected());        
        
    }
    
    public void setCartesianPlaneStatusIntoFields() {
        setCartesianPlaneStatusIntoFields (true);
    }
    
    private void setCartesianPlaneStatusIntoFields(boolean allFields) {
        CartesianPlane cp = cecPlotDisplay.getFunctionDrawer().getCartesianPlane();
        
        if (allFields || jCheckBoxFitX.isSelected()) {
            jTextFieldXMax.setText(setSignificantDigits(cp.getXMax()));
            jTextFieldXMin.setText(setSignificantDigits(cp.getXMin()));
        }
        
        if (allFields || jCheckBoxFitY.isSelected()) {
            jTextFieldYMax.setText(setSignificantDigits(cp.getYMax()));
            jTextFieldYMin.setText(setSignificantDigits(cp.getYMin()));
        }
        
        if (allFields || jCheckBoxFitColor.isSelected()) {
            jTextFieldCMax.setText(setSignificantDigits(cp.getCMax()));
            jTextFieldCMin.setText(setSignificantDigits(cp.getCMin()));
        }
        
        if (allFields || jCheckBoxFitColor.isSelected()) {
            jTextFieldVectMax.setText(setSignificantDigits(cp.getVectMax()));
            jTextFieldVectNumb.setText(Integer.toString(cp.getVectNum()));
        }
        
        if (allFields || jCheckBoxFitXTick.isSelected()) {
            jTextFieldXTick.setText(setSignificantDigits(cp.getXTick()));
        }
        
        if (allFields || jCheckBoxFitYTick.isSelected()) {
            jTextFieldYTick.setText(Double.toString(cp.getYTick()));
        }
        
        if (allFields) {
            jCheckBoxAxes.setSelected(cp.isShowAxes());
            jCheckBoxGrid.setSelected(cp.isShowGrid());
            jCheckBoxDrawLabels.setSelected(cp.isShowDrawLabels());
            jCheckBoxTickLabels.setSelected(cp.isShowTickLabels());
            jComboBoxAxesColor.setSelectedIndex(cp.getAxesCI());
            jComboBoxBackgroundColor.setSelectedIndex(cp.getBackgroundCI());
            jComboBoxGridColor.setSelectedIndex(cp.getGridCI());
            jCheckBoxXLog.setSelected(cp.isxLog());
            jCheckBoxYLog.setSelected(cp.isyLog());
            jCheckBoxVectorLog.setSelected(cp.isvLog());
            jCheckBoxColorLog.setSelected(cp.iscLog());
        }
    }
    
>>>>>>> THEIRS
    private String setSignificantDigits(double val) {
        if (val != 0) {
            final double s = Math.pow(10, - SIGNIFICANTDIGITS + (int) Math.log10(Math.abs(val)));
            val =  (Math.round(val / s / QUANTIZATION)) * s * QUANTIZATION;
        }
        return setSignificantDigits (val, SIGNIFICANTDIGITS);
    }

    private final static DecimalFormat EXPONENTIAL = new DecimalFormat ("0.##E0", new DecimalFormatSymbols (Locale.ENGLISH));
    private final static DecimalFormat DECIMAL = new DecimalFormat ("#.##", new DecimalFormatSymbols (Locale.ENGLISH));

    public static String setSignificantDigits (double val, int significantDigits) {
        if (val == 0.) return "0";
        double abs = Math.abs(val);
        if (abs < 99 && abs > 0.01) return DECIMAL.format(val);
        return EXPONENTIAL.format(val);
    }

    private void jComboBoxAxesColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxAxesColorActionPerformed
        jPanelAxesColor.setBackground(EngCalculatorConsolePlotDisplay.colorConverter(jComboBoxAxesColor.getSelectedIndex()));
}//GEN-LAST:event_jComboBoxAxesColorActionPerformed

    private void jComboBoxGridColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxGridColorActionPerformed
        jPanelGridColor.setBackground(EngCalculatorConsolePlotDisplay.colorConverter(jComboBoxGridColor.getSelectedIndex()));
    }//GEN-LAST:event_jComboBoxGridColorActionPerformed

    private void jComboBoxBackgroundColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxBackgroundColorActionPerformed
        jPanelBackgroundColor.setBackground(EngCalculatorConsolePlotDisplay.colorConverter(jComboBoxBackgroundColor.getSelectedIndex()));
    }//GEN-LAST:event_jComboBoxBackgroundColorActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        setVisible(false);
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jButtonOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOKActionPerformed
        if (storeDataOnCanvas()) {
            jButtonCancelActionPerformed(null);
        }
    }//GEN-LAST:event_jButtonOKActionPerformed

    private void jButtonSavePictureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSavePictureActionPerformed
        File workingFile = findSaveFile(FNEF_PNG);

        savePictureFile (workingFile);
    }//GEN-LAST:event_jButtonSavePictureActionPerformed

    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveActionPerformed
        File workingFile = findSaveFile(FNEF_IPO);

        saveFile (workingFile);
    }//GEN-LAST:event_jButtonSaveActionPerformed

    private void jButtonLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLoadActionPerformed
        File workingFile = findOpenFile(FNEF_IPO);

        loadFile (workingFile);
    }//GEN-LAST:event_jButtonLoadActionPerformed

    public boolean storeDataOnCanvas() {
        try {
            cecPlotDisplay.getFunctionDrawer().setCartesianPlane(new CartesianPlane(Double.parseDouble(jTextFieldXMin.getText()), Double.parseDouble(jTextFieldXMax.getText()), Double.parseDouble(jTextFieldYMin.getText()), Double.parseDouble(jTextFieldYMax.getText()), Double.parseDouble(jTextFieldCMin.getText()), Double.parseDouble(jTextFieldCMax.getText()), Integer.parseInt(jTextFieldVectNumb.getText()), Double.parseDouble(jTextFieldVectMax.getText()), Double.parseDouble(jTextFieldXTick.getText()), Double.parseDouble(jTextFieldYTick.getText()), jCheckBoxAxes.isSelected(), jCheckBoxGrid.isSelected(), jCheckBoxTickLabels.isSelected(), jCheckBoxDrawLabels.isSelected(), jComboBoxBackgroundColor.getSelectedIndex(), jComboBoxAxesColor.getSelectedIndex(), jComboBoxGridColor.getSelectedIndex(), jCheckBoxXLog.isSelected(), jCheckBoxYLog.isSelected(), jCheckBoxColorLog.isSelected(), jCheckBoxVectorLog.isSelected()));
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonFitZoom;
    private javax.swing.JButton jButtonLoad;
    private javax.swing.JButton jButtonOK;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JButton jButtonSavePicture;
    private javax.swing.JCheckBox jCheckBoxAxes;
    private javax.swing.JCheckBox jCheckBoxColorLog;
    private javax.swing.JCheckBox jCheckBoxDrawLabels;
    private javax.swing.JCheckBox jCheckBoxFitColor;
    private javax.swing.JCheckBox jCheckBoxFitVector;
    private javax.swing.JCheckBox jCheckBoxFitX;
    private javax.swing.JCheckBox jCheckBoxFitXTick;
    private javax.swing.JCheckBox jCheckBoxFitY;
    private javax.swing.JCheckBox jCheckBoxFitYTick;
    private javax.swing.JCheckBox jCheckBoxGrid;
    private javax.swing.JCheckBox jCheckBoxTickLabels;
    private javax.swing.JCheckBox jCheckBoxVectorLog;
    private javax.swing.JCheckBox jCheckBoxXLog;
    private javax.swing.JCheckBox jCheckBoxYLog;
    private javax.swing.JComboBox<String> jComboBoxAxesColor;
    private javax.swing.JComboBox<String> jComboBoxBackgroundColor;
    private javax.swing.JComboBox<String> jComboBoxGridColor;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanelAxesColor;
    private javax.swing.JPanel jPanelBackgroundColor;
    private javax.swing.JPanel jPanelGridColor;
    private javax.swing.JPanel jPanelScale;
    private javax.swing.JTextField jTextFieldCMax;
    private javax.swing.JTextField jTextFieldCMin;
    private javax.swing.JTextField jTextFieldVectMax;
    private javax.swing.JTextField jTextFieldVectNumb;
    private javax.swing.JTextField jTextFieldXMax;
    private javax.swing.JTextField jTextFieldXMin;
    private javax.swing.JTextField jTextFieldXTick;
    private javax.swing.JTextField jTextFieldYMax;
    private javax.swing.JTextField jTextFieldYMin;
    private javax.swing.JTextField jTextFieldYTick;
    // End of variables declaration//GEN-END:variables
    private final JFileChooser jFileChooser;
    private final static FileNameExtensionFilter FNEF_PNG = new FileNameExtensionFilter("Portable Network Graphics Picture", "png");
    private final static FileNameExtensionFilter FNEF_IPO = new FileNameExtensionFilter("ICE plot options file", "ipo");

    public void fitDrawing() {
        jButtonFitZoomActionPerformed(null);
        jButtonOKActionPerformed(null);
    }

    private void setCurrentCanonicalPath () {
        File workingFile = cecPlotDisplay.getConsoleEngCalculatorMainFrame().getWorkingFile();
        if (workingFile != null) {
            String path = null;
            try {
                path = workingFile.getCanonicalPath();
            } catch (IOException ex) {
                //nothing to do
            }
            if (path != null) {
                int ext = path.indexOf(".ice");
                if (ext != -1) path = path.substring(0, ext);
                workingFile = new File (path);
                jFileChooser.setSelectedFile(workingFile);
            }
        } 
    }
    
    private File findSaveFile(FileNameExtensionFilter fnef) {
        jFileChooser.addChoosableFileFilter(fnef);
        jFileChooser.setFileFilter(fnef);
        
        setCurrentCanonicalPath();        

        int res = jFileChooser.showSaveDialog(this);
        if (res == JFileChooser.APPROVE_OPTION) {
            File workingFile = jFileChooser.getSelectedFile();
            try {
                workingFile = checkExtension(workingFile, fnef);
            } catch (IOException ex) {
                //JOptionPane.showMessageDialog(this, "It was not possible to save the file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            if (workingFile.exists()) {
                if (JOptionPane.NO_OPTION == JOptionPane.showConfirmDialog(this, "Are you sure? A file with the same name exists and will be overwritten.", "Warning", JOptionPane.YES_NO_OPTION)) {
                    return null;
                } else {
                    workingFile.delete();
                }
            }

            return workingFile;
        }
        return null;
    }

    public File checkExtension (File workingFile, FileNameExtensionFilter fnef) throws IOException {
        if (!fnef.accept(workingFile)) return new File(workingFile.getCanonicalPath() + "." + fnef.getExtensions()[0]);
        return workingFile;
    }

    private File findOpenFile(FileNameExtensionFilter fnef) {
        jFileChooser.addChoosableFileFilter(fnef);
        jFileChooser.setFileFilter(fnef);
        
        setCurrentCanonicalPath();
        
        int res = jFileChooser.showOpenDialog(this);
        if (res == JFileChooser.APPROVE_OPTION) {
            File workingFile = jFileChooser.getSelectedFile();
            try {
                workingFile = checkExtension(workingFile, fnef);
            } catch (IOException ex) {                
                return null;
            }
            return workingFile;
        }
        return null;
    }
    
    private void savePlotOptions(File workingFile) throws IOException {
        if (workingFile.exists()) workingFile.delete();
        workingFile.createNewFile();

        FileOutputStream fos = new FileOutputStream(workingFile);
        OutputTextData otd = new OutputTextData(fos);
        cecPlotDisplay.getFunctionDrawer().getCartesianPlane().saveToFileStream(otd);
        saveToFileStream(otd);
        cecPlotDisplay.saveToFileStream(otd);

        otd.close();
        fos.close();
    }

    private void loadPlotOptions(File workingFile) throws IOException {
        FileInputStream fis = new FileInputStream(workingFile);
        InputTextData itd = new InputTextData (fis);

        cecPlotDisplay.getFunctionDrawer().getCartesianPlane().loadFromFileStream(itd);
        setCartesianPlaneStatus();
        
        loadFromFileStream(itd);
        cecPlotDisplay.loadFromFileStream(itd);

        itd.close();
        fis.close();
    }

    @Override
    public void saveToFileStream(OutputTextData otd) throws IOException {
        otd.writelnComment("Console Eng Calculator Plot Option Save Data");
        otd.writeln("jCheckBoxFitX", jCheckBoxFitX.isSelected());
        otd.writeln("jCheckBoxFitY", jCheckBoxFitY.isSelected());
        otd.writeln("jCheckBoxFitColor", jCheckBoxFitColor.isSelected());
        otd.writeln("jCheckBoxFitTick", jCheckBoxFitXTick.isSelected());
        otd.writeln("jCheckBoxFitYTick", jCheckBoxFitYTick.isSelected());
        otd.writeln("jCheckBoxFitVector", jCheckBoxFitVector.isSelected());
    }

    @Override
    public void loadFromFileStream(InputTextData itd) throws IOException {
        jCheckBoxFitX.setSelected(itd.readBoolean("jCheckBoxFitX", true));
        jCheckBoxFitY.setSelected(itd.readBoolean("jCheckBoxFitY", true));
        jCheckBoxFitColor.setSelected(itd.readBoolean("jCheckBoxFitColor", true));
        jCheckBoxFitXTick.setSelected(itd.readBoolean("jCheckBoxFitTick", true));
        jCheckBoxFitYTick.setSelected(itd.readBoolean("jCheckBoxFitYTick", true));
        jCheckBoxFitVector.setSelected(itd.readBoolean("jCheckBoxFitVector", true));
    }

    public void setCartesianPlaneStatus() {
        CartesianPlane cp = cecPlotDisplay.getFunctionDrawer().getCartesianPlane();
        jTextFieldXMax.setText(Double.toString(cp.getXMax()));
        jTextFieldXMin.setText(Double.toString(cp.getXMin()));
        jTextFieldYMax.setText(Double.toString(cp.getYMax()));
        jTextFieldYMin.setText(Double.toString(cp.getYMin()));
        jTextFieldCMax.setText(Double.toString(cp.getCMax()));
        jTextFieldCMin.setText(Double.toString(cp.getCMin()));
        jTextFieldVectMax.setText(Double.toString(cp.getMaxVect()));
        jTextFieldVectNumb.setText(Integer.toString(cp.getnVect()));
        jTextFieldXTick.setText(Double.toString(cp.getXTick()));
        jTextFieldYTick.setText(Double.toString(cp.getYTick()));
        jCheckBoxAxes.setSelected(cp.isShowAxes());
        jCheckBoxGrid.setSelected(cp.isShowGrid());
        jCheckBoxDrawLabels.setSelected(cp.isShowDrawLabels());
        jCheckBoxTickLabels.setSelected(cp.isShowTickLabels());
        jComboBoxAxesColor.setSelectedIndex(cp.getAxesCI());
        jComboBoxBackgroundColor.setSelectedIndex(cp.getBackgroundCI());
        jComboBoxGridColor.setSelectedIndex(cp.getGridCI());
        jCheckBoxXLog.setSelected(cp.isxLog());
        jCheckBoxYLog.setSelected(cp.isyLog());
        jCheckBoxVectorLog.setSelected(cp.isvLog());
        jCheckBoxColorLog.setSelected(cp.iscLog());
    }

    private void saveFile(File workingFile) {
        if (workingFile != null) {
            try {
                savePlotOptions(workingFile);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "It was not possible to save the file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadFile(File workingFile) {
        if (workingFile != null) {
            try {
                loadPlotOptions(workingFile);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "It was not possible to load the file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void savePictureFile(File workingFile) {
        if (workingFile != null) {
            try {
                cecPlotDisplay.savePicture(workingFile);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "It was not possible to save the file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
<<<<<<< OURS
        setCartesianPlaneStatus();
=======
        setCartesianPlaneStatusIntoFields();
>>>>>>> THEIRS
