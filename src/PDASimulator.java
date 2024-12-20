/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.stream.Collectors;

/**
 * @author zimp
 */
public class PDASimulator extends javax.swing.JFrame {

    DecidableTuringMachine turingMachine;

    PDASetup setupDialog;

    int currentStep = 0; // Current step of the simulation
    String currentState;
    boolean stopSimulation = false; // Flag to stop the simulation
    Timer simulationTask = null;


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CheckStringButton;
    private javax.swing.JPanel LabelPanel;
    private javax.swing.JPanel MainContentPanel;
    private javax.swing.JPanel StackPanel;
    private javax.swing.JPanel bottomPanelWithButtonAndStatus;
    private javax.swing.JSlider characterSlider;
    private javax.swing.JPanel diagramPanel;
    private javax.swing.JButton editPDAButton;
    private javax.swing.JButton exportPDAButton;
    private javax.swing.JButton importPDAButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel languageDescriptionLabel;
    private javax.swing.JLabel languageSetBuilderFormLabel;
    private javax.swing.JTable operationsTable;
    private javax.swing.JButton resetButton;
    private javax.swing.JSlider speedSlider;
    private javax.swing.JTable stackTable;
    private javax.swing.JTable statesTable;
    private javax.swing.JLabel statusLabel;
    private javax.swing.JButton stopButton;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JPanel titleLabelPanel;
    private javax.swing.JTextField userStringInput;
    // End of variables declaration//GEN-END:variables

    /**
     * Creates new form PDASimulator
     */
    public PDASimulator() {
        initComponents();
        this.turingMachine = new DecidableTuringMachine();
        this.setupDialog = new PDASetup(this, true, turingMachine);
        userStringInput.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateString();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateString();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateString();
            }

