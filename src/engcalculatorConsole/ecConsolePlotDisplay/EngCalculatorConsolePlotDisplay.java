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

package engcalculatorConsole.ecConsolePlotDisplay;

/**
 *
 * @author Simone Pernice <pernice@libero.it>
 */

import engcalculatorConsole.iceJPanel.SaveLoadToTextData;
import engcalculatorConsole.iceJPanel.OutputTextData;
import engcalculatorConsole.iceJPanel.InputTextData;
import engcalculatorConsole.iceJPanel.CartesianPlane;
import engcalculatorConsole.iceJPanel.ICEJPanel;
import engcalculator.interval.IntervalPoint;
import engcalculator.function.variable.FunctionVariable;
import engcalculatorConsole.EngCalculatorConsoleMainFrame;
import engcalculatorConsole.ecConsolePlotDisplay.ecPlotOption.EngCalculatorConsolePlotOptions;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import javax.imageio.ImageIO;

public final class EngCalculatorConsolePlotDisplay extends javax.swing.JFrame  implements SaveLoadToTextData {
    public final static int COLOR_SET_BY_VAL = 13;
    public final static int COLOR_SET_BY_TOL = 14;

    public static boolean isColorSetByVal (int index) {
        return index == 13;
    }
    public static boolean isColorSetByTol (int index) {
        return index == 14;
    }

    public static Color colorConverter (int index) {
        switch (index) {
            case 0:
                return Color.BLACK;
            case 1:
                return Color.BLUE;
            case 2:
                return Color.CYAN;
            case 3:
                return Color.DARK_GRAY;
            case 4:
                return Color.GRAY;
            case 5:
                return Color.GREEN;
            case 6:
                return Color.LIGHT_GRAY;
            case 7:
                return Color.MAGENTA;
            case 8:
                return Color.ORANGE;
            case 9:
                return Color.PINK;
            case 10:
                return Color.RED;
            case 11:
                return Color.WHITE;
            case 12:
                return Color.YELLOW;
            default:
                //it was selected variable color
                return Color.WHITE;
        }
    }

    private final EngCalculatorConsolePlotOptions drawOptions;
    private final LinkedList<Drawing> drawings;
    private EngCalculatorConsoleMainFrame consoleEngCalculatorMainFrame;

    public EngCalculatorConsolePlotDisplay(EngCalculatorConsoleMainFrame consoleEngCalculatorMainFrame) {
        this.consoleEngCalculatorMainFrame = consoleEngCalculatorMainFrame;

        setLocationRelativeTo(consoleEngCalculatorMainFrame.getFrame());
        
        try {
            setIconImage(ImageIO.read(getClass().getResourceAsStream("resources/icon.png")));
        } catch (Exception ex) {
            //It was not possible to set the proper icon...
        }
        initComponents();
        
        jButtonNextVisible.setMnemonic(KeyEvent.VK_D);
        jButtonPrevVisible.setMnemonic(KeyEvent.VK_U);
        jButtonOptions.setMnemonic(KeyEvent.VK_O);
        jButtonFit.setMnemonic(KeyEvent.VK_F);        
        
        getRootPane().setDefaultButton(jButtonCancel);
        drawings = new LinkedList<Drawing> ();
        
        drawOptions = new EngCalculatorConsolePlotOptions(this);
        drawOptions.storeDataOnCanvas();

    }