            private void updateString() {
                String text = userStringInput.getText();
                if (text.isEmpty()) {
                    statusLabel.setText("The current string is **EMPTY**");
                    speedSlider.setEnabled(false);
                    CheckStringButton.setEnabled(false);
                    stopButton.setEnabled(false);
                    resetButton.setEnabled(false);
                } else {
                    statusLabel.setText("The current string is **UNPROCESSED**");
                    var lastChar = text.substring(text.length() - 1);
                    if (!turingMachine.inputAlphabet.contains(lastChar)) {
                        JOptionPane.showMessageDialog(
                                PDASimulator.this,
                                "Only characters " +
                                        humanize_list(turingMachine.inputAlphabet.toArray()) +
                                        " are allowed",
                                "Invalid Character; \"" +
                                        lastChar + "\"",
                                JOptionPane.ERROR_MESSAGE
                        );
                        SwingUtilities.invokeLater(() -> userStringInput.setText(text.substring(0, text.length() - 1)));
                        return;
                    }
                    speedSlider.setEnabled(true);
                    CheckStringButton.setEnabled(true);
                    resetButton.setEnabled(true);
                    stopButton.setEnabled(false);
                }
            }
        });
    }

    public static <T> String humanize_list(T[] arr) {
        if (arr.length == 0) {
            return "";
        } else if (arr.length == 1) {
            return arr[0].toString();
        } else if (arr.length == 2) {
            return arr[0].toString() + " and " + arr[1].toString();
        } else {
            var sb = new StringBuilder();
            for (int i = 0; i < arr.length - 1; i++) {
                sb.append(arr[i].toString()).append(", ");
            }
            sb.append(" and ").append(arr[arr.length - 1]);
            return sb.toString();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FlatRobotoFont.install();
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
        FlatMacLightLaf.setup();

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                PDASimulator frame = new PDASimulator();
                frame.setVisible(true);
//                var dialog = frame.setupDialog;
//                dialog.setVisible(true);
//                dialog.setLocationRelativeTo(frame);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    @Override
//                    public void windowClosed(java.awt.event.WindowEvent e) {
//                        if (dialog.stateManager.allowNext(false) && dialog.alphabetManager.allowNext(false) && dialog.transitionManager.allowNext(false)) {
//                            frame.currentState = frame.turingMachine.startState;
//                            frame.editPDAButton.setEnabled(true);
//                            return;
//                        }
//                        frame.dispose();
//                    }
//                });
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        bottomPanelWithButtonAndStatus = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        resetButton = new javax.swing.JButton();
        CheckStringButton = new javax.swing.JButton();
        stopButton = new javax.swing.JButton();
        editPDAButton = new javax.swing.JButton();
        importPDAButton = new javax.swing.JButton();
        exportPDAButton = new javax.swing.JButton();
        statusLabel = new javax.swing.JLabel();
        StackPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        stackTable = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        operationsTable = new javax.swing.JTable();
        MainContentPanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        speedSlider = new javax.swing.JSlider();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        LabelPanel = new javax.swing.JPanel();
        languageDescriptionLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        languageSetBuilderFormLabel = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        characterSlider = new javax.swing.JSlider();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        statesTable = new javax.swing.JTable();
        userStringInput = new javax.swing.JTextField();
        diagramPanel = new javax.swing.JPanel();
        titleLabelPanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.BorderLayout(10, 0));

        bottomPanelWithButtonAndStatus.setLayout(new java.awt.BorderLayout());

        java.awt.GridBagLayout jPanel8Layout = new java.awt.GridBagLayout();
        jPanel8Layout.columnWidths = new int[]{0, 50, 0, 50, 0, 50, 0, 50, 0};
        jPanel8Layout.rowHeights = new int[]{0, 34, 0, 34, 0};
        jPanel8.setLayout(jPanel8Layout);

        resetButton.setText("Reset Simulation");
        resetButton.setEnabled(false);
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 24;
        gridBagConstraints.ipady = 24;
        gridBagConstraints.insets = new java.awt.Insets(0, 24, 0, 0);
        jPanel8.add(resetButton, gridBagConstraints);

        CheckStringButton.setFont(new java.awt.Font("Noto Sans", 1, 16)); // NOI18N
        CheckStringButton.setText("Check String");
        CheckStringButton.setEnabled(false);
        CheckStringButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckStringButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.ipadx = 24;
        gridBagConstraints.ipady = 24;
        jPanel8.add(CheckStringButton, gridBagConstraints);

        stopButton.setText("Stop Simulation");
        stopButton.setEnabled(false);
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 24;
        gridBagConstraints.ipady = 24;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 24);
        jPanel8.add(stopButton, gridBagConstraints);

        editPDAButton.setText("Setup PDA");
        editPDAButton.setPreferredSize(new java.awt.Dimension(141, 24));
        editPDAButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editPDAButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.ipadx = 24;
        gridBagConstraints.ipady = 24;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 13, 0);
        jPanel8.add(editPDAButton, gridBagConstraints);

        importPDAButton.setText("Import PDA");
        importPDAButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importPDAButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 24;
        gridBagConstraints.ipady = 24;
        gridBagConstraints.insets = new java.awt.Insets(0, 24, 13, 0);
        jPanel8.add(importPDAButton, gridBagConstraints);

        exportPDAButton.setText("Export PDA");
        exportPDAButton.setEnabled(false);
        exportPDAButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportPDAButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 24;
        gridBagConstraints.ipady = 24;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 13, 24);
        jPanel8.add(exportPDAButton, gridBagConstraints);

        statusLabel.setFont(new java.awt.Font("Noto Sans", 3, 20)); // NOI18N
        statusLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        statusLabel.setText("The current string is **EMPTY**");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 9;
        jPanel8.add(statusLabel, gridBagConstraints);

        bottomPanelWithButtonAndStatus.add(jPanel8, java.awt.BorderLayout.CENTER);

        getContentPane().add(bottomPanelWithButtonAndStatus, java.awt.BorderLayout.PAGE_END);

        StackPanel.setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jScrollPane2.setPreferredSize(new java.awt.Dimension(200, 402));

        stackTable.setFont(new java.awt.Font("DejaVu Sans", 1, 20)); // NOI18N
        stackTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                        {null},
                        {null},
                        {null},
                        {null}
                },
                new String[]{
                        "Stack"
                }
        ) {
            final Class[] types = new Class[]{
                    java.lang.String.class
            };
            final boolean[] canEdit = new boolean[]{
                    false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        stackTable.setEnabled(false);
        stackTable.setFocusable(false);
        stackTable.setRowSelectionAllowed(false);
        stackTable.getTableHeader().setReorderingAllowed(false);
        stackTable.setUpdateSelectionOnSort(false);
        stackTable.setVerifyInputWhenFocusTarget(false);
        jScrollPane2.setViewportView(stackTable);
        if (stackTable.getColumnModel().getColumnCount() > 0) {
            stackTable.getColumnModel().getColumn(0).setResizable(false);
        }

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(85, 4, 101, 4);
        jPanel1.add(jScrollPane2, gridBagConstraints);

        jScrollPane1.setPreferredSize(new java.awt.Dimension(300, 402));

        operationsTable.setFont(new java.awt.Font("DejaVu Sans", 1, 20)); // NOI18N
        operationsTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                        {null, null},
                        {null, null},
                        {null, null},
                        {null, null}
                },
                new String[]{
                        "Input Char", "Stack Operation"
                }
        ) {
            final Class[] types = new Class[]{
                    java.lang.String.class, java.lang.String.class
            };
            final boolean[] canEdit = new boolean[]{
                    false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        operationsTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        operationsTable.setEnabled(false);
        operationsTable.setFocusable(false);
        operationsTable.setRequestFocusEnabled(false);
        operationsTable.setRowSelectionAllowed(false);
        operationsTable.getTableHeader().setResizingAllowed(false);
        operationsTable.getTableHeader().setReorderingAllowed(false);
        operationsTable.setUpdateSelectionOnSort(false);
        operationsTable.setVerifyInputWhenFocusTarget(false);
        jScrollPane1.setViewportView(operationsTable);
        if (operationsTable.getColumnModel().getColumnCount() > 0) {
            operationsTable.getColumnModel().getColumn(0).setResizable(false);
            operationsTable.getColumnModel().getColumn(0).setPreferredWidth(50);
            operationsTable.getColumnModel().getColumn(1).setResizable(false);
            operationsTable.getColumnModel().getColumn(1).setPreferredWidth(50);
        }

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(85, 4, 101, 4);
        jPanel1.add(jScrollPane1, gridBagConstraints);

        StackPanel.add(jPanel1, java.awt.BorderLayout.CENTER);

        getContentPane().add(StackPanel, java.awt.BorderLayout.LINE_START);

        MainContentPanel.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new java.awt.BorderLayout());

        speedSlider.setFont(new java.awt.Font("Noto Sans", 0, 14)); // NOI18N
        speedSlider.setMajorTickSpacing(25);
        speedSlider.setMaximum(200);
        speedSlider.setMinimum(25);
        speedSlider.setMinorTickSpacing(5);
        speedSlider.setPaintLabels(true);
        speedSlider.setPaintTicks(true);
        speedSlider.setSnapToTicks(true);
        speedSlider.setValue(100);
        speedSlider.setEnabled(false);
        speedSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                speedSliderStateChanged(evt);
            }
        });
        jPanel4.add(speedSlider, java.awt.BorderLayout.CENTER);
        java.util.Hashtable labelTable = new java.util.Hashtable();
        labelTable.put(Integer.valueOf(200), new javax.swing.JLabel("2x"));
        labelTable.put(Integer.valueOf(175), new javax.swing.JLabel("1.75x"));
        labelTable.put(Integer.valueOf(150), new javax.swing.JLabel("1.50x"));
        labelTable.put(Integer.valueOf(125), new javax.swing.JLabel("1.25x"));
        labelTable.put(Integer.valueOf(100), new javax.swing.JLabel("1x"));
        labelTable.put(Integer.valueOf(75), new javax.swing.JLabel("0.75"));
        labelTable.put(Integer.valueOf(50), new javax.swing.JLabel("0.50x"));
        labelTable.put(Integer.valueOf(25), new javax.swing.JLabel("0.25x"));
        speedSlider.setLabelTable(labelTable);

        jLabel1.setFont(new java.awt.Font("Noto Sans", 1, 20)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Speed Selector ( 1.0x )");
        jPanel4.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        MainContentPanel.add(jPanel4, java.awt.BorderLayout.PAGE_END);

        jPanel5.setLayout(new java.awt.BorderLayout());

        LabelPanel.setLayout(new java.awt.BorderLayout());

        languageDescriptionLabel.setFont(new java.awt.Font("DejaVu Sans", 2, 14)); // NOI18N
        languageDescriptionLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LabelPanel.add(languageDescriptionLabel, java.awt.BorderLayout.CENTER);

        jSeparator1.setPreferredSize(new java.awt.Dimension(0, 15));
        LabelPanel.add(jSeparator1, java.awt.BorderLayout.PAGE_END);

        languageSetBuilderFormLabel.setFont(new java.awt.Font("Liberation Sans", 1, 16)); // NOI18N
        LabelPanel.add(languageSetBuilderFormLabel, java.awt.BorderLayout.PAGE_START);

        jPanel5.add(LabelPanel, java.awt.BorderLayout.PAGE_START);

        jPanel7.setLayout(new java.awt.BorderLayout(10, 0));

        jLabel3.setFont(new java.awt.Font("Monospaced", 2, 18)); // NOI18N
        jLabel3.setText("User input string to verify:");
        jPanel7.add(jLabel3, java.awt.BorderLayout.PAGE_START);

        characterSlider.setPaintTrack(false);
        characterSlider.setEnabled(false);
        characterSlider.setFocusable(false);
        characterSlider.setRequestFocusEnabled(false);
        jPanel7.add(characterSlider, java.awt.BorderLayout.PAGE_END);

        jPanel9.setLayout(new java.awt.GridBagLayout());

        jScrollPane3.setPreferredSize(new java.awt.Dimension(150, 402));

        statesTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                        {null},
                        {null},
                        {null},
                        {null}
                },
                new String[]{
                        "States"
                }
        ) {
            final Class[] types = new Class[]{
                    java.lang.String.class
            };
            final boolean[] canEdit = new boolean[]{
                    false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        statesTable.setEnabled(false);
        jScrollPane3.setViewportView(statesTable);
        if (statesTable.getColumnModel().getColumnCount() > 0) {
            statesTable.getColumnModel().getColumn(0).setResizable(false);
        }

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        jPanel9.add(jScrollPane3, gridBagConstraints);

        userStringInput.setFont(new java.awt.Font("Liberation Sans", 1, 18)); // NOI18N
        userStringInput.setEnabled(false);
        userStringInput.setPreferredSize(new java.awt.Dimension(350, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        jPanel9.add(userStringInput, gridBagConstraints);

        javax.swing.GroupLayout diagramPanelLayout = new javax.swing.GroupLayout(diagramPanel);
        diagramPanel.setLayout(diagramPanelLayout);
        diagramPanelLayout.setHorizontalGroup(
                diagramPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );
        diagramPanelLayout.setVerticalGroup(
                diagramPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        jPanel9.add(diagramPanel, gridBagConstraints);

        jPanel7.add(jPanel9, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel7, java.awt.BorderLayout.CENTER);

        MainContentPanel.add(jPanel5, java.awt.BorderLayout.CENTER);

        getContentPane().add(MainContentPanel, java.awt.BorderLayout.CENTER);

        titleLabelPanel.setLayout(new java.awt.BorderLayout());

        titleLabel.setFont(new java.awt.Font("Noto Sans", 3, 30)); // NOI18N
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("PDA SIMULATOR");
        titleLabelPanel.add(titleLabel, java.awt.BorderLayout.PAGE_START);
        titleLabelPanel.add(jSeparator2, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(titleLabelPanel, java.awt.BorderLayout.PAGE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void speedSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_speedSliderStateChanged
        int speed = speedSlider.getValue();
        jLabel1.setText(String.format("Speed Selector ( %.2gx )", speed / 100.0));
        if (this.simulationTask != null) {
            simulationTask.setDelay((int) (1000 / (speed / 100f)));
        }
    }//GEN-LAST:event_speedSliderStateChanged

    private void CheckStringButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckStringButtonActionPerformed
        userStringInput.setEnabled(false);
        CheckStringButton.setEnabled(false);
        startSimulation();
    }//GEN-LAST:event_CheckStringButtonActionPerformed

    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
        userStringInput.setEnabled(true);
        userStringInput.setText("");
        statusLabel.setText("The current string is **EMPTY**");
        resetCharacterSlider();
        CheckStringButton.setEnabled(false);
        stopButton.setEnabled(false);
        resetButton.setEnabled(false);
        var stackModel = (DefaultTableModel) stackTable.getModel();
        var opModel = (DefaultTableModel) operationsTable.getModel();
        stackModel.setRowCount(0);
        opModel.setRowCount(0);
        statesTable.clearSelection();
    }//GEN-LAST:event_resetButtonActionPerformed

    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButtonActionPerformed
        if (simulationTask != null) {
            simulationTask.stop();
            stopSimulation = false;
            currentStep = 0;
            simulationTask = null;
        }
        stopButton.setEnabled(false);
        resetButton.setEnabled(true);
    }//GEN-LAST:event_stopButtonActionPerformed

    private void editPDAButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editPDAButtonActionPerformed
        editPDAButton.setEnabled(false);
        importPDAButton.setEnabled(false);
        exportPDAButton.setEnabled(false);
        setupDialog.turingMachine = turingMachine;
        setupDialog.refreshState();
        setupDialog.setVisible(true);
        setupDialog.setLocationRelativeTo(this);
        editPDAButton.setEnabled(true);
        importPDAButton.setEnabled(true);
        exportPDAButton.setEnabled(true);
        enableSimulator();
    }//GEN-LAST:event_editPDAButtonActionPerformed

    private void importPDAButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importPDAButtonActionPerformed
        var fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setDialogTitle("Select a PDA file (.JSON, .YAML, .YML)");
        var restrict = new FileNameExtensionFilter("Only .json and .yaml files", "json", "yaml", "yml");
        fileChooser.addChoosableFileFilter(restrict);
        var r = fileChooser.showOpenDialog(this);
        if (r == JFileChooser.APPROVE_OPTION) {
            var ext = fileChooser.getSelectedFile().getName().split("\\.")[1];
            ObjectMapper om;
            switch (ext) {
                case "json":
                    om = new ObjectMapper();
                    break;
                case "yaml":
                case "yml":
                    om = new ObjectMapper(new YAMLFactory());
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Invalid file format", "Invalid file", JOptionPane.ERROR_MESSAGE);
                    return;
            }
            try {
                var tm = om.readValue(fileChooser.getSelectedFile(), DecidableTuringMachine.class);
                this.turingMachine.updateFrom(tm);
                enableSimulator();
            } catch (DatabindException e) {
                JOptionPane.showMessageDialog(this,
                        """
                                The PDA format in the given file is invalid.
                                
                                Please ensure the all the following keys are included in the YAML/JSON file:
                                
                                - states ( a list of the names of the states included in this PDA )
                                
                                - inputAlphabet ( a list of the input characters allowed in this PDA )
                                
                                - stackAlphabet ( a list of the stack characters allowed in this PDA )
                                
                                - transitions ( Key-Value pairs describing each \
                                transition in the PDA where the key is the initial state \
                                and the value is a list of key-value pairs \
                                where the key is the go to state and the value is another key-value pair \
                                with the following keys:
                                
                                    - consumeCharacter ( the input character to be read or epsilon (ε) )
                                
                                    - stackOp ( the stack operation to be performed: PUSH, POP, or NOP )
                                
                                    - stackOpArg ( the character to be pushed or popped from the stack, or null if stackOp is NOP )
                                
                                - startState
                                
                                - finalStates
                                
                                - languageDescription
                                
                                - languageSetBuilderForm
                                """,
                        "Invalid PDA Format",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error reading file", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_importPDAButtonActionPerformed

    private void exportPDAButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportPDAButtonActionPerformed
        var fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        var r = fileChooser.showSaveDialog(this);
        if (r == JFileChooser.APPROVE_OPTION) {
            var filename = JOptionPane.showInputDialog(this, "Enter the file name (include .json or .yaml as the extension)", "Input file name", JOptionPane.QUESTION_MESSAGE);
            if (!filename.contains(".")) {
                JOptionPane.showMessageDialog(this, "Invalid file format (missing extension?)", "Invalid file", JOptionPane.ERROR_MESSAGE);
                return;
            }
            var ext = filename.split("\\.")[1];
            ObjectMapper om;
            switch (ext.toLowerCase()) {
                case "json":
                    om = new ObjectMapper();
                    break;
                case "yaml":
                case "yml":
                    om = new ObjectMapper(new YAMLFactory());
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Invalid file format", "Invalid file", JOptionPane.ERROR_MESSAGE);
                    return;
            }
            try {
                om.writeValue(new File(fileChooser.getSelectedFile().getAbsolutePath() + File.separator + filename), turingMachine);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error writing file", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_exportPDAButtonActionPerformed

    public void enableSimulator() {
        userStringInput.setEnabled(true);
        exportPDAButton.setEnabled(true);
        editPDAButton.setText("Edit PDA");
        languageDescriptionLabel.setText(turingMachine.languageDescription);
        languageSetBuilderFormLabel.setText(turingMachine.languageSetBuilderForm);
        var stackModel = (DefaultTableModel) stackTable.getModel();
        var opModel = (DefaultTableModel) operationsTable.getModel();
        stackModel.setRowCount(0);
        opModel.setRowCount(0);
        userStringInput.setText("");
        resetCharacterSlider();
        CheckStringButton.setEnabled(false);
        stopButton.setEnabled(false);
        resetButton.setEnabled(false);
        statusLabel.setText("The current string is **EMPTY**");
        ((DefaultTableModel) statesTable.getModel()).setRowCount(0);
        turingMachine.states.forEach(s -> ((DefaultTableModel) statesTable.getModel()).addRow(new Object[]{s}));

        // TODO: Generate an image of the PDA and put it in the diagramPanel
        // Use a graphviz library to do so
    }

    private void startSimulation() {
        editPDAButton.setEnabled(false);
        stopSimulation = false;
        currentState = turingMachine.startState;
        var characters = userStringInput.getText().split("");
        var stackModel = (DefaultTableModel) stackTable.getModel();
        var opModel = (DefaultTableModel) operationsTable.getModel();
        opModel.setRowCount(0);
        stackModel.setRowCount(0);
        statesTable.setRowSelectionInterval(0, 0);
        setupCharacterSlider(characters);
        simulationTask = new Timer(1000, null);
        simulationTask.setRepeats(true);
        simulationTask.addActionListener(e -> {
            resetButton.setEnabled(false);
            stopButton.setEnabled(true);
            simulate(characters);
            simulationTask.setDelay((int) (1000 / (speedSlider.getValue() / 100f)));
            if (stopSimulation) {
                if (turingMachine.isFinalState(currentState) && currentStep >= characters.length) {
                    JOptionPane.showMessageDialog(this, "The given input string was accepted by the specified PDA", "Valid String", JOptionPane.INFORMATION_MESSAGE);
                    statusLabel.setText("The current string is **VALID**");
                } else {
                    JOptionPane.showMessageDialog(this, "The given input string was not accepted by the specified PDA", "Invalid String", JOptionPane.ERROR_MESSAGE);
                    statusLabel.setText("The current string is **INVALID**");
                }
                currentStep = 0;
                currentState = turingMachine.startState;
                stopSimulation = false;
                stopButton.setEnabled(false);
                resetButton.setEnabled(true);
                editPDAButton.setEnabled(true);
                simulationTask.stop();
                return;
            }
            resetButton.setEnabled(false);
            stopButton.setEnabled(true);
        });
        simulationTask.start();
    }

    private void setupCharacterSlider(String[] characters) {
        characterSlider.setMaximum(characters.length - 1);
        characterSlider.setValue(0);
        var hash = new Hashtable<Integer, javax.swing.JLabel>();
        var i = 0;
        for (var c : characters) {
            hash.put(i++, new javax.swing.JLabel(c));
        }
        characterSlider.setLabelTable(hash);
        characterSlider.setPaintLabels(true);
        characterSlider.setPaintTicks(true);
        characterSlider.setPaintTrack(true);
        characterSlider.setSnapToTicks(true);
        characterSlider.setMajorTickSpacing(1);
    }

    private void resetCharacterSlider() {
        characterSlider.setMaximum(100);
        characterSlider.setValue(50);
        characterSlider.setPaintLabels(false);
        characterSlider.setPaintTicks(false);
        characterSlider.setSnapToTicks(false);
        characterSlider.setMajorTickSpacing(0);
        characterSlider.setLabelTable(new Hashtable<>());
    }

    private void simulate(String[] input) {
        var possibleTransitions = new HashMap<String, ArrayList<TransitionInfo>>();
        var blankTransitions = new HashMap<String, ArrayList<TransitionInfo>>();
        String topOfStack = stackTable.getRowCount() > 0 ? (String) stackTable.getValueAt(0, 0) : "";
        var allTransitions = turingMachine.transitions.get(currentState);
        if ((allTransitions == null || allTransitions.isEmpty())) {
//            if (currentStep < input.length && !turingMachine.isFinalState(currentState)) {
//                JOptionPane.showMessageDialog(PDASimulator.this, "The given input string was not accepted by the specified PDA`", "Invalid String", JOptionPane.ERROR_MESSAGE);
//            }
            stopSimulation = true;
            return;
        }
        var currentChar = currentStep < input.length ? input[currentStep] : TransitionManagerPanel.BLANK_CHARACTER;
        allTransitions.forEach(
                (gotoState, possibilities) -> {
                    var toAdd = possibilities.stream().filter(
                            ti -> {
                                if (ti.stackOp == StackOperation.POP && !topOfStack.equals(ti.stackOpArg)) {
                                    return false;
                                }
                                if (ti.consumeCharacter.equals(TransitionManagerPanel.BLANK_CHARACTER)) {
                                    var arr = blankTransitions.computeIfAbsent(gotoState, _ -> new ArrayList<>());
                                    arr.add(ti);
                                    return false;
                                } else
                                    return ti.consumeCharacter.equals(currentChar);
                            }
                    ).collect(Collectors.toCollection(ArrayList::new));
                    if (!toAdd.isEmpty()) {
                        possibleTransitions.put(gotoState, toAdd);
                    }

                }
        );

        String gotoState;
        TransitionInfo ti;

        if (possibleTransitions.isEmpty() && blankTransitions.isEmpty()) {
            stopSimulation = true;
            return;
        } else {
            var transitionHash = possibleTransitions.isEmpty() ? blankTransitions : possibleTransitions;
            gotoState = transitionHash.keySet().iterator().next();
            ti = transitionHash.get(gotoState).getFirst();
        }

        this.handleTransition(currentState, gotoState, ti);

    }

    private void handleTransition(String initialState, String gotoState, TransitionInfo ti) {
        if (!initialState.equals(gotoState)) {
            currentState = gotoState;
            var ind = turingMachine.states.indexOf(currentState);
            statesTable.setRowSelectionInterval(ind, ind);
        }
        if (!ti.consumeCharacter.equals(TransitionManagerPanel.BLANK_CHARACTER)) {
            currentStep += 1;
        }
        characterSlider.setValue(currentStep);
        if (ti.stackOp != StackOperation.NOP) {
            ((DefaultTableModel) operationsTable.getModel()).addRow(new Object[]{ti.consumeCharacter, ti.stackOp.name() + " " + ti.stackOpArg});
            if (ti.stackOp == StackOperation.POP) {
                ((DefaultTableModel) stackTable.getModel()).removeRow(0);
            } else {
                ((DefaultTableModel) stackTable.getModel()).insertRow(0, new Object[]{ti.stackOpArg});
            }
        }
    }

}