    public EngCalculatorConsoleMainFrame getConsoleEngCalculatorMainFrame () {
        return consoleEngCalculatorMainFrame;
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelCartesian = new engcalculatorConsole.iceJPanel.ICEJPanel();
        jPanelDrawing = new javax.swing.JPanel();
        jPanelDrawingSelection = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jComboBoxDrawing = new javax.swing.JComboBox<Drawing>();
        jButtonPrevVisible = new javax.swing.JButton();
        jButtonNextVisible = new javax.swing.JButton();
        jPanelDrawingAttributes = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jComboBoxPoint = new javax.swing.JComboBox<String>();
        jLabel3 = new javax.swing.JLabel();
        jComboBoxColor = new javax.swing.JComboBox<String>();
        jPanelColor = new engcalculatorConsole.ecConsolePlotDisplay.GradientJPanel ();
        jPanelPoint = new engcalculatorConsole.ecConsolePlotDisplay.PointTypeJPanel ();
        jPanel1 = new javax.swing.JPanel();
        jCheckBoxVisible = new javax.swing.JCheckBox();
        jCheckBoxJoined = new javax.swing.JCheckBox();
        jCheckBoxShowLabel = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jButtonFit = new javax.swing.JButton();
        jButtonOptions = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(engcalculatorConsole.EngCalculatorConsoleApp.class).getContext().getResourceMap(EngCalculatorConsolePlotDisplay.class);
        setTitle(resourceMap.getString("title")); // NOI18N
        setName("Variable Shower"); // NOI18N
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jPanelCartesian.setBackground(resourceMap.getColor("jPanelCartesianPlane.background")); // NOI18N
        jPanelCartesian.setName("jPanelCartesianPlane"); // NOI18N
        jPanelCartesian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanelCartesianMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jPanelCartesianMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanelCartesianLayout = new javax.swing.GroupLayout(jPanelCartesian);
        jPanelCartesian.setLayout(jPanelCartesianLayout);
        jPanelCartesianLayout.setHorizontalGroup(
            jPanelCartesianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanelCartesianLayout.setVerticalGroup(
            jPanelCartesianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 451, Short.MAX_VALUE)
        );

        jPanelDrawing.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelDrawing.setName("jPanelDrawing"); // NOI18N

        jPanelDrawingSelection.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelDrawingSelection.setName("jPanelDrawingSelection"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jComboBoxDrawing.setName("jComboBoxDrawing"); // NOI18N
        jComboBoxDrawing.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxDrawingActionPerformed(evt);
            }
        });

        jButtonPrevVisible.setMnemonic('U');
        jButtonPrevVisible.setText(resourceMap.getString("jButtonPrevVisible.text")); // NOI18N
        jButtonPrevVisible.setToolTipText(resourceMap.getString("jButtonPrevVisible.toolTipText")); // NOI18N
        jButtonPrevVisible.setName("jButtonPrevVisible"); // NOI18N
        jButtonPrevVisible.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPrevVisibleActionPerformed(evt);
            }
        });

        jButtonNextVisible.setMnemonic('D');
        jButtonNextVisible.setText(resourceMap.getString("jButtonNextVisible.text")); // NOI18N
        jButtonNextVisible.setName("jButtonNextVisible"); // NOI18N
        jButtonNextVisible.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNextVisibleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelDrawingSelectionLayout = new javax.swing.GroupLayout(jPanelDrawingSelection);
        jPanelDrawingSelection.setLayout(jPanelDrawingSelectionLayout);
        jPanelDrawingSelectionLayout.setHorizontalGroup(
            jPanelDrawingSelectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDrawingSelectionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDrawingSelectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDrawingSelectionLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(6, 6, 6)
                        .addComponent(jComboBoxDrawing, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanelDrawingSelectionLayout.createSequentialGroup()
                        .addComponent(jButtonPrevVisible)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                        .addComponent(jButtonNextVisible)))
                .addGap(6, 6, 6))
        );
        jPanelDrawingSelectionLayout.setVerticalGroup(
            jPanelDrawingSelectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDrawingSelectionLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelDrawingSelectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxDrawing, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelDrawingSelectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonNextVisible)
                    .addComponent(jButtonPrevVisible))
                .addContainerGap())
        );

        jPanelDrawingSelectionLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButtonNextVisible, jButtonPrevVisible, jComboBoxDrawing, jLabel5});

        jPanelDrawingAttributes.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelDrawingAttributes.setName("jPanelDrawingAttributes"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jComboBoxPoint.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] { "NONE", "PLUS", "CROSS", "SQUARE", "DIAMOND", "CIRCLE", "TRIANGLE" }));
        jComboBoxPoint.setName("jComboBoxPoint"); // NOI18N
        jComboBoxPoint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxPointActionPerformed(evt);
            }
        });

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jComboBoxColor.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] { "BLACK", "BLUE", "CYAN", "DARK_GRAY", "GRAY", "GREEN", "LIGHT_GRAY", "MAGENTA", "ORANGE", "PINK", "RED", "WHITE", "YELLOW", "Set by value", "Set by tolerance" }));
        jComboBoxColor.setName("jComboBoxColor"); // NOI18N
        jComboBoxColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxColorActionPerformed(evt);
            }
        });

        jPanelColor.setBackground(resourceMap.getColor("jPanelColor.background")); // NOI18N
        jPanelColor.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelColor.setName("jPanelColor"); // NOI18N

        javax.swing.GroupLayout jPanelColorLayout = new javax.swing.GroupLayout(jPanelColor);
        jPanelColor.setLayout(jPanelColorLayout);
        jPanelColorLayout.setHorizontalGroup(
            jPanelColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 49, Short.MAX_VALUE)
        );
        jPanelColorLayout.setVerticalGroup(
            jPanelColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        jPanelPoint.setBackground(resourceMap.getColor("jPanelPoint.background")); // NOI18N
        jPanelPoint.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelPoint.setName("jPanelPoint"); // NOI18N

        javax.swing.GroupLayout jPanelPointLayout = new javax.swing.GroupLayout(jPanelPoint);
        jPanelPoint.setLayout(jPanelPointLayout);
        jPanelPointLayout.setHorizontalGroup(
            jPanelPointLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 49, Short.MAX_VALUE)
        );
        jPanelPointLayout.setVerticalGroup(
            jPanelPointLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanelDrawingAttributesLayout = new javax.swing.GroupLayout(jPanelDrawingAttributes);
        jPanelDrawingAttributes.setLayout(jPanelDrawingAttributesLayout);
        jPanelDrawingAttributesLayout.setHorizontalGroup(
            jPanelDrawingAttributesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDrawingAttributesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelDrawingAttributesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDrawingAttributesLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDrawingAttributesLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxPoint, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12)
                .addGroup(jPanelDrawingAttributesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanelPoint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelColor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanelDrawingAttributesLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel3, jLabel4});

        jPanelDrawingAttributesLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jComboBoxColor, jComboBoxPoint});

        jPanelDrawingAttributesLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jPanelColor, jPanelPoint});

        jPanelDrawingAttributesLayout.setVerticalGroup(
            jPanelDrawingAttributesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDrawingAttributesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDrawingAttributesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDrawingAttributesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jComboBoxColor, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelDrawingAttributesLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanelColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanelDrawingAttributesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelDrawingAttributesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBoxPoint, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanelPoint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanelDrawingAttributesLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jComboBoxColor, jComboBoxPoint, jLabel3, jLabel4, jPanelColor, jPanelPoint});

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setName("jPanel1"); // NOI18N

        jCheckBoxVisible.setText(resourceMap.getString("jCheckBoxVisible.text")); // NOI18N
        jCheckBoxVisible.setName("jCheckBoxVisible"); // NOI18N
        jCheckBoxVisible.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxVisibleActionPerformed(evt);
            }
        });

        jCheckBoxJoined.setText(resourceMap.getString("jCheckBoxJoined.text")); // NOI18N
        jCheckBoxJoined.setName("jCheckBoxJoined"); // NOI18N
        jCheckBoxJoined.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxJoinedActionPerformed(evt);
            }
        });

        jCheckBoxShowLabel.setText(resourceMap.getString("jCheckBoxShowLabel.text")); // NOI18N
        jCheckBoxShowLabel.setToolTipText(resourceMap.getString("jCheckBoxShowLabel.toolTipText")); // NOI18N
        jCheckBoxShowLabel.setName("jCheckBoxShowLabel"); // NOI18N
        jCheckBoxShowLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxShowLabelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBoxShowLabel)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jCheckBoxVisible)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBoxJoined)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBoxVisible)
                    .addComponent(jCheckBoxJoined))
                .addGap(18, 18, 18)
                .addComponent(jCheckBoxShowLabel)
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jCheckBoxJoined, jCheckBoxShowLabel, jCheckBoxVisible});

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setName("jPanel2"); // NOI18N

        jButtonFit.setMnemonic('F');
        jButtonFit.setText(resourceMap.getString("jButtonFit.text")); // NOI18N
        jButtonFit.setName("jButtonFit"); // NOI18N
        jButtonFit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFitActionPerformed(evt);
            }
        });

        jButtonOptions.setMnemonic('O');
        jButtonOptions.setText(resourceMap.getString("jButtonOptions.text")); // NOI18N
        jButtonOptions.setName("jButtonOptions"); // NOI18N
        jButtonOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOptionsActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jButtonFit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonOptions))
                    .addComponent(jButtonCancel, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButtonCancel, jButtonOptions});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonOptions)
                    .addComponent(jButtonFit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonCancel)
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButtonCancel, jButtonFit, jButtonOptions});

        javax.swing.GroupLayout jPanelDrawingLayout = new javax.swing.GroupLayout(jPanelDrawing);
        jPanelDrawing.setLayout(jPanelDrawingLayout);
        jPanelDrawingLayout.setHorizontalGroup(
            jPanelDrawingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDrawingLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelDrawingSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelDrawingAttributes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanelDrawingLayout.setVerticalGroup(
            jPanelDrawingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDrawingLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDrawingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelDrawingAttributes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelDrawingSelection, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(8, 8, 8))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelCartesian, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelDrawing, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelCartesian, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelDrawing, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBoxColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxColorActionPerformed
        int color = jComboBoxColor.getSelectedIndex();
        jPanelColor.setBackground(colorConverter(color));
        if (isColorSetByTol(color) || isColorSetByVal(color)) ((GradientJPanel) jPanelColor).setShowGradient(true);
        else ((GradientJPanel) jPanelColor).setShowGradient(false);
        Drawing d = ((Drawing) jComboBoxDrawing.getSelectedItem());
        if (d != null) {
            d.setColorIndex(color);
            redraw ();
        }
    }//GEN-LAST:event_jComboBoxColorActionPerformed

    private void jComboBoxDrawingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxDrawingActionPerformed
        if (jComboBoxDrawing.getSelectedIndex() == -1) return;
        Drawing d = (Drawing) jComboBoxDrawing.getSelectedItem();
        if (d != null) {
            jComboBoxColor.setSelectedIndex(d.getColorIndex());            
            jCheckBoxJoined.setSelected(d.isJoined());          
            jCheckBoxVisible.setSelected(d.isVisible());            
            jComboBoxPoint.setSelectedIndex(d.getPointType());            
            jCheckBoxShowLabel.setSelected (d.isShowLabel());
            
        }
}//GEN-LAST:event_jComboBoxDrawingActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        setupDrawings();
        if (jComboBoxDrawing.getItemCount() > 0) {
            int i = 0;
            while (i < jComboBoxDrawing.getItemCount() && ! ((Drawing) jComboBoxDrawing.getItemAt(i)).isVisible()) ++i;
            if (i >= jComboBoxDrawing.getItemCount()) jComboBoxDrawing.setSelectedIndex(0);
            else jComboBoxDrawing.setSelectedIndex(i);
        }
    }//GEN-LAST:event_formWindowActivated
    
    private void redraw () {
       ((ICEJPanel) jPanelCartesian).setDrawings(drawings);
        jPanelCartesian.repaint();
    }

    private void jButtonOptionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOptionsActionPerformed
        drawOptions.setCartesianPlaneStatus ();
        drawOptions.setVisible(true);
    }//GEN-LAST:event_jButtonOptionsActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        setVisible(false);
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jCheckBoxVisibleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxVisibleActionPerformed
        Drawing d = ((Drawing) jComboBoxDrawing.getSelectedItem());
        if (d != null) {
            d.setVisible(jCheckBoxVisible.isSelected());
            redraw();
        }
    }//GEN-LAST:event_jCheckBoxVisibleActionPerformed

    private void jCheckBoxJoinedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxJoinedActionPerformed
        Drawing d = ((Drawing) jComboBoxDrawing.getSelectedItem());
        if (d != null) {
            d.setJoined(jCheckBoxJoined.isSelected());
            redraw();
        }
    }//GEN-LAST:event_jCheckBoxJoinedActionPerformed

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        jPanelCartesian.repaint();
    }//GEN-LAST:event_formWindowGainedFocus

    private void jButtonPrevVisibleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrevVisibleActionPerformed
        int i = jComboBoxDrawing.getSelectedIndex();
        while (--i >= 0)
            if (((Drawing) jComboBoxDrawing.getItemAt(i)).isVisible()) break;
        if (i < 0) {//cyclic iteration restarting from last element
            i = jComboBoxDrawing.getItemCount();
            while (--i >= 0)
                if (((Drawing) jComboBoxDrawing.getItemAt(i)).isVisible()) break;            
        }
        if (i >= 0) jComboBoxDrawing.setSelectedIndex(i);
    }//GEN-LAST:event_jButtonPrevVisibleActionPerformed

    private void jButtonNextVisibleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNextVisibleActionPerformed
        int i = jComboBoxDrawing.getSelectedIndex();
        while (++i < jComboBoxDrawing.getItemCount())
            if (((Drawing) jComboBoxDrawing.getItemAt(i)).isVisible()) break;
        if (i >= jComboBoxDrawing.getItemCount()) {//cyclic iteration restarting from last element
            i = -1;
            while (++i < jComboBoxDrawing.getItemCount())
                if (((Drawing) jComboBoxDrawing.getItemAt(i)).isVisible()) break;            
        }        
        if (i < jComboBoxDrawing.getItemCount()) jComboBoxDrawing.setSelectedIndex(i);
    }//GEN-LAST:event_jButtonNextVisibleActionPerformed

    private Point firstZoomPoint = null;
    private void jPanelCartesianMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelCartesianMousePressed
        firstZoomPoint = evt.getPoint();
    }//GEN-LAST:event_jPanelCartesianMousePressed

    private void jPanelCartesianMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelCartesianMouseReleased
        if (firstZoomPoint == null) return;

        CartesianPlane cp =  ((ICEJPanel) jPanelCartesian).getCartesianPlane();

        Point secondZoomPoint = evt.getPoint();

        if ((evt.getModifiersEx() & MouseEvent.CTRL_DOWN_MASK) == MouseEvent.CTRL_DOWN_MASK) {
            cp.setPlaneZoomRectFromWindowsData (firstZoomPoint.getX(), firstZoomPoint.getY(), secondZoomPoint.getX(), secondZoomPoint.getY());
        } else if ((evt.getModifiersEx() & MouseEvent.SHIFT_DOWN_MASK) == MouseEvent.SHIFT_DOWN_MASK) {
            for (Drawing d : drawings) {                
                if (d.getName().equals("plotFunctionPointsByMouseClick")) {
                    if (evt.getButton() == MouseEvent.BUTTON1) {
                        d.getData().getValueToModifiy().add(new IntervalPoint(cp.transformXfromPXtoCP(firstZoomPoint.getX())));
                        d.getData().getValueToModifiy().add(new IntervalPoint(cp.transformYfromPXtoCP(firstZoomPoint.getY())));
                    } else if (evt.getButton() == MouseEvent.BUTTON2) {
                        if (d.getData().getValueToModifiy().size() >= 2) {
                            d.getData().getValueToModifiy().pop();
                            d.getData().getValueToModifiy().pop();
                        }
                    } else if (evt.getButton() == MouseEvent.BUTTON3) {
                        if (d.getData().getValueToModifiy().size() >= 2) {
                            d.getData().getValueToModifiy().removeLast();
                            d.getData().getValueToModifiy().removeLast();
                        }
                    }
                    
                    if (!d.isVisible()) {
                        d.setVisible(true);
                        d.setPointType(1);
                        d.setJoined(false);
                        jComboBoxDrawing.setSelectedItem(d);
                    } 
                    
                    jPanelCartesian.repaint();
                    
                    return; //this is to skip the following instructions to report points back
                }
            }
            String point = new StringBuffer ("[").append(cp.transformXfromPXtoCP(firstZoomPoint.getX())).append(", ").append(cp.transformYfromPXtoCP(firstZoomPoint.getY())).append("], ").toString();
            point = point.replace('E', 'e'); //to use the proper exponential symbol
            consoleEngCalculatorMainFrame.appendToInput(point);
        } else {
            if (evt.getButton() == MouseEvent.BUTTON1)
                cp.setPlaneZoomInFromWindowsData (firstZoomPoint.getX(), firstZoomPoint.getY(), secondZoomPoint.getX(), secondZoomPoint.getY());
            else if (evt.getButton() == MouseEvent.BUTTON2)
                cp.setPlaneDisplacementFromWindowsData (firstZoomPoint.getX(), firstZoomPoint.getY(), secondZoomPoint.getX(), secondZoomPoint.getY());
            else
                cp.setPlaneZoomOutFromWindowsData (firstZoomPoint.getX(), firstZoomPoint.getY(), secondZoomPoint.getX(), secondZoomPoint.getY());
        }

        redraw();
        firstZoomPoint = null;
    }//GEN-LAST:event_jPanelCartesianMouseReleased

    private void jButtonFitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFitActionPerformed
        drawOptions.fitDrawing();
        redraw();
    }//GEN-LAST:event_jButtonFitActionPerformed

private void jComboBoxPointActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxPointActionPerformed
        int point = jComboBoxPoint.getSelectedIndex();
        
        ((PointTypeJPanel) jPanelPoint).setPointType(point);
        
        Drawing d = ((Drawing) jComboBoxDrawing.getSelectedItem());
        if (d != null) {
            d.setPointType(point);            
        }
        
        redraw ();
}//GEN-LAST:event_jComboBoxPointActionPerformed

    private void jCheckBoxShowLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxShowLabelActionPerformed
        Drawing d = ((Drawing) jComboBoxDrawing.getSelectedItem());
        if (d != null) {
            d.setShowLabel(jCheckBoxShowLabel.isSelected());
            redraw();
        }
    }//GEN-LAST:event_jCheckBoxShowLabelActionPerformed

    public void savePicture (File outFile) throws IOException {
        BufferedImage bi = new BufferedImage(jPanelCartesian.getWidth(), jPanelCartesian.getHeight(), BufferedImage.TYPE_INT_ARGB);

        jPanelCartesian.paint(bi.createGraphics());

        ImageIO.write(bi, "PNG", outFile);
    }

    public ICEJPanel getFunctionDrawer () {
        return (ICEJPanel) jPanelCartesian;
    }

    public LinkedList<Drawing> getDrawings() {
        return drawings;
    }
    
    private void setupDrawings () {
        //adds new variables
        for (FunctionVariable var : FunctionVariable.getVariables()) {            
            if (! Drawing.isASuitableDrawing(var)) continue;
            boolean exists;
            String v;
            Drawing dra;       

            exists = false;
            v = var.getSymbol();
            for (Drawing d : drawings) 
                if (v.equals(d.getName())) {
                    exists = true;
                    break;
                }
            
            if (! exists) {
                dra = new Drawing (v);
                jComboBoxDrawing.addItem(dra);
                drawings.add(dra);
            }
        }

        //removes deleted variables
        for (int i=0;i<drawings.size(); ++i) {
            boolean exists;
            String v;
            Drawing dra;       
            
            dra = drawings.get(i);
            exists = false;
            v = dra.getName();
            for (FunctionVariable var : FunctionVariable.getVariables())
                if (v.equals(var.getSymbol())) {
                    exists = true;
                    break;
                }

            if (! exists) {
                jComboBoxDrawing.removeItemAt(i);
                drawings.remove(i);
                --i;
            }
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonFit;
    private javax.swing.JButton jButtonNextVisible;
    private javax.swing.JButton jButtonOptions;
    private javax.swing.JButton jButtonPrevVisible;
    private javax.swing.JCheckBox jCheckBoxJoined;
    private javax.swing.JCheckBox jCheckBoxShowLabel;
    private javax.swing.JCheckBox jCheckBoxVisible;
    private javax.swing.JComboBox<String> jComboBoxColor;
    private javax.swing.JComboBox<Drawing> jComboBoxDrawing;
    private javax.swing.JComboBox<String> jComboBoxPoint;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelCartesian;
    private javax.swing.JPanel jPanelColor;
    private javax.swing.JPanel jPanelDrawing;
    private javax.swing.JPanel jPanelDrawingAttributes;
    private javax.swing.JPanel jPanelDrawingSelection;
    private javax.swing.JPanel jPanelPoint;
    // End of variables declaration//GEN-END:variables

    @Override
    public void loadFromFileStream(InputTextData itd) throws IOException {
        final int size = itd.readInteger("Number of drawings", 0);

        String name;
        int color, point;
        boolean visible, joined, label;
        for (int i=0; i<size; ++i) {
            name = itd.readString("Drawing Name", "unknow");
            color = itd.readInteger("Drawing Color", 5);
            visible = itd.readBoolean("Drawing Visible", true);
            joined = itd.readBoolean("Drawing Joined", true);
            point = itd.readInteger("Drawing Point Visible", 0);
            label = itd.readBoolean("Drawing Show Label", false);

            for (Drawing d : drawings) {
                if (d.getName().equals(name)) {
                    d.setColorIndex(color);
                    d.setVisible(visible);
                    d.setJoined(joined);
                    d.setPointType(point);
                    d.setShowLabel(label);
                    break;
                }
            }
        }
    }

    @Override
    public void saveToFileStream(OutputTextData otd) throws IOException {
        otd.writelnComment("Console Eng Calculator Plot Option Save Data");
       
        otd.writeln("Number of drawings", drawings.size());
        
        for (Drawing d : drawings) {
            otd.writeln("Drawing Name", d.getName());

            otd.writeln("Drawing Color", d.getColorIndex());

            otd.writeln("Drawing Visible", d.isVisible());

            otd.writeln("Drawing Joined", d.isJoined());

            otd.writeln("Drawing Point Visible", d.getPointType());
            
            otd.writeln("Drawing Show Label", d.isShowLabel());
        }
    }
}
